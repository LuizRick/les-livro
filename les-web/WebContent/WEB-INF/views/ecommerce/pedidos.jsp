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
<title>Insert title here</title>
</head>
<body>
	<t:template>
		<jsp:body>
	    <div class="row">
        <div class="col-lg-8">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4>Meus Pedidos</h4>
                </div>
                <div class="panel-body">
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
                                <tr>
                                    <td>${pedido.id }</td>
                                    <td>${pedido.statusCompra }</td>
                                    <td>${pedido.total}</td>
                                    <td>${pedido.frete.enderecoEntrega}</td>
                                    <td>
                                        <a href="/les-web/pedidos/reqtroca"
                                        class="btn btn-primary">Trocar itens</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="panel-footer">
    
                </div>
            </div>
        </div>
    </div>
    <script>
    var pedidos = ${ju:toJson(pedidos) };
    setInterval(function () {
        for (var x in pedidos) {
            var pedido = pedidos[x];
            if (pedido.statusCompra == "Processamento") {
                pedido.statusCompra = "Aprovado";
            	WebRequestAsync('/les-web/pedidos/setstatus',{"compra":JSON.stringify(pedido),"operacao":"ALTERAR"}).then(function(){
						location.reload();
                	});
            }
        }
    }, 5000);
	</script>
        </jsp:body>
	</t:template>
</body>
</html>