<%@page import="net.gentledot.rental.vo.RentVO"%>
<%@ page import="net.gentledot.utils.Tools" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.ParseException" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko-KR">
<%
	String contextPath = request.getContextPath();
	RentVO details = (RentVO) request.getAttribute("details");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=contextPath%>/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<title>대여 정보 수정</title>
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
				<h2 class="h2">대여 정보 업데이트</h2>
				<form action="<%= contextPath %>/rent/rentModify.do" method="post" class="form-horizontal" name="rentModifyForm">
					<ul class="list-group">
						<li class="list-group-item form-group">
							<label for="getRmId">대여 ID : </label>
							<input type="text" id="getRmId" name="getRmId" class="form-control" value="<%= Tools.customToEmptyBlank(details.getmId(), "") %>" readonly/>
						</li>
						<li class="list-group-item form-group">
							<label for="getRrentdate">대여 일자 : </label>
							<input type="text" id="getRrentdate" name="getRrentdate" class="form-control" value="<%= Tools.customToEmptyBlank(details.getrRentdate(), "") %>" readonly/>
						</li>
						<li class="list-group-item form-group">
							<label for="getRstId">대여 상품 : </label>
							<input type="text" id="getRstId" name="getRstId" class="form-control" value="<%= Tools.customToEmptyBlank(details.getStId(), "")%>" readonly/>
						</li>
						<li class="list-group-item form-group">
							<label for="getRrentPeriod">대여 기간 : </label>
							<input type="text" id="getRrentPeriod" name="getRrentPeriod" class="form-control" value="<%= Tools.customToEmptyBlank(details.getrRentperiod(), "0") %>" readonly />
						</li>
						<li class="list-group-item form-group">
							<label for="getRcharge">대여료 : </label>
							<input type="text" id="getRcharge" name="getRcharge" class="form-control" value="<%= Tools.customToEmptyBlank(details.getrCharge(), "0") %>" readonly />
						</li>
						<li class="list-group-item form-group">
							<label for="getRreturndate">반납 일자 : </label>
							<input type="number" id="getRreturndate" name="getRreturndate" class="form-control" min="0" value="<%= Tools.customToEmptyBlank(details.getrReturndate(), "") %>" />
						</li>
						<li class="list-group-item form-group">
							<label for="getRreturnStatus">반납 확인 : </label>
							<input type="text" id="getRreturnStatus" name="getRreturnStatus" class="form-control" value="<%= Tools.customToEmptyBlank(details.getrReturnStatus(), "") %>" />
						</li>
						<li class="list-group-item form-group">
							<label for="getRarrears">연체료 : </label>
							<input type="number" id="getRarrears" name="getRarrears" class="form-control" value="<%= Tools.customToEmptyBlank(details.getrArrears(), "") %>" />
						</li>
						<li class="list-group-item form-group">
							<label for="getRarrearsClear">연체료 납부일 : </label>
							<input type="number" id="getRarrearsClear" name="getRarrearsClear" class="form-control" min="0" value="<%= Tools.customToEmptyBlank(details.getrArrearsClear(), "")%>"  />
						</li>
					</ul>
					<div class="form-group">
						<input type="hidden" id="getMPhone" name="getMPhone" />
						<input type="submit" class="btn btn-info" value="수정" />
					</div>
				</form>

                <div class="btn-group">
                    <a href="<%= contextPath %>/rent/rentList.do" class="btn btn-default">목록으로</a>
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

	});
</script>
</html>