<%-- 
    Document   : index
    Created on : 13-abr-2018, 11:29:48
    Author     : uzffs
--%>


<%@page import="demo.util.Util"%>
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
    ProductoDAO dao = null;
    Context ctx = new InitialContext();
    dao = (ProductoDAO)ctx.lookup("java:module/ProductoDAO");
    
    String txtId = request.getParameter("id");
    Long id = null;
    Producto producto = null;
    if (txtId!=null){
        id = Long.valueOf(txtId);
        producto = dao.find(id);
    }
    if ((txtId==null)||(producto==null)){
%>
        <jsp:forward page="menuProductos.jsp"/>     
<%  }
%>
<!DOCTYPE html>
<style>
    body {font-family: "Lato", sans-serif; font-size: 12px;}
</style>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JPA oneToMany</title>
    </head>
    <body>
        <h1>Gestión de pedidos, productos y clientes...</h1>
        <hr>
        >>> <a href="<%=response.encodeRedirectURL("index.jsp")%>">Inicio</a> >>> <a href="<%=response.encodeRedirectURL("menuProductos.jsp")%>">Productos</a> >>> <b>Modificar Producto</b>
        <br>
        <br>
        <br>
        <br>
        <table>
            <tr>
                <td valign="top">           
                    <fieldset><legend>Modificar Producto <b><%=producto.getNombre()%></b>:</legend>
                    <table>
                        <form method="POST" action="<%=response.encodeRedirectURL("modificarProducto")%>">
                            <input type=""hidden name="id" value="<%=producto.getId()%>">
                            <tr>
                                <td>Id:</td>
                                <td><b><%=producto.getId()%></b></td>
                            </tr>
                            <tr>
                                <td>Nombre:</td>
                                <td><input name="nombre" value="<%=producto.getNombre()%>"></td>
                            </tr>
                            <tr>
                                <td>Precio Unitario:</td>
                                <td><input name="precioU" value="<%=producto.getPrecioUnitario()%>"></td>
                            </tr>
                            <tr>
                                <td colspan="2" align="right"><input type="submit" value="Modificar"></td>
                            </tr>
                        </form>
                    </table>
                    </fieldset>
                </td>
            </tr>
        </table>
    </body>
</html>
