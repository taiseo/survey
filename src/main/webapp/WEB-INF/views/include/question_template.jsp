<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- script 태그를 굳이 이렇게 출력하는 이유는 html syntax highlighting 때문이다. -->
<%= "<script type='text/template' id='주관식1'>" %>
<div class="question  clearfix">
	<h2>주관식 짧은 답변</h2>
	<form class="question-form" onsubmit="return false;">
		<input type="hidden" name="type" value="주관식1" />
		<input type="hidden" name="id" value="<@=id@>"/>
		<input type="hidden" name="surveyId" value="${survey.id }" />
		<label>
			질문
			<input type="text" name="content" class="input-block-level" value="<@=content@>"/>
		</label>
		
		<label>
			질문 보조 설명
			<input type="text" name="contentDetail" class="input-block-level" value="<@=contentDetail@>"/>
		</label>
	</form>
	
	<label>
		<input type="text" class="input-block-level" value="답변 칸은 이렇게 나옵니다." disabled />
	</label>
	
	<p class="text-right">
		<input class="btn  js-copy-question" type="button" value="복사" />
		<input class="btn  btn-danger  js-remove-question" type="button" value="제거" />
	</p>
	
	<div class="move-question  js-move-question">
		<i class="icon-move"></i>
	</div>
	<hr />
</div>
<%= "</script>" %>




<%= "<script type='text/template' id='주관식2'>" %>
<div class="question  clearfix">
	<h2>주관식 긴 답변</h2>
	<form class="question-form" onsubmit="return false;">
		<input type="hidden" name="type" value="주관식2" />
		<input type="hidden" name="id" value="<@=id@>"/>
		<input type="hidden" name="surveyId" value="${survey.id }" />
		
		<label>
			질문
			<input type="text" name="content" class="input-block-level" value="<@=content@>"/>
		</label>
		
		<label>
			질문 보조 설명
			<input type="text" name="contentDetail" class="input-block-level" value="<@=contentDetail@>"/>
		</label>
	</form>
	
	<label>
		<textarea class="input-block-level" rows="5" disabled>답변 칸은 이렇게 나옵니다.</textarea>
	</label>
	
	<p class="text-right">
		<input class="btn  js-copy-question" type="button" value="복사" />
		<input class="btn  btn-danger  js-remove-question" type="button" value="제거" />
	</p>
	
	<div class="move-question  js-move-question">
		<i class="icon-move"></i>
	</div>
	<hr />
</div>
<%= "</script>" %>

<%= "<script type='text/template' id='객관식1'>" %>
<div class="question  clearfix">
	<h2>객관식 하나 선택 답변</h2>
	<form class="question-form" onsubmit="return false;">
		<input type="hidden" name="type" value="객관식1" />
		<input type="hidden" name="id" value="<@=id@>"/>
		<input type="hidden" name="surveyId" value="${survey.id }" />
		
		<label>
			질문
			<input type="text" name="content" class="input-block-level" value="<@=content@>"/>
		</label>
		
		<label>
			질문 보조 설명
			<input type="text" name="contentDetail" class="input-block-level" value="<@=contentDetail@>"/>
		</label>
	</form>
	
	<div class="js-response-items-area"></div>
	
	<div>
		<input type="radio" disabled/>
		<input class="js-add-reponse-item  span5  btn" type="button" value="답항을 추가하려면 클릭하세요." 
				data-template-id="객관식1-답항" />
		<input type="button" value="기타 답항 추가" class="js-add-reponse-item span2 btn"
				data-template-id="객관식1-기타-답항" />
	</div>
	
	<p class="text-right">
		<input class="btn  js-copy-question" type="button" value="복사" />
		<input class="btn  btn-danger  js-remove-question" type="button" value="제거" />
	</p>
	
	<div class="move-question  js-move-question">
		<i class="icon-move"></i>
	</div>
	<hr />
</div>
<%= "</script>" %>

<%= "<script type='text/template' id='객관식1-답항'>" %>
	<jsp:include page="response_item_template.jsp">
		<jsp:param name="type" value="radio"/>
	</jsp:include>
<%= "</script>" %>

<%= "<script type='text/template' id='객관식1-기타-답항'>" %>
	<jsp:include page="response_etc_item_template.jsp">
		<jsp:param name="type" value="radio"/>
	</jsp:include>
<%= "</script>" %>





<%= "<script type='text/template' id='객관식2'>" %>
<div class="question  clearfix">
	<h2>객관식 여럿 선택 답변</h2>
	<form class="question-form" onsubmit="return false;">
		<input type="hidden" name="type" value="객관식2" />
		<input type="hidden" name="id" value="<@=id@>" />
		<input type="hidden" name="surveyId" value="${survey.id }" />
		
		<label>
			질문
			<input type="text" name="content" class="input-block-level" value="<@=content@>" />
		</label>
		
		<label>
			질문 보조 설명
			<input type="text" name="contentDetail" class="input-block-level" value="<@=contentDetail@>" />
		</label>
	</form>
	
	<div class="js-response-items-area"></div>
	
	<div>
		<input type="checkbox" disabled/>
		<input class="js-add-reponse-item  span5  btn" type="button" value="답항을 추가하려면 클릭하세요." 
				data-template-id="객관식2-답항" />
		<input type="button" value="기타 답항 추가" class="js-add-reponse-item span2 btn"
				data-template-id="객관식2-기타-답항" />
	</div>
	
	<p class="text-right">
		<input class="btn  js-copy-question" type="button" value="복사" />
		<input class="btn  btn-danger  js-remove-question" type="button" value="제거" />
	</p>
	
	<div class="move-question  js-move-question">
		<i class="icon-move"></i>
	</div>
	<hr />
</div>
<%= "</script>" %>

<%= "<script type='text/template' id='객관식2-답항'>" %>
	<jsp:include page="response_item_template.jsp">
		<jsp:param name="type" value="checkbox"/>
	</jsp:include>
<%= "</script>" %>

<%= "<script type='text/template' id='객관식2-기타-답항'>" %>
	<jsp:include page="response_etc_item_template.jsp">
		<jsp:param name="type" value="checkbox"/>
	</jsp:include>
<%= "</script>" %>




<%= "<script type='text/template' id='점수범위'>" %>
<div class="question  clearfix">
	<h2>점수 범위</h2>
	<form class="question-form" onsubmit="return false;">
		<input type="hidden" name="type" value="점수범위" />
		<input type="hidden" name="id" value="<@=id@>" />
		<input type="hidden" name="surveyId" value="${survey.id }" />
		
		<label>
			질문
			<input type="text" name="content" class="input-block-level" value="<@=content@>" />
		</label>
		
		<label>
			질문 보조 설명
			<input type="text" name="contentDetail" class="input-block-level" value="<@=contentDetail@>" />
		</label>
	</form>
	
	<div class="js-response-items-area"></div>
    
	<p class="text-right">
		<input class="btn  js-copy-question" type="button" value="복사" />
		<input class="btn  btn-danger  js-remove-question" type="button" value="제거" />
	</p>
	
	<div class="move-question  js-move-question">
		<i class="icon-move"></i>
	</div>
	<hr />
</div>
<%= "</script>" %>

<%= "<script type='text/template' id='점수범위-답항'>" %>
<div class="js-response-item">
   	<input type="hidden" name="id" value="<@=id@>"/>
   	<input type="hidden" name="content" value="<@=content@>"/>
   	<label>
    	최솟값 : <input type="text" class="span1  js-number" name="min" value="<@=min@>"/>
    </label>
    <label>
    	최댓값 : <input type="text" class="span1  js-number" name="max" value="<@=max@>"/>
    </label>
</div>
<%= "</script>" %>





<%= "<script type='text/template' id='선호도'>" %>
<div class="question  clearfix">
	<h2>선호도</h2>
	<form class="question-form" onsubmit="return false;">
		<input type="hidden" name="type" value="선호도" />
		<input type="hidden" name="id" value="<@=id@>"/>
		<input type="hidden" name="surveyId" value="${survey.id }" />
		
		<label>
			질문
			<input type="text" name="content" class="input-block-level" value="<@=content@>"/>
		</label>
		
		<label>
			질문 보조 설명
			<input type="text" name="contentDetail" class="input-block-level" value="<@=contentDetail@>"/>
		</label>
	</form>
	
	<div class="js-response-items-area"></div>
	
	<div>
		<input type="radio" disabled/>
		<input class="js-add-reponse-item  span5  btn" type="button" value="답항을 추가하려면 클릭하세요." 
				data-template-id="선호도-답항" />
	</div>
	
	<p class="text-right">
		<input class="btn  js-copy-question" type="button" value="복사" />
		<input class="btn  btn-danger  js-remove-question" type="button" value="제거" />
	</p>
	
	<div class="move-question  js-move-question">
		<i class="icon-move"></i>
	</div>
	<hr />
</div>
<%= "</script>" %>

<%= "<script type='text/template' id='선호도-답항'>" %>
	<div class="js-response-item">
		<input type="hidden" name="id" value="<@=id@>"/>
		<i class="js-move-response-item  icon-move  move-response-item"></i>
		<input class="span7" type="text" name="content" value="<@=content@>" />
		<i class="js-remove-response-item  icon-remove"></i>
	</div>
<%= "</script>" %>










<%= "<script type='text/template' id='페이지-나누기'>" %>
<div class="question  clearfix">
	<h2>페이지 나누기</h2>

	<div class="page-breaker"></div>

	<form class="question-form" onsubmit="return false;">
		<input type="hidden" name="id" value="<@=id@> "/>
		<input type="hidden" name="surveyId" value="${survey.id }" />
		<input type="hidden" name="type" value="페이지-나누기" />
		<input type="hidden" name="content" value="$$$pageBreaker$$$" />
	</form>
	
	<p class="text-right">
		<input class="btn  js-copy-question" type="button" value="복사" />
		<input class="btn  btn-danger  js-remove-question" type="button" value="제거" />
	</p>
	
	<div class="move-question  js-move-question">
		<i class="icon-move"></i>
	</div>
	<hr />
</div>
<%= "</script>" %>