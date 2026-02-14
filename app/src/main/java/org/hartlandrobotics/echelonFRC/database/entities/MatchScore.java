package org.hartlandrobotics.echelonFRC.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="match_score")
public class MatchScore {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "match_key")
    private String matchKey;

    @ColumnInfo(name = "match_number")
    private int matchNumber;

    @ColumnInfo(name = "red_total")
    private int redTotal;

    @ColumnInfo(name = "red_foul")
    private int redFoul;

    @ColumnInfo(name = "blue_total")
    private int blueTotal;

    @ColumnInfo(name = "blue_foul")
    private int blueFoul;

    public MatchScore(@NonNull String matchKey, int matchNumber, int redTotal, int redFoul, int blueTotal, int blueFoul) {
        this.matchKey = matchKey;
        this.matchNumber = matchNumber;
        this.redTotal = redTotal;
        this.redFoul = redFoul;
        this.blueTotal = blueTotal;
        this.blueFoul = blueFoul;
    }

    @NonNull
    public String getMatchKey() {
        return matchKey;
    }

    public void setMatchKey(@NonNull String matchKey) {
        this.matchKey = matchKey;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public int getRedTotal() {
        return redTotal;
    }

    public void setRedTotal(int redTotal) {
        this.redTotal = redTotal;
    }

    public int getRedFoul() {
        return redFoul;
    }

    public void setRedFoul(int redFoul) {
        this.redFoul = redFoul;
    }

    public int getBlueTotal() {
        return blueTotal;
    }

    public void setBlueTotal(int blueTotal) {
        this.blueTotal = blueTotal;
    }

    public int getBlueFoul() {
        return blueFoul;
    }

    public void setBlueFoul(int blueFoul) {
        this.blueFoul = blueFoul;
    }
}
