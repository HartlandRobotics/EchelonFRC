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

import org.hartlandrobotics.echelon2.R;
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
    private ImageButton teleOpReefLevelOne;
    private ImageButton teleOpReefLevelTwo;
    private ImageButton teleOpReefLevelThree;
    private ImageButton teleOpReefLevelFour;
    private ImageButton teleOpProcessor;
    private ImageButton teleOpNet;
    private ImageButton teleOpHumanPlayer;
    private ImageButton subtractReefLevelOne;
    private ImageButton subtractReefLevelTwo;
    private ImageButton subtractReefLevelThree;
    private ImageButton subtractReefLevelFour;
    private ImageButton subtractProcessor;
    private ImageButton subtractNet;
    private ImageButton subtractHumanPlayer;
    private MaterialTextView reefLevelOnePoints;
    private MaterialTextView reefLevelTwoPoints;
    private MaterialTextView reefLevelThreePoints;
    private MaterialTextView reefLevelFourPoints;
    private MaterialTextView processorPoints;
    private MaterialTextView netPoints;
    private MaterialTextView humanPlayerPoints;
    private MaterialTextView endHighHang;
    private MaterialTextView endLowHang;
    private MaterialTextView endPark;


    private int processorDrawable;
    private int buttonColor;
    private int buttonSelectedTextColor;

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
        reefLevelOnePoints.setText(String.valueOf(currentResult.result.getTeleOpInt6()));
        reefLevelTwoPoints.setText(String.valueOf(currentResult.result.getTeleOpInt7()));
        reefLevelThreePoints.setText(String.valueOf(currentResult.result.getTeleOpInt8()));
        reefLevelFourPoints.setText(String.valueOf(currentResult.result.getTeleOpInt9()));
        processorPoints.setText(String.valueOf(currentResult.result.getTeleOpInt10()));
        netPoints.setText(String.valueOf(currentResult.result.getTeleOpInt11()));
        humanPlayerPoints.setText(String.valueOf(currentResult.result.getTeleOpInt12()));

        if( currentResult.result.getEndFlag3() ){
            endHighHang.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.secondaryDarkColor)));
            endHighHang.setTextColor(getResources().getColor(buttonSelectedTextColor));
        } else {
            endHighHang.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            endHighHang.setTextColor(getResources().getColor(R.color.secondaryDarkColor));
        }

        if( currentResult.result.getEndFlag2() ){
            endLowHang.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.secondaryDarkColor)));
            endLowHang.setTextColor(getResources().getColor(buttonSelectedTextColor));
        } else {
            endLowHang.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            endLowHang.setTextColor(getResources().getColor(R.color.secondaryDarkColor));
        }

        if( currentResult.result.getEndFlag3() ){
            endPark.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.secondaryDarkColor)));
            endPark.setTextColor(getResources().getColor(buttonSelectedTextColor));
        } else {
            endPark.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(buttonColor)));
            endPark.setTextColor(getResources().getColor(R.color.secondaryDarkColor));
        }


    }

    private void setupControls() {

        teleOpReefLevelOne = findViewById(R.id.reef_level_one);
        teleOpReefLevelOne.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt6(currentResult.result.getTeleOpInt6()+1);
            populateControlsFromData();
        });
        subtractReefLevelOne = findViewById(R.id.reef_level_one_decrement);
        subtractReefLevelOne.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt6(Math.max(currentResult.result.getTeleOpInt6() -1, 0));
            populateControlsFromData();
        });

        teleOpReefLevelTwo = findViewById(R.id.reef_level_two);
        teleOpReefLevelTwo.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt7(currentResult.result.getTeleOpInt7()+1);
            populateControlsFromData();
        });
        subtractReefLevelTwo = findViewById(R.id.reef_level_two_decrement);
        subtractReefLevelTwo.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt7(Math.max(currentResult.result.getTeleOpInt7() -1, 0));
            populateControlsFromData();
        });

        teleOpReefLevelThree = findViewById(R.id.reef_level_three);
        teleOpReefLevelThree.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt8(currentResult.result.getTeleOpInt8()+1);
            populateControlsFromData();
        });
        subtractReefLevelThree = findViewById(R.id.reef_level_three_decrement);
        subtractReefLevelThree.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt8(Math.max(currentResult.result.getTeleOpInt8() -1, 0));
            populateControlsFromData();
        });

        teleOpReefLevelFour = findViewById(R.id.reef_level_four);
        teleOpReefLevelFour.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt9(currentResult.result.getTeleOpInt9()+1);
            populateControlsFromData();
        });
        subtractReefLevelFour = findViewById(R.id.reef_level_four_decrement);
        subtractReefLevelFour.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt9(Math.max(currentResult.result.getTeleOpInt9() -1, 0));
            populateControlsFromData();
        });

        teleOpNet = findViewById(R.id.net);
        teleOpNet.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt11(currentResult.result.getTeleOpInt11()+1);
            populateControlsFromData();
        });
        subtractNet = findViewById(R.id.net_decrement);
        subtractNet.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt11(Math.max(currentResult.result.getTeleOpInt11() -1, 0));
            populateControlsFromData();
        });

        teleOpHumanPlayer = findViewById(R.id.human_player);
        teleOpHumanPlayer.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt12(currentResult.result.getTeleOpInt12()+1);
            populateControlsFromData();
        });
        subtractHumanPlayer = findViewById(R.id.human_player_decrement);
        subtractHumanPlayer.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt12(Math.max(currentResult.result.getTeleOpInt12() -1, 0));
            populateControlsFromData();
        });

        reefLevelOnePoints = findViewById(R.id.reef_level_one_text);
        reefLevelTwoPoints = findViewById(R.id.reef_level_two_text);
        reefLevelThreePoints = findViewById(R.id.reef_level_three_text);
        reefLevelFourPoints = findViewById(R.id.reef_level_four_text);
        processorPoints = findViewById(R.id.processor_text);
        netPoints = findViewById(R.id.net_text);
        humanPlayerPoints =findViewById(R.id.human_player_text);

        scoutingDoneButton = findViewById(R.id.summary);
        scoutingDoneButton.setOnClickListener(v -> {
            matchResultViewModel.upsert(currentResult.result);
            MatchScoutingEndgameActivity.launch(MatchScoutingTeleopActivity.this, matchKey, teamKey);
        });
    }

    public void setupColor() {
        AdminSettings settings = AdminSettingsProvider.getAdminSettings(getApplicationContext());

        buttonSelectedTextColor = R.color.primaryDarkColor;

        if (settings.getDeviceRole().startsWith("red")) {
            processorDrawable = R.drawable.processor_red;
            buttonColor = R.color.redAlliance;
        } else {
            processorDrawable = R.drawable.processor_blue;
            buttonColor = R.color.blueAlliance;
        }
    }
}