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
import com.google.android.material.radiobutton.MaterialRadioButton;
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

    private MaterialCheckBox leaveLineAuto;

    private ImageButton subtractAutoReefLevelOne;
    private MaterialTextView autoReefLevelOne;
    private ImageButton addAutoReefLevelOne;

    private ImageButton subtractAutoReefLevelTwo;
    private MaterialTextView autoReefLevelTwo;
    private ImageButton addAutoReefLevelTwo;

    private ImageButton subtractAutoReefLevelThree;
    private MaterialTextView autoReefLevelThree;
    private ImageButton addAutoReefLevelThree;

    private ImageButton subtractAutoReefLevelFour;
    private MaterialTextView autoReefLevelFour;
    private ImageButton addAutoReefLevelFour;

    private ImageButton subtractAutoProcessor;
    private MaterialTextView autoProcessor;
    private ImageButton addAutoProcessor;

    private ImageButton subtractAutoNet;
    private MaterialTextView autoNet;
    private ImageButton addAutoNet;

    // TeleOp

    private ImageButton subtractTeleOpReefLevelOne;
    private MaterialTextView teleOpReefLevelOne;
    private ImageButton addTeleOpReefLevelOne;

    private ImageButton subtractTeleOpReefLevelTwo;
    private MaterialTextView teleOpReefLevelTwo;
    private ImageButton addTeleOpReefLevelTwo;

    private ImageButton subtractTeleOpReefLevelThree;
    private MaterialTextView teleOpReefLevelThree;
    private ImageButton addTeleOpReefLevelThree;

    private ImageButton subtractTeleOpReefLevelFour;
    private MaterialTextView teleOpReefLevelFour;
    private ImageButton addTeleOpReefLevelFour;

    private ImageButton subtractTeleOpProcessor;
    private MaterialTextView teleOpProcessor;
    private ImageButton addTeleOpProcessor;

    private ImageButton subtractTeleOpNet;
    private MaterialTextView teleOpNet;
    private ImageButton addTeleOpNet;

    private ImageButton subtractHumanPlayer;
    private MaterialTextView humanPlayer;
    private ImageButton addHumanPlayer;

    // Endgame

   private MaterialRadioButton endHighHang;
    private MaterialRadioButton endLowHang;
    private MaterialRadioButton endPark;
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
        leaveLineAuto = findViewById(R.id.autoParkCheckbox);
        leaveLineAuto.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentResult.result.setAutoFlag1(isChecked);
            populateControlsFromData();
        });

        autoReefLevelOne = findViewById(R.id.autoLevelOneValue);
        subtractAutoReefLevelOne = findViewById(R.id.autoLevelOneDecrement);
        subtractAutoReefLevelOne.setOnClickListener(v -> {
            currentResult.result.setAutoInt6(Math.max(currentResult.result.getAutoInt6() - 1, 0));
                populateControlsFromData();
                });
        addAutoReefLevelOne = findViewById(R.id.autoLevelOneIncrement);
        addAutoReefLevelOne.setOnClickListener(v -> {
            currentResult.result.setAutoInt6( Math.max(currentResult.result.getAutoInt6() + 1, 0));
            populateControlsFromData();
        });

        autoReefLevelTwo = findViewById(R.id.autoLevelTwoValue);
        subtractAutoReefLevelTwo = findViewById(R.id.autoLevelTwoDecrement);
        subtractAutoReefLevelTwo.setOnClickListener(v -> {
            currentResult.result.setAutoInt7(Math.max(currentResult.result.getAutoInt7() - 1, 0));
            populateControlsFromData();
        });
        addAutoReefLevelTwo = findViewById(R.id.autoLevelTwoIncrement);
        addAutoReefLevelTwo.setOnClickListener(v -> {
            currentResult.result.setAutoInt7( Math.max(currentResult.result.getAutoInt7() + 1, 0));
            populateControlsFromData();
        });

        autoReefLevelThree = findViewById(R.id.autoLevelThreeValue);
        subtractAutoReefLevelThree = findViewById(R.id.autoLevelThreeDecrement);
        subtractAutoReefLevelThree.setOnClickListener(v -> {
            currentResult.result.setAutoInt8(Math.max(currentResult.result.getAutoInt8() - 1, 0));
            populateControlsFromData();
        });
        addAutoReefLevelThree = findViewById(R.id.autoLevelThreeIncrement);
        addAutoReefLevelThree.setOnClickListener(v -> {
            currentResult.result.setAutoInt8( Math.max(currentResult.result.getAutoInt8() + 1, 0));
            populateControlsFromData();
        });

        autoReefLevelFour = findViewById(R.id.autoLevelFourValue);
        subtractAutoReefLevelFour = findViewById(R.id.autoLevelFourDecrement);
        subtractAutoReefLevelFour.setOnClickListener(v -> {
            currentResult.result.setAutoInt9(Math.max(currentResult.result.getAutoInt9() - 1, 0));
            populateControlsFromData();
        });
        addAutoReefLevelFour = findViewById(R.id.autoLevelFourIncrement);
        addAutoReefLevelFour.setOnClickListener(v -> {
            currentResult.result.setAutoInt9( Math.max(currentResult.result.getAutoInt9() + 1, 0));
            populateControlsFromData();
        });

        autoProcessor = findViewById(R.id.autoProcessorValue);
        subtractAutoProcessor = findViewById(R.id.autoProcessorDecrement);
        subtractAutoProcessor.setOnClickListener(v -> {
            currentResult.result.setAutoInt10(Math.max(currentResult.result.getAutoInt10() - 1, 0));
            populateControlsFromData();
        });
        addAutoProcessor = findViewById(R.id.autoProcessorIncrement);
        addAutoProcessor.setOnClickListener(v -> {
            currentResult.result.setAutoInt10( Math.max(currentResult.result.getAutoInt10() + 1, 0));
            populateControlsFromData();
        });

        autoNet = findViewById(R.id.autoNetValue);
        subtractAutoNet = findViewById(R.id.autoNetDecrement);
        subtractAutoNet.setOnClickListener(v -> {
            currentResult.result.setAutoInt11(Math.max(currentResult.result.getAutoInt11() - 1, 0));
            populateControlsFromData();
        });
        addAutoNet = findViewById(R.id.autoNetIncrement);
        addAutoProcessor.setOnClickListener(v -> {
            currentResult.result.setAutoInt11( Math.max(currentResult.result.getAutoInt11() + 1, 0));
            populateControlsFromData();
        });

//begin teleop

        teleOpReefLevelOne = findViewById(R.id.teleopLevelOneValue);
        subtractTeleOpReefLevelOne = findViewById(R.id.teleopLevelOneDecrement);
        subtractTeleOpReefLevelOne.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt6(Math.max(currentResult.result.getTeleOpInt6() - 1, 0));
            populateControlsFromData();
        });
        addTeleOpReefLevelOne = findViewById(R.id.teleopLevelOneIncrement);
        addTeleOpReefLevelOne.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt6( Math.max(currentResult.result.getTeleOpInt6() + 1, 0));
            populateControlsFromData();
        });

        teleOpReefLevelTwo = findViewById(R.id.teleopLevelTwoValue);
        subtractTeleOpReefLevelTwo = findViewById(R.id.teleopLevelTwoDecrement);
        subtractTeleOpReefLevelTwo.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt7(Math.max(currentResult.result.getTeleOpInt7() - 1, 0));
            populateControlsFromData();
        });
        addTeleOpReefLevelTwo = findViewById(R.id.teleopLevelTwoIncrement);
        addTeleOpReefLevelTwo.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt7( Math.max(currentResult.result.getTeleOpInt7() + 1, 0));
            populateControlsFromData();
        });

        teleOpReefLevelThree = findViewById(R.id.teleopLevelThreeValue);
        subtractTeleOpReefLevelThree = findViewById(R.id.teleopLevelThreeDecrement);
        subtractTeleOpReefLevelThree.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt8(Math.max(currentResult.result.getTeleOpInt8() - 1, 0));
            populateControlsFromData();
        });
        addTeleOpReefLevelThree = findViewById(R.id.teleopLevelThreeIncrement);
        addTeleOpReefLevelThree.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt8( Math.max(currentResult.result.getTeleOpInt8() + 1, 0));
            populateControlsFromData();
        });

        teleOpReefLevelFour = findViewById(R.id.teleopLevelFourValue);
        subtractTeleOpReefLevelFour = findViewById(R.id.teleopLevelFourDecrement);
        subtractTeleOpReefLevelFour.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt9(Math.max(currentResult.result.getTeleOpInt9() - 1, 0));
            populateControlsFromData();
        });
        addTeleOpReefLevelFour = findViewById(R.id.teleopLevelFourIncrement);
        addTeleOpReefLevelFour.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt9( Math.max(currentResult.result.getTeleOpInt9() + 1, 0));
            populateControlsFromData();
        });

        teleOpProcessor = findViewById(R.id.teleopProcessorValue);
        subtractTeleOpProcessor = findViewById(R.id.teleopProcessorDecrement);
        subtractTeleOpProcessor.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt10(Math.max(currentResult.result.getTeleOpInt10() - 1, 0));
            populateControlsFromData();
        });
        addTeleOpProcessor = findViewById(R.id.teleopProcessorIncrement);
        addTeleOpProcessor.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt10( Math.max(currentResult.result.getTeleOpInt10() + 1, 0));
            populateControlsFromData();
        });

        teleOpNet = findViewById(R.id.teleopNetValue);
        subtractTeleOpNet = findViewById(R.id.teleopNetDecrement);
        subtractTeleOpNet.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt11(Math.max(currentResult.result.getTeleOpInt11() - 1, 0));
            populateControlsFromData();
        });
        addTeleOpNet = findViewById(R.id.teleopNetIncrement);
        addTeleOpProcessor.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt11( Math.max(currentResult.result.getTeleOpInt11() + 1, 0));
            populateControlsFromData();
        });

        humanPlayer = findViewById(R.id.teleopHumanValue);
        subtractHumanPlayer = findViewById(R.id.teleopHumanDecrement);
        subtractHumanPlayer.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt12(Math.max(currentResult.result.getTeleOpInt12() - 1, 0));
            populateControlsFromData();
        });
        addHumanPlayer = findViewById(R.id.teleopHumanIncrement);
        addHumanPlayer.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt12( Math.max(currentResult.result.getTeleOpInt12() + 1, 0));
            populateControlsFromData();
        });

    //endgame begins

        endHighHang = findViewById(R.id.highRadioButton);
        endHighHang.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentResult.result.setEndFlag3(isChecked);
            populateControlsFromData();
        });

        endLowHang = findViewById(R.id.lowRadioButton);
        endHighHang.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentResult.result.setEndFlag2(isChecked);
            populateControlsFromData();
        });

        endPark = findViewById(R.id.parkRadioButton);
        endHighHang.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentResult.result.setEndFlag1(isChecked);
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

        leaveLineAuto.setChecked( currentResult.getAuto1Counts() >0 );
        autoReefLevelOne.setText( String.valueOf( currentResult.result.getAutoInt6() ));
        autoReefLevelTwo.setText( String.valueOf( currentResult.result.getAutoInt7() ));
        autoReefLevelThree.setText( String.valueOf( currentResult.result.getAutoInt8() ));
        autoReefLevelFour.setText( String.valueOf( currentResult.result.getAutoInt9() ));
        autoProcessor.setText( String.valueOf( currentResult.result.getAutoInt10() ));
        autoNet.setText( String.valueOf( currentResult.result.getAutoInt11() ));

        teleOpReefLevelOne.setText( String.valueOf(currentResult.result.getTeleOpInt6()));
        teleOpReefLevelTwo.setText( String.valueOf(currentResult.result.getTeleOpInt7()));
        teleOpReefLevelThree.setText( String.valueOf(currentResult.result.getTeleOpInt8()));
        teleOpReefLevelFour.setText( String.valueOf(currentResult.result.getTeleOpInt9()));
        teleOpProcessor.setText( String.valueOf(currentResult.result.getTeleOpInt10()));
        teleOpNet.setText( String.valueOf(currentResult.result.getTeleOpInt11()));
        humanPlayer.setText( String.valueOf(currentResult.result.getTeleOpInt12()));

        endPark.setChecked(currentResult.result.getEndFlag1());
        endLowHang.setChecked(currentResult.result.getEndFlag2());
        endHighHang.setChecked(currentResult.result.getEndFlag3());

        additionalNotesLayout.getEditText().setText(currentResult.result.getAdditionalNotes());
    }
}