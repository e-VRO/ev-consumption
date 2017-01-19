package fr.univ_tours.polytech.di4.project.data.api.weather;

import java.io.Serializable;

/**
 * @author Archat Mathieu
 * @version 0.1
 *          This class store main information about temperature, pressure, sea level, ground level, humidity
 */
public class Main implements Serializable {

    private double temp;
    private double temp_min;
    private double temp_max;
    private double pressure;
    private double sea_level;
    private double grnd_level;
    private double humidity;

    /**
     * Get Temperature
     *
     * @return temperature in °C
     */
    public double getTemp() {
        return temp;
    }

    /**
     * Get Min temperature
     *
     * @return temperature in °C
     */
    public double getTemp_min() {
        return temp_min;
    }

    /**
     * Get Max temperature
     *
     * @return temperature in °C
     */
    public double getTemp_max() {
        return temp_max;
    }

    /**
     * Get pressure
     *
     * @return pressure in hPa
     */
    public double getPressure() {
        return pressure;
    }

    /**
     * get sea level
     *
     * @return sea level in hPa
     */
    public double getSea_level() {
        return sea_level;
    }

    /**
     * get ground level
     *
     * @return ground level in hPa
     */
    public double getGrnd_level() {
        return grnd_level;
    }

    /**
     * get humidity
     *
     * @return humidity in %
     */
    public double getHumidity() {
        return humidity;
    }
}
