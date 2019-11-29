package com.crossent.microservice.trsfrmcrs.service;

import com.crossent.microservice.trsfrmcrs.configuration.ApplicationConfig;
import com.crossent.microservice.trsfrmcrs.configuration.TestConfiguration;
import com.crossent.microservice.trsfrmcrs.dto.Epsg.Epsg;
import com.crossent.microservice.trsfrmcrs.dto.ThreeDimCrs;
import com.crossent.microservice.trsfrmcrs.dto.TwoDimCrs;
import com.crossent.microservice.trsfrmcrs.util.Ellip2Ellipsoid.Ellipsoid;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.when;

@Import(ApplicationConfig.class)
class TransformServiceTest {

    MockMvc mockMvc;

    @InjectMocks
    private TransformService transformService = new TransformService(TestConfiguration.bessel(),
            TestConfiguration.wgs84(), TestConfiguration.grs80(), TestConfiguration.epsg5174(),
            TestConfiguration.epsg5179(), TestConfiguration.epsg5181());

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(transformService).build();
    }

    @Test
    public void testEpsgSpecByCode() {
        String inputCode = "epsg5174";
        List<String> list = transformService.epsgSpecByCode(inputCode);
        assertEquals(TestConfiguration.setEpsgSpecData(), list);
    }

    @Test
    public void testEllipsoidByName() {
        String inputName = "wgs84";
        Ellipsoid ellipsoid = transformService.ellipsoidByName(inputName);
        assertEquals(ellipsoid.getA(), TestConfiguration.setEllipsoidSpecData().getA());
        assertEquals(ellipsoid.getF(), TestConfiguration.setEllipsoidSpecData().getF());
    }

    @Test
    public void testThreeDimToTwoDim() {
        Double latitude = TestConfiguration.LATITUDE;
        Double longitude = TestConfiguration.LONGITUDE;
        String ellipsoid = TestConfiguration.ELLIPSOID_NAME;
        String epsg = TestConfiguration.EPSG_CODE;

        TwoDimCrs crossentTwoDim = transformService.threeDimToTwoDim(ellipsoid, epsg, latitude, longitude);
        assertEquals(TestConfiguration.setCrossentTwoDimData().getX(),
                crossentTwoDim.getX());
        assertEquals(TestConfiguration.setCrossentTwoDimData().getY(),
                crossentTwoDim.getY());
    }

    @Test
    public void testTwoDimToThreeDim() {
        Double x = TestConfiguration.X;
        Double y = TestConfiguration.Y;
        String ellipsoid = TestConfiguration.ELLIPSOID_NAME;
        String epsg = TestConfiguration.EPSG_CODE;

        ThreeDimCrs crossentThreeDim = transformService.twoDimToThreeDim(epsg, ellipsoid, x, y);
        assertEquals(Math.round(TestConfiguration.setCrossentThreeDimData().getLat()*1000)/1000.0,
                Math.round(crossentThreeDim.getLat()*1000)/1000.0);
        assertEquals(Math.round(TestConfiguration.setCrossentThreeDimData().getLng()*1000)/1000.0,
                Math.round(crossentThreeDim.getLng()*1000)/1000.0);
    }
}