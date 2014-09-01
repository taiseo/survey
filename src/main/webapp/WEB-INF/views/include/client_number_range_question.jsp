<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.freeneo.survey.domain.ResponseItem" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="question" scope="request" type="com.freeneo.survey.domain.Question"/>
<div class="client-question" data-id="${question.id }">
	<h2>
		${question.content }
		(${question.responseItems[0].min}
		~		
		${question.responseItems[0].max})
		<small>${question.contentDetail }</small>
	</h2>
	<div class="response-items  clearfix">
		<c:forEach var="point" begin="${question.responseItems[0].min}" end="${question.responseItems[0].max}">
			<label class="pull-left" style="margin-right: 1em">
				<input type="radio" name="content" value="${point }"/> ${point }
			</label>
		</c:forEach>
	</div>
</div>
