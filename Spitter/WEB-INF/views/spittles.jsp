<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

<div class="listTitle">
  <h1>Recent Spittles</h1>
  <table>
	<tr>
		<th>Message</th><th>Date Time</th>
	</tr>
	
	<c:forEach items="${spittleList}" var="spittle" >
		<tr>
			<td>${spittle.message}</td><td>${spittle.postedTime}</td>
		</tr>
	</c:forEach>
  </table>
  
  <c:url value="/spitter/downUserListExcel" var="downUserListExcelUrl"/>
  <c:url value="/spitter/downUserListPDF" var="downUserListPDFUrl"/>
  <c:url value="/spitter/downUserListXML" var="downUserListXMLUrl"/>
  <c:url value="/spitter/downUserListJSON" var="downUserListJSONUrl"/>
	
  <div><h2>
	<span><a href="${downUserListExcelUrl}">下载员工列表Excel</a></span>
	<span><a href="${downUserListPDFUrl}">下载员工列表PDF</a></span>
	<span><a href="${downUserListXMLUrl}">下载员工列表XML</a></span>
	<span><a href="${downUserListJSONUrl}">下载员工列表JSON</a></span>
  </h2></div>
  
  <c:if test="${fn:length(spittleList) gt 20}">
    <hr />
    <s:url value="/spittles?count=${nextCount}" var="more_url" />
    <a href="${more_url}">Show more</a>
  </c:if>
</div>