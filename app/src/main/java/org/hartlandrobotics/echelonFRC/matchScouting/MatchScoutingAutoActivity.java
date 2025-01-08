package org.hartlandrobotics.echelonFRC.matchScouting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelonFRC.R;
import org.hartlandrobotics.echelonFRC.configuration.AdminSettings;
import org.hartlandrobotics.echelonFRC.configuration.AdminSettingsProvider;
//import org.hartlandrobotics.echelonFRC.database.crescendo.CrescendoResult;
import org.hartlandrobotics.echelonFRC.database.currentGame.CurrentGamePoints;
import org.hartlandrobotics.echelonFRC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFRC.models.MatchResultViewModel;
import org.hartlandrobotics.echelonFRC.status.BlueAllianceStatus;

public class MatchScoutingAutoActivity extends AppCompatActivity {
    private static final String MATCH_KEY = "match_key_param";
    private static final String TEAM_KEY = "team_key_param";

    private ImageButton leaveLineAuto;
    private ImageButton autoReefLevelOne;
    private ImageButton autoReefLevelTwo;
    private ImageButton autoReefLevelThree;
    private ImageButton autoReefLevelFour;
    private ImageButton autoProcessor;
    private ImageButton autoNet;

    // we are finally going to use text buttons to let them take away things on the screen so they don't keep crying about it
    private ImageButton subtractReefLevelOne;
    private ImageButton subtractReefLevelTwo;
    private ImageButton subtractReefLevelThree;
    private ImageButton subtractReefLevelFour;
    private ImageButton subtractProcessor;
    private ImageButton subtractNet;
    private MaterialTextView reefLevelOnePoints;
    private MaterialTextView reefLevelTwoPoints;
    private MaterialTextView reefLevelThreePoints;
    private MaterialTextView reefLevelFourPoints;
    private MaterialTextView processorPoints;
    private MaterialTextView netPoints;

   private MaterialTextView teamKeyText;

    int parkDrawable;
    int processorDrawable;



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
        reefLevelOnePoints.setText(String.valueOf(currentResult.getAuto3Counts()));
        reefLevelTwoPoints.setText(String.valueOf(currentResult.getAuto2Counts()));
        reefLevelThreePoints.setText(String.valueOf(currentResult.getAuto2Counts()));
        reefLevelFourPoints.setText(String.valueOf(currentResult.getAuto2Counts()));
        processorPoints.setText(String.valueOf(currentResult.getAuto2Counts()));
        netPoints.setText(String.valueOf(currentResult.getAuto2Counts()));

        if(currentResult.getAuto1Counts() > 0){
            leaveLineAuto.setImageResource(R.drawable.out_line);
        } else {
            leaveLineAuto.setImageResource(R.drawable.in_line_red);
        }
    }

    public void setupControls(){




        leaveLineAuto = findViewById(R.id.park);
        leaveLineAuto.setImageResource(R.drawable.in_line);
        leaveLineAuto.setOnClickListener(v -> {
            currentResult.result.setAutoFlag1( !(currentResult.getAuto1Counts() > 0 ));
            populateControlsFromData();

        });

        autoReefLevelOne = findViewById(R.id.reef_level_one);
        reefLevelOnePoints = findViewById(R.id.reef_level_one_text);
        subtractReefLevelOne = findViewById(R.id.reef_level_one_decrement);
        autoReefLevelOne.setOnClickListener(v -> {
            currentResult.result.setAutoInt6( Math.max(currentResult.result.getAutoInt6() + 1, 0));
            populateControlsFromData();
        });
        subtractReefLevelOne.setOnClickListener(v -> {
            currentResult.result.setAutoInt6(Math.max(currentResult.result.getAutoInt6() - 1, 0));
            populateControlsFromData();
        });

        autoReefLevelTwo = findViewById(R.id.reef_level_two);
        reefLevelTwoPoints = findViewById(R.id.reef_level_two_text);
        subtractReefLevelTwo = findViewById(R.id.reef_level_two_decrement);
        autoReefLevelTwo.setOnClickListener(v -> {
            currentResult.result.setAutoInt3( Math.max(currentResult.getAuto3Counts() + 1, 0));
            populateControlsFromData();
        });
        subtractReefLevelTwo.setOnClickListener(v -> {
            currentResult.result.setAutoInt3(Math.max(currentResult.getAuto3Counts() - 1, 0));
            populateControlsFromData();
        });

        autoReefLevelThree = findViewById(R.id.reef_level_three);
        reefLevelThreePoints = findViewById(R.id.reef_level_three_text);
        subtractReefLevelThree = findViewById(R.id.reef_level_three_decrement);
        autoReefLevelThree.setOnClickListener(v -> {
            currentResult.result.setAutoInt3( Math.max(currentResult.getAuto3Counts() + 1, 0));
            populateControlsFromData();
        });
        subtractReefLevelThree.setOnClickListener(v -> {
            currentResult.result.setAutoInt3(Math.max(currentResult.getAuto3Counts() - 1, 0));
            populateControlsFromData();
        });

        autoReefLevelFour = findViewById(R.id.reef_level_four);
        reefLevelFourPoints = findViewById(R.id.reef_level_four_text);
        subtractReefLevelFour = findViewById(R.id.reef_level_four_decrement);
        autoReefLevelFour.setOnClickListener(v -> {
            currentResult.result.setAutoInt3( Math.max(currentResult.getAuto3Counts() + 1, 0));
            populateControlsFromData();
        });
        subtractReefLevelFour.setOnClickListener(v -> {
            currentResult.result.setAutoInt3(Math.max(currentResult.getAuto3Counts() - 1, 0));
            populateControlsFromData();
        });

        autoProcessor = findViewById(R.id.processor);
        processorPoints = findViewById(R.id.processor_text);
        subtractProcessor = findViewById(R.id.processor_decrement);
        autoProcessor.setOnClickListener(v -> {
            currentResult.result.setAutoInt3( Math.max(currentResult.getAuto3Counts() + 1, 0));
            populateControlsFromData();
        });
        subtractProcessor.setOnClickListener(v -> {
            currentResult.result.setAutoInt3(Math.max(currentResult.getAuto3Counts() - 1, 0));
            populateControlsFromData();
        });

        autoNet = findViewById(R.id.net);
        netPoints = findViewById(R.id.net_text);
        subtractNet = findViewById(R.id.net_decrement);
        autoNet.setOnClickListener(v -> {
            currentResult.result.setAutoInt3( Math.max(currentResult.getAuto3Counts() + 1, 0));
            populateControlsFromData();
        });
        subtractNet.setOnClickListener(v -> {
            currentResult.result.setAutoInt3(Math.max(currentResult.getAuto3Counts() - 1, 0));
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
            parkDrawable = R.drawable.in_line_red;
        } else {
            parkDrawable = R.drawable.in_line_blue;
        }

        if (settings.getDeviceRole().startsWith("red")){
            processorDrawable = R.drawable.processor_red;
        } else {
            processorDrawable = R.drawable.processor_blue;
        }
    }

}