<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><jsp:include page="include/header.jsp" />
<div class="limit-width  center-block">

	<div class="page-header">
		<h1>${pageTitle}</h1>
	</div>

	<form class="base-margin" onsubmit="return false;" id="frm" name="frm" method="POST">
		<input type="hidden" name="isExcel" id="isExcel" value="N" />
		<input type="hidden" name="branches" id="branches" />
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
			<td class="js-search-by-branch">
				<select id="bonbu"></select>
				<select id="branch" name="branch"></select>
				<input class="btn btn-primary" type="button" value="검색" onclick="search_by_branch()" />
				<button type="button" class="btn btn-info js-search-by-branch-excel"
					style="display: none" 
					onclick="search_by_branch_excel();">Excel Download</button>
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
					<option value="" class="js-all">전체 사용자</option>
					<c:forEach items="${userList }" var="user">
						<option value="${user.username }" class="js-part-${user.part }">${user.name }</option>
					</c:forEach>
				</select>
				<input class="btn btn-primary" type="submit" value="검색" onclick="search_by_user()" />
				<button type="button" class="btn btn-info hidden js-search-by-user-excel"
					style="display: none" 
					onclick="search_by_user_excel();">Excel Download</button>
			</td>
		</tr>
		</tbody>
		</table>
	</form>
	
	<div class="js-search-result"></div>
	
</div>

<jsp:include page="include/footer.jsp" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/history.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/all_branch_selectbox_functions.js"></script>