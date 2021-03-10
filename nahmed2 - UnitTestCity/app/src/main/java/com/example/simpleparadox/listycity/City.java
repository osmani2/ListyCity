package com.example.simpleparadox.listycity;

public class City implements Comparable<City>{
    private String city;
    private String province;

    City(String city, String province){
        this.city = city;
        this.province = province;
    }

    /**
     * Returns the name of the city
     * @return the name of the city
     */
    public String getCityName(){
        return this.city;
    }

    /**
     * Returns the name of the province with the city
     * @return the province of the city
     */
    public String getProvinceName(){
        return this.province;
    }

    @Override
    public int compareTo(City city) {
        return this.city.compareTo(city.getCityName()); //compare base copy of city against city that's passed argument
    }

    /*
     * https://stackoverflow.com/a/52317870
     * CC BY-SA 4.0
     */
    @Override
    public boolean equals(Object obj){
        if(obj==null){
            return false;
        }

        if(this.getClass()==obj.getClass()){
            City city = (City) obj;
            return this.city.equals(city.getCityName());

        }else{
            return false;
        }
    }

}
