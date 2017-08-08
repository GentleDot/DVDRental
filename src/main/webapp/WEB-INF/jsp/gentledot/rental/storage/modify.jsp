<%@ page import="net.gentledot.rental.vo.StorageVO" %>
<%@ page import="net.gentledot.utils.Tools" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko-KR">
<%
	String contextPath = request.getContextPath();
	StorageVO oneOfStorage = (StorageVO) request.getAttribute("oneOfStorage");

	String getWastdate = Tools.customToEmptyBlank(oneOfStorage.getStWastedate(), "");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

	Date tempWastedate = null;
	if (!(getWastdate.equals(""))){
	 	tempWastedate = sdf2.parse(getWastdate);
	}

	String wastedate = getWastdate.equals("") ? "" : sdf.format(tempWastedate);

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=contextPath%>/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
	<title>재고 정보 수정</title>
	<style>
		.row{
			margin-bottom: 1em;
		}

		input[type="number"]::-webkit-outer-spin-button,
		input[type="number"]::-webkit-inner-spin-button {
			-webkit-appearance: none;
			margin: 0;
		}

		input[disabled]{
			background-color: #aaa !important;
		}
	</style>
</head>
<body>
<div id="container">
	<header></header>
	<section class="main">
		<section class="row">
			<div class="col-md-8 col-md-offset-2">
				<h2 class="h2">재고 정보 수정</h2>
				<form action="<%= contextPath %>/storage/storageModify.do" method="post" class="form-horizontal" name="modifyStorageForm">
					<ul class="list-group">
						<li class="list-group-item form-group">
							<label for="getStId">ID : </label>
							<input type="text" id="getStId" name="getStId" class="form-control" value="<%= oneOfStorage.getStId() %>" readonly/>
						</li>
						<li class="list-group-item form-group">
							<label for="getStGetdate">입고일 : </label>
							<input type="text" id="getStGetdate" name="getStGetdate" class="form-control" value="<%= Tools.customToEmptyBlank(oneOfStorage.getStGetdate(), "99999999") %>" readonly/>
						</li>
						<li class="list-group-item form-group">
							<label for="getPname">제품명 : </label>
							<input type="text" id="getPname" name="getPname" class="form-control" value="<%= Tools.customToEmptyBlank(oneOfStorage.getpName(), "") %> [ID : <%= Tools.customToEmptyBlank(oneOfStorage.getpId(), "1") %>]" readonly/>
						</li>
						<li class="list-group-item form-group">
							<label for="getStstatus">재고상태 : </label>
							<select name="getStStatus" id="getStStatus" class="form-control">
								<option value="정상">정상</option>
								<option value="대여" disabled="disabled">대여</option>
								<option value="분실">분실</option>
								<option value="파손">파손</option>
							</select>
						</li>
						<li class="list-group-item form-group">
							<label for="getStWastedate">폐기일 : </label>
							<input type="date" id="getStWastedate" name="getStWastedate" class="form-control" value="<%= wastedate %>" disabled />
						</li>
						<li class="list-group-item form-group">
							<label for="getStWastecost">폐기비용 : </label>
							<input type="number" id="getStWastecost" name="getStWastecost" class="form-control" min="0" value="<%= Tools.customToEmptyBlank(oneOfStorage.getStWastecost(), "") %>" disabled />
						</li>
						<li class="list-group-item form-group">
							<label for="getStWasteReason">사유 : </label>
							<input type="text" id="getStWasteReason" name="getStWasteReason" class="form-control" value="<%= Tools.customToEmptyBlank(oneOfStorage.getStWasteReason(), "") %>" disabled />
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
	    statusSelector();

	    if("<%= Tools.customToEmptyBlank(oneOfStorage.getStStatus(), "정상") %>" === "정상"){
            $('#getStStatus').bind('blur', wasteChk);
        }
	    // select에 상태 정보가 반영되도록 설정
		function statusSelector(){
		    $('#getStStatus > option[value="<%= Tools.customToEmptyBlank(oneOfStorage.getStStatus(), "정상")%>"').attr("selected", "true");
		}

		// 상태가 '정상'이 아닌 경우 폐기 정보를 입력 가능하도록 설정
		function wasteChk(){
		    var status = $('#getStStatus').val();

		    if (!(status === "정상" || status === "대여")){
		        $('#getStWastedate').attr('disabled', false);
		        $('#getStWastecost').attr('disabled', false);
		        $('#getStWasteReason').attr('disabled', false);
			}else{
                $('#getStWastedate').attr('disabled', true).val("");
                $('#getStWastecost').attr('disabled', true).val("");
                $('#getStWasteReason').attr('disabled', true).val("");
			}
		}
	});
</script>
</html>