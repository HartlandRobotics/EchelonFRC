package org.hartlandrobotics.echelonFRC.matchSchedule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.hartlandrobotics.echelonFRC.R;


public class MatchScheduleQualsFragment extends Fragment {

    public MatchScheduleQualsFragment() {
        // Required empty public constructor
    }

    public static MatchScheduleQualsFragment newInstance(String param1, String param2) {
        MatchScheduleQualsFragment fragment = new MatchScheduleQualsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        return inflater.inflate(R.layout.fragment_match_schedule_quals, container, false);
    }
}