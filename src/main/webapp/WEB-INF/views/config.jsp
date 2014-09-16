<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<jsp:include page="include/header.jsp" />

<div class="limit-width center-block">

	<div class="page-header">
		<h1>${pageTitle }</h1>
	</div>
	
	<form method="post">
		<table class="table table-striped">
		<colgroup>
			<col style="width: 20%" />
		</colgroup>
		<tbody>
			<tr>
			<th>기관명</th>
			<td>
				<input type="text" name="organization" value="${organization }" />
			</td>
		</tr>
		<tr>
			<th>전화번호</th>
			<td>
				<input type="text" name="phone" value="${phone }" />
				<span class="help-inline">설문 발송시 발송자 전화번호로 활용됩니다.</span>
			</td>
		</tr>
		<tr>
			<th>팩스</th>
			<td>
				<input type="text" name="fax" value="${fax }" />
			</td>
		</tr>
		<tr>
			<th>주소</th>
			<td>
				<input type="text" name="address" value="${address }" />
			</td>
		</tr>
		<tr>
			<th>로고 이미지</th>
			<td>
				<input type="file" name="logo_image" />
				<span class="help-inline">설문 발송시 첨부됩니다.</span>
				<c:if test="${logo_image }">
				<img src="<%=request.getContextPath() %>/${logo_image}"/>
				</c:if>
			</td>
		</tr>
		<tr>
			<th>도메인</th>
			<td>
				<input type="text" name="domain" value="${domain }" />
				<span class="help-inline">설문 발송시 사용됩니다.</span>
			</td>
		</tr>
		
		</tbody>
		</table>
		
		<p class="text-center">
			<input class="btn  btn-primary  btn-large" type="submit" value="저장" />
		</p>
	</form>
	
</div>

<jsp:include page="include/footer.jsp" />
