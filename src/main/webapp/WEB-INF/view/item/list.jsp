<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- /shop1/src/main/webapp/WEB-INF/view/item/list.jsp -->
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>��ǰ ���</title>
</head>
<body>
	<a href="create">��ǰ���</a>
	<a href="../cart/cartView" style="float:right">��ٱ���</a>
<table>
	<tr>
		<th width="80">��ǰID</th>
		<th width="320">��ǰ��</th>
		<th width="100">����</th>
		<th width="80">����</th>
		<th width="80">����</th>
	</tr>
	<c:forEach items="${itemList}" var="item">
		<tr>
			<td align="center">${item.id}</td>
			<td align="left">
				<a href="detail?id=${item.id}">${item.name}</a>
			</td>
			<td align="right">${item.price}</td>
			<td align="center">
				<a href="update?id=${item.id}">����</a>
			</td>
			<td align="center">
				<a href="delete?id=${item.id}">����</a>
			</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>