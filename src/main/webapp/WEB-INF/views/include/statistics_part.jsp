<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.freeneo.survey.domain.Question" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

    <c:forEach items="${survey.questions }" var="question">
    	<c:if test="${question.type != '페이지-나누기' }">
			<h2 class="normal-size">
				${question.content }
				<small>${question.contentDetail }</small>
			</h2>
			<p class="text-right">
				<c:if test="${fn:contains(question.type, '주관식')}">
				<a href="#" class="btn  btn-small  js-open-responses">펼치기</a>
				<a href="#" class="btn  btn-small  js-close-responses" style="display: none">접기</a>
				</c:if>
				<small>${question.questionRespondentCount }명<!-- 
				 -->(<fmt:formatNumber 
					value="${question.questionRespondentCount / survey.respondentCount * 100 }"
					pattern="0.0" />%) 
				응답</small>
			</p>
				
			<c:if test="${fn:contains(question.type, '주관식')}">
				<ul class="limit-height">
				<c:forEach items="${question.responses }" var="response">
					<li>${response }</li>
				</c:forEach>
				</ul>
			</c:if>
			
			<c:if test="${fn:contains(question.type, '객관식')}">
				<table class="table  table-striped">
				<colgroup>
					<col style="width: 50%" />
					<col style="width: 25%" />
				</colgroup>
				<thead>
					<tr>
						<th>답항</th>
						<th>수</th>
						<th>비율</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${question.responseItems }" var="responseItem">
						<c:if test="${responseItem.content != '$$$etc$$$' }">
						<tr>
							<th>${responseItem.content }</th>
							<td>${responseItem.responseItemCount }</td>
							<td>
								<c:if test="${responseItem.responseItemCount > 0 }">
									<fmt:formatNumber 
										value="${responseItem.responseItemCount / survey.respondentCount * 100}"
										pattern="0.0" />%
								</c:if>
							</td>
							<td style="width: 100px">
								<div class="progress">
									<div class="bar" 
										style="width: <fmt:formatNumber value="${responseItem.responseItemCount / survey.respondentCount * 100}" pattern=".0" />%"></div>
								</div>
							</td>
						</tr>
						</c:if>
						
						<c:if test="${responseItem.content == '$$$etc$$$' }">
						<tr>
							<th>
								<a href="#" onclick="$(this).parents('tr').next().slideToggle(); return false;">
									기타
								</a>
							</th>
							<td>${fn:length(question.etcResponses) }</td>
							<td>
								<c:if test="${fn:length(question.etcResponses) > 0 }">
									<fmt:formatNumber 
										value="${fn:length(question.etcResponses) / survey.respondentCount * 100}"
										pattern="0.0" />%
								</c:if>
							</td>
							<td style="width: 100px">
								<div class="progress">
									<div class="bar" 
										style="width: <fmt:formatNumber value="${fn:length(question.etcResponses) / survey.respondentCount * 100}" pattern=".0" />%"></div>
								</div>
							</td>
						</tr>
						<tr style="display: none">
							<td colspan="3">
								<ul>
								<c:forEach items="${question.etcResponses }" var="etc">
									<li>${etc }</li>
								</c:forEach>
								</ul>
							</td>
						</tr>
						
						<%-- table-striped 스타일 class 어긋남 방지용 --%>
						<tr style="display: none"></tr>
						</c:if>
					</c:forEach>
					</tbody>
				</table>
			</c:if>
			
			<c:if test="${question.type == '점수범위'}">
				<table class="table  table-striped">
				<colgroup>
					<col style="width: 50%" />
					<col style="width: 25%" />
				</colgroup>
				<thead>
				<tr>
					<th>점수</th>
					<th>수</th>
					<th>비율</th>
					<th></th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${question.pointResponseCount }" var="point">
					<tr>
						<td>${point.key }</td>
						<td>
							${point.value }
						</td>
						<td>
							<c:if test="${point.value > 0 }">
								<fmt:formatNumber 
									value="${point.value / survey.respondentCount * 100}"
									pattern="0.0" />%
							</c:if>
						</td>
						<td style="width: 100px">
							<div class="progress">
								<div class="bar" 
									style="width: <fmt:formatNumber value="${point.value / survey.respondentCount * 100}" pattern=".0" />%"></div>
							</div>
						</td>
					</tr>
				</c:forEach>
				</tbody>
				</table>
			</c:if>
			
			
			<c:if test="${question.type == '선호도'}">
				<table class="table  table-striped">
				<thead>
					<tr>
					<th></th>
					<c:forEach items="${question.responseItems }" var="responseItem" varStatus="status">
					<th>${status.count }순위</th>
					</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${question.responseItems }" var="responseItem">
						<tr>
						<th>${responseItem.content }</th>
						<c:forEach items="${responseItem.preference }" var="point">
						<td>
							<fmt:formatNumber value="${point.value / question.questionRespondentCount * 100}" pattern="0.0" />%
							<small class="muted">(${point.value })</small>
						</td>
						</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
				</table>
			</c:if>
			
    	</c:if>
    	<hr />
    </c:forEach>
