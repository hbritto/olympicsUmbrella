package com.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class SQL {

    private Connection conn;
    private String user;

    public SQL() {
        conn = null;
    }


    public boolean connect(String user, String password) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@grad.icmc.usp.br:15215:orcl",user,password);
            this.user = user;
            return !conn.isClosed();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void disconnect() {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean insert(String table, String query, ArrayList<String> data) {

        // create a Statement from the connection
        Statement statement = null;
        try {
            statement = conn.createStatement();
            // insert the data
            statement.executeUpdate("INSERT INTO Customers " + "VALUES (1001, 'Simpson', 'Mr.', 'Springfield', 2001)");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<String> listTables() {
        ArrayList<String> results = new ArrayList<String>();
        try {
            DatabaseMetaData md = conn.getMetaData();
            String[] types = { "TABLE" };
            ResultSet rs = md.getTables(null, user, "%", types);
            while (rs.next()) {
                results.add(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public ResultSet selectTable(String table) {
        PreparedStatement statement =
                null;
            try {
            statement = conn.prepareStatement("SELECT * FROM " + table);
        return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getColumns(String table) {
        if(conn != null) {
            try {
                DatabaseMetaData meta = conn.getMetaData();
                return meta.getColumns(null, user, table,  "%");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<String> getColumnNames(String table) {
        if(conn != null) {
            ArrayList<String> results = new ArrayList<String>();

            try {
                ResultSet rs = getColumns(table);
                while (rs.next()) {
                    results.add(rs.getString(4));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return results;
        }
        return null;
    }
}
