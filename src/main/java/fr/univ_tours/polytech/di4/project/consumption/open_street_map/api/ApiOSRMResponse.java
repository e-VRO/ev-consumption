package fr.univ_tours.polytech.di4.project.consumption.open_street_map.api;

import fr.univ_tours.polytech.di4.project.data.api.osrm.Route;

/**
 * @author Archat Mathieu
 * @version 0.1
 *          This class store OSRM api result
 */
public class ApiOSRMResponse {

    private Route[] routes;

    /**
     * get route tab
     *
     * @return route
     */
    public Route[] getRoutes() {
        return routes;
    }

    /**
     * set route
     *
     * @param routes
     */
    public void setRoutes(Route[] routes) {
        this.routes = routes;
    }
}
