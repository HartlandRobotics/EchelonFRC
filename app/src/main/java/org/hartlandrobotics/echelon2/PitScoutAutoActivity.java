package org.hartlandrobotics.echelon2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputLayout;

public class PitScoutAutoActivity extends AppCompatActivity {
    RadioGroup hasAutoGroup;
    RadioGroup helpAutoGroup;
    TextInputLayout programmingLanguageLayout;
    AutoCompleteTextView programmingLanguageAutoComplete;
    LinearLayout missingAutoLayout;
    LinearLayout hasAutoLayout;

    public static void launch(Context context){
        Intent intent = new Intent(context, PitScoutAutoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_scout_auto);

        hasAutoGroup = findViewById(R.id.hasAutoGroup);
        helpAutoGroup = findViewById(R.id.helpAutoGroup);
        programmingLanguageLayout = findViewById(R.id.autoLanguage);
        programmingLanguageAutoComplete = findViewById(R.id.autoLanguageAutoComplete);
        missingAutoLayout = findViewById(R.id.missingAutoLayout);
        hasAutoLayout = findViewById(R.id.hasAutoLayout);

        hasAutoGroup.setOnCheckedChangeListener((group, checkedId) -> {
           if( checkedId == R.id.hasAutoYes ){
               missingAutoLayout.setVisibility(View.GONE);
               hasAutoLayout.setVisibility(View.VISIBLE);
           }
           if( checkedId == R.id.hasAutoNo ){
               missingAutoLayout.setVisibility(View.VISIBLE);
               hasAutoLayout.setVisibility(View.GONE);
           }
        });


        String[] languages = getResources().getStringArray(R.array.programming_languages);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.dropdown_item, languages);
        programmingLanguageAutoComplete.setAdapter(adapter);
    }
}

/*
 val feelings = resources.getStringArray(R.array.feelings)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, feelings)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
 */