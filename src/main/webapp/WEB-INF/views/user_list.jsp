<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="include/header.jsp" />

<div class="limit-width center-block">
	<table class="table">
	<thead>
		<tr>
			<th>ID</th>
			<th>이름</th>
			<th>부서</th>
			<th>전화</th>
			<th>이메일</th>
			<th>등급</th>
			<th>삭제</th>
		</tr>
	</thead>
	<c:forEach var="user" items="${users}">
		<tr>
			<td>
				<a href="<%=request.getContextPath()%>/users/${user.id}">
					${user.username}
				</a>
			</td>
			<td>
				<a href="<%=request.getContextPath()%>/users/${user.id}">
					${user.name}
				</a>
			</td>
			<td>${user.part}</td>
			<td>${user.tel}</td>
			<td>${user.email}</td>
			<td>${user.userLevel}</td>
			<td>
				<c:if test="${user.username != 'admin' }">
					<a href="<%=request.getContextPath() %>/users/delete/${user.id}" 
						onclick="return confirm('${user.name} 님을 정말로 삭제할까요?')"
						class="btn btn-danger">
						삭제
					</a>
				</c:if>
			</td>
		</tr>
	</c:forEach>
	</table>
	
	<p class="text-center">
		<a href="<%=request.getContextPath() %>/users/insert" class="btn  btn-primary  btn-large">새 사용자</a>
	</p>
</div>
<jsp:include page="include/footer.jsp" />