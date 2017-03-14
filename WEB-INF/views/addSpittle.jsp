<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<head>
    <title>Spittr</title>
    <style type="text/css">
		label{
			align:left;
		}
		
		table{
			margin-left:40%;
			width:800px;
		}
		
		.error{
			border-color: red;
		}
		
		.errors{
			color: #cc0033;
		}
		
	</style>
</head>

<div class="spittleForm">
  <h1>Add Spittle</h1>
  <sf:form method="POST" commandName="spittle" >
  <table>
<!--   	<tr> -->
<%--   		<td width="100"><sf:label path="latitude" cssErrorClass="error">Latitude</sf:label>: </td> --%>
<%--   		<td width="200"><sf:input path="latitude" cssErrorClass="error" /></td> --%>
<%--   		<td><sf:errors path="latitude" element="sapn" cssClass="errors" /></td> --%>
<!--   	</tr> -->
<!--   	<tr> -->
<%--   		<td width="100"><sf:label path="longitude" cssErrorClass="error">Longitude</sf:label>:</td> --%>
<%--   		<td width="200"><sf:input path="longitude" cssErrorClass="error" /></td> --%>
<%--   		<td><sf:errors path="longitude" element="sapn" cssClass="errors" /></td> --%>
<!--   	</tr> -->
  	<tr>
  		<td width="100"><sf:label path="message" cssErrorClass="error">Message</sf:label>:</td>
  		<td width="200"><sf:input path="message" cssErrorClass="error" /><br/></td>
  		<td><sf:errors path="message" element="sapn" cssClass="errors" /></td>
  	</tr>
  	<tr>
  		<td colspan="3" style="align:center;padding-left:140px;padding-top: 20px;"><input type="submit" value="Add Spittle" /></td>
  	</tr>
  </table>
  </sf:form>
</div>