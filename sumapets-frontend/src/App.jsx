import { useMemo, useState } from "react";
import "./App.css";

// Productos de ejemplo basados en el inventario de SumaPets.
const productosIniciales = [
  {
    id: 1,
    nombre: "Alimento para perro adulto 2 kg",
    categoria: "Alimento",
    marca: "NutriCan",
    precio: 24900,
    stock: 12,
  },
  {
    id: 2,
    nombre: "Arena sanitaria 4 kg",
    categoria: "Higiene",
    marca: "MichiClean",
    precio: 18900,
    stock: 8,
  },
  {
    id: 3,
    nombre: "Correa mediana",
    categoria: "Accesorios",
    marca: "PetWalk",
    precio: 32000,
    stock: 5,
  },
];

const formatoPrecio = new Intl.NumberFormat("es-CO", {
  style: "currency",
  currency: "COP",
  maximumFractionDigits: 0,
});

function App() {
  const [productos, setProductos] = useState(productosIniciales);
  const [busqueda, setBusqueda] = useState("");
  const [mensaje, setMensaje] = useState("");

  const [formulario, setFormulario] = useState({
    nombre: "",
    categoria: "",
    marca: "",
    precio: "",
    stock: "",
  });

  // Filtra por nombre, categoría o marca.
  const productosFiltrados = useMemo(() => {
    const texto = busqueda.trim().toLowerCase();

    return productos.filter((producto) => {
      const datosProducto =
        `${producto.nombre} ${producto.categoria} ${producto.marca}`.toLowerCase();

      return datosProducto.includes(texto);
    });
  }, [productos, busqueda]);

  const totalUnidades = productos.reduce(
    (total, producto) => total + producto.stock,
    0
  );

  const productosConPocoStock = productos.filter(
    (producto) => producto.stock <= 5
  ).length;

  function manejarCambio(evento) {
    const { name, value } = evento.target;

    setFormulario((datosAnteriores) => ({
      ...datosAnteriores,
      [name]: value,
    }));
  }

  function agregarProducto(evento) {
    evento.preventDefault();

    const productoNuevo = {
      id: Date.now(),
      nombre: formulario.nombre.trim(),
      categoria: formulario.categoria,
      marca: formulario.marca.trim(),
      precio: Number(formulario.precio),
      stock: Number(formulario.stock),
    };

    // Agrega el nuevo producto a la lista que muestra React.
    setProductos((listaActual) => [productoNuevo, ...listaActual]);

    setFormulario({
      nombre: "",
      categoria: "",
      marca: "",
      precio: "",
      stock: "",
    });

    setMensaje("Producto agregado a la lista de demostración.");
  }

  return (
    <div className="pagina">
      <header className="barra-superior">
        <div className="marca">
          <span className="marca-icono" aria-hidden="true">🐾</span>
          <div>
            <p className="marca-etiqueta">TIENDA DE MASCOTAS</p>
            <h1>SumaPets</h1>
          </div>
        </div>
        <span className="etiqueta-panel">Panel de inventario</span>
      </header>

      <main className="contenedor">
        <section className="introduccion">
          <p className="texto-etiqueta">GESTIÓN DE PRODUCTOS</p>
          <h2>Inventario</h2>
          <p>Consulta y registra los productos de SumaPets.</p>
        </section>

        <section className="resumen" aria-label="Resumen del inventario">
          <article className="tarjeta-resumen">
            <span>Productos registrados</span>
            <strong>{productos.length}</strong>
          </article>

          <article className="tarjeta-resumen">
            <span>Unidades disponibles</span>
            <strong>{totalUnidades}</strong>
          </article>

          <article className="tarjeta-resumen">
            <span>Productos con poco stock</span>
            <strong>{productosConPocoStock}</strong>
          </article>
        </section>

        <section className="contenido">
          <form className="panel formulario" onSubmit={agregarProducto}>
            <p className="texto-etiqueta">REGISTRO</p>
            <h3>Agregar producto</h3>

            {mensaje && <p className="mensaje" role="status">{mensaje}</p>}

            <label htmlFor="nombre">Nombre del producto</label>
            <input
              id="nombre"
              name="nombre"
              value={formulario.nombre}
              onChange={manejarCambio}
              placeholder="Ej. Juguete para gato"
              required
            />

            <label htmlFor="categoria">Categoría</label>
            <select
              id="categoria"
              name="categoria"
              value={formulario.categoria}
              onChange={manejarCambio}
              required
            >
              <option value="">Selecciona una categoría</option>
              <option value="Alimento">Alimento</option>
              <option value="Higiene">Higiene</option>
              <option value="Accesorios">Accesorios</option>
              <option value="Juguetes">Juguetes</option>
              <option value="Salud">Salud</option>
            </select>

            <label htmlFor="marca">Marca</label>
            <input
              id="marca"
              name="marca"
              value={formulario.marca}
              onChange={manejarCambio}
              placeholder="Nombre de la marca"
              required
            />

            <div className="campos-numericos">
              <div>
                <label htmlFor="precio">Precio</label>
                <input
                  id="precio"
                  name="precio"
                  type="number"
                  min="0"
                  step="0.01"
                  value={formulario.precio}
                  onChange={manejarCambio}
                  placeholder="0"
                  required
                />
              </div>

              <div>
                <label htmlFor="stock">Unidades</label>
                <input
                  id="stock"
                  name="stock"
                  type="number"
                  min="0"
                  step="1"
                  value={formulario.stock}
                  onChange={manejarCambio}
                  placeholder="0"
                  required
                />
              </div>
            </div>

            <button className="boton-principal" type="submit">
              Agregar a la lista
            </button>
          </form>

          <section className="panel lista">
            <div className="encabezado-lista">
              <div>
                <p className="texto-etiqueta">CATÁLOGO</p>
                <h3>Productos registrados</h3>
              </div>
              <span className="contador">{productosFiltrados.length} productos</span>
            </div>

            <label className="etiqueta-busqueda" htmlFor="busqueda">
              Buscar productos
            </label>
            <input
              id="busqueda"
              className="busqueda"
              type="search"
              value={busqueda}
              onChange={(evento) => setBusqueda(evento.target.value)}
              placeholder="Buscar por nombre, categoría o marca"
            />

            <div className="tabla-contenedor">
              <table>
                <thead>
                  <tr>
                    <th>Producto</th>
                    <th>Categoría</th>
                    <th>Marca</th>
                    <th>Precio</th>
                    <th>Stock</th>
                  </tr>
                </thead>
                <tbody>
                  {productosFiltrados.map((producto) => (
                    <tr key={producto.id}>
                      <td>{producto.nombre}</td>
                      <td>{producto.categoria}</td>
                      <td>{producto.marca}</td>
                      <td>{formatoPrecio.format(producto.precio)}</td>
                      <td>
                        <span className={producto.stock <= 5 ? "stock bajo" : "stock"}>
                          {producto.stock}
                        </span>
                      </td>
                    </tr>
                  ))}

                  {productosFiltrados.length === 0 && (
                    <tr>
                      <td colSpan="5" className="sin-resultados">
                        No se encontraron productos.
                      </td>
                    </tr>
                  )}
                </tbody>
              </table>
            </div>
          </section>
        </section>
      </main>
    </div>
  );
}

export default App;