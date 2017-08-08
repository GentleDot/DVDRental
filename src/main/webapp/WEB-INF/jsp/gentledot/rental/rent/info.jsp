<%@page import="net.gentledot.rental.vo.RentVO"%>
<%@ page import="net.gentledot.utils.Tools" %>
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
<title>대여 정보 상세조회</title>
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
			<div class="col-md-6 col-md-offset-3">
				<h2 class="h2">대여 정보</h2>
				<ul class="list-group">
                    <li class="list-group-item form-group">
                        <label for="getRmId">대여 회원 : </label>
                        <input type="text" id="getRmId" name="getRmId" class="form-control" value="<%= Tools.customToEmptyBlank(details.getmId(), "") %> (<%= Tools.customToEmptyBlank(details.getmName(), "")%>)" readonly/>
                    </li>
					<li class="list-group-item form-group">
						<label for="getRrentdate">대여 일자 : </label>
						<input type="text" id="getRrentdate" name="getRrentdate" class="form-control" value="<%= Tools.customToEmptyBlank(details.getrRentdate(), "") %>" readonly/>
					</li>
					<li class="list-group-item form-group">
						<label for="getRstId">대여 상품 : </label>
						<input type="text" id="getRstId" name="getRstId" class="form-control" value="<%= Tools.customToEmptyBlank(details.getStId(), "")%> (<%= Tools.customToEmptyBlank(details.getpName(), "")%>)" readonly/>
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
						<input type="text" id="getRreturndate" name="getRreturndate" class="form-control" min="0" value="<%= Tools.customToEmptyBlank(details.getrReturndate(), "") %>" readonly />
					</li>
					<li class="list-group-item form-group">
						<label for="getRreturnStatus">반납 확인 : </label>
						<input type="text" id="getRreturnStatus" name="getRreturnStatus" class="form-control" value="<%= Tools.customToEmptyBlank(details.getrReturnStatus(), "") %>" readonly />
					</li>
					<li class="list-group-item form-group">
						<label for="getRarrears">연체료 : </label>
						<input type="number" id="getRarrears" name="getRarrears" class="form-control" value="<%= Tools.customToEmptyBlank(details.getrArrears(), "") %>" readonly />
					</li>
                    <li class="list-group-item form-group">
                        <label for="getRarrearsClear">연체료 납부일 : </label>
                        <input type="number" id="getRarrearsClear" name="getRarrearsClear" class="form-control" min="0" value="<%= Tools.customToEmptyBlank(details.getrArrearsClear(), "")%>" readonly />
                    </li>
				</ul>

                <div class="btn-group">
                    <a href="<%= contextPath %>/rent/rentList.do" class="btn btn-default">목록으로</a>
                    <a href="<%= contextPath %>/rent/rentDel.do?mId=<%= details.getmId() %>&rentdate=<%= details.getrRentdate()%>&stId=<%= details.getStId() %>" class="btn btn-warning">대여 정보 삭제</a>
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
	    returnChk();

		// 반납 여부 확인
	    function returnChk(){
	        var status = $('#getRreturnStatus').val();
            if(status === 'Y'){
                $('#updateRentInfo').attr('disabled', 'true').bind('click', false);
            }
        }

	});
</script>
</html>
