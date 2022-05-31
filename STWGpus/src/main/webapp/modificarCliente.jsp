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
    Context ctx = new InitialContext();
    dao = (ClienteDAO)ctx.lookup("java:module/ClienteDAO");
//    dao = (ClienteDAO)ctx.lookup("java:global/BD03_oneToMany/ClienteDAO");
    
    String txtId = request.getParameter("id");
    Long id = null;
    Cliente cliente = null;
    if (txtId!=null){
        id = Long.valueOf(txtId);
        cliente = dao.find(id);
    }
    if ((txtId==null)||(cliente==null)){
%>
        <jsp:forward page="menuClientes.jsp"/>     
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
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    </head>
    <body>
        <%@include file="WEB-INF/jspf/cabecera.jspf" %>
        >>> <a href="<%=response.encodeRedirectURL("index.jsp")%>">Inicio</a> >>> <b>Clientes</b>
        <hr>
        <br>

        
        <legend>Modificar Cliente: <b><%=cliente.getNombre()%> <%=cliente.getAp1()%></b></legend>

        <form method="POST" action="<%=response.encodeURL("modificarCliente?id="+cliente.getId())%>">
            <input type="hidden" name="id" value="<%=cliente.getId()%>">
            <table>
                <tr>
                    <td>Nombre:</td>
                    <td><input name="nombre" value="<%=cliente.getNombre()%>"></td>
                </tr>
                <tr>
                    <td>Ap1:</td>
                    <td><input name="ap1" value="<%=cliente.getAp1()%>"></td>
                </tr>
                <tr>
                    <td>Saldo :</td>
                    <td><input name="saldo" value="<%=cliente.getSaldo()%>"></td>
                </tr>
                <tr>
                    <td colspan="2" align="right"><input type="submit" value="Modificar"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
