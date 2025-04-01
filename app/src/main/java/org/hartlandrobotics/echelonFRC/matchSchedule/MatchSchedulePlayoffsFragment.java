package org.hartlandrobotics.echelonFRC.matchSchedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.hartlandrobotics.echelonFRC.R;


public class MatchSchedulePlayoffsFragment extends Fragment {

    public MatchSchedulePlayoffsFragment() {
        // Required empty public constructor
    }

    public static MatchSchedulePlayoffsFragment newInstance(String param1, String param2) {
        MatchSchedulePlayoffsFragment fragment = new MatchSchedulePlayoffsFragment();
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