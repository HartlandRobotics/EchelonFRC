package org.hartlandrobotics.echelonFRC.blueAlliance.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.hartlandrobotics.echelonFRC.database.entities.Opr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SyncOpr {

    //@JsonProperty("totalPoints")
    private Map<String, Double> totalPoints;
    private Map<String, Double> foulPoints;

    public Map<String, Double> getTotalPoints() {
        return totalPoints;
    }
    public Map<String, Double> getFoulPoints(){
        return foulPoints;
    }

    public List<Opr> toOprs(){
        List<Opr> oprs = new ArrayList<Opr>();

        Map<String, Double> tp = getTotalPoints();
        Map<String, Double> tf = getFoulPoints();

        for(Map.Entry<String, Double> e : tp.entrySet() ){
            String teamKey = e.getKey();
            Double oprPoints = e.getValue();
            Double foulPoints = tf.get(teamKey);

            oprs.add( new Opr( teamKey, oprPoints, foulPoints ) );
        }


        return oprs;
    }

    /*
        public District toDistrict(){
        District district = new District(getKey(), getAbbreviation(), getDisplayName(), getYear());
        return district;
    }

     */
}
