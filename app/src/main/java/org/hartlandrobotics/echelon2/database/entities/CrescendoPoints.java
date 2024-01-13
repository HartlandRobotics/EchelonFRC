package org.hartlandrobotics.echelon2.database.entities;

public class CrescendoPoints {
    int leaveLineAuto = 0;
    int ampNoteAuto = 0;
    int speakerNoteAuto = 0;
    int ampNoteTeleOp = 0;
    int neutralSpeakerNoteTeleOp = 0;
    int ampSpeakerNoteTeleOp = 0;
    int parkedEnd = 0;
    int onstageEnd = 0;
    int spotlitEnd = 0;

    int harmonyEnd = 0;
    int trapNoteEnd = 0;



    public CrescendoPoints(MatchResult result){
      leaveLineAuto   = result.getAuto1() == null?0:2;
        ampNoteAuto   = result.getAuto2() == null?0:2;
        speakerNoteAuto   = result.getAuto3() == null?0:5;
        ampNoteTeleOp   = result.getTeleOp1() == null?0:1;
        neutralSpeakerNoteTeleOp   = result.getTeleOp2() == null?0:2;
        ampSpeakerNoteTeleOp   = result.getTeleOp3() == null?0:5;
        parkedEnd   = result.getEnd1() == null?0:1;
        onstageEnd   = result.getEnd2() == null?0:3;
        spotlitEnd   = result.getEnd3() == null?0:1;
        harmonyEnd   = result.getEnd4() == null?0:2;
        trapNoteEnd   = result.getEnd5() == null?0:5;




    }
    public int getAuto(){
        return leaveLineAuto + ampNoteAuto + speakerNoteAuto;

    }

    public int getTeleOp(){
        return ampNoteTeleOp + neutralSpeakerNoteTeleOp + ampSpeakerNoteTeleOp;
    }

    public int getEnd(){
        return parkedEnd + onstageEnd + spotlitEnd + harmonyEnd + trapNoteEnd;
    }
}
