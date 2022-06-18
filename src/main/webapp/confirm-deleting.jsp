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
<%--        <form action="/delete" method="post">--%>
        <div class="modal-header">
            <h5 class="modal-title">Confirm deleting</h5>
        </div>
        <div class="modal-body">
            <p>Are you sure you want to delete <b>${sessionScope.get("sessionName")}</b> publisher?</p>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><a href="/publishers?page=${sessionScope.get('page')}" style="text-decoration: none; color: white">Close</a></button>
            <button type="button" class="btn btn-danger">
                <a href="/publishers?deletePublisher=${sessionScope.get("sessionName")}&page=${sessionScope.get('page')}" style="text-decoration: none; color: white">Delete</a></button>
        </div>
<%--        </form>--%>
    </div>
</div>
</body>
</html>
