var posApiApp = angular.module('posApiApp', []);

posApiApp.controller('posApiListController', function ($scope, $http, $interval) {
    $scope.posApiList = [];

    $interval(function () {
        $scope.posApiList.forEach(function (posApi) {
            $scope.updateApiStatus(posApi);
        })
    }, 5000);

    $scope.posApiListInit = function () {
        $http({
            method: 'get',
            url: 'rest/status/api_list'
        }).success(function (data, status) {
            $scope.posApiList = data;
        });
    }

    $scope.sendData = function (posApi) {
        posApi.sending = true;
        $http({
            method: 'post',
            url: 'rest/posApi/send',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            params: {
                posId: posApi.posId
            }
        }).success(function (data, status) {
            if (status == 200) {
                $scope.updateApiStatus(posApi);
                posApi.sending = false;
            } else {
                alert(data);
            }
        });
    }

    $scope.updateApiStatus = function (posApi) {
        console.log(posApi);
        $http({
            method: 'get',
            url: 'rest/status/api_status',
            params: {
                posId: posApi.posId
            }
        }).success(function (data, status) {
            if (status == 200) {
                posApi.countLottery = data.countLottery;
                posApi.countBill = data.countBill;
                posApi.sending = data.sending;
                posApi.lastSentDate = data.lastSentDate;
            }
        });
    }
});