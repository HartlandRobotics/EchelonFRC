package org.hartlandrobotics.echelon2.TBA;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.hartlandrobotics.echelon2.R;

import org.hartlandrobotics.echelon2.TBA.fragments.DistrictsFragment;
import org.hartlandrobotics.echelon2.database.entities.District;
import org.hartlandrobotics.echelon2.database.repositories.DistrictRepo;

import java.util.List;


public class TBAActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager;
    TBAPagerAdapter tbaPagerAdapter;


    DistrictRepo districtRepo;


    public static void launch(Context context){
        Intent intent = new Intent(context, TBAActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tba);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        tbaPagerAdapter = new TBAPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(tbaPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tbaPagerAdapter.getTabTitle(position))).attach();


        districtRepo = new DistrictRepo(getApplication());
    }

    public void updateDistricts(List<District> districts){
        districtRepo.upsert(districts);

    }
}
