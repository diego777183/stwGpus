<%-- 
    Document   : index
    Created on : 13-abr-2018, 11:29:48
    Author     : uzffs
--%>



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

        <a href="<%=response.encodeRedirectURL("listadoPrecioLuz.jsp")%>">Listado de Precios de la Luz</a> |
        <a href="<%=response.encodeRedirectURL("listadoPrecioEthereum.jsp")%>">Listado de Precios de Ethereum</a>
        <br><br><br><br>
        <form method="POST" action="<%=response.encodeURL("login")%>">
            <table>
                <tr>
                    <td>Login:</td>
                    <td><input name="login"></td>
                </tr>
                <tr>
                    <td>Contraseña:</td>
                    <td><input type="password" name="pwd"></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Login"></td>
                </tr>
            </table>
        
        </form>
            
            <br><br>
            <a href="<%=response.encodeURL("addUsuario.jsp")%>">Dar de alta un nuevo usuario</a>

     <!--   menu dropdown html
     https://codigonaranja.com/curso-html-mostrar-listas-desplegables
        -->
        
   

        
    </body>
</html>
