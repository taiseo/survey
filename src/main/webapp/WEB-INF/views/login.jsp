<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="include/header.jsp" />

<div class="login-form-wrapper">
	<form class="form-horizontal  login-form" method="post"
		action="<%=request.getContextPath()%>/login">
		
		<!-- <img style="width: 400px; height: 150px;" 
			class="l-block  base-spacing" src="http://lorempixel.com/400/150" alt="" /> -->
		<div class="login-logo">
			<img class="l-block" 
				src="<%=request.getContextPath() %>/resources/images/logo.gif" alt="한국농어촌공사" />
		</div>
		<div class="login-form-inner  base-padding">
			<h1 class="page-title  text-center" style="margin-top: 0">고객만족 설문조사 시스템</h1> 
			
			<div class="base-margin">
				<label for="username">아이디</label>
				<input type="text" id="username" name="username" style="width: 300px">
			</div>
			<div class="base-margin">
				<label for="password">암호</label>
				<input type="password" id="password" name="password" style="width: 300px">
			</div>
			
			<p class="text-center  no-margin-bottom">
				<button type="submit" class="btn  btn-primary">로그인</button>
			</p>
		</div>
	</form>


</div>
<jsp:include page="include/footer.jsp" />