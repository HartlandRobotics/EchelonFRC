package org.hartlandrobotics.echelonFRC.database.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "season_scoring")
public class SeasonScoring
{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "season_scoring_key")
    private String seasonScoringKey;
    @ColumnInfo(name = "season_name")
    private String seasonName;
    @ColumnInfo(name = "season_year")
    private int seasonYear;
    @ColumnInfo(name= "scoring_type")
    private String scoringType;
    @ColumnInfo(name= "scoring_code")
    private String scoringCode;
    @ColumnInfo(name= "scoring_description")
    private String scoringDescription;
    @ColumnInfo(name= "scoring_value")
    private int scoringValue;
    public SeasonScoring(@NonNull String seasonScoringKey,@NonNull String seasonName, int seasonYear, String scoringType, String scoringCode, String scoringDescription, int scoringValue)
    {
        this.seasonScoringKey = seasonScoringKey;
        this.scoringCode = scoringCode;
        this.seasonYear = seasonYear;
        this.seasonName = seasonName;
        this.scoringType = scoringType;
        this.scoringDescription = scoringDescription;
        this.scoringValue = scoringValue;
    }

    @NonNull
    public String getSeasonScoringKey() {
        return seasonScoringKey;
    }

    public void setSeasonScoringKey(@NonNull String seasonScoringKey) {
        this.seasonScoringKey = seasonScoringKey;
    }

    public int getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(int seasonYear) {
        this.seasonYear = seasonYear;
    }

    public String getScoringType() {
        return scoringType;
    }

    public void setScoringType(String scoringType) {
        this.scoringType = scoringType;
    }

    public String getScoringCode() {
        return scoringCode;
    }

    public void setScoringCode(String scoringCode) {
        this.scoringCode = scoringCode;
    }

    public String getScoringDescription() {
        return scoringDescription;
    }

    public void setScoringDescription(String scoringDescription) {
        this.scoringDescription = scoringDescription;
    }

    public int getScoringValue() {
        return scoringValue;
    }

    public void setScoringValue(int scoringValue) {
        this.scoringValue = scoringValue;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }
}
