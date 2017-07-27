<%@ page import="net.gentledot.rental.vo.ProductVO" %>
<%@ page import="net.gentledot.utils.Tools" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko-KR">
<%
	String contextPath = request.getContextPath();
	ProductVO oneOfProduct = (ProductVO) request.getAttribute("oneOfProduct");
	String productGrade = "";
	switch (Tools.customToEmptyBlank(oneOfProduct.getpGrade(), "ALL")){
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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=contextPath%>/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<title>제품 상세조회</title>
	<style>
		.row{
			margin-bottom: 1em;
		}

		input[type="number"]::-webkit-outer-spin-button,
		input[type="number"]::-webkit-inner-spin-button {
			-webkit-appearance: none;
			margin: 0;
		}
	</style>
</head>
<body>
<div id="container">
	<header></header>
	<section class="main">
		<section class="row">
			<div class="col-md-8 col-md-offset-2">
				<h2 class="h2">제품 정보</h2>
				<ul class="list-group">
                    <li class="list-group-item form-group">
					<label for="getPId">제품 ID : </label>
					<input type="text" id="getPId" name="getPId" class="form-control" value="<%= oneOfProduct.getpId() %>" readonly/>
				</li>
					<li class="list-group-item form-group">
						<label for="getPName">제품명 : </label>
						<input type="text" id="getPName" name="getPName" class="form-control" value="<%= Tools.customToEmptyBlank(oneOfProduct.getpName(), "") %>" readonly/>
					</li>
					<li class="list-group-item form-group">
						<label for="getPPrice">제품 가격 : </label>
						<input type="text" id="getPPrice" name="getPPrice" class="form-control" value="<%= Tools.customToEmptyBlank(oneOfProduct.getpPrice(), "0") %>" readonly/>
					</li>
					<li class="list-group-item form-group">
						<label for="getPGrade">제품 등급 : </label>
						<input type="text" id="getPGrade" name="getPGrade" class="form-control" value="<%= productGrade %>" readonly />
					</li>
				</ul>

                <div class="btn-group">
                    <a href="<%= contextPath %>/product/productList.do" class="btn btn-default">목록으로</a>
                    <a href="<%= contextPath %>/product/productModifyView.do?pId=<%= oneOfProduct.getpId() %>" class="btn btn-info">정보수정</a>
                    <a href="<%= contextPath %>/product/productDel.do.do?pId=<%= oneOfProduct.getpId() %>" class="btn btn-warning" disabled="true">제품삭제</a>
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
	$(function(){
		$('a[disabled="true"]').bind('click', false);
	});
</script>
</html>