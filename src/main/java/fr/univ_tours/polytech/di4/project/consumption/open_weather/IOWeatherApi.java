package fr.univ_tours.polytech.di4.project.consumption.open_weather;


import com.google.gson.Gson;
import fr.univ_tours.polytech.di4.project.consumption.open_weather.api.ApiWeatherResponse;
import fr.univ_tours.polytech.di4.project.starter.LoadProperties;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Archat Mathieu
 * @version 0.1
 *          This class get data from weather api
 */
public class IOWeatherApi {
    private static String startUrl = "http://api.openweathermap.org/data/2.5/forecast?";
    private static String apiMetric = "units=metric";
    private static String url;
    private static Map<String, Lock> lockMap = new ConcurrentHashMap<>();
    private static Properties configProperties = LoadProperties.getConfigProperties();


    /**
     * get data from weather api
     *
     * @param localizationLat
     * @param localizationLon
     * @return apiResponse from store file or from URL
     */
    public static ApiWeatherResponse getWeather(double localizationLat, double localizationLon) {
        int weekNumber;
        int dayDate;
        ApiWeatherResponse apiWeatherResponse = null;
        File weatherStore = null;
        Calendar calendar = new GregorianCalendar();
        Gson gson = new Gson();

        url = String.format("%s&APPID=%s&lat=%s&lon=%s&%s", startUrl, configProperties.getProperty("OPEN_WEATHER_API_KEY"), localizationLat, localizationLon, apiMetric);

        calendar.setTime(new Date());
        weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
        dayDate = calendar.get(Calendar.DAY_OF_MONTH);

        weatherStore = new File(String.format("weather_%d_%d_%d", weekNumber, (int) localizationLat * 10, (int) localizationLon * 10));//example of name file  "weather_12_47_6"

        /*
            Get lock or create lock on file
         */
        Lock lock = lockMap.get(weatherStore.getName());
        if (lock == null) {
            lock = new ReentrantLock();
            lockMap.put(weatherStore.getName(), lock);
        }

        lock.lock();
        if (weatherStore.exists()) {
            /*
                Read response from File
             */
            try (BufferedReader br = new BufferedReader(new FileReader(weatherStore))) {
                apiWeatherResponse = gson.fromJson(br, ApiWeatherResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            /*
                Read response from URL
             */
            String proxyHostname = configProperties.getProperty("PROXY_HOSTNAME", null);
            int proxyPort = Integer.parseInt(configProperties.getProperty("PROXY_PORT", "0"));
            Proxy proxy = Proxy.NO_PROXY;
            if (proxyHostname != null) {
                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHostname, proxyPort));
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openConnection(proxy).getInputStream(), Charset.forName("UTF-8")))) {
                apiWeatherResponse = gson.fromJson(br, ApiWeatherResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (apiWeatherResponse != null) {
                /*
                    Store response
                 */
                apiWeatherResponse.setDayDate(dayDate);
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(weatherStore))) {
                    bw.write(gson.toJson(apiWeatherResponse));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        /*
            Unlock and remove the lock for the next caller
         */
        lock.unlock();
        lockMap.remove(lock);
        return apiWeatherResponse;
    }
}
