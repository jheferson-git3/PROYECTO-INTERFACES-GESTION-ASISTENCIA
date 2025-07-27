package com.instituto.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/BASEDATO";
    private static final String USER = "root"; //  tu usuario SQL
    private static final String PASS = "";     // contraseña SQL

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
