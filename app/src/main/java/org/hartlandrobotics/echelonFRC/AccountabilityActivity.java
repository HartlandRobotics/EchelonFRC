package org.hartlandrobotics.echelonFRC;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class AccountabilityActivity extends EchelonActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activ);
        setupToolbar("Accuracy");


    }

    public static void launch(Context context){
        Intent intent = new Intent( context, AccountabilityActivity.class );
        context.startActivity(intent);
    }
}
