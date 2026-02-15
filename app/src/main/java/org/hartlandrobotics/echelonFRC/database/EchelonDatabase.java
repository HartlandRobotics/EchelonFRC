package org.hartlandrobotics.echelonFRC.database;

import android.content.Context;

import org.hartlandrobotics.echelonFRC.database.dao.DistrictDao;
import org.hartlandrobotics.echelonFRC.database.dao.DistrictWithEventsDao;
import org.hartlandrobotics.echelonFRC.database.dao.EvtWithMatchesDao;
import org.hartlandrobotics.echelonFRC.database.dao.EvtWithTeamsDao;
import org.hartlandrobotics.echelonFRC.database.dao.MatchDao;
import org.hartlandrobotics.echelonFRC.database.dao.MatchResultDao;
import org.hartlandrobotics.echelonFRC.database.dao.MatchScoreDao;
import org.hartlandrobotics.echelonFRC.database.dao.OprDao;
import org.hartlandrobotics.echelonFRC.database.dao.PitScoutDao;
import org.hartlandrobotics.echelonFRC.database.dao.SeasonDao;
import org.hartlandrobotics.echelonFRC.database.dao.TeamDao;
import org.hartlandrobotics.echelonFRC.database.entities.District;
import org.hartlandrobotics.echelonFRC.database.entities.DistrictEvtCrossRef;
import org.hartlandrobotics.echelonFRC.database.entities.Evt;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import org.hartlandrobotics.echelonFRC.database.dao.EvtDao;
import org.hartlandrobotics.echelonFRC.database.entities.EvtMatchCrossRef;
import org.hartlandrobotics.echelonFRC.database.entities.EvtTeamCrossRef;
import org.hartlandrobotics.echelonFRC.database.entities.Match;
import org.hartlandrobotics.echelonFRC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFRC.database.entities.MatchScore;
import org.hartlandrobotics.echelonFRC.database.entities.Opr;
import org.hartlandrobotics.echelonFRC.database.entities.PitScout;
import org.hartlandrobotics.echelonFRC.database.entities.Season;
import org.hartlandrobotics.echelonFRC.database.entities.Team;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {
        Evt.class,
        District.class,
        Team.class,
        PitScout.class,
        Season.class,
        EvtTeamCrossRef.class,
        EvtMatchCrossRef.class,
        DistrictEvtCrossRef.class,
        Match.class,
        MatchResult.class,
        Opr.class,
        MatchScore.class,

}, version = 8,
        exportSchema = false
)
public abstract class EchelonDatabase extends RoomDatabase {
    public abstract EvtDao eventDao();
    public abstract TeamDao teamDao();
    public abstract DistrictDao districtDao();
    public abstract PitScoutDao pitScoutDao();
    public abstract SeasonDao seasonDao();
    public abstract EvtWithTeamsDao eventTeamsDao();
    public abstract EvtWithMatchesDao eventMatchesDao();
    public abstract DistrictWithEventsDao districtEventsDao();
    public abstract MatchDao matchDao();
    public abstract MatchResultDao matchResultDao();
    public abstract OprDao oprDao();
    public abstract MatchScoreDao matchScoreDao();


    private static volatile EchelonDatabase _instance;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static EchelonDatabase getDatabase(final Context context){
        if(_instance == null){
            synchronized ( EchelonDatabase.class){
                if(_instance == null){
                    _instance = Room.databaseBuilder(context.getApplicationContext(),
                            EchelonDatabase.class,                                    "echelon_frc_database.db")
                            .fallbackToDestructiveMigration()
                            .setJournalMode(JournalMode.TRUNCATE)
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return _instance;
    }



    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                SeasonDao sd = _instance.seasonDao();

                Season reefscapeSeason = new Season("Reefscape", 2025);
                sd.insert(reefscapeSeason);

                Season rebuiltSeason = new Season("Rebuilt", 2026);
                sd.insert(rebuiltSeason);
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){

            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                //any initialization stuff goes here
                EvtDao evtDao = _instance.eventDao();
                TeamDao teamDao = _instance.teamDao();
                DistrictDao districtDao = _instance.districtDao();
                PitScoutDao pitScoutDao = _instance.pitScoutDao();
                SeasonDao seasonDao = _instance.seasonDao();
                MatchResultDao matchResultDao = _instance.matchResultDao();
                OprDao oprDao = _instance.oprDao();
                MatchScoreDao matchScoreDao = _instance.matchScoreDao();
            } );
        }
    };
}