<%-- 
    Document   : index
    Created on : 13-abr-2018, 11:29:48
    Author     : fserna

--%>


<%@page import="demo.bd.Producto"%>
<%@page import="demo.bd.ProductoDAO"%>
<%@page import="demo.util.Util"%>
<%@page import="demo.util.Time"%>
<%@page import="demo.bd.Pedido"%>
<%@page import="demo.bd.Cliente"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.Context"%>
<%@page import="demo.bd.ClienteDAO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    ClienteDAO clienteDAO = null;
    ProductoDAO productoDAO = null;
    Context ctx = new InitialContext();
    clienteDAO = (ClienteDAO)ctx.lookup("java:module/ClienteDAO");
//    clienteDAO = (ClienteDAO)ctx.lookup("java:global/BD03_oneToMany/ClienteDAO");
    productoDAO = (ProductoDAO)ctx.lookup("java:module/ProductoDAO");
//    productoDAO = (ProductoDAO)ctx.lookup("java:global/BD03_oneToMany/ProductoDAO");
    
    Long idCliente = Long.valueOf(request.getParameter("id"));
    Cliente cliente = clienteDAO.find(idCliente);

    List<Producto> productos = productoDAO.findAll();
    
    String msg = (String) session.getAttribute("msg");
    if (msg==null){
        msg = "";
    }else{
        session.setAttribute("msg", null);
    }
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
        >>> <a href="<%=response.encodeRedirectURL("index.jsp")%>">Inicio</a> >>> <a href="<%=response.encodeRedirectURL("menuClientes.jsp")%>">Clientes</a> >>> <b>Gestión de Pedidos</b>
        <hr>
        <div style="background-color: red; color:white"><%=msg%></div>
        <br>
       
        <table>
            <tr>
                <td valign="top">
                    <fieldset>
                        <legend>Añadir nuevo Pedido</legend>

                        <form method="POST" action="<%=response.encodeRedirectURL("addPedido")%>">
                            <input type="hidden" name="idCliente" value="<%=cliente.getId()%>">
                            <table>
                                <tr>
                                    <td>Producto:</td>
                                    <td>
                                        <select id="idProducto" name="idProducto">
                                            <option value="0" SELECTED>Selecciona un Producto...</option>
                                            <% for (Producto p: productos){ %>
                                            <option value="<%=p.getId()%>"><%=p.getNombre()%></option>
                                            <% } %>
                                        </select>
                                    </td>
                                </tr>

                                <tr>
                                    <td>Unidades:</td>
                                    <td><input name="numUnidades" size="5"></td>            
                                </tr>
                                <tr>
                                    <td colspan="6" align="right"><input type="submit" value="Añadir Pedido"></td>
                                </tr>

                            </table>
                        </form>
                    </fieldset>
                </td>

                <td width="150px;"></td>                
                
                <td valign="top">
                     <h3>Pedidos de: <u><%=cliente.getNombre()%> <%=cliente.getAp1()%></u></h3>
                    <table border="1">
                        <tr style="background-color:blue; color:white">
                            <td>Id</td>
                            <td>Fecha</td>
                            <td>Núm.Unidades</td>
                            <td>Producto</td>
                            <td>PVP Unidad</td>
                            <td>PVP Total</td>
                            <td></td>
                        </tr>
                        <%  Double total = 0.0;
                            for (Pedido p: cliente.getPedidos()) { 
                                total += p.getPrecio();
                        %>
                        <tr>
                            <td><%=p.getId()%></td>
                            <td><%=Time.getDDMMYYYY(p.getFecha())%>@<%=Time.getHHMMSS(p.getFecha())%></td>
                            <td align="right"><%=p.getNumUnidades()%></td>
                            <td><%=p.getProducto().getNombre()%></td>
                            <td align="right"><%=p.getProducto().getPrecioUnitario()%> €</td>
                            <td align="right"><%=Util.getNumberFormatted(p.getPrecio(), "#,###,##0.00")%> €</td>
                            <td><a href="eliminarPedido?idCliente=<%=cliente.getId()%>&idPedido=<%=p.getId()%>">Eliminar</a></td>
                        </tr>
                        <% } %>
                        <tr style="background-color:blue; color:white">
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td align="right"><b><%=Util.getNumberFormatted(total, "#,###,##0.00")%> €</b></td>
                        </tr>
                        <tr>                            
                            <td colspan="6" align="right"> Saldo restante: <b><%=Util.getNumberFormatted(cliente.getSaldo(), "#,###,##0.00")%> €</b></td>
                        </tr>

                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>
