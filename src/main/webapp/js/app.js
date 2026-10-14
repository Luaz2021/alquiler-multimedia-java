const API_URL = "http://localhost:8080/api";

// Cargar datos automáticamente al abrir la página
document.addEventListener("DOMContentLoaded", () => {
    cargarDatosDesdeJava();
});

async function cargarDatosDesdeJava() {
    try {
        const res = await fetch(`${API_URL}/datos`);
        if (!res.ok) throw new Error("Error en la petición");
        const data = await res.json();

        // 1. Actualizar Tarjetas de Métricas
        document.querySelector(".metric-card:nth-child(1) h3").textContent = data.metricas.activos;
        document.querySelector(".metric-card:nth-child(2) h3").textContent = data.metricas.vencidos;
        document.querySelector(".metric-card:nth-child(3) h3").textContent = data.metricas.totalUsuarios;
        document.querySelector(".metric-card:nth-child(3) .text-muted").textContent =
            `${data.metricas.alumnos} Alumnos / ${data.metricas.docentes} Docentes`;

        // 2. Cargar Préstamos (Dashboard y Vista Préstamos)
        renderizarPrestamos(data.prestamos);

        // 3. Cargar Usuarios
        renderizarUsuarios(data.usuarios);

        // 4. Cargar Artículos
        renderizarArticulos(data.articulos);

    } catch (err) {
        console.warn("⚠️ Servidor Java no detectado en el puerto 8080. Ejecuta Main.java primero.", err);
    }
}

function renderizarPrestamos(prestamos) {
    const tbodyDashboard = document.querySelector("#tab-dashboard tbody");
    const tbodyPrestamos = document.querySelector("#tab-prestamos tbody");

    let filasDashboard = "";
    let filasPrestamos = "";

    prestamos.forEach(p => {
        let badgeClass = "status-activo";
        if (p.estado === "DEVUELTO") badgeClass = "status-devuelto";
        if (p.estado === "VENCIDO") badgeClass = "status-vencido";

        const btnAccion = (p.estado === "DEVUELTO")
            ? `<button class="btn-table-devolver" disabled>Devuelto</button>`
            : `<button class="btn-table-devolver" onclick="devolverArticulo(${p.id})">Devolver</button>`;

        filasDashboard += `
            <tr>
                <td><strong>${p.dni}</strong></td>
                <td>Serie: ${p.serie}</td>
                <td><span class="chip-tipo">Préstamo</span></td>
                <td>${p.fechaPrestamo}</td>
                <td><span class="badge-status ${badgeClass}">${p.estado}</span></td>
                <td>${btnAccion}</td>
            </tr>
        `;

        filasPrestamos += `
            <tr>
                <td>${p.id}</td>
                <td>${p.dni}</td>
                <td>${p.serie}</td>
                <td>${p.fechaPrestamo}</td>
                <td>${p.fechaEsperada}</td>
                <td><span class="badge-status ${badgeClass}">${p.estado}</span></td>
                <td>${btnAccion}</td>
            </tr>
        `;
    });

    if (tbodyDashboard) tbodyDashboard.innerHTML = filasDashboard;
    if (tbodyPrestamos) tbodyPrestamos.innerHTML = filasPrestamos;
}

function renderizarUsuarios(usuarios) {
    const tbody = document.querySelector("#tab-usuarios tbody");
    if (!tbody) return;

    tbody.innerHTML = usuarios.map(u => `
        <tr>
            <td>${u.dni}</td>
            <td>${u.nombre}</td>
            <td>${u.edad}</td>
            <td><span class="chip-tipo">${u.tipo}</span></td>
            <td>${u.asignatura ? u.asignatura : "-"}</td>
        </tr>
    `).join("");
}

function renderizarArticulos(articulos) {
    const tbody = document.querySelector("#tab-articulos tbody");
    if (!tbody) return;

    tbody.innerHTML = articulos.map(a => `
        <tr>
            <td>${a.serie}</td>
            <td>${a.titulo}</td>
            <td>${a.genero}</td>
            <td><span class="chip-tipo">${a.tipo}</span></td>
            <td>${a.plataforma ? a.plataforma : "-"}</td>
        </tr>
    `).join("");
}

async function devolverArticulo(idPrestamo) {
    if (!confirm(`¿Confirmar devolución para el préstamo ID ${idPrestamo}?`)) return;

    try {
        await fetch(`${API_URL}/devolver?id=${idPrestamo}`);
        alert("Devolución procesada en MySQL");
        cargarDatosDesdeJava(); // Recarga la tabla de inmediato
    } catch (e) {
        alert("Error al procesar devolución");
    }
}

// Navegación de pestañas
function switchNav(tabId, btnElement) {
    document.querySelectorAll('.tab-content').forEach(tab => tab.classList.remove('active'));
    document.querySelectorAll('aside nav button').forEach(button => button.classList.remove('active'));
    document.getElementById('tab-' + tabId).classList.add('active');
    if (btnElement) btnElement.classList.add('active');
}

// Modales
function openModal(modalId) { document.getElementById(modalId).classList.add('open'); }
function closeModal(modalId) { document.getElementById(modalId).classList.remove('open'); }
function toggleAsignatura(val) { document.getElementById('grupo-asig').style.display = (val === 'DOCENTE') ? 'block' : 'none'; }
function togglePlataforma(val) { document.getElementById('grupo-plat').style.display = (val === 'VIDEOJUEGO') ? 'block' : 'none'; }