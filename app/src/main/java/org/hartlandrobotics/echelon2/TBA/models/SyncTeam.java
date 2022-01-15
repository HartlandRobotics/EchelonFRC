package org.hartlandrobotics.echelon2.TBA.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SyncTeam {
    @JsonProperty("key")
    private String teamKey;

    @JsonProperty("team_number")
    private int teamNumber;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("name")
    private String name;

    @JsonProperty("school_name")
    private String schoolName;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state_prov")
    private String stateProv;

    @JsonProperty("country")
    private String country;

    @JsonProperty("address")
    private String address;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("gmaps_place_id")
    private String googleMapsPlaceId;

    @JsonProperty("gmaps_url")
    private String googleMapsUrl;

    @JsonProperty("location_name")
    private String locationName;

    @JsonProperty("website")
    private String website;

    @JsonProperty("rookie_year")
    private int rookieYear;

    @JsonProperty("motto")
    private String motto;

    public String getTeamKey() {
        return teamKey;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public String getName() {
        return name;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getCity() {
        return city;
    }

    public String getStateProv() {
        return stateProv;
    }

    public String getCountry() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getGoogleMapsPlaceId() {
        return googleMapsPlaceId;
    }

    public String getGoogleMapsUrl() {
        return googleMapsUrl;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getWebsite() {
        return website;
    }

    public int getRookieYear() {
        return rookieYear;
    }

    public String getMotto() {
        return motto;
    }

}
