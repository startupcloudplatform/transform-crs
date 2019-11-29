package com.crossent.microservice.trsfrmcrs.controller;

import com.crossent.microservice.trsfrmcrs.configuration.TestConfiguration;
import com.crossent.microservice.trsfrmcrs.service.TransformService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TransformControllerTest {
    MockMvc mockMvc;

    @InjectMocks
    private TransformController transformController;

    @Mock
    private TransformService transformService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(transformController).build();
    }

    @Test
    public void epsgListAll() throws Exception {
        when(transformService.epsgListAll()).thenReturn(TestConfiguration.setEpsgListData());
        mockMvc.perform(
                get("/api/crs/epsg/list/all").contentType(
                        MediaType.APPLICATION_JSON
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andDo(print());
    }

    @Test
    public void ellipsoidListAll() throws Exception {
        when(transformService.ellipsoidListAll()).thenReturn(TestConfiguration.setEllipsoidListData());
        mockMvc.perform(
                get("/api/crs/ellipsoid/list/all").contentType(
                        MediaType.APPLICATION_JSON
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andDo(print());
    }


    @Test
    public void epsgSpecByCode() throws Exception {
        when(transformService.epsgSpecByCode(TestConfiguration.EPSG_CODE)).thenReturn(TestConfiguration.setEpsgSpecData());
        mockMvc.perform(
                get("/api/crs/epsg/spec?code="+TestConfiguration.EPSG_CODE).contentType(
                        MediaType.APPLICATION_JSON
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andDo(print());
    }


    @Test
    public void ellipsoidByName() throws Exception {
        when(transformService.ellipsoidByName(TestConfiguration.ELLIPSOID_NAME)).thenReturn(TestConfiguration.setEllipsoidSpecData());
        mockMvc.perform(
                get("/api/crs/ellipsoid/spec?name="+TestConfiguration.ELLIPSOID_NAME).contentType(
                        MediaType.APPLICATION_JSON
                ))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void threeDimToTwoDim() throws Exception {
        when(transformService.threeDimToTwoDim(TestConfiguration.ELLIPSOID_NAME, TestConfiguration.EPSG_CODE,
                TestConfiguration.LATITUDE,
                TestConfiguration.LONGITUDE))
                .thenReturn(TestConfiguration.setCrossentTwoDimData());
        mockMvc.perform(
                get("/api/crs/transform/xy?inputEllipsoid="+TestConfiguration.ELLIPSOID_NAME
                        +"&outputEpsg="+TestConfiguration.EPSG_CODE
                        +"&lat="+TestConfiguration.LATITUDE+"&lng="+TestConfiguration.LONGITUDE)
                        .contentType(
                        MediaType.APPLICATION_JSON
                ))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void twoDimToThreeDim() throws Exception {
        when(transformService.twoDimToThreeDim(TestConfiguration.EPSG_CODE,TestConfiguration.ELLIPSOID_NAME,
                TestConfiguration.X,
                TestConfiguration.Y))
                .thenReturn(TestConfiguration.setCrossentThreeDimData());
        mockMvc.perform(
                get("/api/crs/transform/latlng?inputEpsg="+TestConfiguration.EPSG_CODE
                        +"&outputEllipsoid="+TestConfiguration.ELLIPSOID_NAME
                        +"&x="+TestConfiguration.X+"&y="+TestConfiguration.Y)
                        .contentType(
                                MediaType.APPLICATION_JSON
                        ))
                .andExpect(status().isOk())
                .andDo(print());
    }
}