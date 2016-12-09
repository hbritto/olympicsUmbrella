package com.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guian on 26/11/2016.
 */
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
}
