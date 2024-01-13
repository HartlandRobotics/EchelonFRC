package org.hartlandrobotics.echelon2.database.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.apache.commons.lang3.StringUtils;
import java.util.UUID;

@Entity(tableName="match_result")
public class MatchResult {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "match_result_key")
    private String matchResultKey;

    @NonNull
    @ColumnInfo(name = "event_key")
    private String eventKey;

    @NonNull
    @ColumnInfo(name = "match_key")
    private String matchKey;

    @NonNull
    @ColumnInfo(name = "team_key")
    private String teamKey;

    @ColumnInfo(name = "has_been_synced")
    private boolean hasBeenSynced;

    @ColumnInfo(name="auto_1")
    private String auto1;

    @ColumnInfo(name = "auto_2")
    private String auto2;

    @ColumnInfo( name = "auto_3")
    private String auto3;

    @ColumnInfo( name = "auto_4")
    private String auto4;

    @ColumnInfo( name = "auto_5")
    private String auto5;

    @ColumnInfo( name = "teleop_1")
    private String teleOp1;

    @ColumnInfo( name = "teleop_2")
    private String teleOp2;

    @ColumnInfo( name = "teleop_3")
    private String teleOp3;

    @ColumnInfo( name = "teleop_4")
    private String teleOp4;

    @ColumnInfo( name = "teleop_5")
    private String teleOp5;

    @ColumnInfo( name = "end_1")
    private String end1;

    @ColumnInfo(name = "end_2")
    private String end2;

    @ColumnInfo(name = "end_3")
    private String end3;

        @ColumnInfo(name = "end_4")
    private String end4;

    @ColumnInfo(name = "end_5")
    private String end5;

    @ColumnInfo(name = "additional_notes")
    private String additionalNotes;

    @ColumnInfo(name = "defense_count")
    private int defenseCount;

    @Ignore
    public MatchResult(){}

    public MatchResult(
            @NonNull String matchResultKey,
            @NonNull String eventKey,
            @NonNull String matchKey,
            @NonNull String teamKey,
            boolean hasBeenSynced,
            @NonNull String auto1,
            @NonNull String auto2,
            @NonNull String auto3,
            @NonNull String auto4,
            @NonNull String auto5,
            @NonNull String teleOp1,
            @NonNull String teleOp2,
            @NonNull String teleOp3,
            @NonNull String teleOp4,
            @NonNull String teleOp5,
            @NonNull String end1,
            @NonNull String end2,
            @NonNull String end3,
            @NonNull String end4,
            @NonNull String end5,
            String additionalNotes,
            int defenseCount
    ) {

        this.matchResultKey = StringUtils.defaultIfBlank(matchResultKey, UUID.randomUUID().toString());
        this.eventKey = eventKey;
        this.matchKey = matchKey;
        this.teamKey = teamKey;
        this.hasBeenSynced = hasBeenSynced;
        this.auto1 = auto1;
        this.auto2 = auto2;
        this.auto3 = auto3;
        this.auto4 = auto4;
        this.auto5 = auto5;
        this.teleOp1 = teleOp1;
        this.teleOp2 = teleOp2;
        this.teleOp3 = teleOp3;
        this.teleOp4 = teleOp4;
        this.teleOp5 = teleOp5;
        this.end1 = end1;
        this.end2 = end2;
        this.end3 = end3;
        this.end4 = end4;
        this.end5 = end5;
        this.additionalNotes = additionalNotes;
        this.defenseCount = defenseCount;
    }

    public String getMatchResultKey() { return matchResultKey; }
    public void setMatchResultKey(String matchResultKey){
        this.matchResultKey = matchResultKey;
    }

    public String getEventKey(){ return eventKey; }
    public void setEventKey(String eventKey){
        this.eventKey = eventKey;
    }

    public String getMatchKey(){ return matchKey; }
    public void setMatchKey(String matchKey){ this.matchKey = matchKey; }

    public String getTeamKey(){ return teamKey; }
    public void setTeamKey(String teamKey){
        this.teamKey = teamKey;
    }

    public boolean getHasBeenSynced(){ return hasBeenSynced; }
    public void setHasBeenSynced(boolean hasBeenSynced){
        this.hasBeenSynced = hasBeenSynced;
    }

    public String getAuto1(){ return auto1; }
    public void setAuto1(String auto1){ this.auto1 = auto1; }

    public String getAuto2(){ return auto2; }
    public void setAuto2(String auto2){ this.auto2 = auto2; }

    public String getAuto3(){ return auto3; }
    public void setAuto3(String auto3){ this.auto3 = auto3; }

    public String getAuto4(){ return auto4; }
    public void setAuto4(String auto4){ this.auto4 = auto4; }

    public String getAuto5(){ return auto5; }
    public void setAuto5(String auto5){ this.auto5 = auto5; }

    public String getTeleOp1(){ return teleOp1; }
    public void setTeleOp1(String teleOp1){ this.teleOp1 = teleOp1; }

    public String getTeleOp2(){ return teleOp2; }
    public void setTeleOp2(String teleOp2){ this.teleOp2 = teleOp2; }

    public String getTeleOp3(){ return teleOp3; }
    public void setTeleOp3(String teleOp3){ this.teleOp3 = teleOp3; }

    public String getTeleOp4(){ return teleOp4; }
    public void setTeleOp4(String teleOp4){ this.teleOp4 = teleOp4; }

    public String getTeleOp5(){ return teleOp5; }
    public void setTeleOp5(String teleOp5){ this.teleOp5 = teleOp5; }

    public String getEnd1(){ return end1; }
    public void setEnd1(String end1){ this.end1 = end1; }

    public String getEnd2(){ return end2; }
    public void setEnd2(String end2){ this.end2 = end2; }

    public String getEnd3(){ return end3; }
    public void setEnd3(String end3){ this.end3 = end3; }

    public String getEnd4(){ return end4; }
    public void setEnd4(String end4){ this.end4 = end4; }

    public String getEnd5(){ return end5; }
    public void setEnd5(String end5){ this.end5 = end5; }

    public String getAdditionalNotes(){ return additionalNotes; }
    public void setAdditionalNotes( String additionalNotes ){
        this.additionalNotes = additionalNotes;
    }

    public int getDefenseCount(){ return defenseCount; }
    public void setDefenseCount( int defenseCount ){
        this.defenseCount = defenseCount;
    }
}
