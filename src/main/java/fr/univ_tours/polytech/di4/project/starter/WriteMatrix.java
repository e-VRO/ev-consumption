package fr.univ_tours.polytech.di4.project.starter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.univ_tours.polytech.di4.project.consumption.ConsumptionCalculation;
import fr.univ_tours.polytech.di4.project.data.ConsumptionResume;
import fr.univ_tours.polytech.di4.project.data.Location;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Timothy Nibeaudeau
 * @version 0.1
 *          This class create a matrix using a list of node
 */
public class WriteMatrix {
    static ConsumptionResume[][] resumes;
    static Location[] locations;

    /**
     * Main to start the creation of the matrix.
     * Output a file "instance_filename.json"
     *
     * @param args [0] profile name in folder profile example nissan_leaf
     *             args [1] filename of the JSON file which contain a tab of node
     */
    public static void main(String[] args) {
        BufferedReader br = null;
        Properties profileProperties;
        String path = args[1];
        String[] temp = args[1].split("\\\\");
        String filename = temp[temp.length - 1].split("\\.")[0];
        try (FileWriter file = new FileWriter("instance_" + filename + ".json")) {
            profileProperties = LoadProperties.getProfileProperties(args[0]);
            br = new BufferedReader(new FileReader(path));
            locations = new Gson().fromJson(br, Location[].class);

            ConsumptionCalculation consumptionCalculation = new ConsumptionCalculation(profileProperties);
            resumes = new ConsumptionResume[locations.length][locations.length];

            ExecutorService executorService = Executors.newFixedThreadPool(8);//1 if you want single thread


            long startTime = System.currentTimeMillis();

            for (int i = 0; i < locations.length; i++) {
                for (int j = 0; j < locations.length; j++) {
                    ComputeTask computeTask = new ComputeTask();
                    computeTask.consumptionCalculation = consumptionCalculation;
                    computeTask.indexSource = i;
                    computeTask.indexDestination = j;
                    executorService.submit(computeTask);
                }
            }

            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

            consumptionCalculation.closeDB();
            System.out.println("in " + (System.currentTimeMillis() - startTime));

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.serializeSpecialFloatingPointValues();

            String json = gsonBuilder.create().toJson(resumes);

            file.write(json);

        } catch (IOException | ClassNotFoundException | SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This class handle the compute task
     */
    private static class ComputeTask implements Runnable {
        ConsumptionCalculation consumptionCalculation;
        int indexSource;
        int indexDestination;

        public void run() {
            try {
                resumes[indexSource][indexDestination] = consumptionCalculation.getPowerConsumption(locations[indexSource], locations[indexDestination]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
