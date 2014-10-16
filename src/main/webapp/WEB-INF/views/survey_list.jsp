<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@ taglib uri="http://www.okjsp.pe.kr/ok-taglib" prefix="pagination" %><jsp:include page="include/header.jsp" />

<div class="limit-width  center-block">
	<div class="page-header">
		<h1>${pageTitle }</h1>
	</div>

	<p class="text-right">
		<a href="<%=request.getContextPath() %>/surveys/insert" class="btn  btn-primary">
			새 설문
		</a>
	</p>	
	
	<table class="table  table-striped  table-data">
		<thead>
			<tr>

				<th nowrap="nowrap">No</th>
				<th nowrap="nowrap">제목</th>
				<th nowrap="nowrap">조사기간</th>
				<th nowrap="nowrap">대상</th>
				<th nowrap="nowrap">작성자(부서)</th>
				<th nowrap="nowrap">입력일시</th>
				<th nowrap="nowrap">상태</th>
				<th nowrap="nowrap">진행</th>
				<th nowrap="nowrap">응답수</th>
				<th nowrap="nowrap">관리</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="survey" items="${pagedListHolder.pageList}" varStatus="status">
				<tr>
					<td>${pagedListHolder.nrOfElements - (pagedListHolder.pageSize * pagedListHolder.page) - status.count +1}</td>
					<td nowrap="nowrap">
						<c:if test="${survey.status == '임시저장' or survey.status == '승인대기'}">
							<a href="<%=request.getContextPath()%>/surveys/update/${survey.id}" class="survey-title">
								${survey.title }
							</a>
						</c:if>
						<c:if test="${survey.status != '임시저장' and survey.status != '승인대기'}">
							<a href="<%=request.getContextPath()%>/survey/${survey.id}" class="survey-title">
								${survey.title }
							</a>
						</c:if>
						<c:if test="${survey.status == '발송' or survey.status == '종료'}">
							<br />
							<small>
								<a href="<%=request.getContextPath()%>/surveys/targets/${survey.id}">
									(대상 보기)
								</a>
							</small>
						</c:if>
					</td>
					<td nowrap="nowrap">${survey.startDate } ~
					<br />
					${survey.endDate }</td>
					<td>
						${survey.targetCategory1 }
						${survey.targetCategory2 }
						<%-- ${survey.targetBranches } --%>
					</td>
					<td>${survey.writer } <br />[${survey.part }]</td>
					<td nowrap="nowrap" class="align-center">${survey.datetime }</td>
					<td nowrap="nowrap">
						${survey.status }
						<c:if test="${survey.status == '승인' and  sessionScope.user.userLevel == '시스템 관리자'}">
							<a class="btn  btn-small" href="<%=request.getContextPath()%>/surveys/update-status/${survey.id}/standby">
								취소
							</a>
						</c:if>
					</td>
					<td>
						<%-- 설문 상태는 임시저장(temporary) -> 승인대기(standby) 
						     [ -> 심의승인(approval1) - 승인자1이 승인하는 것이고 필수가 아니다. 승인자2가 승인하면 바로 발송대기가 된다. ] 
						     -> 발송대기(approval_completed) -> 발송(sending) -> 종료(close) 다. --%>
						<%-- URL 인코딩 디코딩 문제를 피하기 위해서 URL에 영어를 사용했다. --%>
						<c:choose>
							<c:when test="${survey.status == '임시저장'}">
								<a class="btn  btn-small" href="<%=request.getContextPath()%>/surveys/update-status/${survey.id}/standby">
									승인요청
								</a>
							</c:when>
							
							<%--승인자1 입장 --%>
							<c:when test="${(survey.status == '승인대기') 
									and ( sessionScope.user.userLevel == '시스템 관리자' or sessionScope.user.userLevel == '승인자1' )}">
								<a class="btn  btn-small  btn-primary" href="<%=request.getContextPath()%>/surveys/update-status/${survey.id}/approval1">
									승인
								</a>
							</c:when>
							
							<%-- 승인자2 입장 --%>
							<c:when test="${(survey.status == '승인대기' or survey.status == '심의승인') 
									and ( sessionScope.user.userLevel == '시스템 관리자' or sessionScope.user.userLevel == '승인자2' )}">
								<a class="btn  btn-small  btn-primary" href="<%=request.getContextPath()%>/surveys/update-status/${survey.id}/approval_completed">
									승인
								</a>
							</c:when>
							
							<c:when test="${survey.status == '발송대기'}">
								<a class="btn  btn-small" href="<%=request.getContextPath()%>/surveys/update-status/${survey.id}/sending"
									onclick="return confirm('한 번 발송하면 돌이킬 수 없습니다. 정말로 발송할까요?')">
									발송
								</a>
							</c:when>
							<c:when test="${survey.status == '발송'}">
								<a class="btn  btn-small" href="<%=request.getContextPath()%>/surveys/update-status/${survey.id}/close">
									종료
								</a>
							</c:when>
							<c:when test="${survey.status == '종료'}">
								<input class="btn  btn-small" type="button" disabled value="종료" />
							</c:when>
							<c:otherwise>
								<input class="btn  btn-small" type="button" disabled value="승인대기" />
							</c:otherwise>
						</c:choose>
						
						<c:if test="${survey.status == '발송대기' and 
								(sessionScope.user.userLevel == '승인자2' or sessionScope.user.userLevel == '시스템 관리자') }">
							<a class="btn  btn-small  btn-danger" href="<%=request.getContextPath()%>/surveys/update-status/${survey.id}/temporary"
								onclick="return confirm('승인을 회수할까요?')">
								회수
							</a>
						</c:if>
						
						<c:if test="${survey.status == '승인대기' or survey.status == '심의승인'}">
							<a class="btn  btn-small  btn-danger" href="<%=request.getContextPath()%>/surveys/update-status/${survey.id}/temporary"
								onclick="return confirm('승인 요청을 회수할까요?')">
								회수
							</a>
						</c:if> 
					</td>
					<td>
						${survey.respondentCount }
					</td>
					<td nowrap="nowrap">
						<a class="btn  btn-small" href="<%=request.getContextPath()%>/surveys/update/${survey.id}">
							수정
						</a>
						<a class="btn  btn  btn-small" href="<%=request.getContextPath()%>/survey/${survey.id}">
							보기
						</a>
						<a class="btn  btn-info  btn-small" href="<%=request.getContextPath()%>/statistics/${survey.id}">
							결과
						</a>
						<a class="btn  btn-danger  btn-small" href="<%=request.getContextPath()%>/surveys/delete/${survey.id}"
							onclick="return confirm('정말로 삭제할까요?')">
							삭제
						</a>
						<a class="btn  btn-small" href="<%=request.getContextPath()%>/surveys/clone/${survey.id}">
							복사
						</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<p class="text-right">
		<a href="<%=request.getContextPath() %>/surveys/insert" class="btn  btn-primary">
			새 설문
		</a>
	</p>
	
	<% String path = request.getContextPath() + "/surveys"; %>
	<pagination:paging pageListHolder="${pagedListHolder}" offset="10" contextRoot="<%= path %>"/>
</div>



<jsp:include page="include/footer.jsp" />
