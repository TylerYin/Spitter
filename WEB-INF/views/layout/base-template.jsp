<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>   
    
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<%@ page session="false" %>
<html>
  <head>
    <title>Spittr</title>
    <link rel="stylesheet" type="text/css" href="<s:url value="/resources/style.css" />" >
  </head>
  <body>
    <div id="header" style="top:20px;padding-top:20px;">
      <t:insertAttribute name="header" />
    </div>
    <div id="content" style="text-align:center;">
    	<div id="left-content" style="width:260px;color:blue;font-size:18px;">
    		<c:set var="currentUser"><security:authentication property='principal.username'/></c:set>
    	
    		<div style="text-align:left"><h4>Current User : ${currentUser}</h4></div>
    		<div style="text-align:left"><a href="<c:url value="/spitter/${currentUser}" /> " style="text-decoration:none">My Account</a></div> 
			<div style="text-align:left"><a href="<c:url value="/spittles/addSpittle" />" style="text-decoration:none">Add Spittle</a></div>
    		<div style="text-align:left"><a href="<c:url value="/spittles" /> " style="text-decoration:none">Spittle Management</a></div> 
			<div style="text-align:left"><a href="<c:url value="/spitter/register" />" style="text-decoration:none">Register User</a></div>
			
			<div style="text-align:left;padding-top:40px;"><a href="<c:url value="/logout" />" style="text-decoration:none"><s:message code="user.logout" /></a></div>
    	</div>
      	<div id="right-content">
	      	<t:insertAttribute name="body" />
      	</div>
    </div>
    <div id="footer" style="padding-bottom:20px;bottom:0px;position:absolute;text-align:center;width:98%;">
      <t:insertAttribute name="footer" />
    </div>
  </body>
</html>