<%-- 
    Document   : index
    Created on : 13-abr-2018, 11:29:48
    Author     : uzffs
--%>


<%@page import="demo.util.Util"%>
<%@page import="demo.bd.Cliente"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.Context"%>
<%@page import="demo.bd.ClienteDAO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    ClienteDAO dao = null;
    Long t0 = System.nanoTime();
    Context ctx = new InitialContext();
    dao = (ClienteDAO)ctx.lookup("java:module/ClienteDAO");
//    dao = (ClienteDAO)ctx.lookup("java:global/BD03_oneToMany/ClienteDAO");
    
    List<Cliente> cc= dao.findAll();
    Long t1 = System.nanoTime();
    
System.out.println("ns: "+(t1-t0));
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
        >>> <a href="<%=response.encodeRedirectURL("index.jsp")%>">Inicio</a> >>> <b>Clientes</b>
        <hr>
        <br>
        <table>
            <tr>
                <td valign="top">           
                    <fieldset>
                        <legend>Añadir Nuevo Cliente</legend>
        
                        <table>
                        <form method="POST" action="addCliente">
                            <tr>
                                <td>Nombre:</td>
                                <td><input name="nombre"></td>
                            </tr>
                            <tr>
                                <td>Ap1:</td>
                                <td><input name="ap1"></td>
                            </tr>
                            <tr>
                                <td>Saldo :</td>
                                <td><input name="saldo"></td>            </tr>
                            <tr>
                                <td colspan="2"><input type="submit" value="Añadir"></td>
                            </tr>
                        </form>
                        </table>
                    </fieldset>
                </td>

                <td width="150px;"></td>
                
                <td valign="top">
                    <legend>Clientes registrados:</legend>
                    <table border="1">
                        <tr style="background-color:blue; color:white">
                            <td>Id</td>
                            <td>Nombre</td>
                            <td>Ap1</td>
                            <td>Saldo</td>
                            <td></td>
                            <td></td>
                            <td></td>
                            <td></td>
                        </tr>
                        <% for (Cliente c: cc) { %>
                        <tr>
                            <td><%=c.getId()%></td>
                            <td><%=c.getNombre()%></td>
                            <td><%=c.getAp1()%></td>
                            <td align="right"><%=Util.getNumberFormatted(c.getSaldo(), "#,###,##0.00")%> €</td>
                            <td><a href="modificarCliente.jsp?id=<%=c.getId()%>">Modificar</td>
                            <td><a href="eliminarCliente?id=<%=c.getId()%>">Eliminar</td>
                            <td width="30px;"></td>
                            <td><a href="menuPedidosCliente.jsp?id=<%=c.getId()%>"><button>Gestión de Pedidos</button></td>
                        </tr>
                        <% } %>
                    </table>
                </td>
            </tr>
        </table>
        <br><br><br>
        <%=Util.getNumberFormatted((t1-t0), "###,###,##0")%> ns
    </body>
</html>
