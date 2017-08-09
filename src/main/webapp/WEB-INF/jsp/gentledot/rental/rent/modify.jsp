<%@page import="net.gentledot.rental.vo.RentVO"%>
<%@ page import="net.gentledot.utils.Tools" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.util.Calendar" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko-KR">
<%
	String contextPath = request.getContextPath();
	RentVO details = (RentVO) request.getAttribute("details");

	Date curDate = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

	String returndate = sdf.format(curDate);

	String rentdateStr = details.getrRentdate();
	Date tempRentdate = null;

	tempRentdate = sdf2.parse(rentdateStr);

	String rentdate = sdf.format(tempRentdate);

	Calendar curCal = Calendar.getInstance();
	Calendar diffCal = Calendar.getInstance();

	curCal.setTime(curDate);
	diffCal.setTime(tempRentdate);

	int diff = curCal.get(Calendar.DAY_OF_YEAR) - diffCal.get(Calendar.DAY_OF_YEAR);

	System.out.println(curCal.get(Calendar.DAY_OF_YEAR));
	System.out.println(diffCal.get(Calendar.DAY_OF_YEAR));

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
			<div class="col-md-10 col-md-offset-1">
				<h2 class="h2">대여 상품 반납</h2>
				<form method="post" class="form-inline text-center" name="rentInfoForm">
					<ul class="list-group">
						<li class="list-group-item form-group">
							<label for="getRmId">대여 ID : </label>
							<input type="text" id="infoRmId" name="infoRmId" class="form-control" value="<%= Tools.customToEmptyBlank(details.getmId(), "") %>" readonly/>
						</li>
						<li class="list-group-item form-group">
							<label for="getRrentdate">대여 일자 : </label>
							<input type="text" id="infoRrentdate" name="infoRrentdate" class="form-control" value="<%= Tools.customToEmptyBlank(details.getrRentdate(), "") %>" readonly/>
						</li>
						<li class="list-group-item form-group">
							<label for="getRstId">대여 상품 : </label>
							<input type="text" id="infoRstId" name="infoRstId" class="form-control" value="<%= Tools.customToEmptyBlank(details.getStId(), "")%>" readonly/>
						</li>
					</ul>
				</form>
				<form action="<%= contextPath %>/rent/rentModify.do" method="post" class="form-horizontal" name="rentModifyForm">
					<ul class="list-group">
						<li class="list-group-item form-group">
							<label for="getRreturndate">반납 일자 : </label>
							<input type="date" id="getRreturndate" name="getRreturndate" class="form-control" value="<%= returndate %>" />
						</li>
						<li class="list-group-item form-group">
							<label for="getRarrears">연체료 : </label>
							<input type="number" id="getRarrears" name="getRarrears" class="form-control" value="0" />
						</li>
					</ul>
					<div class="form-group">
						<input type="hidden" id="getMPhone" name="getMPhone" />
						<input type="hidden" id="getRmId" name="getRmId" value="<%= Tools.customToEmptyBlank(details.getmId(), "") %>">
						<input type="hidden" id="getRrentdate" name="getRrentdate" value="<%= Tools.customToEmptyBlank(details.getrRentdate(), "") %>">
						<input type="hidden" id="getRstId" name="getRstId" value="<%= Tools.customToEmptyBlank(details.getStId(), "")%>">
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
	    returndateCalForSetArrears();

	    // 연체료 계산 (1일 200원, 최대 12일(2주))
		function returndateCalForSetArrears(){
		    var rentdate = new Date('<%= rentdate %>');
		    var curDate = new Date('<%= returndate %>');

			var limitOfReturn = (rentdate.getDate() + <%= details.getrRentperiod() %>);
			rentdate.setDate(limitOfReturn);

			if(curDate > rentdate){
				var afterDays = Number(<%= diff %>);
				var setArrears = afterDays >= 14 ? (200 * 12) : (200 * afterDays);
				$('#getRarrears').val(setArrears);
			}
		}
	});
</script>
</html>