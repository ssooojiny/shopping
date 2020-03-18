<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ul>
<c:if test="${member == null}">
 <li> <a href="/member/signin">로그인</a> </li>
</c:if>

<c:if test="${member != null}">
	<c:if test="${member.verify == 9}">
		<li> <a href="/">일반페이지</a> </li>
	</c:if>
 <li><a href="/member/signout">로그아웃</a></li>
</c:if>
</ul>