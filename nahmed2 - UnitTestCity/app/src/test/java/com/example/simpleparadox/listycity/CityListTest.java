package com.example.simpleparadox.listycity;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CityListTest {

    private CityList mockCityList(){
        CityList cityList = new CityList();
        cityList.add(mockCity());
        return cityList;
    }

    private City mockCity(){
        return new City("Edmonton","Alberta");
    }

    @Test
    void testAdd(){
        CityList cityList = mockCityList();
        assertEquals(1, cityList.getCities().size());

        City city = new City("Regina", "Saskatchewan");
        cityList.add(city);
        assertEquals(2, cityList.getCities().size());

        assertTrue(cityList.getCities().contains(city));
    }

    @Test
    void testAddException(){
        CityList cityList = mockCityList();
        City city = new City("Yellowknife", "Northwest Territories");
        cityList.add(city);

        assertThrows(IllegalArgumentException.class, () -> {
            cityList.add(city);
        });
    }

    @Test
    void testGetCities(){
        CityList cityList = mockCityList();
        assertEquals(0,mockCity().compareTo(cityList.getCities().get(0)));  //city at beginning of mockCity list is equal to city at start of my mockCity list

        City city = new City("Charlottetown", "Prince Edward Island");
        cityList.add(city);

        assertEquals(0,city.compareTo(cityList.getCities().get(0))); //list is sorted, so new start should be this new city
        assertEquals(0,mockCity().compareTo(cityList.getCities().get(1))); //check if second item matches
    }

    @Test
    void testHasCity(){
        CityList cityList = mockCityList();
        boolean result = cityList.hasCity(mockCity());
        assertEquals(true,result);

        City city = new City("Vancouver", "British Columbia");
        result = cityList.hasCity(city);
        assertEquals(false,result);
    }

    @Test
    void testDeleteCity(){
        CityList cityList = mockCityList();
        assertEquals(1,cityList.getCities().size());

        cityList.delete(mockCity());
        assertEquals(0,cityList.getCities().size());

        assertFalse(cityList.getCities().contains(mockCity()));
    }

    @Test
    void testDeleteException(){
        CityList cityList = mockCityList();
        City city = new City("Yellowknife", "Northwest Territories");

        assertThrows(IllegalArgumentException.class, () -> {
            cityList.delete(city);
        });
    }

    @Test
    void testCountCities(){
        CityList cityList = mockCityList();
        assertEquals(cityList.countCities(),cityList.getCities().size());
    }


}
