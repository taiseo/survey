<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="question" scope="request" type="com.freeneo.survey.domain.Question"/>
<div class="client-question" data-id="${question.id }" data-type="${question.type }">
	<h2>
		[1~${fn:length(question.responseItems) } 숫자로 응답]
		${question.content }
		<small>${question.contentDetail }</small>
	</h2>
	<div class="response-items">
		<form onsubmit="return false;">
		<c:forEach items="${question.responseItems }" var="responseItem">
		<label>
			<input class="js-number" type="text" style="width: 20px" name="content"/>
			${responseItem.content }
		</label>
		</c:forEach>
		</form>
	</div>
</div>
