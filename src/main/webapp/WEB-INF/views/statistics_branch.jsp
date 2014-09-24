<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.freeneo.survey.domain.Question" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="include/header.jsp" />
<div class="limit-width  center-block">
	
	<div class="page-header">
		<h1>${pageTitle } <small>${survey.respondentCount }명 응답</small></h1>
	</div>
	<c:if test="${listUrl != null}">
		<div class="text-right">
			<a href="${listUrl }" class="btn">목록</a>
		</div>
	</c:if>
	<jsp:include page="include/statistics_part.jsp"/>
	<c:if test="${listUrl != null}">
		<div class="text-right">
			<a href="${listUrl }" class="btn">목록</a>
		</div>
	</c:if>
</div>
<jsp:include page="include/footer.jsp" />