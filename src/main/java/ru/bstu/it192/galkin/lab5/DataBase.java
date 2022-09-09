package ru.bstu.it192.galkin.lab5;

import javafx.scene.control.Alert;
import java.io.FileInputStream;
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

            Statement statement = connection.createStatement();
            statement.executeUpdate("insert countries(name, continent, area, population, capital) values (" +
                    "'"+country.getName()+"'" + ", " +
                    "'"+country.getContinent() + "'" + ", " +
                    country.getArea() + ", " +
                    country.getPopulation() + ", " +
                    "'" +country.getCapital() + "'"+")"
            );

            read();
            connection.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void redactCountry(Country country){
        try {
            connection = DriverManager.getConnection(properties.getProperty("DataBaseUrl"),
                    properties.getProperty("DataBaseUser"),
                    properties.getProperty("DataBasePassword"));

            Statement statement = connection.createStatement();
            statement.executeUpdate("update countries set " +
                    "name="+"'"+country.getName()+"'"+
                    ",continent="+"'"+country.getContinent()+"'"+
                    ",area="+country.getArea()+
                    ",population="+country.getPopulation()+
                    ",capital="+"'"+country.getCapital()+"'"+
                    " where id="+country.getId());
            read();
            connection.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void deleteCountry(Country country){
        try {
            connection = DriverManager.getConnection(properties.getProperty("DataBaseUrl"),
                    properties.getProperty("DataBaseUser"),
                    properties.getProperty("DataBasePassword"));

            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from countries where id="+country.getId());
            read();
            connection.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public boolean isExists(Country country){
        boolean isExists = false;
        int count = 0;
        while(count<countries.size() && !isExists){
            if(countries.get(count).getId() == country.getId()) isExists = true;
            count++;
        }
        return isExists;
    }
    public List<Country> getCountries(){
        return countries;
    }
}
