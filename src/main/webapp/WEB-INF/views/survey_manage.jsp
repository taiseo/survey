<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="include/header.jsp" />
<div class="limit-width  center-block">
	
	<div class="page-header">
		<h1>${pageTitle }</h1>
	</div>
	
	<form:form method="${httpMethod }" modelAttribute="survey" cssClass="js-survey-form">
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
					<th><form:label path="msgSubject">MMS 제목</form:label></th>
					<td><form:input cssClass="input-block-level" path="msgSubject"/></td>
				</tr>
				<tr>
					<th><form:label path="msg">MMS 인사말</form:label></th>
					<td><form:input cssClass="input-block-level" path="msg"/></td>
				</tr>
				<tr>
					<th><form:label path="startDate">조사 기간</form:label></th>
					<td>
						<form:input cssClass="datepicker  span2" path="startDate" />
						~
						<form:input cssClass="datepicker  span2" path="endDate" />
					</td>
				</tr>
				<tr>
					<th><form:label path="description">설문개요 (취지)</form:label></th>
					<td><form:textarea rows="10" cssClass="wysiwyg  input-block-level" path="description"/></td>
				</tr>
				<tr>
					<th><form:label path="targetRegistrationType">타겟 등록 방식</form:label></th>
					<td>
						<label>
							<form:radiobutton path="targetRegistrationType" value="캠페인 그룹 선택"/>
							캠페인 그룹 선택
						</label>
						<label>
							<form:radiobutton path="targetRegistrationType" value="CRM DB 추출"/>
							CRM DB 추출
						</label>
						<label>
							<form:radiobutton path="targetRegistrationType" value="엑셀파일 업로드"/>
							엑셀파일 업로드
						</label>
					</td>
				</tr>
				<tr class="target-registration-type js-target-registration-type" 
						data-target-registration-type="캠페인 그룹 선택">
					<th>
						<form:label path="targetCategory1">캠페인 그룹 선택</form:label>
					</th>
					<td>
						
					</td>
				</tr>
				<tr class="target-registration-type js-target-registration-type" 
						data-target-registration-type="CRM DB 추출">
					<th>
						<form:label path="description">CRM DB 추출</form:label>
						<div class="js-target-count"></div>
					</th>
					<td>
						<div class="row  js-used-branch-checkbox-area">
							<div class="js-target-category1  span4">
								<form:select path="targetCategory1">
									<option value="">목표 그룹 선택 </option>>
									<form:option value="목적외사업" />
									<form:option value="농지은행" />
									<form:option value="유지관리" />
								</form:select>
							</div>
							<div class="js-target-category2  span4" style="display: none">
								<form:select path="targetCategory2">
									<form:option value="농지연금" />
									<form:option value="농지은행" />
									<form:option value="경영이양직불" />
								</form:select>
							</div>
							<div class="js-target-branches  span4">
								<jsp:include page="include/branch_list.jsp" />
							</div>
						</div>
					</td>
				</tr>
				<tr class="target-registration-type js-target-registration-type" 
						data-target-registration-type="엑셀파일 업로드">
					<th>엑셀파일 업로드</th>
					<td>
						<input type="file" name="excel" id="excel" />
					</td>
				</tr>
				<tr>
					<th><form:label path="limit">대상 추출 방법</form:label></th>
					<td>
						<div class="row">
							<div class="span12">
								<form:input path="limit" cssClass="span1  js-number" 
										cssStyle="text-align: right"/>명씩 
								랜덤으로 추출합니다.
							</div>
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

${survey}

<script type="text/javascript">
	survey.target_branches = ${targetBranches == null || survey.targetBranches == '' ? '[]' : survey.targetBranches} ;
</script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/lib/tinymce/js/tinymce/tinymce.min.js"></script>
<jsp:include page="include/footer.jsp" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/survey_manage.js"></script>