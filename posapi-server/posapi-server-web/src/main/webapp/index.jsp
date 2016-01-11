<%--
  Created by IntelliJ IDEA.
  User: nasanjargal
  Date: 1/5/16
  Time: 2:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="WEB-INF/inc/header.jsp"/>
</head>
<body class="container">
<div class="navbar">
    <div class="row">
        <div class="ten columns"><h1 style="color:#ffffff; font-weight: bold;">PosAPI Server</h1></div>
        <div class="two columns">
            <div class="small xxwide primary btn"><a href="apiform.jsp">Шинэ PosAPI</a></div>
        </div>
    </div>
</div>
<div class="row" ng-app="posApiApp">
    <div class="twelve columns" ng-controller="posApiListController" ng-init="posApiListInit()">
        <table class="rounded">
            <thead>
            <tr>
                <th width="100px">ТТД</th>
                <th width="100px">PosID</th>
                <th width="85px">Салбар</th>
                <th width="100px">Сугалаа</th>
                <th width="100px">Баримт</th>
                <th width="100px">Илгээсэн</th>
                <th width="100px">Илгээх</th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="posApi in posApiList">
                <td>
                    {{posApi.registerNo}}
                </td>
                <td>
                    {{posApi.posId}}
                </td>
                <td>
                    {{posApi.branchNo}}
                </td>
                <td>
                    {{posApi.countLottery}}
                </td>
                <td>
                    {{posApi.countBill}}
                </td>
                <td>
                    {{posApi.lastSentDate}}
                </td>
                <td>
                    <div class="medium primary btn active" ng-click="sendData(posApi)"
                         ng-hide="posApi.sending">
                        <a href="#">Илгээх</a>
                    </div>
                    <div ng-show="posApi.sending">
                        <img src="app/img/sending.gif"/>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<script src="app/script/app.js" type="application/javascript"></script>
</body>
</html>
