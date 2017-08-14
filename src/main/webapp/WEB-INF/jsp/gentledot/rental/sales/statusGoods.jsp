<%@page import="net.gentledot.rental.vo.RentVO"%>
<%@ page import="net.gentledot.utils.Tools" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Calendar" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko-KR">
<%
	String contextPath = request.getContextPath();
	List<RentVO> resultList = (List<RentVO>) request.getAttribute("resultList");
	String selectedDate = (String) request.getAttribute("selectedDate");

	String pName = "";
	String stId = "";

	pName = resultList.size() == 0 ? "" : resultList.get(0).getpName();
	stId = resultList.size() == 0 ? "" : "[재고 ID : " + resultList.get(0).getStId() + "]";

	// 받은 년도, 월 구분
	String getYear = selectedDate.substring(0, 4);
	String getMonth = selectedDate.substring(4, selectedDate.length());

	int tempYear = Integer.parseInt(getYear);
	int tempMonth = Integer.parseInt(getMonth);

	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.YEAR, tempYear);
	cal.set(Calendar.MONTH, tempMonth - 1);
	int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=contextPath%>/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<title>상품 별 대여 현황</title>
	<style>
		.row {
			margin-bottom: 1em;
		}

		.statisticMonth td, .statisticMonth th{
			text-align: center;
		}
	</style>
</head>
<body>
<div id="container">
	<header>
	</header>
	<section class="main">
		<section class="row">
			<div class="col-md-8 col-md-offset-2">
				<h2>상품 별 대여 현황 <span>(<%= selectedDate %> 월)</span></h2>
			</div>
		</section>
		<section class="row">
			<div class="col-md-8 col-md-offset-2 text-right">
				<a href="<%= contextPath %>/sales/salesList.do" class="btn btn-default">목록으로</a>
			</div>
			<div class="col-md-8 col-md-offset-2">
				<table class="table table-bordered">
					<caption class="sr-only">상품 별 대여 현황 (<%= selectedDate %>) 목록</caption>
					<thead>
					<tr>
						<th scope="row">상품 : </th>
						<td><%= pName %> <%= stId %></td>
					</tr>
					<tr>
						<th>대여 일자</th>
						<th>회원 ID</th>
						<th>회원명</th>
						<th>대여료</th>
						<th>반납 / 대여</th>
						<th>반납 일자</th>
						<th>연체료</th>
					</tr>
					</thead>
					<tfoot>

					</tfoot>
					<tbody>
					<%
						for(RentVO item : resultList){
					%>
						<tr>
							<td>
								<%=Tools.customToEmptyBlank(item.getrRentdate(), "")%>
							</td>
							<td class="rentals">
								<%= Tools.customToEmptyBlank(item.getmId(), "") %>
							</td>
							<td>
								<%= Tools.customToEmptyBlank(item.getmName(), "") %>
							</td>
							<td class="charges">
								<%= Tools.customToEmptyBlank(item.getrCharge(), "0") %>
							</td>
							<td>
								<%
									String returnStatus = Tools.customToEmptyBlank(item.getrReturnStatus(), "");
									returnStatus = returnStatus.equals("Y") ? "반납" : "대여";
								%>
								<%= returnStatus %>
							</td>
							<td>
								<%= Tools.customToEmptyBlank(item.getrReturndate(), "") %>
							</td>
							<td>
								<%= Tools.customToEmptyBlank(item.getrArrears(), "") %>
							</td>
						</tr>
					<% }%>
					</tbody>
				</table>

				<% if (resultList.size() <= 0){ %>
				<div class="text-center">
					<span class="text-danger">조회한 값의 데이터가 없습니다.</span>
				</div>
				<% }else{ %>
				<table class="table table-bordered table-striped statisticMonth">
					<caption >상품_ <%= pName %> 대여 통계 (<%= selectedDate + "01" %> 부터 <%=selectedDate + lastDay %> 까지 )</caption>
					<thead class="bg-success">
						<tr>
							<th>월 대여 수 (일)</th>
							<th>월 평균 대여 (일, 월 대여 수 / <%= lastDay %>일)</th>
							<th>월 대여료 (원)</th>
							<th>월 평균 대여료 (원, 월 대여료 / <%= lastDay %> 일)</th>
						</tr>
					</thead>
					<tbody class="bg-info">
						<tr>
							<td class="statisticsCal1"></td>
							<td class="statisticsCal2"></td>
							<td class="statisticsCal3"></td>
							<td class="statisticsCal4"></td>
						</tr>
					</tbody>
				</table>
				<% }%>

			</div>
		</section>
	</section>
	<footer></footer>
</div>
</body>
<script src="<%=contextPath%>/webjars/jquery/3.2.1/jquery.min.js"></script>
<script src="<%=contextPath%>/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
<script>
    $(function() {
        calStatistic();

        function calStatistic(){
            var totalRent = 0;
            var avgRent = 0;
            var totalCharge = 0;
            var avgCharge = 0;
            var totalArrears = 0;

            // 월 대여 수
            $('.rentals').each(function(){
                totalRent += 1;
            });

            // 평균 대여 수
            avgRent = Math.round((totalRent / <%= lastDay %>) * 100) / 100;

            // 월 대여료
            $('.charges').each(function(){
                totalCharge += Number($(this).html());
            });

            // 월 평균 대여료
            avgCharge = Math.round((totalCharge / <%= lastDay %>) * 100) / 100;


            // 통계 셀에 반영
            $('.statisticsCal1').html(totalRent);
            $('.statisticsCal2').html(avgRent);
            $('.statisticsCal3').html(totalCharge);
            $('.statisticsCal4').html(avgCharge);
        }
    });
</script>
</html>