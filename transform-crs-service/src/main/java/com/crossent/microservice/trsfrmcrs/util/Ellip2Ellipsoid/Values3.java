/*
 * Ellip2Ellipsoid�� Ÿ��ü���� ������ ��ǥ�踦 ��ȯ�ϱ� ���� ���¼ҽ� ���̺귯���Դϴ�.
 * 7 �Ķ���͸� ����Ͽ� Ÿ��ü���� ������ ��ǥ�� ��ȯ�մϴ�.
 * 
 * 2011/09/16 ó�� ���������� ���� 1.0�Դϴ�.
 *  
 * ���۱� (C) 2011 ��������(GeoService)
 * 
 * �� ���̺귯���� ���� ����Ʈ�����Դϴ�.
 * GNU LGPL�� ���� �������� �� ������/�ְų� ������ �� �ֽ��ϴ�.
 * �� ����Ʈ��� ����Կ� �־� �߻��ϴ� ������ å������ �ʽ��ϴ�.
 * 
 * ���� �ڼ��� ������ ���ؼ��� ���� ����ó�� ���� �����Ͻñ� �ٶ��ϴ�.
 * 
 * �� �� �� hjkim@geoservice.co.kr
 * �� �� �� www.gisdeveloper.co.kr
 * Ȩ������ www.geoservice.co.kr 
 * 
 * �����մϴ�.
 */

package com.crossent.microservice.trsfrmcrs.util.Ellip2Ellipsoid;

public class Values3 {
	public double getV1() {
		return V1;
	}

	public double getV2() {
		return V2;
	}

	public double getV3() {
		return V3;
	}

	public void setV1(double v1) {
		V1 = v1;
	}

	public void setV2(double v2) {
		V2 = v2;
	}

	public void setV3(double v3) {
		V3 = v3;
	}

	public Values3() {
		V1 = 0.0;
		V2 = 0.0;
		V3 = 0.0;
	}
	
	public Values3(Values3 v) {
		V1 = v.V1;
		V2 = v.V2;
		V3 = v.V3;
	}
	
	public Values3(double V1, double V2, double V3) {
		this.V1 = V1;
		this.V2 = V2;
		this.V3 = V3;
	}
	
	public String toString() {
		return "(" + V1 + ", " + V2 + ", " + V3 + ")";
	}
	
	public double V1;
	public double V2;
	public double V3;
}
