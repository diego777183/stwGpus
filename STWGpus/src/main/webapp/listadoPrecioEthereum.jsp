<%-- 
    Document   : index
    Created on : 20-03-2020, 11:29:48
    Author     : fserna

--%>


<%@page import="bd.PrecioEthereum"%>
<%@page import="bd.PrecioEthereumDAO"%>
<%@page import="bd.PrecioLuz"%>
<%@page import="bd.PrecioLuzDAO"%>
<%@page import="util.Util"%>
<%@page import="util.Time"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.Context"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
    PrecioEthereumDAO   preciosEthereumDAO = null;
    
    Context ctx = new InitialContext();
    preciosEthereumDAO = (PrecioEthereumDAO)ctx.lookup("java:module/PrecioEthereumDAO");
%>
<!DOCTYPE html>
<style>
    body {font-family: "Lato", sans-serif; font-size: 12px;}
    
</style>
  <html>
  <head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Year', 'Sales'],
          ['2004',  1000],
          ['2005',  1170],
          ['2006',  660],
          ['2007',  1030]
        ]);

        var options = {
          title: 'Company Performance',
          curveType: 'function',
          legend: { position: 'bottom' }
        };

        var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));

        chart.draw(data, options);
      }
    </script>
  </head>
  <body>
      <%@include file="WEB-INF/jspf/cabecera.jspf" %>
    <div id="curve_chart" style="width: 900px; height: 500px"></div>
  </body>
</html>
