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
	window.compra = ${ju:toJson(compra)};
</script>
	<t:template>
		<jsp:body>
			${resultado.msg }
			<div class="row">
				<div class="col-lg-12">
					<div class="page-header">
						<h4>Cupons de compra <strong>${compra.id}</strong></h4>
					</div>
				</div>
				<div class="col-lg-7">
					
					<c:if test="${!empty compra}">
						
						<div class="panel panel-default">
							<div class="panel-heading">
								
							</div>
							<div class="panel-body">
								
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