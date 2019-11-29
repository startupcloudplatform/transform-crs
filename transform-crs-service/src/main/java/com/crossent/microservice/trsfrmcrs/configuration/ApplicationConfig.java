package com.crossent.microservice.trsfrmcrs.configuration;

import com.crossent.microservice.trsfrmcrs.dto.Epsg.Epsg;
import com.crossent.microservice.trsfrmcrs.util.Ellip2Ellipsoid.Ellipsoid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    // ellipsoid
    @Bean(name="bessel")
    public Ellipsoid bessel() {
        return new Ellipsoid(6377397.155, 1.0 / 299.152813);
    }

    @Bean(name="grs80")
    public Ellipsoid grs80() {
        return new Ellipsoid(6378137.0, 1.0 / 298.257222101);
    }

    @Bean(name="wgs84")
    public Ellipsoid wgs84() {
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

    @Bean(name="epsg5174")
    public Epsg epsg5174() {
        //return new Epsg("bessel", initSpec());
        return new Epsg("bessel", epsg5174Spec);
    }

    @Bean(name="epsg5179")
    public Epsg epsg5179() {
        return new Epsg("grs80", epsg5179Spec);
    }

    @Bean(name="epsg5181")
    public Epsg epsg5181() {
        return new Epsg("grs80", epsg5181Spec);
    }
}
