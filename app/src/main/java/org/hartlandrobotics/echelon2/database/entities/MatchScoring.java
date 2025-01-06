package org.hartlandrobotics.echelonFRC.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName="match_scoring")
public class MatchScoring {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "match_scoring_key")
    private int matchScoringKey;

    @NonNull
    @ColumnInfo(name = "event_key")
    private String eventKey;

    {
        eventKey = "";
    }

    @NonNull
    @ColumnInfo(name = "match_key")
    private String matchKey;

    {
        matchKey = "";
    }

    @NonNull
    @ColumnInfo(name = "team_key")
    private String teamKey;

    {
        teamKey = "";
    }

    @ColumnInfo(name="season_scoring_Key")
    private String seasonScoringKey;

    @Ignore
    public MatchScoring() {
    }

    public MatchScoring(
            int matchScoringKey,
            @NonNull String eventKey,
            @NonNull String matchKey,
            @NonNull String teamKey,
            @NonNull String seasonScoringKey)
    {
        this.matchScoringKey = matchScoringKey;
        this.eventKey = eventKey;
        this.matchKey = matchKey;
        this.teamKey = teamKey;
        this.seasonScoringKey = seasonScoringKey;
    }

    public int getMatchScoringKey() {
        return matchScoringKey;
    }

    public void setMatchScoringKey(int matchScoringKey) {
        this.matchScoringKey = matchScoringKey;
    }

    @NonNull
    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(@NonNull String eventKey) {
        this.eventKey = eventKey;
    }

    @NonNull
    public String getMatchKey() {
        return matchKey;
    }

    public void setMatchKey(@NonNull String matchKey) {
        this.matchKey = matchKey;
    }

    @NonNull
    public String getTeamKey() {
        return teamKey;
    }

    public void setTeamKey(@NonNull String teamKey) {
        this.teamKey = teamKey;
    }

    public String getSeasonScoringKey() {
        return seasonScoringKey;
    }

    public void setSeasonScoringKey(String seasonScoringKey) {
        this.seasonScoringKey = seasonScoringKey;
    }
}
