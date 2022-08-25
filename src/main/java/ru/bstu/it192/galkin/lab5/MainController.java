package ru.bstu.it192.galkin.lab5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class MainController {
    public Label idLabel;
    public TextField nameTextField;
    public TextField continentTextField;
    public TextField areaTextField;
    public TextField populationTextField;
    public TextField capitalTextField;
    public Button addButton;
    public Button redactButton;
    public Button deleteButton;
    public ListView<Country> listView;
    public Button modeButton;
    public Button filterButton;
    XmlFile file = new XmlFile();
    String mode = "xml";

    Properties properties = new Properties();
    public void initialize() {
        try {
            listView.setItems(FXCollections.observableList(file.getCountries()));
            setDisableButtons(true);
            properties.load(new FileInputStream("config.properties"));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void changeModeClick() {
        if(Objects.equals(mode, "xml")){
            mode = "database";
            modeButton.setText("Database mode");
        }
        else {
            listView.setItems(FXCollections.observableList(file.getCountries()));
            mode = "xml";
            modeButton.setText("Xml mode");
        }
    }
    public void setDisableButtons(boolean is) {
        deleteButton.setDisable(is);
        redactButton.setDisable(is);
    }
    public void onCountryClick() {
        try {
            Country country = listView.getSelectionModel().getSelectedItem();

            idLabel.setText("Country id:"+country.getId());
            nameTextField.setText(country.getName());
            continentTextField.setText(country.getContinent());
            areaTextField.setText(String.valueOf(country.getArea()));
            populationTextField.setText(String.valueOf(country.getPopulation()));
            capitalTextField.setText(country.getCapital());

            setDisableButtons(false);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void onAddButtonClick() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        Country country = new Country(  nameTextField.getText().trim(),
                                continentTextField.getText().trim(),
                                Integer.parseInt(areaTextField.getText().trim()),
                                Integer.parseInt(populationTextField.getText().trim()),
                                capitalTextField.getText().trim());

        if(Objects.equals(mode, "xml")){
            WriteDomXml dom = new WriteDomXml();
            dom.add(country);
            file.read();
            listView.setItems(FXCollections.observableList(file.getCountries()));
        }
        else {

        }
    }
    public void redactButtonClick() throws ParserConfigurationException, IOException, TransformerException, SAXException {
        if(!listView.getSelectionModel().isEmpty()){
            Country country = new Country(  listView.getSelectionModel().getSelectedItem().id,
                                    nameTextField.getText().trim(),
                                    continentTextField.getText().trim(),
                                    Integer.parseInt(areaTextField.getText().trim()),
                                    Integer.parseInt(populationTextField.getText().trim()),
                                    capitalTextField.getText().trim()
                                            );
            if(Objects.equals(mode, "xml")){
                WriteDomXml dom = new WriteDomXml();
                dom.redact(country);
                file.read();
                listView.setItems(FXCollections.observableList(file.getCountries()));
            }
            else {

            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(properties.getProperty("messageEAC"));
            alert.showAndWait();
            setDisableButtons(true);
        }

    }
    public void deleteButtonClick() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        if(!listView.getSelectionModel().isEmpty()) {
            Country country = listView.getSelectionModel().getSelectedItem();
            if(Objects.equals(mode, "xml")){
                WriteDomXml dom = new WriteDomXml();
                dom.delete(country);
                file.read();
                listView.setItems(FXCollections.observableList(file.getCountries()));
            }
            else {

            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(properties.getProperty("messageEAC"));
            alert.showAndWait();
            setDisableButtons(true);
        }
    }

    public void filterButtonClick() {
        try {
            List<Country> result = new ArrayList<>();
            for (Country country : Objects.equals(mode, "xml")? file.getCountries() : new ArrayList<Country>()){
                boolean isName = nameTextField.getText().trim().equals(country.getName()) || nameTextField.getText().trim().isEmpty();
                boolean isContinent = continentTextField.getText().trim().equals(country.getContinent()) || continentTextField.getText().trim().isEmpty();
                boolean isArea = areaTextField.getText().trim().isEmpty() || (Integer.parseInt(areaTextField.getText().trim())) == country.getArea();
                boolean isPopulation = populationTextField.getText().trim().isEmpty() || (Integer.parseInt(populationTextField.getText().trim())) == country.getPopulation();
                boolean isCapital = capitalTextField.getText().trim().equals(country.getCapital()) || capitalTextField.getText().trim().isEmpty();

                if(isName && isContinent && isCapital && isArea && isPopulation) result.add(country);
            }
            listView.setItems(FXCollections.observableList(result));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}