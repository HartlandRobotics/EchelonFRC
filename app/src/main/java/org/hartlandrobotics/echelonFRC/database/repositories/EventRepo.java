package org.hartlandrobotics.echelonFRC.database.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelonFRC.database.EchelonDatabase;
import org.hartlandrobotics.echelonFRC.database.dao.DistrictWithEventsDao;
import org.hartlandrobotics.echelonFRC.database.dao.EvtDao;
import org.hartlandrobotics.echelonFRC.database.dao.EvtWithMatchesDao;
import org.hartlandrobotics.echelonFRC.database.dao.EvtWithTeamsDao;
import org.hartlandrobotics.echelonFRC.database.entities.DistrictEvtCrossRef;
import org.hartlandrobotics.echelonFRC.database.entities.DistrictWithEvents;
import org.hartlandrobotics.echelonFRC.database.entities.Evt;
import org.hartlandrobotics.echelonFRC.database.entities.EvtMatchCrossRef;
import org.hartlandrobotics.echelonFRC.database.entities.EvtTeamCrossRef;
//import org.hartlandrobotics.echelonFRC.database.entities.EvtWithMatches;
import org.hartlandrobotics.echelonFRC.database.entities.EvtWithMatches;
import org.hartlandrobotics.echelonFRC.database.entities.EvtWithTeams;

import java.util.List;

public class EventRepo {
    private EvtDao eventDao;
    private EvtWithTeamsDao eventWithTeamsDao;
    private EvtWithMatchesDao eventWithMatchesDao;
    private DistrictWithEventsDao districtWithEventDao;


    public EventRepo(Application application) {
        EchelonDatabase db = EchelonDatabase.getDatabase( application );

        eventDao = db.eventDao();

        eventWithTeamsDao = db.eventTeamsDao();
        eventWithMatchesDao = db.eventMatchesDao();
        districtWithEventDao = db.districtEventsDao();
    }

    public LiveData<EvtWithTeams> getEventWithTeams(String eventKey) {
       return eventWithTeamsDao.getEventTeams( eventKey );
    }

    public LiveData<EvtWithMatches> getEventWithMatchs(String eventKey){
        return eventWithMatchesDao.getEventMatches(eventKey);
    }

    public void upsert(Evt event) {
        EchelonDatabase.databaseWriteExecutor.execute( () -> eventDao.upsert( event ) );
    }

    public void upsert(DistrictEvtCrossRef crossRefDistrict){
        EchelonDatabase.databaseWriteExecutor.execute( () -> { districtWithEventDao.upsert(crossRefDistrict);
        });
    }

    public void upsert(EvtTeamCrossRef crossRef) {
        EchelonDatabase.databaseWriteExecutor.execute( () -> {
            eventWithTeamsDao.upsert(crossRef);
        });
    }

    public void upsert(EvtMatchCrossRef crossRef) {
        EchelonDatabase.databaseWriteExecutor.execute( () -> eventWithMatchesDao.upsert( crossRef ) );
    }

    public void upsert(List<Evt> events){
        for(Evt evt: events){
            upsert(evt);
        }
    }

    public LiveData<List<Evt>> getEvent(String eventKey){
        return eventDao.getEvent(eventKey);
    }

    public LiveData<DistrictWithEvents> getDistrictWithEvents(String currentDistrict) {
        return districtWithEventDao.getDistrictEvents(currentDistrict);
    }

    public LiveData<EvtWithMatches> getMatchesForEvent(String eventKey){
        return eventWithMatchesDao.getEventMatches(eventKey);
    }
}
