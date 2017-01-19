package fr.univ_tours.polytech.di4.project.starter;

import java.io.File;

/**
 * @author Timothy Nibeaudeau
 * @version 0.1
 *          This class create matrix foreach file in the folder inputs
 */
public class WriteMatrixFromFolder {

    /**
     * Main start
     *
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            File folder = new File("inputs/");
            File[] listOfFiles = folder.listFiles();

            long startTimeAll = System.currentTimeMillis();

            for (File file : listOfFiles) {
                long startTime = System.currentTimeMillis();
                System.out.printf("Load : %s%n", file.getName());
                WriteMatrix.main(new String[]{args[0], file.getAbsolutePath()});
                System.out.printf("%s in %d ms%n", file.getName(), System.currentTimeMillis() - startTime);
            }

            System.out.printf("Finish in %sh%n", (double) (System.currentTimeMillis() - startTimeAll) / 1000.0 / 60.0 / 60.0);
        }
        System.err.println("You have to give the profile as only parameter");
    }
}
