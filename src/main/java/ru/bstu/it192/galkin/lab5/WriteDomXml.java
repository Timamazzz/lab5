package ru.bstu.it192.galkin.lab5;

import javafx.scene.control.Alert;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class WriteDomXml {

    Properties properties = new Properties();

    WriteDomXml(){
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }




}
