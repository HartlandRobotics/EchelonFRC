package org.hartlandrobotics.echelonFRC.matchScouting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelonFRC.R;
import org.hartlandrobotics.echelonFRC.configuration.AdminSettings;
import org.hartlandrobotics.echelonFRC.configuration.AdminSettingsProvider;
//import org.hartlandrobotics.echelonFRC.database.crescendo.CrescendoResult;
//import org.hartlandrobotics.echelonFRC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFRC.database.currentGame.CurrentGamePoints;
import org.hartlandrobotics.echelonFRC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFRC.models.MatchResultViewModel;
import org.hartlandrobotics.echelonFRC.status.BlueAllianceStatus;

public class MatchScoutingTeleopActivity extends AppCompatActivity {
    private static final String MATCH_KEY = "match_key_param";
    private static final String TEAM_KEY = "team_key_param";

    MaterialButton scoutingDoneButton;
    private ImageButton activeFuelDecrement;
    private ImageButton activeFuel;
    private MaterialTextView activeFuelText;
    private ImageButton missedFuelDecrement;
    private ImageButton missedFuel;
    private MaterialTextView missedFuelText;
    private ImageButton passingDecrement;
    private ImageButton passing;
    private MaterialTextView passingText;
    private ImageButton humanFuelDecrement;
    private ImageButton humanFuel;
    private MaterialTextView humanFuelText;
    private MaterialButton highClimb;
    private MaterialButton midClimb;
    private MaterialButton lowClimb;
    private int buttonColor;
    private int buttonSelectedTextColor;
    private int secondaryDarkColor;

    MatchResultViewModel matchResultViewModel;
    CurrentGamePoints currentResult;
    //CrescendoResult crescendoResult;

    String matchKey;
    String teamKey;

    public static void launch(Context context, String matchKey, String teamKey) {
        Intent intent = new Intent(context, MatchScoutingTeleopActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(MATCH_KEY, matchKey);
        bundle.putString(TEAM_KEY, teamKey);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_teleop_scouting);

        setupColor();
        setupControls();

        Bundle bundle = getIntent().getExtras();
        matchKey = bundle.getString(MATCH_KEY);
        teamKey = bundle.getString(TEAM_KEY);

        BlueAllianceStatus blueAllianceStatus = new BlueAllianceStatus(getApplicationContext());

        matchResultViewModel = new ViewModelProvider(this).get(MatchResultViewModel.class);
        matchResultViewModel.getMatchResultByMatchTeam(matchKey, teamKey)
                .observe(MatchScoutingTeleopActivity.this, mr -> {
                    if (mr == null) {
                        mr = matchResultViewModel.getDefault(blueAllianceStatus.getEventKey(), matchKey, teamKey);
                    }
                    currentResult = MatchResult.toCurrentGamePoints(mr);
                    populateControlsFromData();
                });
    }


    public void populateControlsFromData() {
        activeFuelText.setText(String.valueOf(currentResult.result.getTeleOpInt6()));
        missedFuelText.setText(String.valueOf(currentResult.result.getTeleOpInt7()));
        passingText.setText(String.valueOf(currentResult.result.getTeleOpInt8()));
        humanFuelText.setText(String.valueOf(currentResult.result.getTeleOpInt9()));

        if (currentResult.result.getEndFlag1()){
            highClimb.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(secondaryDarkColor)));
            highClimb.setTextColor(getResources().getColor(buttonSelectedTextColor));
        }
        else{
            highClimb.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            highClimb.setTextColor(getResources().getColor(secondaryDarkColor));
        }

        if (currentResult.result.getEndFlag2()){
            midClimb.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(secondaryDarkColor)));
            midClimb.setTextColor(getResources().getColor(buttonSelectedTextColor));
        }
        else{
            midClimb.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            midClimb.setTextColor(getResources().getColor(secondaryDarkColor));
        }

        if (currentResult.result.getEndFlag3()){
            lowClimb.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(secondaryDarkColor)));
            lowClimb.setTextColor(getResources().getColor(buttonSelectedTextColor));
        }
        else{

            lowClimb.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            lowClimb.setTextColor(getResources().getColor(secondaryDarkColor));
        }

    }

    private void setupControls() {

        activeFuel = findViewById(R.id.active_fuel);
        activeFuelText = findViewById(R.id.active_fuel_text);
        activeFuel.setOnClickListener(v -> {
                    currentResult.result.setTeleOpInt6(currentResult.result.getTeleOpInt6() + 1);
                    populateControlsFromData();
        });
        activeFuelDecrement = findViewById(R.id.active_fuel_decrement);
        activeFuelDecrement.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt6(Math.max(currentResult.result.getTeleOpInt6() -1, 0));
            populateControlsFromData();
        });

        missedFuel = findViewById(R.id.missed_fuel);
        missedFuelText = findViewById(R.id.missed_fuel_text);
        missedFuel.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt7(currentResult.result.getTeleOpInt7()+1);
            populateControlsFromData();
        });
        missedFuelDecrement = findViewById(R.id.missed_fuel_decrement);
        missedFuelDecrement.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt7(Math.max(currentResult.result.getTeleOpInt7() -1, 0));
            populateControlsFromData();
        });

        passing = findViewById(R.id.passing);
        passingText = findViewById(R.id.passing_text);
        passing.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt8(currentResult.result.getTeleOpInt8()+1);
            populateControlsFromData();
        });
        passingDecrement = findViewById(R.id.passing_decrement);
        passingDecrement.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt8(Math.max(currentResult.result.getTeleOpInt8() -1, 0));
            populateControlsFromData();
        });

        humanFuel = findViewById(R.id.human_fuel);
        humanFuelText = findViewById(R.id.human_fuel_text);
        humanFuel.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt9(currentResult.result.getTeleOpInt9()+1);
            populateControlsFromData();
        });
        humanFuelDecrement = findViewById(R.id.human_fuel_decrement);
        humanFuelDecrement.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt9(Math.max(currentResult.result.getTeleOpInt9() -1, 0));
            populateControlsFromData();
        });


        activeFuelText = findViewById(R.id.active_fuel_text);
        missedFuelText = findViewById(R.id.missed_fuel_text);
        passingText = findViewById(R.id.passing_text);
        humanFuelText = findViewById(R.id.human_fuel_text);

        highClimb = findViewById(R.id.high_climb);
        highClimb.setOnClickListener(v-> {
            currentResult.result.setEndFlag1(!currentResult.result.getEndFlag1());
            boolean isSelected = currentResult.result.getEndFlag1();
            if (isSelected){
                currentResult.result.setEndFlag3(false);
                currentResult.result.setEndFlag2(false);
            }
            matchResultViewModel.upsert(currentResult.result);
        });

        midClimb = findViewById(R.id.mid_climb);
        midClimb.setOnClickListener(v-> {
            currentResult.result.setEndFlag2(!currentResult.result.getEndFlag2());
            boolean isSelected = currentResult.result.getEndFlag2();
            if (isSelected){
                currentResult.result.setEndFlag1(false);
                currentResult.result.setEndFlag3(false);
            }
            matchResultViewModel.upsert(currentResult.result);

        });

        lowClimb = findViewById(R.id.low_climb);
        lowClimb.setOnClickListener(v-> {
            currentResult.result.setEndFlag3(!currentResult.result.getEndFlag3());
            boolean isSelected = currentResult.result.getEndFlag3();
            if (isSelected){
                currentResult.result.setEndFlag1(false);
                currentResult.result.setEndFlag2(false);
            }
            matchResultViewModel.upsert(currentResult.result);
        });


        scoutingDoneButton = findViewById(R.id.matchSummarySaveButton);
        scoutingDoneButton.setOnClickListener(v -> {
            matchResultViewModel.upsert(currentResult.result);
            MatchScoutingSummaryActivity.launch(MatchScoutingTeleopActivity.this, matchKey, teamKey);
        });

    }



    public void setupColor() {
        AdminSettings settings = AdminSettingsProvider.getAdminSettings(getApplicationContext());

        buttonSelectedTextColor = R.color.primaryDarkColor;
        secondaryDarkColor = R.color.secondaryDarkColor;


        }
}