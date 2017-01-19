package fr.univ_tours.polytech.di4.project.data;


/**
 * @author Nibeaudeau Timothy
 * @version 0.1
 *          This class store result of consumption calculation
 */
public class ConsumptionResume {
    private Location source;
    private Location destination;

    private double distance;
    private double time;


    private double positiveHeightDifference = 0;
    private double negativeHeightDifference = 0;
    private double consumption = 0;

    /**
     * Constructor
     *
     * @param time        in minute
     * @param distance    in m
     * @param source      location source
     * @param destination location destination
     */
    public ConsumptionResume(double time, double distance, Location source, Location destination) {
        this.time = time;
        this.distance = distance;
        this.source = source;
        this.destination = destination;
    }

    /**
     * get positive height difference
     *
     * @return positive difference
     */
    public double getPositiveHeightDifference() {
        return positiveHeightDifference;
    }

    /**
     * get negative height difference
     *
     * @return negative difference
     */
    public double getNegativeHeightDifference() {
        return negativeHeightDifference;
    }


    /**
     * get distance
     *
     * @return distance in meter
     */
    public double getDistance() {
        return distance;
    }

    /**
     * get time
     *
     * @return time in minute
     */
    public double getTime() {
        return time;
    }

    /**
     * Add consumption
     *
     * @param consumption
     */
    public void addConsumption(double consumption) {
        this.consumption += consumption;
    }

    public double getConsumption() {
        return consumption;
    }

    /**
     * Add HeightDifference Positive or Negative to the right var
     *
     * @param differenceAltitude between two nodes
     */
    public void addElevation(double differenceAltitude) {
        if (differenceAltitude < 0) {
            negativeHeightDifference += differenceAltitude;
        } else {
            positiveHeightDifference += differenceAltitude;
        }
    }

    public Location getSource() {
        return source;
    }

    public Location getDestination() {
        return destination;
    }
}