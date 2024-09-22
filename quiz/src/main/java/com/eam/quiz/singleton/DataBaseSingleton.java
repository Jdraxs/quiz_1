package com.eam.quiz.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author JuanAbarka
 */
public class DataBaseSingleton {

    //Self Declaration Of The Singleton
    private static DataBaseSingleton instance;
    private Connection connection;

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // Name Of The Database
    private static final String DATABASE = "contenedores";

    // Host
    private static final String HOSTNAME = "localhost";

    // Port
    private static final String PORT = "3306";

    // Username
    private static final String USER = "root";

    // User Password
    private static final String PASSWORD = "root";

    // Rute Of The Database (The Use Of SSL Is Deactivated With "?useSSL=false")
    private static final String DATABASE_URL = "jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" + DATABASE + "?useSSL=false";

    //Constructor Of The Singleton
    private DataBaseSingleton() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Getter
    public static DataBaseSingleton getInstance() {
        if (instance == null){
            instance = new DataBaseSingleton();
        }
        return instance;
    }

    //Get Of The Connection
    public Connection getConnection() {
        return connection;
    }

    //Closer Of The Conection
    public void closeConnection() {
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No Se Ha Podido Establecer Una Conexion A La Base De Datos");
        }
    }
}