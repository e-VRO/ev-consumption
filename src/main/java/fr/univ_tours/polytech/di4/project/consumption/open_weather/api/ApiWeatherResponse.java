package fr.univ_tours.polytech.di4.project.consumption.open_weather.api;

import fr.univ_tours.polytech.di4.project.data.api.weather.City;
import fr.univ_tours.polytech.di4.project.data.api.weather.List;

import java.io.Serializable;

/**
 * @author Archat Mathieu
 * @version 0.1
 *          This class store Weather api result
 */
public class ApiWeatherResponse implements Serializable {
    private City city;
    private List[] list;
    private int dayDate;

    /**
     * get city object
     *
     * @return City
     */
    public City getCity() {
        return city;
    }

    /**
     * get list of weather data
     *
     * @return List[]
     */
    public List[] getList() {
        return list;
    }

    /**
     * get Day
     *
     * @return int
     */
    public int getDayDate() {
        return dayDate;
    }

    /**
     * set Day
     *
     * @param dayDate
     */
    public void setDayDate(int dayDate) {
        this.dayDate = dayDate;
    }
}
