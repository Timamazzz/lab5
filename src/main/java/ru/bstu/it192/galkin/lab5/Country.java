package ru.bstu.it192.galkin.lab5;

public class Country {
    protected int id;
    protected String name;
    protected String continent;
    protected int area;
    protected int population;
    protected String capital;

    public Country(){

    }
    public Country(int id, String name, String continent, int area, int population, String capital){
        this.id = id;
        this.name = name;
        this.continent = continent;
        this.area = area;
        this.population = population;
        this.capital = capital;
    }

    public Country(String name, String continent, int area, int population, String capital){
        this.name = name;
        this.continent = continent;
        this.area = area;
        this.population = population;
        this.capital = capital;
    }

    public int getId() {
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getContinent(){
        return this.continent;
    }
    public int getArea(){
        return this.area;
    }
    public int getPopulation(){
        return this.population;
    }
    public String getCapital(){
        return this.capital;
    }

    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setContinent(String continent){
        this.continent = continent;
    }
    public void setArea(int area){
        this.area = area;
    }
    public void setPopulation(int population){
        this.population = population;
    }
    public void setCapital(String capital){
        this.capital = capital;
    }

    @Override
    public String toString() {
        return "Id: " + getId() + "\n" +
                "name: " + getName() + "\n" +
                "continent: " + getContinent() + "\n" +
                "area: " + getArea() + "\n" +
                "population: " + getPopulation() + "\n" +
                "capital: " + getCapital() + "\n";
    }
}
