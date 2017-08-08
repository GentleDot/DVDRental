<%@page import="net.gentledot.rental.vo.RentVO"%>
<%@ page import="java.util.List" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<%@ page import="net.gentledot.utils.Tools" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="net.gentledot.rental.vo.StorageVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko-KR">
<%
	String contextPath = request.getContextPath();
	String mId = (String) request.getAttribute("mId");
	List<StorageVO> itemList = (List<StorageVO>) request.getAttribute("itemList");
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	String curDate = sdf.format(date);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=contextPath%>/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<title>대여 정보 입력</title>
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
			<div class="col-md-6 col-md-offset-3">
				<h2 class="h2">상품 대여</h2>
				<form action="<%= contextPath %>/rent/addRent.do" method="post" class="form-horizontal" name="addRentForm">
					<ul class="list-group">
						<li class="list-group-item form-group">
							<label for="inputRmId">회원 ID : </label>
							<input type="text" id="inputRmId" name="inputRmId" class="form-control" value="<%= mId %>" readonly />
						</li>
						<li class="list-group-item form-group">
							<label for="inputRrentdate">대여 일자 : </label>
							<input type="text" id="inputRrentdate" name="inputRrentdate" class="form-control" value="<%= curDate %>" readonly />
						</li>
						<li class="list-group-item form-group">
							<label for="inputRstId">대여 상품 : </label>
							<select id="inputRstId" name="inputRstId" class="form-control">
								<% for(StorageVO item : itemList){ %>
								<option value="<%= item.getStId()%>"><%= item.getpName()%> [<%= item.getStId() %>]</option>
								<% } %>
							</select>

						</li>
						<li class="list-group-item form-group">
							<label for="inputRrentPeriod">대여 기간 : </label>
							<input type="number" id="inputRrentPeriod" name="inputRrentPeriod" class="form-control" min="0" />
						</li>
						<li class="list-group-item form-group">
							<label for="inputRcharge">대여료 : </label>
							<input type="number" id="inputRcharge" name="inputRcharge" class="form-control" min="0" readonly />
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
		$('#inputRrentPeriod').bind('blur', calArrears);

	    // 대여료 계산
        function calArrears(){
			var period = Number($('#inputRrentPeriod').val());
			var totalArrears = 500 * period;
			$('#inputRcharge').val(totalArrears);
        }
	});
</script>
</html>