<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="include/header.jsp" />

<div class="limit-width  center-block">
	<div class="page-header">
		<h1>${pageTitle }</h1>
	</div>
	
	<p class="text-center">
		<a href="<%=request.getContextPath() %>/surveys/insert" class="btn  btn-primary  btn-large">
			새 설문
		</a>
	</p>

	<table class="table  table-striped">
		<thead>
			<tr>
				<th>제목</th>
				<th>설문시작일</th>
				<th>종료일</th>
				<th>대상</th>
				<th>작성자</th>
				<th>부서</th>
				<th>입력일시</th>
				<th>상태</th>
				<th>진행</th>
				<th>기능</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="survey" items="${list}">
				<tr>
					<td>
						<a href="<%=request.getContextPath()%>/surveys/update/${survey.id}">
						${survey.title }
						</a>
					</td>
					<td>${survey.startDate }</td>
					<td>${survey.endDate }</td>
					<td>${survey.target }</td>
					<td>${survey.writer }</td>
					<td>${survey.part }</td>
					<td>${survey.datetime }</td>
					<td>${survey.status }</td>
					<td>
						<%-- 설문 상태는 대기 -> 승인 -> 발송 -> 종료 다. --%>
						<c:choose>
							<c:when test="${(survey.status == '대기' or survey.status == null) 
									and sessionScope.user.userLevel == 'admin'}">
								<a class="btn" href="<%=request.getContextPath()%>/surveys/updateStatus/${survey.id}/승인">
									승인
								</a>
							</c:when>
							<c:when test="${survey.status == '승인'}">
								<a class="btn" href="<%=request.getContextPath()%>/surveys/updateStatus/${survey.id}/발송">
									발송
								</a>
							</c:when>
							<c:when test="${survey.status == '발송'}">
								<a class="btn" href="<%=request.getContextPath()%>/surveys/updateStatus/${survey.id}/종료">
									종료
								</a>
							</c:when>
							<c:when test="${survey.status == '종료'}">
								<input class="btn" type="button" disabled value="종료" />
							</c:when>
							<c:otherwise>
								<input class="btn" type="button" disabled value="승인" />
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<a class="btn" href="<%=request.getContextPath()%>/surveys/update/${survey.id}">
							수정
						</a>
						<a class="btn  btn" href="<%=request.getContextPath()%>/survey/${survey.id}">
							미리보기
						</a>
						<a class="btn  btn-info" href="<%=request.getContextPath()%>/statistics/${survey.id}">
							결과
						</a>
						<a class="btn  btn-danger" href="<%=request.getContextPath()%>/surveys/delete/${survey.id}"
							onclick="return confirm('정말로 삭제할까요?')">
							삭제
						</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>	
</div>

<jsp:include page="include/footer.jsp" />