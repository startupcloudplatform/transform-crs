package com.crossent.microservice.trsfrmcrsfront.controller;

import com.crossent.microservice.trsfrmcrsfront.configuration.TestConfiguration;
import com.crossent.microservice.trsfrmcrsfront.service.TransformCrsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransformCrsControllerTest {

    MockMvc mockMvc;

    @InjectMocks
    private TransformCrsController transformCrsController;

    @Mock
    private TransformCrsService transformCrsService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(transformCrsController).build();
    }

    @Test
    public void epsgListAll() throws Exception {
       when(transformCrsService.epsgListAll()).thenReturn(TestConfiguration.setEpsgList());
       mockMvc.perform(get("/api/crs/epsg/list/all").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andDo(print())
               .andReturn();
    }

    @Test
    public void ellipsoidListAll() throws Exception {
        when(transformCrsService.ellipsoidListAll()).thenReturn(TestConfiguration.setEllipsoidList());
        mockMvc.perform(get("/api/crs/ellipsoid/list/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void epsgSpecByCode() throws Exception {
        when(transformCrsService.epsgSpecByCode(TestConfiguration.QUERY_ELLIPSOID)).thenReturn(TestConfiguration.setEpsgSpec());
        mockMvc.perform(get("/api/crs/epsg/spec?code="+TestConfiguration.QUERY_ELLIPSOID).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void ellipsoidByName() throws Exception {
        when(transformCrsService.ellipsoidByName(TestConfiguration.QUERY_EPSG)).thenReturn(TestConfiguration.setEllipsoidSpec());
        mockMvc.perform(get("/api/crs/ellipsoid/spec?name="+TestConfiguration.QUERY_EPSG).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void twoDimToThreeDim() throws Exception {
        when(transformCrsService.twoDimToThreeDim(TestConfiguration.QUERY_EPSG,TestConfiguration.QUERY_ELLIPSOID,
                TestConfiguration.QUERY_X,TestConfiguration.QUERY_Y)).thenReturn(TestConfiguration.setTwoDimCrsData());

        mockMvc.perform(get("/api/crs/transform/latlng?inputEpsg="+TestConfiguration.QUERY_EPSG+"&outputEllipsoid="+
                TestConfiguration.QUERY_ELLIPSOID+"&x="+TestConfiguration.QUERY_X+"&y="+TestConfiguration.QUERY_Y).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void threeDimToTwoDim() throws Exception {
        when(transformCrsService.threeDimToTwoDim(TestConfiguration.QUERY_ELLIPSOID,TestConfiguration.QUERY_EPSG,
                TestConfiguration.QUERY_LAT,TestConfiguration.QUERY_LNG)).thenReturn(TestConfiguration.setThreeDimCrsData());
        mockMvc.perform(get("/api/crs/transform/xy?inputEllipsoid="+TestConfiguration.QUERY_ELLIPSOID+"&outputEpsg="+
                TestConfiguration.QUERY_EPSG+"&lat="+TestConfiguration.QUERY_LAT+"&lng="+TestConfiguration.QUERY_LNG).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }
}
