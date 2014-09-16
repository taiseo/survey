<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="include/header.jsp" />

<div class="limit-width  center-block">
	
	<div class="page-header">
		<h1>${pageTitle }</h1>
	</div>
	
	<form:form method="${httpMethod }" modelAttribute="user">
		<table class="table  table-striped">
			<colgroup>
				<col width="20%"/>
			</colgroup>
			<tbody>
				<c:if test="${pageCommand == 'update' }">
				<tr>
					<th>아이디</th>
					<td>
						${user.username }
						<form:hidden path="username"/>
					</td>
				</tr>
				</c:if>
				<c:if test="${pageCommand == 'insert' }">
				<tr>
					<th><form:label path="name">아이디</form:label></th>
					<td>
						<form:input path="username"/>
					</td>
				</tr>
				</c:if>
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
						<c:if test="${sessionScope.user.userLevel == '시스템 관리자' }">
							<form:select path="userLevel">
								<form:option value="시스템 관리자">시스템 관리자</form:option>
								<form:option value="승인자2">승인자2</form:option>
								<form:option value="승인자1">승인자1</form:option>
								<form:option value="일반">일반</form:option>
							</form:select>
						</c:if>
						<c:if test="${sessionScope.user.userLevel != '시스템 관리자' }">
							${user.userLevel }
							<form:hidden path="userLevel"/>
						</c:if>
					</td>
				</tr>
				
			</tbody>
		</table>

		<c:if test="${pageCommand == 'update' }">		
				<p class="text-center">
					<input class="btn btn-primary btn-large" type="submit" value="저장" />
				</p>
		</c:if>	
		
		<c:if test="${pageCommand == 'update' }">		
				<h2>비밀번호 변경</h2>
				<p>비밀번호를 변경하는 경우에만 채워 주세요.</p>
		</c:if>
		
		<table class="table table-striped">
			<colgroup>
				<col width="20%"/>
			</colgroup>
			<tbody>
				<c:if test="${pageCommand == 'update' }">
				<tr>
					<th>
						<label for="oldPassword">비밀번호</label>
					</th>
					<td>
						<input type="password" name="oldPassword" id="oldPassword" />
					</td>
				</tr>
				</c:if>
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

<jsp:include page="include/footer.jsp" />