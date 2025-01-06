package org.hartlandrobotics.echelonFRC.blueAlliance.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hartlandrobotics.echelonFRC.database.entities.Team;

public class SyncTeam {
    @JsonProperty("key")
    private String teamKey;

    @JsonProperty("team_number")
    private int teamNumber;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("name")
    private String name;

    @JsonProperty("school_name")
    private String schoolName;


    public String getTeamKey() {
        return teamKey;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public String getName() {
        return name;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public Team toTeam(){
        Team team = new Team(
            getTeamKey(), getTeamNumber(), getNickname(), getName(), getSchoolName());
        return team;
    }
}


