package fr.univ_tours.polytech.di4.project.data.api.osrm;


/**
 * @author Archat Mathieu
 * @version 0.1
 *          This class store an object which contain coordinates of each node
 */
public class Geometry {

    private float[][] coordinates;  // latitude & longitude
    private String type;

    /**
     * @return type of geometry
     */
    public String getType() {
        return type;
    }

    /**
     * Get tab of coordinate
     *
     * @return tab of coordinate
     */
    public float[][] getCoordinates() {
        return coordinates;
    }


}
