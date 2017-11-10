<?xml version="1.0" encoding="UTF-8" ?>
<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true"%>
<%@attribute name="footer" fragment="true"%>
<%@attribute name="scriptAppend" fragment="true" %>
<%@attribute name="scriptPrepend" fragment="true" %>
<%@attribute name="linkAppend" fragment="true" %>
<%@attribute name="linkPrepend" fragment="true" %>
<html ng-app="app">
    <head>
        <jsp:invoke fragment="linkPrepend"/>
        <link rel="stylesheet"
              href="/les-web/resources/css/bootstrap-theme.min.css" />
        <link rel="stylesheet" href="/les-web/resources/css/bootstrap.min.css" />
        <link rel="stylesheet" href="/les-web/resources/css/style.css" />
        <jsp:invoke fragment="linkAppend"/>
        <meta charset="utf-8"/>
    </head>
    <body>
        <div class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed"
                            data-toggle="collapse" data-target="#menu" aria-expanded="false">
                        <span class="sr-only">Toggle navigation</span> <span
                            class="icon-bar"></span> <span class="icon-bar"></span> <span
                            class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="/les-web/">Home</a>
                </div>
                <div class="collapse navbar-collapse" id="menu">

                    <ul class="nav navbar-nav">
						<li><a href="/les-web/cliente/FrmConsultarCliente.jsp">Consultar</a>
						<li><a href="/les-web/cliente/frmCadastrar.jsp">Cadastrar</a>
						<li><a href="/les-web/public/index">Produtos</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div id="pageheader">
            <jsp:invoke fragment="header" />
        </div>
        <div id="body" class="container-fluid">
            <jsp:doBody />
        </div>
        <div id="pagefooter">
            <jsp:invoke fragment="footer" />
        </div>
        <jsp:invoke fragment="scriptPrepend"/>
        <script src="/les-web/resources/js/jquery.js"></script>
        <script src="/les-web/resources/js/jquery.mask.js"></script>
        <script src="/les-web/resources/js/bootstrap.js"></script>
        <script src="/les-web/resources/js/angular.js"></script>
        <script src="/les-web/resources/js/script.js"></script>
        <jsp:invoke fragment="scriptAppend"/>
    </body>
</html>