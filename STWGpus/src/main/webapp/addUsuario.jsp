<%-- 
    Document   : index
    Created on : 13-abr-2018, 11:29:48
    Author     : uzffs
--%>


<%@page import="demo.bd.Usuario"%>
<%@page import="demo.util.Util"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.Context"%>
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
    </head>
    <body>
        <h1>Mensajería</h1>
        <hr>
        <br>
        <a href="index.jsp">Volver</a>
        <br><br>
        
        <legend>Crear Usuario</legend>

        <form method="POST" action="<%=response.encodeURL("addUsuario")%>">
            <table>
                <tr>
                    <td>Login:</td>
                    <td><input name="login"></td>
                </tr>
                <tr>
                    <td>Contraseña:</td>
                    <td><input type="pwd" name="pwd"></td>
                </tr>
                <tr>
                    <td>Nombre:</td>
                    <td><input name="nombre"></td>
                </tr>
                <tr>
                    <td>Ap1:</td>
                    <td><input name="ap1"></td>
                </tr>

                <tr>
                    <td colspan="2" align="right"><input type="submit" value="Dar de Alta"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
