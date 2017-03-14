<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<h1><s:message code="user.add" /></h1>

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
		
		.errors{
			color: red;
		}
	</style>
</head>

<sf:form method="POST" commandName="spitter" >
  <table>
  	<tr>
  		<td width="100"><sf:label path="firstname" cssErrorClass="error">First Name</sf:label>: </td>
  		<td width="200"><sf:input path="firstname" cssErrorClass="error" /></td>
  		<td><sf:errors path="firstname" element="sapn" cssClass="errors" /></td>
  	</tr>
  	<tr>
  		<td width="100"><sf:label path="lastName" cssErrorClass="error">Last Name</sf:label>:</td>
  		<td width="200"><sf:input path="lastName" cssErrorClass="error" /></td>
  		<td><sf:errors path="lastName" element="sapn" cssClass="errors" /></td>
  	</tr>
  	<tr>
  		<td width="100"><sf:label path="email" cssErrorClass="error">Email</sf:label>:</td>
  		<td width="200"><sf:input path="email" cssErrorClass="error" /><br/></td>
  		<td><sf:errors path="email" element="sapn" cssClass="errors" /></td>
  	</tr>
  	<tr>
  		<td width="100"><sf:label path="username" cssErrorClass="error">Username</sf:label>:</td>
  		<td width="200"><sf:input path="username" cssErrorClass="error" /></td>
  		<td><sf:errors path="username" element="sapn" cssClass="errors" /></td>
  	</tr>
  	<tr>
  		<td width="100"><sf:label path="password" cssErrorClass="error">Password</sf:label>:</td>
  		<td width="200"><sf:password path="password" cssErrorClass="error" /></td>
  		<td><sf:errors path="password" element="sapn" cssClass="errors" /></td>
  	</tr>
  	<tr>
  		<td colspan="3" style="align:center;padding-left:100px;padding-top: 20px;"><input type="submit" value="Register" /></td>
  	</tr>
  </table>
</sf:form>
