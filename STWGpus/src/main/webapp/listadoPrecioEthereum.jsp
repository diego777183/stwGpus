<%-- 
    Document   : index
    Created on : 20-03-2020, 11:29:48
    Author     : fserna

--%>


<%@page import="bd.PrecioEthereum"%>
<%@page import="bd.PrecioEthereumDAO"%>
<%@page import="bd.PrecioLuz"%>
<%@page import="bd.PrecioLuzDAO"%>
<%@page import="util.Util"%>
<%@page import="util.Time"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.Context"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    PrecioEthereumDAO   preciosEthereumDAO = null;
    
    Context ctx = new InitialContext();
    preciosEthereumDAO = (PrecioEthereumDAO)ctx.lookup("java:module/PrecioEthereumDAO");
%>
<!DOCTYPE html>
<style>
    body {font-family: "Lato", sans-serif; font-size: 12px;}
</style>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JPA oneToMany</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    </head>
    <body>
        <%@include file="WEB-INF/jspf/cabecera.jspf" %>
        >>> <a href="<%=response.encodeURL("index.jsp")%>">Inicio</a> >>> <b>Listado de Precios de Ethereum</b>
        <hr>
        <br>
        <b>Histórico de precios de Ethereum</b>
        <table border="1">
            <tr style="background-color:blue; color:white">
                <td>Fecha</td>
                <td>Precio</td>
            </tr>
            <%  Double total = 0.0;
                for (PrecioEthereum p: preciosEthereumDAO.findAll()) { 
                    total += p.getPrecio();
            %>
<<<<<<< HEAD
            <tr>
                <td><%=Time.getDDMMYYYY(p.getFecha())%>@<%=Time.getHHMMSS(p.getFecha())%></td>
                <td align="right"><%=Util.getNumberFormatted(p.getPrecio(), "#,###,##0.00")%> €</td>
            </tr>
=======
        
>>>>>>> pruebas
            <% } %>

        </table>
    </body>
</html>
