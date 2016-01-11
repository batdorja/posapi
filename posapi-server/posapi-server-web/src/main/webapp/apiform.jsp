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
        <h1 style="color:#ffffff; font-weight: bold;">PosAPI Server</h1>
    </div>
</div>
<form action="/rest/posApi/upload" method="post" enctype="multipart/form-data" class="row">
    <fieldset>
        <legend>PosAPI 2.1 install</legend>
        <div class="row">
            <div class="one columns">PosAPI:</div>
            <div class="eleven columns"><input type="file" name="posapi"/></div>
        </div>
        <div class="row" style="margin-top: 10px;">
            <div class="twelve columns">
                <div class="medium primary btn"><input type="submit" value="Хуулах"/></div>
            </div>
        </div>
    </fieldset>
</form>
</body>
</html>
