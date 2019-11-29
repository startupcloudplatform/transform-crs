package com.crossent.microservice.trsfrmcrs.dto.Epsg;

import java.util.Arrays;

public class Epsg {
    private String ellipsoid;
    private String[] specs;

    public Epsg(String ellipsoid, String[] specs) {
        this.ellipsoid = ellipsoid;
        this.specs = specs;
    }

    public String getEllipsoid() {
        return ellipsoid;
    }

    public String[] getSpecs() {
        return specs;
    }

    @Override
    public String toString() {
        return "Epsg{" +
                "ellipsoid='" + ellipsoid + '\'' +
                ", specs=" + Arrays.toString(specs) +
                '}';
    }
}
