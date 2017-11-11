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
			<form action="addcarrinho" method="post">
			<input type="hidden" name="id" value="${livro.id}"/>
				<div class="row">
					<div class="col-lg-6">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3>
								<c:out value="${livro.titulo}"></c:out>
							</h3>
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<img src="img/img.jpg" alt="livro" class="img-thumbnail" />
								</div>
								<div class="col-lg-6">
									<p>
										<b>Categorias:</b>
										<c:forEach var="categoria" items="${livro.categoria }">
											<c:out value="${categoria.nome }"></c:out>
										</c:forEach>
									</p>
									<p>
										<c:out value="${livro.sinopse }"></c:out>
									</p>
									<div>
										<button class="btn btn-success">Comprar</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				</div>
			</form>
        </jsp:body>
	</t:template>
</body>
</html>