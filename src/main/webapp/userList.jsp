<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--<%@ page import="ua.domain.User" %>--%>
<%--<%@ page import="ua.dao.UserMySqlDao" %>--%>
<%--<%@ page import="java.util.List" %>--%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="userService" class="ua.services.UserServiceImpl"/>
<html lang="en">
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
    </style>
</head>
<body>
<div class="container">
    <div class="table-wrapper">
        <div class="table-title">
            <div class="row">
                <div class="col-sm-6">
                    <h2>Manage <b>Users</b></h2>
                </div>
                <div class="col-sm-6">
                    <a href="#addEmployeeModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i>
                        <span>Add New User</span></a>
                    <a href="#deleteEmployeeModal" class="btn btn-danger" data-toggle="modal"><i class="material-icons">&#xE15C;</i>
                        <span>Delete</span></a>
                </div>
            </div>
        </div>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th>Id</th>
                <th>Role</th>
                <th>Email</th>
                <th>User password</th>
                <th>Is active</th>
                <th>Created</th>
                <th>Updated</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="p" items="${userList}">
                <tr>
                    <td>${p.id}</td>
                    <td>${p.role}</td>
                    <td>${p.email}</td>
                    <td>${p.password}</td>
                    <td>${p.isActive}</td>
                    <td>${p.created}</td>
                    <td>${p.update}</td>
                    <td>
                        <a href="#editEmployeeModal" class="edit" data-toggle="modal">
                            <i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
                        <a href="?page=${currentPage}&id=${p.id}&isActive=${p.isActive}" class="delete" data-toggle="modal">
                            <i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>


    </div>

    <nav aria-label="...">
        <ul class="pagination">
            <c:if test="${currentPage != 1}">
                <li class="page-item">
                    <a class="page-link" tabindex="-1" aria-disabled="true" href="users?page=${currentPage - 1}">Previous</a>
                </li>
            </c:if>

            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item active"><a class="page-link" href="#">${i}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link" href="users?page=${i}">${i}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage lt noOfPages}">
                <li class="page-item"><a class="page-link" href="users?page=${currentPage+ 1}">Next</a></li>
            </c:if>
        </ul>
    </nav>


</div>


<!-- Edit Modal HTML -->
<div id="addEmployeeModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form>
                <div class="modal-header">
                    <h4 class="modal-title">Add Employee</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Name</label>
                        <input type="text" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Address</label>
                        <textarea class="form-control" required></textarea>
                    </div>
                    <div class="form-group">
                        <label>Phone</label>
                        <input type="text" class="form-control" required>
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
            <form>
                <div class="modal-header">
                    <h4 class="modal-title">Edit Employee</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>Name</label>
                        <input type="text" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Address</label>
                        <textarea class="form-control" required></textarea>
                    </div>
                    <div class="form-group">
                        <label>Phone</label>
                        <input type="text" class="form-control" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" class="btn btn-info" value="Save">
                </div>
            </form>
        </div>
    </div>
</div>
<!-- Delete Modal HTML -->
<div id="deleteEmployeeModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <form>
                <div class="modal-header">
                    <h4 class="modal-title">Delete Employee</h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                </div>
                <div class="modal-body">
                    <p>Are you sure you want to delete these Records?</p>
                    <p class="text-warning">
                        <small>This action cannot be undone.</small>
                    </p>
                </div>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
                    <input type="submit" class="btn btn-danger" value="Delete">
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>






































<%--<head>--%>
<%--    <!-- Required meta tags -->--%>
<%--    <meta charset="utf-8">--%>
<%--    <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1">--%>
<%--    <title>User list</title>--%>
<%--    <!-- Bootstrap CSS -->--%>
<%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">--%>
<%--</head>--%>


<%--<body>--%>
<%--<div class="container">--%>
<%--    <div class="table-wrapper">--%>
<%--        <div class="table-title">--%>
<%--            <div class="row">--%>
<%--                <div class="col-sm-6">--%>
<%--                    <h2>Manage <b>Users</b></h2>--%>
<%--                </div>--%>
<%--                <div class="col-sm-6">--%>
<%--                    <a href="signUp.jsp" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add New User</span>--%>
<%--                    </a>--%>
<%--                    <a href="#deleteEmployeeModal" class="btn btn-danger" data-toggle="modal"><i class="material-icons">&#xE15C;</i> <span>Delete</span>--%>
<%--                    </a>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--        <table class="table table-striped table-hover">--%>
<%--            <thead>--%>
<%--            <tr>--%>
<%--                <th>--%>
<%--							<span class="custom-checkbox">--%>
<%--								<input type="checkbox" id="selectAll">--%>
<%--								<label for="selectAll"></label>--%>
<%--							</span>--%>
<%--                </th>--%>
<%--                <th>Id</th>--%>
<%--                <th>Role</th>--%>
<%--                <th>Email</th>--%>
<%--                <th>User password</th>--%>
<%--                <th>Is active</th>--%>
<%--                <th>Created</th>--%>
<%--                <th>Updated</th>--%>
<%--                <th>Actions</th>--%>
<%--            </tr>--%>
<%--            </thead>--%>
<%--            <tbody>--%>
<%--&lt;%&ndash;            <tr>&ndash;%&gt;--%>

<%--                    <c:forEach items="${userService.all}" var="u">--%>
<%--                        <tr>--%>
<%--                            <td>--%>
<%--                                <span class="custom-checkbox">--%>
<%--                                    <input type="checkbox" id="checkbox1" name="options[]" value="1">--%>
<%--                                    <label for="checkbox1"></label>--%>
<%--                                </span>--%>
<%--                            </td>--%>
<%--                            <td>${u.getId()}</td>--%>
<%--                            <td>${u.getRole()}</td>--%>
<%--                            <td>${u.getEmail()}</td>--%>
<%--                            <td>${u.getPassword()}</td>--%>
<%--                            <td>${u.isActive()}</td>--%>
<%--                            <td>${u.getCreated()}</td>--%>
<%--                            <td>${u.getUpdate()}</td>--%>
<%--                            <td><a href="#editEmployeeModal" class="edit" data-toggle="modal">--%>
<%--                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">--%>
<%--                                    <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>--%>
<%--                                    <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>--%>
<%--                                </svg>--%>
<%--                            </a></td>--%>
<%--                            <td><a href="#deleteEmployeeModal" class="delete" data-toggle="modal">--%>
<%--                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">--%>
<%--                                    <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>--%>
<%--                                    <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>--%>
<%--                                </svg>--%>
<%--                            </a></td>--%>
<%--                        </tr>--%>
<%--                    </c:forEach>--%>

<%--            </tbody>--%>
<%--        </table>--%>
<%--        <div class="clearfix">--%>
<%--            <div class="hint-text">Showing <b>5</b> out of <b>25</b> entries</div>--%>
<%--            <ul class="pagination">--%>
<%--                <li class="page-item disabled"><a href="#">Previous</a></li>--%>
<%--                <li class="page-item"><a href="#" class="page-link">1</a></li>--%>
<%--                <li class="page-item"><a href="#" class="page-link">2</a></li>--%>
<%--                <li class="page-item active"><a href="#" class="page-link">3</a></li>--%>
<%--                <li class="page-item"><a href="#" class="page-link">4</a></li>--%>
<%--                <li class="page-item"><a href="#" class="page-link">5</a></li>--%>
<%--                <li class="page-item"><a href="#" class="page-link">Next</a></li>--%>
<%--            </ul>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>
<%--<div id="addEmployeeModal" class="modal fade">--%>
<%--    <div class="modal-dialog">--%>
<%--        <div class="modal-content">--%>
<%--            <form>--%>
<%--                <div class="modal-header">--%>
<%--                    <h4 class="modal-title">Add User</h4>--%>
<%--                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>--%>
<%--                </div>--%>
<%--                <div class="modal-body">--%>
<%--                    <div class="form-group">--%>
<%--                        <label>Full name</label>--%>
<%--                        <input type="text" class="form-control" required>--%>
<%--                    </div>--%>
<%--                    <div class="form-group">--%>
<%--                        <label>Phone number</label>--%>
<%--                        <input type="tel" class="form-control" required>--%>
<%--                    </div>--%>
<%--                    <div class="form-group">--%>
<%--                        <label>Date of birthd</label>--%>
<%--                        <input type="date" class="form-control" required>--%>
<%--                    </div>--%>
<%--                    <div class="form-group">--%>
<%--                        <label>Weight</label>--%>
<%--                        <input type="number" class="form-control" required>--%>
<%--                    </div>--%>
<%--                    <div class="form-group">--%>
<%--                        <label>Height</label>--%>
<%--                        <input type="number" class="form-control" required>--%>
<%--                    </div>--%>

<%--                    <div class="form-group">--%>

<%--                        <label>Sex</label>--%>
<%--                    <div>--%>
<%--                        <ul class="list-group list-group-horizontal">--%>
<%--                            <li class="list-group-item">--%>
<%--                                <input class="form-check-input" type="radio" name="gridRadios" id="gridRadios1" value="option1">--%>
<%--                                <label class="form-check-label" for="gridRadios1">--%>
<%--                                    Male</label>--%>
<%--                            </li>--%>
<%--                            <li class="list-group-item">--%>
<%--                                <input class="form-check-input" type="radio" name="gridRadios" id="gridRadios2" value="option2">--%>
<%--                                <label class="form-check-label" for="gridRadios1">--%>
<%--                                    Female</label>--%>
<%--                            </li>--%>
<%--                        </ul>--%>
<%--                    </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <div class="modal-footer">--%>
<%--                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">--%>
<%--                    <input type="submit" class="btn btn-success" value="Add">--%>
<%--                </div>--%>
<%--            </form>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>
<%--<!-- Edit Modal HTML -->--%>
<%--<div id="editEmployeeModal" class="modal fade">--%>
<%--    <div class="modal-dialog">--%>
<%--        <div class="modal-content">--%>
<%--            <form>--%>
<%--                <div class="modal-header">--%>
<%--                    <h4 class="modal-title">Edit User</h4>--%>
<%--                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>--%>
<%--                </div>--%>
<%--                <div class="modal-body">--%>
<%--                    <div class="form-group">--%>
<%--                        <label>Full name</label>--%>
<%--                        <input type="text" class="form-control" required>--%>
<%--                    </div>--%>
<%--                    <div class="form-group">--%>
<%--                        <label>Phone number</label>--%>
<%--                        <input type="tel" class="form-control" required>--%>
<%--                    </div>--%>
<%--                    <div class="form-group">--%>
<%--                        <label>Date of birthd</label>--%>
<%--                        <input type="date" class="form-control" required>--%>
<%--                    </div>--%>
<%--                    <div class="form-group">--%>
<%--                        <label>Weight</label>--%>
<%--                        <input type="number" class="form-control" required>--%>
<%--                    </div>--%>
<%--                    <div class="form-group">--%>
<%--                        <label>Height</label>--%>
<%--                        <input type="number" class="form-control" required>--%>
<%--                    </div>--%>

<%--                    <div class="form-group">--%>

<%--                        <label>Sex</label>--%>
<%--                    <div>--%>
<%--                        <ul class="list-group list-group-horizontal">--%>
<%--                            <li class="list-group-item">--%>
<%--                                <input class="form-check-input" type="radio" name="gridRadios" id="gridRadios1" value="option1">--%>
<%--                                <label class="form-check-label" for="gridRadios1">--%>
<%--                                    Male</label>--%>
<%--                            </li>--%>
<%--                            <li class="list-group-item">--%>
<%--                                <input class="form-check-input" type="radio" name="gridRadios" id="gridRadios2" value="option2">--%>
<%--                                <label class="form-check-label" for="gridRadios1">--%>
<%--                                    Female</label>--%>
<%--                            </li>--%>
<%--                        </ul>--%>
<%--                    </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <div class="modal-footer">--%>
<%--                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">--%>
<%--                    <input type="submit" class="btn btn-info" value="Save">--%>
<%--                </div>--%>
<%--            </form>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>
<%--<!-- Delete Modal HTML -->--%>
<%--<div id="deleteEmployeeModal" class="modal fade">--%>
<%--    <div class="modal-dialog">--%>
<%--        <div class="modal-content">--%>
<%--            <form>--%>
<%--                <div class="modal-header">--%>
<%--                    <h4 class="modal-title">Delete User</h4>--%>
<%--                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>--%>
<%--                </div>--%>
<%--                <div class="modal-body">--%>
<%--                    <p>Are you sure you want to delete these Records?</p>--%>
<%--                    <p class="text-warning"><small>This action cannot be undone.</small></p>--%>
<%--                </div>--%>
<%--                <div class="modal-footer">--%>
<%--                    <input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">--%>
<%--                    <input type="submit" class="btn btn-danger" value="Delete">--%>
<%--                </div>--%>
<%--            </form>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>
<%--<script>$(document).ready(function(){--%>
<%--    $('[data-toggle="tooltip"]').tooltip();--%>

<%--    var checkbox = $('table tbody input[type="checkbox"]');--%>
<%--    $("#selectAll").click(function(){--%>
<%--        if(this.checked){--%>
<%--            checkbox.each(function(){--%>
<%--                this.checked = true;--%>
<%--            });--%>
<%--        } else{--%>
<%--            checkbox.each(function(){--%>
<%--                this.checked = false;--%>
<%--            });--%>
<%--        }--%>
<%--    });--%>
<%--    checkbox.click(function(){--%>
<%--        if(!this.checked){--%>
<%--            $("#selectAll").prop("checked", false);--%>
<%--        }--%>
<%--    });--%>
<%--});</script>--%>
<%--</body>--%>

<%--</html>--%>