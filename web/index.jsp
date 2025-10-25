<%-- 
    Document   : index
    Created on : 15/10/2025, 1:36:49 a. m.
    Author     : jona
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ferretería - Sistema</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <header>
        <div class="container">
            <div class="logo">
                <h1>🔧 Ferretería JBH</h1>
            </div>
            <nav class="main-nav">
                <a href="#" class="nav-link active" data-tab="dashboard">Inicio</a>
                <a href="#" class="nav-link" data-tab="usuarios">Usuarios</a>
                <a href="#" class="nav-link" data-tab="productos">Productos</a>
                <a href="#" class="nav-link" data-tab="facturas">Facturas</a>
                <a href="#" class="nav-link" data-tab="detalle-factura">Detalle Facturas</a>
                <a href="#" class="nav-link" data-tab="ingreso-almacen">Ingreso Almacén</a>
                <a href="#" class="nav-link" data-tab="detalle-ingreso">Detalle Ingresos</a>
            </nav>
            <div class="user-info">
                <span>Administrador</span>
                <button class="btn-logout">Cerrar Sesión</button>
            </div>
        </div>
    </header>

    <main class="container">
        <!-- Dashboard 3x3 -->
        <div class="tab-content active" id="dashboard">
            <h2 class="tab-title">Panel de Control</h2>
            <p>Bienvenido al sistema de gestión de Ferretería JBH</p>
            
            <div class="card-grid-3x3">
                <div class="card click-effect" data-tab="usuarios">
                    <h3>👥 Usuarios</h3>
                    <p>Gestionar usuarios del sistema</p>
                </div>
                <div class="card click-effect" data-tab="productos">
                    <h3>🛠️ Productos</h3>
                    <p>Administrar inventario</p>
                </div>
                <div class="card click-effect" data-tab="facturas">
                    <h3>🧾 Facturas</h3>
                    <p>Ver ventas y facturas</p>
                </div>
                <div class="card click-effect" data-tab="detalle-factura">
                    <h3>📋 Detalle Facturas</h3>
                    <p>Detalles de ventas</p>
                </div>
                <div class="card click-effect" data-tab="ingreso-almacen">
                    <h3>📦 Ingreso Almacén</h3>
                    <p>Control de entradas</p>
                </div>
                <div class="card click-effect" data-tab="detalle-ingreso">
                    <h3>📄 Detalle Ingresos</h3>
                    <p>Detalles de entradas</p>
                </div>
            </div>
        </div>

        <!-- Usuarios -->
        <div class="tab-content" id="usuarios">
            <div class="tab-header">
                <h2>Gestión de Usuarios</h2>
                <div class="action-buttons">
                    <button id="btn-add-user" class="btn btn-primary click-effect">Agregar Usuario</button>
                    <button class="btn btn-success click-effect">Actualizar</button>
                    <button class="btn btn-danger click-effect">Eliminar Seleccionados</button>
                </div>
            </div>
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="select-all-users"></th>
                            <th>ID</th>
                            <th>Usuario</th>
                            <th>Nombre</th>
                            <th>Rol</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="usuarios-tbody">
                        <!-- filas serán renderizadas dinámicamente por js -->
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Productos -->
        <div class="tab-content" id="productos">
            <div class="tab-header">
                <h2>Gestión de Productos</h2>
                <div class="action-buttons">
                    <button id="btn-add-product" class="btn btn-primary click-effect">Agregar Producto</button>
                    <button class="btn btn-success click-effect">Actualizar</button>
                    <button class="btn btn-danger click-effect">Eliminar Seleccionados</button>
                </div>
            </div>
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="select-all-products"></th>
                            <th>Código</th>
                            <th>Nombre</th>
                            <th>Precio</th>
                            <th>Stock</th>
                            <th>Categoría</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="productos-tbody">
                        <!-- filas serán renderizadas dinámicamente por js -->
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Facturas -->
        <div class="tab-content" id="facturas">
            <div class="tab-header">
                <h2>Gestión de Facturas</h2>
                <div class="action-buttons">
                    <button id="btn-add-factura" class="btn btn-primary click-effect">Agregar Factura</button>
                    <button class="btn btn-success click-effect">Actualizar</button>
                    <button class="btn btn-danger click-effect">Eliminar Seleccionadas</button>
                </div>
            </div>
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="select-all-invoices"></th>
                            <th>N° Factura</th>
                            <th>Fecha</th>
                            <th>Cliente</th>
                            <th>Total</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="facturas-tbody">
                        <tr>
                            <td><input type="checkbox" class="row-select"></td>
                            <td>F-001</td>
                            <td>01/12/2023</td>
                            <td>Cliente General</td>
                            <td>$150.00</td>
                            <td>Pagada</td>
                            <td>
                                <button class="btn btn-success click-effect">Actualizar</button>
                                <button class="btn btn-danger click-effect">Eliminar</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Detalle Factura -->
        <div class="tab-content" id="detalle-factura">
            <div class="tab-header">
                <h2>Detalle de Facturas</h2>
                <div class="action-buttons">
                    <button id="btn-add-detail-factura" class="btn btn-primary click-effect">Agregar Detalle</button>
                    <button class="btn btn-success click-effect">Actualizar</button>
                    <button class="btn btn-danger click-effect">Eliminar Seleccionados</button>
                </div>
            </div>
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="select-all-invoice-details"></th>
                            <th>ID Detalle</th>
                            <th>N° Factura</th>
                            <th>Producto</th>
                            <th>Cantidad</th>
                            <th>Precio Unitario</th>
                            <th>Subtotal</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="detalles-factura-tbody">
                        <!-- Datos renderizados dinámicamente -->
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Ingreso Almacén -->
        <div class="tab-content" id="ingreso-almacen">
            <div class="tab-header">
                <h2>Ingresos a Almacén</h2>
                <div class="action-buttons">
                    <button id="btn-add-ingreso" class="btn btn-primary click-effect">Agregar Ingreso</button>
                    <button class="btn btn-success click-effect">Actualizar</button>
                    <button class="btn btn-danger click-effect">Eliminar Seleccionados</button>
                </div>
            </div>
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="select-all-storage"></th>
                            <th>N° Ingreso</th>
                            <th>Fecha</th>
                            <th>Proveedor</th>
                            <th>Total</th>
                            <th>Factura Proveedor</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="ingresos-tbody">
                        <!-- Datos renderizados dinámicamente -->
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Detalle Ingreso -->
        <div class="tab-content" id="detalle-ingreso">
            <div class="tab-header">
                <h2>Detalle de Ingresos</h2>
                <div class="action-buttons">
                    <button id="btn-add-detail-ingreso" class="btn btn-primary click-effect">Agregar Detalle</button>
                    <button class="btn btn-success click-effect">Actualizar</button>
                    <button class="btn btn-danger click-effect">Eliminar Seleccionados</button>
                </div>
            </div>
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="select-all-storage-details"></th>
                            <th>ID Detalle</th>
                            <th>N° Ingreso</th>
                            <th>Producto</th>
                            <th>Cantidad</th>
                            <th>Precio Compra</th>
                            <th>Subtotal</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="detalles-ingreso-tbody">
                        <!-- Datos renderizados dinámicamente -->
                    </tbody>
                </table>
            </div>
        </div>
    </main>

    <footer>
        <div class="container">
            <p>Ferretería Pro &copy; 2025 - Universidad Mariano Gálvez de Guatemala</p>
        </div>
    </footer>

    <script src="js/main.js"></script>
</body>
</html>