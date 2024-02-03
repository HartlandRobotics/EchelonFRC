package org.hartlandrobotics.echelon2.matchScouting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.configuration.AdminSettings;
import org.hartlandrobotics.echelon2.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelon2.database.crescendo.CrescendoResult;
import org.hartlandrobotics.echelon2.database.entities.MatchResult;
import org.hartlandrobotics.echelon2.models.MatchResultViewModel;
import org.hartlandrobotics.echelon2.status.BlueAllianceStatus;

public class MatchScoutingEndgameActivity extends AppCompatActivity {
    private static final String MATCH_KEY = "match_key_param";
    private static final String TEAM_KEY = "team_key_param";

    MaterialButton scoutingDoneButton;
    private ImageButton parkButton;
    private ImageButton onstageButton;
    private ImageButton trapButton;
    private ImageButton harmonyButton;
    private ImageButton subtractSpotlightButton;
    private ImageButton spotlightButton;

    private MaterialTextView spotlightValueText;
    private MaterialTextView buttonSelectedTextColor;
    private MaterialTextView buttonColor;

    private int parkButtonDrawable;
    private int onstageButtonDrawable;
    private int trapButtonDrawable;
    private int harmonyButtonDrawable;

    MatchResultViewModel matchResultViewModel;
    CrescendoResult crescendoResult;

    String matchKey;
    String teamKey;

    public static void launch(Context context, String matchKey, String teamKey){
        Intent intent = new Intent(context, MatchScoutingEndgameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(MATCH_KEY, matchKey);
        bundle.putString(TEAM_KEY, teamKey);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_endgame_scouting);

        setupColor();
        setupControls();

        Bundle bundle = getIntent().getExtras();
        matchKey = bundle.getString(MATCH_KEY);
        teamKey = bundle.getString(TEAM_KEY);

        BlueAllianceStatus blueAllianceStatus = new BlueAllianceStatus(getApplicationContext());

        matchResultViewModel = new ViewModelProvider(this).get(MatchResultViewModel.class);
        matchResultViewModel.getMatchResultByMatchTeam(matchKey, teamKey)
                .observe(MatchScoutingEndgameActivity.this, mr->{
                    if( mr == null ){
                        crescendoResult = new CrescendoResult(matchResultViewModel.getDefault(blueAllianceStatus.getEventKey(), matchKey, teamKey));
                    } else {
                        crescendoResult = new CrescendoResult(mr);
                    }

                    populateControlsFromData();
                });
    }


    public void populateControlsFromData(){
        spotlightValueText.setText(String.valueOf(crescendoResult.getEndSpotlight()));

        if( crescendoResult.getEndParked()){
            parkButtonDrawable = R.drawable.center_yes;
        }
        else{
            parkButtonDrawable = R.drawable.center_park;
        }
        parkButton.setImageResource(parkButtonDrawable);

        if( crescendoResult.getEndOnstage()){
            onstageButtonDrawable = R.drawable.stage_yes;
        }
        else{
            setupColor();
        }
        onstageButton.setImageResource(onstageButtonDrawable);

        if(crescendoResult.getEndTrapNote()){
            trapButtonDrawable = R.drawable.trap_yes;
        }
        else {
            trapButtonDrawable = R.drawable.trap;
        }
        trapButton.setImageResource(trapButtonDrawable);

        if(crescendoResult.getEndHarmony()){
            harmonyButtonDrawable = R.drawable.harmony_yes;
        }
        else {
            harmonyButtonDrawable = R.drawable.harmony;
        }
        harmonyButton.setImageResource(harmonyButtonDrawable);

    }

    private void setupControls(){
        scoutingDoneButton = findViewById(R.id.summary);
        scoutingDoneButton.setOnClickListener(v -> {
            matchResultViewModel.upsert(crescendoResult.matchResult);
            MatchScoutingSummaryActivity.launch(MatchScoutingEndgameActivity.this, matchKey, teamKey);
        });



        parkButton = findViewById(R.id.centerPark);
        parkButton.setImageResource(parkButtonDrawable);
        parkButton.setOnClickListener( v -> {
            crescendoResult.setEndParked( !crescendoResult.getEndParked());
            populateControlsFromData();
        });

        onstageButton = findViewById(R.id.centerStage);
        onstageButton.setImageResource(onstageButtonDrawable);
        onstageButton.setOnClickListener(v -> {
                crescendoResult.setEndOnstage( !crescendoResult.getEndOnstage());
                populateControlsFromData();
        });

        trapButton = findViewById(R.id.trapButton);
        trapButton.setImageResource(trapButtonDrawable);
        trapButton.setOnClickListener(v -> {
            crescendoResult.setEndTrapNote( !crescendoResult.getEndTrapNote());
            populateControlsFromData();
        });

        harmonyButton = findViewById(R.id.harmonyButton);
        harmonyButton.setImageResource(harmonyButtonDrawable);
        harmonyButton.setOnClickListener(v -> {
            crescendoResult.setEndHarmony( !crescendoResult.getEndHarmony());
            populateControlsFromData();
        });

        spotlightButton = findViewById(R.id.spotlightButton);
        spotlightButton.setOnClickListener(v -> {
            crescendoResult.setEndSpotlight(crescendoResult.getEndSpotlight()+1);
            populateControlsFromData();
        });
        subtractSpotlightButton = findViewById(R.id.subtractSpotlight);
        subtractSpotlightButton.setOnClickListener(v -> {
            crescendoResult.setEndSpotlight(Math.max(crescendoResult.getEndSpotlight()-1,0));
            populateControlsFromData();
        });

        spotlightValueText = findViewById(R.id.spotlightValue);
    }

    public void setupColor() {
        AdminSettings settings = AdminSettingsProvider.getAdminSettings(getApplicationContext());


        if (settings.getDeviceRole().startsWith("red")){
            onstageButtonDrawable = R.drawable.red_center_stage;
        } else {
            onstageButtonDrawable = R.drawable.blue_center_stage;
        }
    }
}