<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<p>
<input type="button" onclick='$(".chkbox").prop("checked",true); write_target_count();' class="btn btn-primary btn-small" value="전체선택"/>
<input type="button" onclick='$(".chkbox").prop("checked",false); write_target_count();' class="btn btn-warning btn-small" value="전체해제"/> 
</p>

<c:if test="${fn:length(branches) > 0 }">
<div class="row">
	<c:forEach items="${branches }" var="branch">
		<label class="span3">
			<input type="checkbox" class="js-target-branch chkbox" value="${branch }"/>
			${branch }
		</label>
	</c:forEach>
</div>
</c:if>

<c:if test="${fn:length(branches) == 0 }">
	지사가 없음
</c:if>
<input type="hidden" name="branches" class="js-target-branches" />
<input type="hidden" name="targetBranches" class="js-target-branches" />


