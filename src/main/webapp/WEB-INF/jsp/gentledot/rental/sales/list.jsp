<%@page import="net.gentledot.rental.vo.SalesVO"%>
<%@ page import="net.gentledot.utils.Tools" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko-KR">
<%
	String contextPath = request.getContextPath();
	List<SalesVO> resultList = (List<SalesVO>) request.getAttribute("resultList");
	String selectedYear = (String) request.getAttribute("selectedYear");
	String selectedMonth = (String) request.getAttribute("selectedMonth");
	int pageNo = (Integer) request.getAttribute("pageNo");

	NumberFormat nf = new DecimalFormat("00");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=contextPath%>/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<title>매출 조회</title>
	<style>
		.row {
			margin-bottom: 1em;
		}

		.gains{
			background-color : #32506d;
			color: #fff;
		}
		.cost{
			background-color: #fff8ea;
		}
	</style>
</head>
<body>
<div id="container">
	<header>
		<jsp:include page ="/WEB-INF/jsp/gentledot/inc/menu_header.jsp">
			<jsp:param name="curPage" value="sales" />
		</jsp:include>
	</header>
	<section class="main">
		<section class="row">
			<div class="col-md-8 col-md-offset-2">
				<h2>매출 현황</h2>
				<form action="<%= contextPath %>/sales/salesList.do" method="post" class="form-inline" name="searchPeriodForm">
					<fieldset class="form-group">
						<legend>매출 검색</legend>
						<div class="form-group">
							<select name="selectedYear" id="selectedYear" class="form-control">
						<%
							for(int i = 2000; i <= 2100; i++){
						%>
								<option value="<%= i %>"><%=i %>년</option>
						<%
							}
						%>
							</select>
							<select name="selectedMonth" id="selectedMonth" class="form-control">
								<%
									for(int i = 1; i <= 12; i++){
								%>
								<option value="<%= nf.format(i) %>"><%=i %>월</option>
								<%
									}
								%>
							</select>
						</div>
						<div class="form-group">
							<input type="submit" class="btn btn-default" value="검색"/>
						</div>
					</fieldset>
					<input type="hidden" name="pageNo" value="<%= pageNo %>">
				</form>
			</div>
		</section>
		<section class="row">
			<div class="col-md-8 col-md-offset-2">
				<table class="table table-bordered">
					<caption class="sr-only">DVD 대여점 매출 현황</caption>
					<thead>
					<tr>
						<th>일자</th>
						<th>회원ID</th>
						<th>상품ID(제품id)</th>
						<th>대여료</th>
						<th>연체료</th>
						<th>구입비용</th>
						<th>폐기비용</th>
					</tr>
					</thead>
					<tfoot>
					<tr>
						<th class="text-center" colspan="3">수  익</th>
						<td id="totalGains" class="text-center text-info h3" colspan="2"></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<th class="text-center" colspan="3">비  용</th>
						<td></td>
						<td></td>
						<td id="totalCosts" class="text-center text-danger h3" colspan="2"></td>
					</tr>
					<tr>
						<th class="text-center" colspan="3">총  계</th>
						<td id="calRevenue" class="text-center h2" colspan="4"></td>
					</tr>
					</tfoot>
					<tbody>
					<%
						for(SalesVO sales : resultList){
					%>
						<tr>
							<td>
								<%=Tools.customToEmptyBlank(sales.getsDate(), "")%>
							</td>
							<td>
								<a href="<%=contextPath%>/sales/rentStatusOfMember.do?mId=<%= Tools.customToEmptyBlank(sales.getmId(), "") %>&rentdate=<%= sales.getsDate().substring(0, 6) %>"><%= Tools.customToEmptyBlank(sales.getmId(), "")%>
								</a>
							</td>
							<td>
								<a href="<%=contextPath%>/sales/rentStatusOfGoods.do?stId=<%= Tools.customToEmptyBlank(sales.getStId(), "") %>&rentdate=<%= sales.getsDate().substring(0, 6) %>">
								<%= Tools.customToEmptyBlank(sales.getStId(), "")%>
								</a>
							</td>
							<td class="gains charge">
								<%= Tools.customToEmptyBlank(sales.getsCharge(), "0")%>
							</td>
							<td class="gains arrears">
								<%=Tools.customToEmptyBlank(sales.getsArrears(), "0")%>
							</td>
							<td class="cost price">
								<%=Tools.customToEmptyBlank(sales.getsPrice(), "0")%>
							</td>
							<td class="cost waste">
								<%=Tools.customToEmptyBlank(sales.getsWasteCost(), "0")%>
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
    $(function(){
        $("input:submit").click(pageNoInitializer);
        searchPeriodReminder();
        calTotalReturns();


        function pageNoInitializer(){
            $("input[name='pageNo']").val(1);
        }

        function searchPeriodReminder(){
            $('#selectedYear > option[value="<%= selectedYear %>"]').attr("selected", "true");
            $('#selectedMonth > option[value="<%= selectedMonth %>"]').attr("selected", "true");
        }

        // 각 수익과 비용 총계 구하기
        function calTotalReturns(){
            var totalCharge = 0;
            var totalArrears = 0;
            var totalPrice = 0;
            var totalWaste = 0;

            $('.charge').each(function(){
				totalCharge += Number($(this).html());
			});
            $('.arrears').each(function(){
                totalArrears += Number($(this).html());
            });
            $('.price').each(function(){
                totalPrice += Number($(this).html());
            });
            $('.waste').each(function(){
                totalWaste += Number($(this).html());
            });

			$('#totalGains').html(totalCharge + totalArrears);
			$('#totalCosts').html(totalPrice + totalWaste);

			var result = (totalCharge + totalArrears) - (totalPrice + totalWaste);

			if (result > 0){
				$('#calRevenue').addClass("text-success").html(result);
			}else if(result === 0){
                $('#calRevenue').html(result);
			}else{
                $('#calRevenue').addClass("text-warning").html(result);
			}

		}
	});

    function goPage(pageNo){
        document.searchPeriodForm.pageNo.value = pageNo;
        document.searchPeriodForm.submit();
    }
</script>
</html>