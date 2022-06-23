package com.example.musicapp.tool;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

public class  Sqltool {
    // Connect to your database.
    // Replace server name, username, and password with your credentials
    public void connect() {

                String connectionUrl =

                        "jdbc:sqlserver://192.168.1.100:1433;databaseName=MusicApp;integratedSecurity=false;";

                try (Connection con = DriverManager.getConnection(connectionUrl, "sa", "Zjtlaoshi1,");

                     Statement stmt = con.createStatement();) {

                    String SQL = "SELECT TOP 10 * FROM User;";

                    ResultSet rs = stmt.executeQuery(SQL);

                    while (rs.next()) {

                        System.out.println(rs.getString("u_username"));

                    }

                } catch (SQLException e) {

                    e.printStackTrace();

                }

            }

        }