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
<title>Visualizar de troca</title>
</head>
<body>
<script>
	
</script>
	<t:template>
		<jsp:body>
			${resultado.msg }
			<div class="row">
				<div class="col-lg-12">
					<div class="page-header">
						<h4>Cupons de compra</h4>
					</div>
				</div>
				<div class="col-lg-7">
					
					<c:if test="${!empty resultado}">
						
						<div class="panel panel-default">
							<div class="panel-heading">
								
							</div>
							<div class="panel-body">
								<table class="table table-hover">
									<thead>
										<tr>
											<th>Codigo</th>
											<th>Valor</th>
											<th>Data Criação</th>
											<th>Status</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="cupom" items="${resultado.entidades }">
											<tr>
												<td>${cupom.codigoCupom }</td>
												<td>${cupom.valor }</td>
												<td>${cupom.dtCadastro }</td>
												<td>
													<c:choose>
														<c:when test="${cupom.valido }">
															Cupom Não Utilizado
														</c:when>
														<c:when test="${!cupom.valido }">
															Já utilizado
														</c:when>
													</c:choose>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="panel-footer">
								
							</div>
						</div>
					</c:if>
				</div>
			</div>
        </jsp:body>
	</t:template>
</body>
</html>