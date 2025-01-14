package org.hartlandrobotics.echelonFRC.database.currentGame;

import org.hartlandrobotics.echelonFRC.database.entities.MatchResult;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class CurrentGamePoints {
    public MatchResult result = null;

    public CurrentGamePoints(MatchResult result) {
        this.result = result;
    }

    public int getAutoPoints() {
        int autoPoints = 0;
        autoPoints += this.getAuto1Points();
        autoPoints += this.getAuto2Points();
        autoPoints += this.getAuto3Points();
        autoPoints += this.getAuto4Points();
        autoPoints += this.getAuto5Points();

        autoPoints += this.getAuto6Points();
        autoPoints += this.getAuto7Points();
        autoPoints += this.getAuto8Points();
        autoPoints += this.getAuto9Points();
        autoPoints += this.getAuto10Points();
        autoPoints += this.getAuto11Points();

        return autoPoints;
    }

    public int getAutoCounts() {
        int autoCounts = 0;
        autoCounts += this.getAuto1Counts();
        autoCounts += this.getAuto2Counts();
        autoCounts += this.getAuto3Counts();
        autoCounts += this.getAuto4Counts();
        autoCounts += this.getAuto5Counts();

        autoCounts += this.getAuto6Counts();
        autoCounts += this.getAuto7Counts();
        autoCounts += this.getAuto8Counts();
        autoCounts += this.getAuto9Counts();
        autoCounts += this.getAuto10Counts();
        autoCounts += this.getAuto11Counts();

        return autoCounts;
    }

    public int getAuto1Counts(){
        if( result == null ) return 0;
        return result.getAutoFlag1() ? 1:0;

    }
    public int getAuto1Points(){
        if( result == null ) return 0;
        return result.getAutoFlag1()? 3:0;
    }

    public int getAuto2Counts(){
        if( result == null ) return 0;
        return result.getAutoFlag2() ? 1:0;
    }

    public int getAuto2Points(){
        if( result == null ) return 0;
        return result.getAutoFlag2() ? 1:0;
    }

    public int getAuto3Counts(){
        if( result == null ) return 0;
        return result.getAutoFlag3() ? 1:0;
    }

    public int getAuto3Points(){
        if( result == null ) return 0;

        return result.getAutoFlag3() ? 1:0;
    }

    public int getAuto4Counts(){
        return result.getAutoFlag4() ? 1:0;
    }

    public int getAuto4Points(){
        if( result == null ) return 0;

        return result.getAutoFlag4() ? 1:0;
    }

    public int getAuto5Counts(){
        return result.getAutoFlag5() ? 1:0;
    }

    public int getAuto5Points(){
        if( result == null ) return 0;

        return result.getAutoFlag5() ? 1:0;
    }

    public int getAuto6Counts(){
        return result.getAutoInt6();
    }

    public int getAuto6Points(){
        if( result == null ) return 0;

        return result.getAutoInt6() * 3;
    }

    public int getAuto7Counts(){
        return result.getAutoInt7();
    }

    public int getAuto7Points(){
        if( result == null ) return 0;

        return result.getAutoInt7() * 4;
    }

    public int getAuto8Counts(){
        return result.getAutoInt8();
    }

    public int getAuto8Points(){
        if( result == null ) return 0;

        return result.getAutoInt8() * 6;
    }

    public int getAuto9Counts(){
        return result.getAutoInt9();
    }

    public int getAuto9Points(){
        if( result == null ) return 0;

        return result.getAutoInt9() * 7;
    }

    public int getAuto10Counts(){
        return result.getAutoInt10();
    }

    public int getAuto10Points(){
        if( result == null ) return 0;

        return result.getAutoInt10() * 6;
    }

    public int getAuto11Counts(){
        return result.getAutoInt11();
    }

    public int getAuto11Points(){
        if( result == null ) return 0;

        return result.getAutoInt11() * 4;
    }


    public int getTeleOpPoints() {

        int teleOpPoints = 0;
        teleOpPoints += this.getTeleOp6Points();
        teleOpPoints += this.getTeleOp7Points();
        teleOpPoints += this.getTeleOp8Points();
        teleOpPoints += this.getTeleOp9Points();
        teleOpPoints += this.getTeleOp10Points();
        teleOpPoints += this.getTeleOp11Points();
        return teleOpPoints;
    }

    public int getTeleOpCounts() {

        int teleOpCounts = 0;
        teleOpCounts += this.getTeleOp6Points();
        teleOpCounts += this.getTeleOp7Points();
        teleOpCounts += this.getTeleOp8Points();
        teleOpCounts += this.getTeleOp9Points();
        teleOpCounts += this.getTeleOp10Points();
        teleOpCounts += this.getTeleOp11Points();
        return teleOpCounts;
    }

    public int getTeleOp6Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt6();
    }

    public int getTeleOp6Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt6() * 2;
    }

    public int getTeleOp7Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt7();
    }

    public int getTeleOp7Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt7() * 3;
    }


    public int getTeleOp8Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt8();
    }

    public int getTeleOp8Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt8() * 4;
    }

    public int getTeleOp9Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt9();
    }

    public int getTeleOp9Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt9() * 5;
    }

    public int getTeleOp10Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt10();
    }

    public int getTeleOp10Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt10() * 6;
    }

    public int getTeleOp11Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt11();
    }

    public int getTeleOp11Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt11() * 4;
    }

    public int getTeleOp12Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt12();
    }

    public int getTeleOp12Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt12() * 4;
    }

    public int getDefenseCount(){
        if( result == null ) return 0;
        return result.getDefenseCount();
    }

    public int getEndPoints() {

        int endPoints = 0;
        endPoints += this.getEndFlag1Points();
        endPoints += this.getEndFlag2Points();
        endPoints += this.getEndFlag3Points();
        endPoints += this.getEndFlag4Points();
        endPoints += this.getEndFlag5Points();
        return endPoints;
    }

    public int getEndCounts() {
        int endCounts = 0;
        endCounts += this.getEndFlag1Counts();
        endCounts += this.getEndFlag2Counts();
        endCounts += this.getEndFlag3Counts();
        endCounts += this.getEndFlag4Counts();
        endCounts += this.getEndFlag5Counts();
        return endCounts;
    }

    public int getEndFlag1Points(){
        return result.getEndFlag1() ? 2:0;
    }

    public int getEndFlag1Counts(){
        return result.getEndFlag1() ? 1:0;
    }

    public int getEndFlag2Points(){
        return result.getEndFlag2() ? 6:0;
    }

    public int getEndFlag2Counts(){
        return result.getEndFlag2() ? 1:0;
    }

    public int getEndFlag3Points(){
        return result.getEndFlag3() ? 12:0;
    }
    public int getEndFlag3Counts(){
        return result.getEndFlag3() ? 1:0;
    }
    public int getEndFlag4Counts(){
        return result.getEndFlag4() ? 1:0;
    }

    public int getEndFlag4Points(){
        return result.getEndFlag4() ? 1:0;
    }

    public int getEndFlag5Counts(){
        return result.getEndFlag5() ? 1:0;
    }

    public int getEndFlag5Points(){
        return result.getEndFlag5() ? 1:0;
    }


}
