package com.crossent.microservice.trsfrmcrs.service;


import com.crossent.microservice.trsfrmcrs.dto.Epsg.Epsg;

import com.crossent.microservice.trsfrmcrs.dto.ThreeDimCrs;
import com.crossent.microservice.trsfrmcrs.dto.TwoDimCrs;
import com.crossent.microservice.trsfrmcrs.util.Ellip2Ellipsoid.Ellip2Ellipsoid;
import com.crossent.microservice.trsfrmcrs.util.Ellip2Ellipsoid.Ellipsoid;
import com.crossent.microservice.trsfrmcrs.util.Ellip2Ellipsoid.Parameters7;
import com.crossent.microservice.trsfrmcrs.util.Ellip2Ellipsoid.Values3;
import com.jhlabs.map.proj.Projection;
import com.jhlabs.map.proj.ProjectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TransformService {


    @Autowired
    @Qualifier("bessel")
    Ellipsoid bessel;
    @Autowired
    @Qualifier("grs80")
    Ellipsoid grs80;
    @Autowired
    @Qualifier("wgs84")
    Ellipsoid wgs84;

    @Autowired
    @Qualifier("epsg5181")
    Epsg epsg5181;

    @Autowired
    @Qualifier("epsg5179")
    Epsg epsg5179;

    @Autowired
    @Qualifier("epsg5174")
    Epsg epsg5174;

    // epsg, ellipsoid 리스트보관
    HashMap<String, Ellipsoid> ellipsoidHashMap;
    HashMap<String, Epsg> epsgHashMap;

    public TransformService(Ellipsoid bessel, Ellipsoid wgs84, Ellipsoid grs80,
                            Epsg epsg5174, Epsg epsg5179, Epsg epsg5181) {
        this.ellipsoidHashMap = new HashMap<String, Ellipsoid>();
        this.ellipsoidHashMap = initEllipsoid(bessel, grs80, wgs84);

        this.epsgHashMap = new HashMap<String, Epsg>();
        this.epsgHashMap = initEpsg(epsg5174, epsg5179, epsg5181);
    }

    // 대문자 소문자 문제
    // List 컬렉션으로 통째로 넘기는게 더 나을텐데....
    private HashMap<String, Epsg> initEpsg(Epsg epsg1, Epsg epsg2, Epsg epsg3) {
        this.epsgHashMap.put("epsg5174", epsg1);
        this.epsgHashMap.put("epsg5179", epsg2);
        this.epsgHashMap.put("epsg5181", epsg3);
        return this.epsgHashMap;
    }

    private HashMap<String, Ellipsoid> initEllipsoid(Ellipsoid ellps1, Ellipsoid ellps2, Ellipsoid ellps3) {
        this.ellipsoidHashMap.put("bessel", ellps1);
        this.ellipsoidHashMap.put("grs80", ellps2);
        this.ellipsoidHashMap.put("wgs84", ellps3);

        return this.ellipsoidHashMap;
    }

    // epsg 목록 return
    public List<String> epsgListAll() {
        System.out.println("request /api/crs/epsg/list/all -- Service --");
        ArrayList<String> listEpsg = new ArrayList<String>();
        for (String code : epsgHashMap.keySet()) {
            listEpsg.add(code);
        }
        return listEpsg;
    }

    // ellipsoid 목록 return
    public List<String> ellipsoidListAll() {
        System.out.println("request /api/crs/ellipsoid/list/all -- Service --");
        ArrayList<String> ellipsoidList = new ArrayList<String>();
        for (String name : ellipsoidHashMap.keySet()) {
            ellipsoidList.add(name);
        }

        return ellipsoidList;
    }

    public List<String> epsgSpecByCode(String code) {
        ArrayList<String> epsgSpec = new ArrayList<String>();

        Epsg epsg = this.epsgHashMap.get(code);
        for (String spec : epsg.getSpecs()) {
            epsgSpec.add(spec);
        }
        return epsgSpec;
    }


    public Ellipsoid ellipsoidByName(String name) {
        System.out.println("request /api/crs/ellipsoid/spec? -- Service --");
        Ellipsoid ellipsoid = this.ellipsoidHashMap.get(name);

        // 반환을 위해서 double 상태로는 RestController로 반환이 안되서 반환을 위해서 String 거쳐서 Double로 전환
        String aString = String.valueOf(ellipsoid.getA());
        String fString = String.valueOf(ellipsoid.getF());
        Ellipsoid result = new Ellipsoid(Double.parseDouble(aString), Double.parseDouble(fString));
        return result;

    }

    // 사용할 인스턴스 정의
    Point2D.Double srcProjec = null;
    Point2D.Double dstProjec = null;
    Parameters7 towgs84 = new Parameters7(-115.8, 474.99, 674.11, -1.16, 2.31, 1.63, 6.43);
    //Parameters7 to2wgs84 = new Parameters7(-145.907,505.034,685.756,-1.162,2.347,1.592,6.342);
    Parameters7 tozero = new Parameters7(0, 0, 0, 0, 0, 0, 0);
    public TwoDimCrs threeDimToTwoDim(String inputEllipsoid, String outputEpsg, Double lat, Double lng) {

        //사용되는 epsg(2차원) 확인
        Epsg epsg = epsgHashMap.get(outputEpsg);

        // 투영법 생성
        Projection proj = ProjectionFactory.fromPROJ4Specification(epsg.getSpecs());

        // 위경도 셋팅
        Values3 latlng = new Values3(lat, lng, 0);
        Values3 changedLatlng = new Values3();

        // 타원체 비교
        if (inputEllipsoid.equals(epsg.getEllipsoid())) {
            // 위경도 위치 바꿔서 전달해야 라이브러이 동작함.(경도, 위도)로 넣어줘야함.
            srcProjec = new Point2D.Double(latlng.getV2(), latlng.getV1());
            dstProjec = proj.transform(srcProjec, new Point2D.Double());
        } else {
            Ellip2Ellipsoid transform;
            // bessel 타원체가 가장 차이가 크기 때문에 타원체 변화를 반드시 해줘야함.
            if (inputEllipsoid.equals("bessel")||epsgHashMap.get(outputEpsg).getEllipsoid().equals("bessel")) {
                System.out.println("");
                // 타원체 변경
                transform = new Ellip2Ellipsoid(ellipsoidHashMap.get(epsg.getEllipsoid()),
                        ellipsoidHashMap.get(inputEllipsoid), towgs84);

            } else {
                // 타원체 변경
                transform = new Ellip2Ellipsoid(
                        ellipsoidHashMap.get(epsg.getEllipsoid()), ellipsoidHashMap.get(inputEllipsoid), tozero);

            }
            transform.reverseTransform(latlng, changedLatlng);
            // 2차원 좌표로 변환
            srcProjec = new Point2D.Double(changedLatlng.getV2(), changedLatlng.getV1());
            dstProjec = proj.transform(srcProjec, new Point2D.Double());
        }

        TwoDimCrs output= new TwoDimCrs(outputEpsg, dstProjec.getX(), dstProjec.getY());
        return output;
    }

    // 1. 2차원 좌표를 3차원 좌표(a)로 변환
    // 2. 3차원 좌표(a)를 3차원 좌표(b)로 변환 타원체 이용
    public ThreeDimCrs twoDimToThreeDim(String inputEpsg, String outputEllipsoid, Double x, Double y) {
        Epsg epsg = epsgHashMap.get(inputEpsg);

        System.out.println("스펙 스펙: "+ epsg.getSpecs());
        // 투영법 생성
        Projection proj = ProjectionFactory.fromPROJ4Specification(epsg.getSpecs());
        System.out.println("epsg: " + epsg.toString());

        // 위경도 셋팅
        Values3 latlng = new Values3();
        Values3 changedLatlng = new Values3();

        // 2차원 좌표를 3차원으로 변환
        srcProjec = new Point2D.Double(x,y);
        System.out.println("2차원 좌표:" + srcProjec);
        dstProjec = proj.inverseTransform(srcProjec, new Point2D.Double());
        System.out.println("변환 3차원:" + dstProjec);
        // 타원체 비교
        if (outputEllipsoid.equals(epsg.getEllipsoid())) {
            System.out.println(dstProjec);
            return new ThreeDimCrs(outputEllipsoid, dstProjec.getX(), dstProjec.getY());
        } else {
            // bessel 타원체만 차이가 크기 때문에 변화를 줘야함.
            Ellip2Ellipsoid transform;
            System.out.println("타원체 변환 없이 2차원 좌표를 3차원으로:  "+dstProjec);
            if (epsgHashMap.get(inputEpsg).getEllipsoid().equals("bessel")||outputEllipsoid.equals("bessel")) {
                // 타원체 변경
                transform = new Ellip2Ellipsoid(ellipsoidHashMap.get(epsg.getEllipsoid()),
                        ellipsoidHashMap.get(outputEllipsoid), towgs84);
            } else {
                // 타원체 변경
                transform = new Ellip2Ellipsoid(ellipsoidHashMap.get(epsg.getEllipsoid()),
                        ellipsoidHashMap.get(outputEllipsoid), tozero);
            }
            latlng.setV1(dstProjec.getY());
            latlng.setV2(dstProjec.getX());
            latlng.setV3(0);
            //3차원 좌표 변환
            transform.transform(latlng, changedLatlng);
            System.out.println("최종 변환: "+ changedLatlng);
        }
        ThreeDimCrs output= new ThreeDimCrs(outputEllipsoid, changedLatlng.getV1(), changedLatlng.getV2());
        return output;
    }
}
