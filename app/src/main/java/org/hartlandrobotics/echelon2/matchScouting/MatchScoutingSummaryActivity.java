package org.hartlandrobotics.echelon2.matchScouting;

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

import org.hartlandrobotics.echelon2.R;
//import org.hartlandrobotics.echelon2.database.crescendo.CrescendoResult;
import org.hartlandrobotics.echelon2.database.currentGame.CurrentGamePoints;
import org.hartlandrobotics.echelon2.database.entities.MatchResult;
import org.hartlandrobotics.echelon2.models.MatchResultViewModel;
import org.hartlandrobotics.echelon2.status.BlueAllianceStatus;

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

    private MaterialCheckBox leaveLineAuto;

    private ImageButton ampNoteAutoDecrement;
    private MaterialTextView ampNoteAutoValue;
    private ImageButton ampNoteAutoIncrement;

    private ImageButton speakerNoteAutoDecrement;
    private MaterialTextView speakerNoteAutoValue;
    private ImageButton speakerNoteAutoIncrement;

    // TeleOp

    private ImageButton ampNoteTeleOpDecrement;
    private MaterialTextView ampNoteTeleOpValue;
    private ImageButton ampNoteTeleOpIncrement;

    private ImageButton neutralSpeakerNoteTeleOpDecrement;
    private MaterialTextView neutralSpeakerNoteTeleOpValue;
    private ImageButton neutralSpeakerNoteTeleOpIncrement;

    private ImageButton ampSpeakerNoteTeleOpDecrement;
    private MaterialTextView ampSpeakerNoteTeleOpValue;
    private ImageButton ampSpeakerNoteTeleOpIncrement;

    // Endgame

    private MaterialCheckBox endPark;
    private MaterialCheckBox endOnstage;

    private ImageButton endSpotlightDecrement;
    private MaterialTextView endSpotlightValue;
    private ImageButton endSpotlightIncrement;

    private ImageButton teleOpDefensesDecrement;
    private MaterialTextView teleOpDefensesValue;
    private ImageButton teleOpDefensesIncrement;

    private MaterialCheckBox endHarmony;
    private MaterialCheckBox endTrapNote;

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
                    currentResult = new CurrentGamePoints(matchResultViewModel.getDefault(blueAllianceStatus.getEventKey(), matchKey, teamKey));
                    populateControlsFromData();
                });
    }

    private void setupControls(){
        leaveLineAuto = findViewById(R.id.autoParkCheckbox);
        leaveLineAuto.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentResult.setAuto1(isChecked);
            populateControlsFromData();
        });

        ampNoteAutoValue = findViewById(R.id.autoAmpValue);
        ampNoteAutoDecrement = findViewById(R.id.autoAmpDecrement);
        ampNoteAutoDecrement.setOnClickListener(v -> {
            currentResult.setAuto2(!currentResult.getAuto2());
                populateControlsFromData();
                });
        ampNoteAutoIncrement = findViewById(R.id.autoAmpIncrement);
        ampNoteAutoIncrement.setOnClickListener(v -> {
            currentResult.setAuto2( !currentResult.getAuto2());
            populateControlsFromData();
        });

        speakerNoteAutoValue = findViewById(R.id.autoSpeakerValue);
        speakerNoteAutoDecrement = findViewById(R.id.autoSpeakerDecrement);
        speakerNoteAutoDecrement.setOnClickListener(v -> {
            currentResult.setAuto3(!currentResult.getAuto3());
            populateControlsFromData();
        });
        speakerNoteAutoIncrement = findViewById(R.id.autoSpeakerIncrement);
        speakerNoteAutoIncrement.setOnClickListener(v -> {
            currentResult.setAuto3( !currentResult.getAuto3());
            populateControlsFromData();
        });

        ampNoteTeleOpValue = findViewById(R.id.teleOpAmpValue);
        ampNoteTeleOpDecrement = findViewById(R.id.teleopAmpDecrement);
        ampNoteTeleOpDecrement.setOnClickListener(v -> {
            currentResult.setTeleOp1(!currentResult.getTeleOp1());
            populateControlsFromData();
        });
        ampNoteTeleOpIncrement = findViewById(R.id.teleOpAmpIncrement);
        ampNoteTeleOpIncrement.setOnClickListener(v -> {
            currentResult.setTeleOp1( !currentResult.getTeleOp1());
            populateControlsFromData();
        });

        neutralSpeakerNoteTeleOpValue = findViewById(R.id.teleOpSpeakerValue);
        neutralSpeakerNoteTeleOpDecrement = findViewById(R.id.teleOpSpeakerDecrement);
        neutralSpeakerNoteTeleOpDecrement.setOnClickListener(v -> {
            currentResult.setTeleOp2(!currentResult.getTeleOp2());
            populateControlsFromData();
        });
        neutralSpeakerNoteTeleOpIncrement = findViewById(R.id.teleOpSpeakerIncrement);
        neutralSpeakerNoteTeleOpIncrement.setOnClickListener(v -> {
            currentResult.setTeleOp2( !currentResult.getTeleOp2());
            populateControlsFromData();
        });

        ampSpeakerNoteTeleOpValue = findViewById(R.id.teleOpAmplifiedSpeakerValue);
        ampSpeakerNoteTeleOpDecrement = findViewById(R.id.teleOpAmplifiedSpeakerDecrement);
        ampSpeakerNoteTeleOpDecrement.setOnClickListener(v -> {
            currentResult.setTeleOp3(!currentResult.getTeleOp3());
            populateControlsFromData();
        });

        ampSpeakerNoteTeleOpIncrement = findViewById(R.id.teleOpAmplifiedSpeakerIncrement);
        ampSpeakerNoteTeleOpIncrement.setOnClickListener(v -> {
            currentResult.setTeleOp3( !currentResult.getTeleOp3());
            populateControlsFromData();
        });

        endPark = findViewById(R.id.endParkCheckbox);
        endPark.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentResult.setEnd1(isChecked);
            populateControlsFromData();
        });

        endOnstage = findViewById(R.id.endOnstageCheckbox);
        endOnstage.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentResult.setEnd2(isChecked);
            populateControlsFromData();
        });

        endSpotlightValue = findViewById(R.id.endSpotlightValue);
        endSpotlightDecrement = findViewById(R.id.endSpotlightDecrement);
        endSpotlightDecrement.setOnClickListener(v -> {
            currentResult.setEnd3(!currentResult.getEnd3());
            populateControlsFromData();
        });

        endSpotlightIncrement = findViewById(R.id.endSpotlightIncrement);
        endSpotlightIncrement.setOnClickListener(v -> {
            currentResult.setEnd3( !currentResult.getEnd3());
            populateControlsFromData();
        });

        endHarmony = findViewById(R.id.harmonyCheckbox);
        endHarmony.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentResult.setEnd4(isChecked);
            populateControlsFromData();
        });

        endTrapNote = findViewById(R.id.trapCheckbox);
        endTrapNote.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentResult.setEnd5(isChecked);
            populateControlsFromData();
        });

        teleOpDefensesValue = findViewById(R.id.teleOpDefensesValue);
        teleOpDefensesDecrement = findViewById(R.id.teleOpDefensesDecrement);
        teleOpDefensesDecrement.setOnClickListener(v -> {
            currentResult.setDefenseCount(!currentResult.getDefenseCount());
            populateControlsFromData();
        });
        teleOpDefensesIncrement = findViewById(R.id.teleOpDefensesIncrement);
        teleOpDefensesIncrement.setOnClickListener(v -> {
            currentResult.setDefenseCount(!currentResult.getDefenseCount());
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

        leaveLineAuto.setChecked( currentResult.getAuto1() );
        ampNoteAutoValue.setText( String.valueOf( currentResult.getAuto2() ));
        speakerNoteAutoValue.setText( String.valueOf( currentResult.getAuto3() ));

        ampNoteTeleOpValue.setText( String.valueOf(currentResult.getTeleOp1()));
        neutralSpeakerNoteTeleOpValue.setText( String.valueOf(currentResult.getTeleOp2()));
        ampSpeakerNoteTeleOpValue.setText( String.valueOf( currentResult.getTeleOp3() ));


        teleOpDefensesValue.setText( String.valueOf( currentResult.getDefenseCount() ));

        endPark.setChecked(currentResult.getEnd1());
        endOnstage.setChecked(currentResult.getEnd2());
        endSpotlightValue.setText( String.valueOf(currentResult.getEnd3()));
        endHarmony.setChecked(currentResult.getEnd4());
       endTrapNote.setChecked(currentResult.getEnd5());


        additionalNotesLayout.getEditText().setText(currentResult.result.getAdditionalNotes());
    }
}