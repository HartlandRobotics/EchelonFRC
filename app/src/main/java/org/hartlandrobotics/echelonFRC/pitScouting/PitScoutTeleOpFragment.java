package org.hartlandrobotics.echelonFRC.pitScouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFRC.R;
import org.hartlandrobotics.echelonFRC.database.entities.PitScout;

public class PitScoutTeleOpFragment extends Fragment {
    public static final String TAG = "PitScoutTeleOpFragment";

    CheckBox teleOpCanPassCheckbox;
    RadioGroup rolePreferenceGroup;
    RadioGroup defenseExperienceGroup;
    TextInputLayout teleOpFuelPerSecondIngestLayout;
    TextInputLayout teleOpFuelPerSecondShootLayout;
    RadioGroup howShootGroup;
    TextInputLayout teleOpPositionLayout;



    //    RadioGroup pickOffGround;
//    TextInputLayout scoringMethodLayout;
//    RadioGroup defenseGroup;
    PitScout data;

    boolean areControlsSetup = false;

    public void setData( PitScout data) {
        this.data = data;
        populateControlsFromData();
    }

    public PitScoutTeleOpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pitscout_tele_op, container, false);

        setupControls(view);

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.i(TAG, "on Resume");
        //populateControlsFromData();
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i(TAG, "on Pause");
        //populateDataFromControls();
    }

    private void setupControls(View view) {
        if (data == null) {
            return;
        }

        teleOpCanPassCheckbox = view.findViewById(R.id.canPass);

        rolePreferenceGroup = view.findViewById(R.id.rolePrefrence);
        defenseExperienceGroup = view.findViewById(R.id.teleOpDefense);
        teleOpFuelPerSecondIngestLayout = view.findViewById(R.id.fuelPerSecondIngest);
        teleOpFuelPerSecondShootLayout = view.findViewById(R.id.fuelPerSecondShooting);
        howShootGroup = view.findViewById(R.id.shootingProcess);
        teleOpPositionLayout = view.findViewById(R.id.shootingPosition);

//        pickOffGround = view.findViewById(R.id.rolePrefrence);
//        pickOffGround.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                boolean offGroundYes = checkedId == R.id.offGroundYes;
//                data.setOffGroundYes(offGroundYes);
//            }
//        });
//
//        scoringMethodLayout = view.findViewById(R.id.scoringMethod);
//        String scoringMethod = StringUtils.defaultIfBlank(data.getScoringMethod(), StringUtils.EMPTY);
//        scoringMethodLayout.getEditText().setText(scoringMethod);
//
//        defenseGroup = view.findViewById(R.id.robotDefense);
//
//        areControlsSetup = true;
    }

    public void populateDataFromControls() {
        Log.i(TAG, "populate data from controls");

        data.setTeleOpCanPass( teleOpCanPassCheckbox.isChecked() );

        int rolePreferenceId = rolePreferenceGroup.getCheckedRadioButtonId();
        if( rolePreferenceId == R.id.rolePrefrenceScoring ){
            data.setRolePreference(0);
        }else if( rolePreferenceId == R.id.rolePrefrencePassing ){
            data.setRolePreference(1);
        } else {
            data.setRolePreference(2);
        }

        int defenseExperienceId = defenseExperienceGroup.getCheckedRadioButtonId();
        if( defenseExperienceId == R.id.robotDefenseYes ){
            data.setDefenseExperience(0);
        } else {
            data.setDefenseExperience(1);
        }

        int teleOpFuelPerSecondIngest = Integer.parseInt(StringUtils.defaultIfBlank(teleOpFuelPerSecondIngestLayout.getEditText().getText().toString(), "0"));
        data.setFuelPerSecondIngest(teleOpFuelPerSecondIngest);

        int teleOpFuelPerSecondShoot = Integer.parseInt(StringUtils.defaultIfBlank(teleOpFuelPerSecondShootLayout.getEditText().getText().toString(), "0"));
        data.setFuelPerSecondShoot(teleOpFuelPerSecondShoot);

        int howShootId = howShootGroup.getCheckedRadioButtonId();
        if( howShootId == R.id.shootingProcessMoving ){
            data.setHowShoot(0);
        } else {
            data.setHowShoot(1);
        }

        String teleOpPosition = teleOpPositionLayout.getEditText().getText().toString();
        data.setTeleOpPosition(teleOpPosition);


//        boolean offGroundYes = pickOffGround.getCheckedRadioButtonId() == R.id.offGroundYes;
//        data.setOffGroundYes(offGroundYes);
//
//        boolean canPlayDefense = defenseGroup.getCheckedRadioButtonId() == R.id.robotDefenseYes;
//        data.setCanPlayDefense(canPlayDefense);
//
//        String scoringMethod = scoringMethodLayout.getEditText().getText().toString();
//        data.setScoringMethod(scoringMethod);
    }

    private void populateControlsFromData() {
        if (data == null) {
            Log.i(TAG, "no data to bind");
            return;
        }
        if (teleOpCanPassCheckbox == null) return;

        int rolePreference = data.getRolePreference();
        switch (rolePreference){
            case 0:
                rolePreferenceGroup.check(R.id.rolePrefrenceScoring);
                break;
            case 1:
                rolePreferenceGroup.check(R.id.rolePrefrencePassing);
                break;
            case 2:
                rolePreferenceGroup.check(R.id.rolePrefrenceNeither);
                break;
        }

        int defenseExperience = data.getDefenseExperience();
        switch (defenseExperience){
            case 0:
                defenseExperienceGroup.check(R.id.robotDefenseYes);
                break;
            case 1:
                defenseExperienceGroup.check(R.id.robotDefenseNo);
                break;
        }

        teleOpCanPassCheckbox.setChecked(data.getTeleOpCanPass());

        String teleOpFuelPerSecondIngest = StringUtils.defaultIfBlank(String.valueOf(data.getFuelPerSecondIngest()), "0");
        teleOpFuelPerSecondIngestLayout.getEditText().setText(teleOpFuelPerSecondIngest);

        String teleOpFuelPerSecondShoot = StringUtils.defaultIfBlank(String.valueOf(data.getFuelPerSecondShoot()), "0");
        teleOpFuelPerSecondShootLayout.getEditText().setText(teleOpFuelPerSecondShoot);

        int howShoot = data.getHowShoot();
        switch (howShoot){
            case 0:
                howShootGroup.check(R.id.shootingProcessMoving);
                break;
            case 1:
                howShootGroup.check(R.id.shootingProcessStandstill);
                break;
        }

        String teleOpPosition = data.getTeleOpPosition();
        teleOpPositionLayout.getEditText().setText(teleOpPosition);

        // check that controls have been established
//        if(pickOffGround == null) return;
//
//
//        Log.i(TAG, "populate controls from data");
//        boolean offGroundYes = data.getPickOffGround();
//        int offGroundYesSelection = offGroundYes ? R.id.offGroundYes : R.id.offGroundNo;
//        pickOffGround.check(offGroundYesSelection);
//
//
//        boolean canPlayDefense = data.getCanPlayDefense();
//        int canPlayDefenseSelection = canPlayDefense ? R.id.robotDefenseYes : R.id.robotDefenseNo;
//        defenseGroup.check(canPlayDefenseSelection);
    }
}