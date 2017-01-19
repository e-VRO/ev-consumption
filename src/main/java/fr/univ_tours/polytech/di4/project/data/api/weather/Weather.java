package fr.univ_tours.polytech.di4.project.data.api.weather;

import java.io.Serializable;

/**
 * @author Archat Mathieu
 * @version 0.1
 *          This class store a description of weather
 */
public class Weather implements Serializable {

    private String main;
    private String description;
    private String icon;

    /**
     * get the weather like clouds, rain, snow
     *
     * @return weather
     */
    public String getMain() {
        return main;
    }

    /**
     * get weather condition
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * get weather icon id
     *
     * @return icon id
     */
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
