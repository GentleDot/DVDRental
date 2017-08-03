<%@ page import="java.util.List" %>
<%@ page import="net.gentledot.rental.vo.ProductVO" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko-KR">
<%
	String contextPath = request.getContextPath();
	List<ProductVO> productList = (List<ProductVO>) request.getAttribute("productList");

	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	String curDate = sdf.format(date);

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=contextPath%>/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<title>재고 입력</title>
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
				<h2 class="h2">재고 추가</h2>
				<form action="<%= contextPath %>/storage/addStorage.do" method="post" class="form-horizontal" name="addProductForm">
					<ul class="list-group">
						<li class="list-group-item form-group">
							<label for="inputStGetdate">입고일 : </label>
							<input type="number" id="inputStGetdate" name="inputStGetdate" class="form-control" value="<%= curDate %>" />
						</li>
						<li class="list-group-item form-group">
							<label for="inputPid">제품 : </label>
							<select name="inputPid" id="inputPid" class="form-control">
								<% for(ProductVO item : productList){ %>
										<option value="<%= item.getpId() %>"><%=item.getpName()%></option>
								<% } %>
							</select>
						</li>
					</ul>
					<div class="form-group">
						<input type="submit" class="form-control btn btn-success" value="추가">
					</div>
				</form>
			</div>
		</section>
	</section>
	<footer></footer>
</div>
</body>
<script src="<%=contextPath%>/webjars/jquery/3.2.1/jquery.min.js"></script>
<script src="<%=contextPath%>/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
</html>