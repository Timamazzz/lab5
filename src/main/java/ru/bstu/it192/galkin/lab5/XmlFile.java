package ru.bstu.it192.galkin.lab5;

import javafx.scene.control.Alert;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.List;

public class XmlFile {
    protected static String path = "D:/University/Debts/Isis/lab5/countryList.xml";
    protected List<Country> countries;
    protected int countCountries;

    XmlFile(){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            CountryHandlerSax handler = new CountryHandlerSax();
            saxParser.parse(path, handler);
            countries = handler.getResult();
            countCountries = countries.size();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void read(){
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            CountryHandlerSax handler = new CountryHandlerSax();
            saxParser.parse(path, handler);
            countries = handler.getResult();
            countCountries = countries.size();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public List<Country> getCountries(){
        return countries;
    }
}
