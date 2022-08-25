package ru.bstu.it192.galkin.lab5;

public class Filter {
    protected String name;
    protected String continent;
    protected int area;
    protected int population;
    protected String capital;

    public Filter(String name, String continent, int area, int population, String capital){
        this.name = name;
        this.continent = continent;
        this.area = area;
        this.population = population;
        this.capital = capital;
    }
}
