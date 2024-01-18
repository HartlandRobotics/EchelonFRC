package org.hartlandrobotics.echelon2.matchScouting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.database.crescendo.CrescendoResult;
import org.hartlandrobotics.echelon2.database.entities.MatchResult;
import org.hartlandrobotics.echelon2.models.MatchResultViewModel;
import org.hartlandrobotics.echelon2.status.BlueAllianceStatus;

public class MatchScoutingSummaryActivity extends AppCompatActivity {
    private static final String TAG = "MatchScoutingSummaryActivity";

    private static final String MATCH_KEY = "auto_match_key_param";
    private static final String TEAM_KEY = "auto_team_key_param";

    private String matchKey;
    private String teamKey;

    MatchResultViewModel matchResultViewModel;
    MatchResult matchResult;
    CrescendoResult crescendoResult;

    // auto

    private MaterialCheckBox leaveLineAuto;

    private MaterialButton ampNoteAutoDecrement;
    private MaterialTextView ampNoteAutoValue;
    private MaterialButton ampNoteAutoIncrement;

    private MaterialButton speakerNoteAutoDecrement;
    private MaterialTextView speakerNoteAutoValue;
    private MaterialButton speakerNoteAutoIncrement;

    // TeleOp

    private MaterialButton ampNoteTeleOpDecrement;
    private MaterialTextView ampNoteTeleOpValue;
    private MaterialButton ampNoteTeleOpIncrement;

    private MaterialButton neutralSpeakerNoteTeleOpDecrement;
    private MaterialTextView neutralSpeakerNoteTeleOpValue;
    private MaterialButton neutralSpeakerNoteTeleOpIncrement;

    private MaterialButton ampSpeakerNoteTeleOpDecrement;
    private MaterialTextView ampSpeakerNoteTeleOpValue;
    private MaterialButton ampSpeakerNoteTeleOpIncrement;

    // Endgame

    private MaterialCheckBox parkedEnd;
    private MaterialCheckBox onstageEnd;

    private MaterialButton spotlitEndDecrement;
    private MaterialTextView spotlitEndValue;
    private MaterialButton spotlitEndIncrement;

    private MaterialButton teleOpDefensesDecrement;
    private MaterialTextView teleOpDefensesValue;
    private MaterialButton teleOpDefensesIncrement;

    private MaterialCheckBox harmonyEnd;
    private MaterialCheckBox trapNoteEnd;

    private TextInputLayout additionalNotesLayout;

    private MaterialButton submitButton;

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
                    if( mr == null ){
                        matchResult = matchResultViewModel.getDefault(blueAllianceStatus.getEventKey(), matchKey, teamKey);
                    } else {
                        matchResult = mr;
                    }
                    crescendoResult = new CrescendoResult( matchResult);

                    populateControlsFromData();
                });
    }

    private void setupControls(){
        leaveLineAuto = findViewById(R.id.leaveLineAutoCheckBox);
        leaveLineAuto.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setLeaveLineAuto(isChecked);
            populateControlsFromData();
        });

        ampNoteAutoValue = findViewById(R.id.ampNoteAutoValue);
        ampNoteAutoDecrement = findViewById(R.id.ampNoteAutoDecrement);
        ampNoteAutoDecrement.setOnClickListener(v -> {
            crescendoResult.setAmpNoteAuto(crescendoResult.getAmpNoteAuto() - 1);
                populateControlsFromData();
                });
        ampNoteAutoIncrement = findViewById(R.id.ampNoteAutoIncrement);
        ampNoteAutoIncrement.setOnClickListener(v -> {
            crescendoResult.setAmpNoteAuto( crescendoResult.getAmpNoteAuto() + 1);
            populateControlsFromData();
        });

        speakerNoteAutoValue = findViewById(R.id.speakerNoteAutoValue);
        speakerNoteAutoDecrement = findViewById(R.id.speakerNoteAutoDecrement);
        speakerNoteAutoDecrement.setOnClickListener(v -> {
            crescendoResult.setSpeakerNoteAuto(crescendoResult.getSpeakerNoteAuto() - 1);
            populateControlsFromData();
        });
        speakerNoteAutoIncrement = findViewById(R.id.speakerNoteAutoIncrement);
        speakerNoteAutoIncrement.setOnClickListener(v -> {
            crescendoResult.setSpeakerNoteAuto( crescendoResult.getSpeakerNoteAuto() + 1);
            populateControlsFromData();
        });

        ampNoteTeleOpValue = findViewById(R.id.ampNoteTeleOpValue);
        ampNoteTeleOpDecrement = findViewById(R.id.ampNoteTeleOpDecrement);
        ampNoteTeleOpDecrement.setOnClickListener(v -> {
            crescendoResult.setAmpNoteTeleOp(crescendoResult.getAmpNoteTeleOp() - 1);
            populateControlsFromData();
        });
        ampNoteTeleOpIncrement = findViewById(R.id.ampNoteAutoIncrement);
        ampNoteTeleOpIncrement.setOnClickListener(v -> {
            crescendoResult.setAmpNoteTeleOp( crescendoResult.getAmpNoteTeleOp() + 1);
            populateControlsFromData();
        });

        neutralSpeakerNoteTeleOpValue = findViewById(R.id.neutralSpeakerNoteTeleOpValue);
        neutralSpeakerNoteTeleOpDecrement = findViewById(R.id.neutralSpeakerNoteTeleOpDecrement);
        neutralSpeakerNoteTeleOpDecrement.setOnClickListener(v -> {
            crescendoResult.setNeutralSpeakerNoteTeleOp(crescendoResult.getNeutralSpeakerNoteTeleOp() - 1);
            populateControlsFromData();
        });
        neutralSpeakerNoteTeleOpIncrement = findViewById(R.id.neutralSpeakerNoteTeleOpIncrement);
        neutralSpeakerNoteTeleOpIncrement.setOnClickListener(v -> {
            crescendoResult.setNeutralSpeakerNoteTeleOp( crescendoResult.getNeutralSpeakerNoteTeleOp() + 1);
            populateControlsFromData();
        });

        ampSpeakerNoteTeleOpValue = findViewById(R.id.ampSpeakerNoteTeleOpValue);
        ampSpeakerNoteTeleOpDecrement = findViewById(R.id.ampSpeakerNoteTeleOpDecrement);
        ampSpeakerNoteTeleOpDecrement.setOnClickListener(v -> {
            crescendoResult.setAmpSpeakerNoteTeleOp(crescendoResult.getAmpSpeakerNoteTeleOp() - 1);
            populateControlsFromData();
        });

        ampSpeakerNoteTeleOpIncrement = findViewById(R.id.ampSpeakerNoteTeleOpIncrement);
        ampSpeakerNoteTeleOpIncrement.setOnClickListener(v -> {
            crescendoResult.setAmpSpeakerNoteTeleOp( crescendoResult.getAmpSpeakerNoteTeleOp() + 1);
            populateControlsFromData();
        });

        parkedEnd = findViewById(R.id.parkedEndCheckBox);
        parkedEnd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setParkedEnd(isChecked);
            populateControlsFromData();
        });

        onstageEnd = findViewById(R.id.onstageEndCheckBox);
        onstageEnd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setOnstageEnd(isChecked);
            populateControlsFromData();
        });

        spotlitEndValue = findViewById(R.id.spotlitEndValue);
        spotlitEndDecrement = findViewById(R.id.spotlitEndDecrement);
        spotlitEndDecrement.setOnClickListener(v -> {
            crescendoResult.setSpotlitEnd(crescendoResult.getSpotlitEnd() - 1);
            populateControlsFromData();
        });

        spotlitEndIncrement = findViewById(R.id.spotlitEndIncrement);
        spotlitEndIncrement.setOnClickListener(v -> {
            crescendoResult.setSpotlitEnd( crescendoResult.getSpotlitEnd() + 1);
            populateControlsFromData();
        });

        harmonyEnd = findViewById(R.id.harmonyEndCheckBox);
        harmonyEnd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setHarmonyEnd(isChecked);
            populateControlsFromData();
        });

        trapNoteEnd = findViewById(R.id.trapNoteEndCheckBox);
        trapNoteEnd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            matchResult.setTrapNoteEnd(isChecked);
            populateControlsFromData();
        });

        teleOpDefensesValue = findViewById(R.id.teleOpDefensesValue);
        teleOpDefensesDecrement = findViewById(R.id.teleOpDefensesDecrement);
        teleOpDefensesDecrement.setOnClickListener(v -> {
            matchResult.setDefenseCount(Math.max(matchResult.getDefenseCount()-1, 0));
            populateControlsFromData();
        });
        teleOpDefensesIncrement = findViewById(R.id.teleOpDefensesIncrement);
        teleOpDefensesIncrement.setOnClickListener(v -> {
            matchResult.setDefenseCount(matchResult.getDefenseCount()+1);
            populateControlsFromData();
        });

        submitButton = findViewById(R.id.matchSummarySaveButton);
        submitButton.setOnClickListener(v -> {
            matchResultViewModel.upsert(matchResult);
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
                matchResult.setAdditionalNotes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void populateControlsFromData() {
        if( matchResult == null ) return;
        CrescendoResult crescendoResult = new CrescendoResult( matchResult);

        leaveLineAuto.setChecked( crescendoResult.getLeaveLineAuto() );
        ampNoteAutoValue.setText( String.valueOf( crescendoResult.getAmpNoteAuto() ));
        speakerNoteAutoValue.setText( String.valueOf( crescendoResult.getSpeakerNoteAuto() ));


        teleOpHighValue.setText( String.valueOf( matchResult.getTeleOpHighBalls() ));
        teleOpLowValue.setText( String.valueOf( matchResult.getTeleOpLowBalls() ));
        teleOpDefensesValue.setText( String.valueOf( matchResult.getDefenseCount() ));

        endTraverseCheckbox.setChecked( matchResult.getEndHangTraverse() );
        endHighCheckBox.setChecked( matchResult.getEndHangHigh() );
        endMidCheckBox.setChecked( matchResult.getEndHangMid() );
        endLowCheckBox.setChecked( matchResult.getEndHangLow() );

        additionalNotesLayout.getEditText().setText(matchResult.getAdditionalNotes());
    }
}