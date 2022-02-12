package org.hartlandrobotics.echelon2.TBA.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hartlandrobotics.echelon2.database.entities.Match;

public class SyncMatch {
    @JsonProperty( "match_key")
    private String matchKey;

    @JsonProperty( "match_number")
    private int matchNumber;

    @JsonProperty( "comp_level")
    private String compLevel;

    @JsonProperty( "match_time")
    private int matchTime;

    @JsonProperty( "predicted_time")
    private int predictedTime;

    @JsonProperty( "red_1_team_key")
    private String red1TeamKey;

    @JsonProperty( "red_2_team_key")
    private String red2TeamKey;

    @JsonProperty( "red_3_team_key")
    private String red3TeamKey;

    @JsonProperty( "blue_1_team_key")
    private String blue1TeamKey;

    @JsonProperty( "blue_2_team_key")
    private String blue2TeamKey;

    @JsonProperty( "blue_3_team_key")
    private String blue3TeamKey;

    public String getMatchKey() {
        return matchKey;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public String getCompLevel() {
        return compLevel;
    }

    public int getMatchTime() {
        return matchTime;
    }

    public int getPredictedTime() {
        return predictedTime;
    }

    public String getRed1TeamKey() {
        return red1TeamKey;
    }

    public String getRed2TeamKey() {
        return red2TeamKey;
    }

    public String getRed3TeamKey() {
        return red3TeamKey;
    }

    public String getBlue1TeamKey() {
        return blue1TeamKey;
    }

    public String getBlue2TeamKey() {
        return blue2TeamKey;
    }

    public String getBlue3TeamKey() {
        return blue3TeamKey;
    }

    public Match toMatch(){
        Match match = new Match(
                getMatchKey(), getMatchNumber(), getCompLevel(), getMatchTime(), getPredictedTime(), getRed1TeamKey(), getRed2TeamKey(), getRed3TeamKey(), getBlue1TeamKey(), getBlue2TeamKey(), getBlue3TeamKey());
        return match;
    }
}
