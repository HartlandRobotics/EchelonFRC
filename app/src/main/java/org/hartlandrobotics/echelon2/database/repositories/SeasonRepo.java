package org.hartlandrobotics.echelonFRC.database.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelonFRC.database.EchelonDatabase;
import org.hartlandrobotics.echelonFRC.database.dao.SeasonDao;
import org.hartlandrobotics.echelonFRC.database.entities.Season;

import java.util.List;

public class SeasonRepo {
    private SeasonDao seasonDao;

    public SeasonRepo(Application application){
        EchelonDatabase db = EchelonDatabase.getDatabase(application);
        seasonDao = db.seasonDao();
    }

    public LiveData<List<Season>> getSeasons(){return seasonDao.getSeasons();}

}
