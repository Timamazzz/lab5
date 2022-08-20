package ru.bstu.it192.galkin.lab5;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

public class WriteDomXml {

    protected static String path = "D:/University/Debts/Isis/lab5/countryList.xml";

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
        nameElement.setTextContent(country.name);
        countryElement.appendChild(nameElement);

        Element continentElement = document.createElement("continent");
        continentElement.setTextContent(country.continent);
        countryElement.appendChild(continentElement);

        Element areaElement = document.createElement("area");
        areaElement.setTextContent(String.valueOf(country.area));
        countryElement.appendChild(areaElement);

        Element populationElement = document.createElement("population");
        populationElement.setTextContent(String.valueOf(country.population));
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
}
