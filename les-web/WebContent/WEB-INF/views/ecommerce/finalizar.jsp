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
<link rel="stylesheet" type="text/css" href="../../../resources/css/bootstrap.css"/>
<title>Insert title here</title>
</head>
<body>
	<t:template>
		<jsp:body>
	    	<div class="row">
	    		<div class="col-lg-10 col-lg-offset-1">
	    			<div class="panel panel-success">
	    				<div class="panel-heading">
	    					<h5>Finalizando Compra...</h5>
	    				</div>
	    				<div class="panel-body">
	    					<form action="setcompra" method="post" enctype="multipart/form-data">
	    						<fieldset>
	    						<legend>Cartão</legend>
	    						<c:forEach var="cartao" items="${cliente.cartao }">
	    							<div class="row">
	    								<div class="col-lg-3">
	    									<div class="checkbox">
	    										<label>
	    											<input type="checkbox" value="${cartao.id }" name="venda.cartao.id"/> ${cartao.titular }
	    										</label>
	    									</div>
	    								</div>
	    								<div class="col-lg-3">
	    									Bandeira: ${cartao.bandeira }
	    								</div>
	    								<div class="col-lg-6">
	    									Numero: ${cartao.numero}
	    									<div class="form-group">
	    										<label>valor</label>
	    										<input type="text" name="venda.cartao.valor" class="form-control"/>
	    									</div>
	    								</div>
	    								<hr/>
	    							</div>
	    						</c:forEach>
	    						<a href="adicionarCartao" class="btn btn-success">Adicionar novo cartão</a>
	    						</fieldset>
	    						<fieldset>
	    						<legend>Endereço de Entrega</legend>
	    						<c:forEach var="endereco" items="${cliente.endereco }">
	    							<div class="row">
	    								<div class="col-lg-3">
	    									<div class="radio">
	    										<label><input type="radio" name="venda.endereco.id" value="${ endereco.id}"/> ${endereco.nome }</label>
	    									</div>
	    								</div>
	    								<div class="col-lg-8">
	    									<span>
	    										Logradouro ${endereco.logradouro}<br/>
	    										Rua: ${endereco.bairro }<br/>
	    										Cidade: ${endereco.cidade.nome }<br/>
	    										Estado: ${endereco.cidade.estado.nome }<br/>
	    									</span>
	    								</div>
	    							</div>
	    						</c:forEach>
	    						<a href="adicionarEndereco" class="btn btn-success">Adicionar novo endereco</a>
	    						</fieldset>
	    						 <fieldset id="lista_cupons">
	    						 	<legend>Cupom compra</legend>
	    						 	<div class="form-group">
	    						 		<label>Codigo Cupom:</label>
	    						 		<input type="text" id="codigoCupom" name="cupom.codigo" class="form-control"/>
	    						 	</div>
	    						 	<button id="btnAddCupom" class="btn btn-success" type="button">Adicionar</button>
	    						 </fieldset>
	    					</form>
	    				</div>
	    				<div class="panel-footer">
	    					<input type="submit" value="SALVAR" name="operacao" class="btn btn-success"/>
	    				</div>
	    			</div>
	    		</div>
	    	</div>
	    </jsp:body>
	</t:template>
</body>
</html>