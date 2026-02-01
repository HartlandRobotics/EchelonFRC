//package org.hartlandrobotics.echelonFRC.pitScouting;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//
//import com.google.android.material.textfield.TextInputLayout;
//
//import org.apache.commons.lang3.StringUtils;
//import org.hartlandrobotics.echelonFRC.R;
//import org.hartlandrobotics.echelonFRC.database.entities.PitScout;
//
//public class  PitScoutTeamFragment extends Fragment {
//    TextInputLayout driverSeasonNumberLayout;
//    TextInputLayout operatorSeasonNumberLayout;
//    TextInputLayout humanPositionPrefLayout;
//    TextInputLayout driveTrainLayout;
//    AutoCompleteTextView driveTrainAutoComplete;
//    String defaultDriveTrain;
//    TextInputLayout additionalNotesLayout;
//
//    PitScout data;
//
//    public void setData( PitScout data) {
//        this.data = data;
//        populateControlsFromData();
//    }
//
//    public PitScoutTeamFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_pitscout_team, container, false);
//
//        setupControls(view);
//
//        return view;
//    }
//
//    @Override
//    public void onStart(){
//        super.onStart();
//        populateControlsFromData();
//    }
//
//    @Override
//    public void onPause(){
//        super.onPause();
//        populateDataFromControls();
//    }
//
//    private void setupControls(View view){
//        driveTrainLayout = view.findViewById(R.id.driveTrain);
//        driveTrainAutoComplete = view.findViewById(R.id.driveTrainAutoComplete);
//        String[] driveTrains = getResources().getStringArray(R.array.drive_train);
//        defaultDriveTrain = driveTrains[0];
//        ArrayAdapter adapterDriveTrain = new ArrayAdapter(getActivity(), R.layout.dropdown_item, driveTrains);
//        driveTrainAutoComplete.setAdapter(adapterDriveTrain);
//
//        driverSeasonNumberLayout = view.findViewById(R.id.driverSeasonNumber);
//        operatorSeasonNumberLayout = view.findViewById(R.id.operatorSeasonNumber);
//        humanPositionPrefLayout = view.findViewById(R.id.humanPositionPref);
//        additionalNotesLayout = view.findViewById(R.id.additionalNotes);
//    }
//
//    public void populateControlsFromData(){
//        if( data == null ) return;
//        if( driverSeasonNumberLayout == null ) return;
//
//        String driveType = StringUtils.defaultIfBlank(data.getRobotDriveTrain(), defaultDriveTrain);
//        driveTrainAutoComplete.setText(driveType,false);
//
//        String driverExperienceText = String.valueOf(data.getDriverExperience());
//        driverSeasonNumberLayout.getEditText().setText(driverExperienceText);
//
//        String operatorExperienceText = String.valueOf(data.getOperatorExperience());
//        operatorSeasonNumberLayout.getEditText().setText(operatorExperienceText);
//
//        String humanPositionPrefText = String.valueOf(data.getHumanPositionPref());
//        humanPositionPrefLayout.getEditText().setText(humanPositionPrefText);
//
//        String additionalNotes = StringUtils.defaultIfBlank(data.getExtraNotes(), StringUtils.EMPTY);
//        additionalNotesLayout.getEditText().setText(additionalNotes);
//    }
//
//    public void populateDataFromControls(){
//        if( data == null ) return;
//        if( driverSeasonNumberLayout == null ) return;
//
//        String driveTrain = StringUtils.defaultIfBlank(driveTrainLayout.getEditText().getText().toString(), StringUtils.EMPTY);
//        data.setRobotDriveTrain(driveTrain);
//
//        int driverExperience= Integer.valueOf( driverSeasonNumberLayout.getEditText().getText().toString());
//        data.setDriverExperience(driverExperience);
//
//        int operatorExperience = Integer.valueOf( operatorSeasonNumberLayout.getEditText().getText().toString());
//        data.setOperatorExperience(operatorExperience);
//
//        double humanPositionPref = Double.valueOf( humanPositionPrefLayout.getEditText().getText().toString());
//        data.setHumanPositionPref(humanPositionPref);
//
//        String additionalNotes = additionalNotesLayout.getEditText().getText().toString();
//        data.setExtraNotes(additionalNotes);
//    }
//}