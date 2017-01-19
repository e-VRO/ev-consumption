package fr.univ_tours.polytech.di4.project.data.api.osrm;

/**
 * @author Archat Mathieu
 * @version 0.1
 *          This main class of osrm api who store legs, geometry and path's information
 */
public class Route {

    private Geometry geometry;
    private Legs[] legs;
    private float duration;
    private float distance;


    /**
     * Get Geometry
     *
     * @return geometry object
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * Get tab of Legs
     *
     * @return tab of legs
     */
    public Legs[] getLegs() {
        return legs;
    }

    /**
     * Get path's duration
     *
     * @return duration in minutes
     */
    public float getDuration() {
        return duration;
    }

    /**
     * Get path's distance
     *
     * @return distance in meter
     */
    public float getDistance() {
        return distance;
    }

}
