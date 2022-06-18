<%@ page import="ua.dto.PublisherDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<%
    PublisherDto publisherDto = ((PublisherDto) session.getAttribute("publisherCreateDto"));
%>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Document</title>
</head>
<body>
<div class="modal-dialog">
    <div class="modal-content">
        <form method="post" action="/create-publisher" enctype="multipart/form-data">
            <div class="modal-header">
                <h4 class="modal-title">Add Publisher</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <b style="color: red">*</b><label>Name</label>
                    <input type="text" name="inputPublisherName" class="form-control" required
                           value="<%=publisherDto!=null?publisherDto.getName():""%>"/>
                    <c:if test="${sessionScope.get('publisherErrorMessages') != null &&
                    sessionScope.get('publisherErrorMessages').contains('publisherName')}">
                            <span class="text-danger">
<%--                                <fmt:message key="label.wrongFullNume" />--%>
                                Publisher name must be less than 40 symbols
                            </span>
                    </c:if>
                    <c:if test="${sessionScope.get('publisherErrorMessages') != null &&
                    sessionScope.get('publisherErrorMessages').contains('publisherNameExist')}">
                            <span class="text-danger">
<%--                                <fmt:message key="label.wrongFullNume" />--%>
                                This publisher already exists
                            </span>
                    </c:if>
                </div>
                <div class="form-group pt-2 pb-2">
                    <b style="color: red">*</b>
                    <select name="inputTopic" required>
                        <option value="<%=publisherDto!=null?publisherDto.getTopic():""%>"><%=publisherDto!=null?publisherDto.getTopic():"Chose topic"%></option>
                        <c:forEach var="p" items="${sessionScope.get('allTopics')}">
                            <option value="${p}">${p}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <b style="color: red">*</b><label>Price</label>
                    <input type="number" name="inputPrice" step=".01" class="form-control"
                           value="<%=publisherDto!=null?publisherDto.getPrice():""%>" required/>
                    <c:if test="${sessionScope.get('publisherErrorMessages') != null &&
                    sessionScope.get('publisherErrorMessages').contains('publisherPriceIsNull')}">
                            <span class="text-danger">
<%--                                <fmt:message key="label.wrongFullNume" />--%>
                                Chose publisher price!
                            </span>
                    </c:if>
                </div>
                <div class="form-group">
                    <label>Description</label>
                    <input type="text" name="inputDescription" class="form-control"
                           value="<%=publisherDto!=null?publisherDto.getDescription():""%>">
                    <c:if test="${sessionScope.get('publisherErrorMessages') != null &&
                    sessionScope.get('publisherErrorMessages').contains('publisherDescription')}">
                            <span class="text-danger">
<%--                                <fmt:message key="label.wrongFullNume" />--%>
                                Publisher's description must be less than 500 symbols!
                            </span>
                    </c:if>
                </div>
                <div class="form-group">
                    <label>Picture</label>
                    <input type="file" name="file" class="form-control">
                </div>
            </div>
            <div class="modal-footer">
                <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                <input type="submit" class="btn btn-success" value="Add">
            </div>
        </form>
    </div>
</div>
</body>
</html>
