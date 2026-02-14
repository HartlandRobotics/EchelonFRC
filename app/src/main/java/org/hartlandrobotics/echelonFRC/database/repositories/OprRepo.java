package org.hartlandrobotics.echelonFRC.database.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelonFRC.database.EchelonDatabase;
import org.hartlandrobotics.echelonFRC.database.dao.OprDao;
import org.hartlandrobotics.echelonFRC.database.entities.Opr;

import java.util.List;

public class OprRepo {
    private OprDao oprDao;

    public OprRepo(Application application){
        EchelonDatabase db = EchelonDatabase.getDatabase(application);
        oprDao = db.oprDao();
    }

    public LiveData<List<Opr>> getOprs(){return oprDao.getOprs();}

    public void upsert(Opr opr){
        EchelonDatabase.databaseWriteExecutor.execute( () -> {
            oprDao.upsert(opr);
        });
    }

    public void upsert(List<Opr> oprs){
        for( Opr o : oprs){
            upsert(o);
        }
    }
}
