<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	    		<div class="col-lg-12">
	    		<h3 class="title-header">Carrinho de compras</h3>
	    			<c:if test="${!empty resultado.msg}">
	    				<div class="alert alert-info">
	    					Livro: <strong>${resultado.entidades[0].titulo}</strong> ${resultado.msg}
	    				</div>
	    			</c:if>
	    			<c:if test="${!empty carrinho.itens }">
	    				<c:forEach var="item" items="${carrinho.itens }">
	    				<form action="editarItem" method="post">
							<div class="row">
								<div class="col-lg-6 col-sm-12" style="background: #fff;margin-bottom: 30px;">
									<div class="media" style="background:#f4f4f4">
										<div class="media-left">
											<a href="javascript:void(0)">
												<img class="media-object" src="img/img.jpg"/>
											</a>
										</div>
										<div class="media-body">
											<h4 class="media-heading"><c:out value="${item.produto.titulo }"/></h4>
											<p>Valor Unitário R$ ${item.produto.valor}</p>
											<p>Estoque ${item.produto.estoque }</p>
											<p>Total(R$): <fmt:formatNumber value="${item.quantidade * item.produto.valor}"/> </p>
											<input type="hidden" name="id" value="${item.id }"/>
											<div class="form-group">
												<label>Quantidade:</label>
												<input type="number" value="${item.quantidade }" name="quantidade" class="form-control "/>
											</div>
										</div>
									</div>
									<div class="" style="padding:12px">
										<input class="btn btn-primary hidden alterar-qtd" type="submit" name="operacao" value="ALTERAR QUANTIDADE" />
										<input type="submit" name="operacao" value="REMOVER" class="btn btn-danger"/>
									</div>
									<hr/>
								</div>
							</div>
						</form>
						</c:forEach>
						<footer>
							<a href="finalizar" class="btn btn-success">Finalizar</a>
						</footer>
	    			</c:if>
	    			<c:if test="${empty carrinho.itens}">
	    				<div class='alert alert-danger'>
	    					<span class='text-primary'><strong>Carrinho vazio!!!</strong></span>
	    				</div>
	    			</c:if>
	    		</div>
	    	</div>
	    </jsp:body>
	</t:template>
</body>
</html>