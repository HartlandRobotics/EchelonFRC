package org.hartlandrobotics.echelonFRC.pitScouting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFRC.R;
import org.hartlandrobotics.echelonFRC.database.entities.PitScout;

public class PitScoutEndGameFragment extends Fragment {
    public static final String TAG = "PitScoutEndGameFragment";

    CheckBox endHangCheckbox;

    TextInputLayout hangTimeLayout;
    TextInputLayout endWhereHangLayout;
    TextInputLayout endHowHangLayout;

    TextView endHangLevelsText;

    CheckBox endHighClimbCheckbox;
    CheckBox endMidClimbCheckbox;
    CheckBox endLowClimbCheckbox;

    public PitScoutEndGameFragment() {
        // Required empty public constructor
    }

    PitScout data;

    public void setData(PitScout data) {
        this.data = data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pitscout_end_game, container, false);

        setupControls(view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        populateControlsFromData();

    }

    @Override
    public void onPause() {
        super.onPause();
        populateDataFromControls();
    }

    private void setupControls(View view) {
        if (data == null) {
            return;
        }
        endHangCheckbox = view.findViewById(R.id.endHang);

        hangTimeLayout = view.findViewById(R.id.hangTime);
        endWhereHangLayout = view.findViewById(R.id.endWhereHang);
        endHowHangLayout = view.findViewById(R.id.endHowHang);

        endHangLevelsText = view.findViewById(R.id.endHangLevelsText);

        endHighClimbCheckbox = view.findViewById(R.id.endHighClimb);
        endMidClimbCheckbox = view.findViewById(R.id.endMidClimb);
        endLowClimbCheckbox = view.findViewById(R.id.endLowClimb);
    }

    public void populateDataFromControls() {
        if (data == null) return;
        if (hangTimeLayout == null) return;

        data.setEndHang(endHangCheckbox.isChecked());

        String hangTimeText = StringUtils.defaultIfBlank(hangTimeLayout.getEditText().getText().toString(), "0");
        data.setHangTime(Integer.valueOf(hangTimeText));

        String endWhereHang = endWhereHangLayout.getEditText().getText().toString();
        data.setEndWhereHang(endWhereHang);
        String endHowHang = endHowHangLayout.getEditText().getText().toString();
        data.setAutoHowHang(endHowHang);

        data.setEndHighClimb(endHighClimbCheckbox.isChecked());
        data.setEndMidClimb(endMidClimbCheckbox.isChecked());
        data.setEndLowClimb(endLowClimbCheckbox.isChecked());

    }

    private void populateControlsFromData() {
        if (data == null) {
            return;
        }

        endHangCheckbox.setChecked(data.getEndHang());

        if (hangTimeLayout == null) return;

        String hangTimeText = String.valueOf(data.getHangTime());
        hangTimeLayout.getEditText().setText(hangTimeText);

        String endWhereHang = data.getAutoWhereHang();
        endWhereHangLayout.getEditText().setText(endWhereHang);

        String endHowHang = data.getAutoHowHang();
        endHowHangLayout.getEditText().setText(endHowHang);

        endHighClimbCheckbox.setChecked(data.getEndHighClimb());
        endMidClimbCheckbox.setChecked(data.getEndMidClimb());
        endLowClimbCheckbox.setChecked(data.getEndLowClimb());

        setVisibility();
    }

    public void setVisibility() {
        Log.i(TAG, "start of visibility");

        if (data == null) { return; }
        if( endHangCheckbox == null ) return;

        Log.i(TAG, "end of visibility");

        boolean visible = endHangCheckbox.isChecked();

        hangTimeLayout.setVisibility(visible ? View.VISIBLE: View.GONE);
        endWhereHangLayout.setVisibility(visible ? View.VISIBLE: View.GONE);
        endHowHangLayout.setVisibility(visible ? View.VISIBLE: View.GONE);
        endHighClimbCheckbox.setVisibility(visible ? View.VISIBLE: View.GONE);
        endMidClimbCheckbox.setVisibility(visible ? View.VISIBLE: View.GONE);
        endLowClimbCheckbox.setVisibility(visible ? View.VISIBLE: View.GONE);
        endHangLevelsText.setVisibility(visible ? View.VISIBLE: View.GONE);



        int i;
        i = 10;
    }
}