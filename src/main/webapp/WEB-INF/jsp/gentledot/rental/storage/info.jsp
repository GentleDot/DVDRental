<%@ page import="net.gentledot.rental.vo.StorageVO" %>
<%@ page import="net.gentledot.utils.Tools" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko-KR">
<%
	String contextPath = request.getContextPath();
	StorageVO oneOfStorage = (StorageVO) request.getAttribute("oneOfStorage");

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=contextPath%>/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<title>재고 상세조회</title>
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
			<div class="col-md-8 col-md-offset-2">
				<h2 class="h2">재고 정보</h2>
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
						<label for="getStStatus">재고상태 : </label>
						<input type="text" id="getStStatus" name="getStStatus" class="form-control" value="<%= Tools.customToEmptyBlank(oneOfStorage.getStStatus(), "") %>" readonly />
					</li>
					<li class="list-group-item form-group">
						<label for="getStWastedate">폐기일 : </label>
						<input type="text" id="getStWastedate" name="getStWastedate" class="form-control" value="<%= Tools.customToEmptyBlank(oneOfStorage.getStWastedate(), "") %>" readonly />
					</li>
					<li class="list-group-item form-group">
						<label for="getStWastecost">폐기비용 : </label>
						<input type="text" id="getStWastecost" name="getStWastecost" class="form-control" value="<%= Tools.customToEmptyBlank(oneOfStorage.getStWastecost(), "") %>" readonly />
					</li>
					<li class="list-group-item form-group">
						<label for="getStWasteReason">사유 : </label>
						<input type="text" id="getStWasteReason" name="getStWasteReason" class="form-control" value="<%= Tools.customToEmptyBlank(oneOfStorage.getStWasteReason(), "") %>" readonly />
					</li>
				</ul>

                <div class="btn-group">
                    <a href="<%= contextPath %>/storage/storageList.do" class="btn btn-default">목록으로</a>
                    <a href="<%= contextPath %>/storage/storageModifyView.do?stId=<%= oneOfStorage.getStId() %>" id="stModifyBtn" class="btn btn-info">정보수정</a>
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
        stockStatusChk();

        // 상태가 정상이 아닌 경우에 수정되지 않도록 설정
        function stockStatusChk(){
            var status = $('#getStStatus').val();

            if(status !== "정상"){
                $('#stModifyBtn').attr('disabled', true).bind('click', false);
            }
        }
	});
</script>
</html>