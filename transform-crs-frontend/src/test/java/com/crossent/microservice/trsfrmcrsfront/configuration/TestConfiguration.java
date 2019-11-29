package com.crossent.microservice.trsfrmcrsfront.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestConfiguration {

    public static String QUERY_EPSG     = "EPSG5179";
    public static String QUERY_ELLIPSOID     = "wgs84";
    public static Double QUERY_X        = 958233.5;
    public static Double QUERY_Y        = 1944497.9;
    public static Double QUERY_LAT      = 37.4987986;
    public static Double QUERY_LNG      = 127.0274847;

    public static List<String> setEpsgList(){
        List<String> list = new ArrayList<String>();
        list.add("epsg5174");
        list.add("epsg5179");
        list.add("epsg5181");

        return list;
    }

    public static List<String> setEllipsoidList(){
        List<String> list = new ArrayList<String>();
        list.add("bessel");
        list.add("grs80");
        list.add("wgs84");

        return list;
    }

    public static List<String> setEpsgSpec(){
        List<String> list = new ArrayList<String>();
        list.add("+proj=tmerc");
        list.add("+lat_0=38");
        list.add("+lon_0=127.5");
        list.add("+k=0.9996");
        list.add("+x_0=1000000");
        list.add("+y_0=2000000");
        list.add("+ellps=GRS80");
        list.add("+units=m");
        list.add("+no_defs");

        return list;
    }

    public static Map<String,Object> setEllipsoidSpec(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("a",6378137);
        map.put("f",0.003352810681182319);
        return map;
    }

    public static Map<String, Object> setTwoDimCrsData(){
        Map<String,Object> map = new HashMap<>();
        map.put("epsg","epsg5179");
        map.put("x",969340.0149436432);
        map.put("y",1870408.6303107166);
        return map;
    }

    public static Map<String, Object> setThreeDimCrsData(){

        Map<String,Object> map = new HashMap<>();
        map.put("ellipsoid", "wgs84");
        map.put("lat",36.83139262627871);
        map.put("lng", 127.15617123364385);
        return map;
    }


}
