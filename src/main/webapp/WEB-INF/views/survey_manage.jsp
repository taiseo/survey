<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="include/header.jsp" />
<div class="limit-width  center-block">
	
	<div class="page-header">
		<h1>${pageTitle }</h1>
	</div>
	
	<form:form method="${httpMethod }" modelAttribute="survey">
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
					<th><form:label path="description">내용</form:label></th>
					<td><form:textarea rows="10" cssClass="wysiwyg  input-block-level" path="description"/></td>
				</tr>
				<tr>
					<th>게시일</th>
					<td>
						<form:input cssClass="datepicker  span2" path="startDate" />
						~
						<form:input cssClass="datepicker  span2" path="endDate" />
					</td>
				</tr>
				<tr>
					<th>대상</th>
					<td>
						<div class="row  js-category">
							<div class="js-target-category1  span4">
								<select name="targetCategory1">
									<option>목적외사업</option>
									<option>농지은행</option>
									<option>유지관리</option>
								</select>
							</div>
							<div class="js-target-category2  span4" style="display: none"></div>
							<div class="js-branches  span4" style="display: none"></div>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<p class="text-center">
			<input type="submit" value="저장 후 문항 편집" class="btn btn-primary btn-large" />
		</p>
	</form:form>
</div>

<script type="text/template" id="target-category2">
	<select name="targetCategory2">
		<option>농지연금</option>
		<option>농지은행</option>
		<option>경영이양직불</option>
	</select>
</script>

<script type="text/javascript" src="<%= request.getContextPath() %>/resources/lib/tinymce/js/tinymce/tinymce.min.js"></script>
<jsp:include page="include/footer.jsp" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/survey_manage.js"></script>