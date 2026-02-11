package org.hartlandrobotics.echelonFRC;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import org.hartlandrobotics.echelonFRC.database.currentGame.CurrentGamePoints;
import org.hartlandrobotics.echelonFRC.database.entities.Match;
import org.hartlandrobotics.echelonFRC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFRC.database.repositories.MatchRepo;
import org.hartlandrobotics.echelonFRC.database.repositories.MatchResultRepo;

import java.util.ArrayList;
import java.util.List;

public class AccountabilityActivity extends EchelonActivity {

    String TAG = "AccountabilityActivity";
    MatchRepo matchRepo;
    MatchResultRepo matchResultRepo;

    public static void launch(Context context){
        Intent intent = new Intent( context, AccountabilityActivity.class );
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accuracy);

        setupToolbar("Accuracy");

        matchRepo= new MatchRepo(getApplication());
        matchResultRepo= new MatchResultRepo(getApplication());
        matchRepo.getMatches().observe( this, lm -> {


            for (Match match:lm){
                List<String> allianceTeams = new ArrayList<String>();
                match.getBlueScore();

                String matchKey = match.getMatchKey();

                int blueBlueAllianceScore = match.getBlueScore();
                allianceTeams.add(match.getBlue1TeamKey());
                allianceTeams.add(match.getBlue2TeamKey());
                allianceTeams.add(match.getBlue3TeamKey());

                List<String> redAllianceTeams = new ArrayList<String>();
                match.getRedScore();

                int redBlueAllianceScore = match.getRedScore();
                allianceTeams.add(match.getRed1TeamKey());
                allianceTeams.add(match.getRed2TeamKey());
                allianceTeams.add(match.getRed3TeamKey());

                for(String teamKey : allianceTeams) {
                    matchResultRepo.getMatchResultByMatchTeam(matchKey, teamKey).observe(this, mr -> {
                        if( mr != null) {
                            Log.i(TAG, mr.getAlliance());
                            CurrentGamePoints pts =  new CurrentGamePoints(mr);
                        }
                    });
                }
                int i;
                i=10;
            }



            // red alliance
        });

    }

}
