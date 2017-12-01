<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="ju" uri="/WEB-INF/tld/json.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:c="http://java.sun.com/jsp/jstl/core" ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
<link rel="stylesheet"
	href="/les-web/resources/css/bootstrap-theme.min.css" />
<link rel="stylesheet" href="/les-web/resources/css/bootstrap.min.css" />
<link rel="stylesheet" href="/les-web/resources/css/style.css" />
</head>
<body>
	<div class="container-fluid"
		ng-controller="PainelVendasController as Painel">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel panel-heading">
						<h4>Painel Vendas</h4>
						<a href="http://localhost:8080/les-web/index.xhtml">Indice</a>
					</div>
					<div class="panel-body">
						<fieldset>
							<legend>Gráfico de vendas</legend>
							<form action="getGraph" method="post">
								<div class="form-group">
									<select name="tipo" class="form-control">
										<option value="1">Categoria</option>
										<option value="2">Bandeira cartão</option>
									</select>
								</div>
								<div class="form-group">
									<label style="float: left">Data</label>
									<ul style="width: 300px; float: left;">
										<li ng-repeat="(key,value) in Painel.data.meses">
											<div class="checkbox">
												<lable> <input type="checkbox" name="mes"
													value="{{key + 1}}" />{{value}} </lable>
											</div>
										</li>
									</ul>
									<div class="div_categorias">
										<label>categorias</label>
										<ul class="list_categorias">
											<li ng-repeat="categoria in Painel.categorias">
												<div class="checkbox">
													<label> <input type="checkbox" name="categoria"/> {{categoria.nome}}
													</label>
												</div>
											</li>
										</ul>
									</div>
								</div>
							</form>
						</fieldset>
						<table class="table table-hover">
							<thead>
								<th>Id</th>
								<th>Status</th>
								<th>total</th>
								<th>Endereço</th>
								<th>Ações</th>
							</thead>
							<tbody>
								<c:forEach var="pedido" items="${pedidos}">
									<form action="visualizarPedidoCliente" method="post">
										<tr>
											<td>${pedido.id }</td>
											<td>${pedido.statusCompra }</td>
											<td>${pedido.total}</td>
											<td>${pedido.frete.enderecoEntrega}</td>
											<td><input type="hidden" name="compra"
												value='${ju:toJson(pedido)}' /> <input type="submit"
												value="VISUALIZAR" class="btn btn-primary " /></td>
										</tr>
									</form>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="panel-footer"></div>
				</div>
			</div>
		</div>
	</div>
	<script src="/les-web/resources/js/jquery.js"></script>
	<script src="/les-web/resources/js/jquery.mask.js"></script>
	<script src="/les-web/resources/js/bootstrap.js"></script>
	<script src="/les-web/resources/js/angular.js"></script>
	<script src="/les-web/resources/js/script.js"></script>
</body>
</html>