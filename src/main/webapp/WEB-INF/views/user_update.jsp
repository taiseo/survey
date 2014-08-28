<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="include/header.jsp" />
<jsp:include page="include/footer.jsp" />

<div class="limit-width  center-block">
	
	<div class="page-header">
		<h1>${pageTitle }</h1>
	</div>
	
	<c:if test="${not empty error_msg }">
		<p>${error_msg}</p>
	</c:if>
	
	<form:form method="PUT" modelAttribute="user">
		<form:hidden path="username"/>
		<table class="table  table-striped">
			<tbody>
				<tr>
					<th>아이디</th>
					<td>
						${user.username }
					</td>
				</tr>
				<tr>
					<th><form:label path="name">이름</form:label></th>
					<td>
						<form:input path="name"/>
					</td>
				</tr>
				<tr>
					<th><form:label path="part">부서</form:label></th>
					<td>
						<form:input path="part"/>
					</td>
				</tr>
				<tr>
					<th><form:label path="tel">전화</form:label></th>
					<td>
						<form:input path="tel"/>
					</td>
				</tr>
				<tr>
					<th><form:label path="email">이메일</form:label></th>
					<td>
						<form:input path="email"/>
					</td>
				</tr>
				<tr>
					<th><form:label path="userLevel">등급</form:label></th>
					<td>
						<form:select path="userLevel">
							<form:option value="admin">관리자</form:option>
							<form:option value="normal">일반</form:option>
						</form:select>
					</td>
				</tr>
			</tbody>
		</table>
		
		<p class="text-center">
			<input class="btn btn-primary btn-large" type="submit" value="저장" />
		</p>
		
		<h2>비밀번호 변경</h2>
		<p>비밀번호를 변경하는 경우에만 채워 주세요.</p>
		<table class="table table-striped">
			<tbody>
				<tr>
					<th>
						<label for="oldPassword">비밀번호</label>
					</th>
					<td>
						<input type="password" name="oldPassword" id="oldPassword" />
					</td>
				</tr>
				<tr>
					<th>
						<label for="password">새 비밀번호</label>
					</th>
					<td>
						<input type="password" name="password" id="password" />
					</td>
				</tr>
				<tr>
					<th>
						<label for="passwordConfirm">새 비밀번호 확인</label>
					</th>
					<td>
						<input type="password" name="passwordConfirm" id="passwordConfirm" />
					</td>
				</tr>
			</tbody>
		</table>
		
		<p class="text-center">
			<input class="btn btn-primary btn-large" type="submit" value="저장" />
		</p>
		
	</form:form>
</div>