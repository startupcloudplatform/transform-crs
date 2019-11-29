package com.crossent.microservice.trsfrmcrs.dto;

public class TwoDimCrs {

    private String epsg;
    private Double x;
    private Double y;

    public TwoDimCrs(String epsg, Double x, Double y) {
        this.epsg = epsg;
        this.x = x;
        this.y = y;
    }

    public String getEpsg() {
        return epsg;
    }

    public void setEpsg(String epsg) {
        this.epsg = epsg;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }


}
