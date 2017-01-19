package fr.univ_tours.polytech.di4.project.data;


/**
 * @author Timothy Nibeaudeau
 * @version 0.1
 *          This class store the localisation, latitude, longitude and information
 */
public class Location {
    private String info;
    private double longitude;
    private double latitude;

    public Location(String info, double longitude, double latitude) {
        this.info = info;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return longitude + "," + latitude;
    }
}
