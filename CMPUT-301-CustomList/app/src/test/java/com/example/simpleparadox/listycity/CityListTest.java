package com.example.simpleparadox.listycity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CityListTest {
    private CityList mockCityList() {
        CityList cityList = new CityList();
        cityList.add(mockCity());
        return cityList;
    }

    private City mockCity() {
        return new City("Edmonton", "Alberta");
    }

    @Test
    void testAdd(){
        CityList cityList = mockCityList();

        assertEquals(1, cityList.getCities().size());

        City city = new City("Regina","Saskatchewan");
        cityList.add(city);

        assertEquals(2, cityList.getCities().size());
        assertTrue(cityList.getCities().contains((city)));
    }

    @Test
    void testAddException(){
        CityList cityList = mockCityList();
        City city = new City("Yellowknife","NWT");
        cityList.add(city);

        assertThrows(IllegalArgumentException.class, ()->{
            cityList.add(city);
        });
    }

    @Test
    void testGet(){
        CityList cityList = mockCityList();

        assertEquals(0, mockCity().compareTo(cityList.getCities().get(0)));

        City city = new City("Charlottetown","PEI");
        cityList.add(city);

        assertEquals(0, city.compareTo(cityList.getCities().get(0)));
        assertEquals(0, mockCity().compareTo(cityList.getCities().get(1)));
    }

    /**
     * Test to see whether your method is correct
     */
   @Test
    void testHasCity(){
       CityList cityList = mockCityList();
       // Should contain the mock city
       assertTrue(cityList.hasCity(mockCity()));

       // Should not contain this random city
       City city = new City("Toronto","Ontario");
       assertFalse(cityList.hasCity(city));
   }

    /**
     * Test to see if your method actually removes it from the list
     * Test to see if the exception is actually thrown
     */
    @Test
    void testDelete(){
        CityList cityList = mockCityList();

        // If city does not already exist, throw an exception
        assertThrows(IllegalArgumentException.class, ()->{
            City city = new City("Yellowknife","NWT");
            cityList.delete(city);
        });

        cityList.delete(mockCity());
        assertFalse(cityList.hasCity(mockCity()));
        assertEquals(0, cityList.getCities().size());
    }

    /**
     * Test to see whether your method is correct
     */
    @Test
    void testCountCities(){
        CityList cityList = mockCityList();

        assertEquals(1, cityList.countCities());
    }
}
