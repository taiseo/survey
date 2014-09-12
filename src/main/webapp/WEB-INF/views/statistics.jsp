<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="include/header.jsp" />
<div class="limit-width  center-block">
	
	<div class="page-header">
		<h1>${pageTitle } <small>${survey.respondentCount }명 응답</small></h1>
	</div>

    <ul>
    <c:forEach items="${questions }" var="question">
    	<c:if test="${question.type != '페이지-나누기' }">
    	<li>
    		${question.content } 
    		<small>${question.contentDetail }</small>
    		${question.questionRespondentCount }
    	</li>
    	</c:if>
    </c:forEach>
    </ul>

</div>
<jsp:include page="include/footer.jsp" />