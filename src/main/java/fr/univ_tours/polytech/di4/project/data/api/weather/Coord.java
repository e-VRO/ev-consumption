package fr.univ_tours.polytech.di4.project.data.api.weather;

import java.io.Serializable;

/**
 * @author Archat Mathieu
 * @version 0.1
 *          This class store longitude and latitude
 */
public class Coord implements Serializable {
    private double lon;
    private double lat;

    /**
     * Get longitude
     *
     * @return longitude
     */
    public double getLon() {
        return lon;
    }

    /**
     * Get latitude
     *
     * @return latitude
     */
    public double getLat() {
        return lat;
    }
}
