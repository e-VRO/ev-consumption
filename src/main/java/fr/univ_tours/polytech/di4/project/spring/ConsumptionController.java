package fr.univ_tours.polytech.di4.project.spring;

import fr.univ_tours.polytech.di4.project.consumption.ConsumptionCalculation;
import fr.univ_tours.polytech.di4.project.data.ConsumptionResume;
import fr.univ_tours.polytech.di4.project.data.Location;
import fr.univ_tours.polytech.di4.project.starter.LoadProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Timothy Nibeaudeau
 * @version 0.1
 *          Spring web Service to get consumption
 */
@RestController
public class ConsumptionController {

    /**
     * Compute consumption to destination from source
     *
     * @param source      as longitude,latitude
     * @param destination as longitude,latitude
     * @param profile     as the name of the profile in folder profile example nissan_leaf
     * @return consumptionResume
     */
    @RequestMapping("/consumption")
    public ConsumptionResume getConsumption(@RequestParam(value = "source") String source, @RequestParam(value = "destination") String destination, @RequestParam(value = "profile", defaultValue = "nissan_leaf") String profile) {
        String[] splitSource = source.split(",");
        String[] splitDestination = destination.split(",");
        Location locationSource = new Location("point", Double.parseDouble(splitSource[0]), Double.parseDouble(splitSource[1]));
        Location locationDestination = new Location("point", Double.parseDouble(splitDestination[0]), Double.parseDouble(splitDestination[1]));
        ConsumptionResume consumptionResume = null;
        try {
            ConsumptionCalculation consumptionCalculation = new ConsumptionCalculation(LoadProperties.getProfileProperties(profile));

            consumptionResume = consumptionCalculation.getPowerConsumption(locationSource, locationDestination);

            consumptionCalculation.closeDB();
        } catch (IOException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return consumptionResume;
    }
}
