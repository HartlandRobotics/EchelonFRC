package org.hartlandrobotics.echelon2.database.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    @NonNull
    @ColumnInfo(name = "synced")
    private boolean hasBeenSynced;

    @NonNull
    @ColumnInfo(name = "has_autonomous")
    private boolean hasAutonomous;

    @NonNull
    @ColumnInfo(name = "help_creating_auto")
    private boolean helpCreatingAuto;

    @NonNull
    @ColumnInfo(name = "coding_langauge")
    private String codingLangauge;

    @NonNull
    @ColumnInfo(name = "shoots_in_auto")
    private boolean shootsInAuto;

    @NonNull
    @ColumnInfo(name = "percent_auto_shots")
    private double percentAutoShots;

    @NonNull
    @ColumnInfo(name = "balls_picked_or_shot_in_auto")
    private int ballsPickedOrShotInAuto;

    @NonNull
    @ColumnInfo(name = "can_shoot")
    private boolean canShoot;

    @NonNull
    @ColumnInfo(name = "shooting_accuracy")
    private double shootingAccuracy;

    @NonNull
    @ColumnInfo(name = "preferred_goal")
    private String preferredGoal;

    @NonNull
    @ColumnInfo(name = "can_play_defense")
    private boolean canPlayDefense;

    @NonNull
    @ColumnInfo(name = "can_robot_hang")
    private boolean canRobotHang;

    @NonNull
    @ColumnInfo(name = "highest_hang_bar")
    private int highestHangBar;


}
