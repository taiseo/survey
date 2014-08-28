<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	<title>${pageTitle}</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/lib/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/style.css" />
</head>
<body>
<c:if test="${not empty sessionScope.user}">
	<div class="navbar  limit-width  center-block">
	  <div class="navbar-inner">
	    <a class="brand" href="#">고객 만족 설문</a>
	    <ul class="nav">
	      <li class="active"><a href="#">Home</a></li>
	      <li><a href="#">Link</a></li>
	      <li><a href="<%= request.getContextPath() %>/logout">로그아웃</a></li>
	    </ul>
	  </div>
	</div>
</c:if>