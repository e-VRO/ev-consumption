package fr.univ_tours.polytech.di4.project.data.api.weather;

import java.io.Serializable;

/**
 * @author Archat Mathieu
 * @version 0.1
 *          This class store city's informations
 */
public class City implements Serializable {

    private String name;
    private Coord coord;
    private String country;

    /**
     * Get name of city
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * get coordonates of city
     *
     * @return object Coord
     */
    public Coord getCoord() {
        return coord;
    }

    /**
     * Get Country of city
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

}
