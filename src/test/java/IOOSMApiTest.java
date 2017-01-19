import fr.univ_tours.polytech.di4.project.consumption.ConsumptionCalculation;
import fr.univ_tours.polytech.di4.project.data.ConsumptionResume;
import fr.univ_tours.polytech.di4.project.data.Location;
import fr.univ_tours.polytech.di4.project.starter.LoadProperties;


/**
 * Created by Mathieu on 27/04/2016.
 */
public class IOOSMApiTest {

    static public void main(String[] args) {

//        IOOSRMApi IOOSRMApi = new IOOSRMApi("0.673876,47.349553","0.698606,47.355586");
//        IOOSRMApi IOOSRMApi = new IOOSRMApi("0.673876,47.349553","0.898606,47.355586");
        // IOOSRMApi IOOSRMApi = new IOOSRMApi("0.689034,47.389851", "0.683930,47.366213");

        Location source = new Location("point", 0.683930, 47.366213);
        Location destination = new Location("point", 0.689034, 47.389851);
        try {
            ConsumptionCalculation consumptionCalculation = new ConsumptionCalculation(LoadProperties.getProfileProperties("nissan_leaf"));
            ConsumptionResume consumptionResume = null;


            long startTime = System.currentTimeMillis();
            consumptionResume = consumptionCalculation.getPowerConsumption(source, destination);
            System.out.println("Temps total : " + (System.currentTimeMillis() - startTime) / 60000 + "m " + (System.currentTimeMillis() - startTime) / 1000 + "secs");
            //System.out.println("\nNombre de noeuds : " + apiOSRMResponse.getRoutes()[0].getLegs()[0].getSteps().length);
            System.out.println("Duree de Trajet : " + consumptionResume.getTime() + " minutes");
            System.out.println("Distance : " + consumptionResume.getDistance() + " m");
            System.out.println("Positive Elevation : " + consumptionResume.getPositiveHeightDifference());
            System.out.println("Negative Elevation : " + consumptionResume.getNegativeHeightDifference());
            System.out.println("Consomation : " + consumptionResume.getConsumption() + " KWh/100km");
            consumptionCalculation.closeDB();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
