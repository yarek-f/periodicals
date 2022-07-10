<%@ page import="ua.dto.PublisherDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<%
    PublisherDto publisherDto = ((PublisherDto) session.getAttribute("publisherAddNewVersionDto"));
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
        <form method="post" action="/new-version" enctype="multipart/form-data">
            <div class="modal-header">
                <h4 class="modal-title">Add new issue of journal</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <b style="color: red">*</b><label>Name</label>
                    <select id="inputPublisherName" name="inputPublisherName">
                        <option value="<%=publisherDto!=null?publisherDto.getName():""%>"><%=publisherDto!=null?publisherDto.getName():"Chose publisher"%></option>
                        <c:forEach var="p" items="${sessionScope.get('publishers')}">
                            <option value="${p.name}">${p.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <b style="color: red">*</b><label>Version</label>
                    <input type="number" name="inputPublisherVersion" class="form-control" required
                           value="<%=publisherDto!=null?publisherDto.getVersion():""%>"/>
                    <c:if test="${sessionScope.get('publisherErrorMessages') != null &&
                    sessionScope.get('publisherErrorMessages').contains('publisherVersion')}">
                            <span class="text-danger">
<%--                                <fmt:message key="label.wrongFullNume" />--%>
                                The Number of Issue should be larger than previous
                            </span>
                    </c:if>
                </div>

                <div class="form-group">
                    <label>Picture</label>
                    <input type="file" name="file" class="form-control">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><a href="/publishers?page=${sessionScope.get('page')}" style="text-decoration: none; color: white">Close</a></button>
                <input type="submit" class="btn btn-primary" value="Add">
            </div>
        </form>
    </div>
</div>
</body>
</html>
