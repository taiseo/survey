<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><jsp:include page="include/header.jsp" />

<div class="limit-width  center-block">
	<div class="page-header">
		<h1>${pageTitle }</h1>
	</div>

	<jsp:include page="include/question_buttons.jsp" />
	
	<h2>도움말</h2>
	<ul>
		<li>각 문항은 편집과 함께 자동으로 저장됩니다.</li>
		<li>문항 순서를 변경하려면 제목이나 <i class="icon-move"></i> 아이콘을 잡고 옮기면 됩니다.</li>
	</ul>
	
	<hr />
	<div class="js-questions-area"></div>
	
	<jsp:include page="include/question_buttons.jsp" />
	
	<p class="text-center">
		
		<c:if test="${survey.status == '승인대기' }">
			<a href="<%=request.getContextPath() %>/surveys" style="margin-top: 3em" 
				class="btn  btn-primary  btn-large">저장</a>
		</c:if>
		
		<c:if test="${survey.status == '임시저장' }">
			<a href="<%=request.getContextPath() %>/surveys" style="margin-top: 3em" 
				class="btn  btn-primary  btn-large">승인 요청 없이 저장</a>
			
			
			<a href="<%=request.getContextPath() %>/surveys/update-status/${survey.id}/standby" style="margin-top: 3em"
				onclick="confirm('승인 요청을 할까요?')"
				class="btn  btn-primary  btn-large  pull-right">승인 요청</a>
		</c:if>
	</p>
	
</div>

<jsp:include page="include/question_template.jsp" />

<script type="text/javascript">
survey.questions = ${questionsString};
</script>

<jsp:include page="include/footer.jsp" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/lib/jquery-ui/jquery-ui.min.js"></script>
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/lib/jquery-ui/jquery-ui.theme.min.css" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/js/survey_detail.js"></script>