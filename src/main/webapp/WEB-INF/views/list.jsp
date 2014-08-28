<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="include/header.jsp" />

<h1 class="page-title">${pageTitle}</h1>
<p><a href="<%= request.getContextPath() %>/logout">로그아웃</a></p>
<ul>
    <li>목록</li>
</ul>

<jsp:include page="include/footer.jsp" />