const express = require("express");

const app = express();
const puerto = 3000;

// Permite que el servicio lea datos enviados en formato JSON.
app.use(express.json());

// Lista temporal para las pruebas de este ejercicio.
// Se reinicia cuando se detiene el servidor.
const usuarios = [];

// Confirma que el servicio está funcionando.
app.get("/", (req, res) => {
  res.json({ mensaje: "API de SumaPets en funcionamiento" });
});

// Registra un usuario de prueba.
app.post("/api/registro", (req, res) => {
  const { usuario, contrasena } = req.body || {};

  if (!usuario || !contrasena) {
    return res.status(400).json({
      mensaje: "Debes enviar un usuario y una contraseña.",
    });
  }

  const usuarioExistente = usuarios.find(
    (elemento) => elemento.usuario === usuario
  );

  if (usuarioExistente) {
    return res.status(409).json({
      mensaje: "Ese usuario ya está registrado.",
    });
  }

  usuarios.push({ usuario, contrasena });

  return res.status(201).json({
    mensaje: "Usuario registrado correctamente.",
  });
});

// Comprueba que el usuario exista y que la contraseña coincida.
app.post("/api/login", (req, res) => {
  const { usuario, contrasena } = req.body || {};

  if (!usuario || !contrasena) {
    return res.status(400).json({
      mensaje: "Debes enviar un usuario y una contraseña.",
    });
  }

  const usuarioEncontrado = usuarios.find(
    (elemento) =>
      elemento.usuario === usuario &&
      elemento.contrasena === contrasena
  );

  if (!usuarioEncontrado) {
    return res.status(401).json({
      mensaje: "Error en la autenticación.",
    });
  }

  return res.status(200).json({
    mensaje: "Autenticación satisfactoria.",
  });
});

// Inicia el servicio en el puerto 3000.
app.listen(puerto, () => {
  console.log(`Servicio iniciado en http://localhost:${puerto}`);
});