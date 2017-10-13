<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="dominio.EntidadeDominio"%>
<%@page import="java.util.List"%>
<%@page import="core.dfs.aplicacao.Resultado"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:c="http://java.sun.com/jsp/jstl/core">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>Listagem de Clientes</title>
    </head>
    <body>
        <%
            Resultado resultado = (Resultado) session.getAttribute("resultado");
            if(resultado != null){
            	List<EntidadeDominio> lstEntidades = resultado.getEntidades();
                request.setAttribute("lstClientes", lstEntidades);
            }
        %>
        <t:template>
            <jsp:body>
                <div class="row">
                    <div class="col-lg-12">
                        <table class="table table-condensed">
                            <thead>
                                <tr>
                                    <th>Nome</th>
                                    <th>CPF</th>
                                    <th>Nascimento</th>
                                    <th>email</th>
                                    <th>Genero</th>
                                    <th>Visualizar</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="cliente" items="${lstClientes}">
                                    <tr>
                                        <td><c:out value="${cliente.getPessoa().getNome()}"/></td>
                                        <td>${cliente.getPessoa().getCpf() }</td>
                                        <td>${cliente.getPessoa().getNascimento().toLocaleString()}</td>
                                        <td>${cliente.getEmail() }</td>
                                        <td>${cliente.getPessoa().getGenero()}</td>
                                        <td>
                                        	<a href="/les-web/cliente/ConsultarCliente?id=${cliente.getId()}&operacao=VISUALIZAR">Visualizar</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </jsp:body>
        </t:template>
    </body>
</html>