package org.hartlandrobotics.echelonFRC.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity( tableName = "opr" )
public class Opr {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "team_key")
    private String teamKey;
    //teamkey

    @ColumnInfo(name = "opr")
    private double opr;
    //opr

    @ColumnInfo(name = "foul")
    private double foul;

    public Opr(@NonNull String teamKey, double opr, double foul) {
        this.teamKey = teamKey;
        this.opr = opr;
        this.foul = foul;
    }

    @NonNull
    public String getTeamKey() { return teamKey; }

    public double getOpr() { return opr; }

    public double getFoul() { return foul; }

}
