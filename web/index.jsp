
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Ajax con Servlet</title>
  <script src="http://code.jquery.com/jquery-latest.js"></script>
  <script>
    $(document).ready(function() {
      $('#submit').click(function(event) {
        var nombreVar = $('#nombre').val();
        var apellidoVar = $('#apellido').val();
        var emailVar = $('#email').val();
        $.post('connected', {
          nombre : nombreVar,
          apellido: apellidoVar,
          email: emailVar
        }, function(responseText) {
          $('#tabla').html(responseText);
        });
      });
    });
  </script>
</head>
<body>
<center>

  <h2>Registro de Usuarios</h2>
  <form id="form1">
    <table >
      <tr><td>Nombre:</td><td><input type="text" id="nombre" /></td></tr>
      <tr><td>Apellido:</td><td><input type="text" id="apellido" /></td></tr>
      <tr><td>Email:</td><td><input type="text" id="email" /></td></tr>
      <tr><td colspan="3" align="center"><input type="button" id="submit" value="Añadir" /></td></tr>
    </table>
  </form>

  <div id="tabla"></div>
</center>
</body>
</html>