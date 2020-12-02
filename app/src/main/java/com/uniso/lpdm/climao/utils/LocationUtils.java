package com.uniso.lpdm.climao.utils;

public class LocationUtils {

    private double longitude;
    private double latitude;
    private String city;
    private String code;
    private String country;
    private String street;
    private Boolean location = false;

    public String getCity() {
        return city;
    }

    public void setCity(String city) { this.city = city; }

    public Boolean getLocation() { return location; }

    public void setLocation(Boolean location) { this.location = location; }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

    public String getStreet() { return street; }

    public void setStreet(String street) { this.street = street; }
}
