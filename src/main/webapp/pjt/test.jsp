<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<script type="text/javascript">
	$(document).ready(funtion()){
		$("#tree").dynatree({
			//Ajax������� data���� �Ѱ� ����� �ޱ�
			initAjax:[
				url:"/test/getTree.json"
				data:{folderId:"01"}
			],
			//�׸��� �����ϸ� �ش� �׸� �̸��� state�� ���
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
		������ ��� : <span id = "state">-</span>
	</div>
</body>
</html>