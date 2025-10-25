// Navegación entre pestañas
document.addEventListener('DOMContentLoaded', function() {
    // Elementos del DOM
    var navLinks = document.querySelectorAll('.nav-link');
    var tabContents = document.querySelectorAll('.tab-content');
    var cards = document.querySelectorAll('.card');
    var buttons = document.querySelectorAll('.btn');
    var logoutBtn = document.querySelector('.btn-logout');

    // --- API base ---
    var apiBase = 'http://localhost:8080/Ferreteria/webresources/generic/';

    // --- Helpers fetch ---
    function fetchJson(url, opts) {
        return fetch(url, opts).then(function(resp){
            if(!resp.ok) throw new Error('HTTP error '+resp.status);
            // try parse json, otherwise text
            return resp.headers.get('content-type') && resp.headers.get('content-type').indexOf('application/json') !== -1
                ? resp.json()
                : resp.text();
        });
    }

    function postJson(url, data) {
        return fetchJson(url, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });
    }
    function putJson(url, data) {
        return fetchJson(url, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });
    }
    function deleteReq(url) {
        return fetchJson(url, { method: 'DELETE' });
    }

    // Función para cambiar pestaña
    function switchTab(tabName) {
        // Remover activo de todos los links
        navLinks.forEach(function(link) {
            link.classList.remove('active');
        });
        
        // Remover activo de todos los contenidos
        tabContents.forEach(function(content) {
            content.classList.remove('active');
        });

        // Activar el link seleccionado
        var activeLink = document.querySelector('.nav-link[data-tab="' + tabName + '"]');
        if (activeLink) {
            activeLink.classList.add('active');
        }

        // Activar el contenido seleccionado
        var activeContent = document.getElementById(tabName);
        if (activeContent) {
            activeContent.classList.add('active');
        }
    }

    // Eventos para links de navegación
    navLinks.forEach(function(link) {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            var tabName = this.getAttribute('data-tab');
            switchTab(tabName);
        });
    });

    // Eventos para cards del dashboard
    cards.forEach(function(card) {
        card.addEventListener('click', function() {
            var tabName = this.getAttribute('data-tab');
            if (tabName) {
                switchTab(tabName);
            }
        });
    });

    // Evento para botón logout
    if (logoutBtn) {
        logoutBtn.addEventListener('click', function() {
            if (confirm('¿Estás seguro de que quieres cerrar sesión?')) {
                alert('Sesión cerrada correctamente');
                // Aquí iría la lógica real de logout
            }
        });
    }

    // Eventos para botones de acción (ejemplo)
    buttons.forEach(function(button) {
        button.addEventListener('click', function() {
            // Aquí puedes agregar acciones específicas para cada botón
            var buttonText = this.textContent || this.innerText;
            console.log('Botón clickeado: ' + buttonText);
            
            // Ejemplo: Si es botón de "Nuevo Usuario"
            if (buttonText.indexOf('Nuevo') !== -1) {
                // Lógica para nuevo registro
            }
        });
    });

    // Función para inicializar la primera pestaña
    function initFirstTab() {
        var firstTab = document.querySelector('.nav-link.active');
        if (firstTab) {
            var tabName = firstTab.getAttribute('data-tab');
            switchTab(tabName);
        } else if (navLinks.length > 0) {
            // Activar el primer tab si no hay ninguno activo
            var firstNavLink = navLinks[0];
            var firstTabName = firstNavLink.getAttribute('data-tab');
            firstNavLink.classList.add('active');
            switchTab(firstTabName);
        }
    }

    // --- Render functions ---
    function renderUsers(users) {
        var tbody = document.getElementById('usuarios-tbody');
        if(!tbody) return;
        tbody.innerHTML = '';
        users.forEach(function(u){
            var tr = document.createElement('tr');

            tr.innerHTML = ''
                + '<td><input type="checkbox" class="row-select"></td>'
                + '<td>' + (u.id_usuario || '') + '</td>'
                + '<td>' + (u.usuario || '') + '</td>'
                + '<td>' + (u.nombre || '') + '</td>'
                + '<td>' + (u.tipo || '') + '</td>'
                + '<td>'
                +   '<button class="btn btn-success btn-update-user" data-id="'+(u.id_usuario||'')+'">Actualizar</button> '
                +   '<button class="btn btn-danger btn-delete-user" data-id="'+(u.id_usuario||'')+'">Eliminar</button>'
                + '</td>';
            tbody.appendChild(tr);
        });

        // attach delete handlers
        Array.prototype.slice.call(tbody.querySelectorAll('.btn-delete-user')).forEach(function(b){
            b.addEventListener('click', function(){
                var id = this.getAttribute('data-id');
                if(!id) return;
                if(confirm('Eliminar usuario id ' + id + ' ?')) {
                    deleteReq(apiBase + 'Eliminar/' + encodeURIComponent(id))
                        .then(function(res){
                            alert('Eliminación: '+res);
                            fetchUsers();
                        }).catch(function(err){ alert('Error: '+err.message); });
                }
            });
        });

        // update handler (simple prompt)
        Array.prototype.slice.call(tbody.querySelectorAll('.btn-update-user')).forEach(function(b){
            b.addEventListener('click', function(){
                var id = this.getAttribute('data-id');
                if(!id) return;
                var usuario = prompt('Usuario (nombre de usuario):');
                if(usuario===null) return;
                var clave = prompt('Clave:');
                if(clave===null) return;
                var nombre = prompt('Nombre completo:');
                if(nombre===null) return;
                var tipo = prompt('Tipo (Administrador/Usuario):');
                if(tipo===null) return;
                var payload = { id_usuario: parseInt(id), usuario: usuario, clave: clave, nombre: nombre, tipo: tipo };
                putJson(apiBase + 'modificar', payload)
                    .then(function(res){ alert('Respuesta: '+res); fetchUsers(); })
                    .catch(function(err){ alert('Error: '+err.message); });
            });
        });
    }

    function renderProducts(products) {
        var tbody = document.getElementById('productos-tbody');
        if(!tbody) return;
        tbody.innerHTML = '';
        products.forEach(function(p){
            var tr = document.createElement('tr');
            tr.innerHTML = ''
                + '<td><input type="checkbox" class="row-select"></td>'
                + '<td>' + (p.codigo || '') + '</td>'
                + '<td>' + (p.nombre || '') + '</td>'
                + '<td>' + (p.precio != null ? ('$' + p.precio) : '') + '</td>'
                + '<td>' + (p.existencia != null ? p.existencia : '') + '</td>'
                + '<td>' + (p.categoria || '') + '</td>'
                + '<td>'
                +   '<button class="btn btn-success btn-update-prod" data-id="'+(p.id_producto||'')+'">Actualizar</button> '
                +   '<button class="btn btn-danger btn-delete-prod" data-id="'+(p.id_producto||'')+'">Eliminar</button>'
                + '</td>';
            tbody.appendChild(tr);
        });

        Array.prototype.slice.call(tbody.querySelectorAll('.btn-delete-prod')).forEach(function(b){
            b.addEventListener('click', function(){
                var id = this.getAttribute('data-id');
                if(!id) return;
                if(confirm('Eliminar producto id ' + id + ' ?')) {
                    deleteReq(apiBase + 'eliminarProducto/' + encodeURIComponent(id))
                        .then(function(res){
                            alert('Eliminación: '+res);
                            fetchProducts();
                        }).catch(function(err){ alert('Error: '+err.message); });
                }
            });
        });

        Array.prototype.slice.call(tbody.querySelectorAll('.btn-update-prod')).forEach(function(b){
            b.addEventListener('click', function(){
                var id = this.getAttribute('data-id');
                if(!id) return;
                var codigo = prompt('Código:');
                if(codigo===null) return;
                var nombre = prompt('Nombre:');
                if(nombre===null) return;
                var descripcion = prompt('Descripción:');
                if(descripcion===null) return;
                var precio = parseFloat(prompt('Precio:'));
                if(isNaN(precio)) precio = 0;
                var existencia = parseInt(prompt('Existencia:'));
                if(isNaN(existencia)) existencia = 0;
                var payload = { id_producto: parseInt(id), codigo: codigo, nombre: nombre, descripcion: descripcion, precio: precio, existencia: existencia };
                putJson(apiBase + 'modificarProductos', payload)
                    .then(function(res){ alert('Respuesta: '+res); fetchProducts(); })
                    .catch(function(err){ alert('Error: '+err.message); });
            });
        });
    }

    // --- Facturas ---
    function renderFacturas(facturas) {
        var tbody = document.getElementById('facturas-tbody');
        if(!tbody) return;
        tbody.innerHTML = '';
        facturas.forEach(function(f){
            var tr = document.createElement('tr');
            tr.innerHTML = ''
                + '<td><input type="checkbox" class="row-select"></td>'
                + '<td>' + (f.numero_factura || '') + '</td>'
                + '<td>' + (f.fecha ? new Date(f.fecha).toLocaleString() : '') + '</td>'
                + '<td>' + (f.cliente || '') + '</td>'
                + '<td>' + (f.total != null ? ('$' + f.total) : '') + '</td>'
                + '<td>' + (f.forma_pago || '') + '</td>'
                + '<td>'
                +   '<button class="btn btn-danger btn-delete-fact" data-id="'+(f.id_factura||'')+'">Eliminar</button>'
                + '</td>';
            tbody.appendChild(tr);
        });
        Array.prototype.slice.call(tbody.querySelectorAll('.btn-delete-fact')).forEach(function(b){
            b.addEventListener('click', function(){
                var id = this.getAttribute('data-id');
                if(!id) return;
                if(confirm('Eliminar factura id ' + id + ' ?')) {
                    deleteReq(apiBase + 'eliminarFactura/' + encodeURIComponent(id))
                        .then(function(res){
                            alert('Eliminación: '+res);
                            fetchFacturas();
                        }).catch(function(err){ alert('Error: '+err.message); });
                }
            });
        });
    }

    function fetchUsers() {
        fetchJson(apiBase + 'lista')
            .then(function(data){
                var arr = data;
                if (typeof data === 'string') {
                    try {
                        arr = JSON.parse(data);
                    } catch (e) {
                        console.error('fetchUsers: response not JSON:', data);
                        return;
                    }
                }
                if (!Array.isArray(arr)) {
                    console.error('fetchUsers: expected array, got', arr);
                    return;
                }
                renderUsers(arr || []);
            })
            .catch(function(err){ console.error(err); });
    }

    function fetchProducts() {
        fetchJson(apiBase + 'listaProductos')
            .then(function(data){
                var arr = data;
                if (typeof data === 'string') {
                    try {
                        arr = JSON.parse(data);
                    } catch (e) {
                        console.error('fetchProducts: response not JSON:', data);
                        return;
                    }
                }
                if (!Array.isArray(arr)) {
                    console.error('fetchProducts: expected array, got', arr);
                    return;
                }
                renderProducts(arr || []);
            })
            .catch(function(err){ console.error(err); });
    }

    function fetchFacturas() {
        fetchJson(apiBase + 'listaFacturas')
            .then(function(data){
                var arr = data;
                if (typeof data === 'string') {
                    try { arr = JSON.parse(data); } catch (e) { console.error('fetchFacturas: not JSON', data); return; }
                }
                if (!Array.isArray(arr)) { console.error('fetchFacturas: expected array', arr); return; }
                renderFacturas(arr || []);
            })
            .catch(function(err){ console.error(err); });
    }

    function fetchIngresos() {
        fetchJson(apiBase + 'listaA')
            .then(function(data){
                var arr = data;
                if (typeof data === 'string') {
                    try { arr = JSON.parse(data); } catch (e) { console.error('fetchIngresos: not JSON', data); return; }
                }
                if (!Array.isArray(arr)) { console.error('fetchIngresos: expected array', arr); return; }
                renderIngresos(arr || []);
            })
            .catch(function(err){ console.error(err); });
    }

    // --- Wire add buttons ---
    var addUserBtn = document.getElementById('btn-add-user');
    if(addUserBtn){
        addUserBtn.addEventListener('click', function(){
            var usuario = prompt('Usuario (nombre de usuario):');
            if(usuario===null) return;
            var clave = prompt('Clave:');
            if(clave===null) return;
            var nombre = prompt('Nombre completo:');
            if(nombre===null) return;
            var tipo = prompt('Tipo (Administrador/Usuario):');
            if(tipo===null) return;
            // id_usuario can be left to DB if auto, but service expects id_usuario param; try 0
            var payload = { id_usuario: 0, usuario: usuario, clave: clave, nombre: nombre, tipo: tipo };
            postJson(apiBase + 'agregar', payload)
                .then(function(res){ alert('Creación: '+res); fetchUsers(); })
                .catch(function(err){ alert('Error: '+err.message); });
        });
    }

    var addProductBtn = document.getElementById('btn-add-product');
    if(addProductBtn){
        addProductBtn.addEventListener('click', function(){
            var codigo = prompt('Código:');
            if(codigo===null) return;
            var nombre = prompt('Nombre:');
            if(nombre===null) return;
            var descripcion = prompt('Descripción:');
            if(descripcion===null) return;
            var precio = parseFloat(prompt('Precio:'));
            if(isNaN(precio)) precio = 0;
            var existencia = parseInt(prompt('Existencia:'));
            if(isNaN(existencia)) existencia = 0;
            // El endpoint agregaProductos espera: codigo, nombre, descripcion, precio, existencia
            var payload = { codigo: codigo, nombre: nombre, descripcion: descripcion, precio: precio, existencia: existencia };
            postJson(apiBase + 'agregaProductos', payload)
                .then(function(res){ alert('Creación: '+res); fetchProducts(); })
                .catch(function(err){ alert('Error: '+err.message); });
        });
    }

    // Agregar factura
    var addFacturaBtn = document.getElementById('btn-add-factura');
    if(addFacturaBtn){
        addFacturaBtn.addEventListener('click', function(){
            var cliente = prompt('Cliente:');
            if(cliente===null) return;
            var forma_pago = prompt('Forma de pago:');
            if(forma_pago===null) return;
            var payload = { id_usuario: 1, numero_factura: '', cliente: cliente, forma_pago: forma_pago, subtotal: 0, total: 0 };
            postJson(apiBase + 'agregarFactura', payload)
                .then(function(res){ alert('Creación: '+res); fetchFacturas(); })
                .catch(function(err){ alert('Error: '+err.message); });
        });
    }

    // Agregar ingreso almacén
    var addIngresoBtn = document.getElementById('btn-add-ingreso');
    if(addIngresoBtn){
        addIngresoBtn.addEventListener('click', function(){
            var proveedor = prompt('Proveedor:');
            if(proveedor===null) return;
            var factura_prov = prompt('Número factura proveedor:');
            if(factura_prov===null) return;
            var payload = { id_ingreso: 0, id_usuario: 1, fecha_ingreso: null, proveedor: proveedor, factura_proveedor: factura_prov, total: 0 };
            postJson(apiBase + 'agregarA', payload)
                .then(function(res){ alert('Creación: '+res); fetchIngresos(); })
                .catch(function(err){ alert('Error: '+err.message); });
        });
    }

    // Integrar con switchTab: al cambiar a usuarios o productos, cargar datos
    var originalSwitchTab = switchTab;
    switchTab = function(tabName){
        originalSwitchTab(tabName);
        if(tabName === 'usuarios'){
            fetchUsers();
        } else if(tabName === 'productos'){
            fetchProducts();
        } else if(tabName === 'facturas') {
            fetchFacturas();
        } else if(tabName === 'ingreso-almacen') {
            fetchIngresos();
        }
     };

     // Inicializar primera pestaña y cargar datos si corresponde
     initFirstTab();
     // Si la primera tab ya activa es usuarios o productos, fetches ya se invocarán porque switchTab fue sobrescrita

     // ...existing code...
 });