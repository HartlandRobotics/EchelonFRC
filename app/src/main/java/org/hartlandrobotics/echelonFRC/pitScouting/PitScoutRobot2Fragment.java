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


public class PitScoutRobot2Fragment extends Fragment {
    private static final String TAG = "PitScoutRobot2Fragment";

    PitScout data;
    
    TextInputLayout fuelCapacityLayout;

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

        fuelCapacityLayout = view.findViewById(R.id.fuelCapacity);

    }




    public void populateDataFromControls() {
        if( data == null) return;

        String fuelCapacity = fuelCapacityLayout.getEditText().getText().toString();
        data.setFuelCapacity(fuelCapacity);



    }

    public void populateControlsFromData() {
        if( data == null ) return;

        String fuelCapacity = data.getFuelCapacity();
        fuelCapacityLayout.getEditText().setText(fuelCapacity);

        }

    }




