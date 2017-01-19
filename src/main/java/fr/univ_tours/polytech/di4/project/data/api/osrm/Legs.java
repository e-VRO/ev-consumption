package fr.univ_tours.polytech.di4.project.data.api.osrm;

/**
 * @author Archat Mathieu
 * @version 0.1
 *          This class store an object which contain the detail of path
 */
public class Legs {

    private Steps[] steps;


    /**
     * Get tab of steps
     *
     * @return tab or steps
     */
    public Steps[] getSteps() {
        return steps;
    }

}
