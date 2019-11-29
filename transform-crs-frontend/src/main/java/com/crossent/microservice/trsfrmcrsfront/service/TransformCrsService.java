package com.crossent.microservice.trsfrmcrsfront.service;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.*;

@Service
@RefreshScope
public class TransformCrsService {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Value("${gateway.basic.user:}")
    String user;

    @Value("${gateway.basic.password:}")
    String password;

    // spring security가 적용되어 있기 때문에 http 요청 시, header정보를 반드시 함께 전달해야함
    private HttpHeaders getHeaders(){
        String basicAuth = String.format("%s:%s", user, password);
        String base64Auth = Base64Utils.encodeToString(basicAuth.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Basic %s", base64Auth));
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        postConstruct();

        return headers;
    }

    @PostConstruct
    private void postConstruct() {
        System.out.println(">>Basic Auth user/password: " + user + "/" + password);
    }

    /**
     * 제공하는 좌표계 목록 반환 API
     */
    public List<String> epsgListAll() {

        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        URI uri = URI.create("http://apigateway/transform-crs/api/crs/epsg/list/all");

        try{
            UriComponents requestUri = UriComponentsBuilder.fromHttpUrl(String.valueOf(uri)).build().encode("UTF-8");
            uri = requestUri.toUri();
        }catch(Exception e){
            e.printStackTrace();
        }

        ResponseEntity<Object> result = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);

        return (ArrayList)result.getBody();

    }

    /**
     * 제공하는 타원체 목록 반환 API
     */
    public List<String> ellipsoidListAll() {

        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        URI uri = URI.create("http://apigateway/transform-crs/api/crs/ellipsoid/list/all");

        try{
            UriComponents requestUri = UriComponentsBuilder.fromHttpUrl(String.valueOf(uri)).build().encode("UTF-8");
            uri = requestUri.toUri();
        }catch(Exception e){
            e.printStackTrace();
        }

        ResponseEntity<Object> result = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);

        return (ArrayList)result.getBody();

    }

    /**
     * 제공하는 좌표계 목록 중 해당하는 좌표계의 spec 반환 API
     */
    public List<String> epsgSpecByCode(String code) {

        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        URI uri = URI.create("http://apigateway/transform-crs/api/crs/epsg/spec");

        try{
            UriComponents requestUri = UriComponentsBuilder.fromHttpUrl(String.valueOf(uri))
                    .queryParam("code",code)
                    .build().encode("UTF-8");
            uri = requestUri.toUri();
        }catch(Exception e){
            e.printStackTrace();
        }

        ResponseEntity<Object> result = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);

        return (ArrayList)result.getBody();
    }

    /**
     * 제공하는 타원체 목록 중 해당하는 타원체의 spec 반환 API
     */
    public Map<String, Object> ellipsoidByName(String name) {
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        URI uri = URI.create("http://apigateway/transform-crs/api/crs/ellipsoid/spec");

        try{
            UriComponents requestUri = UriComponentsBuilder.fromHttpUrl(String.valueOf(uri))
                    .queryParam("name",name)
                    .build().encode("UTF-8");
            uri = requestUri.toUri();
        }catch(Exception e){
            e.printStackTrace();
        }

        ResponseEntity<Object> result = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);

        return (Map<String,Object>)result.getBody();
    }

    /**
     * 좌표변환 front 사용
     * 입력받은 좌표계와 x,y 값을 통해 타원체와 위경도값 반환 API
     */
    public Map<String, Object> twoDimToThreeDim(String inputEpsg, String outputEllipsoid, Double x, Double y) {

        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        URI uri = URI.create("http://apigateway/transform-crs/api/crs/transform/latlng");

        try{
            UriComponents requestUri = UriComponentsBuilder.fromHttpUrl(String.valueOf(uri)).
                    queryParam("inputEpsg", inputEpsg).
                    queryParam("outputEllipsoid", outputEllipsoid).
                    queryParam("x", x).
                    queryParam("y",y).build().encode("UTF-8");
            uri = requestUri.toUri();
        }catch(Exception e){
            e.printStackTrace();
        }

        ResponseEntity<Object> result = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);

        return (Map<String, Object>)result.getBody();
    }

    /**
     * 입력받은 타원체와 위경도 값을 통해 좌표계와 x,y값을 반환 API
     */
    public Map<String, Object> threeDimToTwoDim(String inputEllipsoid, String outputEpsg, Double lat, Double lng) {

        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        URI uri = URI.create("http://apigateway/transform-crs/api/crs/transform/xy");

        try{
            UriComponents requestUri = UriComponentsBuilder.fromHttpUrl(String.valueOf(uri)).
                    queryParam("inputEllipsoid", inputEllipsoid).
                    queryParam("outputEpsg", outputEpsg).
                    queryParam("lat", lat).
                    queryParam("lng",lng).build().encode("UTF-8");
            uri = requestUri.toUri();
        }catch(Exception e){
            e.printStackTrace();
        }

        ResponseEntity<Object> result = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);

        return (Map<String, Object>)result.getBody();
    }

}
