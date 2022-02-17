package com.example.simpleparadox.listycity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is a class that keeps track of a list of city objects
 */
public class CityList {
    private List<City> cities = new ArrayList<City>();

    /**
     * This adds a city to the list, if the city already exist then it throws
     * IllegalArgumentException
     * @throws IllegalArgumentException
     *      If the city already exist in the list, this is thrown
     * @param city
     *      This is a candidate city to add
     */
    public void add(City city){
        if (cities.contains((city))){
            throw new IllegalArgumentException();
        }
        cities.add(city);
    }

    /**
     * This returns a sorted list of cities
     * @return
     *      Return the sorted list of cities
     */
    public List<City> getCities(){
        List<City> list = cities;
        Collections.sort(list);
        return list;
    }

    /**
     * When given a city, return whether or not it belongs in the list
     * @param city the city that you want to check is in the cities list
     * @return
     *      if cities list contains the city
     */
    public boolean hasCity(City city){
        return cities.contains(city);
    }

    /**
     * Check if a city is present in the list.
     * If it does then remove it from the list, if not then throw an exception
     * @param city the city to delete
     */
    public void delete(City city) {
        if (!cities.contains((city))){
            throw new IllegalArgumentException();
        }
        cities.remove(city);
    }

    /**
     * Return how many cities are in the list
     * @return number of cities
     */
    public int countCities(){
        return cities.size();
    }

}
