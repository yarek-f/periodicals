<%@ page import="ua.domain.Publisher" %>
<%@ page import="java.util.List" %>
<%@ page import="ua.services.PublisherServiceImpl" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ua.dao.UserMySqlDao" %>
<%@ page import="ua.dao.CustomerMySqlDao" %>
<%@ page import="ua.services.UserService" %>
<%@ page import="ua.services.UserServiceImpl" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "a" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>


<%@ page session="true" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
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
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <fmt:message key="label.languages" />
                    </a>
                    <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDropdown">
                            <li><a href="?lang=en&page=${currentPage}" class="link-light"><fmt:message key="label.lang.en" /></a></li>
                            <li><a href="?lang=uk&page=${currentPage}" class="link-light"><fmt:message key="label.lang.uk" /></a></li>
                    </ul>
                </li>
                <li class="nav-item dropdown" id="sorting">
                    <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown2" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <fmt:message key="label.sorting" />
                    </a>
                    <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDropdown">
                            <li>
                                <c:if test="${topic == null}">
                                    <a href="?sort=byName&page=${currentPage}" class="link-light"> <fmt:message key="label.sorting.byName"/></a>
                                </c:if>
                                <c:if test="${topic != null}">
                                    <a href="?topic=${topic}&sort=byName&page=${currentPage}" class="link-light"><fmt:message key="label.sorting.byName"/></a>
                                </c:if>
                            </li>
                            <li>
                                <c:if test="${topic == null}">
                                    <a href="?sort=byPrice&page=${currentPage}" class="link-light"><fmt:message key="label.sorting.byPrice"/></a>
                                </c:if>
                                <c:if test="${topic != null}">
                                    <a href="?topic=${topic}&sort=byPrice&page=${currentPage}" class="link-light"><fmt:message key="label.sorting.byPrice"/></a>
                                </c:if>
                            </li>
                    </ul>
                </li>
                <li class="nav-item dropdown" id="byTopic">
                    <a class="nav-link dropdown-toggle active" href="#" id="navbarDropdown3" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <fmt:message key="label.topics" />
                    </a>
                    <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDropdown">
                        <c:forEach items="${sessionScope.get('publishersByTopic')}" var="p">
                            <c:if test="${p == 'FASHION'}">
                                <li><a href="?topic=${p}" class="link-light"><fmt:message key="label.topic.fashion"/></a></li>
                            </c:if>
                            <c:if test="${p == 'NEWS'}">
                                <li><a href="?topic=${p}" class="link-light"><fmt:message key="label.topic.news"/></a></li>
                            </c:if>
                            <c:if test="${p == 'SCIENCE'}">
                                <li><a href="?topic=${p}" class="link-light"><fmt:message key="label.topic.science"/></a></li>
                            </c:if>
                            <c:if test="${p == 'MUSIC'}">
                                <li><a href="?topic=${p}" class="link-light"><fmt:message key="label.topic.music"/></a></li>
                            </c:if>
                            <c:if test="${p == 'ECONOMY'}">
                                <li><a href="?topic=${p}" class="link-light"><fmt:message key="label.topic.economy"/></a></li>
                            </c:if>
                            <c:if test="${p == 'NATURE'}">
                                <li><a href="?topic=${p}" class="link-light"><fmt:message key="label.topic.nature"/></a></li>
                            </c:if>
                            <c:if test="${p == 'OTHER'}">
                                <li><a href="?topic=${p}" class="link-light"><fmt:message key="label.topic.other"/></a></li>
                            </c:if>

                        </c:forEach>
                    </ul>
                </li>
                <c:if test="${sessionScope.get('profile')!=null}">
                    <li class="nav-item">
                        <a class="nav-link active" href="#"><b style="color: orange;">Balance:</b>
<%--                            <%userService.getCustomer((String)session.getAttribute("profile")).getBalance();%>--%>
                            ${sessionScope.get("balance")}
                        </a>
                    </li>
                </c:if>
            </ul>
            <form class="d-flex mt-3" method="post" action="/periodicals">
                <button class="btn btn-outline-success  me-2" type="submit"><b><fmt:message key="label.search"/></b></button>
                <input class="form-control me-2" type="search" name="search" placeholder="<fmt:message key="label.search" />" aria-label="Search">
            </form>
                <ul class="navbar-nav mb-2 mb-lg-0">
                    <li class="nav-item dropdown">
                        <a class="nav-link active" href="#" id="navbarDropdown5" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <div class="text-truncate " style="max-width: 120px;">
                                ${sessionScope.get('profile')}</div>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-dark" aria-labelledby="navbarDropdown">
                            <li class="ps-2"><a href="#" class="link-light" style="text-decoration: none;">Profile</a></li>
<%--                                <li class="ps-2"><a href="?emailForSubscription=${sessionScope.get('profile')}" class="link-light" style="text-decoration: none;">Subscriptions</a></li>--%>
                            <li class="ps-2"><a href="/my-subscriptions" class="link-light" style="text-decoration: none;">Subscriptions</a></li>
                            <li class="ps-2"><a href="/login" class="link-light" style="text-decoration: none;"><span class="pe-2">Log out</span>
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-box-arrow-right" viewBox="0 0 16 16">
                                    <path fill-rule="evenodd" d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0v2z"/>
                                    <path fill-rule="evenodd" d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3z"/>
                                </svg>
                            </a>
                            </li>
                        </ul>
                    </li>
                </ul>
        </div>
    </div>
</nav>

<div class="conteiner col-2 mt-3" style="position: absolute; left: 0; right: 0; margin: auto; background-color: white; border-radius: 10px">
    <form  class="p-2" action="/replenish-balance" method="post">
        <span>
            <label for="exampleInputEmail1" class="form-label"><b>Balance</b></label>
            <input type="number" step=".01" name="balance" class="form-control" width="100px" id="exampleInputEmail1" aria-describedby="emailHelp" style="align-content: center">

            <c:if test="${sessionScope.get('balanceErrorMessages') != null}">
<%--            <span class="text-danger">Balance can't be negative</span>--%>
            <div class="text-danger">Balance can't be negative</div>
            </c:if>

            <%--            <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>--%>

<%--        </div>--%>
        <button type="submit" class="btn btn-primary mt-3">Submit</button>
    </form>
</div>


</div>



<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

</body>
</html>
