<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
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
      <t:insertAttribute name="body" />
    </div>
    <div id="footer" style="padding-bottom:20px;bottom:0px;position:absolute;text-align:center;width:98%;">
      <t:insertAttribute name="footer" />
    </div>
  </body>
</html>