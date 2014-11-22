'use strict';

/*
 * 项目构建时，_‍_FRAMEWOR‍K_CONFIG__变量会被替换成框架配置，包括依
 * 赖树、别名表、combo连接形式、配置文件等，这样就可以对资源请求进行
 * 按需、合并等优化了
 */
require.config(__FRAMEWORK_CONFIG__);

var $routeSet = function ($controllerProvider, options) {
    if (!$controllerProvider) {
        throw new Error("$controllerProvider is not set!");
    }

    var $routeSet_js = require.alias(options.resource);
    var importUrl = $routeSet_js;
    var d = /\.[^\.]+$/.exec(importUrl);
    var pos = importUrl.search(d);
    var $routeSet_html = importUrl.slice(0, pos).concat(".html");

    return {
        templateUrl: "public/c/" + $routeSet_html,
        controller: 'TestCtrl',
        resolve: {
            delay: ['$q', '$rootScope',
            function ($q, $rootScope) {
                    var defer = $q.defer();

                    require.async($routeSet_js, function (e) { //按需加载 
                        $controllerProvider.register("TestCtrl", e);
                        defer.resolve();
                    });

                    return defer.promise;
        }]
        }
    };
};

angular.module('phonecat', ['ngRoute']).config(['$routeProvider', '$controllerProvider',
    function ($routeProvider, $controllerProvider) {

        $routeProvider.when('/phones', $routeSet($controllerProvider, {
            controller: 'TestCtrl',
            resource: 'pages/letter'
        })).when('/phones1', $routeSet($controllerProvider, {
            controller: 'TestCtrl',
            resource: 'pages/letter'
        })).otherwise({
            redirectTo: '/phones'
        });
}]);