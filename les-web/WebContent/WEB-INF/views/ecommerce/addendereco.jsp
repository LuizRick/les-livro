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
		<div class="row">
			<div class="col-lg-4">
				<c:if test="${!empty result && !result.msg.equals('')}">
            		<div class="alert alert-info">
            			${result.msg }
            		</div>
            	</c:if>
            	<form action="adicionarEndereco" method="post">
                    <div class="form-group">
                        <label>Logradouro</label>
                        <input type="text" name="logradouro" value="${endereco.logradouro}" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Bairro</label>
                        <input type="text" name="bairro" value="${endereco.bairro}" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Cep</label>
                        <input type="text" name="cep" value="${endereco.cep}" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Numero</label>
                        <input type="text" name="numero" value="${endereco.numero}" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Complemento</label>
                        <input type="text" name="complemento" value="${endereco.complemento}" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Nome</label>
                        <input type="text" name="nome" value="${endereco.nome}" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Tipo Residencia</label>
                        <input type="text" name="tipoResidencia" value="${endereco.logradouro}" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Tipo de Logradouro</label>
                        <input type="text" name="tipoLogradouro" value="${endereco.logradouro}" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Cidade</label>
                        <input type="text" name="cidade.nome" value="${endereco.cidade.nome}" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Estado</label>
                        <input type="text" name="cidade.estado.nome" value="${endereco.cidade.estado.nome}" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Pais</label>
                        <input type="text" name="cidade.estado.pais.nome" value="${endereco.cidade.estado.pais.nome}" class="form-control" />
                    </div>
                    <div class="form-group">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" name="preferencial" value="${endereco.preferencial}" /> Preferencial
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                    	<div class="checkbox">
                    		<label>
                    			<input type="checkbox" name="addPerfil" /> Adicionar ao perfil
                    		</label>
                    	</div>
                    </div>
                    <input type="hidden" name="id_cliente" value="${cliente.id}"/>
                    <input type="submit" name="operacao" value="SALVAR" class="btn btn-success"/>
                </form>
			</div>
		</div>
        </jsp:body>
	</t:template>
</body>
</html>