<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../header.jsp" %>

<!-- 본문 시작 template.jsp (view안에 페이지 작성할 것)-->
<div class="middle-bar">
	<a href="<%=request.getContextPath() %>/master/manage.do">관리자 페이지</a>
</div>
<div class="tbframe">
	<div align="center">
	<br>
	<img src="../resources/images/hotsource.PNG" width="1%"> &nbsp;&nbsp;
		<span style="font-size : 20px; font-weight : bold;">빈 / 도 / 분 / 석</span>
	</div>
	<br>
	<span align="center" style="background-color : #f0b6b6; width:50%; margin-left : 10%; font-weight : bold;">
	빈도분석 1) 기간 내에 가장 많이 다운로드 받은 프로젝트 : 가장 인기있는 언어와 분야
	</span>
	&nbsp;&nbsp;
	<span>
		<input type="date" id="date1" name="date1" value="2018-01-01"> - <input type="date" id="date2" name="date2" > 
	</span> &nbsp;&nbsp;
	<input type="button" id="result" value="결과" onclick="window.open('./exam.do?date1=date1&date2=date2','빈도분석 결과',
	 													 'width=600px, height=300px, top=250px, left=500px')">

	<br>
	
	<span align="center" style="background-color : #f0b6b6; width:50%; margin-left : 10%; font-weight : bold;">
	빈도분석 2) 강사님이 내주실 빈도분석 문제
	</span>
	&nbsp;&nbsp;
	<input type="button" id="result2" value="결과" onclick="window.open('./wordcloud.jsp','빈도분석 결과',
	 													 'width=600px, height=300px, top=250px, left=500px')">
	
	<hr>
	<div align="center">
		<img src="../resources/images/hotsource.PNG" width="1%"> &nbsp;&nbsp;
		<span style="font-size : 20px; font-weight : bold;">회 / 원 / 관 / 리</span>
		<br><br>
		<table class="memmanage">
			<tr>
				<th>ID</th>
				<th>이름</th>
				<th>전화번호</th>
				<th>E-mail</th>
				<th>생년월일</th>
				<th>가입일자</th>
				<th>등급</th>
				<th>관리<th>
			</tr>
			<c:forEach var="dto" items="${list }">
			<tr>
				<td>${dto.id }</td>
				<td>${dto.name }</td>
				<td>${dto.ph_no }</td>
				<td>${dto.email }</td>
				<td>${dto.birth }</td>
				<td>${dto.joindate }</td>
				<td>${dto.rating }</td>
				<td>
					<input type="button" value="등급변경" onclick="window.open('./ratingupdate.do?id=${dto.id}','등급 변경',
							 	   								'width=600px, height=300px, top=250px, left=500px')">
					<input type="button" value="강퇴" onclick="window.open('./memberdelete.do?id=${dto.id}','강퇴',
							 	   								'width=600px, height=300px, top=250px, left=500px')">
				</td>
			</tr>
			</c:forEach>
		</table>
		<hr>
		<div style="float:right;">전체 회원 수 : ${count } &nbsp;&nbsp;</div>
		
		<!-- 페이지 리스트 -->
		<div align="center">
		<c:if test="${count>0 }">
		   <c:set var="pageCount" value="${totalPage }"/>
		   <c:set var="startPage" value="${startPage }"/>
		   <c:set var="endPage" value="${endPage }"/>
		
		   <c:if test="${endPage>pageCount }">
			  <c:set var="endPage" value="${pageCount+1 }"/>
		   </c:if>

		   <c:if test="${startPage>0 }">
		      <a href="./manage.do?pageNum=${startPage }">[이전]</a>	
		   </c:if>

		   <c:forEach var="i" begin="${startPage+1 }" end="${endPage-1 }">
		      <a href="./manage.do?pageNum=${i }">[${i }]</a>
		   </c:forEach>
		
		   <c:if test="${endPage<pageCount }">
		      <a href="./manage.do?pageNum=${startPage+11 }">[다음]</a>
		   </c:if>  
		</c:if>
		</div>
		
	</div>
	
	
	
</div>
<!-- 본문 끝 -->			
<script>
document.getElementById('date2').valueAsDate = new Date();

</script>
<%@ include file="../footer.jsp" %>