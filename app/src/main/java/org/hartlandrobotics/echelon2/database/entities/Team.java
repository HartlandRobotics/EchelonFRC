package org.hartlandrobotics.echelon2.database.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "team")
public class Team {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "team_key")
    private String teamKey;

    @ColumnInfo(name = "team_number")
    private int teamNumber;

    @ColumnInfo(name = "nickname")
    private String nickname;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "school_name")
    private String schoolName;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "state")
    private String state;

    @ColumnInfo(name = "country")
    private String country;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "postal_code")
    private String postalCode;

    @ColumnInfo(name = "google_maps_place_id")
    private String googleMapsPlaceId;

    @ColumnInfo(name = "google_maps_url")
    private String googleMapsUrl;

    @ColumnInfo(name = "location_name")
    private String locationName;

    @ColumnInfo(name = "website")
    private String website;

    @ColumnInfo(name = "manual_entry")
    private boolean manualEntry;

    public Team(@NonNull String teamKey, int teamNumber, String nickname, String name, String schoolName, String city, String state, String country, String address, String postalCode, String googleMapsPlaceId, String googleMapsUrl, String locationName, String website, boolean manualEntry) {
        this.teamKey = teamKey;
        this.teamNumber = teamNumber;
        this.nickname = nickname;
        this.name = name;
        this.schoolName = schoolName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.address = address;
        this.postalCode = postalCode;
        this.googleMapsPlaceId = googleMapsPlaceId;
        this.googleMapsUrl = googleMapsUrl;
        this.locationName = locationName;
        this.website = website;
        this.manualEntry = manualEntry;
    }

    @NonNull
    public String getTeamKey() {
        return teamKey;
    }

    public void setTeamKey(@NonNull String teamKey) {
        this.teamKey = teamKey;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getGoogleMapsPlaceId() {
        return googleMapsPlaceId;
    }

    public void setGoogleMapsPlaceId(String googleMapsPlaceId) {
        this.googleMapsPlaceId = googleMapsPlaceId;
    }

    public String getGoogleMapsUrl() {
        return googleMapsUrl;
    }

    public void setGoogleMapsUrl(String googleMapsUrl) {
        this.googleMapsUrl = googleMapsUrl;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isManualEntry() {
        return manualEntry;
    }

    public void setManualEntry(boolean manualEntry) {
        this.manualEntry = manualEntry;
    }
}
