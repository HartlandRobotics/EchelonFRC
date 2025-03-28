package org.hartlandrobotics.echelonFRC.database.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelonFRC.database.EchelonDatabase;
import org.hartlandrobotics.echelonFRC.database.dao.PitScoutDao;
import org.hartlandrobotics.echelonFRC.database.entities.PitScout;

import java.util.List;

public class PitScoutRepo {
    private PitScoutDao pitScoutDao;

    public PitScoutRepo(Application application){
        EchelonDatabase db = EchelonDatabase.getDatabase(application);

        pitScoutDao = db.pitScoutDao();
    }

    public LiveData<PitScout> getPitScout(String eventKey, String teamKey){

        return pitScoutDao.getPitScout(eventKey, teamKey);
    }

    public LiveData<List<PitScout>> getPitScoutByEvent(String eventKey){
        return pitScoutDao.getPitScoutByEvent(eventKey);
    }
    public void upsert(PitScout pitScout){
        EchelonDatabase.databaseWriteExecutor.execute(() -> pitScoutDao.upsert(pitScout));
    }
}
