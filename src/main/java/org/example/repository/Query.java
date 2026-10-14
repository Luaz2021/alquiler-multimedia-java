package org.example.repository;

public class Query {

    // ==========================================
    //            CONSULTAS DE USUARIOS
    // ==========================================
    public static final String INSERTAR_USUARIO =
            "INSERT INTO usuarios (dni, nombre, edad, asignatura, tipo) VALUES (?, ?, ?, ?, ?)";

    public static final String BUSCAR_POR_DNI =
            "SELECT dni, nombre, edad, asignatura, tipo FROM usuarios WHERE dni = ?";

    public static final String LISTAR_TODOS =
            "SELECT dni, nombre, edad, asignatura, tipo FROM usuarios";

    public static final String ACTUALIZAR =
            "UPDATE usuarios SET nombre = ?, edad = ?, asignatura = ?, tipo = ? WHERE dni = ?";

    public static final String ELIMINAR_POR_DNI =
            "DELETE FROM usuarios WHERE dni = ?";

    // ==========================================
    //            CONSULTAS DE ARTÍCULOS
    // ==========================================
    public static final String INSERTAR_ARTICULO =
            "INSERT INTO articulos (num_serie, titulo, genero, plataforma, tipo) VALUES (?, ?, ?, ?, ?)";

    public static final String BUSCAR_ARTICULO_POR_SERIE =
            "SELECT num_serie, titulo, genero, plataforma, tipo FROM articulos WHERE num_serie = ?";

    public static final String LISTAR_ARTICULOS =
            "SELECT num_serie, titulo, genero, plataforma, tipo FROM articulos";

    public static final String ACTUALIZAR_ARTICULO =
            "UPDATE articulos SET titulo = ?, genero = ?, plataforma = ? WHERE num_serie = ?";

    public static final String ELIMINAR_ARTICULO =
            "DELETE FROM articulos WHERE num_serie = ?";

    // ==========================================
    //            CONSULTAS DE PRÉSTAMOS
    // ==========================================
    public static final String INSERTAR_PRESTAMO =
            "INSERT INTO prestamos (usuario_dni, articulo_num_serie, fecha_prestamo, fecha_devolucion_esperada, fecha_devolucion_real) VALUES (?, ?, ?, ?, ?)";

    public static final String REGISTRAR_DEVOLUCION =
            "UPDATE prestamos SET fecha_devolucion_real = ? WHERE id_prestamo = ?";

    public static final String LISTAR_TODOS_PRESTAMOS =
            "SELECT id_prestamo, usuario_dni, articulo_num_serie, fecha_prestamo, fecha_devolucion_esperada, fecha_devolucion_real FROM prestamos";
}