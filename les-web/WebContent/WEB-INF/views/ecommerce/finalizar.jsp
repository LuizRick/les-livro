<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="ju" uri="/WEB-INF/tld/json.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:c="http://java.sun.com/jsp/jstl/core" ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
</head>
<body>
	<t:template>
		<jsp:body>
		<c:if test="${!empty resultado.msg}">
	    	<div class="alert alert-info">
	    		Livro: <strong>${resultado.entidades[0].titulo}</strong> ${resultado.msg}
	    	</div>
	    </c:if>
	    	<div class="row" ng-controller="FinalizacaoCompraController as Compra">
            <div class="col-lg-10 col-lg-offset-1">
                <form action="setcompra" method="post" id="frmSetCompra">
                    <div class="panel panel-success">
                        <div class="panel-heading">
                            <h5>Finalizando Compra...</h5>
                        </div>
                        <div class="panel-body">
                        <fieldset>
                                <legend>Endereço de Entrega</legend>
                                <c:forEach var="endereco" items="${cliente.endereco }">
                                    <div class="row">
                                        <div class="col-lg-3">
                                            <div class="radio">
                                                <label>
                                                    <input type="radio" name="frete.endereco.id" value="${ endereco.id}" class="input-frete"
                                                    	data-json='${ju:toJson(endereco) }' />
                                                     ${endereco.nome }</label>
                                            </div>
                                        </div>
                                        <div class="col-lg-8">
                                            <span>
                                                Logradouro ${endereco.logradouro}
                                                <br/> Rua: ${endereco.bairro }
                                                <br/> Cidade: ${endereco.cidade.nome }
                                                <br/> Estado: ${endereco.cidade.estado.nome }
                                                <br/> Cep: ${endereco.cep }
                                                <br/>
                                            </span>
                                        </div>
                                    </div>
                                </c:forEach>
                                <a href="adicionarEndereco" class="btn btn-success">Adicionar novo endereco</a>
                            </fieldset>
                            <fieldset>
                                <legend>Cartão</legend>
                                <c:forEach var="cartao" items="${cliente.cartao }" varStatus="status">
                                    <div class="row">
                                        <div class="col-lg-3">
                                            <div class="checkbox">
                                                <label>
                                                    <input type="checkbox" value="${cartao.id }" name="formasPagamento.id" class="cardcheck"
                                                    	data-index="${status.index}"/> ${cartao.titular }
                                                </label>
                                            </div>
                                        </div>
                                        <div class="col-lg-3">
                                            Bandeira: ${cartao.bandeira }
                                        </div>
                                        <div class="col-lg-6">
                                            Numero: <span class="numcard">${cartao.numero}</span>
                                            <div class="form-group">
                                                <label>valor</label>
                                                <input type="text" name="formasPagamento.valor" class="form-control" disabled="disabled" value="0.0"
                                                	data-cardindex="${status.index }"/>
                                            </div>
                                        </div>
                                        <hr/>
                                    </div>
                                </c:forEach>
                                <a href="adicionarCartao" class="btn btn-success">Adicionar novo cartão</a>
                            </fieldset>
                            <fieldset id="lista_cupons">
                                <legend>Cupom compra</legend>
                                <div class="form-group">
                                    <label>Codigo Cupom:</label>
                                    <input type="text" id="codigoCupom" class="form-control cupomcompra" />
                                </div>
                                <button id="btnAddCupom" class="btn btn-success" type="button">Aplicar</button>
                            </fieldset>
                        </div>
                        <div class="panel-footer">
                        	<div class="form-group">
                        		<label>Total:</label>
                        		<input type="text" id="total" ng-model="Compra.total" ng-value="Compra.setProp('total',${total})" class="form-control" readonly="readonly"
                        		name="total"/>
                        	</div>
                        	<div class="form-group">
                        		<label>Total à Pagar:</label>
                        		<input type="text" id="totalPago" ng-model="Compra.totalPago" ng-value="Compra.setProp('totalPago',${total})" class="form-control" readonly="readonly"/>
                        	</div>
                        	<div class="form-group">
                        		<label>Frete</label>
                        		<input type="text" id="txtFrete" ng-model="Compra.frete" class="form-control" readonly="readonly" 
                        			name="totalFrete" placeholder="Selecione o endereco"/>
                        	</div>
                        	<div class="form-group">
                        		<label>Descontos:</label>
                        		<input type="text" id="descontos" value="0" class="form-control"  readonly="readonly"
                        		name="totalDesc"/>
                        	</div>
                        	<fieldset>
                        		<lenged>Detalhamento - Cupons</lenged>
                        		<ul>
                        			<li ng-repeat="cupom in Compra.cupons">
                        				Cupom : <span class='text-upper'> {{cupom.nome}} </span> / <b>Valor - R$ {{cupom.valor}}</b>
                        			</li>
                        		</ul>
                        	</fieldset>
                        	<input type="hidden" name="cartoesJSON" id="cartoesJSON"/>
                        	<input type="hidden" name="cuponsJSON" id="cuponsJSON"/>
                        	<input type="hidden" name="produtosJSON" id="produtosJSON" value='${produtos}'/>
                        	<input type="hidden" name="enderecoJSON" id="enderecoJSON" />
                            <input type="submit" value="SALVAR" name="operacao" class="btn btn-success" />
                        </div>
                    </div>
                </form>
            </div>
        </div>
	    </jsp:body>
	</t:template>
</body>
</html>