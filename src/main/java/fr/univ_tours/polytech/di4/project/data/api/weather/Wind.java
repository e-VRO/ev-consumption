package fr.univ_tours.polytech.di4.project.data.api.weather;

import java.io.Serializable;

/**
 * @author Archat Mathieu
 * @version 0.1
 *          This class store wind description
 */
public class Wind implements Serializable {

    private double speed;
    private double deg;

    /**
     * get wind speed
     *
     * @return speed in meter/sec
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * get wind direction
     *
     * @return direction in degrees
     */
    public double getDeg() {
        return deg;
    }

}
