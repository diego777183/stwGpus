<%-- 
    Document   : index
    Created on : 15-may-2022, 11:29:48
    Author     : Fernando Revilla
--%>



<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.Context"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%

%>
<!DOCTYPE html>
<html>
    <head>
        <style>
            body {
                font-family: "Lato", sans-serif;
                font-size: 12px;
            }
        </style>


        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="websocket.js"></script>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <title>STWGpus</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    </head>
    <body>
        <%@include file="WEB-INF/jspf/cabecera.jspf" %>
        <div style="margin-left: 25px;">
            <h3>Seleccione que quiere visualizar</h3>

            <br>
            <script type="text/javascript" src="websocket.js"></script>

            <select name="color" id="tipoGrafica" onchange="">
                <option value="nada"> </option>    
                <option value="luz">Gráfico de Precios de la Luz</option>
                <option value="eth">Gráfico de Precios de Ethereum</option>
            </select>
            <input type="date" id="current_date" name="current_date" value="" min="2022-06-6">
            <button onclick="prueba();">Buscar</button>
        </div>

        <br><br><br><br>
        <div style="margin-left: 1600px;margin-top: -150px">
            <form method="POST" action="<%=response.encodeURL("login")%>">
                <table>

                    <tr>
                        <td>Login:</td>
                        <td><input name="login"></td>
                    </tr>
                    <tr>
                        <td>Contraseña:</td>
                        <td><input type="password" name="pwd"></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="submit" value="Login"></td>
                    </tr>

                </table>

            </form>
            <a href="<%=response.encodeURL("addUsuario.jsp")%>">Nuevo usuario</a>

            <br>

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


    </body>
</html>
