package com.crossent.microservice.trsfrmcrsfront.controller;

import com.crossent.microservice.trsfrmcrsfront.service.TransformCrsService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RefreshScope
@RequestMapping(value="/api/crs")
public class TransformCrsController {

    private static final Logger logger = LoggerFactory.getLogger(TransformCrsController.class);

    @Autowired
    private TransformCrsService transformCrsService;

    @ApiOperation("제공하는 좌표계 목록 반환 API")
    @RequestMapping(value="/epsg/list/all", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<String> epsgListAll() {
        return transformCrsService.epsgListAll();
    }

    @ApiOperation("제공하는 타원체 목록 반환 API")
    @RequestMapping(value="/ellipsoid/list/all", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<String> ellipsoidListAll() {
        return transformCrsService.ellipsoidListAll();
    }

    @ApiOperation("제공하는 좌표계 목록 중 해당하는 좌표계의 spec 반환 API")
    @RequestMapping(value="/epsg/spec", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<String> epsgSpecByCode(@RequestParam(name="code")String code) {
        return transformCrsService.epsgSpecByCode(code);
    }

    @ApiOperation("제공하는 타원체 목록 중 해당하는 타원체의 spec 반환 API")
    @RequestMapping(value="/ellipsoid/spec", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> ellipsoidByName(@RequestParam(name="name")String name) {
        return transformCrsService.ellipsoidByName(name);
    }

    @ApiOperation("입력받은 좌표계와 x,y 값을 통해 타원체와 위경도값 반환 API")
    @RequestMapping(value="/transform/latlng", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> twoDimToThreeDim(@RequestParam(name="inputEpsg", required = false)String inputEpsg,
                                                @RequestParam(name="outputEllipsoid")String outputEllipsoid,
                                                @RequestParam(name="x")Double x,
                                                @RequestParam(name="y")Double y) {
        return transformCrsService.twoDimToThreeDim(inputEpsg, outputEllipsoid, x, y);
    }

    //excel 7번
    @ApiOperation("입력받은 타원체와 위경도 값을 통해 좌표계와 x,y값을 반환 API")
    @RequestMapping(value="/transform/xy", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> threeDimToTwoDim(@RequestParam(name="inputEllipsoid", required = false)String inputEllipsoid,
                                                @RequestParam(name="outputEpsg")String outputEpsg,
                                                @RequestParam(name="lat")Double lat,
                                                @RequestParam(name="lng")Double lng) {
        return transformCrsService.threeDimToTwoDim(inputEllipsoid, outputEpsg, lat, lng);
    }
}