<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	<title>로그인</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/lib/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/style.css" />
</head>
<body>

<div class="login-form-wrapper">
<form class="form-horizontal  login-form" method="post" action="<%= request.getContextPath() %>/login">
  <h1 class="page-title">고객만족 설문조사 프로그램</h1>
  <c:if test="${not empty login_failed_msg}">
  	<p>${login_failed_msg}</p>
  </c:if>
  
  <div class="control-group">
    <label class="control-label" for="username">아이디</label>
    <div class="controls">
      <input type="text" id="username" name="username">
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="password">암호</label>
    <div class="controls">
      <input type="password" id="password" name="password">
    </div>
  </div>
  <div class="control-group">
    <div class="controls">
      <button type="submit" class="btn  btn-primary">로그인</button>
    </div>
  </div>
</form>

</div>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/lib/jquery.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/lib/bootstrap/js/bootstrap.min.js"></script>	
</body>
</html>