package ru.bstu.it192.galkin.lab5;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

public class WriteDomXml {

    protected static String path = "countryList.xml";

    public void add(Country country) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document document = docBuilder.parse(path);

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
        transformer.transform(new DOMSource(document), new StreamResult(path));
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
    public void redact(Country country) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document document = docBuilder.parse(path);

        Element currentCountry = findCountry(document, country.getId());

        Country exampleCountry  = new Country(1, "name", "continent", 1, 1, "capital");
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
            transformer.transform(new DOMSource(document), new StreamResult(path));
        }
    }
    public void delete(Country country) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document document = docBuilder.parse(path);
        Element rootElement = document.getDocumentElement();

        Element currentCountry = findCountry(document, country.getId());

        if(currentCountry != null){
            rootElement.removeChild(currentCountry);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(path));
        }
    }
}
