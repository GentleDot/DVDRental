<%@ page import="net.gentledot.rental.vo.ProductVO" %>
<%@ page import="net.gentledot.utils.Tools" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko-KR">
<%
	String contextPath = request.getContextPath();
	ProductVO oneOfProduct = (ProductVO) request.getAttribute("oneOfProduct");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=contextPath%>/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
	<title>제품 정보 입력</title>
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
				<h2 class="h2">제품 정보 수정</h2>
				<form action="<%= contextPath %>/product/productModify.do" method="post" class="form-horizontal" name="addProductForm">
					<ul class="list-group">
						<li class="list-group-item form-group">
							<label for="getPId">제품 ID : </label>
							<input type="text" id="getPId" name="getPId" class="form-control" value="<%= oneOfProduct.getpId() %>" readonly/>
						</li>
						<li class="list-group-item form-group">
							<label for="getPName">제품 이름 : </label>
							<input type="text" id="getPName" name="getPName" class="form-control" value="<%= Tools.customToEmptyBlank(oneOfProduct.getpName(), "")%>"/>
						</li>
						<li class="list-group-item form-group">
							<label for="getPPrice">제품 가격 : </label>
							<input type="number" id="getPPrice" name="getPPrice" class="form-control" min="0" value="<%= Tools.customToEmptyBlank(oneOfProduct.getpPrice(), "0") %>"/>
						</li>
						<li class="list-group-item form-group">
							<label for="getPGrade">제품 등급 : </label>
							<select name="getPGrade" id="getPGrade" class="form-control">
								<option value="ALL">전체 관람가</option>
								<option value="7">7세 관람가</option>
								<option value="12">12세 관람가</option>
								<option value="15">15세 관람가</option>
								<option value="19">청소년 관람불가</option>
							</select>
						</li>
					</ul>
					<div class="form-group">
						<input type="submit" class="form-control btn btn-success" value="확인">
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
<script>
	$(function(){
	    gradeSelector();

	    $('#getPPrice').bind('blur', chkPrice);

	    // 현재 등급 정보가 select에 반영되도록 설정
		function gradeSelector(){
		    $('#getPGrade > option[value="<%= Tools.customToEmptyBlank(oneOfProduct.getpGrade(), "ALL")%>"').attr("selected", "true");
		}

		// 가격이 0 미만으로는 입력되지 못하도록 설정
		function chkPrice(){
		    var inputPrice = $('#getPPrice').val();
		    var regExp = /^\d+$/g;
		    var regResult = regExp.test(inputPrice);
		    if(Number(inputPrice) < 0 || !(regResult)){
		        alert('올바른 가격을 입력해주세요.');
		        $('#getPPrice').val("100").focus();
			}
		}
	});
</script>
</html>