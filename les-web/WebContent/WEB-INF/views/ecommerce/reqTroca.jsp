<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="ju" uri="/WEB-INF/tld/json.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:c="http://java.sun.com/jsp/jstl/core">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Solicitação de troca</title>
</head>
<body>
	<t:template>
		<jsp:body>
			${resultado.msg }
			<div class="row">
				<div class="col-lg-12">
					<h1>Solicitação de troca</h1>
				</div>
				<div class="col-lg-7">
					
					<c:if test="${!empty compra}">
						<script>
							window.compra = ${ju.toJson(compra)}
						</script>
						<div class="panel panel-default">
							<div class="panel-heading">
								Pedido Nº: <strong>${compra.id}</strong>
							</div>
							<div class="panel-body">
								<h4>Items para troca</h4>
								<table class="table table-hover">
									<thead>
										<tr>
											<th>Id</th>
											<th>Titulo</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="item" items="${compra.produtos.itens}">
											<tr>
												<td> ${item.id } </td>
												<td>
													${item.produto.titulo }
												</td>
												<td>
													<button type="button" class="btn btn-primary add-troca"
														data-json='${ju:toJson(item)}'>
														add 
													</button>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="panel-footer">
								<button type="button" class="btn btn-success" id="salvarItens">
									<span class="glyphicon glyphicon-pencil"></span>&nbsp;
									TROCAR ITENS ADICIONADOS
								</button>
								<button type="button" class="btn btn-success" id="trocarTudo">
									<span class="glyphicon glyphicon-list-alt"></span>&nbsp;
									TROCAR TODO O PEDIDO
								</button>
							</div>
						</div>
					</c:if>
				</div>
			</div>
        </jsp:body>
	</t:template>
</body>
</html>