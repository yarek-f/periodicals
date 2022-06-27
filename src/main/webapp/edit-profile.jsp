<%@ page import="ua.dto.UserSignUpDto" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>


<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<jsp:useBean id="searchPublisher" class="ua.dao.PublisherMySqlDao"/>
<html lang="${sessionScope.lang}">
<%
    UserSignUpDto userDto = ((UserSignUpDto) session.getAttribute("customerDto"));
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <title>Main page</title>
    <style>
        html, body {
            margin: 0;
            height: 100%;
            overflow-x: hidden;
        }
        .gradient-custom-4 {
            background: #84fab0;

            background: -webkit-linear-gradient(to right, rgba(132, 250, 176, 1), rgba(143, 211, 244, 1));

            background: linear-gradient(to right, rgba(132, 250, 176, 1), rgba(143, 211, 244, 1))
        }
        body {
            background:
                    url(/images/Screenshot.png)
                    no-repeat center center fixed;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
        }

    </style>
</head>

<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid" style="margin-top: -12px; margin-bottom: -12px;">
        <a class="navbar-brand" href="/periodicals"><fmt:message key="label.navbar"/></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" href="/publishers"><fmt:message key="lable.publishers"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="/users"><fmt:message key="lable.userList"/></a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <fmt:message key="label.languages" />
                    </a>
                    <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDropdown">
                            <li><a href="?lang=en" class="link-light"><fmt:message key="label.lang.en" /></a></li>
                            <li><a href="?lang=uk" class="link-light"><fmt:message key="label.lang.uk" /></a></li>
                    </ul>
                </li>

            </ul>
            <form class="d-flex mt-3" method="post" action="/periodicals">
                <button class="btn btn-outline-success  me-2" type="submit"><b><fmt:message key="label.search" /></b></button>
                <input class="form-control me-2" type="search" name="search" placeholder="<fmt:message key="label.search" />" aria-label="Search">
            </form>
            <c:choose>
                <c:when test="${sessionScope.get('profile')!=null}">
                    <ul class="navbar-nav mb-2 mb-lg-0">
                        <li class="nav-item dropdown">
                            <a class="nav-link active" href="#" id="navbarDropdown5" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <div class="text-truncate " style="max-width: 120px;">
                                        ${sessionScope.get('profile')}</div>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDropdown">
                                <li class="ps-2"><a href="#" class="link-light" style="text-decoration: none;">Profile</a></li>
                                <li class="ps-2"><a href="#" class="link-light" style="text-decoration: none;">Subscriptions</a></li>
                                <li class="ps-2"><a href="#" class="link-light" style="text-decoration: none;"><span class="pe-2">Log out</span>
                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-box-arrow-right" viewBox="0 0 16 16">
                                        <path fill-rule="evenodd" d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0v2z"/>
                                        <path fill-rule="evenodd" d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3z"/>
                                    </svg>
                                </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </c:when>
                <c:otherwise>
                    <div>
                        <a href="signUp.jsp" class="btn btn-primary"><fmt:message key="label.signUp" /></a>
                        <a href="logIn.jsp" class="btn btn-primary"><fmt:message key="label.logIn" /></a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

<form action="/my-profile" method="post" class="vh-100 bg-image" >
    <div class="mask d-flex align-items-center h-100 <%--gradient-custom-3--%>">
        <div class="container h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                    <div class="card" style="border-radius: 15px;">
                        <div class="card-body p-5">

                            <c:if test="${sessionScope.get('errorEditingMessages') !=null}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <strong>
                                    <fmt:message key="label.wrongUserInputData" />
                                </strong>
                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                            </div>
                            </c:if>
                            <h2 class="text-uppercase text-center mb-5">EDIT AN ACCOUNT</h2>

                            <div class="form-outline mb-4">
                                <label class="form-label" for="inputFullName">
                                    <fmt:message key="label.fullName"/>
                                </label>
                                <input type="text" name="inputFullName" id="inputFullName" class="form-control form-control-lg"
                                       placeholder="<%=userDto!=null?userDto.getFullName():""%>"/>
                                <c:if test="${sessionScope.get('errorEditingMessages') != null && sessionScope.get('errorEditingMessages').contains('fullName')}">
                                        <span class="text-danger">
                                            <fmt:message key="label.wrongFullNume" />
                                        </span>
                                </c:if>
                            </div>

                            <c:if test="<%=userDto.getDob()==null%>">
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="inputDob">
                                        <fmt:message key="label.birthDay" />
                                    </label>
                                    <input type="date" name="inputDob" id="inputDob" class="form-control form-control-lg"
                                           value="<%=userDto!=null?userDto.getDob():""%>"/>
                                    <c:if test="${sessionScope.get('errorEditingMessages') != null && sessionScope.get('errorEditingMessages').contains('dob')}">
                                        <span class="text-danger">
                                            <fmt:message key="label.wrongDob"/>
                                        </span>
                                    </c:if>
                                </div>
                            </c:if>

                            <div class="form-outline mb-4">
                                <label class="form-label" for="inputPhoneNumber">
                                    <fmt:message key="label.phoneNumber" />
                                </label>
                                <input type="tel" name="inputPhoneNumber" id="inputPhoneNumber" class="form-control form-control-lg"
                                       placeholder="<%=userDto!=null?userDto.getPhoneNumber():""%>"/>
                                <c:if test="${sessionScope.get('errorEditingMessages') != null && sessionScope.get('errorEditingMessages').contains('phoneNumber')}">
                                        <span class="text-danger">
                                            <fmt:message key="label.wrongPhoneNumber" />
                                        </span>
                                </c:if>
                            </div>

                            <div class="form-outline mb-4">
                                <label class="form-label" for="inputEmail"><fmt:message key="label.email" />
                                </label>
                                <input type="email" name="inputEmail" id="inputEmail" class="form-control form-control-lg"
                                       placeholder="<%=userDto!=null?userDto.getEmail():""%>"/>
                                <c:if test="${sessionScope.get('errorEditingMessages') != null && sessionScope.get('errorEditingMessages').contains('email') }">
                                        <span class="text-danger">
                                            <fmt:message key="label.wrongEmail" />
                                        </span>
                                </c:if>
                            </div>

                            <div class="form-outline mb-4">
                                <label class="form-label" for="inputPassword"><fmt:message key="label.password" />
                                </label>
                                <input type="password" name="inputPassword" id="inputPassword" class="form-control form-control-lg"/>
                                <c:if test="${sessionScope.get('errorEditingMessages') != null && sessionScope.get('errorEditingMessages').contains('password') }">
                                        <span class="text-danger">
                                            <fmt:message key="label.wrongPassword" />
                                        </span>
                                </c:if>
                            </div>

                            <div class="form-outline mb-4">
                                <label class="form-label" for="inputConfirmPassword"><fmt:message key="label.repeatPassword" />
                                </label>
                                <input type="password" name="inputConfirmPassword" id="inputConfirmPassword" class="form-control form-control-lg"/>
                                <c:if test="${sessionScope.get('errorEditingMessages') != null && sessionScope.get('errorEditingMessages').contains('confirmPassword') }">
                                        <span class="text-danger">
                                            <fmt:message key="label.wrongConfirmPassword" />
                                        </span>
                                </c:if>
                            </div>

                            <div class="d-flex justify-content-center">
                                <button type="submit" class="btn btn-success btn-block btn-lg gradient-custom-4 text-body">Edit</button>
                            </div>
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
