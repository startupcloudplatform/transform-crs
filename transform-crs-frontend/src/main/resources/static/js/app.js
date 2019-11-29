'use strict';

// Define the `transformCrsApp` module
var app = angular.module('transformCrsApp', [
    'ngRoute',
    'ui.bootstrap',
    'ngclipboard'
]);

app.config(['$routeProvider', '$httpProvider', '$locationProvider',
    function($routeProvider) {
        $routeProvider
            .when('/main', {
                templateUrl: '/views/converter.html',
                controller: 'convertCtrl as con'
            })
            .otherwise({
                redirectTo: '/main'
            });
    }
]);