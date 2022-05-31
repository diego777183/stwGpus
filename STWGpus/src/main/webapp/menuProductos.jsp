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
//    dao = (ProductoDAO)ctx.lookup("java:global/BD03_oneToMany/ProductoDAO");
    
    List<Producto> pp= dao.findAll();
    
    String msg = (String) session.getAttribute("msg");
    if (msg==null){
        msg = "";
    }else{
        session.setAttribute("msg", null);
    }
    
%>
<!DOCTYPE html>
<!--style>
    body {font-family: "Lato", sans-serif; font-size: 12px;}
</style-->
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JPA oneToMany</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    </head>
    <body>
        <%@include file="WEB-INF/jspf/cabecera.jspf" %>
        >>> <a href="<%=response.encodeRedirectURL("index.jsp")%>">Inicio</a> >>> <b>Productos</b>
        <hr>

        <div style="background-color: red; color:white"><%=msg%></div>
        <br>
        <table>
            <tr>
                <td valign="top">           
                    <fieldset><legend>Añadir Nuevo Producto:</legend>
                    <table>
                        <form method="POST" action="<%=response.encodeRedirectURL("addProducto")%>">
                            <tr>
                                <td>Nombre:</td>
                                <td><input name="nombre"></td>
                            </tr>
                            <tr>
                                <td>Precio Unitario:</td>
                                <td><input name="precioU"></td>
                            </tr>
                            <tr>
                                <td colspan="2" align="right"><input type="submit" value="Añadir"></td>
                            </tr>
                        </form>
                    </table>
                    </fieldset>
                </td>
            
                <td width="150px;"></td>
                
                
                <td valign="top">
                    <legend>Productos existentes:</legend>
                    <table border="1">
                        <tr style="background-color:blue; color:white">
                            <td>Id</td>
                            <td>Nombre</td>
                            <td>Precio Unitario</td>
                            <td></td>
                            <td></td>
                        </tr>
                    <% for (Producto p: pp) { %>
                    <tr>
                        <td><%=p.getId()%></td>
                        <td><%=p.getNombre()%></td>
                        <td align="right"><%=Util.getNumberFormatted(p.getPrecioUnitario(), "#,###,##0.00")%> €</td>
                        <td><a href="modificarProducto.jsp?id=<%=p.getId()%>">Modificar</td>
                        <td><a href="eliminarProducto?id=<%=p.getId()%>">Eliminar</td>
                    </tr>
                    <% } %>
                    </table>
                    IMPORTANTE: solo pueden eliminarse los productos que no figuren en ningún pedido.
                </td>
            </tr>
        </table>
    </body>
</html>
