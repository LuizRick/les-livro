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
	<script>
		window.compra = ${ju:toJson(pedido)};
	</script>
	<div class="container-fluid"
		ng-controller="ControllerPedido as pedido">
		<div class="row">
			<div class="col-lg-12">
			<form action="/les-web/trocas/trocarpedido" method="post">
				<div class="panel panel-default">
					<div class="panel panel-heading">
						<h4>Painel Trocas</h4>
						<a href="http://localhost:8080/les-web/index.xhtml">Indice</a>/
						<a href="http://localhost:8080/les-web/pedidos/listar">Listar Pedidos</a>
					</div>
					<div class="panel-body">
						<p class="text-left">
							<span>Codigo compra:</span> <b>{{pedido.compra.id}}</b>º
						</p>
						<p class="text-left">
							<span>Status:</span> <b style="text-transform: capitalize;">{{pedido.compra.statusCompra}}</b>
						</p>
						<p class="text-left">
							<span>Total: </span> <b>{{pedido.compra.total}} Reais</b>
						</p>
						<fieldset>
							<legend>Produtos</legend>
							<p class="help-block">
								Selecionar itens para retornar ao estoque
							</p>
							<table class="table table-hover">
								<thead>
									<th>ID</th>
									<th>Titulo</th>
									<th>Quantidade</th>
									<th>Valor</th>
								</thead>
								<tr ng-repeat="item in pedido.compra.produtos.itens">
									<td>
										<div class="checkbox">
											<label><input type='checkbox' name='produtos' value='{{item.id}}'/>
												{{item.id}}
											</label>
										</div>
									</td>
									<td>{{item.produto.titulo}}</td>
									<td>{{item.quantidade}}</td>
									<td><b>R$</b>{{item.produto.valor}}</td>
								</tr>
							</table>
						</fieldset>
					</div>
					<div class="panel-footer">
						
						<button type="submit" name="operacao" value="ALTERAR" class="btn btn-primary">
							<span class='glyphicon glyphicon-floppy-disk'></span> Trocar e Adicionar
						</button>
					</div>
					<input type="hidden" name="status" value="Trocado" />
					<input type="hidden" name='pedido' value='{{pedido.compra}}'/>
					</div>
					</form>
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