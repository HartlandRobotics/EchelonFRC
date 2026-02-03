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