<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib uri="http://www.okjsp.pe.kr/ok-taglib" prefix="pagination" %>

<jsp:include page="include/header.jsp" />

<div class="limit-width  center-block">
	<div class="page-header">
		<h1>${pageTitle }</h1>
	</div>

	<p class="text-right">
		<a href="<%=request.getContextPath() %>/target-groups/insert" class="btn  btn-primary">
			새 캠페인(타겟) 그룹
		</a>
	</p>	

	
	<table class="table  table-striped">
		<thead>
			<tr>
				<th nowrap="nowrap">No</th>
				<th nowrap="nowrap">제목</th>
				<th nowrap="nowrap">사업분류1</th>
				<th nowrap="nowrap">사업분류2</th>
				<th nowrap="nowrap">지역</th>
				<th nowrap="nowrap">지사</th>
				<th nowrap="nowrap"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${pagedListHolder.pageList}" varStatus="status">
				<tr>
					<td>
						${pagedListHolder.nrOfElements - (pagedListHolder.pageSize * pagedListHolder.page) - status.count +1}
						<c:set var="index" value="${pagedListHolder.nrOfElements - (pagedListHolder.pageSize * pagedListHolder.page) - status.count +1}"/>
					</td>
					<td>
						<a href="<%= request.getContextPath() %>/target-groups/update/${item.id}">
							${item.title }
						</a>
					</td>
					<td>${item.category1 }</td>
					<td>${item.category2 }</td>
					<td>${item.bonbu }</td>
					<c:if test="${fn:length(item.branches) < 20 }">
						<td style="width: 150px">${item.branches }</td>
					</c:if>
					<c:if test="${fn:length(item.branches) > 20 }">
						<td style="width: 150px">
							<h2 class="jquery-on-container">
								<a class="js-open-target  btn btn-info btn-small"
									data-target=".closed-content_${index }">펼치기</a>
							</h2>
							<div class="closed-content_${index }">${item.branches }</div>
						</td>
					</c:if>
					<td>
						<a href="<%= request.getContextPath() %>/target-groups/delete/${item.id}" class="btn btn-danger">삭제</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<p class="text-right">
		<a href="<%=request.getContextPath() %>/target-groups/insert" class="btn  btn-primary">
			새 캠페인(타겟) 그룹
		</a>
	</p>
	
	<% String path = request.getContextPath() + "/target-groups"; %>
	<pagination:paging pageListHolder="${pagedListHolder}" offset="10" contextRoot="<%= path %>"/>
</div>

<jsp:include page="include/footer.jsp" />
