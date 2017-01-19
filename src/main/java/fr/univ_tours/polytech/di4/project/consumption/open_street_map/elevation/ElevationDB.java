package fr.univ_tours.polytech.di4.project.consumption.open_street_map.elevation;

import java.sql.*;
import java.util.Map;

/**
 * @author Nibeaudeau Timothy
 * @version 0.1
 *          This class contain all method to access the database of elevation
 */
public class ElevationDB {
    private Connection connection;

    /**
     * Constructor open a connection to the postgreSql db (postgis)
     *
     * @param IP       of the server
     * @param table    of the server
     * @param user     of the server
     * @param password of the server
     * @throws ClassNotFoundException if the driver isn't load
     * @throws SQLException           something fail in the opening of the DB
     */
    public ElevationDB(String IP, String table, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(String.format("jdbc:postgresql://%s/%s", IP, table), user, password);
    }

    /**
     * Get elevation from the Db
     *
     * @param longitude of the node
     * @param latitude  of the node
     * @return the nearest altitude otherwise 0 when elevation not found or error
     * @throws SQLException
     */
    public double getElevation(double longitude, double latitude) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                String.format("SELECT tags \n  FROM nodes ORDER BY geom <-> St_SetSRID(ST_MakePoint(%s,%s),4326) \n    asc limit 1;  ", longitude, latitude));
        ResultSet result = statement.executeQuery();

        Map<String, String> tags = null;
        if (result.next()) {
            tags = (Map<String, String>) result.getObject(1);
        }

        result.close();
        statement.close();
        if (tags != null && tags.containsKey("height")) {
            return Double.parseDouble(tags.get("height"));
        }
        return 0.0;
    }

    /**
     * Close the Db you need to close it after you use it
     *
     * @throws SQLException
     */
    public void closeDB() throws SQLException {
        if (connection != null) {
            connection.close();
        }

    }

}
