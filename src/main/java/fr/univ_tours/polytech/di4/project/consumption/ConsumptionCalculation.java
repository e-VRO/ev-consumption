package fr.univ_tours.polytech.di4.project.consumption;

import fr.univ_tours.polytech.di4.project.consumption.open_street_map.IOOSRMApi;
import fr.univ_tours.polytech.di4.project.consumption.open_street_map.api.ApiOSRMResponse;
import fr.univ_tours.polytech.di4.project.consumption.open_street_map.elevation.ElevationDB;
import fr.univ_tours.polytech.di4.project.consumption.open_weather.IOWeatherApi;
import fr.univ_tours.polytech.di4.project.consumption.open_weather.api.ApiWeatherResponse;
import fr.univ_tours.polytech.di4.project.data.ConsumptionResume;
import fr.univ_tours.polytech.di4.project.data.Location;
import fr.univ_tours.polytech.di4.project.data.api.osrm.Steps;
import fr.univ_tours.polytech.di4.project.starter.LoadProperties;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

/**
 * @author Archat Mathieu
 * @version 0.1
 *          This class contain consumption data and some calculation
 */
public class ConsumptionCalculation {

    // ---- constants
    private double rollingResistance;
    private double aerodynamic;
    private double auxiliaries;
    private double positiveElevation;
    private double negativeElevation;
    private double temperature;

    private Properties config;


    private ElevationDB elevationDB;

    /**
     * Constructor
     *
     * @param profile of the car
     */
    public ConsumptionCalculation(Properties profile) throws SQLException, ClassNotFoundException {
        config = LoadProperties.getConfigProperties();
        rollingResistance = Double.parseDouble(profile.getProperty("ROLLING_RESISTANCE", "0.132"));
        aerodynamic = Double.parseDouble(profile.getProperty("AERODYNAMIC", "0.000005"));
        auxiliaries = Double.parseDouble(profile.getProperty("AUXILIARIES", "0.183"));
        positiveElevation = Double.parseDouble(profile.getProperty("POSITIVE_ELEVATION", "0.00308"));
        negativeElevation = Double.parseDouble(profile.getProperty("NEGATIVE_ELEVATION", "-0.00254"));
        temperature = Double.parseDouble(profile.getProperty("TEMPERATURE", "20.0"));
        elevationDB = new ElevationDB(config.getProperty("DB_IP"), config.getProperty("DB_TABLE"), config.getProperty("DB_LOGIN"), config.getProperty("DB_PASS"));
    }

    /**
     * Get the power consumption between two location
     *
     * @param source      location source
     * @param destination location destination
     * @return consumptionResume object
     * @throws Exception
     */
    public ConsumptionResume getPowerConsumption(Location source, Location destination) throws SQLException, IOException {
        ApiOSRMResponse apiOSRMResponse = IOOSRMApi.IOOSRMApi(config, source, destination);

        Steps[] nodes = apiOSRMResponse.getRoutes()[0].getLegs()[0].getSteps();

        ConsumptionResume consumptionResume = new ConsumptionResume(apiOSRMResponse.getRoutes()[0].getDuration() / 60, apiOSRMResponse.getRoutes()[0].getDistance(), source, destination);

        double temperature = importTemperature(nodes[0].getGeometry().getCoordinates()[0][1], nodes[0].getGeometry().getCoordinates()[0][0]);

        for (int index = 0; index < nodes.length - 2; index++) {

            double distance = nodes[index].getDistance() / 1000; // in meters => KM
            double time = nodes[index].getDuration() / 3600; // in seconds => hour
            double speedLimit = distance / time; // km/h
            if (Double.isNaN(speedLimit)) {
                speedLimit = 0.0;
            }
            double differenceAltitude = getDifferenceAltitude(nodes[index].getGeometry().getCoordinates());
            consumptionResume.addElevation(differenceAltitude);
            consumptionResume.addConsumption(computeConsumption(distance, time, speedLimit, differenceAltitude, temperature));

        }

        return consumptionResume;
    }

    /**
     * get difference altitude in a tab
     *
     * @param nodes float[][]
     * @return double difference altitude
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private double getDifferenceAltitude(float[][] nodes) throws SQLException {
        int maxIndex = nodes.length - 1;
        return getAltitude(nodes[maxIndex]) - getAltitude(nodes[0]);
    }

    /**
     * get node's altitude
     *
     * @param node float[]
     * @return double elevation
     * @throws SQLException
     */
    private double getAltitude(float[] node) throws SQLException {
        return elevationDB.getElevation(node[0], node[1]);
    }


    /**
     * get result of formula
     *
     * @param distance           double
     * @param time               double
     * @param speedLimit         double
     * @param differenceAltitude double
     * @return double result
     */
    private double computeConsumption(double distance, double time, double speedLimit, double differenceAltitude, double temperature) {
        differenceAltitude *= (differenceAltitude > 0) ? positiveElevation : -negativeElevation;

        return (rollingResistance * distance + aerodynamic * Math.pow(speedLimit, 2) * distance + auxiliaries * Math.abs(20 - temperature) * time + differenceAltitude);
    }

    /**
     * get Temperature from openWeatherApi
     *
     * @param lat
     * @param lon
     * @return double temperature
     */
    private double importTemperature(float lat, float lon) {
        ApiWeatherResponse apiWeatherResponse = IOWeatherApi.getWeather(lat, lon);
        if (apiWeatherResponse != null) {
            int index = (int) getTimeTemperature(apiWeatherResponse);
            return apiWeatherResponse.getList()[index].getMain().getTemp();
        }
        return 20;
    }


    /**
     * get index about hour and day for temperature
     *
     * @param apiWeatherResponse
     * @return double index Temperature
     */
    private double getTimeTemperature(ApiWeatherResponse apiWeatherResponse) {
        String time = apiWeatherResponse.getList()[0].getDt_txt();
        int fileHour = Integer.parseInt(time.substring(11, 13)); //2016-05-18 09:00:00   11-13 = '09'
        int fileDay = apiWeatherResponse.getDayDate();

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        int hourNeeded = calendar.get(Calendar.HOUR_OF_DAY);
        int dayNeeded = calendar.get(Calendar.DAY_OF_MONTH);

        int finalHour = Math.round((hourNeeded - fileHour) / 3);
        int finalDay = dayNeeded - fileDay;

        if (finalHour > 7) {
            finalHour -= 7 * (finalHour % 7);
            finalDay -= finalHour % 7;
        }
        if (finalDay > 5) {
            finalDay = 4;
        }

        return finalDay * 8 + finalHour;
    }

    public void closeDB() throws SQLException {
        elevationDB.closeDB();
    }
}