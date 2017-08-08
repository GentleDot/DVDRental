<%@page import="net.gentledot.rental.vo.RentVO"%>
<%@ page import="java.util.List" %>
<%@ page import="net.gentledot.utils.Tools" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko-KR">
<%
	String contextPath = request.getContextPath();
	List<RentVO> resultList = (List<RentVO>) request.getAttribute("resultList");
	String keyword = (String) request.getAttribute("keyword");
	String category = (String) request.getAttribute("category");
	int pageNo = (Integer) request.getAttribute("pageNo");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=contextPath%>/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<title>대여 목록 조회</title>
	<style>
		.row{
			margin-bottom: 1em;
		}
	</style>
</head>
<body>
<div id="container">
	<header>
		<jsp:include page ="/WEB-INF/jsp/gentledot/inc/menu_header.jsp">
			<jsp:param name="curPage" value="rent" />
		</jsp:include>
	</header>
	<section class="main">
		<section class="row">
			<div class="col-md-8 col-md-offset-2">
				<h2>대여 목록</h2>
				<form action="<%= contextPath %>/rent/rentList.do" method="post" class="form-inline" name="sendSearchKeyword">
					<fieldset class="form-group">
						<legend>대여 정보 검색</legend>
						<div class="form-group">
							<select name="category" id="category" class="form-control">
								<option value="id" selected>회원 ID</option>
								<option value="date">대여 일자</option>
								<option value="item">상품 ID</option>
							</select>
							<input type="text" id="keyword" class="form-control" name="keyword" value="<%= keyword %>"/>
						</div>
						<div class="form-group">
							<input type="submit" class="btn btn-default" value="검색"/>
						</div>
					</fieldset>
					<input type="hidden" name="pageNo" value="<%= pageNo %>">
				</form>
			</div>
		</section>
		<section class="row">
			<div class="col-md-8 col-md-offset-2">
				<div class="text-right">
					<a href="<%= contextPath %>/member/memberList.do" class="btn btn-default">회원 목록</a>
				</div>
				<table class="table table-bordered">
					<caption class="sr-only">DVD 대여점 대여 목록</caption>
					<thead>
						<tr>
							<th>회원</th>
							<th>대여 일자</th>
							<th>대여 상품</th>
							<th>대여 기간</th>
							<th>대여료</th>
							<th>반납 처리</th>
							<th>반납 일자</th>
						</tr>
					</thead>
					<tfoot>

					</tfoot>
					<tbody>
					<% for(RentVO rent : resultList){ %>
					<%
						if(rent.getrReturnStatus() != null){
							if(rent.getrReturnStatus().equals("Y")){
					%>
						<tr class="bg-warning">
					<%
							}
						}else{
					%>
						<tr>
					<%
						}
					%>
							<td>
								<a href="<%= contextPath %>/rent/rentInfo.do?mId=<%= rent.getmId() %>&rentdate=<%= rent.getrRentdate()%>&stId=<%= rent.getStId() %>"><%=Tools.customToEmptyBlank(rent.getmId(), "")%><span>(<%= Tools.customToEmptyBlank(rent.getmName(), "") %>)</span></a>
						</td>
							<td>
								<a href="<%= contextPath %>/rent/rentInfo.do?mId=<%= rent.getmId() %>&rentdate=<%= rent.getrRentdate()%>&stId=<%= rent.getStId() %>"><%=Tools.customToEmptyBlank(rent.getrRentdate(), "")%></a>
							</td>
							<td>
								<a href="<%= contextPath %>/rent/rentInfo.do?mId=<%= rent.getmId() %>&rentdate=<%= rent.getrRentdate()%>&stId=<%= rent.getStId() %>"><%=Tools.customToEmptyBlank(rent.getStId(), "")%><span>(<%= rent.getpName() %>)</span></a>
							</td>
							<td><%=Tools.customToEmptyBlank(rent.getrRentperiod(), "")%>
							</td>
							<td><%=Tools.customToEmptyBlank(rent.getrCharge(), "")%>
							</td>
							<td>
							<%
								if(rent.getrReturnStatus() != null){
									if(rent.getrReturnStatus().equals("Y")){
							%>
								<a href="#" class="btn btn-info" disabled="disabled">반납완료</a>
							<%
									}
								}else{
							%>
								<a href="<%= contextPath %>/rent/rentModifyView.do?mId=<%= rent.getmId() %>&rentdate=<%= rent.getrRentdate()%>&stId=<%= rent.getStId() %>" class="btn btn-warning">반 납</a>
							<%
								}
							%>
							</td>
							<td><%=Tools.customToEmptyBlank(rent.getrReturndate(), "")%>
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