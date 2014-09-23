<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="include/header.jsp" />
<div class="limit-width  center-block">
	
	<div class="page-header">
		<h1>${pageTitle }</h1>
	</div>
	
	<form:form method="${httpMethod }" modelAttribute="survey" cssClass="js-survey-form">
		<table class="table  table-striped">
			<colgroup>
				<col style="width: 15%"/>
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
						<label class="radio  inline">
							<form:radiobutton path="targetRegistrationType" value="캠페인 그룹 선택"/>
							캠페인 그룹 선택
						</label>
						<label class="radio  inline">
							<form:radiobutton path="targetRegistrationType" value="CRM DB 추출"/>
							CRM DB 추출
						</label>
						<label class="radio  inline">
							<form:radiobutton path="targetRegistrationType" value="엑셀파일 업로드"/>
							엑셀파일 업로드
						</label>
						<p class="muted">CRM DB를 사용하는 경우 고객이 달라질 수 있기 때문에 실제 발송 대상은 발송 시점에 추출합니다.</p>
					</td>
				</tr>
				
				<tr class="js-target-date">
					<th>계약 기간</th>
					<td>
						<form:input cssClass="datepicker  span2  js-target-start-date" path="targetStartDate" />
						~
						<form:input cssClass="datepicker  span2  js-target-end-date" path="targetEndDate" />
					</td>
				</tr>
				
				<tr class="target-registration-type js-target-registration-type" 
						data-target-registration-type="캠페인 그룹 선택">
					<th>
						캠페인 그룹 선택
					</th>
					<td>
						<div class="row">
						
							<c:forEach items="${targetGroups }" var="targetGroup">
								<label class="span3">
									<input type="checkbox" name="targetGroupId[]"
										class="js-target-group-id" value="${targetGroup.id }"/>
									${targetGroup.title }
								</label>
							</c:forEach>
							<form:hidden path="targetGroupIds" cssClass="js-target-group-ids"/>
						</div>						
					</td>
				</tr>
				

				<tr class="target-registration-type js-target-registration-type"
						data-target-registration-type="CRM DB 추출">
					<th>
						<form:label path="description">CRM DB 추출</form:label>
					</th>
					<td>
						<div class="row  js-used-branch-checkbox-area">
							<table class="table">
								<colgroup>
									<col style="width: 100px"/>
								</colgroup>
								<tr>
									<th><form:label path="limit">추출수</form:label></th>
									<td>
										<form:input path="limit" cssClass="span1  js-number  js-target-limit"  cssStyle="text-align: right"/>명씩 
										랜덤으로 추출합니다. <span class="js-target-count"></span>
									</td>
								</tr>
								<tr class="js-target-category1-wrapper">
									<th><form:label path="targetCategory1">사업분류1</form:label></th>
									<td>
										<form:select path="targetCategory1" cssClass="js-target-category1">
											<option value="">사업 대분류 선택</option>>
											<form:option value="목적외사업" />
											<form:option value="농지은행" />
											<form:option value="유지관리" />
										</form:select>
									</td>
								</tr>
								<tr class="js-target-category2-wrapper" style="display: none">
									<th><form:label path="targetCategory2">사업분류2</form:label></th>
									<td>
										<form:select path="targetCategory2" cssClass="js-target-category2">
											<form:option value="농지연금" />
											<form:option value="농지은행" />
											<form:option value="경영이양직불" />
										</form:select>
									</td>
								</tr>
								<tr>
									<th><form:label path="targetBonbu">지역</form:label></th>
									<td>
										<select name="targetBonbu" class="js-target-bonbu" data-value="${survey.targetBonbu}"></select>
									</td>
								</tr>
								<tr>
									<th>
										<form:label path="targetBranches">지사</form:label>
									</th>
									<td>
										<div class="js-target-branches-checkbox-area">
											<jsp:include page="include/branch_list.jsp" />
										</div>
									</td>
								</tr>
							</table>
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
			</tbody>
		</table>
		<p class="text-center">
			<input type="submit" value="저장 후 문항 편집" class="btn btn-primary btn-large" />
		</p>
	</form:form>
</div>

<div id="target-detail-modal" class="modal  fade" tabindex="-1" role="dialog" aria-hidden="true"></div>

<script type="text/javascript">
	survey.target_group_ids = ${survey.targetGroupIds == null || survey.targetGroupIds == '' ? '[]' : survey.targetGroupIds} ;
	survey.target_branches = ${targetBranches == null || targetBranches == '' ? '[]' : targetBranches} ;
</script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/lib/tinymce/js/tinymce/tinymce.min.js"></script>
<jsp:include page="include/footer.jsp" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/used_branch_checkbox.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/survey_manage.js"></script>
