package com.crossent.microservice.trsfrmcrsfront.service;

import com.crossent.microservice.trsfrmcrsfront.TrsfrmCrsFrontApplication;
import com.crossent.microservice.trsfrmcrsfront.configuration.TestConfiguration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrsfrmCrsFrontApplication.class)
public class TransformCrsServiceTest {

    MockMvc mockMvc;

    @InjectMocks
    private TransformCrsService transformCrsService;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(transformCrsService).build();
    }

    @Test
    public void epsgListAll() {

        Mockito.when(restTemplate.exchange(
                Matchers.any(),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<String>>any(),
                Matchers.any(Class.class))
        ).thenReturn(new ResponseEntity(TestConfiguration.setEpsgList(), HttpStatus.OK));
        List<String> list = transformCrsService.epsgListAll();
        Assert.assertEquals(TestConfiguration.setEpsgList(), list);
    }

    @Test
    public void ellipsoidListAll() {
        Mockito.when(restTemplate.exchange(
                Matchers.any(),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<String>>any(),
                Matchers.any(Class.class))
        ).thenReturn(new ResponseEntity(TestConfiguration.setEllipsoidList(), HttpStatus.OK));
        List<String> list = transformCrsService.ellipsoidListAll();
        Assert.assertEquals(TestConfiguration.setEllipsoidList(), list);
    }

    @Test
    public void epsgSpecByCode() {
        Mockito.when(restTemplate.exchange(
                Matchers.any(),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<String>>any(),
                Matchers.any(Class.class))
        ).thenReturn(new ResponseEntity(TestConfiguration.setEpsgSpec(), HttpStatus.OK));
        List<String> list = transformCrsService.epsgSpecByCode(TestConfiguration.QUERY_EPSG);
        Assert.assertEquals(TestConfiguration.setEpsgSpec(), list);
    }

    @Test
    public void ellipsoidByName() {
        Mockito.when(restTemplate.exchange(
                Matchers.any(),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<String>>any(),
                Matchers.any(Class.class))
        ).thenReturn(new ResponseEntity(TestConfiguration.setEllipsoidSpec(), HttpStatus.OK));
        Map<String,Object> map = transformCrsService.ellipsoidByName(TestConfiguration.QUERY_ELLIPSOID);
        Assert.assertEquals(TestConfiguration.setEllipsoidSpec(), map);
    }

    @Test
    public void twoDimToThreeDim() {
        Mockito.when(restTemplate.exchange(
                Matchers.any(),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<String>>any(),
                Matchers.any(Class.class))
        ).thenReturn(new ResponseEntity(TestConfiguration.setTwoDimCrsData(), HttpStatus.OK));
        Map<String,Object> map = transformCrsService.twoDimToThreeDim(TestConfiguration.QUERY_EPSG, TestConfiguration.QUERY_ELLIPSOID,
                TestConfiguration.QUERY_X, TestConfiguration.QUERY_Y);
        Assert.assertEquals(TestConfiguration.setTwoDimCrsData(), map);
    }

    @Test
    public void threeDimToTwoDim() {
        Mockito.when(restTemplate.exchange(
                Matchers.any(),
                Matchers.eq(HttpMethod.GET),
                Matchers.<HttpEntity<String>>any(),
                Matchers.any(Class.class))
        ).thenReturn(new ResponseEntity(TestConfiguration.setThreeDimCrsData(), HttpStatus.OK));
        Map<String, Object> map = transformCrsService.threeDimToTwoDim(TestConfiguration.QUERY_ELLIPSOID, TestConfiguration.QUERY_EPSG,
                TestConfiguration.QUERY_LAT, TestConfiguration.QUERY_LNG);
        Assert.assertEquals(TestConfiguration.setThreeDimCrsData(), map);
    }
}
