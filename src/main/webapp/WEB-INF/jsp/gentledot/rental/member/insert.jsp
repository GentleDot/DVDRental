<%@page import="net.gentledot.rental.vo.MemberVO"%>
<%@ page import="java.util.List" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<%@ page import="net.gentledot.utils.Tools" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko-KR">
<%
	String contextPath = request.getContextPath();
	Calendar calendar = Calendar.getInstance();
	int curYear = calendar.get(Calendar.YEAR);
	int curMonth = calendar.get(Calendar.MONTH) + 1;
	int curDay = calendar.get(Calendar.DAY_OF_MONTH);

	NumberFormat nf = new DecimalFormat("00");

	String curDate = "" + curYear + nf.format(curMonth) + nf.format(curDay);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=contextPath%>/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<title>회원 정보 입력</title>
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
				<h2 class="h2">회원가입 (추가)</h2>
				<form action="<%= contextPath %>/member/addMember.do" method="post" class="form-horizontal" name="addMemberForm">
					<ul class="list-group">
						<li class="list-group-item form-group">
							<label for="inputMName">회원 이름 : </label>
							<input type="text" id="inputMName" name="inputMName" class="form-control" />
						</li>
						<li class="list-group-item form-group">
							<label for="inputMBirth">회원 생일 : </label>
							<input type="date" id="inputMBirth" name="inputMBirth" class="form-control" />
						</li>
						<li class="list-group-item form-group">
							<label for="inputMJoinDate">가입일 : </label>
							<input type="text" id="inputMJoinDate" name="inputMJoinDate" class="form-control" value="<%= curDate %>" readonly />
						</li>
						<li class="list-group-item form-group">
							<label for="inputMAddr">주소 : </label>
							<input type="text" id="inputMAddr" name="inputMAddr" class="form-control" />
						</li>
						<li class="list-group-item form-group">
							<label for="inputMPhone">전화번호 : </label>
							<input type="number" id="inputMPhone" name="inputMPhone" class="form-control" min="0" />
						</li>
						<li class="list-group-item form-group">
							<label for="inputMMail">이메일 : </label>
							<input type="email" id="inputMMail" name="inputMMail" class="form-control" />
						</li>
						<li class="list-group-item form-group">
							<label for="chkEmail">이메일 확인 : </label>
							<input type="email" id="chkEmail" name="chkEmail" class="form-control" />
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
</html>