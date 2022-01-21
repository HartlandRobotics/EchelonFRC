package org.hartlandrobotics.echelon2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.hartlandrobotics.echelon2.configuration.AdminSettings;
import org.hartlandrobotics.echelon2.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelon2.configuration.AdminSettingsViewModel;

public class AdminSettingsActivity extends AppCompatActivity {
    private static final String LOG_TAG = AdminSettingsActivity.class.getSimpleName();

    private TextInputLayout blueAllianceText;
    private TextInputLayout scoutingYearText;
    private TextInputLayout errorText;
    private AutoCompleteTextView scoutingYearsAutoComplete;

    public static void launch(Context context){
        Intent intent = new Intent( context, AdminSettingsActivity.class );
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        errorText = this.findViewById(R.id.errorText);
        scoutingYearsAutoComplete = findViewById(R.id.scoutingYearDropDown);

        AdminSettingsViewModel viewModel = AdminSettingsProvider.getAdminSettings(getApplicationContext());
        if( viewModel == null ){
            Log.e(LOG_TAG, "Could not load admin settings");
            showError("Could not load admin settings");
            return;
        }

        setupScoutingYearDropDown();
        initializeBlueAllianceKey(viewModel);
        initializeScoutingYear(viewModel);

    }

    public void setupScoutingYearDropDown(){
        String[] scoutingYears = getResources().getStringArray(R.array.scouting_years);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.season_dropdown_item, scoutingYears);
        scoutingYearsAutoComplete.setAdapter(adapter);
    }
    public void initializeBlueAllianceKey(AdminSettingsViewModel vm){
        blueAllianceText = this.findViewById(R.id.blueAllianceApiText);
        setDisplayText( blueAllianceText, vm.getBlueAllianceApiKey() );
        if( !vm.isBlueAllianceApikeySynced() ){
            setOutOfSync(blueAllianceText, vm.getFileSettings().getBlueAllianceApiKey());
        }
    }

    public void initializeScoutingYear(AdminSettingsViewModel vm){
        scoutingYearText = this.findViewById(R.id.scoutingYearText);
        setDisplayText( scoutingYearText, vm.getScoutingYear() );
        if( !vm.isScoutingYearSynced() ){
            setOutOfSync(scoutingYearText, vm.getFileSettings().getScoutingYear());
        }
    }


    private void setDisplayText(TextInputLayout layout, String displayText){
        layout.getEditText().setText(displayText);
    }

    private void setOutOfSync(TextInputLayout layout, String fileValue ){
        layout.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
        layout.setEndIconDrawable(R.drawable.ic_menu_refresh);
        layout.setHelperText(fileValue);
    }

    private void showError( String errorMessage ){
        errorText.setErrorEnabled(true);
        errorText.setError(errorMessage);
        errorText.getEditText().setText("Admin Settings not available");
    }
}