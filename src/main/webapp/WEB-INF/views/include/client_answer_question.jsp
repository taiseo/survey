<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="question" scope="request" type="com.freeneo.survey.domain.Question"/>
<div class="client-question" data-id="${question.id }">
	<h2>
		${question.content }
		<small>${question.contentDetail }</small>
	</h2>
	<c:if test="${question.type == '주관식1' }">
		<input type="text" name="content" class="input-block-level" />
	</c:if>
	<c:if test="${question.type == '주관식2' }">
		<textarea name="content" class="input-block-level" rows="7"></textarea>
	</c:if>
</div>
