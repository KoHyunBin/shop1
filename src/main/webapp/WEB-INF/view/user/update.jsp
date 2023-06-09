<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- /shop1/src/main/webapp/WEB-INF/view/user/update.jsp --%>
<%@ include file="/WEB-INF/view/jspHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자정보 수정</title>
</head>
<body>
<h2>사용자 수정</h2>
<form:form modelAttribute="user" method="post" action="update">
	<spring:hasBindErrors name="user">
		<font color="red">
		<%-- ${errors.globalErrors} : controller에서 bresult.reject(코드값)메서드로
				추가한 error 코드 값들 --%>
			<c:forEach items="${errors.globalErrors}" var="error">
<%--
	<spring:message code="${error.code}"/> : 코드값에 해당하는 메세지를 출력
											 message.properties 파일에 설정
	${error.code} : reject(코드값)으로 등록한 코드값
 --%>
				<spring:message code="${error.code}"/>
			</c:forEach>
		</font>
	</spring:hasBindErrors>
	<table>
		<tr>
			<td>아이디</td>
			<td><form:input path="userid" readonly="true"/>
			<font color="red"><form:errors path="userid"/></font></td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><form:password path="password"/>
			<font color="red"><form:errors path="password"/></font></td>
		</tr>
		<tr>
			<td>이름</td>
			<td><form:input path="username"/>
			<font color="red"><form:errors path="username"/></font></td>
		</tr>
		<tr>
			<td>전화번호</td>
			<td><form:input path="phoneno"/></td>
		</tr>
		<tr>
			<td>우편번호</td>
			<td><form:input path="postcode"/></td>
		</tr>
		<tr>
			<td>주소</td>
			<td><form:input path="address"/></td>
		</tr>
		<tr>
			<td>이메일</td>
			<td><form:input path="email"/>
			<font color="red"><form:errors path="email"/></font></td>
		</tr>
		<tr>
			<td>생년월일</td>
			<td><form:input path="birthday"/>
			<font color="red"><form:errors path="birthday"/></font></td>
		</tr>
		<tr>
			<td>
				<input type="submit" value="회원수정">
				<input type="reset" value="초기화">
			</td>
		</tr>
	</table>
</form:form>
</body>
</html>