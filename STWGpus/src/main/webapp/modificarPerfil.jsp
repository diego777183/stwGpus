<%-- 
    Document   : index
    Created on : 13-abr-2018, 11:29:48
    Author     : uzffs
--%>


<%@page import="bd.UsuarioFacade"%>
<%@page import="bd.Usuario"%>
<%@page import="util.Util"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.Context"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
        session.setAttribute("msg", "ERROR: El usuario ha sido eliminado.");
%>
        <jsp:forward page="index.jsp"/>
<%
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
       
        
        <legend>Modificar Usuario <b><%=usuario.getNombre()%> <%=usuario.getAp1()%></b></legend>

        <form method="POST" action="<%=response.encodeURL("modificarUsuario")%>">
            <input type="hidden" name="id" value="<%=usuario.getId()%>">
            <table>
                <tr>
                    <td>Login:</td>
                    <td><input name="login" value="<%=usuario.getLogin()%>"></td>
                </tr>
                <tr>
                    <td>Contraseña:</td>
                    <td><input type="pwd" name="pwd" value="<%=usuario.getPassword()%>"></td>
                </tr>
                <tr>
                    <td>Nombre:</td>
                    <td><input name="nombre" value="<%=usuario.getNombre()%>"></td>
                </tr>
                <tr>
                    <td>Ap1:</td>
                    <td><input name="ap1" value="<%=usuario.getAp1()%>"></td>
                </tr>
                <tr>
                    <td colspan="2" align="right"><input type="submit" value="Modificar"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
