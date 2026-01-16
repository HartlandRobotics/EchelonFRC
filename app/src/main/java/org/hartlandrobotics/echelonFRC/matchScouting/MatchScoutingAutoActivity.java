package org.hartlandrobotics.echelonFRC.matchScouting;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

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
    private MaterialButton lowClimb;

    // we are finally going to use text buttons to let them take away things on the screen so they don't keep crying about it


    private int buttonColor;
    private int buttonSelectedTextColor;
    private int secondaryDarkColor;

   private MaterialTextView teamKeyText;




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
        activeFuelText.setText(String.valueOf(currentResult.result.getAutoInt6()));
        missedFuelText.setText(String.valueOf(currentResult.result.getAutoInt7()));
        passingText.setText(String.valueOf(currentResult.result.getAutoInt8()));
        humanFuelText.setText(String.valueOf(currentResult.result.getAutoInt9()));

        if (currentResult.result.getAutoFlag3()) {
            lowClimb.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(secondaryDarkColor)));
            lowClimb.setTextColor(getResources().getColor(buttonSelectedTextColor));
        }
        else {
            lowClimb.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            lowClimb.setTextColor(getResources().getColor(secondaryDarkColor));
        }
    }
    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    public void preSetupControls(){
        lowClimb = findViewById(R.id.low_climb);
        lowClimb.setOnClickListener(v -> {
            currentResult.result.setAutoFlag1( !(currentResult.result.getAutoFlag1()  ));
            populateControlsFromData();
        });
    }
    public void setupControls(){

        activeFuelDecrement = findViewById(R.id.active_fuel_decrement);
        activeFuelDecrement.setOnClickListener(v -> {
            currentResult.result.setAutoInt6( Math.max(currentResult.result.getAutoInt6() - 1, 0));
            populateControlsFromData();
        });

        activeFuel = findViewById(R.id.active_fuel);
        activeFuelText = findViewById(R.id.active_fuel_text);
        activeFuel.setOnClickListener(v -> {
            currentResult.result.setAutoInt6( Math.max(currentResult.result.getAutoInt6() + 1, 0));
            populateControlsFromData();
        });

        missedFuelDecrement = findViewById(R.id.missed_fuel_decrement);
        missedFuelDecrement.setOnClickListener(v -> {
            currentResult.result.setAutoInt7( Math.max(currentResult.result.getAutoInt7() - 1, 0));
            populateControlsFromData();
        });

        missedFuel = findViewById(R.id.missed_fuel);
        missedFuelText = findViewById(R.id.missed_fuel_text);
        missedFuel.setOnClickListener(v -> {
            currentResult.result.setAutoInt7( Math.max(currentResult.result.getAutoInt7() + 1, 0));
            populateControlsFromData();
        });

        passingDecrement = findViewById(R.id.passing_decrement);
        passingDecrement.setOnClickListener(v -> {
            currentResult.result.setAutoInt8( Math.max(currentResult.result.getAutoInt8() - 1, 0));
            populateControlsFromData();
        });

        passing = findViewById(R.id.passing);
        passingText = findViewById(R.id.passing_text);
        passing.setOnClickListener(v -> {
            currentResult.result.setAutoInt8( Math.max(currentResult.result.getAutoInt8() + 1, 0));
            populateControlsFromData();
        });

        humanFuelDecrement = findViewById(R.id.human_fuel_decrement);
        humanFuelDecrement.setOnClickListener(v -> {
            currentResult.result.setAutoInt9( Math.max(currentResult.result.getAutoInt9() - 1, 0));
            populateControlsFromData();
        });

        humanFuel = findViewById(R.id.human_fuel);
        humanFuelText = findViewById(R.id.human_fuel_text);
        humanFuel.setOnClickListener(v -> {
            currentResult.result.setAutoInt9( Math.max(currentResult.result.getAutoInt9() + 1, 0));
            populateControlsFromData();
        });

        lowClimb.setOnClickListener(view -> {
            currentResult.result.setAutoFlag3 (!currentResult.result.getAutoFlag3());
            lowClimb.setChecked(currentResult.result.getAutoFlag3());
            if( currentResult.result.getAutoFlag3()){
                lowClimb.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.secondaryDarkColor)));
                lowClimb.setTextColor(getResources().getColor(R.color.primaryDarkColor));

            }else{
                lowClimb.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
                lowClimb.setTextColor(getResources().getColor(secondaryDarkColor));
            }
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
            buttonColor = R.color.redAlliance;
        } else {
            buttonColor = R.color.blueAlliance;
        }
    }

}