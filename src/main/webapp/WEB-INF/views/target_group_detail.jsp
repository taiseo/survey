<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
	<h3>${pageTitle }</h3>
</div>
<div class="modal-body">
	<table class="table">
	<thead>
		<tr>
			<th>구분</th>
			<th>대상</th>
		</tr>
	</thead>
	<c:forEach var="map" items="${targetInfosByTargetGroup}">
		<tr>
			<td>${map.targetGroupName }</td>
			<td>${map.count }</td>
		</tr>
	</c:forEach>
	</table>
</div>
