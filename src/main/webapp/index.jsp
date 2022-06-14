<%@ page import="ua.domain.Publisher" %>
<%@ page import="java.util.List" %>
<%@ page import="ua.services.PublisherServiceImpl" %>
<%@ page import="java.util.ArrayList" %>
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
                                    <a href="?topic=${topic}&sort=byName" class="link-light"><fmt:message key="label.sorting.byName"/></a>
                                </c:if>
                            </li>
                            <li>
                                <c:if test="${topic == null}">
                                    <a href="?sort=byPrice&page=${currentPage}" class="link-light"><fmt:message key="label.sorting.byPrice"/></a>
                                </c:if>
                                <c:if test="${topic != null}">
                                    <a href="?topic=${topic}&sort=byPrice" class="link-light"><fmt:message key="label.sorting.byPrice"/></a>
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
                                <li><a href="?topic=${p}" class="link-light"><fmt:message key="label.topic.fashion" /></a></li>
                            </c:if>
                            <c:if test="${p == 'NEWS'}">
                                <li><a href="?topic=${p}" class="link-light"><fmt:message key="label.topic.news" /></a></li>
                            </c:if>
                            <c:if test="${p == 'SCIENCE'}">
                                <li><a href="?topic=${p}" class="link-light"><fmt:message key="label.topic.science" /></a></li>
                            </c:if>
                            <c:if test="${p == 'MUSIC'}">
                                <li><a href="?topic=${p}" class="link-light"><fmt:message key="label.topic.music" /></a></li>
                            </c:if>
                            <c:if test="${p == 'ECONOMY'}">
                                <li><a href="?topic=${p}" class="link-light"><fmt:message key="label.topic.economy" /></a></li>
                            </c:if>
                            <c:if test="${p == 'NATURE'}">
                                <li><a href="?topic=${p}" class="link-light"><fmt:message key="label.topic.nature" /></a></li>
                            </c:if>
                            <c:if test="${p == 'OTHER'}">
                                <li><a href="?topic=${p}" class="link-light"><fmt:message key="label.topic.other" /></a></li>
                            </c:if>

                        </c:forEach>
                    </ul>
                </li>
            </ul>
            <form class="d-flex mt-3" method="post" action="/periodicals">
                <button class="btn btn-outline-success  me-2" type="submit"><b><fmt:message key="label.search"/></b></button>
                <input class="form-control me-2" type="search" name="search" placeholder="<fmt:message key="label.search" />" aria-label="Search">
            </form>
            <div>
                <a href="signUp.jsp" class="btn btn-primary"><fmt:message key="label.signUp" /></a>
                <a href="logIn.jsp" class="btn btn-primary"><fmt:message key="label.logIn" /></a>
            </div>
        </div>
    </div>
</nav>

    <div class="container col-8" style="justify-content: center">
        <c:forEach items="${publisherList}" var="p">
        <div class="row m-4"  style="background-color: white; border-radius: 5px;">
            <div class="col" style="position: relative">
                <div  style="float:left"><img src="images/${p.image}" class="p-4" alt="" style="border-radius: 26px;" width="200px" height="275"></div>
                <h3 style="text-align: center">${p.name}</h3><br><br>
                <p style="text-align: center">${p.description}</p>
                <div style="text-align: center; position: absolute; bottom: 15px; left: 555px">
                    <a href="logIn.jsp">
                        <button type="button" class="btn btn-primary "><fmt:message key="label.subscribe" />
                            <p style="margin: -3px; text-align: center">${p.price} <fmt:message key="label.uah" /></p>
                        </button>
                    </a>
                </div>

            </div>
        </div>
        </c:forEach>
        <nav aria-label="...">
            <ul class="pagination">
                <c:if test="${currentPage != 1}">
                    <li class="page-item">
                        <a:if test="${sort == null}">
                            <a class="page-link" tabindex="-1" aria-disabled="true" href="?page=${currentPage - 1}"><fmt:message key="label.previous" /></a>
                        </a:if>
                        <a:if test="${sort != null}">
                            <a class="page-link" tabindex="-1" aria-disabled="true" href="?sort=${sort}&page=${currentPage - 1}"><fmt:message key="label.previous" /></a>
                        </a:if>
                    </li>
                </c:if>

                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li class="page-item active"><a class="page-link" href="#">${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <a:if test="${sort == null}">
                                <li class="page-item"><a class="page-link" href="?page=${i}">${i}</a></li>
                            </a:if>
                            <a:if test="${sort != null}">
                                <li class="page-item"><a class="page-link" href="?sort=${sort}&page=${i}">${i}</a></li>
                            </a:if>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage lt noOfPages}">
                    <a:if test="${sort == null}">
                        <li class="page-item"><a class="page-link" href="?page=${currentPage+ 1}"><fmt:message key="label.next" /></a></li>
                    </a:if>
                    <a:if test="${sort != null}">
                        <a class="page-link" tabindex="-1" aria-disabled="true" href="?sort=${sort}&page=${currentPage + 1}"><fmt:message key="label.next" /><</a>
                    </a:if>
                </c:if>
            </ul>
        </nav>
    </div>


</div>



<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

</body>
</html>
