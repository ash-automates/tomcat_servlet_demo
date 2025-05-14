<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Test JSP</title>
</head>
<body>
    <h1>JSTL Test</h1>
    <c:choose>
        <c:when test="${1 > 0}">
            <p>JSTL is working!</p>
        </c:when>
        <c:otherwise>
            <p>JSTL is not working.</p>
        </c:otherwise>
    </c:choose>
</body>
</html>
