<%@ page import="ua.dto.UserSignUpDto" %>
<%@ page import="java.util.Map" %>
<%@ page import="ua.dto.CustomerSignUpDto" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page session="true" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<%
    UserSignUpDto userDto = ((UserSignUpDto) session.getAttribute("signUpDTO"));
%>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <title>Main page</title>
    <style>
        .gradient-custom-3 {
            background: #84fab0;

            background: -webkit-linear-gradient(to right, rgba(132, 250, 176, 0.5), rgba(143, 211, 244, 0.5));

            background: linear-gradient(to right, rgba(132, 250, 176, 0.5), rgba(143, 211, 244, 0.5))
        }
        .gradient-custom-4 {
            background: #84fab0;

            background: -webkit-linear-gradient(to right, rgba(132, 250, 176, 1), rgba(143, 211, 244, 1));

            background: linear-gradient(to right, rgba(132, 250, 176, 1), rgba(143, 211, 244, 1))
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="index.jsp"><fmt:message key="label.navbar"/></a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="publisherList.jsp"><fmt:message key="lable.publishers"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="userList.jsp"><fmt:message key="lable.userList"/></a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <fmt:message key="label.languages" />
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
                        <li><a href="?lang=uk"><fmt:message key="label.lang.uk" /></a></li>
                    </ul>
                </li>
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
                                    <strong>
                                        <fmt:message key="label.wrongUserInputData" />
                                    </strong>
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            </c:if>
                            <h2 class="text-uppercase text-center mb-5"><fmt:message key="label.createAccount" /></h2>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="inputFullName">
                                        <fmt:message key="label.fullName" />
                                    </label>
                                    <input type="text" name="inputFullName" id="inputFullName" class="form-control form-control-lg"
                                           value="<%=userDto!=null?userDto.getFullName():""%>"
                                    />
                                </div>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="inputDob">
                                        <fmt:message key="label.birthDay" />
                                    </label>
                                    <input type="date" name="inputDob" id="inputDob" class="form-control form-control-lg"
                                           value="<%=userDto!=null?userDto.getDob():""%>"
                                    />
                                </div>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="inputPhoneNumber">
                                        <fmt:message key="label.phoneNumber" />
                                    </label>
                                    <input type="tel" name="inputPhoneNumber" id="inputPhoneNumber" class="form-control form-control-lg"
                                           value="<%=userDto!=null?userDto.getPhoneNumber():""%>"/>
                                    <c:if test="${sessionScope.get('errorMessages') != null && sessionScope.get('errorMessages').contains('phoneNumber')}">
                                        <span class="text-danger">
                                            <fmt:message key="label.wrongPhoneNumber" />
                                        </span>
                                    </c:if>
                                </div>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="inputEmail"><fmt:message key="label.email" /></label>
                                    <input type="email" name="inputEmail" id="inputEmail" class="form-control form-control-lg"
                                           value="<%=userDto!=null?userDto.getEmail():""%>"/>
                                    <c:if test="${sessionScope.get('errorMessages') != null && sessionScope.get('errorMessages').contains('email') }">
                                        <span class="text-danger">
                                            <fmt:message key="label.wrongEmail" />
                                        </span>
                                    </c:if>
                                </div>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="inputPassword"><fmt:message key="label.password" /></label>
                                    <input type="password" name="inputPassword" id="inputPassword" class="form-control form-control-lg"
                                           value="<%=userDto!=null?userDto.getPassword():""%>"/>
                                    <c:if test="${sessionScope.get('errorMessages') != null && sessionScope.get('errorMessages').contains('password') }">
                                        <span class="text-danger">
                                            <fmt:message key="label.wrongPassword" />
                                        </span>
                                    </c:if>
                                </div>

                                <div class="form-outline mb-4">
                                    <label class="form-label" for="inputConfirmPassword"><fmt:message key="label.repeatPassword" /></label>
                                    <input type="password" name="inputConfirmPassword" id="inputConfirmPassword" class="form-control form-control-lg"
                                           value="<%=userDto!=null?userDto.getConfirmPassword():""%>"/>
                                    <c:if test="${sessionScope.get('errorMessages') != null && sessionScope.get('errorMessages').contains('confirmPassword') }">
                                        <span class="text-danger">
                                            <fmt:message key="label.wrongConfirmPassword" />
                                        </span>
                                    </c:if>
                                </div>

                                <div class="form-check d-flex justify-content-center mb-5">
                                    <input
                                            class="form-check-input me-2"
                                            type="checkbox"
                                            value=""
                                            id="form2Example3cg"
                                    />
                                    <label class="form-check-label">
                                        <fmt:message key="label.agree" /> <a href="#!" class="text-body"><u><fmt:message key="label.service" /></u></a>
                                    </label>
                                </div>

                                <div class="d-flex justify-content-center">
                                    <button type="submit" class="btn btn-success btn-block btn-lg gradient-custom-4 text-body"><fmt:message key="label.signUp" /></button>
                                </div>

                                <p class="text-center text-muted mt-5 mb-0"><fmt:message key="label.haveAlreadyAnAccount" /> <a href="logIn.jsp" class="fw-bold text-body"><u><fmt:message key="label.loginHere" /></u></a></p>

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