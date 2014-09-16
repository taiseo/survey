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
				<th nowrap="nowrap">제목</th>
				<th nowrap="nowrap">설문시작일</th>
				<th nowrap="nowrap">종료일</th>
				<th nowrap="nowrap">대상</th>
				<th nowrap="nowrap">작성자</th>
				<th nowrap="nowrap">부서</th>
				<th nowrap="nowrap">입력일시</th>
				<th nowrap="nowrap">상태</th>
				<th nowrap="nowrap">진행</th>
				<th nowrap="nowrap">기능</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="survey" items="${list}">
				<tr>
					<td>
						<c:if test="${survey.status == '임시저장' or survey.status == '승인대기'}">
							<a href="<%=request.getContextPath()%>/surveys/update/${survey.id}">
								${survey.title }
							</a>
						</c:if>
						<c:if test="${survey.status != '임시저장' and survey.status != '승인대기'}">
							<a href="<%=request.getContextPath()%>/survey/${survey.id}">
								${survey.title }
							</a>
						</c:if>
						<c:if test="${survey.status == '발송' or survey.status == '종료'}">
							<small>
								<a href="<%=request.getContextPath()%>/surveys/targets/${survey.id}">
									(대상 보기)
								</a>
							</small>
						</c:if>
					</td>
					<td nowrap="nowrap">${survey.startDate }</td>
					<td nowrap="nowrap">${survey.endDate }</td>
					<td>
						${survey.targetCategory1 }
						${survey.targetCategory2 }
						${survey.targetBranches }
					</td>
					<td nowrap="nowrap">${survey.writer }</td>
					<td nowrap="nowrap">${survey.part }</td>
					<td nowrap="nowrap">${survey.datetime }</td>
					<td nowrap="nowrap">
						${survey.status }
						<c:if test="${survey.status == '승인' and  sessionScope.user.userLevel == '시스템 관리자'}">
							<a class="btn  btn-small" href="<%=request.getContextPath()%>/surveys/update-status/${survey.id}/standby">
								취소
							</a>
						</c:if>
					</td>
					<td>
						<%-- 설문 상태는 임시저장(temporary) -> 승인대기(standby) -> 승인자1만 승인(approval1) && 승인자2만 승인(approval2) -> 발송(sending) -> 종료(close) 다. --%>
						<%-- URL 인코딩 디코딩 문제를 피하기 위해서 URL에 영어를 사용했다. --%>
						<c:choose>
							<c:when test="${survey.status == '임시저장'}">
								<a class="btn" href="<%=request.getContextPath()%>/surveys/update-status/${survey.id}/standby">
									승인요청
								</a>
							</c:when>
							
							<%--승인자1 입장 --%>
							<c:when test="${(survey.status == '승인대기' or survey.status == '승인자2만 승인') 
									and ( sessionScope.user.userLevel == '시스템 관리자' or sessionScope.user.userLevel == '승인자1' )}">
								<a class="btn" href="<%=request.getContextPath()%>/surveys/update-status/${survey.id}/approval1">
									승인
								</a>
							</c:when>
							
							<%-- 승인자2 입장 --%>
							<c:when test="${(survey.status == '승인대기' or survey.status == '승인자1만 승인') 
									and ( sessionScope.user.userLevel == '시스템 관리자' or sessionScope.user.userLevel == '승인자2' )}">
								<a class="btn" href="<%=request.getContextPath()%>/surveys/update-status/${survey.id}/approval2">
									승인
								</a>
							</c:when>
							
							<c:when test="${survey.status == '발송대기'}">
								<a class="btn" href="<%=request.getContextPath()%>/surveys/update-status/${survey.id}/sending"
									onclick="return confirm('한 번 발송하면 돌이킬 수 없습니다. 정말로 발송할까요?')">
									발송
								</a>
							</c:when>
							<c:when test="${survey.status == '발송'}">
								<a class="btn" href="<%=request.getContextPath()%>/surveys/update-status/${survey.id}/close">
									종료
								</a>
							</c:when>
							<c:when test="${survey.status == '종료'}">
								<input class="btn" type="button" disabled value="종료" />
							</c:when>
							<c:otherwise>
								<input class="btn" type="button" disabled value="승인대기" />
							</c:otherwise>
						</c:choose>
					</td>
					<td nowrap="nowrap">
						<a class="btn" href="<%=request.getContextPath()%>/surveys/update/${survey.id}">
							수정
						</a>
						<a class="btn  btn" href="<%=request.getContextPath()%>/survey/${survey.id}">
							보기
						</a>
						
						<br />
						
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