package org.hartlandrobotics.echelonFRC.matchScouting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelonFRC.R;
//import org.hartlandrobotics.echelonFRC.database.crescendo.CrescendoResult;
import org.hartlandrobotics.echelonFRC.database.currentGame.CurrentGamePoints;
import org.hartlandrobotics.echelonFRC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFRC.models.MatchResultViewModel;
import org.hartlandrobotics.echelonFRC.status.BlueAllianceStatus;

public class MatchScoutingSummaryActivity extends AppCompatActivity {
    private static final String TAG = "MatchScoutingSummaryActivity";

    private static final String MATCH_KEY = "match_key_param";
    private static final String TEAM_KEY = "team_key_param";

    private String matchKey;
    private String teamKey;

    MatchResultViewModel matchResultViewModel;
    //MatchResult matchResult;
    //CrescendoResult crescendoResult;
    CurrentGamePoints currentResult;

    // auto

    private MaterialCheckBox autoLowClimb;

    private ImageButton autoActiveFuelDecrement;
    private MaterialTextView autoActiveFuel;
    private ImageButton autoActiveFuelIncrement;

    private ImageButton autoMissedFuelDecrement;
    private MaterialTextView autoMissedFuel;
    private ImageButton autoMissedFuelIncrement;

    private ImageButton autoPassingDecrement;
    private MaterialTextView autoPassing;
    private ImageButton autoPassingIncrement;

    private ImageButton autoHumanFuelDecrement;
    private MaterialTextView autoHumanFuel;
    private ImageButton autoHumanFuelIncrement;

    // TeleOp

    private ImageButton teleOpActiveFuelDecrement;
    private MaterialTextView teleOpActiveFuel;
    private ImageButton teleOpActiveFuelIncrement;

    private ImageButton teleOpMissedFuelDecrement;
    private MaterialTextView teleOpMissedFuel;
    private ImageButton teleOpMissedFuelIncrement;

    private ImageButton teleOpPassingDecrement;
    private MaterialTextView teleOpPassing;
    private ImageButton teleOpPassingIncrement;

    private ImageButton teleOpHumanFuelDecrement;
    private MaterialTextView teleOpHumanFuel;
    private ImageButton teleOpHumanFuelIncrement;

    // Endgame

   private MaterialCheckBox endHighClimb;
    private MaterialCheckBox endMidClimb;
    private MaterialCheckBox endLowClimb;
    private TextInputLayout additionalNotesLayout;

    private MaterialButton submitButton;

    public MatchScoutingSummaryActivity() {
    }

    public static void launch(Context context, String matchKey, String teamKey){
        Intent intent = new Intent(context, MatchScoutingSummaryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(MATCH_KEY, matchKey);
        bundle.putString(TEAM_KEY, teamKey);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_summary);

        setupControls();

        Bundle bundle = getIntent().getExtras();
        matchKey = bundle.getString(MATCH_KEY);
        teamKey = bundle.getString(TEAM_KEY);
        BlueAllianceStatus blueAllianceStatus = new BlueAllianceStatus(getApplicationContext());

        matchResultViewModel = new ViewModelProvider(this).get(MatchResultViewModel.class);
        matchResultViewModel.getMatchResultByMatchTeam(matchKey, teamKey)
                .observe(MatchScoutingSummaryActivity.this, mr->{
                    if(mr == null){
                        mr = matchResultViewModel.getDefault(blueAllianceStatus.getEventKey(), matchKey, teamKey);
                    }
                    currentResult = MatchResult.toCurrentGamePoints(mr);
                    populateControlsFromData();
                });
    }

    private void setupControls(){
        autoLowClimb = findViewById(R.id.autoLowClimbCheckbox);
        autoLowClimb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentResult.result.setAutoFlag1(isChecked);
            populateControlsFromData();
        });

        autoActiveFuel = findViewById(R.id.autoActiveFuelValue);
        autoActiveFuelDecrement = findViewById(R.id.autoActiveFuelDecrement);
        autoActiveFuelDecrement.setOnClickListener(v -> {
            currentResult.result.setAutoInt6(Math.max(currentResult.result.getAutoInt6() - 1, 0));
                populateControlsFromData();
                });
        autoActiveFuelIncrement = findViewById(R.id.autoActiveFuelIncrement);
        autoActiveFuelIncrement.setOnClickListener(v -> {
            currentResult.result.setAutoInt6( Math.max(currentResult.result.getAutoInt6() + 1, 0));
            populateControlsFromData();
        });

        autoMissedFuel = findViewById(R.id.autoMissedFuelValue);
        autoMissedFuelDecrement = findViewById(R.id.autoMissedFuelDecrement);
        autoMissedFuelDecrement.setOnClickListener(v -> {
            currentResult.result.setAutoInt7(Math.max(currentResult.result.getAutoInt7() - 1, 0));
            populateControlsFromData();
        });
        autoMissedFuelIncrement = findViewById(R.id.autoMissedFuelIncrement);
        autoMissedFuelIncrement.setOnClickListener(v -> {
            currentResult.result.setAutoInt7( Math.max(currentResult.result.getAutoInt7() + 1, 0));
            populateControlsFromData();
        });

        autoPassing = findViewById(R.id.autoPassingValue);
        autoPassingDecrement = findViewById(R.id.autoPassingDecrement);
        autoPassingDecrement.setOnClickListener(v -> {
            currentResult.result.setAutoInt8(Math.max(currentResult.result.getAutoInt8() - 1, 0));
            populateControlsFromData();
        });
        autoPassingIncrement = findViewById(R.id.autoPassingIncrement);
        autoPassingIncrement.setOnClickListener(v -> {
            currentResult.result.setAutoInt8( Math.max(currentResult.result.getAutoInt8() + 1, 0));
            populateControlsFromData();
        });

        autoHumanFuel = findViewById(R.id.autoHumanFuelValue);
        autoHumanFuelDecrement = findViewById(R.id.autoHumanFuelDecrement);
        autoHumanFuelDecrement.setOnClickListener(v -> {
            currentResult.result.setAutoInt9(Math.max(currentResult.result.getAutoInt9() - 1, 0));
            populateControlsFromData();
        });
        autoHumanFuelIncrement = findViewById(R.id.autoHumanFuelIncrement);
        autoHumanFuelIncrement.setOnClickListener(v -> {
            currentResult.result.setAutoInt9( Math.max(currentResult.result.getAutoInt9() + 1, 0));
            populateControlsFromData();
        });


//begin teleop

        teleOpActiveFuel = findViewById(R.id.teleOpActiveFuelValue);
        teleOpActiveFuelDecrement = findViewById(R.id.teleOpActiveFuelDecrement);
        teleOpActiveFuelDecrement.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt6(Math.max(currentResult.result.getTeleOpInt6() - 1, 0));
            populateControlsFromData();
        });
        teleOpActiveFuelIncrement = findViewById(R.id.teleOpActiveFuelIncrement);
        teleOpActiveFuelIncrement.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt6( Math.max(currentResult.result.getTeleOpInt6() + 1, 0));
            populateControlsFromData();
        });

        teleOpMissedFuel = findViewById(R.id.teleOpMissedFuelValue);
        teleOpMissedFuelDecrement = findViewById(R.id.teleOpMissedFuelDecrement);
        teleOpMissedFuelDecrement.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt7(Math.max(currentResult.result.getTeleOpInt7() - 1, 0));
            populateControlsFromData();
        });
        teleOpMissedFuelIncrement = findViewById(R.id.teleOpMissedFuelIncrement);
        teleOpMissedFuelIncrement.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt7( Math.max(currentResult.result.getTeleOpInt7() + 1, 0));
            populateControlsFromData();
        });

        teleOpPassing = findViewById(R.id.teleOpPassingValue);
        teleOpPassingDecrement = findViewById(R.id.teleOpPassingDecrement);
        teleOpPassingDecrement.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt8(Math.max(currentResult.result.getTeleOpInt8() - 1, 0));
            populateControlsFromData();
        });
        teleOpPassingIncrement = findViewById(R.id.teleOpPassingIncrement);
        teleOpPassingIncrement.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt8( Math.max(currentResult.result.getTeleOpInt8() + 1, 0));
            populateControlsFromData();
        });

        teleOpHumanFuel = findViewById(R.id.teleOpHumanFuelValue);
        teleOpHumanFuelDecrement = findViewById(R.id.teleOpHumanFuelDecrement);
        teleOpHumanFuelDecrement.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt9(Math.max(currentResult.result.getTeleOpInt9() - 1, 0));
            populateControlsFromData();
        });
        teleOpHumanFuelIncrement = findViewById(R.id.teleOpHumanFuelIncrement);
        teleOpHumanFuelIncrement.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt9( Math.max(currentResult.result.getTeleOpInt9() + 1, 0));
            populateControlsFromData();
        });


    //endgame begins

        endHighClimb = findViewById(R.id.highClimbCheckBox);
        endHighClimb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentResult.result.setEndFlag2(isChecked);
            if(isChecked){
                currentResult.result.setEndFlag1(false);
                currentResult.result.setEndFlag3(false);
            }

            populateControlsFromData();
        });

        endMidClimb = findViewById(R.id.midClimbCheckBox);
        endMidClimb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentResult.result.setEndFlag3(isChecked);
            if(isChecked){
                currentResult.result.setEndFlag1(false);
                currentResult.result.setEndFlag2(false);
            }

            populateControlsFromData();
        });

        endLowClimb = findViewById(R.id.lowClimbCheckBox);
        endLowClimb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentResult.result.setEndFlag1(isChecked);
            if(isChecked){
                currentResult.result.setEndFlag2(false);
                currentResult.result.setEndFlag3(false);
            }

            populateControlsFromData();
        });

        submitButton = findViewById(R.id.matchSummarySaveButton);
        submitButton.setOnClickListener(v -> {
            matchResultViewModel.upsert(currentResult.result);
            Log.i(TAG, "current match key is " + matchKey);
            // 2020mimil_qm1
            String[] tokens = matchKey.split("_qm");
            String matchNumberStr = tokens[1];
            Integer nextMatchNumber = Integer.valueOf(matchNumberStr) + 1;
            MatchSelectionActivity.launch(MatchScoutingSummaryActivity.this, nextMatchNumber);
        });

        additionalNotesLayout = findViewById(R.id.additionalNotes);
        additionalNotesLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentResult.result.setAdditionalNotes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void populateControlsFromData() {
        if( currentResult == null ) return;

        autoLowClimb.setChecked( currentResult.getAuto1Counts() >0 );
        autoActiveFuel.setText( String.valueOf( currentResult.result.getAutoInt6() ));
        autoMissedFuel.setText( String.valueOf( currentResult.result.getAutoInt7() ));
        autoPassing.setText( String.valueOf( currentResult.result.getAutoInt8() ));
        autoHumanFuel.setText( String.valueOf( currentResult.result.getAutoInt9() ));

        teleOpActiveFuel.setText( String.valueOf(currentResult.result.getTeleOpInt6()));
        teleOpMissedFuel.setText( String.valueOf(currentResult.result.getTeleOpInt7()));
        teleOpPassing.setText( String.valueOf(currentResult.result.getTeleOpInt8()));
        teleOpHumanFuel.setText( String.valueOf(currentResult.result.getTeleOpInt9()));

        endLowClimb.setChecked(currentResult.result.getEndFlag1());
        endMidClimb.setChecked(currentResult.result.getEndFlag3());
        endHighClimb.setChecked(currentResult.result.getEndFlag2());

        additionalNotesLayout.getEditText().setText(currentResult.result.getAdditionalNotes());
    }
}