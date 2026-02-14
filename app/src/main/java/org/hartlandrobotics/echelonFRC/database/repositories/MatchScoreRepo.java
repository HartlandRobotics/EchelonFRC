package org.hartlandrobotics.echelonFRC.database.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelonFRC.database.EchelonDatabase;
import org.hartlandrobotics.echelonFRC.database.dao.MatchScoreDao;
import org.hartlandrobotics.echelonFRC.database.entities.MatchScore;
import org.hartlandrobotics.echelonFRC.database.entities.Opr;

import java.util.List;

public class MatchScoreRepo {

    private MatchScoreDao matchScoreDao;

    public MatchScoreRepo(Application application){
        EchelonDatabase db = EchelonDatabase.getDatabase(application);
        matchScoreDao = db.matchScoreDao();
    }

    public LiveData<List<MatchScore>> getMatchScores(){return matchScoreDao.getMatchScore();}

    public void upsert(MatchScore matchScore){
        EchelonDatabase.databaseWriteExecutor.execute(() ->{
            matchScoreDao.upsert(matchScore);
        });
    }

    public void upsert(List<MatchScore> matchScores){
        for(MatchScore matchScore : matchScores){
            upsert(matchScore);
        }
    }

}
