package fr.univ_tours.polytech.di4.project.consumption.open_street_map;

import com.google.gson.Gson;
import fr.univ_tours.polytech.di4.project.consumption.open_street_map.api.ApiOSRMResponse;
import fr.univ_tours.polytech.di4.project.data.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * @author Archat Mathieu
 * @version 0.1
 *          This class get data from osrm api
 */
public class IOOSRMApi {
    private static String startUrl = "route/v1/driving/";
    private static String endUrl = ".json?steps=true&geometries=geojson";

    /**
     * get data form osrm api
     *
     * @param properties
     * @param startLocalization like "47.36470,0.68475"
     * @param endLocalization   like "47.36470,0.68475"
     * @return apiOSRMResponse object
     */
    public static ApiOSRMResponse IOOSRMApi(Properties properties, Location startLocalization, Location endLocalization) throws IOException {
        ApiOSRMResponse apiOSRMResponse = null;
        String url;
        Gson gson = new Gson();
        url = "http://" +
                properties.getProperty("SERVER_OSRM_IP") +
                ":" + properties.getProperty("SERVER_OSRM_PORT") +
                "/" + startUrl +
                startLocalization + ";" + endLocalization +
                endUrl;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream(), Charset.forName("UTF-8")))) {
            apiOSRMResponse = gson.fromJson(br, ApiOSRMResponse.class);
        }
        return apiOSRMResponse;
    }

}



