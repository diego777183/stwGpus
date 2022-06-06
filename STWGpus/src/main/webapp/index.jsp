<%-- 
    Document   : index
    Created on : 13-abr-2018, 11:29:48
    Author     : uzffs
--%>


<%@page import="demo.bd.Cliente"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.Context"%>
<%@page import="demo.bd.ClienteDAO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 

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
<!--
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://fonts.xz.style/serve/inter.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@exampledev/new.css@1.1.2/new.min.css"> 
-->
    </head>
    <body>
        <%@include file="WEB-INF/jspf/cabecera.jspf" %>
        
        <h3>Ejemplo de OneToMany con JPA</h3>
        
        <br>
        <a href="<%=response.encodeRedirectURL("menuProductos.jsp")%>">Productos</a> | 
        <a href="<%=response.encodeRedirectURL("menuClientes.jsp")%>">Clientes</a> | 
        <a href="<%=response.encodeRedirectURL("listadoPedidos.jsp")%>">Listado de Pedidos</a>

    </body>
</html>
