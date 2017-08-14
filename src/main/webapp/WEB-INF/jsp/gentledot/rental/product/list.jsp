<%@ page import="net.gentledot.rental.vo.ProductVO" %>
<%@ page import="java.util.List" %>
<%@ page import="net.gentledot.utils.Tools" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko-KR">
<%
	String contextPath = request.getContextPath();
	List<ProductVO> resultList = (List<ProductVO>) request.getAttribute("resultList");
	String keyword = (String) request.getAttribute("keyword");
	String category = (String) request.getAttribute("category");
	int pageNo = (Integer) request.getAttribute("pageNo");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=contextPath%>/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<title>제품 목록 조회</title>
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
			<jsp:param name="curPage" value="product" />
		</jsp:include>
	</header>
	<section class="main">
		<section class="row">
			<div class="col-md-8 col-md-offset-2">
				<h2>제품 목록</h2>
				<form action="<%= contextPath %>/product/productList.do" method="post" class="form-inline" name="sendSearchKeyword">
					<fieldset class="form-group">
						<legend>제품 검색</legend>
						<div class="form-group">
							<label for="category" class="sr-only">검색 설정 (ID / 제품명)</label>
							<select name="category" id="category" class="form-control">
								<option value="id" selected>제품 ID</option>
								<option value="name">제품 이름</option>
							</select>
							<label for="keyword" class="sr-only">검색 키워드 입력</label>
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
					<a href="<%= contextPath %>/product/addProductView.do" class="btn btn-success">제품 추가</a>
				</div>
				<table class="table table-bordered">
					<caption class="sr-only">DVD 대여점 제품 목록</caption>
					<thead>
					<tr>
						<th>ID</th>
						<th>제품 이름</th>
						<th>제품 가격</th>
						<th>등급</th>
					</tr>
					</thead>
					<tfoot>

					</tfoot>
					<tbody>
					<%
						for(ProductVO product : resultList){
						    String productGrade = "";
							switch (Tools.customToEmptyBlank(product.getpGrade(), "all")){
								case "ALL":
									productGrade = "전체 관람가";
									break;
								case "7":
									productGrade = "7세 관람가";
									break;
								case "12":
								    productGrade = "12세 관람가";
								    break;
								case "15":
								    productGrade = "15세 관람가";
								    break;
								default:
								    productGrade = "청소년 관람불가";
								    break;
							}
					%>
						<tr>
							<td>
								<a href="<%= contextPath %>/product/productModifyView.do?pId=<%=Tools.customToEmptyBlank(product.getpId(), "")%>"><%=Tools.customToEmptyBlank(product.getpId(), "")%></a>
							</td>
							<td>
								<a href="<%= contextPath %>/product/productModifyView.do?pId=<%=Tools.customToEmptyBlank(product.getpId(), "")%>"><%=Tools.customToEmptyBlank(product.getpName(), "")%></a>
							</td>
							<td><%=Tools.customToEmptyBlank(product.getpPrice(), "0")%>
							</td>
							<td><%= productGrade %>
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