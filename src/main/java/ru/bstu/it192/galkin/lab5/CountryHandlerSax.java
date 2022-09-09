package ru.bstu.it192.galkin.lab5;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.List;

public class CountryHandlerSax extends DefaultHandler{
    List<Country> result;
    Country currentCountry;
    private final StringBuilder currentValue = new StringBuilder();

    @Override
    public void startDocument() {
        result = new ArrayList<>();
    }

    public List<Country> getResult() {
        return result;
    }

    @Override
    public void startElement(
            String uri,
            String localName,
            String qName,
            Attributes attributes) {

        currentValue.setLength(0);
        if (qName.equalsIgnoreCase("Country")) {
            currentCountry = new Country();
            String id = attributes.getValue("id");
            currentCountry.setId(Integer.parseInt(id));
        }
    }

    public void endElement(String uri,
                           String localName,
                           String qName) {
        switch (qName){
            case "name":
                currentCountry.setName(currentValue.toString());
                break;
            case "continent":
                currentCountry.setContinent(currentValue.toString());
                break;
            case "area":
                currentCountry.setArea(Integer.parseInt(currentValue.toString()));
                break;
            case "population":
                currentCountry.setPopulation(Integer.parseInt(currentValue.toString()));
                break;
            case "capital":
                currentCountry.setCapital(currentValue.toString());
                break;
            case "Country":
                result.add(currentCountry);
                break;
        }
    }

    public void characters(char[] ch, int start, int length) {
        currentValue.append(ch, start, length);
    }
}
