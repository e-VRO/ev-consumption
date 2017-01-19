import fr.univ_tours.polytech.di4.project.consumption.open_weather.IOWeatherApi;
import fr.univ_tours.polytech.di4.project.consumption.open_weather.api.ApiWeatherResponse;

/**
 * Created by Mathieu on 06/05/2016.
 */
public class IOWeatherApiTest {


    static public void main(String[] args) {
        double lat = 47.389905;
        double lon = 0.688259;
        ApiWeatherResponse apiWeatherResponse = IOWeatherApi.getWeather(lat, lon);

        System.out.println("\n\napi:");
        System.out.println("Ville : " + apiWeatherResponse.getCity().getName());

        System.out.println("Temperature 1: " + apiWeatherResponse.getList()[3].getMain().getTemp() + "°C   date : " + apiWeatherResponse.getList()[3].getDt_txt());
        System.out.println("Temperature 2: " + apiWeatherResponse.getList()[11].getMain().getTemp() + "°C   date : " + apiWeatherResponse.getList()[11].getDt_txt());
        System.out.println("Temperature 3: " + apiWeatherResponse.getList()[19].getMain().getTemp() + "°C   date : " + apiWeatherResponse.getList()[19].getDt_txt());
        System.out.println("Temperature 4: " + apiWeatherResponse.getList()[27].getMain().getTemp() + "°C   date : " + apiWeatherResponse.getList()[27].getDt_txt());
        System.out.println("Temperature 5: " + apiWeatherResponse.getList()[35].getMain().getTemp() + "°C   date : " + apiWeatherResponse.getList()[35].getDt_txt());


        System.out.println("taille list: " + apiWeatherResponse.getList().length + "   nombre de jours: " + apiWeatherResponse.getList().length / 8);


    }

}
