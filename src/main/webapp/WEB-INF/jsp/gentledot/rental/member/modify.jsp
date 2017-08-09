<%@page import="net.gentledot.rental.vo.MemberVO"%>
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
	MemberVO oneOfMember = (MemberVO) request.getAttribute("oneOfMember");

    String memberStatus = Tools.customToEmptyBlank(oneOfMember.getmStatus(), "").equals("M") ? "회원" : "탈퇴";

    Date date = new Date();

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
	Date tempBirth= null;
	Date tempJoindate= null;
	try {
		tempBirth = sdf2.parse(oneOfMember.getmBirth());
		tempJoindate = sdf2.parse(oneOfMember.getmJoinDate());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String mBirth = sdf.format(tempBirth);
	String mJoindate = sdf.format(tempJoindate);
	String curDate = sdf2.format(date);

	String mPhone = oneOfMember.getmPhone();

	String tel1 = "";
	String tel2 = "";
	String tel3 = "";
	if (mPhone == null || mPhone.equals("")) {

	}else{
		tel1 = mPhone.substring(0, 3);

		int mPhoneLen = mPhone.length();
		if (mPhoneLen == 11){
			tel2 = mPhone.substring(3, 7);
			tel3 = mPhone.substring(7, 11);
		}else if(mPhoneLen == 10){
			tel2 = mPhone.substring(3, 7);
			tel3 = mPhone.substring(6, 10);
		}
	}

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=contextPath%>/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<title>회원 정보 수정</title>
	<style>
		.row{
			margin-bottom: 1em;
		}

		.numBox{
			width: 32.5%;
			height: 34px;
			padding: 6px 12px;
			font-size: 14px;
			line-height: 1.42857143;
			color: #555;
			background-color: #fff;
			background-image: none;
			border: 1px solid #ccc;
			border-radius: 4px;
			-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
			box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
			-webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
			-o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
			transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;

		}

		/*출처: http://web-atelier.tistory.com/16 [웹아틀리에 - Web atelier]*/
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
				<h2 class="h2">회원 정보 수정</h2>
				<form action="<%= contextPath %>/member/memberModify.do" method="post" class="form-horizontal" name="memberModifyForm">
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
								   value="<%= Tools.customToEmptyBlank(mBirth, "9999-99-99")%>"
								   readonly/>
						</li>
						<li class="list-group-item form-group">
							<label for="getMJoinDate">회원가입일 : </label>
							<input type="text" id="getMJoinDate" name="getMJoinDate" class="form-control"
								   value="<%= Tools.customToEmptyBlank(mJoindate, "9999-99-99") %>"
								   readonly/>
						</li>
						<li class="list-group-item form-group">
							<label for="getMAddr">주소 : </label>
							<input type="text" id="getMAddr" name="getMAddr" class="form-control"
								   value="<%= Tools.customToEmptyBlank(oneOfMember.getmAddr(), "") %>"/>
						</li>
						<li class="list-group-item form-group">
							<label for="phoneNum">전화번호 : </label> <br/>
							<select name="phoneNum" id="phoneNum" class="numBox">
								<option value="010">010</option>
								<option value="011">011</option>
								<option value="016">016</option>
								<option value="017">017</option>
								<option value="018">018</option>
								<option value="019">019</option>
								<option value="0130">0130</option>
							</select>

							<input type="number" id="phoneNum2" name="phoneNum2" class="numBox" min="0" max="9999" value="<%= tel2 %>"/>
							<input type="number" id="phoneNum3" name="phoneNum3" class="numBox" min="0" max="9999" value="<%= tel3 %>" />
						</li>
						<li class="list-group-item form-group">
							<label for="getMMail">이메일 : </label>
							<input type="email" id="getMMail" name="getMMail" class="form-control"
								   value="<%= Tools.customToEmptyBlank(oneOfMember.getmMail(), "") %>"/>
						</li>
						<%--<li class="list-group-item form-group">
							<label for="chkMail">이메일 확인: </label>
							<input type="email" id="chkMail" name="chkMail" class="form-control"
								   placeholder="<%= Tools.customToEmptyBlank(oneOfMember.getmMail(), "") %>"/>
						</li>--%>
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
						<input type="hidden" id="getMPhone" name="getMPhone" />
						<input type="submit" class="btn btn-info" value="수정" />
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

<script>
	$(function(){
	    tel1Selector();

	    $('form[name="memberModifyForm"] input[type="submit"]').click(buildTelNum);

	    $('button[name="outMemberBtn"]').click(memberOut);


		function tel1Selector(){
		    $('#phoneNum > option[value="<%= tel1 %>"]').attr("selected", "true");
		}

		function buildTelNum(){
		    var tel1 = $('#phoneNum').val();
		    var tel2 = $('#phoneNum2').val();
		    var tel3 = $('#phoneNum3').val();

		    var rebuildTelNum = tel1 + tel2 + tel3;

		    $('#getMPhone').val(rebuildTelNum);
		}

		function memberOut(){
		    if(confirm('경고 : 회원 탈퇴 처리 후에는 다시 변경할 수 없습니다.')){
				$('#getMStatus').val('탈퇴');
				$('#getMOutdate').val(<%= curDate %>);
				alert('수정 버튼을 눌러야 탈퇴가 반영됩니다. ');
			}else{

			}
		}
	});
</script>
</html>