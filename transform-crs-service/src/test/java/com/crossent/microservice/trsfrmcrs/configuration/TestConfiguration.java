package com.crossent.microservice.trsfrmcrs.configuration;

import com.crossent.microservice.trsfrmcrs.dto.Epsg.Epsg;
import com.crossent.microservice.trsfrmcrs.dto.ThreeDimCrs;
import com.crossent.microservice.trsfrmcrs.dto.TwoDimCrs;
import com.crossent.microservice.trsfrmcrs.util.Ellip2Ellipsoid.Ellipsoid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestConfiguration {


    static public Ellipsoid bessel() {
        return new Ellipsoid(6377397.155, 1.0 / 299.152813);
    }
    public static Ellipsoid grs80() {
        return new Ellipsoid(6378137.0, 1.0 / 298.257222101);
    }
    public static Ellipsoid wgs84() {
        return new Ellipsoid(6378137.0, 1.0 / 298.257223563);
    }

    // epsg
    public static final String[] epsg5174Spec = {"+proj=tmerc", "+lat_0=38", "+lon_0=127.0028902777778",
            "+k=1", "+x_0=200000", "+y_0=500000",
            "+ellps=bessel", "+units=m","+no_defs"
    };

    public static final String[] epsg5179Spec ={"+proj=tmerc", "+lat_0=38", "+lon_0=127.5",
            "+k=0.9996", "+x_0=1000000", "+y_0=2000000",
            "+ellps=GRS80", "+units=m", "+no_defs"
    };

    public static final String[] epsg5181Spec ={"+proj=tmerc", "+lat_0=38", "+lon_0=127",
            "+k=1", "+x_0=200000", "+y_0=500000",
            "+ellps=GRS80", "+units=m", "+no_defs"
    };
    public static Epsg epsg5174() { return new Epsg("bessel", epsg5174Spec);}
    public static Epsg epsg5179() {
        return new Epsg("grs80", epsg5179Spec);
    }
    public static Epsg epsg5181() {
        return new Epsg("grs80", epsg5181Spec);
    }

// ------------------------------------------------------------------------------------------
    public static final String EPSG_CODE = "epsg5174";
    public static final Double LATITUDE = 37.5387075;
    public static final Double LONGITUDE = 126.8914192;
    public static final Double X = 190333.89982435096;
    public static final Double Y = 448500.5375905436;
    public static String ELLIPSOID_NAME = "wgs84";

    public static List<String> setEpsgListData() {
        String[] stringArray = {"epsg5174","epsg5179", "epsg5181"};
        List<String> returns = new ArrayList<>(Arrays.asList(stringArray));
        return returns;
    }

    public static List<String> setEllipsoidListData(){
        String[] stringArray = {"grs80", "wgs84", "bessel"};
        List<String> returns = new ArrayList<>(Arrays.asList(stringArray));
        return returns;
    }

    public static List<String> setEpsgSpecData() {
        String[] epsg5174Spec = {"+proj=tmerc", "+lat_0=38", "+lon_0=127.0028902777778",
                "+k=1", "+x_0=200000", "+y_0=500000",
                "+ellps=bessel", "+units=m","+no_defs"
        };
        List<String> returns = new ArrayList<String>(Arrays.asList(epsg5174Spec));
        return returns;
    }

    public static Ellipsoid setEllipsoidSpecData() {
        return new Ellipsoid(6378137.0, 1.0 / 298.257223563);
    }

    public static TwoDimCrs setCrossentTwoDimData() {
        return new TwoDimCrs(EPSG_CODE, X,Y);
    }

    public static ThreeDimCrs setCrossentThreeDimData() {
        return new ThreeDimCrs(ELLIPSOID_NAME,LATITUDE,LONGITUDE);
    }
}
