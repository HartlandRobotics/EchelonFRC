package org.hartlandrobotics.echelon2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import org.hartlandrobotics.echelon2.database.entities.MatchResult;

public class ExportActivity extends EchelonActivity {

    private Button exportMatchResultsButton;
    private MatchResult matchResult; // may not need to use this

    public static void launch(Context context){
        Intent intent = new Intent( context, ExportActivity.class );
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        setupToolbar();

        exportMatchResultsButton = findViewById(R.id.exportMatchResults);
        exportMatchResults();
    }

    public void exportMatchResults(){
        exportMatchResultsButton.setOnClickListener((view) -> {
            //Use viewmodel that will return a list of matchresults, then observe it and do export inside
        });
    }
}