package org.hartlandrobotics.echelonFRC.pitScouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFRC.R;
import org.hartlandrobotics.echelonFRC.database.entities.PitScout;


public class PitScoutRobot1Fragment extends Fragment {
    private static final String TAG = "PitScoutRobot1Fragment";

    TextInputLayout driveTrainLayout;
    AutoCompleteTextView driveTrainAutoComplete;

    CheckBox traversesHumpCheckBox;
    CheckBox underTrenchCheckBox;
    TextInputLayout shooterTypeLayout;
    TextInputLayout robotSizeLayout;
    TextInputLayout intakeSizeLayout;

    RadioGroup intakeGroup;


    String defaultDriveTrain;


    PitScout data;

    public PitScoutRobot1Fragment() {
        // Required empty public constructor
    }

    public void setData(PitScout data) {
        this.data = data;
        populateControlsFromData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pitscout_robot1, container, false);

        setupControls(view);

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        populateControlsFromData();
    }

    @Override
    public void onPause() {
        super.onPause();
        populateDataFromControls();
    }

    private void setupControls(View view) {


        driveTrainLayout = view.findViewById(R.id.driveTrain);
        driveTrainAutoComplete = view.findViewById(R.id.driveTrainAutoComplete);
        String[] driveTrains = getResources().getStringArray(R.array.drive_train);
        defaultDriveTrain = driveTrains[0];
        ArrayAdapter adapterDriveTrain = new ArrayAdapter(getActivity(), R.layout.dropdown_item, driveTrains);
        driveTrainAutoComplete.setAdapter(adapterDriveTrain);

        traversesHumpCheckBox = view.findViewById(R.id.traversesHump);
        underTrenchCheckBox = view.findViewById(R.id.underTrench);

        shooterTypeLayout = view.findViewById(R.id.shooterType);
        robotSizeLayout = view.findViewById(R.id.sizeOfRobot);
        intakeSizeLayout = view.findViewById(R.id.sizeOfIntake);

        intakeGroup = view.findViewById(R.id.intakeGroup);
    }




    public void populateDataFromControls() {

        if( data == null) return;

        String driveTrain = StringUtils.defaultIfBlank(driveTrainLayout.getEditText().getText().toString(), StringUtils.EMPTY);
        data.setRobotDriveTrain(driveTrain);

        data.setTraversesHump( traversesHumpCheckBox.isChecked() );
        data.setUnderTrench( underTrenchCheckBox.isChecked() );

        String shooterType = shooterTypeLayout.getEditText().getText().toString();
        data.setShooterType(shooterType);
        String robotSize = robotSizeLayout.getEditText().getText().toString();
        data.setRobotSize(robotSize);
        String intakeSize = intakeSizeLayout.getEditText().getText().toString();
        data.setIntakeSize(intakeSize);

        int intakeSelectedId = intakeGroup.getCheckedRadioButtonId();
        if( intakeSelectedId == R.id.overBumperIntake ){
            data.setIntakeType(0);
        }else{
            data.setIntakeType(1);
        }


    }

    public void populateControlsFromData() {
        if( data == null ) return;


        String driveType = StringUtils.defaultIfBlank(data.getRobotDriveTrain(), defaultDriveTrain);
        driveTrainAutoComplete.setText(driveType,false);

        traversesHumpCheckBox.setChecked(data.getTraversesHump());
        underTrenchCheckBox.setChecked(data.getUnderTrench());

        String shooterType = data.getShooterType();
        shooterTypeLayout.getEditText().setText(shooterType);

        String robotSize = data.getRobotSize();
        robotSizeLayout.getEditText().setText(robotSize);

        String intakeSize = data.getIntakeSize();
        intakeSizeLayout.getEditText().setText(intakeSize);

        int intakeType = data.getIntakeType();
        switch (intakeType){
            case 0:
                intakeGroup.check(R.id.overBumperIntake);
                break;
            case 1:
                intakeGroup.check(R.id.passthroughBumperIntake);
                break;
        }
        
    }




}