<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- script 태그를 굳이 이렇게 출력하는 이유는 html syntax highlighting 때문이다. -->
<%= "<script type='text/template' id='주관식1'>" %>
<div class="question  clearfix">

	<input type="hidden" name="id" />
	<label>
		질문
		<input type="text" name="content" class="input-block-level" />
	</label>
	
	<label>
		질문 보조 설명
		<input type="text" name="contentDetail" class="input-block-level" />
	</label>
	
	<label>
		<input type="text" class="input-block-level" value="답변 칸은 이렇게 나옵니다." disabled />
	</label>
	
	<p class="text-right">
		<input class="btn  js-remove-question" type="button" value="제거" />
	</p>
	
	<div class="move-question  js-move-question">
		<i class="icon-move"></i>
	</div>
	<hr />
</div>
<%= "</script>" %>

<%= "<script type='text/template' id='주관식2'>" %>
<div class="question  clearfix">

	<input type="hidden" name="id" />
		<label>
		질문
		<input type="text" name="content" class="input-block-level" />
	</label>
	
	<label>
		질문 보조 설명
		<input type="text" name="contentDetail" class="input-block-level" />
	</label>
	
	<label>
		<textarea class="input-block-level" rows="5" disabled>답변 칸은 이렇게 나옵니다.</textarea>
	</label>
	
	<p class="text-right">
		<input class="btn  js-remove-question" type="button" value="제거" />
	</p>
	
	<div class="move-question  js-move-question">
		<i class="icon-move"></i>
	</div>
	<hr />
</div>
<%= "</script>" %>

<%= "<script type='text/template' id='객관식1'>" %>
<div class="question  clearfix">

	<input type="hidden" name="id" />
		<label>
		질문
		<input type="text" name="content" class="input-block-level" />
	</label>
	
	<label>
		질문 보조 설명
		<input type="text" name="contentDetail" class="input-block-level" />
	</label>
	
	<div class="js-response-items-area">
		<label class="js-reponse-item">
			<i class="js-move-response-item  icon-move  move-response-item"></i>
			<input type="radio" disabled/>
			<input class="span7" type="text" name="content" />
			<i class="js-remove-response-item  icon-remove" onclick="console.log('clicked'); $(this).parent().remove();"></i>
		</label>
	</div>
	
	<div>
		<input type="radio" disabled/>
		<input class="js-add-reponse-item  span5  btn" type="button" value="답항을 추가하려면 클릭하세요." 
				data-template-id="객관식1-답항" />
		<input type="button" value="기타 답항 추가" class="js-add-reponse-item span2 btn"
				data-template-id="객관식1-기타-답항" />
	</div>
	
	<p class="text-right">
		<input class="btn  js-remove-question" type="button" value="제거" />
	</p>
	
	<div class="move-question  js-move-question">
		<i class="icon-move"></i>
	</div>
	<hr />
</div>
<%= "</script>" %>

<%= "<script type='text/template' id='객관식1-답항'>" %>
	<label class="js-reponse-item">
		<i class="js-move-response-item  icon-move  move-response-item"></i>
		<input type="radio" disabled/>
		<input class="span7" type="text" name="content" />
		<i class="js-remove-response-item  icon-remove" onclick="console.log('clicked'); $(this).parent().remove();"></i>
	</label>
<%= "</script>" %>

<%= "<script type='text/template' id='객관식1-기타-답항'>" %>
	<label class="js-reponse-item">
		<i class="js-move-response-item  icon-move  move-response-item"></i>
		<input type="radio" disabled/>
		기타 : 
		<input class="span6" type="text" name="content" value="여기에 사용자가 직접입력하게 됩니다." disabled />
		<i class="js-remove-response-item  icon-remove" onclick="console.log('clicked'); $(this).parent().remove();"></i>
	</label>
<%= "</script>" %>


<%= "<script type='text/template' id='객관식2'>" %>
<div class="question  clearfix">

	<input type="hidden" name="id" />
		<label>
		질문
		<input type="text" name="content" class="input-block-level" />
	</label>
	
	<label>
		질문 보조 설명
		<input type="text" name="contentDetail" class="input-block-level" />
	</label>
	
	<div class="js-response-items-area">
		<label class="js-reponse-item">
			<i class="js-move-response-item  icon-move  move-response-item"></i>
			<input type="checkbox" disabled/>
			<input class="span7" type="text" name="content" />
			<i class="js-remove-response-item  icon-remove" onclick="console.log('clicked'); $(this).parent().remove();"></i>
		</label>
	</div>
	
	<div>
		<input type="checkbox" disabled/>
		<input class="js-add-reponse-item  span5  btn" type="button" value="답항을 추가하려면 클릭하세요." 
				data-template-id="객관식2-답항" />
		<input type="button" value="기타 답항 추가" class="js-add-reponse-item span2 btn"
				data-template-id="객관식2-기타-답항" />
	</div>
	
	<p class="text-right">
		<input class="btn  js-remove-question" type="button" value="제거" />
	</p>
	
	<div class="move-question  js-move-question">
		<i class="icon-move"></i>
	</div>
	<hr />
</div>
<%= "</script>" %>

<%= "<script type='text/template' id='객관식2-답항'>" %>
	<label class="js-reponse-item">
		<i class="js-move-response-item  icon-move  move-response-item"></i>
		<input type="checkbox" disabled/>
		<input class="span7" type="text" name="content" />
		<i class="js-remove-response-item  icon-remove" onclick="console.log('clicked'); $(this).parent().remove();"></i>
	</label>
<%= "</script>" %>

<%= "<script type='text/template' id='객관식2-기타-답항'>" %>
	<label class="js-reponse-item">
		<i class="js-move-response-item  icon-move  move-response-item"></i>
		<input type="checkbox" disabled/>
		기타 : 
		<input class="span6" type="text" name="content" value="여기에 사용자가 직접입력하게 됩니다." disabled />
		<i class="js-remove-response-item  icon-remove" onclick="console.log('clicked'); $(this).parent().remove();"></i>
	</label>
<%= "</script>" %>




<%= "<script type='text/template' id='점수범위'>" %>
<div class="question  clearfix">

	<input type="hidden" name="id" />
		<label>
		질문
		<input type="text" name="content" class="input-block-level" />
	</label>
	
	<label>
		질문 보조 설명
		<input type="text" name="contentDetail" class="input-block-level" />
	</label>
	
    <label>
    	최솟값 : <input type="text" class="span1  js-number" name="min"/>
    </label>
    <label>
    	최댓값 : <input type="text" class="span1  js-number" name="max"/>
    </label>
	<p class="text-right">
		<input class="btn  js-remove-question" type="button" value="제거" />
	</p>
	
	<div class="move-question  js-move-question">
		<i class="icon-move"></i>
	</div>
	<hr />
</div>
<%= "</script>" %>

<%= "<script type='text/template' id='페이지-나누기'>" %>
<div class="question  clearfix">

	<div class="page-breaker"></div>

	<input type="hidden" name="id" />
	<input type="hidden" name="content" value="$$$page-breaker$$$" />
	
	<p class="text-right">
		<input class="btn  js-remove-question" type="button" value="제거" />
	</p>
	
	<div class="move-question  js-move-question">
		<i class="icon-move"></i>
	</div>
	<hr />
</div><%= "</script>" %>
