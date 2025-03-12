package org.hartlandrobotics.echelonFRC.matchScouting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
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
    private MaterialButton autoRightBlue;
    private MaterialButton autoCenterBlue;
    private MaterialButton autoLeftBlue;
    private MaterialTextView driversText;
    private MaterialButton autoLeftRed;
    private MaterialButton autoCenterRed;
    private MaterialButton autoRightRed;
    private MaterialTextView redDriversText;

    private int buttonColor;
    private int buttonSelectedTextColor;
    private int secondaryDarkColor;

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

        preSetupControls();
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
        reefLevelOnePoints.setText(String.valueOf(currentResult.result.getAutoInt6()));
        reefLevelTwoPoints.setText(String.valueOf(currentResult.result.getAutoInt7()));
        reefLevelThreePoints.setText(String.valueOf(currentResult.result.getAutoInt8()));
        reefLevelFourPoints.setText(String.valueOf(currentResult.result.getAutoInt9()));
        processorPoints.setText(String.valueOf(currentResult.result.getAutoInt10()));
        netPoints.setText(String.valueOf(currentResult.result.getAutoInt11()));

        if(currentResult.result.getAutoFlag1()){
            leaveLineAuto.setImageResource(R.drawable.out_line_green);
        } else {
            leaveLineAuto.setImageResource(parkDrawable);
        }

        if (currentResult.result.getAutoFlag2()){
            autoRightBlue.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(secondaryDarkColor)));
            autoRightBlue.setTextColor(getResources().getColor(buttonSelectedTextColor));
            autoRightRed.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(secondaryDarkColor)));
            autoRightRed.setTextColor(getResources().getColor(buttonSelectedTextColor));
        }
        else{
            autoRightBlue.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            autoRightBlue.setTextColor(getResources().getColor(secondaryDarkColor));
            autoRightRed.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            autoRightRed.setTextColor(getResources().getColor(secondaryDarkColor));
        }

        if (currentResult.result.getAutoFlag3()){
            autoCenterBlue.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(secondaryDarkColor)));
            autoCenterBlue.setTextColor(getResources().getColor(buttonSelectedTextColor));
            autoCenterRed.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(secondaryDarkColor)));
            autoCenterRed.setTextColor(getResources().getColor(buttonSelectedTextColor));
        }
        else{
            autoCenterBlue.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            autoCenterBlue.setTextColor(getResources().getColor(secondaryDarkColor));
            autoCenterRed.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            autoCenterRed.setTextColor(getResources().getColor(secondaryDarkColor));
        }

        if (currentResult.result.getAutoFlag4()){
            autoLeftBlue.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(secondaryDarkColor)));
            autoLeftBlue.setTextColor(getResources().getColor(buttonSelectedTextColor));
            autoLeftRed.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(secondaryDarkColor)));
            autoLeftRed.setTextColor(getResources().getColor(buttonSelectedTextColor));
        }
        else{
            autoLeftBlue.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            autoLeftBlue.setTextColor(getResources().getColor(secondaryDarkColor));
            autoLeftRed.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            autoLeftRed.setTextColor(getResources().getColor(secondaryDarkColor));
        }

    }

    public void preSetupControls(){
        autoRightBlue = findViewById(R.id.auto_right_blue);
        autoRightBlue.setOnClickListener(v -> {
            currentResult.result.setAutoFlag2( !(currentResult.result.getAutoFlag2()  ));
            currentResult.result.setAutoFlag3(false);
            currentResult.result.setAutoFlag4(false);
            populateControlsFromData();
        });

        autoCenterBlue = findViewById(R.id.auto_center_blue);
        autoCenterBlue.setOnClickListener(v -> {
            currentResult.result.setAutoFlag3( !(currentResult.result.getAutoFlag3()  ));
            currentResult.result.setAutoFlag2(false);
            currentResult.result.setAutoFlag4(false);
            populateControlsFromData();
        });

        autoLeftBlue = findViewById(R.id.auto_left_blue);
        autoLeftBlue.setOnClickListener(v -> {
            currentResult.result.setAutoFlag4( !(currentResult.result.getAutoFlag4()  ));
            currentResult.result.setAutoFlag3(false);
            currentResult.result.setAutoFlag2(false);
            populateControlsFromData();
        });

        driversText = findViewById(R.id.drivers_text);

        autoLeftRed = findViewById(R.id.auto_left_red);
        autoLeftRed.setOnClickListener(v -> {
            currentResult.result.setAutoFlag4( !(currentResult.result.getAutoFlag4()  ));
            populateControlsFromData();
        });

        autoCenterRed = findViewById(R.id.auto_center_red);
        autoCenterRed.setOnClickListener(v -> {
            currentResult.result.setAutoFlag3( !(currentResult.result.getAutoFlag3()  ));
            populateControlsFromData();
        });

        autoRightRed = findViewById(R.id.auto_right_red);
        autoRightRed.setOnClickListener(v -> {
            currentResult.result.setAutoFlag2( !(currentResult.result.getAutoFlag2()  ));
            populateControlsFromData();
        });

        redDriversText = findViewById(R.id.red_drivers_text);
    }
    public void setupControls(){

        leaveLineAuto = findViewById(R.id.park);
        leaveLineAuto.setImageResource(R.drawable.in_line);
        leaveLineAuto.setOnClickListener(v -> {
            currentResult.result.setAutoFlag1( !(currentResult.result.getAutoFlag1()  ));
            populateControlsFromData();

        });

        autoReefLevelOne = findViewById(R.id.reef_level_one);
        reefLevelOnePoints = findViewById(R.id.reef_level_one_text);
        subtractReefLevelOne = findViewById(R.id.reef_level_one_decrement);
        autoReefLevelOne.setOnClickListener(v -> {
            //need to update single page scoring sheet so we know what these are
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
            currentResult.result.setAutoInt7( Math.max(currentResult.result.getAutoInt7() + 1, 0));
            populateControlsFromData();
        });
        subtractReefLevelTwo.setOnClickListener(v -> {
            currentResult.result.setAutoInt7(Math.max(currentResult.result.getAutoInt7() - 1, 0));
            populateControlsFromData();
        });

        autoReefLevelThree = findViewById(R.id.reef_level_three);
        reefLevelThreePoints = findViewById(R.id.reef_level_three_text);
        subtractReefLevelThree = findViewById(R.id.reef_level_three_decrement);
        autoReefLevelThree.setOnClickListener(v -> {
            currentResult.result.setAutoInt8( Math.max(currentResult.result.getAutoInt8() + 1, 0));
            populateControlsFromData();
        });
        subtractReefLevelThree.setOnClickListener(v -> {
            currentResult.result.setAutoInt8(Math.max(currentResult.result.getAutoInt8() - 1, 0));
            populateControlsFromData();
        });

        autoReefLevelFour = findViewById(R.id.reef_level_four);
        reefLevelFourPoints = findViewById(R.id.reef_level_four_text);
        subtractReefLevelFour = findViewById(R.id.reef_level_four_decrement);
        autoReefLevelFour.setOnClickListener(v -> {
            currentResult.result.setAutoInt9( Math.max(currentResult.result.getAutoInt9() + 1, 0));
            populateControlsFromData();
        });
        subtractReefLevelFour.setOnClickListener(v -> {
            currentResult.result.setAutoInt9(Math.max(currentResult.result.getAutoInt9() - 1, 0));
            populateControlsFromData();
        });

        autoProcessor = findViewById(R.id.processor);
        autoProcessor.setImageResource(processorDrawable);
        processorPoints = findViewById(R.id.processor_text);
        subtractProcessor = findViewById(R.id.processor_decrement);
        autoProcessor.setOnClickListener(v -> {
            currentResult.result.setAutoInt10( Math.max(currentResult.result.getAutoInt10() + 1, 0));
            populateControlsFromData();
        });
        subtractProcessor.setOnClickListener(v -> {
            currentResult.result.setAutoInt10(Math.max(currentResult.result.getAutoInt10() - 1, 0));
            populateControlsFromData();
        });

        autoNet = findViewById(R.id.net);
        netPoints = findViewById(R.id.net_text);
        subtractNet = findViewById(R.id.net_decrement);
        autoNet.setOnClickListener(v -> {
            currentResult.result.setAutoInt11( Math.max(currentResult.result.getAutoInt11() + 1, 0));
            populateControlsFromData();
        });
        subtractNet.setOnClickListener(v -> {
            currentResult.result.setAutoInt11(Math.max(currentResult.result.getAutoInt11() - 1, 0));
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

        buttonSelectedTextColor = R.color.primaryDarkColor;
        secondaryDarkColor = R.color.secondaryDarkColor;

        if (settings.getDeviceRole().startsWith("red")){
            parkDrawable = R.drawable.in_line_red;
            processorDrawable = R.drawable.processor_red;
            autoLeftRed.setVisibility(View.VISIBLE);
            autoCenterRed.setVisibility(View.VISIBLE);
            autoRightRed.setVisibility(View.VISIBLE);
            redDriversText.setVisibility(View.VISIBLE);
            driversText.setVisibility(View.GONE);
            autoRightBlue.setVisibility(View.GONE);
            autoCenterBlue.setVisibility(View.GONE);
            autoLeftBlue.setVisibility(View.GONE);
            buttonColor = R.color.redAlliance;

        } else {
            parkDrawable = R.drawable.in_line_blue;
            processorDrawable = R.drawable.processor_blue;
            driversText.setVisibility(View.VISIBLE);
            autoRightBlue.setVisibility(View.VISIBLE);
            autoCenterBlue.setVisibility(View.VISIBLE);
            autoLeftBlue.setVisibility(View.VISIBLE);
            autoLeftRed.setVisibility(View.GONE);
            autoCenterRed.setVisibility(View.GONE);
            autoRightRed.setVisibility(View.GONE);
            redDriversText.setVisibility(View.GONE);
            buttonColor = R.color.blueAlliance;
        }
    }

}