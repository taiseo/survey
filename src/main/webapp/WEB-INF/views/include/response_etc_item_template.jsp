<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="js-response-item">
	<input type="hidden" name="id" value="<@=id@>" />
	<input type="hidden" name="content" value="$$$etc$$$" />
	<i class="js-move-response-item  icon-move  move-response-item"></i>
	<input type="${param.type }" disabled/>
	기타 : 
	<input class="span6" type="text" name="content" value="여기에 사용자가 직접입력하게 됩니다." disabled />
	<i class="js-remove-response-item  icon-remove"></i>
</div>