package org.hartlandrobotics.echelonFRC.pitScouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.hartlandrobotics.echelonFRC.R;
import org.hartlandrobotics.echelonFRC.database.entities.PitScout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PitScoutRobot2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PitScoutRobot2Fragment extends Fragment {
    private static final String TAG = "PitScoutRobot2Fragment";
    public void populateDataFromControls() {
    }

    public void populateControlsFromData() {
    }

    PitScout data;
    public void setData(PitScout data) {
        this.data = data;
        populateControlsFromData();
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PitScoutRobot2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PitScoutRobot2.
     */
    // TODO: Rename and change types and number of parameters
    public static PitScoutRobot2Fragment newInstance(String param1, String param2) {
        PitScoutRobot2Fragment fragment = new PitScoutRobot2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pitscout_robot2, container, false);
    }
}