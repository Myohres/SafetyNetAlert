package entity;

import lombok.Data;

import java.util.List;

@Data
public class FireStationEntity {

    private Long station;
    private List<String> address;

    public void setStation(final String stationInput) {
        this.station = Long.parseLong(stationInput);
    }

}
