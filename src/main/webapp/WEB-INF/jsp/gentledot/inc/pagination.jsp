<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <nav class="boardNav text-center">
        <ul class="pagination pagination-sm">
            <li><a href="javascript:goPage(${param.firstPageNo})"><span aria-hidden="true">&laquo;&laquo;</span><span class="sr-only">처음 페이지</span></a></li>
            <li><a href="javascript:goPage(${param.prevPageNo})"><span aria-hidden="true">&laquo;</span><span class="sr-only">이전 페이지</span></a></li>

            <c:forEach var="i" begin="${param.startPageNo}" end="${param.endPageNo}" step="1">
                <c:choose>
                    <c:when test="${i eq param.pageNo}"> <li><a class="active" href="javascript:goPage(${i})">${i}</a></li></c:when>
                    <c:otherwise><li><a href="javascript:goPage(${i})">${i}</a></li></c:otherwise>
                </c:choose>
            </c:forEach>

            <li><a href="javascript:goPage(${param.nextPageNo})"><span aria-hidden="true">&raquo;</span><span class="sr-only">다음 페이지</span></a>
            <li><a href="javascript:goPage(${param.finalPageNo})"><span aria-hidden="true">&raquo;&raquo;</span><span class="sr-only">마지막 페이지</span></a>
        </ul>
        <div class="clearfix"></div>
    </nav>
</div>