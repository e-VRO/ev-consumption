import fr.univ_tours.polytech.di4.project.consumption.open_street_map.elevation.ElevationDB;
import fr.univ_tours.polytech.di4.project.starter.LoadProperties;

import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Timothy Nibeaudeau on 30/04/2016 for BaseProject.
 */
public class ElevationDbTest {

    public static void main(String[] args) {
        Properties configProperties = LoadProperties.getConfigProperties();
        ElevationDB elevationDB = null;
        try {
            elevationDB = new ElevationDB(configProperties.getProperty("DB_IP"), configProperties.getProperty("DB_TABLE"), configProperties.getProperty("DB_LOGIN"), configProperties.getProperty("DB_PASS"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        long threadStart = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        final ElevationDB finalElevationDB = elevationDB;
        for (int i = 0; i < 800; i++) {
            final int finalI = i;
            executorService.submit(new Runnable() {
                public void run() {
                    long startTime = System.currentTimeMillis();
                    try {
                        System.out.println(finalElevationDB.getElevation(0.6865974 + ((double) finalI / 100.0), 47.3674987) + "m in " + (System.currentTimeMillis() - startTime) + " ms");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("IN " + (System.currentTimeMillis() - threadStart));
        try {
            elevationDB.closeDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
