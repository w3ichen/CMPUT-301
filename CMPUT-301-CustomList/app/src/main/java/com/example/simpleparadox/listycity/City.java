package com.example.simpleparadox.listycity;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

/**
 * City class
 * Attributes: city and province
 */
public class City implements Comparable<City> {
    private String city;
    private String province;

    /**
     * Create new City constructor
     * @param city city name
     * @param province province name
     */
    City(String city, String province) {
        this.city = city;
        this.province = province;
    }

    /**
     * Get name of city
     * @return city name string
     */
    String getCityName() {
        return this.city;
    }

    /**
     * Get name of province
     * @return province name string
     */
    String getProvinceName() {
        return this.province;
    }

    /**
     * Compare two cities using the city name
     * @param city the other city to compare to self
     * @return integer of comparison
     */
    @Override
    public int compareTo(City city) {
        return this.city.compareTo(city.getCityName());
    }

    /**
     * Whether two objects are equal.
     * Required for contains() to work
     * @param o object to equate
     * @return true or false on whether two objects are equal
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city1 = (City) o;
        return Objects.equals(city, city1.city) && Objects.equals(province, city1.province);
    }

    /**
     * Hash code of object
     * @return unique hash code generated from city and province attributes
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(city, province);
    }
}
