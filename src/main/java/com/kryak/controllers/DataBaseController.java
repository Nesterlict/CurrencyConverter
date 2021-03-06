package com.kryak.controllers;

import java.sql.*;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;

import com.kryak.stages.MainStage;

public class DataBaseController {

    public int id = getID();
    private final String url = "jdbc:mysql://localhost:3306/currencyconverter?serverTimezone=UTC";
    private final String user = "root";
    private final String password = "mysql";



    public Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (conn == null)
            System.out.println("Failed to make connection to database");

        return conn;
    }


    public void addOperation(String res, String login) {
        String query = " insert into log_files (operation_id, log_string, login)"
                + " values (?, ?, ?)";
        try (
                Connection connection = connect();
                PreparedStatement preparedStmt = connection.prepareStatement(query);
        ) {
            preparedStmt.setInt(1, id);
            preparedStmt.setString(2, res);
            preparedStmt.setString(3, login);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        id++;
    }

    public int getID() {
        String sql = "select max(operation_id) from log_files";
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery(sql)) {
            while (set.next()) {
                id = set.getInt("max(operation_id)") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void registerUser(String email, String login, String password) {
        String query = " insert into account_info (email, login, password)"
                + " values (?, ?, ?)";
        try (Connection connection = connect();
             PreparedStatement preparedStmt = connection.prepareStatement(query);
        ){
            preparedStmt.setString(1, email);
            preparedStmt.setString(2, login);
            preparedStmt.setString(3, password);
            preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void authenicateUser(String login, String password) {
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet logins = statement.executeQuery("select login from account_info");
        ){
            ArrayList<String> arrayList = new ArrayList<>();
            while (logins.next()) {
                arrayList.add(logins.getString(1));
            }
            if (arrayList.contains(login)) {
                ResultSet passwords = statement.executeQuery("SELECT password FROM account_info WHERE login = '" + login + "';");
                ArrayList<String> arrayList1 = new ArrayList();
                while (passwords.next()) {
                    arrayList1.add(passwords.getString(1));
                }
                System.out.println(Arrays.toString(arrayList1.toArray()));
                System.out.println(password);
                System.out.println(arrayList1.get(0));
                if (password.equals(arrayList1.get(0))) {
                    MainStage.auth.setText(login);
                    System.out.println("you log in as " + login);
                }
            } else {
                System.out.println("Incorrect username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
