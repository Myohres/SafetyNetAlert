package entity;

import lombok.Data;

import java.util.List;

@Data
public class FireStationEntity {
    /** Station. */
    private Long station;
    /** Address List. */
    private List<String> address;
    /**
     * Setter for station with parsing into long type.
     * @param stationInput String Station
     */
    public void setStation(final String stationInput) {
        this.station = Long.parseLong(stationInput);
    }

}
