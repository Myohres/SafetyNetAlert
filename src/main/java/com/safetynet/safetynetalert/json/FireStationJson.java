package com.safetynet.safetynetalert.json;
/**
 * Models objects FireStation.
 */
public class FireStationJson {
    /**FiresStationJson default Constructor.*/
    public FireStationJson() {
    }
    /** String Station for Station from JsonFile.*/
    private String station;
    /** String address for Station from JsonFile.*/
    private String address;
    /**
     * Getter for address FireStationJson.
     * @return String address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter for address FireStationJson.
     * @param addressInput new address for fireStationJson
     */
    public void setAddress(final String addressInput) {
        address = addressInput;
    }

    /**
     * Getter for station FireStationJson.
     * @return String station
     */
    public String getStation() {
        return station;
    }

    /**
     * Setter for station FireStation.
     * @param stationInput new station value for FireStation
     */
    public void setStation(final String stationInput) {
        station = stationInput;
    }

}
