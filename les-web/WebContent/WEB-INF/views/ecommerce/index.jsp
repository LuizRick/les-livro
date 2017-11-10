<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:c="http://java.sun.com/jsp/jstl/core">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</head>
<body>
	<t:template>
		<jsp:body>
		<div class="row">
			<c:forEach var="livro" items="${livros}">
					<div class='col-lg-3'>
						<div class='thumbnail'>
							<img src="img/img.jpg" alt="livro" />
							<div class="caption">
								<h3>${livro.getTitulo()}</h3>
								<p style="height: 50px">
									${livro.sinopse }
								</p>
								<p>
									<b>R$:<c:out value="${livro.valor }"></c:out></b>
								</p>
								<p>
									<a href="produto?id=${livro.id}" class="btn btn-primary">VISUALIZAR</a>
								</p>
							</div>
						</div>
					</div>
			</c:forEach>
			</div>
        </jsp:body>
	</t:template>
</body>
</html>