package com.crossent.microservice.trsfrmcrs.controller;

import com.crossent.microservice.trsfrmcrs.dto.ThreeDimCrs;
import com.crossent.microservice.trsfrmcrs.dto.TwoDimCrs;
import com.crossent.microservice.trsfrmcrs.service.TransformService;
import com.crossent.microservice.trsfrmcrs.util.Ellip2Ellipsoid.Ellipsoid;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value="/api/crs")
@RestController
public class TransformController {
    @Autowired
    private TransformService transformService;

    @ApiOperation("제공하는 좌표계 목록 반환")
    @GetMapping("/epsg/list/all")
    @ResponseStatus(HttpStatus.OK)
    public List<String> epsgListAll() {
        System.out.println("request /api/crs/epsg/list/all -- Controller --");
        return transformService.epsgListAll();
    }

    @ApiOperation("제공하는 타원체 목록 반환")
    @GetMapping("/ellipsoid/list/all")
    @ResponseStatus(HttpStatus.OK)
    public List<String> ellipsoidListAll() {
        System.out.println("request /api/crs/ellipsoid/list/all -- Controller --");
        return transformService.ellipsoidListAll();
    }

    @ApiOperation("제공하는 좌표계 목록 중에서 해당하는 좌표계의 Spec반환")
    @GetMapping("/epsg/spec")
    @ResponseStatus(HttpStatus.OK)
    public List<String> epsgSpecByCode(@RequestParam(name="code")String code) {
        System.out.println("request /api/crs/epsg/spec?code -- Controller --");
        return transformService.epsgSpecByCode(code.toLowerCase());
    }

    @ApiOperation("제공하는 타원체 목록 중에서 해당하는 타원체의 Spec반환")
    @GetMapping("/ellipsoid/spec")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Ellipsoid ellipsoidByName(@RequestParam(name="name")String name) {
        System.out.println("request /api/crs/ellipsoid/spec? -- Controller --");
        return transformService.ellipsoidByName(name.toLowerCase());
    }

    @ApiOperation("타원체와 위경도 값을 입력받아서 좌표계와 x,y값을 반환")
    @GetMapping("/transform/xy")
    @ResponseStatus(HttpStatus.OK)
    public TwoDimCrs threeDimToTwoDim(@RequestParam(name="inputEllipsoid", required = false)String inputEllipsoid,
                                      @RequestParam(name="outputEpsg")String outputEpsg,
                                      @RequestParam(name="lat")Double lat,
                                      @RequestParam(name="lng")Double lng) {
        System.out.println("request /api/crs/tranform? -- Controller --");
        return transformService.threeDimToTwoDim(inputEllipsoid.toLowerCase(), outputEpsg.toLowerCase(), lat, lng);
    }

    @ApiOperation("좌표계와 x,y값을 입력받아서 타원체와 위경도값 반환")
    @GetMapping("/transform/latlng")
    @ResponseStatus(HttpStatus.OK)
    public ThreeDimCrs twoDimToThreeDim(@RequestParam(name="inputEpsg", required = false)String inputEpsg,
                                        @RequestParam(name="outputEllipsoid")String outputEllipsoid,
                                        @RequestParam(name="x")Double x,
                                        @RequestParam(name="y")Double y) {
        System.out.println("request /api/crs/tranform? -- Controller --");
        return transformService.twoDimToThreeDim(inputEpsg.toLowerCase(), outputEllipsoid.toLowerCase(), x, y);
    }


}
