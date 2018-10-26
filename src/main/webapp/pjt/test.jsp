<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script type="text/javascript">
	$(document).ready(funtion()){
		$("#tree").dynatree({
			//Ajax통신으로 data값을 넘겨 결과값 받기
			initAjax:[
				url:"/test/getTree.json"
				data:{folderId:"01"}
			],
			//항목을 선택하면 해당 항목 이름을 state에 출력
			onActivate:function(node){
				$("#state").html(node.data.title);
			}
		});
	});
</script>
<title>Insert title here</title>
</head>
<body>
	<div id="tree">
	</div>
	<div>
		선택한 노드 : <span id = "state">-</span>
	</div>
</body>
</html>