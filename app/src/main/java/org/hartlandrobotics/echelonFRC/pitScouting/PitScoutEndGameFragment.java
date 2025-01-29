package org.hartlandrobotics.echelonFRC.pitScouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFRC.R;
import org.hartlandrobotics.echelonFRC.database.entities.PitScout;

public class PitScoutEndGameFragment extends Fragment {

    TextInputLayout hangTimeLayout;

    public PitScoutEndGameFragment() {
        // Required empty public constructor
    }

    PitScout data;

    public void setData( PitScout data) {
        this.data = data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_pitscout_end_game, container, false);

        setupControls(view);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        populateControlsFromData();

    }

    @Override
    public void onPause(){
        super.onPause();
        populateDataFromControls();
    }

    private void setupControls(View view){
        hangTimeLayout = view.findViewById(R.id.hangTime);
    }

    public void populateDataFromControls(){
        if( data == null ) return;
        if( hangTimeLayout == null ) return;

        String hangTimeText = StringUtils.defaultIfBlank(hangTimeLayout.getEditText().getText().toString(), "0");
        data.setHangTime(Integer.valueOf(hangTimeText));

        }

    private void populateControlsFromData(){
        if( data == null ){
            return;
        }

        if( hangTimeLayout == null ) return;

        String hangTimeText = String.valueOf(data.getHangTime());
        hangTimeLayout.getEditText().setText(hangTimeText);

    }
}