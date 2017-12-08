<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:c="http://java.sun.com/jsp/jstl/core" ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</head>
<body>
	<t:template>
		<jsp:body>
		<div class="row">
            <div class="col-lg-4">
            	<c:if test="${!empty result && !result.msg.equals('')}">
            		<div class="alert alert-info">
            			${result.msg }<br />
            			<a href="http://localhost:8080/les-web/public/finalizar" class="btn btn-success">
            				Finalizar Compra
            			</a>
            		</div>
            	</c:if>
                <form action="adicionarCartao" method="post">
                    <div class="form-group">
                        <label>Titular</label>
                        <input type="text" name="titular" class="form-control" 
                        value="${cartao.titular }" />
                    </div>
                    <div class="form-group">
                        <label>Numero</label>
                        <input type="text" name="numero" class="form-control" 
                        	data-mask="9999-9999-9999-9999" value="${cartao.numero }" />
                    </div>
                    <div class="form-group">
                        <label>Baindeira</label>
                        <input type="text" name="bandeira" class="form-control" 
                        	value="${cartao.bandeira }" />
                    </div>
                    <div class="form-group">
                        <label>Codigo de segurança</label>
                        <input type="text" name="codigo" class="form-control" maxlength="3" 
                        value="${cartao.codigo }"/>
                    </div>
                    <div class="form-group">
                        <label>Validade</label>
                        <input type="text" name="validade" class="form-control" data-mask="99/99/9999" required
                        	value="${cartao.validade }"/>
                    </div>
                    <div class="form-group">
                    	<div class="checkbox">
                    		<label>
                    			<input type="checkbox" name="addPerfil" value="${cartao.addPerfil}"/> Adicionar ao Perfil
                    		</label>
                    	</div>
                    </div>
                    <input type="hidden" name="id_cliente" value="${cliente.id }"/>
                    <input type="hidden" name="id" value="" />
                    <input type="submit" name="operacao" value="SALVAR" class="btn btn-success"/>
                    <input type="reset"  value="LIMPAR" class="btn btn-danger"/>
                </form>
            </div>
        </div>
        </jsp:body>
	</t:template>
</body>
</html>