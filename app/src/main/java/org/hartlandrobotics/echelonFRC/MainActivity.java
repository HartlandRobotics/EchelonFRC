package org.hartlandrobotics.echelonFRC;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.hartlandrobotics.echelonFRC.blueAlliance.BlueAllianceActivity;
import org.hartlandrobotics.echelonFRC.charts.ChartsActivity;
import org.hartlandrobotics.echelonFRC.configuration.AdminSettings;
import org.hartlandrobotics.echelonFRC.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelonFRC.database.entities.Season;
import org.hartlandrobotics.echelonFRC.matchScouting.MatchSelectionActivity;
import org.hartlandrobotics.echelonFRC.models.SeasonViewModel;
import org.hartlandrobotics.echelonFRC.pitScouting.PitScoutActivity;
import org.hartlandrobotics.echelonFRC.status.BlueAllianceStatus;
import org.hartlandrobotics.echelonFRC.utilities.RoleUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainActivity extends EchelonActivity {

    AdminSettings adminSettings;
    private BlueAllianceStatus status;
    String deviceRole;

    private boolean hasSelectedSeason;

    private MaterialButton startScouting;
    private MaterialButton pitScouting;
    private MaterialButton matchSchedule;
    private MaterialButton chartsButton;
    private MaterialButton accuracyButton;


    private AutoCompleteTextView seasonsAutoComplete;

    public static void launch(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar("Home");

        status= new BlueAllianceStatus(getApplicationContext());
        adminSettings= AdminSettingsProvider.getAdminSettings(this);
        deviceRole = adminSettings.getDeviceRole();

        setupStartScoutingButton();
        setupPitScoutingButton();
        setupMatchScheduleButton();
        setupChartsButton();
        setupAccuracyButton();

        setupSeasonSelection();

        handlePermissions();
    }

    private void handlePermissions() {
        int REQUEST_REQUIRED_PERMISSIONS = 1;
        String[] REQUIRED_PERMISSIONS = {
                Manifest.permission.INTERNET,
        };

        boolean requestNeeded = false;
        for( String permission : REQUIRED_PERMISSIONS ){
            int permissionId = ActivityCompat.checkSelfPermission(this, permission);
            requestNeeded |=  (permissionId != PackageManager.PERMISSION_GRANTED);
        }
        if( requestNeeded ) {
            ActivityCompat.requestPermissions( this, REQUIRED_PERMISSIONS, REQUEST_REQUIRED_PERMISSIONS );
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        setupStatus();
    }

    private void setupStartScoutingButton(){
        startScouting = this.findViewById(R.id.main_admin_start_scouting);
        startScouting.setOnClickListener(view -> MatchSelectionActivity.launch(MainActivity.this, null));
    }

    private void setupPitScoutingButton(){
        pitScouting = findViewById(R.id.pit_scout_button);
        pitScouting.setOnClickListener( v -> PitScoutActivity.launch(MainActivity.this ));
    }

    private void setupMatchScheduleButton(){
        matchSchedule = findViewById(R.id.match_schedule_button);
       matchSchedule.setOnClickListener( v -> MatchScheduleActivity.launch(MainActivity.this));
    }

    private void setupChartsButton(){
        chartsButton = findViewById(R.id.charts_button);
        chartsButton.setOnClickListener( v -> ChartsActivity.launch(MainActivity.this));

        if( RoleUtilities.isAdminTablet(deviceRole)){
            chartsButton.setVisibility(View.VISIBLE);
        }
    }

    private void setupAccuracyButton(){
        accuracyButton = findViewById(R.id.main_admin_accuracy_config);
        accuracyButton.setOnClickListener( v -> AccountabilityActivity.launch(MainActivity.this));

        if( RoleUtilities.isAdminTablet(deviceRole)){
            accuracyButton.setVisibility(View.VISIBLE);
        }
    }


    private void setupStatus(){
       status.loadSettingsFromPrefs();
        TextInputLayout seasonLayout = findViewById(R.id.season_status_layout);
        seasonLayout.getEditText().setText(status.getSeason());

        TextInputLayout districtLayout = findViewById(R.id.district_status_layout);
        districtLayout.getEditText().setText(status.getDistrictKey());

        TextInputLayout eventLayout = findViewById(R.id.event_status_layout);
        eventLayout.getEditText().setText(status.getEventKey());

        TextInputLayout deviceLayout = findViewById(R.id.device_status_layout);
        deviceLayout.getEditText().setText(deviceRole);
    }

    List<Season> seasons = new ArrayList<>();
    List<String> displaySeasons = new ArrayList<>();
    private void setupSeasonSelection(){
        seasonsAutoComplete = findViewById(R.id.seasonSelectionAutoComplete);

        SeasonViewModel seasonViewModel = new ViewModelProvider(this).get(SeasonViewModel.class);
        seasonViewModel.getSeasons().observe( this, seasonEntities -> {
            seasons.addAll(seasonEntities);
            displaySeasons = seasons.stream()
                    .map(s -> s.getName() + " - " + s.getYear() )
                    .collect(Collectors.toList());

            ArrayAdapter seasonsAdapter = new ArrayAdapter(this, R.layout.dropdown_item, displaySeasons);
            seasonsAutoComplete.setAdapter(seasonsAdapter);
            // get the current season from status
            String currentSeason = status.getSeason();
            String currentYear = status.getYear();
            String currentDisplay = currentSeason + " - " + currentYear;

            Optional<Integer> foundIndex = Optional.empty();
            for(int displayIndex=0; displayIndex< displaySeasons.size(); displayIndex++ ){
                if( displaySeasons.get(displayIndex).equals(currentDisplay)){
                    foundIndex = Optional.of(Integer.valueOf(displayIndex));
                    break;
                }
            }
            if( foundIndex.isPresent() ){
                String selectedText = seasonsAdapter.getItem(foundIndex.get()).toString();

                seasonsAutoComplete.setText(selectedText, false);
            }
            hasSelectedSeason = foundIndex.isPresent();
            setEnabled();

            seasonsAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
                Season selectedSeason = seasons.get(position);
                status.setYear( String.valueOf(selectedSeason.getYear()) );
                status.setSeason( selectedSeason.getName() );
                String selectedText = status.getSeason() + " - " + status.getYear();
                seasonsAutoComplete.setText(selectedText, false);
                setupStatus();
                hasSelectedSeason = true;
                setEnabled();
            });
        });
    }

    private void setEnabled(){
           startScouting.setEnabled(hasSelectedSeason && RoleUtilities.isStudentTablet(deviceRole));
           pitScouting.setEnabled(hasSelectedSeason && RoleUtilities.isAdminTablet(deviceRole));
           matchSchedule.setEnabled(hasSelectedSeason);
           chartsButton.setEnabled(hasSelectedSeason && RoleUtilities.isAdminTablet(deviceRole));
           accuracyButton.setEnabled(RoleUtilities.isAdminTablet(deviceRole));
    }
}