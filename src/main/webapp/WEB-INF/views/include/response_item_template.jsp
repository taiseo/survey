<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="js-response-item">
	<input type="hidden" name="id" />
	<i class="js-move-response-item  icon-move  move-response-item"></i>
	<input type="${param.type }" disabled/>
	<input class="span7" type="text" name="content" />
	<i class="js-remove-response-item  icon-remove" onclick="$(this).parent().remove();"></i>
</div>