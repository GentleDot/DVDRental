<%@ page import="net.gentledot.rental.vo.StorageVO" %>
<%@ page import="java.util.List" %>
<%@ page import="net.gentledot.utils.Tools" %>
<%@ page import="net.gentledot.rental.vo.ProductVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko-KR">
<%
	String contextPath = request.getContextPath();
	List<StorageVO> resultList = (List<StorageVO>) request.getAttribute("resultList");
	List<ProductVO> productList = (List<ProductVO>) request.getAttribute("productList");
	String stId = (String) request.getAttribute("stId");
	int pageNo = (Integer) request.getAttribute("pageNo");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=contextPath%>/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<title>재고 목록 조회</title>
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
			<jsp:param name="curPage" value="storage" />
		</jsp:include>
	</header>
	<section class="main">
		<section class="row">
			<div class="col-md-12">
				<h2>재고 목록</h2>
				<form action="<%= contextPath %>/storage/storageList.do" method="post" class="form-inline text-center" name="sendSearchKeyword">
					<fieldset class="form-group">
						<legend class="sr-only">재고 검색</legend>
						<div class="form-group">
							<label for="stId">id 검색 : </label>
							<input type="text" id="stId" class="form-control" name="stId" value="<%= stId %>"/>
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
					<a href="<%= contextPath %>/storage/addStorageView.do" class="btn btn-success">재고 추가</a>
				</div>
				<table class="table table-bordered">
					<caption class="sr-only">DVD 대여점 재고 목록</caption>
					<thead>
						<tr>
							<th>ID</th>
							<th>입고일</th>
							<th>제품명</th>
							<th>상태</th>
						</tr>
					</thead>
					<tfoot>

					</tfoot>
					<tbody>
					<%
						for(StorageVO item : resultList){
						    String pId = Tools.customToEmptyBlank(item.getpId(), "1");
					%>
						<tr>
							<td>
								<a href="<%= contextPath %>/storage/storageInfo.do?stId=<%= item.getStId() %>"><%= item.getStId() %></a>
							</td>
							<td>
								<a href="<%= contextPath %>/storage/storageInfo.do?stId=<%= item.getStId() %>"><%=Tools.customToEmptyBlank(item.getStGetdate(), "9999-99-99")%></a>
							</td>
							<td>
								<%= Tools.customToEmptyBlank(item.getpName(), "") %>
							</td>
							<td>
								<%= Tools.customToEmptyBlank(item.getStStatus(), "") %>
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


    function goPage(pageNo){
        document.sendSearchKeyword.pageNo.value = pageNo;
        document.sendSearchKeyword.submit();
    }
    
    function pageNoInitializer(){
        $("input[name='pageNo']").val(1);
    }
</script>
</html>