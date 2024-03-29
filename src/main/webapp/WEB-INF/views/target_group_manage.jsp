<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%><jsp:include page="include/header.jsp" />
<div class="limit-width  center-block">
	<div class="page-header">
		<h1>${pageTitle }</h1>
	</div>
	<div class="base-margin  text-right">
		<a href="${listUrl }" class="btn">목록</a>
	</div>
	<form:form method="${httpMethod }" modelAttribute="targetGroup" cssClass="js-used-branch-checkbox-area">
		<table class="table  table-striped">
			<colgroup>
				<col style="width: 10%"/>
			</colgroup>
			<tbody>
				<tr>
					<th><form:label path="title">제목</form:label></th>
					<td><form:input cssClass="input-block-level" path="title"/></td>
				</tr>
				<tr>
					<th><form:label path="limit">지사별 인원</form:label></th>
					<td>
						<form:input path="limit" cssClass="span1  js-number  js-target-limit"  cssStyle="text-align: right"/>명씩 
						랜덤으로 추출합니다. 
					</td>
				</tr>
				<tr>
					<th>추출된 인원</th>
					<td>
						<span class="js-target-count"></span>
						<br />
						<small class="muted">추출된 인원은 현 시점을 기준으로 하고 계약 기간은 신경쓰지 않은 것으로 실제 문자 발송시엔 인원이 달라질 수 있습니다.</small>
					</td>
				</tr>
				<tr class="js-target-category1-wrapper">
					<th><form:label path="category1">사업분류1</form:label></th>
					<td>
						<form:select path="category1" cssClass="js-target-category1">
							<option value="">사업 대분류 선택</option>>
							<form:option value="목적외이용자" />
							<form:option value="농지은행" />
							<form:option value="유지관리" />
						</form:select>
					</td>
				</tr>
				<tr class="js-target-category2-wrapper" style="display: none">
					<th><form:label path="category2">사업분류2</form:label></th>
					<td>
						<form:select path="category2" cssClass="js-target-category2">
							<form:option value="농지연금" />
							<form:option value="농지은행" />
							<form:option value="경영이양직접지불사업" />
						</form:select>
					</td>
				</tr>
				<tr>
					<th><form:label path="bonbu">지역</form:label></th>
					<td>
						<select name="bonbu" class="js-target-bonbu" data-value="${targetGroup.bonbu}"></select>
					</td>
				</tr>
				<tr>
					<th>
						<form:label path="branches">지사</form:label>
					</th>
					<td>
						<div class="js-target-branches-checkbox-area">
							<jsp:include page="include/branch_list.jsp" />
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<p class="text-center">
			<input type="submit" value="저장" class="btn btn-primary btn-large" />
			<c:if test="${listUrl != null}">
				<a href="${listUrl }" class="btn  pull-right">목록</a>
			</c:if>
		</p>
	</form:form>
</div>

<script type="text/javascript">
	survey.target_branches = ${targetBranches == null || targetBranches == '' ? '[]' : targetBranches} ;
</script>
<jsp:include page="include/footer.jsp" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/used_branch_checkbox.js"></script>