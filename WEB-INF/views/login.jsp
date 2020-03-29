<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<head>
<title>Spitter</title>
<link rel="stylesheet" type="text/css" th:href="@{/resources/style.css}"></link>
</head>

<body onload='document.f.username.focus();'>
	<div style="text-align:center;margin: 0 auto;width: 400px;padding-top: 160px;padding-left: 130px;">
		<form name='loginForm' th:action='/spittr/login' method='POST'>
			<table>
				<tr>
					<td>User:</td>
					<td><input type='text' name='username' value='' /></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type='password' name='password' /></td>
				</tr>
				<tr>
					<td colspan='2'><input id="remember_me" name="remember-me" type="checkbox" /> <label for="remember_me" class="inline">Remember me</label></td>
				</tr>
				<tr style="text-align: center">
					<td colspan="2"><input name="submit" type="submit" value="Login" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>