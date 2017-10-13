<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="core.dfs.aplicacao.Resultado"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" ng-app="app">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>Insert title here</title>
    </head>
    <body>
    	<%
    		Resultado resultado = (Resultado) session.getAttribute("resultado");
    		if(resultado != null && resultado.getMsg() != null)
    			out.print(resultado.getMsg());
    	%>
        <t:template>
            <jsp:body>
                <form action="/les-web/cliente/ConsultarCliente" method="post"
                      novalidate ng-controller="FormClienteController as ctrl">
                    <div class="row">
                        <div class="col-lg-12">
                            <ul class="nav nav-tabs">
                                <li>
                                    <a href="javascript:void(0)"
                                       ng-click="ctrl.setTab('tab1')">Cliente</a>
                                </li>
                                <li>
                                    <a href="javascript:void(0)"
                                       ng-click="ctrl.setTab('tab2')">Telefones</a>
                                </li>
                            </ul>
                        </div>
                        <div class="col-lg-6"
                             ng-class="{'visible' : ctrl.tab == 'tab1','tab-view':true}">
                            <div class="form-group">
                                <label>Nome:</label>
                                <input type="text" name="txtNome"
                                       required="true" class="form-control"
                                       value="${empty cliente ? '' : cliente.getPessoa().getNome() }" />
                            </div>
                            <div class="form-group">
                                <label>CPF:</label>
                                <input type="text" name="txtCpf"
                                       required="true" class="form-control" data-mask="999.999.999-99"
                                       value="${empty pessoa ? '' : pessoa.getCpf()}" />
                            </div>
                            <div class="form-group">
                                <label>E-Mail:</label>
                                <input type="text" name="txtEmail"
                                       required="true" class="form-control"
                                       value="${empty cliente? '': cliente.getEmail() }" />
                            </div>
                            <div class="form-group">
                                <label>Senha:</label>
                                <input type="password" name="txtSenha"
                                       required="true" class="form-control"
                                       value="${empty cliente ? '': cliente.getSenha() }" />
                            </div>
                            <div class="form-group">
                                <label>Data de nascimento</label>
                                <input type="text" name="txtNascimento"
                                       data-mask="99/99/9999" class="form-control" />
                            </div>
                            <div class="form-group">
                                <div class="checkbox">
                                    <label>
                                        <input type="radio" name="radSexo" value="M" /> MASCULINO
                                    </label>
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input type="radio" name="radSexo" value="F" /> FEMININO
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-6"
                             ng-class="{'visible' : ctrl.tab == 'tab2','tab-view':true}">
                            <div class="numeros"
                                 ng-repeat="(key, value) in ctrl.telefones">
                                <div class="form-group">
                                    <label>Telefone</label>
                                    <input type="text"
                                           data-mask="(99)-9999-9999" class="form-control input-phone"
                                           name="telefone" />
                                </div>
                                <div class="form-group">
                                    <select name="tipo" class="form-control">
                                        <option value="1">Residencial</option>
                                        <option value="2">Celular</opiton>
                                            <option value="3">Comercial</option>

                                    </select>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-6">

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <input type="submit" name="operacao" class"btn
                               btn-primary" value="CONSULTAR" />
                </div>
            </form>
        </jsp:body>
    </t:template>
</body>
</html>