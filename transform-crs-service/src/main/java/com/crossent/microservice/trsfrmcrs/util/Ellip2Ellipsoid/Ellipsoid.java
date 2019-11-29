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

public class Ellipsoid {
	double a; // ��ݰ�
	double f; // ���� = (��ݰ�-�ܹݰ�) / ��ݰ�

	public Ellipsoid(double a, double f) {
		this.a = a;
		this.f = f;
	}

	public double getA() {
		return a;
	}

	public double getF() {
		return f;
	}
}
