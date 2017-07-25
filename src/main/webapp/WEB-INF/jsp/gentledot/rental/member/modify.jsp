<%@page import="net.gentledot.rental.vo.MemberVO"%>
<%@ page import="net.gentledot.utils.Tools" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko-KR">
<%
	String contextPath = request.getContextPath();
	MemberVO oneOfMember = (MemberVO) request.getAttribute("oneOfMember");

    String memberStatus = Tools.customToEmptyBlank(oneOfMember.getmStatus(), "").equals("M") ? "회원" : "탈퇴";

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=contextPath%>/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<title>회원 정보 수정</title>
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
				<h2 class="h2">회원 정보 수정</h2>
				<form action="<%= contextPath %>/member/memberModify.do" method="post" name="memberModifyForm">
					<ul class="list-group">
						<li class="list-group-item form-group">
							<label for="getMId">회원 ID : </label>
							<input type="text" id="getMId" name="getMId" class="form-control"
								   value="<%= Tools.customToEmptyBlank(oneOfMember.getmId(), "") %>" readonly/>
						</li>
						<li class="list-group-item form-group">
							<label for="getMName">회원 이름 : </label>
							<input type="text" id="getMName" name="getMName" class="form-control"
								   value="<%= Tools.customToEmptyBlank(oneOfMember.getmName(), "") %>" readonly/>
						</li>
						<li class="list-group-item form-group">
							<label for="getMBirth">회원 생일 : </label>
							<input type="text" id="getMBirth" name="getMBirth" class="form-control"
								   value="<%= Tools.customToEmptyBlank(oneOfMember.getmBirth(), "99999999")%>"
								   readonly/>
						</li>
						<li class="list-group-item form-group">
							<label for="getMJoinDate">회원가입일 : </label>
							<input type="text" id="getMJoinDate" name="getMJoinDate" class="form-control"
								   value="<%= Tools.customToEmptyBlank(oneOfMember.getmJoinDate(), "99999999") %>"
								   readonly/>
						</li>
						<li class="list-group-item form-group">
							<label for="getMAddr">주소 : </label>
							<input type="text" id="getMAddr" name="getMAddr" class="form-control"
								   value="<%= Tools.customToEmptyBlank(oneOfMember.getmAddr(), "") %>"/>
						</li>
						<li class="list-group-item form-group">
							<label for="getMPhone">전화번호 : </label>
							<input type="number" id="getMPhone" name="getMPhone" class="form-control" min="0"
								   value="<%= Tools.customToEmptyBlank(oneOfMember.getmPhone(), "") %>"/>
						</li>
						<li class="list-group-item form-group">
							<label for="getMMail">이메일 : </label>
							<input type="email" id="getMMail" name="getMMail" class="form-control"
								   value="<%= Tools.customToEmptyBlank(oneOfMember.getmMail(), "") %>"/>
						</li>
						<li class="list-group-item form-group">
							<label for="getMLimit">대여제한 : </label>
							<input type="number" id="getMLimit" name="getMLimit" class="form-control"
								   value="<%= Tools.customToEmptyBlank(oneOfMember.getmLimit(), "") %>" readonly/>
						</li>
						<li class="list-group-item form-group text-center">
							<button type="button" class="btn btn-danger" name="outMemberBtn">탈퇴처리</button>
						</li>
						<li class="list-group-item form-group">
							<label for="getMStatus">회원상태 : </label>
							<input type="text" id="getMStatus" name="getMStatus" class="form-control"
								   value="<%= memberStatus %>" readonly/>
						</li>
						<li class="list-group-item form-group">
							<label for="getMOutdate">탈퇴일 : </label>
							<input type="text" id="getMOutdate" name="getMOutdate" class="form-control"
								   value="<%= Tools.customToEmptyBlank(oneOfMember.getmOutDate(), "") %>" readonly/>
						</li>
					</ul>
					<div class="form-group">
						<input type="submit" class="btn btn-info" value="수정">
					</div>
				</form>

                <div class="btn-group">
                    <a href="<%= contextPath %>/member/memberList.do" class="btn btn-default">목록으로</a>
                </div>

			</div>
		</section>
	</section>
	<footer></footer>
</div>
</body>
<script src="<%=contextPath%>/webjars/jquery/3.2.1/jquery.min.js"></script>
<script src="<%=contextPath%>/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
</html>