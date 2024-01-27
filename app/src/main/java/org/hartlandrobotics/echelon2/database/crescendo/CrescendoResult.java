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
    Boolean parkedEnd = Boolean.FALSE;
    Boolean onstageEnd = Boolean.FALSE;
    int spotlightEnd = 0;
    Boolean harmonyEnd = Boolean.FALSE;
    Boolean trapNoteEnd = Boolean.FALSE;


    public CrescendoResult(MatchResult result) {
        matchResult = result;
        leaveLineAuto = result.getAuto1() == null ? Boolean.FALSE : Boolean.TRUE;
        ampNoteAuto = result.getAuto2() == null ? 0:1;
        speakerNoteAuto = result.getAuto3() == null ? 0:1;
        ampNoteTeleOp = result.getTeleOp1() == null ? 0:1;
        neutralSpeakerNoteTeleOp = result.getTeleOp2() == null ? 0:1;
        ampSpeakerNoteTeleOp = result.getTeleOp3() == null ? 0:1;
        parkedEnd = result.getEnd1() == null ? Boolean.FALSE : Boolean.TRUE;
        onstageEnd = result.getEnd2() == null ? Boolean.FALSE : Boolean.TRUE;
        spotlightEnd = result.getEnd3() == null ? 0:1;
        harmonyEnd = result.getEnd4() == null ? Boolean.FALSE : Boolean.TRUE;
        trapNoteEnd = result.getEnd5() == null ? Boolean.FALSE : Boolean.TRUE;
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
    public Boolean getParkedEnd(){
        return parkedEnd;
    }
    public void setParkedEnd(boolean result) {
        if (result == Boolean.FALSE) {
            matchResult.setEnd1(null);
        } else {
            matchResult.setEnd1(new Date().toString());
        }
    }
    public Boolean getOnstageEnd(){
        return onstageEnd;
    }
    public void setOnstageEnd(boolean result) {
        if (result == Boolean.FALSE) {
            matchResult.setEnd2(null);
        } else {
            matchResult.setEnd2(new Date().toString());
        }
    }
    public int getSpotlightEnd(){
        return spotlightEnd;
    }
    public void setSpotlightEnd(int result) {
        matchResult.setEnd3(result > 0 ? null : new Date().toString());
    }
    public int getDefenseCount(){ return defenseCount; }
    public void setDefenseCount( int defenseCount ){
        this.matchResult.setDefenseCount(defenseCount);
    }
    public Boolean getHarmonyEnd(){
        return harmonyEnd;
    }
    public void setHarmonyEnd(boolean result) {
        if (result == Boolean.FALSE) {
            matchResult.setEnd4(null);
        } else {
            matchResult.setEnd4(new Date().toString());
        }
    }
    public Boolean getTrapNoteEnd(){
        return trapNoteEnd;
    }
    public void setTrapNoteEnd(boolean result) {
        if (result == Boolean.FALSE) {
            matchResult.setEnd5(null);
        } else {
            matchResult.setEnd5(new Date().toString());
        }
    }
}
