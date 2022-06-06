<%-- 
    Document   : index
    Created on : 20-03-2020, 11:29:48
    Author     : fserna

--%>


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
    PrecioLuzDAO   preciosDAO = null;
    
    Context ctx = new InitialContext();
    preciosDAO = (PrecioLuzDAO)ctx.lookup("java:module/PrecioLuzDAO");
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
        >>> <a href="<%=response.encodeURL("index.jsp")%>">Inicio</a> >>> <b>Listado de Precios de la Luz</b>
        <hr>
        <br>
        <b>Histórico de Pedidos:</b>
        <table border="1">
            <tr style="background-color:blue; color:white">
                <td>Id</td>
                <td>Fecha</td>
                <td>Cliente</td>
                <td>Núm.Unidades</td>
                <td>Producto</td>
                <td>PVP Unidad</td>
                <td>PVP Total</td>
            </tr>
            <%  Double total = 0.0;
                for (PrecioLuz p: preciosDAO.findAll()) { 
                    total += p.getPrecio();
            %>
           
            <% } %>
            <tr style="background-color:blue; color:white">
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td align="right"><b><%=Util.getNumberFormatted(total, "#,###,##0.00")%> €</b></td>
            </tr>
        </table>
    </body>
</html>
