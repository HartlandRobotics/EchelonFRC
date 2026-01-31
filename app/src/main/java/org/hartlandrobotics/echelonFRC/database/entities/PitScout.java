package org.hartlandrobotics.echelonFRC.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@Entity(tableName = "pit_scout")
public class PitScout {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "pit_scout_key")
    private String pitScoutKey;

    @NonNull
    @ColumnInfo(name = "event_key")
    private String eventKey;

    @NonNull
    @ColumnInfo(name = "team_key")
    private String teamKey;

    @ColumnInfo(name = "synced")
    private boolean hasBeenSynced;

    @ColumnInfo(name = "has_autonomous")
    private boolean hasAutonomous;

    @ColumnInfo(name = "help_creating_auto")
    private boolean helpCreatingAuto;

    @ColumnInfo(name = "coding_language")
    private String codingLanguage;

    @ColumnInfo(name="robot_traverses_hump")
    private boolean traversesHump;
    @ColumnInfo(name="robot_under_trench")
    private boolean underTrench;
    @ColumnInfo(name="robot_shooter_type")
    private String shooterType;
    @ColumnInfo(name="robot_size")
    private String robotSize;
    @ColumnInfo(name="intake_size")
    private String intakeSize;
    @ColumnInfo(name="robot_intake_type")
    private int intakeType;

    @ColumnInfo(name="robot_fuel_capacity")
    private String fuelCapacity;

    @ColumnInfo(name = "points_in_auto")
    private int pointsScoredInAuto;

    @ColumnInfo(name = "balls_picked_or_shot_in_auto")
    private int ballsPickedOrShotInAuto;

    @ColumnInfo(name = "pick_off_ground")
    private boolean pickOffGround;

    @ColumnInfo(name = "off_ground_yes")
    private boolean offGroundYes;

    @ColumnInfo(name = "can_play_defense")
    private boolean canPlayDefense;

    @ColumnInfo(name = "scoring_method")
    private String scoringMethod;

    @ColumnInfo(name = "hang_time")
    private int hangTime;

    @ColumnInfo(name = "driver_experience")
    private int driverExperience;

    @ColumnInfo(name = "operator_experience")
    private int operatorExperience;

    @ColumnInfo(name = "human_position_pref")
    private double humanPositionPref;

    @ColumnInfo(name = "robot_drive_train")
    private String robotDriveTrain;

    @ColumnInfo(name = "extra_notes")
    private String extraNotes;
    
    @Ignore
    public PitScout(){
    }

    public PitScout(@NonNull String pitScoutKey, @NonNull String eventKey, @NonNull String teamKey, boolean hasBeenSynced,
                    boolean hasAutonomous, boolean helpCreatingAuto, @NonNull String codingLanguage, int pointsScoredInAuto,
                    boolean pickOffGround, boolean offGroundYes, @NonNull String scoringMethod, boolean canPlayDefense, int hangTime, int driverExperience, int operatorExperience, double humanPositionPref, @NonNull String robotDriveTrain,  @NonNull String extraNotes) {
        if(StringUtils.isBlank(pitScoutKey)){
            pitScoutKey = UUID.randomUUID().toString();
        }

        this.pitScoutKey = pitScoutKey;
        this.eventKey = eventKey;
        this.teamKey = teamKey;
        this.hasBeenSynced = hasBeenSynced;
        this.hasAutonomous = hasAutonomous;
        this.helpCreatingAuto = helpCreatingAuto;
        this.codingLanguage = codingLanguage;
        this.pointsScoredInAuto = pointsScoredInAuto;
        this.scoringMethod = scoringMethod;
        this.pickOffGround = pickOffGround;
        this.offGroundYes = offGroundYes;
        this.canPlayDefense = canPlayDefense;
        this.hangTime = hangTime;
        this.driverExperience = driverExperience;
        this.operatorExperience = operatorExperience;
        this.humanPositionPref = humanPositionPref;
        this.robotDriveTrain = robotDriveTrain;
        this.extraNotes = extraNotes;
    }

    @NonNull
    public String getPitScoutKey() {
        return pitScoutKey;
    }

    public void setPitScoutKey(@NonNull String pitScoutKey) {
        this.pitScoutKey = pitScoutKey;
    }

    @NonNull
    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(@NonNull String eventKey) {
        this.eventKey = eventKey;
    }

    @NonNull
    public String getTeamKey() {
        return teamKey;
    }

    public void setTeamKey(@NonNull String teamKey) {
        this.teamKey = teamKey;
    }

    public boolean getHasBeenSynced() {
        return hasBeenSynced;
    }

    public void setHasBeenSynced(boolean hasBeenSynced) {
        this.hasBeenSynced = hasBeenSynced;
    }

    public boolean getHasAutonomous() {
        return hasAutonomous;
    }

    public void setHasAutonomous(boolean hasAutonomous) {
        this.hasAutonomous = hasAutonomous;
    }

    public boolean getHelpCreatingAuto() {
        return helpCreatingAuto;
    }

    public void setHelpCreatingAuto(boolean helpCreatingAuto) {
        this.helpCreatingAuto = helpCreatingAuto;
    }

    @NonNull
    public String getCodingLanguage() {
        return codingLanguage;
    }

    public void setCodingLanguage(@NonNull String codingLanguage) {
        this.codingLanguage = codingLanguage;
    }


    public boolean getTraversesHump(){
        return traversesHump;
    }
    public void setTraversesHump(boolean traversesHump) {
        this.traversesHump = traversesHump;
    }

    public boolean getUnderTrench(){
        return underTrench;
    }
    public void setUnderTrench(boolean underTrench){
        this.underTrench = underTrench;
    }


    public String getShooterType(){
        return shooterType;
    }
    public void setShooterType(String shooterType){
        this.shooterType = shooterType;
    }

    public String getRobotSize(){
        return robotSize;
    }
    public void setRobotSize(String robotSize){
        this.robotSize = robotSize;
    }

    public String getIntakeSize(){
        return intakeSize;
    }
    public void setIntakeSize(String intakeSize){
        this.intakeSize = intakeSize;
    }

    public int getIntakeType(){
        return intakeType;
    }
    public void setIntakeType(int intakeType) {
        this.intakeType = intakeType;
    }

    public String getFuelCapacity(){
        return fuelCapacity;
    }
    public void setFuelCapacity(String fuelCapacity){
        this.fuelCapacity = fuelCapacity;
    }

    public int getPointsScoredInAuto() {
        return pointsScoredInAuto;
    }

    public void setPointsScoredInAuto(int pointsScoredInAuto) {
        this.pointsScoredInAuto = pointsScoredInAuto;
    }

    public int getBallsPickedOrShotInAuto() {
        return ballsPickedOrShotInAuto;
    }

    public void setBallsPickedOrShotInAuto(int ballsPickedOrShotInAuto) {
        this.ballsPickedOrShotInAuto = ballsPickedOrShotInAuto;
    }

    public void setOffGroundYes(boolean offGroundYes) {
        this.offGroundYes = offGroundYes;
    }

    public boolean getOffGroundYes() {
        return offGroundYes;
    }

    public boolean getCanPlayDefense() {
        return canPlayDefense;
    }

    public boolean getPickOffGround() {
        return offGroundYes;
    }

    public void setCanPlayDefense(boolean canPlayDefense) {
        this.canPlayDefense = canPlayDefense;
    }

    public int getHangTime() {
        return hangTime;
    }

    public void setHangTime(int hangTime) {
        this.hangTime = hangTime;
    }

    public int getDriverExperience() {
        return driverExperience;
    }

    public void setDriverExperience(int driverExperience) {
        this.driverExperience = driverExperience;
    }

    public int getOperatorExperience() {
        return operatorExperience;
    }

    public void setOperatorExperience(int operatorExperience) {
        this.operatorExperience = operatorExperience;
    }

    public double getHumanPositionPref() { return humanPositionPref; }
    public void setHumanPositionPref( double humanPositionPref ){
        this.humanPositionPref = humanPositionPref;
    }

    @NonNull
    public String getRobotDriveTrain() {return robotDriveTrain; }
    public void setRobotDriveTrain(@NonNull String robotDriveTrain) {
        this.robotDriveTrain = robotDriveTrain;
    }

    @NonNull
    public String getExtraNotes() {
        return extraNotes;
    }

    public void setExtraNotes(@NonNull String extraNotes) {
        this.extraNotes = extraNotes;
    }

    public String getScoringMethod() {
        return scoringMethod;
    }

    public void setScoringMethod(@NonNull String scoringMethod) {
        this.scoringMethod = scoringMethod;
    }
}
