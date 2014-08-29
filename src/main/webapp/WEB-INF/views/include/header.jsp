<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>${pageTitle}</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/lib/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/style.css" />
</head>
<body>
	<c:if test="${not empty sessionScope.user}">
		<div class="navbar  limit-width  center-block">
			<div class="navbar-inner">
				<a class="brand" href="<%=request.getContextPath()%>">고객 만족 설문</a>
				<ul class="nav">
					<c:if test="${sessionScope.user.userLevel == 'admin'}">
						<li><a href="<%=request.getContextPath()%>/users">사용자 관리</a></li>
					</c:if>
					<c:if test="${sessionScope.user.userLevel != 'admin' }">
						<li><a href="<%=request.getContextPath()%>/users/${sessionScope.user.id}">내 정보</a></li>
					</c:if>
				</ul>
				<ul class="nav pull-right">
					<li>
						<a href="<%= request.getContextPath() %>/logout">
						${sessionScope.user.name } 로그아웃
						</a>
					</li>
				</ul>
			</div>
		</div>
	</c:if>
	
	<div class="limit-width center-block">
		<c:if test="${not empty msg }">
			<div class="alert">
				<strong>알림!</strong> ${msg }
			</div>
		</c:if>
		<c:if test="${not empty error_msg }">
			<div class="alert alert-error">
				<strong>에러!</strong> ${error_msg }
			</div>
		</c:if>
		<c:if test="${not empty success_msg }">
			<div class="alert alert-success">
				<strong>성공!</strong> ${success_msg }
			</div>
		</c:if>
		<c:if test="${not empty info_msg }">
			<div class="alert alert-info">
				<strong>알림!</strong> ${info_msg }
			</div>
		</c:if>
	</div>