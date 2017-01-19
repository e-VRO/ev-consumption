package fr.univ_tours.polytech.di4.project.data.api.weather;

import java.io.Serializable;

/**
 * @author Archat Mathieu
 * @version 0.1
 *          This class store city weather's information
 */
public class List implements Serializable {

    private Main main;
    private Weather[] weather;
    private Wind wind;
    private String dt_txt;

    /**
     * get main informations
     *
     * @return Main object
     */
    public Main getMain() {
        return main;
    }

    /**
     * Get weather description
     *
     * @return weather object
     */
    public Weather[] getWeather() {
        return weather;
    }

    /**
     * Get wind infomation
     *
     * @return wind object
     */
    public Wind getWind() {
        return wind;
    }

    /**
     * Get data time
     *
     * @return time : "yyyy-MM-dd hh:mm:ss"
     */
    public String getDt_txt() {
        return dt_txt;
    }

}
