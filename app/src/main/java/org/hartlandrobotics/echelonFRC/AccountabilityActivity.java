package org.hartlandrobotics.echelonFRC;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import org.hartlandrobotics.echelonFRC.database.currentGame.CurrentGamePoints;
import org.hartlandrobotics.echelonFRC.database.entities.Match;
import org.hartlandrobotics.echelonFRC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFRC.database.repositories.EventRepo;
import org.hartlandrobotics.echelonFRC.database.repositories.MatchRepo;
import org.hartlandrobotics.echelonFRC.database.repositories.MatchResultRepo;
import org.hartlandrobotics.echelonFRC.status.BlueAllianceStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AccountabilityActivity extends EchelonActivity {

    String TAG = "AccountabilityActivity";
    EventRepo eventRepo;
    MatchRepo matchRepo;
    MatchResultRepo matchResultRepo;

    public static void launch(Context context) {
        Intent intent = new Intent(context, AccountabilityActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accuracy);

        setupToolbar("Accuracy");

        BlueAllianceStatus status = new BlueAllianceStatus(getApplicationContext());
        String currentEvent = status.getEventKey();

        eventRepo = new EventRepo(getApplication());
        matchRepo = new MatchRepo(getApplication());
        matchResultRepo = new MatchResultRepo(getApplication());

        eventRepo.getMatchesForEvent(currentEvent).observe(this, e -> {
            matchResultRepo.getMatchResultsByEvent(currentEvent).observe(this, matchResultList -> {
                for (Match m : e.matches) {
                    String currentMatchKey = m.getMatchKey();


                    int BlueScore = m.getBlueScore();
                    int RedScore = m.getRedScore();

                    int studentRedScore = 0;
                    int studentBlueScore = 0;
                    List<MatchResult> matchResultByMatch = matchResultList.stream()
                            .filter(mr -> mr.getMatchKey().equals(currentMatchKey))
                            .collect(Collectors.toList());

                    for (MatchResult mr : matchResultByMatch) {
                        String alliance = mr.getAlliance();
                        CurrentGamePoints cg = new CurrentGamePoints(mr);
                        if (alliance.equals("red")) {
                            studentRedScore += cg.getAutoPoints() + cg.getTeleOpPoints() + cg.getEndPoints();
                        }
                        if (alliance.equals("blue")) {
                            studentBlueScore += cg.getAutoPoints() + cg.getTeleOpPoints() + cg.getEndPoints();
                        }
                    }



                }
            });

        });


    }

}
