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
    private ImageButton ampSpeakerTeleOp;
    private ImageButton subtractAmpSpeakerTeleOp;
    private ImageButton speakerTeleOp;
    private ImageButton subtractSpeakerTeleOp;
    private ImageButton ampTeleOp;
    private ImageButton subtractAmpTeleOp;
    private ImageButton defensesButton;
    private ImageButton defensesSubtractButton;
    private MaterialTextView defensesText;
    private MaterialTextView ampSpeakerTeleOpText;
    private MaterialTextView speakerTeleOpText;
    private MaterialTextView ampTeleOpText;


    private int ampSpeakerTeleOpDrawable;
    private int ampTeleOpDrawable;
    private int buttonColor;
    private int buttonSelectedTextColor;
    private int defenseDrawable;

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
        defensesText.setText(String.valueOf(currentResult.getDefenseCount()));
        ampTeleOpText.setText(String.valueOf(currentResult.getTeleOpInt1Counts()));
        speakerTeleOpText.setText(String.valueOf(currentResult.getTeleOpInt2Counts()));
        ampSpeakerTeleOpText.setText(String.valueOf(currentResult.getTeleOpInt3Counts()));

    }

    private void setupControls() {

        defensesButton = findViewById(R.id.teleOpDefenses);
        defensesButton.setImageResource(defenseDrawable);
        defensesButton.setOnClickListener(v -> {
            currentResult.result.setDefenseCount(currentResult.getDefenseCount()+1);
            populateControlsFromData();
        });
        defensesSubtractButton = findViewById(R.id.subtractDefense);
        defensesSubtractButton.setOnClickListener(v -> {
            currentResult.result.setDefenseCount(Math.max(currentResult.getDefenseCount() -1, 0));
            populateControlsFromData();
        });

        ampSpeakerTeleOp = findViewById(R.id.amplifiedSpeaker);
        ampSpeakerTeleOpText = findViewById(R.id.teleOpAmplifiedSpeakerValue);
        subtractAmpSpeakerTeleOp = findViewById(R.id.subtractAmplifiedSpeakerPointsTeleOp);
        subtractAmpSpeakerTeleOp.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt3(Math.max(currentResult.getTeleOpInt3Counts() -1, 0));
            populateControlsFromData();
        });
        ampSpeakerTeleOp.setImageResource(ampSpeakerTeleOpDrawable);
        ampSpeakerTeleOp.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt3(currentResult.getTeleOpInt3Counts()+1);
            populateControlsFromData();
        });

        speakerTeleOp = findViewById(R.id.teleOpSpeaker);
        speakerTeleOpText = findViewById(R.id.teleOpSpeakerValue);
        subtractSpeakerTeleOp = findViewById(R.id.subtractSpeakerPointsTeleOp);
        subtractSpeakerTeleOp.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt2(Math.max(currentResult.getTeleOpInt2Counts() - 1, 0));
            populateControlsFromData();
        });
        speakerTeleOp.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt2(Math.max(currentResult.getTeleOpInt2Counts() + 1, 0));
            populateControlsFromData();
        });

        ampTeleOp = findViewById(R.id.teleOpAmp);
        ampTeleOpText = findViewById(R.id.teleOpAmpValue);
        subtractAmpTeleOp = findViewById(R.id.subtractAmpPointsTeleOp);
        subtractAmpTeleOp.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt1(Math.max(currentResult.getTeleOpInt1Counts() -1, 0));
            populateControlsFromData();
        });
        ampTeleOp.setImageResource(ampTeleOpDrawable);
        ampTeleOp.setOnClickListener(v -> {
            currentResult.result.setTeleOpInt1(currentResult.getTeleOpInt1Counts()+1);
            populateControlsFromData();
        });

        defensesText = findViewById(R.id.teleOpDefensesValue);

        scoutingDoneButton = findViewById(R.id.endgame);
        scoutingDoneButton.setOnClickListener(v -> {
            matchResultViewModel.upsert(currentResult.result);
            MatchScoutingSummaryActivity.launch(MatchScoutingTeleopActivity.this, matchKey, teamKey);
        });
    }

    public void setupColor() {
        AdminSettings settings = AdminSettingsProvider.getAdminSettings(getApplicationContext());

        buttonSelectedTextColor = R.color.primaryDarkColor;

        if (settings.getDeviceRole().startsWith("red")) {
            ampSpeakerTeleOpDrawable = R.drawable.amplified_speaker_red;
            ampTeleOpDrawable = R.drawable.teleop_red_amp;
            defenseDrawable = R.drawable.defense_red;
            buttonColor = R.color.redAlliance;
        } else {
            ampSpeakerTeleOpDrawable = R.drawable.amplified_speaker_blue;
            ampTeleOpDrawable = R.drawable.auto_blue_amp;
            defenseDrawable = R.drawable.defense_blue;
            buttonColor = R.color.blueAlliance;
        }
    }
}