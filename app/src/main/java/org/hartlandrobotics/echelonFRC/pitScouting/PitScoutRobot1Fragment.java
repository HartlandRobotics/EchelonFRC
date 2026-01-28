package org.hartlandrobotics.echelonFRC.pitScouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;

import org.hartlandrobotics.echelonFRC.R;
import org.hartlandrobotics.echelonFRC.database.entities.PitScout;


public class PitScoutRobot1Fragment extends Fragment {
    private static final String TAG = "PitScoutRobot1Fragment";

    TextInputLayout driveTrainLayout;
    AutoCompleteTextView driveTrainAutoComplete;
    String defaultDriveTrain;



    PitScout data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pitscout_robot1, container, false);

        setupControls(view);

        return view;
    }
    private void setupControls(View view) {
        if (data == null) {
            return;
        }

        driveTrainLayout = view.findViewById(R.id.driveTrain);
        driveTrainAutoComplete = view.findViewById(R.id.driveTrainAutoComplete);
        String[] driveTrains = getResources().getStringArray(R.array.drive_train);
        defaultDriveTrain = driveTrains[0];
        ArrayAdapter adapterDriveTrain = new ArrayAdapter(getActivity(), R.layout.dropdown_item, driveTrains);
        driveTrainAutoComplete.setAdapter(adapterDriveTrain);



    }


        public void setData(PitScout data) {
        this.data = data;
        populateControlsFromData();
    }

    public void populateDataFromControls() {
        String robot_drivetrain = driveTrainAutoComplete.getText().toString();

        int i;
        i = 10;
    }

    public void populateControlsFromData() {
    }




    public PitScoutRobot1Fragment() {
        // Required empty public constructor
    }


}