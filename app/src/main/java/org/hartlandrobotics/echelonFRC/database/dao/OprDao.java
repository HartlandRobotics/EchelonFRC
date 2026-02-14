package org.hartlandrobotics.echelonFRC.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.hartlandrobotics.echelonFRC.database.entities.Opr;

import java.util.List;

@Dao
public abstract class OprDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Opr opr);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(Opr opr);

    @Query("SELECT * FROM opr")
    public abstract LiveData<List<Opr>> getOprs();

    @Query("SELECT * FROM opr WHERE team_key = :teamKey")
    public abstract LiveData<List<Opr>> getOprsByTeam(int teamKey);

    public void upsert(Opr opr){
        long id = insert(opr);
        if(id == -1){
            update(opr);
        }
    }

    public void upsert(List<Opr> oprs){
        for( Opr o : oprs ){
            upsert(o);
        }
    }
}