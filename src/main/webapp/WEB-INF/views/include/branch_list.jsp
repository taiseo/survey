<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<select name="지사명">
	<c:if test="${fn:length(branches) > 0 }">
		<c:forEach items="${branches }" var="branch">
			<option>${branch }</option>
		</c:forEach>
	</c:if>
	<c:if test="${fn:length(branches) == 0 }">
		<option value="">해당하는 지사가 없습니다.</option>	
	</c:if>
</select>


