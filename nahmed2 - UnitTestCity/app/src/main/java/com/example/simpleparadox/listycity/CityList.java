package com.example.simpleparadox.listycity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class keeps track of a list of city objects
 */

public class CityList {
    private List<City> cities = new ArrayList<>();

    /**
     * This adds a city to the list if the city does not exist
     * @param city candidate city to be added
     */
    public void add(City city){
        if(cities.contains(city)){
            throw new IllegalArgumentException("city already exists");
        }

        cities.add(city);
    }

    /**
     * Return sorted list of cities
     * @return the sorted list
     */
    public List<City> getCities(){
        List<City> list = cities;
        Collections.sort(list);     //need to create a way to compare cities

        return list;
    }

    /**
     * Checks if a city is in the list
     * @param city candidate city to be searched for
     * @return true if city is in list, else false
     */
    public boolean hasCity(City city){
        return cities.contains(city);
    }

    /**
     * Deletes city from list if it exists
     * @param city candidate city to be deleted
     */
    public void delete(City city){
        if(!cities.contains(city)){
            throw new IllegalArgumentException("city does not exist");
        }

        cities.remove(city);
    }

    /**
     * Return the amount of cities in the list
     * @return the size of the list of cities
     */
    public int countCities(){
        return cities.size();
    }
}
