<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="question" scope="request" type="com.freeneo.survey.domain.Question"/>
<div class="client-question" data-id="${question.id }" data-type="${question.type }">
	<h2>
		${question.content }
		<small>${question.contentDetail }</small>
	</h2>
	<c:if test="${question.type == '객관식1' }">
		<c:set var="inputType" value="radio" scope="page" />
	</c:if>
	<c:if test="${question.type == '객관식2' }">
		<c:set var="inputType" value="checkbox" scope="page" />
	</c:if>
	<div class="response-items">
		<c:forEach items="${question.responseItems }" var="responseItem">
		<label>
			<input type="${inputType }" name="content" value="${responseItem.content }"/>
			<c:if test="${responseItem.content != '$$$etc$$$'}">
				${responseItem.content }
			</c:if>
			<c:if test="${responseItem.content == '$$$etc$$$'}">
				기타 : 
				<input type="text" name="content" class="span7  js-etc" />
			</c:if>
			
		</label>
		</c:forEach>
	</div>
</div>
