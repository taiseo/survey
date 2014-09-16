<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<jsp:include page="include/header.jsp" />
<div class="limit-width  center-block">

	<div class="page-header">
		<h1>${pageTitle}</h1>
	</div>

	<form class="base-margin" onsubmit="return false;">
		<table class="table  table-striped">
		<colgroup>
			<col style="width: 15%"/>
		</colgroup>
		<tbody>
		<tr>
			<th>기간</th>
			<td>
				<input class="datepicker  span2" type="text" name="startDate" value="${startDate }"/>
				~
				<input class="datepicker  span2" type="text" name="endDate" value="${endDate }"/>
			</td>
		</tr>
		<tr>
			<th>지사별 검색</th>
			<td>
				<select name="branch">
					<option value="">전체 지사</option>
					<c:forEach items="${branchList }" var="branch">
						<option value="${branch }">${branch }</option>
					</c:forEach>
				</select>
				<input class="btn btn-primary" type="submit" value="검색" onclick="search_by_branch()" />
			</td>
		</tr>
		<tr>
			<th>사용자별 검색</th>
			<td>
				<select name="part">
					<option value="">전체 부서</option>
					<c:forEach items="${partList }" var="part">
						<option value="${part }">${part }</option>
					</c:forEach>
				</select>
				
				<select name="username">
					<option value="">전체 사용자</option>
					<c:forEach items="${userList }" var="user">
						<option value="${user.username }" class="js-part-${user.part }">${user.name }</option>
					</c:forEach>
				</select>
				<input class="btn btn-primary" type="submit" value="검색" onclick="search_by_user()" />
			</td>
		</tr>
		</tbody>
		</table>
	</form>
	
	<div class="js-search-result"></div>
	
</div>
<jsp:include page="include/footer.jsp" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/history.js"></script>