package org.hartlandrobotics.echelonFRC.blueAlliance;

import org.hartlandrobotics.echelonFRC.blueAlliance.models.SyncDistrict;
import org.hartlandrobotics.echelonFRC.blueAlliance.models.SyncEvent;
import org.hartlandrobotics.echelonFRC.blueAlliance.models.SyncMatch;
import org.hartlandrobotics.echelonFRC.blueAlliance.models.SyncOpr;
import org.hartlandrobotics.echelonFRC.blueAlliance.models.SyncStatus;
import org.hartlandrobotics.echelonFRC.blueAlliance.models.SyncTeam;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("status")
    Call<SyncStatus> getStatus();

    @GET("districts/{year}")
    Call<List<SyncDistrict>> getDistrictsByYear(@Path("year") int year);

    @GET("district/{district_key}/events")
    Call<List<SyncEvent>> getEventsByDistrict(@Path("district_key") String districtKey);

    @GET("event/{event_key}")
    Call<SyncEvent> getEventByKey(@Path("event_key") String eventKey);

    @GET("event/{event_key}/teams")
    Call<List<SyncTeam>> getTeamsByEvent(@Path("event_key") String eventKey);


    @GET("event/{event_key}/matches/simple")
    Call<List<SyncMatch>> getMatchesByEvent(@Path("event_key") String eventKey);

    @GET("event/{event_key}/coprs")
    Call<List<SyncOpr>> getOprsByEvent(@Path("event_key") String eventKey);
}
