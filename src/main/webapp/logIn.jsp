<%@ page import="ua.dto.UserSignUpDto" %>
<%@ page import="java.util.Map" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${param.lang}">
<%
    UserSignUpDto dto = ((UserSignUpDto) session.getAttribute("userSignUpDto"));
%>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8"> <!-- WARNING!!! -->
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <title>Main page</title>
    <style>
        .gradient-custom-3 {
            /* fallback for old browsers */
            background: #84fab0;

            /* Chrome 10-25, Safari 5.1-6 */
            background: -webkit-linear-gradient(to right, rgba(132, 250, 176, 0.5), rgba(143, 211, 244, 0.5));

            /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
            background: linear-gradient(to right, rgba(132, 250, 176, 0.5), rgba(143, 211, 244, 0.5))
        }
        .gradient-custom-4 {
            /* fallback for old browsers */
            background: #84fab0;

            /* Chrome 10-25, Safari 5.1-6 */
            background: -webkit-linear-gradient(to right, rgba(132, 250, 176, 1), rgba(143, 211, 244, 1));

            /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
            background: linear-gradient(to right, rgba(132, 250, 176, 1), rgba(143, 211, 244, 1))
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#"><fmt:message key="label.navbar"/></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#"><fmt:message key="lable.home"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="userList.jsp"><fmt:message key="lable.userList"/></a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <fmt:message key="label.languages" />
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <%--                        <li><a class="dropdown-item" href="#">Action</a></li>--%>
                        <%--                        <li><a class="dropdown-item" href="#">Another action</a></li>--%>
                        <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
                        <li><a href="?lang=uk"><fmt:message key="label.lang.uk" /></a></li>
                    </ul>
                </li>
                <%--                <li class="nav-item">--%>
                <%--                    <a class="nav-link disabled">Disabled</a>--%>
                <%--                </li>--%>
            </ul>
            <div>
                <a href="signUp.jsp" class="btn btn-primary"><fmt:message key="label.signUp" /></a>
                <a href="logIn.jsp" class="btn btn-primary"><fmt:message key="label.logIn" /></a>
            </div>
        </div>
    </div>
</nav>

<form action="/signUp" method="post" class="vh-100 bg-image" style="background-image: url('https://mdbcdn.b-cdn.net/img/Photos/new-templates/search-box/img4.webp');">
    <div class="mask d-flex align-items-center h-100 gradient-custom-3">
        <div class="container h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                    <div class="card" style="border-radius: 15px;">
                        <div class="card-body p-5">

                            <c:if test="${sessionScope.get('errorMessages') !=null}">

                                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                    <strong>Wrong user input data
                                    </strong>
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            </c:if>
                            <h2 class="text-uppercase text-center mb-5"><fmt:message key="label.logIn" /></h2>

                            <form>


                                <div class="form-outline mb-4">
                                    <label class="form-label" for="inputEmail"><fmt:message key="label.email" /></label>
                                    <input type="email" name="inputEmail" id="inputEmail" class="form-control form-control-lg"
                                           value="<%=dto!=null?dto.getEmail():""%>"/>
                                    <c:if test="${sessionScope.get('errorMessages') != null && sessionScope.get('errorMessages').containsKey('phoneNumber') }">
                                        <span class="text-danger">
                                        <%=((Map<String, String>) session.getAttribute("errorMessages")).get("email")%></span>
                                    </c:if>
                                </div>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="inputPassword"><fmt:message key="label.password" /></label>
                                    <input type="password" name="inputPassword" id="inputPassword" class="form-control form-control-lg"
                                           value="<%=dto!=null?dto.getPassword():""%>"/>
                                    <c:if test="${sessionScope.get('errorMessages') != null && sessionScope.get('errorMessages').containsKey('password') }">
                                        <span class="text-danger">
                                        <%=((Map<String, String>) session.getAttribute("errorMessages")).get("password")%></span>
                                    </c:if>
                                </div>

                                <div class="d-flex justify-content-center">
                                    <button type="submit" class="btn btn-success btn-block btn-lg gradient-custom-4 text-body"><fmt:message key="label.logIn" /></button>
                                </div>

                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

</body>
</html>