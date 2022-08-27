package ru.bstu.it192.galkin.lab5;

import javafx.scene.control.Alert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class XmlFile {
    Properties properties = new Properties();
    protected List<Country> countries;
    XmlFile(){
        try {
            properties.load(new FileInputStream("config.properties"));
            read();
        } catch (IOException e) {
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
            saxParser.parse(properties.getProperty("pathToXml"), handler);
            countries = handler.getResult();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public void addCountry(Country country) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document document = docBuilder.parse(properties.getProperty("pathToXml"));

            Element rootElement = document.getDocumentElement();
            int lastId = Integer.parseInt(rootElement.getAttribute("lastId"));
            lastId++;

            Element countryElement = document.createElement("Country");
            countryElement.setAttribute("id", String.valueOf(lastId));

            Element nameElement = document.createElement("name");
            nameElement.setTextContent(country.getName());
            countryElement.appendChild(nameElement);

            Element continentElement = document.createElement("continent");
            continentElement.setTextContent(country.getContinent());
            countryElement.appendChild(continentElement);

            Element areaElement = document.createElement("area");
            areaElement.setTextContent(String.valueOf(country.getArea()));
            countryElement.appendChild(areaElement);

            Element populationElement = document.createElement("population");
            populationElement.setTextContent(String.valueOf(country.getPopulation()));
            countryElement.appendChild(populationElement);

            Element capitalElement = document.createElement("capital");
            capitalElement.setTextContent(country.capital);
            countryElement.appendChild(capitalElement);

            rootElement.appendChild(countryElement);
            rootElement.setAttribute("lastId", String.valueOf(lastId));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(properties.getProperty("pathToXml")));

            read();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    public Element findCountry(Document document, int id) {
        NodeList countries = document.getElementsByTagName("Country");
        Element currentCountry = null;
        int i = 0;

        while(i < countries.getLength() && currentCountry == null){
            Node node = countries.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                if(String.valueOf(id).equals(element.getAttribute("id"))){
                    currentCountry = element;
                }
            }
            i++;
        }
        return currentCountry;
    }
    public void redactCountry(Country country) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document document = docBuilder.parse(properties.getProperty("pathToXml"));

            Element currentCountry = findCountry(document, country.getId());

            if(currentCountry != null){
                NodeList children = currentCountry.getChildNodes();

                for (int j=0; j < children.getLength(); j++){
                    Node node = children.item(j);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        switch (element.getTagName()){
                            case "name":
                                element.setTextContent(country.getName());
                                break;
                            case "continent":
                                element.setTextContent(country.getContinent());
                                break;
                            case "area":
                                element.setTextContent(String.valueOf(country.getArea()));
                                break;
                            case "population":
                                element.setTextContent(String.valueOf(country.getPopulation()));
                                break;
                            case "capital":
                                element.setTextContent(country.getCapital());
                                break;
                        }
                    }
                }

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(new DOMSource(document), new StreamResult(properties.getProperty("pathToXml")));

                read();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }
    public void deleteCountry(Country country) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document document = docBuilder.parse(properties.getProperty("pathToXml"));
            Element rootElement = document.getDocumentElement();

            Element currentCountry = findCountry(document, country.getId());

            if(currentCountry != null){
                rootElement.removeChild(currentCountry);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(new DOMSource(document), new StreamResult(properties.getProperty("pathToXml")));

                read();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Not found country");
                alert.showAndWait();
            }
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
