package org.hartlandrobotics.echelon2.pitScouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.database.entities.PitScout;

public class PitScoutEndGameFragment extends Fragment {
    public PitScoutEndGameFragment() {
        // Required empty public constructor
    }

    PitScout data;

    public void setData( PitScout data) { this.data = data; }
    public PitScout getData() { return data; }

    public static PitScoutEndGameFragment newInstance() {
        PitScoutEndGameFragment fragment = new PitScoutEndGameFragment();
        return fragment;
    }

    TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View endGameView =  inflater.inflate(R.layout.fragment_pitscout_end_game, container, false);
        tv = endGameView.findViewById(R.id.ps_end_text);

        return endGameView;
    }
}