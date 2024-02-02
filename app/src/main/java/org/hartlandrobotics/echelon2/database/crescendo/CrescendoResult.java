package org.hartlandrobotics.echelon2.database.crescendo;

import org.hartlandrobotics.echelon2.database.entities.MatchResult;
import java.util.Date;

public class CrescendoResult {
    public MatchResult matchResult;
    Boolean leaveLineAuto = Boolean.FALSE;
    int ampNoteAuto = 0;
    int speakerNoteAuto = 0;
    int ampNoteTeleOp = 0;
    int neutralSpeakerNoteTeleOp = 0;
    int ampSpeakerNoteTeleOp = 0;

    int defenseCount = 0;
    Boolean endParked = Boolean.FALSE;
    Boolean endOnstage = Boolean.FALSE;
    int endSpotlight = 0;
    Boolean endHarmony = Boolean.FALSE;
    Boolean endTrapNote = Boolean.FALSE;


    public CrescendoResult(MatchResult result) {
        matchResult = result;
        leaveLineAuto = result.getAuto1() == null ? Boolean.FALSE : Boolean.TRUE;
        ampNoteAuto = result.getAuto2() == null ? 0:1;
        speakerNoteAuto = result.getAuto3() == null ? 0:1;
        ampNoteTeleOp = result.getTeleOp1() == null ? 0:1;
        neutralSpeakerNoteTeleOp = result.getTeleOp2() == null ? 0:1;
        ampSpeakerNoteTeleOp = result.getTeleOp3() == null ? 0:1;
        endParked = result.getEnd1() == null ? Boolean.FALSE : Boolean.TRUE;
        endOnstage = result.getEnd2() == null ? Boolean.FALSE : Boolean.TRUE;
        endSpotlight = result.getEnd3() == null ? 0:1;
        endHarmony = result.getEnd4() == null ? Boolean.FALSE : Boolean.TRUE;
        endTrapNote = result.getEnd5() == null ? Boolean.FALSE : Boolean.TRUE;
        defenseCount = result.getDefenseCount();

    }
    public Boolean getLeaveLineAuto(){
        return leaveLineAuto;
    }
    public void setLeaveLineAuto(boolean result){
        if(result == Boolean.FALSE){
            matchResult.setAuto1(null);
        }
        else{
            matchResult.setAuto1(new Date().toString());
        }
    }
    //dburton need to implement setter

    public int getAmpNoteAuto(){
        return ampNoteAuto;
    }
    public void setAmpNoteAuto(int result){
        matchResult.setAuto2(result > 0 ? null : new Date().toString());
    }

    public int getSpeakerNoteAuto(){
        return speakerNoteAuto;
    }
    public void setSpeakerNoteAuto(int result){
        matchResult.setAuto3(result > 0 ? null : new Date().toString());
    }

    public int getAmpNoteTeleOp(){
        return ampNoteTeleOp;
    }
    public void setAmpNoteTeleOp(int result){
        matchResult.setTeleOp1(result > 0 ? null : new Date().toString());
    }
    public int getNeutralSpeakerNoteTeleOp(){
        return neutralSpeakerNoteTeleOp;
    }
    public void setNeutralSpeakerNoteTeleOp(int result){
        matchResult.setTeleOp2(result > 0 ? null : new Date().toString());
    }
    public int getAmpSpeakerNoteTeleOp(){
        return ampSpeakerNoteTeleOp;
    }
    public void setAmpSpeakerNoteTeleOp(int result) {
        matchResult.setTeleOp3(result > 0 ? null : new Date().toString());
    }
    public Boolean getEndParked(){
        return endParked;
    }
    public void setEndParked(boolean result) {
        if (result == Boolean.FALSE) {
            matchResult.setEnd1(null);
        } else {
            matchResult.setEnd1(new Date().toString());
        }
    }
    public Boolean getEndOnstage(){
        return endOnstage;
    }
    public void setEndOnstage(boolean result) {
        if (result == Boolean.FALSE) {
            matchResult.setEnd2(null);
        } else {
            matchResult.setEnd2(new Date().toString());
        }
    }
    public int getEndSpotlight(){
        return endSpotlight;
    }
    public void setEndSpotlight(int result) {
        matchResult.setEnd3(result > 0 ? null : new Date().toString());
    }
    public int getDefenseCount(){ return defenseCount; }
    public void setDefenseCount( int defenseCount ){
        this.matchResult.setDefenseCount(defenseCount);
    }
    public Boolean getEndHarmony(){
        return endHarmony;
    }
    public void setEndHarmony(boolean result) {
        if (result == Boolean.FALSE) {
            matchResult.setEnd4(null);
        } else {
            matchResult.setEnd4(new Date().toString());
        }
    }
    public Boolean getEndTrapNote(){
        return endTrapNote;
    }
    public void setEndTrapNote(boolean result) {
        if (result == Boolean.FALSE) {
            matchResult.setEnd5(null);
        } else {
            matchResult.setEnd5(new Date().toString());
        }
    }
}
