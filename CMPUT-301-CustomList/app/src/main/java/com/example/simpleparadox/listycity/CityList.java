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
}
