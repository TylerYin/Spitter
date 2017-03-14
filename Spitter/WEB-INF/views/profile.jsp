<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<head>
    <title>Spittr</title>
    <style type="text/css">
		table{
			width:550px;
			border:1px solid gray;
			cellspacing:1px;
			margin-left:36%;
		}
		
		th{
			border:1px solid gray;
			cellspacing:1px;
		}
		
		td{
			border:1px solid gray;
			cellspacing:1px;
		}
	</style>
</head>

<h1>Your Profile</h1>
<table>
	<tr>
		<td>User Name</td><td>${spitter.username}</td>
	</tr>
	<tr>
		<td>First Name</td><td>${spitter.firstname}</td>
	</tr>
	<tr>
		<td>Last Name</td><td>${spitter.lastName}</td>
	</tr>
	<tr>
		<td>Email</td><td>${spitter.email}</td>
	</tr>
</table>
