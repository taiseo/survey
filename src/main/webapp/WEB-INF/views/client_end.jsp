<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><jsp:include page="include/header.jsp" />

<div class="limit-width  center-block  client" data-client="${sessionId}" data-survey-id="${survey.id }">
	<div class="page-header">
		<h1>${pageTitle }</h1>
	</div>
	
	<div class="complete-page" style="display: none">
		<div class="hero-unit">
			<h3>고객님의 소중한 의견에 감사합니다.</h3>
			<p>설문기간이 종료되었습니다.</p>
		</div>
	</div>


</div>

<jsp:include page="include/footer.jsp" />