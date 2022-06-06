<%-- 
    Document   : index
    Created on : 20-03-2020, 11:29:48
    Author     : fserna

--%>


<%@page import="demo.bd.PrecioLuz"%>
<%@page import="demo.bd.PrecioLuzDAO"%>
<%@page import="demo.util.Util"%>
<%@page import="demo.util.Time"%>
<%@page import="demo.bd.Pedido"%>
<%@page import="demo.bd.PedidoDAO"%>
<%@page import="demo.bd.Producto"%>
<%@page import="demo.bd.ProductoDAO"%>
<%@page import="demo.bd.Cliente"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.Context"%>
<%@page import="demo.bd.ClienteDAO"%>
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
                <td>Fecha</td>
                <td>Precio</td>
            </tr>
            <%  Double total = 0.0;
                for (PrecioLuz p: preciosDAO.findAll()) { 
                    total += p.getPrecio();
            %>
            <tr>
                <td><%=Time.getDDMMYYYY(p.getFecha())%>@<%=Time.getHHMMSS(p.getFecha())%></td>
                <td align="right"><%=Util.getNumberFormatted(p.getPrecio(), "#,###,##0.00")%> €</td>
            </tr>
            <% } %>
            <tr style="background-color:blue; color:white">
                <td></td>
                <td align="right"><b><%=Util.getNumberFormatted(total, "#,###,##0.00")%> €</b></td>
            </tr>
        </table>
    </body>
</html>
