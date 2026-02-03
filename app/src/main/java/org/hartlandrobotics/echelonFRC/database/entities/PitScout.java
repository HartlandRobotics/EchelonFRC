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
    @ColumnInfo(name="year_key")
    private String yearKey;
    @NonNull
    @ColumnInfo(name = "event_key")
    private String eventKey;
    @NonNull
    @ColumnInfo(name = "team_key")
    private String teamKey;

    @ColumnInfo(name = "robot_drive_train")
    private String robotDriveTrain;
    @ColumnInfo(name="robot_traverses_hump")
    private boolean traversesHump;
    @ColumnInfo(name="robot_under_trench")
    private boolean underTrench;
    @ColumnInfo(name="robot_shooter_type")
    private String shooterType;
    @ColumnInfo(name="robot_size")
    private String robotSize;
    @ColumnInfo(name="robot_intake_size")
    private String intakeSize;
    @ColumnInfo(name="robot_intake_type")
    private int intakeType;

    @ColumnInfo(name="robot_fuel_capacity")
    private int fuelCapacity;
    @ColumnInfo(name="robot_ground_fuel")
    private boolean groundFuel;
    @ColumnInfo(name="robot_human_fuel")
    private boolean humanFuel;
    @ColumnInfo(name="robot_human_accuracy")
    private String humanAccuracy;
    @ColumnInfo(name="robot_additional_notes")
    private String additionalNotes;


    @ColumnInfo(name = "auto_has_autonomous")
    private boolean hasAutonomous;
    @ColumnInfo(name = "auto_help_creating_auto")
    private boolean helpCreatingAuto;
    @ColumnInfo(name = "auto_coding_language")
    private String codingLanguage;
    @ColumnInfo(name = "auto_fuel_scored")
    private int autoFuelScored;
    @ColumnInfo(name="auto_hang")
    private boolean autoHang;
    @ColumnInfo(name="auto_how_hang")
    private String autoHowHang;
    @ColumnInfo(name="auto_where_hang")
    private String autoWhereHang;
    @ColumnInfo(name="auto_center_line")
    private boolean autoCenterLine;


    @Ignore
    public PitScout(){
    }

    public PitScout(@NonNull String pitScoutKey, @NonNull String yearKey, @NonNull String eventKey, @NonNull String teamKey,
                    @NonNull String robotDriveTrain, boolean traversesHump, boolean underTrench, String shooterType, String robotSize, String intakeSize, int intakeType,
                    int fuelCapacity, boolean groundFuel, boolean humanFuel, String humanAccuracy, String additionalNotes,
                    boolean hasAutonomous, boolean helpCreatingAuto, @NonNull String codingLanguage, int autoFuelScored, boolean autoHang, String autoHowHang, String autoWhereHang, boolean autoCenterLine
                    ) {

        if(StringUtils.isBlank(pitScoutKey)){
            pitScoutKey = yearKey + "_" + eventKey + "_" + teamKey;
        }

        this.pitScoutKey = pitScoutKey;
        this.yearKey = yearKey;
        this.eventKey = eventKey;
        this.teamKey = teamKey;

        this.robotDriveTrain = robotDriveTrain;
        this.traversesHump = traversesHump;
        this.underTrench = underTrench;
        this.shooterType = shooterType;
        this.robotSize = robotSize;
        this.intakeSize = intakeSize;
        this.intakeType = intakeType;

        this.fuelCapacity = fuelCapacity;
        this.groundFuel = groundFuel;
        this.humanFuel = humanFuel;
        this.humanAccuracy = humanAccuracy;
        this.additionalNotes = additionalNotes;

        this.hasAutonomous = hasAutonomous;
        this.helpCreatingAuto = helpCreatingAuto;
        this.codingLanguage = codingLanguage;
        this.autoFuelScored = autoFuelScored;
        this.autoHang = autoHang;
        this.autoHowHang = autoHowHang;
        this.autoWhereHang = autoWhereHang;
        this.autoCenterLine = autoCenterLine;
    }

    @NonNull
    public String getPitScoutKey() {
        return pitScoutKey;
    }
    public void setPitScoutKey(@NonNull String pitScoutKey) {
        this.pitScoutKey = pitScoutKey;
    }

    public String getYearKey(){
        return yearKey;
    }
    public void setYearKey(@NonNull String yearKey){
        this.yearKey = yearKey;
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


   @NonNull
   public String getRobotDriveTrain() {return robotDriveTrain; }
    public void setRobotDriveTrain(@NonNull String robotDriveTrain) {
        this.robotDriveTrain = robotDriveTrain;
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


    public int getFuelCapacity(){
        return fuelCapacity;
    }
    public void setFuelCapacity(int fuelCapacity){
        this.fuelCapacity = fuelCapacity;
    }
    public boolean getGroundFuel(){
        return groundFuel;
    }
    public void setGroundFuel(boolean groundFuel) {
        this.groundFuel = groundFuel;
    }
    public boolean getHumanFuel(){
        return humanFuel;
    }
    public void setHumanFuel(boolean humanFuel){
        this.humanFuel = humanFuel;
    }
    public String getHumanAccuracy(){
        return humanAccuracy;
    }
    public void setHumanAccuracy(String humanAccuracy){
        this.humanAccuracy = humanAccuracy;
    }
    public String getAdditionalNotes(){
        return additionalNotes;
    }
    public void setAdditionalNotes(String additionalNotes){
        this.additionalNotes = StringUtils.defaultIfBlank(additionalNotes.trim(), StringUtils.EMPTY);
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
   public int getAutoFuelScored() {
       return autoFuelScored;
   }
    public void setAutoFuelScored (int autoFuelScored) {
        this.autoFuelScored = autoFuelScored;
    }

    public boolean getAutoHang(){
        return autoHang;
    }
    public void setAutoHang (boolean autoHangCheckbox) {
        this.autoHang = autoHangCheckbox;
    }

    public String getAutoHowHang(){
        return autoHowHang;
    }
    public void setAutoHowHang(String autoHowHang){
        this.autoHowHang = autoHowHang;
    }
    public String getAutoWhereHang(){
        return autoWhereHang;
    }
    public void setAutoWhereHang(String autoWhereHang){
        this.autoWhereHang = autoWhereHang;
    }

    public boolean getAutoCenterLine(){
        return autoCenterLine;
    }
    public void setAutoCenterLine (boolean autoCenterLine) {
        this.autoCenterLine = autoCenterLine;
    }

}
