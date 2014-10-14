<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<c:if test="${isClient}">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</c:if>
<title>${pageTitle}</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/lib/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/style.css" />
	<script type="text/javascript">
	
	var survey = {
		context_path: '<%=request.getContextPath()%>'
	};
	</script>
	<style type="text/css">
	#dvLoading
{
   background:#000 url(<%=request.getContextPath()%>/resources/images/loader.gif) no-repeat center center;
   height: 220px;
   width: 176px;
   position: fixed;
   z-index: 1000;
   left: 50%;
   top: 50%;
   margin: -25px 0 0 -25px;
}
	</style>
</head>
<body>
	<c:if test="${not empty sessionScope.user and ! isClient}">
		<!-- 관리자 -->
		<div id="dvLoading"></div>
		<div class="navbar  limit-width  center-block">
			<div class="navbar-inner">
				<a class="brand" href="<%=request.getContextPath()%>">
					<img style="height: 20px" src="<%=request.getContextPath() %>/resources/images/logo.gif" alt="한국농어촌공사" />
				</a>
				<ul class="nav">
					<li><a href="<%=request.getContextPath()%>/surveys">설문관리</a></li>
					<li><a href="<%=request.getContextPath()%>/target-groups">캠페인(타겟)</a></li>
					<li><a href="<%=request.getContextPath()%>/history">조사 결과</a></li>
					<c:if test="${sessionScope.user.userLevel == '시스템 관리자'}">
						<li class="dropdown">
							<a href="<%=request.getContextPath()%>/users" class="dropdown-toggle" data-toggle="dropdown">
								사용자관리
								<b class="caret"></b>
							</a>
							<ul class="dropdown-menu">
								<li><a href="<%=request.getContextPath()%>/users">사용자관리</a></li>
								<li><a href="<%=request.getContextPath()%>/logs">접속기록관리</a></li>
							</ul>
						
						</li>
						<li><a href="<%=request.getContextPath()%>/config">환경설정</a></li>
					</c:if>
					<c:if test="${sessionScope.user.userLevel != '시스템 관리자' }">
						<li><a href="<%=request.getContextPath()%>/users/${sessionScope.user.id}">내 정보</a></li>
					</c:if>
					
				</ul>
				<ul class="nav pull-right">
					<li>
						<a href="<%= request.getContextPath() %>/logout">
						${sessionScope.user.name } <span class="label label-warning">로그아웃</span>
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