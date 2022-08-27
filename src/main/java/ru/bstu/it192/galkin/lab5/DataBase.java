package ru.bstu.it192.galkin.lab5;

import javafx.scene.control.Alert;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataBase {
    protected Connection connection;
    Properties properties = new Properties();
    protected List<Country> countries;

    DataBase(){
        try {
            properties.load(new FileInputStream("config.properties"));
            read();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void read() {
        try {
            connection = DriverManager.getConnection(properties.getProperty("DataBaseUrl"),
                    properties.getProperty("DataBaseUser"),
                    properties.getProperty("DataBasePassword"));

            countries = new ArrayList<>();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from countries;");

            while(resultSet.next()){

                int id = resultSet.getInt("Id");
                String name =resultSet.getString("name");
                String continent = resultSet.getString("continent");
                int area = resultSet.getInt("area");
                int population = resultSet.getInt("population");
                String capital = resultSet.getString("capital");

                Country country = new Country(id, name, continent, area, population, capital);
                countries.add(country);
                int i = 0;

            }
            connection.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void addCountry(Country country){
        try {
            connection = DriverManager.getConnection(properties.getProperty("DataBaseUrl"),
                    properties.getProperty("DataBaseUser"),
                    properties.getProperty("DataBasePassword"));

            String countryString = "'" + country.getName() + "'" + ", " +
                    "'" + country.getName() + "'" + ", " +
                    country.getArea() + ", " +
                    country.getPopulation() + ", " +
                    "'" + country.getName() + "'";

            Statement statement = connection.createStatement();
            statement.executeUpdate("insert countries(name, continent, area, population, capital) values (" + countryString + ")");


            read();
            connection.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public List<Country> getCountries(){
        return countries;
    }
}
