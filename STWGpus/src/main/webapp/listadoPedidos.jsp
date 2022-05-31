<%-- 
    Document   : index
    Created on : 20-03-2020, 11:29:48
    Author     : fserna

--%>


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
    PedidoDAO   pedidosDAO = null;
    
    Context ctx = new InitialContext();
    pedidosDAO = (PedidoDAO)ctx.lookup("java:module/PedidoDAO");
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
        >>> <a href="<%=response.encodeURL("index.jsp")%>">Inicio</a> >>> <b>Listado de Pedidos</b>
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
                for (Pedido p: pedidosDAO.findAll()) { 
                    total += p.getPrecio();
            %>
            <tr>
                <td><%=p.getId()%></td>
                <td><%=Time.getDDMMYYYY(p.getFecha())%>@<%=Time.getHHMMSS(p.getFecha())%></td>
                <td><%=p.getCliente().getNombre()%> <%=p.getCliente().getAp1()%></td>
                <td align="right"><%=p.getNumUnidades()%></td>
                <td><%=p.getProducto().getNombre()%></td>
                <td align="right"><%=p.getProducto().getPrecioUnitario()%> €</td>
                <td align="right"><%=Util.getNumberFormatted(p.getPrecio(), "#,###,##0.00")%> €</td>
            </tr>
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
