package org.hartlandrobotics.echelonFRC.pitScouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFRC.R;
import org.hartlandrobotics.echelonFRC.database.entities.PitScout;


public class PitScoutAutoFragment extends Fragment {
    private static final String TAG = "PitScoutAutoFragment";

    RadioGroup hasAutoGroup;
    RadioGroup helpAutoGroup;
    TextInputLayout programmingLanguageLayout;
    AutoCompleteTextView programmingLanguageAutoComplete;
    String defaultProgrammingLanguage;
    LinearLayout missingAutoLayout;
    LinearLayout hasAutoLayout;
    TextInputLayout autoFuelScoredLayout;



    PitScout data;

    public PitScoutAutoFragment() {
        // Required empty public constructor
    }

    public void setData(PitScout data) {
        this.data = data;
        populateControlsFromData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pitscout_auto, container, false);

        setupControls(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        setupControls(requireView());

        populateControlsFromData();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "OnPause running");
        super.onPause();
        populateDataFromControls();
    }

    private void setupControls(View view) {
        if (data == null) {
            return;
        }
        hasAutoLayout = view.findViewById(R.id.hasAutoLayout);

        hasAutoGroup = view.findViewById(R.id.hasAutoGroup);
        hasAutoGroup.setOnCheckedChangeListener((group, checkedId) -> {
            setVisibility();
        });

        autoFuelScoredLayout = view.findViewById(R.id.autoFuelScored);

        missingAutoLayout = view.findViewById(R.id.missingAutoLayout);

        helpAutoGroup = view.findViewById(R.id.helpAutoGroup);
        helpAutoGroup.setOnCheckedChangeListener((group, checkedId) -> {
            setVisibility();
        });


        programmingLanguageLayout = view.findViewById(R.id.autoLanguage);
        programmingLanguageAutoComplete = view.findViewById(R.id.autoLanguageAutoComplete);
        String[] languages = getResources().getStringArray(R.array.programming_languages);
        defaultProgrammingLanguage = languages[0];
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireActivity(), R.layout.dropdown_item, languages);
        programmingLanguageAutoComplete.setAdapter(adapter);

    }


    public void populateDataFromControls() {

        if( data == null ) return;
        if( hasAutoGroup == null ) return;

        boolean hasAuto = hasAutoGroup.getCheckedRadioButtonId() == R.id.hasAutoYes;
        data.setHasAutonomous(hasAuto);

        boolean wantsHelp = hasAutoGroup.getCheckedRadioButtonId() == R.id.helpAutoYes;
        data.setHelpCreatingAuto(wantsHelp);

        String codingLanguage = StringUtils.defaultIfBlank(programmingLanguageAutoComplete.getEditableText().toString(), StringUtils.EMPTY);
        data.setCodingLanguage(codingLanguage);

        int autoFuelScored = Integer.parseInt(StringUtils.defaultIfBlank(autoFuelScoredLayout.getEditText().getText().toString(), "0"));
        data.setAutoFuelScored(autoFuelScored);



    }

    public void populateControlsFromData() {
        if (data == null) { return; }
        if( hasAutoGroup == null ) return;

        Log.i(TAG, "populating controls from data");

        int hasAutoCheckedButtonId = data.getHasAutonomous() ? R.id.hasAutoYes : R.id.hasAutoNo;
        hasAutoGroup.check(hasAutoCheckedButtonId);

        String autoFuelScoredText = StringUtils.defaultIfBlank(String.valueOf(data.getAutoFuelScored()), "0");
        autoFuelScoredLayout.getEditText().setText(autoFuelScoredText);

        boolean wantsHelpWithAuto = data.getHelpCreatingAuto();
        int wantsHelpCheckedButtonId = wantsHelpWithAuto ? R.id.helpAutoYes : R.id.helpAutoNo;
        helpAutoGroup.check(wantsHelpCheckedButtonId);

        String programmingLanguage = StringUtils.defaultIfBlank(data.getCodingLanguage(), defaultProgrammingLanguage);
        programmingLanguageAutoComplete.setText(programmingLanguage, false);

        setVisibility();
    }

    public void setVisibility() {
        Log.i(TAG, "start of visibility");

        if (data == null) { return; }
        if( hasAutoGroup == null ) return;

        Log.i(TAG, "end of visibility");

        programmingLanguageLayout.setVisibility( data.getHasAutonomous() ? View.GONE : View.VISIBLE);


        int i;
        i = 10;
    }
}