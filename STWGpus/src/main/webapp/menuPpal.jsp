<%-- 
    Document   : menuPpal
    Created on : 20-may-2022, 11:29:48
    Author     : Diego Santome, Alberto Perez y Fernando Revilla
--%>


<%@page import="bd.UsuarioFacade"%>
<%@page import="util.Time"%>
<%@page import="java.util.List"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="bd.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>

<%
    Long idUsuario = (Long) session.getAttribute("idUsuario");
    if (idUsuario == null) {
        session.setAttribute("msg", "ERROR: La sesión ha caducado.");
%>
<jsp:forward page="index.jsp"/>
<%
    }

    String msg = (String) session.getAttribute("msg");

    UsuarioFacade usuarioDB;
    InitialContext ic = new InitialContext();
    usuarioDB = (UsuarioFacade) ic.lookup("java:module/UsuarioFacade");
    Usuario usuario = (Usuario) usuarioDB.find(idUsuario);
    if (usuario == null) {
%>
<jsp:forward page="index.jsp"/>
<%
    }
%>

<style>
    body {
        font-family: "Lato", sans-serif;
        font-size: 12px;
    }
</style>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

        <title>STW GPUS</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <!--
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link rel="stylesheet" href="https://fonts.xz.style/serve/inter.css">
                <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@exampledev/new.css@1.1.2/new.min.css"> 
        -->
    </head>
    <body>
        <%@include file="WEB-INF/jspf/cabecera.jspf" %>

        <div style="background-color: red; color:white; font-size:large;">${msg}</div>
        <p style="margin-left: 25px;" >Hola <%=usuario.getNombre()%> </p>

        <div style="margin-left: 25px;">
            <h3>Seleccione que quiere visualizar</h3>

            <br>
            <script type="text/javascript" src="websocket.js"></script>

            <select name="color" id="tipoGrafica"">
                <option value="nada"> </option>    
                <option value="temp">Gráfico de temperatura de la gráfica</option>
                <option value="efi">Gráfico de la eficiencia H/w</option>
                <option value="watt">Gráfico del consumo</option>
                <option value="hash">Gráfico hashrate</option>
                <option value="tempAmbiente">Gráfico de la temperatura del ambiente</option>
            </select>

            <input type="date" id="current_date" name="current_date" value="" min="2022-06-6">
            <button onclick="prueba();">Buscar</button>


        </div>

        <div id="graficaLuz"></div>  

        <script type="text/javascript">
            google.charts.load('current', {'packages': ['corechart'], language: 'es'});

            /*google.charts.setOnLoadCallback(
             function() { // Anonymous function that calls drawChart1 and drawChart2
             initCo2GraphLive();
             initCo2GraphLog();
             });*/
            google.charts.setOnLoadCallback(initGrafica);
            //google.charts.setOnLoadCallback(initCo2GraphLog);
        </script>
       
        <%
            session.setAttribute("msg", null);
        %>
    </body>
</html>
