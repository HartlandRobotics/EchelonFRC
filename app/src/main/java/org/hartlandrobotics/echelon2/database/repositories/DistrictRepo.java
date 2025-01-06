package org.hartlandrobotics.echelonFRC.database.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelonFRC.database.EchelonDatabase;
import org.hartlandrobotics.echelonFRC.database.dao.DistrictDao;
import org.hartlandrobotics.echelonFRC.database.entities.District;

import java.util.List;

public class DistrictRepo {
    private DistrictDao districtDao;

    public DistrictRepo(Application application){
        EchelonDatabase db = EchelonDatabase.getDatabase(application);
        districtDao = db.districtDao();

    }

    public LiveData<List<District>> getDistrictsByYear(int year){return districtDao.getDistrictsByYear(year);}

    public void upsert(District district){
        EchelonDatabase.databaseWriteExecutor.execute( () -> {
            districtDao.upsert(district);
        });
    }

    public void upsert(List<District> districts){
        for( District d : districts){
            upsert(d);
        }
    }
}
