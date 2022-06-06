<%-- 
    Document   : index
    Created on : 13-abr-2018, 11:29:48
    Author     : uzffs
--%>


<%@page import="bd.UsuarioFacade"%>
<%@page import="util.Time"%>
<%@page import="java.util.List"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="bd.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>

<%
   Long idUsuario = (Long)session.getAttribute("idUsuario");
   if (idUsuario==null){
        session.setAttribute("msg", "ERROR: La sesión ha caducado.");
%>
        <jsp:forward page="index.jsp"/>
<%
    }

    String msg = (String)session.getAttribute("msg");
  
    UsuarioFacade usuarioDB;
    InitialContext ic = new InitialContext();
    usuarioDB = (UsuarioFacade) ic.lookup("java:module/UsuarioFacade");
    Usuario usuario = (Usuario)usuarioDB.find(idUsuario);
    if (usuario==null){
%>
        <jsp:forward page="index.jsp"/>
<%
    }
%>

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
   
        <div style="background-color: red; color:white; font-size:large;">${msg}</div>
        <p>Hola <%=usuario.getNombre()%> </p>
        
<%
    session.setAttribute("msg", null);
%>
    </body>
</html>
