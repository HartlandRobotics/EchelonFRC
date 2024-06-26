package org.hartlandrobotics.echelon2.matchScouting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.configuration.AdminSettings;
import org.hartlandrobotics.echelon2.configuration.AdminSettingsProvider;
//import org.hartlandrobotics.echelon2.database.crescendo.CrescendoResult;
import org.hartlandrobotics.echelon2.database.currentGame.CurrentGamePoints;
import org.hartlandrobotics.echelon2.database.entities.MatchResult;
import org.hartlandrobotics.echelon2.models.MatchResultViewModel;
import org.hartlandrobotics.echelon2.status.BlueAllianceStatus;

public class MatchScoutingAutoActivity extends AppCompatActivity {
    private static final String MATCH_KEY = "match_key_param";
    private static final String TEAM_KEY = "team_key_param";

    private ImageButton leaveLineAuto;
    private ImageButton autoSpeaker;
    private ImageButton autoAmp;

    // we are finally going to use text buttons to let them take away things on the screen so they don't keep crying about it
    private ImageButton subtractSpeaker;
    private ImageButton subtractAmp;
    private MaterialTextView speakerPoints;
    private MaterialTextView ampPoints;

   private MaterialTextView teamKeyText;

    int autoAmpDrawable;


    MatchResultViewModel matchResultViewModel;
    //CrescendoResult crescendoResult;
    CurrentGamePoints currentResult;

    String matchKey;
    String teamKey;


    public static void launch(Context context, String matchKey, String teamKey){
        Intent intent = new Intent(context, MatchScoutingAutoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(MATCH_KEY, matchKey);
        bundle.putString(TEAM_KEY, teamKey);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_auto_scouting);

        setupColor();
        setupControls();

        Bundle bundle = getIntent().getExtras();
        matchKey = bundle.getString(MATCH_KEY);
        teamKey = bundle.getString(TEAM_KEY);

        teamKeyText = findViewById(R.id.teamKeyText);
        teamKeyText.setText(teamKey);

        BlueAllianceStatus blueAllianceStatus = new BlueAllianceStatus(getApplicationContext());

        matchResultViewModel = new ViewModelProvider(this).get(MatchResultViewModel.class);
        matchResultViewModel.getMatchResultByMatchTeam(matchKey, teamKey)
                .observe(MatchScoutingAutoActivity.this, mr->{
                    if( mr == null ){
                        mr = matchResultViewModel.getDefault(blueAllianceStatus.getEventKey(), matchKey, teamKey);
                    }
                    currentResult = MatchResult.toCurrentGamePoints(mr);
                    populateControlsFromData();
                });
    }


    // all red text that refers from things from last year needs to be updated
    public void populateControlsFromData(){
        speakerPoints.setText(String.valueOf(currentResult.getAuto3Counts()));
        ampPoints.setText(String.valueOf(currentResult.getAuto2Counts()));

        if(currentResult.getAuto1Counts() > 0){
            leaveLineAuto.setImageResource(R.drawable.out_line);
        } else {
            leaveLineAuto.setImageResource(R.drawable.in_line);
        }
    }

    public void setupControls(){




        leaveLineAuto = findViewById(R.id.park);
        leaveLineAuto.setImageResource(R.drawable.in_line);
        leaveLineAuto.setOnClickListener(v -> {
            currentResult.result.setAutoFlag1( !(currentResult.getAuto1Counts() > 0 ));
            populateControlsFromData();

        });

        autoSpeaker = findViewById(R.id.autoSpeaker);
        speakerPoints = findViewById(R.id.speakerPoints);
        subtractSpeaker = findViewById(R.id.subtractSpeakerPointsAuto);
        autoSpeaker.setOnClickListener(v -> {
            currentResult.result.setAutoInt3( Math.max(currentResult.getAuto3Counts() + 1, 0));
            populateControlsFromData();
        });
        subtractSpeaker.setOnClickListener(v -> {
            currentResult.result.setAutoInt3(Math.max(currentResult.getAuto3Counts() - 1, 0));
            populateControlsFromData();
        });

        autoAmp = findViewById(R.id.autoAmp);
        ampPoints = findViewById(R.id.ampPointsAuto);
        subtractAmp = findViewById(R.id.subtractAmpPoints);
        autoAmp.setImageResource(autoAmpDrawable);
        autoAmp.setOnClickListener(v -> {
            currentResult.result.setAutoInt2( Math.max(currentResult.getAuto2Counts() + 1, 0) );
            populateControlsFromData();
        });
        subtractAmp.setOnClickListener(v -> {
            currentResult.result.setAutoInt2(Math.max(currentResult.getAuto2Counts() - 1, 0));
            populateControlsFromData();
        });

        MaterialButton teleOpButton = findViewById(R.id.teleOp);
        teleOpButton.setOnClickListener(v -> {
            matchResultViewModel.upsert(currentResult.result);
            MatchScoutingTeleopActivity.launch(MatchScoutingAutoActivity.this, matchKey, teamKey );
        });
    }

    public void setupColor() {
        AdminSettings settings = AdminSettingsProvider.getAdminSettings(getApplicationContext());

        if (settings.getDeviceRole().startsWith("red")){
            autoAmpDrawable = R.drawable.auto_red_amp;
        } else {
            autoAmpDrawable = R.drawable.auto_blue_amp;
        }
    }

}