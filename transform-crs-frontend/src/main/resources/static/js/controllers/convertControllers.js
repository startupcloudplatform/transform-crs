'use strict';


angular.module('transformCrsApp')
    .controller('convertCtrl', function ($scope, $http, $window) {

        var con = this;
        con.crsList = ['EPSG:5181(kakao Map)', 'EPSG:5179(Naver Map)', 'EPSG:5174(국토부)'];
        con.sltCrs = con.crsList[0];
        con.xCoord = null;
        con.yCoord = null;
        con.latitude = null;  //위도
        con.longitude = null; //경도
        con.result = '';
        con.isXCoordValid = true;
        con.isYCoordValid = true;

        // 변환 클릭 시, 좌표정보 경도 위도로 변환
        con.exchange = function() {

            if(con.xCoord === null || con.yCoord === null){
                alert("변환할 좌표를 입력 해주세요!");
                con.reset();
                return;
            }
            if(con.isYCoordValid === false || con.isXCoordValid === false || con.xCoord === '' || con.yCoord === ''){
                alert("숫자만 입력 가능합니다");
                con.reset();
                return;
            }
            if(angular.isUndefined(con.xCoord)){
                alert("x 좌표의 입력 값을 확인하세요!");
                return;
            }
            if(angular.isUndefined(con.yCoord)){
                alert("y 좌표의 입력 값을 확인하세요!");
                return;
            }

            var params = {
                inputEpsg: con.sltCrs===con.crsList[0]? 'EPSG5181' : con.sltCrs===con.crsList[1]? 'EPSG5179' : 'EPSG5174',
                outputEllipsoid: 'wgs84',
                x: con.xCoord,
                y: con.yCoord
            };

            $http({
                method: 'GET',
                url: '/api/crs/transform/latlng',
                params: params,
                headers: {'Content-Type': 'application/json; charset=utf-8'}

            }).then(function success(response) {
                con.latitude = response.data.lat;
                con.longitude = response.data.lng;
                con.result = con.latitude+","+con.longitude;
            }, function error(response) {
                alert("좌표 변환 실패!");
                con.reset();
            });
        };

        con.validChange = function(coord, value) {
            if(coord === 'con.xCoord'){
                con.isXCoordValid = value;
            }else{
                con.isYCoordValid = value;
            }
        }

        con.reset = function() {
            con.xCoord = null;
            con.yCoord = null;
            con.latitude = null;  //위도
            con.longitude = null; //경도
            con.result = '';
            con.isXCoordValid = true;
            con.isYCoordValid = true;
        }

        con.pageLoadData = function() {
        };

        con.pageLoadData();
    })


    .directive("floatingNumberOnly", function() {
    return {
        require: 'ngModel',
        link: function(scope, ele, attr, ctrl) {
            var coord = attr.ngModel;
            ctrl.$parsers.push(function(inputValue) {
                var pattern = /^[+-]?\d*(\.?\d*)$/
                scope.con.validChange(coord, true);

                if (inputValue == ''){
                    return '';
                }
                var dotPattern = /^[.]*$/;

                if(!pattern.test(inputValue)) {
                    scope.con.validChange(coord, false);
                    return '';
                }

                if (dotPattern.test(inputValue)) {
                    scope.con.validChange(coord, false);
                    return '';
                }


                var newInput = inputValue.replace(/[^[+-]?0-9.]/g, '');

                if (newInput != inputValue) {
                    scope.con.validChange(coord, false);
                }else{
                    scope.con.validChange(coord, true);
                }

                var dotCount = inputValue.split(".").length - 1; // count of dots present
                if (dotCount > 1) { //condition to restrict "integer part" to 9 digit count
                    scope.con.validChange(coord, false);
                }
                return newInput;
            });
        }
    };
});