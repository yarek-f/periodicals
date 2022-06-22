<%@ page import="ua.dto.UserSignUpDto" %>
<%@ page import="java.util.Map" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page session="true" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <title>Main page</title>
    <style>
        html, body {
            margin: 0;
            height: 100%;
            overflow: hidden
        }

        body {
            background: url(/images/Screenshot.png) no-repeat center center fixed;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
        }

        .gradient-custom-4 {
            background: #84fab0;

            background: -webkit-linear-gradient(to right, rgba(132, 250, 176, 1), rgba(143, 211, 244, 1));

            background: linear-gradient(to right, rgba(132, 250, 176, 1), rgba(143, 211, 244, 1))
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="/periodicals"><fmt:message key="label.navbar"/></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="publisherList.jsp"><fmt:message
                            key="lable.publishers"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="userList.jsp"><fmt:message key="lable.userList"/></a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        <fmt:message key="label.languages"/>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDropdown">
                        <li><a href="?lang=en" class="link-light"><fmt:message key="label.lang.en"/></a></li>
                        <li><a href="?lang=uk" class="link-light"><fmt:message key="label.lang.uk"/></a></li>
                    </ul>
                </li>
            </ul>
            <div>
                <a href="signUp.jsp" class="btn btn-primary"><fmt:message key="label.signUp"/></a>
                <a href="logIn.jsp" class="btn btn-primary"><fmt:message key="label.logIn"/></a>
            </div>
        </div>
    </div>
</nav>

<div class="mask d-flex align-items-center h-100">
    <div class="container ">
        <div class="row d-flex justify-content-center align-items-center pb-5 mb-5">
            <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                <div class="card" style="border-radius: 15px;">
                    <div class="card-body p-5">
                        <c:if test="${sessionScope.get('loginError') !=null}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <strong>
                                        ${sessionScope.get('loginError')}
                                </strong>
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                        </c:if>
                        <h2 class="text-uppercase text-center mb-5"><fmt:message key="label.logIn"/></h2>

                        <form method="post" action="/login">

                            <div class="form-outline mb-4">
                                <label class="form-label" for="inputEmail"><fmt:message key="label.email"/></label>
                                <input type="email" name="email" id="inputEmail"
                                       class="form-control form-control-lg"/>

                            </div>

                            <div class="form-outline mb-4">
                                <label class="form-label" for="inputPassword"><fmt:message
                                        key="label.password"/></label>
                                <input type="password" name="password" id="inputPassword"
                                       class="form-control form-control-lg"/>

                            </div>

                            <div class="d-flex justify-content-center">
                                <button type="submit"
                                        class="btn btn-success btn-block btn-lg gradient-custom-4 text-body">
                                    <fmt:message key="label.logIn"/></button>
                            </div>
                            <p class="text-center text-muted mt-5 mb-0"><fmt:message key="label.haventAcaunt"/> <a
                                    href="signUp.jsp" class="fw-bold text-body"><u><fmt:message
                                    key="label.signUpHere"/></u></a></p>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

</body>
</html>