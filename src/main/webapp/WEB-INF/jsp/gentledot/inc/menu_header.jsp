<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String contextPath = request.getContextPath();
    String curMenu = request.getParameter("curPage");
%>

<nav class="navbar navbar-inverse navbar-static-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#menuNavBar">
                <span class="sr-only">토글 메뉴</span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<%=contextPath%>">DVD 대여점</a>
        </div>
        <div class="collapse navbar-collapse"
             id="menuNavBar">
            <ul class="nav navbar-nav navbar-right">
                <li <%= curMenu.equals("member") ? "class=\"active\"" : ""%>><a
                        href="<%=contextPath%>/member/memberList.do"> 회원 목록 <%=curMenu.equals("member") ? "<span class='sr-only'> 현재 페이지는 회원 목록 메뉴 입니다. </span>" : ""%></a>
                </li>
                <li <%= curMenu.equals("product") ? "class=\"active\"" : ""%>><a
                        href="<%=contextPath%>/product/productList.do"> 제품 목록 <%=curMenu.equals("product") ? "<span class='sr-only'> 현재 페이지는 제품 목록 입니다. </span>" : ""%></a>
                </li>
                <li <%= curMenu.equals("storage") ? "class=\"active\"" : ""%>><a
                        href="<%=contextPath%>/storage/storageList.do"> 재고 관리 <%=curMenu.equals("storage") ? "<span class='sr-only'> 현재 페이지는 재고 관리 메뉴 입니다. </span>" : ""%></a>
                </li>
                <li <%= curMenu.equals("rent") ? "class=\"active\"" : ""%>><a
                        href="<%=contextPath%>/rent/rentList.do"> 대여 관리 <%=curMenu.equals("rent") ? "<span class='sr-only'> 현재 페이지는 대여 관리 메뉴 입니다. </span>" : ""%></a>
                </li>
                <li <%= curMenu.equals("sales") ? "class=\"active\"" : ""%>><a
                        href="<%=contextPath%>/sales/salesList.do"> 매출 관리 <%=curMenu.equals("sales") ? "<span class='sr-only'> 현재 페이지는 대여 관리 메뉴 입니다. </span>" : ""%></a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>