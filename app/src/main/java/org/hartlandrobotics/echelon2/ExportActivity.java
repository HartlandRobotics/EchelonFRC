package org.hartlandrobotics.echelon2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ExportActivity extends EchelonActivity {

    private Button exportMatchResultsButton;

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

        });
    }
}