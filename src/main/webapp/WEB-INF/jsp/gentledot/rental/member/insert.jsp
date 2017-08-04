<%@page import="net.gentledot.rental.vo.MemberVO"%>
<%@ page import="java.util.List" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<%@ page import="net.gentledot.utils.Tools" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
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

	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String tempCurDate = sdf.format(date);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=contextPath%>/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<title>회원 정보 입력</title>
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

							<input type="number" id="phoneNum2" name="phoneNum2" class="numBox" min="0" max="9999" />
							<input type="number" id="phoneNum3" name="phoneNum3" class="numBox" min="0" max="9999"  />
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
						<input type="hidden" id="inputMPhone" name="inputMPhone" />
						<input type="button" id="formSubmit" class="form-control btn btn-success" value="확인">
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
	$(function () {
	    // 오늘 날짜보다 미래의 생일 날짜가 입력되지 않도록 설정
		$('#inputMBirth').blur(birthChk);
		// 이메일 확인
		$('#formSubmit').click(chkItemsBeforeSubmit);
		// 전화번호
		$('#phoneNum2').blur(phoneNumChk);
		$('#phoneNum3').blur(phoneNumChk);

        function birthChk(){
            var inputBirthStr = $('#inputMBirth').val();
            var inputBirth = Date.parse(inputBirthStr);
            var curDate = new Date();

            if (inputBirth > curDate){
                alert('올바른 생일을 입력해주세요. (생일은 오늘 날짜보다 미래일 수 없습니다.)');
                $('#inputMBirth').val('<%= tempCurDate %>');
                $('#inputMBirth').focus();
            }
        }

        function chkItemsBeforeSubmit(){
            emailChk();
			buildTelNum();

            $('form[name="addMemberForm"]').submit();
		}

        function emailChk(){
            var inputEMail = $('#inputMMail').val();
            var checkEMail = $('#chkEmail').val();

            if (inputEMail != checkEMail){
                alert('이메일이 올바르지 않습니다. 다시 입력해주세요.');
                $('#chkEmail').val('');
                $('#inputMMail').val('').focus();
                return;
            }
        }

        function buildTelNum(){
            var tel1 = $('#phoneNum').val();
            var tel2 = $('#phoneNum2').val();
            var tel3 = $('#phoneNum3').val();

            var rebuildTelNum = tel1 + tel2 + tel3;

            $('#inputMPhone').val(rebuildTelNum);
        }

        function phoneNumChk(){
            if($(this).val() > 0 && ($(this).val() < 100 || $(this).val() > 9999)){
                alert('올바른 전화번호를 입력해주세요.');
                $(this).val('').focus();
            }
        }
    });
</script>
</html>