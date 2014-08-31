<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="include/header.jsp" />

<div class="limit-width  center-block">
	<div class="page-header">
		<h1>${pageTitle }</h1>
	</div>
	<div class="question-buttons">
		<input type="button" value="주관식 짧은 답변" class="btn  js-add-question" data-template-id="주관식1" />
		<input type="button" value="주관식 긴 답변" class="btn  js-add-question" data-template-id="주관식2" />
		<input type="button" value="객관식 하나 선택 답변" class="btn  js-add-question" data-template-id="객관식1" />
		<input type="button" value="객관식 여럿 선택 답변" class="btn  js-add-question" data-template-id="객관식2" />
		<input type="button" value="점수 범위" class="btn  js-add-question" data-template-id="점수 범위" />
		<input type="button" value="날짜" class="btn  js-add-question" data-template-id="날짜" />
		<input type="button" value="시간" class="btn  js-add-question" data-template-id="시간" />
		<input type="button" value="날짜+시간" class="btn  js-add-question" data-template-id="날짜+시간" />
		<input type="button" value="페이지 나누기" class="btn  js-add-question" data-template-id="페이지 나누기" />
	</div>
	
	<h2>도움말</h2>
	<ul>
		<li>각 문항은 편집과 함께 자동으로 저장됩니다.</li>
		<li>문항 순서를 변경하려면 <i class="icon-move"></i> 아이콘을 잡고 옮기면 됩니다.</li>
	</ul>
	
	<hr />
	<div class="js-questions-area"></div>
	
	<div class="question-buttons">
		<input type="button" value="주관식 짧은 답변" class="btn  js-add-question" data-template-id="주관식1" />
		<input type="button" value="주관식 긴 답변" class="btn  js-add-question" data-template-id="주관식2" />
		<input type="button" value="객관식 하나 선택 답변" class="btn  js-add-question" data-template-id="객관식1" />
		<input type="button" value="객관식 여럿 선택 답변" class="btn  js-add-question" data-template-id="객관식2" />
		<input type="button" value="점수 범위" class="btn  js-add-question" data-template-id="점수 범위" />
		<input type="button" value="날짜" class="btn  js-add-question" data-template-id="날짜" />
		<input type="button" value="시간" class="btn  js-add-question" data-template-id="시간" />
		<input type="button" value="날짜+시간" class="btn  js-add-question" data-template-id="날짜+시간" />
		<input type="button" value="페이지 나누기" class="btn  js-add-question" data-template-id="페이지 나누기" />
	</div>
</div>

<jsp:include page="include/question_template.jsp" />

<jsp:include page="include/footer.jsp" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/lib/jquery-ui/jquery-ui.min.js"></script>
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/lib/jquery-ui/jquery-ui.theme.min.css" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/survey_detail.js"></script>