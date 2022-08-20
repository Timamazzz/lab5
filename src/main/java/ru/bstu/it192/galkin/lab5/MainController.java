package ru.bstu.it192.galkin.lab5;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

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
    XmlFile file = new XmlFile();

    public void initialize() {
        try {
            listView.setItems(FXCollections.observableList(file.getCountries()));
            setDisableButtons(true);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void setDisableButtons(boolean is) {
        deleteButton.setDisable(is);
        redactButton.setDisable(is);
    }
    public void onCountryClick() {

        Country country = listView.getSelectionModel().getSelectedItem();
        idLabel.setText("id:"+country.getId());
        nameTextField.setText(country.getName());
        continentTextField.setText(country.getContinent());
        areaTextField.setText(String.valueOf(country.getArea()));
        populationTextField.setText(String.valueOf(country.getPopulation()));
        capitalTextField.setText(country.getCapital());

        setDisableButtons(false);

    }

    public void onAddButtonClick() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        WriteDomXml dom = new WriteDomXml();
        Country country = new Country(  nameTextField.getText().trim(),
                                        continentTextField.getText().trim(),
                                        Integer.parseInt(areaTextField.getText().trim()),
                                        Integer.parseInt(populationTextField.getText().trim()),
                                        capitalTextField.getText().trim());
        dom.add(country);
        file.read();
        listView.setItems(FXCollections.observableList(file.getCountries()));
    }
}