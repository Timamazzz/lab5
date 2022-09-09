package ru.bstu.it192.galkin.lab5;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public Button convertButton;
    XmlFile file = new XmlFile();
    DataBase db = new DataBase();
    String mode = "xml";

    public void initialize() {
        try {
            setDisableButtons(true);

            listView.setItems(FXCollections.observableList(file.getCountries()));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void changeModeClick() {
        if(Objects.equals(mode, "xml")){
            listView.setItems(FXCollections.observableList(db.getCountries()));
            mode = "database";

            modeButton.setText("Database mode");
            convertButton.setText("Write all to the file");
            setDisableButtons(true);
        }
        else {
            listView.setItems(FXCollections.observableList(file.getCountries()));
            mode = "xml";

            modeButton.setText("Xml mode");
            convertButton.setText("Write all to the database");
            setDisableButtons(true);
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
    public void onAddButtonClick() {
        Country country = new Country(  nameTextField.getText().trim(),
                                continentTextField.getText().trim(),
                                Integer.parseInt(areaTextField.getText().trim()),
                                Integer.parseInt(populationTextField.getText().trim()),
                                capitalTextField.getText().trim());

        if(Objects.equals(mode, "xml")){
            file.addCountry(country);
            listView.setItems(FXCollections.observableList(file.getCountries()));
        }
        else {
            db.addCountry(country);
            listView.setItems(FXCollections.observableList(db.getCountries()));
        }
    }
    public void redactButtonClick() {
        if(!listView.getSelectionModel().isEmpty()){
            Country country = new Country(  listView.getSelectionModel().getSelectedItem().id,
                                    nameTextField.getText().trim(),
                                    continentTextField.getText().trim(),
                                    Integer.parseInt(areaTextField.getText().trim()),
                                    Integer.parseInt(populationTextField.getText().trim()),
                                    capitalTextField.getText().trim()
                                            );
            if(Objects.equals(mode, "xml")){
                file.redactCountry(country);
                listView.setItems(FXCollections.observableList(file.getCountries()));
            }
            else {
                db.redactCountry(country);
                listView.setItems(FXCollections.observableList(db.getCountries()));
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Enter a country");
            alert.showAndWait();
            setDisableButtons(true);
        }

    }
    public void deleteButtonClick() {
        if(!listView.getSelectionModel().isEmpty()) {
            Country country = listView.getSelectionModel().getSelectedItem();
            if(Objects.equals(mode, "xml")){
                file.deleteCountry(country);
                listView.setItems(FXCollections.observableList(file.getCountries()));
            }
            else {
                db.deleteCountry(country);
                listView.setItems(FXCollections.observableList(db.getCountries()));
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Enter a country");
            alert.showAndWait();
            setDisableButtons(true);
        }
    }
    public void filterButtonClick() {
        try {
            List<Country> result = new ArrayList<>();
            for (Country country : Objects.equals(mode, "xml")? file.getCountries() : db.getCountries()){
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
    public void ConvertButtonClick() {
        if(Objects.equals(mode, "xml")){
            for(Country country : file.getCountries()) {
                if (db.isExists(country)) {
                    db.redactCountry(country);
                } else {
                    db.addCountry(country);
                }
            }
        }
        else{
            for(Country country : db.getCountries()) {
                if (file.isExists(country)) {
                    file.redactCountry(country);
                } else {
                    file.addCountry(country);
                }
            }
        }
    }
}