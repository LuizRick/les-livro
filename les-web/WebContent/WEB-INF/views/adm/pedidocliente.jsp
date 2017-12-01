<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="ju" uri="/WEB-INF/tld/json.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
<link rel="stylesheet"
	href="/les-web/resources/css/bootstrap-theme.min.css" />
<link rel="stylesheet" href="/les-web/resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="/les-web/resources/css/style.css" />
</head>
<body>
	<script>
		window.compra = ${ju:toJson(pedido)};
	</script>
	<div class="container-fluid" ng-controller="ControllerPedido as pedido">
		<div class="row">
			<div class="col-lg-8">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4>Pedido</h4>
						<a href="http://localhost:8080/les-web/index.xhtml">Indice</a> / <a
							href="/les-web/pedidos/listar">Listar pedidos</a>
					</div>
					<div class="panel-body">
						<p class="text-left">
							<span>Codigo compra:</span> <b>{{pedido.compra.id}}</b>
						</p>
						<p class="text-left">
							<span>Status:</span><b>{{pedido.compra.statusCompra}}</b>
						</p>
						<p class="text-left">
							<span>Total: </span><b>{{pedido.compra.total}} Reais</b>
						</p>
						<p class="text-left">
							<span>Endereço de Entrega :</span><b>{{pedido.compra.frete.enderecoEntrega}}</b>
						</p>
						<fieldset>
							<legend>Produtos</legend>
							<table class="table table-hover">
								<thead>
									<th>Titulo</th>
									<th>Quantidade</th>
									<th>Valor</th>
								</thead>
								<tr ng-repeat="item in pedido.compra.produtos.itens">
									<td>{{item.produto.titulo}}</td>
									<td>{{item.quantidade}}</td>
									<td><b>R$</b>{{item.produto.valor}}</td>
								</tr>
							</table>
						</fieldset>
						<fieldset>
							<legend>Status Pedido</legend>
							<form action="alterarpedido" method="post">
								<div class="form-group">
									<label>Status:</label>
									<select name="statusCompra" class="form-control" id="statusCompra">
										<option value="Aprovado">Aprovado</option>
										<option value="Transporte">Transporte</option>
										<option value="Entregue">Entregue</option>
									</select>
								</div>
								<input type="hidden" name="compra" value='${ju:toJson(pedido)}'/>
								<input type="submit" name="operacao" value="ALTERAR" class="btn btn-success"/>
							</form>
						</fieldset>
					</div>
					<div class="panel-footer"></div>
				</div>
			</div>
		</div>
	</div>
	<script>
		var elt = document.getElementById('statusCompra');
		elt.value = window.compra.statusCompra;
	</script>
	<script src="/les-web/resources/js/jquery.js"></script>
	<script src="/les-web/resources/js/jquery.mask.js"></script>
	<script src="/les-web/resources/js/bootstrap.js"></script>
	<script src="/les-web/resources/js/angular.js"></script>
	<script src="/les-web/resources/js/script.js"></script>
</body>
</html>