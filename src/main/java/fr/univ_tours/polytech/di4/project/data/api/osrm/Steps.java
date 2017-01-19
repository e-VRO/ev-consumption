package fr.univ_tours.polytech.di4.project.data.api.osrm;

/**
 * @author Archat Mathieu
 * @version 0.1
 *          This class store the path's information
 */
public class Steps {

    private Geometry geometry;
    private float duration;
    private float distance;
    private String name;

    /**
     * Get geometry object
     *
     * @return geometry object
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * Get duration
     *
     * @return duration in minute
     */
    public float getDuration() {
        return duration;
    }

    /**
     * get distance
     *
     * @return distance in meter
     */
    public float getDistance() {
        return distance;
    }

    /**
     * get name
     *
     * @return name of street
     */
    public String getName() {
        return name;
    }

}
