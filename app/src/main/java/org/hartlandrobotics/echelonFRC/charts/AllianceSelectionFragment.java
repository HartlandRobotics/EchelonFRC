package org.hartlandrobotics.echelonFRC.charts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import org.hartlandrobotics.echelonFRC.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllianceSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllianceSelectionFragment extends Fragment {
    private AutoCompleteTextView teamNumberAutoComplete;

    public AllianceSelectionFragment() {
        // Required empty public constructor
    }
    
    public static AllianceSelectionFragment newInstance(String param1, String param2) {
        AllianceSelectionFragment fragment = new AllianceSelectionFragment();

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
        return inflater.inflate(R.layout.fragment_alliance_selection, container, false);
    }
}