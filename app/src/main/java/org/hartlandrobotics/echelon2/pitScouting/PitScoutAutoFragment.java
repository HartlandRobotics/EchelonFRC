package org.hartlandrobotics.echelon2.pitScouting;

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
import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.database.entities.PitScout;


public class PitScoutAutoFragment extends Fragment {
    private static final String TAG = "PitScoutAutoFragment";

    TextInputLayout autoStartPositions;
    RadioGroup helpAutoGroup;
    TextInputLayout programmingLanguageLayout;
    AutoCompleteTextView programmingLanguageAutoComplete;
    String defaultProgrammingLanguage;
    LinearLayout missingAutoLayout;
    LinearLayout hasAutoLayout;
    TextInputLayout autoLanguage;
    TextInputLayout autoCount;
    TextInputLayout ringPickUp;
    TextInputLayout ringShot;


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
        Log.i(TAG, "OnResume running");
        populateControlsFromData();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "OnPause running");
        super.onPause();
        populateDataFromControls();
    }

    private void setupControls(View view) {


        autoStartPositions = view.findViewById(R.id.autoStartPositions);

        autoCount = view.findViewById(R.id.autoCount);

        ringPickUp = view.findViewById(R.id.ringPickUp);

        ringShot = view.findViewById(R.id.ringScoring);

        missingAutoLayout = view.findViewById(R.id.missingAutoLayout);

        helpAutoGroup = view.findViewById(R.id.helpAutoGroup);
        helpAutoGroup.setOnCheckedChangeListener((group, checkedId) -> {
            setVisibility();
        });

        autoLanguage = view.findViewById(R.id.autoLanguage);

        programmingLanguageLayout = view.findViewById(R.id.autoLanguage);
        programmingLanguageAutoComplete = view.findViewById(R.id.autoLanguageAutoComplete);
        String[] languages = getResources().getStringArray(R.array.programming_languages);
        defaultProgrammingLanguage = languages[0];
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.dropdown_item, languages);
        programmingLanguageAutoComplete.setAdapter(adapter);

    }


    public void populateDataFromControls() {

        if( data == null ) return;
        String startingPositions = StringUtils.defaultIfBlank(autoStartPositions.getEditText().getText().toString(),"none");
        data.setAutoStartPositions(startingPositions);

        String notePickUpString = StringUtils.defaultIfBlank(ringPickUp.getEditText().getText().toString(), "0");
        int notePickUp = Integer.parseInt(notePickUpString.toString());
        data.setRingsPickedUpInAuto(notePickUp);

        String noteScoredString = StringUtils.defaultIfBlank(ringShot.getEditText().getText().toString(), "0");
        int noteScored = Integer.parseInt(noteScoredString.toString());
        data.setRingsPickedUpInAuto(noteScored);



        boolean wantsHelp = startingPositions.equalsIgnoreCase("none");
        data.setHelpCreatingAuto(wantsHelp);

        String codingLanguage = StringUtils.defaultIfBlank(programmingLanguageAutoComplete.getEditableText().toString(), StringUtils.EMPTY);
        data.setCodingLanguage(codingLanguage);
    }

    public void populateControlsFromData() {
        if (data == null) {
            return;
        }

        Log.i(TAG, "populating controls from data");

        String autoStartPositionsValue = StringUtils.defaultIfBlank(String.valueOf(data.getAutoStartPositions()),"none");
        int ringPickUp = Integer.parseInt(StringUtils.defaultIfBlank(String.valueOf(data.getRingsPickedUpInAuto()),"0"));
        int ringScoring = Integer.parseInt(StringUtils.defaultIfBlank(String.valueOf(data.getRingsShotInAuto()),"0"));

        


        boolean wantsHelpWithAuto = data.getHelpCreatingAuto();
        int wantsHelpCheckedButtonId = wantsHelpWithAuto ? R.id.helpAutoYes : R.id.helpAutoNo;
        helpAutoGroup.check(wantsHelpCheckedButtonId);

        String programmingLanguage = StringUtils.defaultIfBlank(data.getCodingLanguage(), defaultProgrammingLanguage);
        programmingLanguageAutoComplete.setText(programmingLanguage, false);

        setVisibility();
    }

    public void setVisibility() {
        if (hasAutoGroup.getCheckedRadioButtonId() == R.id.hasAutoYes) {
            missingAutoLayout.setVisibility(View.GONE);
            hasAutoLayout.setVisibility(View.VISIBLE);
            autoLanguage.setVisibility(View.GONE);
        } else {
            missingAutoLayout.setVisibility(View.VISIBLE);
            hasAutoLayout.setVisibility(View.GONE);
            autoLanguage.setVisibility(View.VISIBLE);
        }

        if (helpAutoGroup.getCheckedRadioButtonId() == R.id.helpAutoNo) {
            autoLanguage.setVisibility(View.GONE);
        } else {
            autoLanguage.setVisibility(View.VISIBLE);
        }
    }
}