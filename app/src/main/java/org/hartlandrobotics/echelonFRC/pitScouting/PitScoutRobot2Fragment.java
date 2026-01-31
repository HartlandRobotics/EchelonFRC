package org.hartlandrobotics.echelonFRC.pitScouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.google.android.material.textfield.TextInputLayout;

import org.hartlandrobotics.echelonFRC.R;
import org.hartlandrobotics.echelonFRC.database.entities.PitScout;


public class PitScoutRobot2Fragment extends Fragment {
    private static final String TAG = "PitScoutRobot2Fragment";

    TextInputLayout fuelCapacityLayout;
    CheckBox groundFuelCheckBox;
    CheckBox humanFuelCheckBox;
    TextInputLayout humanAccuracyLayout;
    TextInputLayout additionalNotesLayout;
    PitScout data;

    public PitScoutRobot2Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_pitscout_robot2, container, false);

        setupControls(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        populateControlsFromData();
    }

    @Override
    public void onPause() {
        super.onPause();
        populateDataFromControls();
    }

    private void setupControls(View view) {

        fuelCapacityLayout = view.findViewById(R.id.fuelCapacity);

        groundFuelCheckBox = view.findViewById(R.id.ground_fuel);
        humanFuelCheckBox = view.findViewById(R.id.human_fuel);

        humanAccuracyLayout = view.findViewById(R.id.humanAccuracy);
        additionalNotesLayout = view.findViewById(R.id.additionalNotes);


    }


    public void populateDataFromControls() {
        if (data == null) return;
        if(fuelCapacityLayout==null) return;

        int fuelCapacity = Integer.parseInt(fuelCapacityLayout.getEditText().getText().toString());
        data.setFuelCapacity(fuelCapacity);

        data.setGroundFuel( groundFuelCheckBox.isChecked() );
        data.setHumanFuel( humanFuelCheckBox.isChecked() );

        String humanAccuracy = humanAccuracyLayout.getEditText().getText().toString();
        data.setHumanAccuracy(humanAccuracy);
        String additionalNotes = additionalNotesLayout.getEditText().getText().toString();
        data.setAdditionalNotes(additionalNotes);


    }

    public void populateControlsFromData() {
        if (data == null) return;
        if(fuelCapacityLayout==null) return;

        int fuelCapacity = data.getFuelCapacity();
        fuelCapacityLayout.getEditText().setText(String.valueOf(fuelCapacity));

        groundFuelCheckBox.setChecked(data.getGroundFuel());
        humanFuelCheckBox.setChecked(data.getHumanFuel());

        String humanAccuracy = data.getHumanAccuracy();
        humanAccuracyLayout.getEditText().setText(humanAccuracy);

        String additionalNotes = data.getAdditionalNotes();
        additionalNotesLayout.getEditText().setText(additionalNotes);


    }



}




