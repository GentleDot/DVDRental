<%@page import="net.gentledot.rental.vo.MemberVO"%>
<%@ page import="java.util.List" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<%@ page import="net.gentledot.utils.Tools" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko-KR">
<%
	String contextPath = request.getContextPath();
	List<MemberVO> resultList = (List<MemberVO>) request.getAttribute("resultList");
	String keyword = (String) request.getAttribute("keyword");
	String category = (String) request.getAttribute("category");
	int pageNo = (Integer) request.getAttribute("pageNo");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=contextPath%>/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<title>회원 목록 조회</title>
	<style>
		.row{
			margin-bottom: 1em;
		}
	</style>
</head>
<body>
<div id="container">
	<header></header>
	<section class="main">
		<section class="row">
			<div class="col-md-8 col-md-offset-2">
				<h2>회원 목록</h2>
				<form action="<%= contextPath %>/member/memberList.do" method="post" class="form-inline" name="sendSearchKeyword">
					<fieldset class="form-group">
						<legend>회원 검색</legend>
						<div class="form-group">
							<select name="category" id="category" class="form-control">
								<option value="id" selected>회원 ID</option>
								<option value="name">회원 이름</option>
							</select>
							<input type="text" id="keyword" class="form-control" name="keyword" value="<%= keyword %>"/>
						</div>
						<div class="form-group">
							<input type="submit" class="btn btn-default" value="검색"/>
						</div>
					</fieldset>
					<input type="hidden" name="pageNo" value="${pagination.pageNo}">
				</form>
			</div>
		</section>
		<section class="row">
			<div class="col-md-8 col-md-offset-2">
				<div class="text-right">
					<a href="<%= contextPath %>/member/addMemberView.do" class="btn btn-success">회원 추가</a>
				</div>
				<table class="table table-bordered">
					<caption class="sr-only">DVD 대여점 회원 목록</caption>
					<thead>
					<tr>
						<th>ID</th>
						<th>이름</th>
						<th>생년월일</th>
						<th>가입일</th>
						<th>연락처</th>
						<th>대여 가능 개수</th>
						<th>대여</th>
					</tr>
					</thead>
					<tfoot>

					</tfoot>
					<tbody>
					<% for(MemberVO member : resultList){ %>
						<tr>
							<td>
								<a href="<%= contextPath %>/member/memberInfo.do?mId=<%=Tools.customToEmptyBlank(member.getmId(), "")%>"><%=Tools.customToEmptyBlank(member.getmId(), "")%></a>
							</td>
							<td>
								<a href="<%= contextPath %>/member/memberInfo.do?mId=<%=Tools.customToEmptyBlank(member.getmId(), "")%>"><%=Tools.customToEmptyBlank(member.getmName(), "")%></a>
							</td>
							<td><%=Tools.customToEmptyBlank(member.getmBirth(), "")%>
							</td>
							<td><%=Tools.customToEmptyBlank(member.getmJoinDate(), "")%>
							</td>
							<td><%=Tools.customToEmptyBlank(member.getmPhone(), "")%>
							</td>
							<td><%=Tools.customToEmptyBlank(member.getmLimit(), "0")%>
							</td>
							<td>
								<a href="<%= contextPath %>/rent/addRentView.do?mId=<%=member.getmId()%>" class="btn btn-default">상품 대여</a>
							</td>
						</tr>
					<% }%>
					</tbody>
				</table>

				<% if (resultList.size() <= 0){ %>
				<div class="text-center">
					<span class="text-danger">조회한 값의 데이터가 없습니다.</span>
				</div>
				<% } %>

				<div>
					<jsp:include page="/WEB-INF/jsp/gentledot/inc/pagination.jsp">
						<jsp:param name="firstPageNo" value="${pagination.firstPageNo}" />
						<jsp:param name="prevPageNo" value="${pagination.prevPageNo}" />
						<jsp:param name="startPageNo" value="${pagination.startPageNo}" />
						<jsp:param name="pageNo" value="${pagination.pageNo}" />
						<jsp:param name="endPageNo" value="${pagination.endPageNo}" />
						<jsp:param name="nextPageNo" value="${pagination.nextPageNo}" />
						<jsp:param name="finalPageNo" value="${pagination.finalPageNo}" />
					</jsp:include>
				</div>

			</div>
		</section>
	</section>
	<footer></footer>
</div>
</body>
<script src="<%=contextPath%>/webjars/jquery/3.2.1/jquery.min.js"></script>
<script src="<%=contextPath%>/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
<script>
    $("input:submit").click(pageNoInitializer);
    searchCatReminder();
    
    function goPage(pageNo){
        document.sendSearchKeyword.pageNo.value = pageNo;
        document.sendSearchKeyword.submit();
    }
    
    function pageNoInitializer(){
        $("input[name='pageNo']").val(1);
    }
    
    function searchCatReminder(){
        $('#category > option[value="<%= category %>"]').attr("selected", "true");
	}
</script>
</html>