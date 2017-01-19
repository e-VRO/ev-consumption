package fr.univ_tours.polytech.di4.project.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Timothy Nibeaudeau
 * @version 0.1
 *          WebService's main to start the server
 */
@SpringBootApplication
public class Server {
    /**
     * Start the server
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}
