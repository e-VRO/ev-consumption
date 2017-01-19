package fr.univ_tours.polytech.di4.project.starter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Timothy Nibeaudeau
 * @version 0.1
 *          This class load properties's files
 */
public abstract class LoadProperties {
    /**
     * Get the main config from config.xml
     *
     * @return config Properties
     */
    public static Properties getConfigProperties() {
        Properties config = new Properties();
        try {
            config.loadFromXML(new FileInputStream("config.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

    /**
     * Get the profile config from folder profile
     *
     * @param profileName the name of the profile
     * @return profile properties
     * @throws IOException if profile not found
     */
    public static Properties getProfileProperties(String profileName) throws IOException {
        Properties profile = new Properties();
        profile.loadFromXML(new FileInputStream(String.format("profile/%s.xml", profileName)));
        return profile;
    }
}
