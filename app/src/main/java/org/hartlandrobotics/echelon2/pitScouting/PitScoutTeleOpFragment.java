package org.hartlandrobotics.echelon2.pitScouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.database.entities.PitScout;


public class PitScoutTeleOpFragment extends Fragment {

    PitScout data;

    public void setData( PitScout data) { this.data = data; }
    public PitScout getData() { return data; }

    public PitScoutTeleOpFragment() {
        // Required empty public constructor
    }


    public static PitScoutTeleOpFragment newInstance() {
        PitScoutTeleOpFragment fragment = new PitScoutTeleOpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pitscout_tele_op, container, false);
    }
}