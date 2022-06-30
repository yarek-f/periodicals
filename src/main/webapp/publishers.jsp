<%@ page import="ua.dto.PublisherDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<%
    PublisherDto publisherDto = ((PublisherDto) session.getAttribute("publisherCreateDto"));
%>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Publishers</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style type="text/css">
        body {
            color: #566787;
            background: #f5f5f5;
            font-family: 'Varela Round', sans-serif;
            font-size: 13px;
        }

        .table-wrapper {
            background: #fff;
            padding: 20px 25px;
            margin: 30px 0;
            border-radius: 1px;
            box-shadow: 0 1px 1px rgba(0, 0, 0, 0.247);
        }

        .table-title {
            padding-bottom: 15px;
            background: linear-gradient(40deg, #2096ff, #05ffa3) !important;
            color: #fff;
            padding: 16px 30px;
            margin: -20px -25px 10px;
            border-radius: 1px 1px 0 0;
            box-shadow: 0 1px 1px rgba(0, 0, 0, 0.247);
        }

        .table-title h2 {
            margin: 5px 0 0;
            font-size: 24px;
        }

        .table-title .btn-group {
            float: right;
        }

        .table-title .btn {
            color: #fff;
            float: right;
            font-size: 13px;
            border: none;
            min-width: 50px;
            border-radius: 1px;
            border: none;
            outline: none !important;
            margin-left: 10px;
            box-shadow: 0 1px 1px rgba(0, 0, 0, 0.247);
        }

        .table-title .btn i {
            float: left;
            font-size: 21px;
            margin-right: 5px;
        }

        .table-title .btn span {
            float: left;
            margin-top: 2px;
        }

        table.table tr th, table.table tr td {
            border-color: #e9e9e9;
            padding: 12px 15px;
            vertical-align: middle;
        }

        table.table tr th:first-child {
            width: 60px;
        }

        table.table tr th:last-child {
            width: 100px;
        }

        table.table-striped tbody tr:nth-of-type(odd) {
            background-color: #fcfcfc;
        }

        table.table-striped.table-hover tbody tr:hover {
            background: #f5f5f5;
        }

        table.table th i {
            font-size: 13px;
            margin: 0 5px;
            cursor: pointer;
        }

        table.table td:last-child i {
            opacity: 0.9;
            font-size: 22px;
            margin: 0 5px;
        }

        table.table td a {
            font-weight: bold;
            color: #566787;
            display: inline-block;
            text-decoration: none;
            outline: none !important;
        }

        table.table td a:hover {
            color: #2196F3;
        }

        table.table td a.edit {
            color: #FFC107;
        }

        table.table td a.delete {
            color: #F44336;
        }

        table.table td i {
            font-size: 19px;
        }

        table.table .avatar {
            border-radius: 1px;
            vertical-align: middle;
            margin-right: 10px;
        }

        .pagination {
            float: right;
            margin: 0 0 5px;
        }

        .pagination li a {
            border: none;
            font-size: 13px;
            min-width: 30px;
            min-height: 30px;
            color: #999;
            margin: 0 2px;
            line-height: 30px;
            border-radius: 1px !important;
            text-align: center;
            padding: 0 6px;
        }

        .pagination li a:hover {
            color: #666;
        }

        .pagination li.active a, .pagination li.active a.page-link {
            background: #03A9F4;
        }

        .pagination li.active a:hover {
            background: #0397d6;
        }

        .pagination li.disabled i {
            color: #ccc;
        }

        .pagination li i {
            font-size: 16px;
            padding-top: 6px
        }

        .hint-text {
            float: left;
            margin-top: 10px;
            font-size: 13px;
        }

        /* Custom checkbox */
        .custom-checkbox {
            position: relative;
        }

        .custom-checkbox input[type="checkbox"] {
            opacity: 0;
            position: absolute;
            margin: 5px 0 0 3px;
            z-index: 9;
        }

        .custom-checkbox label:before {
            width: 18px;
            height: 18px;
        }

        .custom-checkbox label:before {
            content: '';
            margin-right: 10px;
            display: inline-block;
            vertical-align: text-top;
            background: white;
            border: 1px solid #bbb;
            border-radius: 1px;
            box-sizing: border-box;
            z-index: 2;
        }

        .custom-checkbox input[type="checkbox"]:checked + label:after {
            content: '';
            position: absolute;
            left: 6px;
            top: 3px;
            width: 6px;
            height: 11px;
            border: solid #000;
            border-width: 0 3px 3px 0;
            transform: inherit;
            z-index: 3;
            transform: rotateZ(45deg);
        }

        .custom-checkbox input[type="checkbox"]:checked + label:before {
            border-color: #03A9F4;
            background: #03A9F4;
        }

        .custom-checkbox input[type="checkbox"]:checked + label:after {
            border-color: #fff;
        }

        .custom-checkbox input[type="checkbox"]:disabled + label:before {
            color: #b8b8b8;
            cursor: auto;
            box-shadow: none;
            background: #ddd;
        }

        /* Modal styles */
        .modal .modal-dialog {
            max-width: 400px;
        }

        .modal .modal-header, .modal .modal-body, .modal .modal-footer {
            padding: 20px 30px;
        }

        .modal .modal-content {
            border-radius: 1px;
        }

        .modal .modal-footer {
            background: #ecf0f1;
            border-radius: 0 0 1px 1px;
        }

        .modal .modal-title {
            display: inline-block;
        }

        .modal .form-control {
            border-radius: 1px;
            box-shadow: none;
            border-color: #dddddd;
        }

        .modal textarea.form-control {
            resize: vertical;
        }

        .modal .btn {
            border-radius: 1px;
            min-width: 100px;
        }

        .modal form label {
            font-weight: normal;
        }
        .layer {
            overflow: auto; 
            width: 140px;
            height: 75px;
            padding: 5px;
            border: solid 1px black;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/periodicals">Navbar</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#"><h5>Publisher list</h5></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link " href="/users"><h5>User list</h5></a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="d-flex justify-content-center">
    <nav aria-label="...">
        <ul class="pagination">
            <c:if test="${currentPage != 1}">
                <li class="page-item">
                    <a class="page-link" tabindex="-1" aria-disabled="true" href="publishers?page=${currentPage - 1}">Previous</a>
                </li>
            </c:if>

            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item active"><a class="page-link" href="#">${i}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link" href="publishers?page=${i}">${i}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage lt noOfPages}">
                <li class="page-item"><a class="page-link" href="publishers?page=${currentPage+ 1}">Next</a></li>
            </c:if>
        </ul>
    </nav>
</div>

<div class="container">
    <div class="table-wrapper">
        <div class="table-title">
            <div class="row">
                <div class="col-sm-6">
                    <h2>Manage <b>Publishers</b></h2>
                </div>
                <div class="col-sm-6">
                    <a href="/add-publisher.jsp" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i>
                        <span>Add New Publisher</span></a>
                    <a href="/add-new-version.jsp" class="btn btn-primary" data-toggle="modal"><i class="material-icons">&#xE147;</i>
                        <span>Add new version</span></a>
                </div>
            </div>
    </div>
    <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th>Id</th>
                <th>Image</th>
                <th>Name</th>
                <th>Version</th>
                <th>Topic</th>
                <th>Price</th>
                <th>Description</th>
                <th>Created</th>
                <th>Updated</th>
                <th>Is active</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="p" items="${publisherList}">
                <tr>
                    <td>${p.id}</td>
                    <td><img src="images/${p.image}" width="50" height="75"></td>
                    <td><a href="/subscribed-users?publisherName=${p.name}">${p.name}</a></td>
                    <td>${p.version}</td>
                    <td>${p.topic}</td>
                    <td>${p.price}</td>
                    <td><div class="layer"><p>${p.description}</p></div></td>
                    <td>${p.create}</td>
                    <td>${p.updated}</td>
                    <td>${p.isActive}</td>
                    <td>
                        <a href="/edit-publisher?publisherName=${p.name}&page=${currentPage}" class="edit">
                            <i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i>
                            </a>
                        <a href="/delete?name=${p.name}&page=${currentPage}" class="delete" data-toggle="modal">
                            <i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
                    </td>
                </tr>
            </c:forEach>

        </tbody>
    </table>

    </div>

</div>


<!-- Add Modal HTML -->
<div id="addEmployeeModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="post" action="/create-publisher" enctype="multipart/form-data">
                <c:if test="${sessionScope.get('publisherErrorMessages') !=null}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>
<%--                            <fmt:message key="label.wrongUserInputData" />--%>
                                wrongUserInputData
                        </strong>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                <div class="modal-header">
                    <h4 class="modal-title">Add Publisher</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <b style="color: red">*</b><label>Name</label>
                        <input type="text" name="inputPublisherName" class="form-control" required
                               value="<%=publisherDto!=null?publisherDto.getName():""%>"/>
                        <c:if test="${sessionScope.get('publisherErrorMessages') != null && sessionScope.get('publisherErrorMessages').contains('publisherName')}">
                            <span class="text-danger">
<%--                                <fmt:message key="label.wrongFullNume" />--%>
                                Publisher name must be less than 40 symbols
                            </span>
                        </c:if>
                    </div>
                    <div class="form-group">
                        <b style="color: red">*</b>
                        <select name="inputTopic">
                            <option value="">Chose topic</option>
                            <c:forEach var="p" items="${sessionScope.get('allTopics')}">
                                <option value="${p}">${p}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <b style="color: red">*</b><label>Price</label>
                        <input type="number" name="inputPrice" step=".01" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Description</label>
                        <textarea class="form-control" name="inputDescription"></textarea>
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
</div>
<!-- Edit Modal HTML -->
<div id="editEmployeeModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="post" action="/edit-publisher" enctype="multipart/form-data">
                <div class="modal-header">
                    <h4 class="modal-title">Edit publisher</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <b style="color: red">*</b><label>Publisher name</label>
                        <select id="publisherName" name="publisherName">
                            <option value="">Chose publisher name</option>
                            <c:forEach var="p" items="${sessionScope.get('publishers')}">
                                <option value="${p.name}">${p.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Topic</label>
                        <select name="inputTopic">
                            <option value="">Chose topic</option>
                            <c:forEach var="p" items="${sessionScope.get('allTopics')}">
                                <option value="${p}">${p}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Price</label>
                        <input type="number" name="inputPrice" step=".01" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>Description</label>
                        <textarea class="form-control" name="inputDescription"></textarea>
                    </div>
                    <div class="form-group">
                        <label>Picture</label>
                        <input type="file" name="file" class="form-control" value="">
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" class="btn btn-warning" value="Update">
                </div>
            </form>
        </div>
    </div>
</div>
<!-- Add new version Modal HTML -->
<div id="addNewVersionModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="post" action="/new-version" enctype="multipart/form-data">
                <div class="modal-header">
                    <h4 class="modal-title">Add new issue of journal</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <b style="color: red">*</b><label>Name</label>
                        <select id="inputPublisherName" name="inputPublisherName">
                            <c:forEach var="p" items="${sessionScope.get('publishers')}">
                                <option value="${p.name}">${p.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <b style="color: red">*</b><label>Version</label>
                        <input type="number" name="inputPublisherVersion" class="form-control" required>
                    </div>

                    <div class="form-group">
                        <label>Picture</label>
                        <input type="file" name="file" class="form-control">
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" class="btn btn-primary" value="Add">
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
