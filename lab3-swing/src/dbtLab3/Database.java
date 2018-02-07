package dbtLab3;

import java.sql.*;
import java.util.*;

/**
 * Database is an interface to the college application database, it
 * uses JDBC to connect to a SQLite3 file.
 */
public class Database {

    /**
     * The database connection.
     */
    private Connection conn;

    /**
     * Creates the database interface object. Connection to the
     * database is performed later.
     */
    public Database() {
        conn = null;
    }

    /**
     * Opens a connection to the database, using the specified
     * filename (if we'd used a traditional DBMS, such as PostgreSQL
     * or MariaDB, we would have specified username and password
     * instead).
     */
    public boolean openConnection(String filename) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + filename);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Closes the connection to the database.
     */
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the connection to the database has been established
     * 
     * @return true if the connection has been established
     */
    public boolean isConnected() {
        return conn != null;
    }
    
    public boolean userExist(String ID){
        String query =
            "SELECT  username\n" +
            "FROM    users\n" +
            "WHERE 	 username = ?\n";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<String> getMovies(){
    	List<String> found = new LinkedList<>();
        String query =
            "SELECT  *\n" +
            "FROM    movies\n";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                found.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return found;
    }
    public List<String> getDates(String movie){
    	List<String> found = new LinkedList<>();
        String query =
            "SELECT  *\n" +
            "FROM    shows\n" + 
            "Where   movie_name = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
        	ps.setString(1, movie);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                found.add(rs.getString("show_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return found;
    }
    
    public String getTheater(String movie, String date){
    	String query =
                "SELECT  *\n" +
                "FROM    shows\n" +
                "WHERE 	 movie_name = ? AND show_date = ?\n";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, movie);
            ps.setString(2, date);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
            	return rs.getString("theater_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	return "";
    }
    public String getSeats(String movie, String date, String theater){
    	System.out.println( getTheaterSeats(theater) + " : ");
    	
    	return "hej"; //Integer.toString(getTheaterSeats(theater) - getReservatedSeats(movie, date));
    }
    
    public int getReservatedSeats(String movie, String date){
    	List<String> found = new LinkedList<>();
        String query =
        		"SELECT  *" +
                "FROM    reservations" +
                "WHERE   movie_name = ? AND show_date = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
        	ps.setString(1, movie);
        	ps.setString(2, date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                found.add(rs.getString("show_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(found);
        return found.size();
    }
    
    public int getTheaterSeats(String theater){
    	List<String> found = new LinkedList<>();
    	String query =
                "SELECT  *" +
                "FROM    theaters" + 
                "WHERE   name = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, theater);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                found.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(found);
    	return found.size();
    }

    /* ================================== */
    /* --- insert your own code below --- */
    /* ===============================*== */

    /*
    public List<...> ...(...) {
        List<...> found = new LinkedList<>();
        String query =
            "SELECT  ...\n" +
            "FROM    ...\n" +
            "...\n";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, ...);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                found.add(new ...(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return found;
    }
    */
}
