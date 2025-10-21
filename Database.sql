-- 1. Crear la tabla "Periodista" (la entidad "uno")
-- Se usa 'dni' como clave primaria.
CREATE TABLE Periodista (
                            dni VARCHAR(9) NOT NULL,
                            nombre VARCHAR(255),
                            telefono VARCHAR(255),
                            PRIMARY KEY (dni)
);

-- 2. Crear la tabla "Articulo" (la entidad "muchos")
-- Tiene su propia clave primaria autoincremental 'id'.
CREATE TABLE Articulo (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          anioPublicacion INT NOT NULL,
                          numeroPalabras INT NOT NULL,
                          titulo VARCHAR(255),

    -- Columna para la clave foránea que apunta al 'dni' del periodista
                          periodista_dni VARCHAR(9) NOT NULL,

                          PRIMARY KEY (id)
);

-- 3. Añadir la restricción de clave foránea (la relación)
-- Esto conecta Articulo con Periodista.
ALTER TABLE Articulo
    ADD CONSTRAINT FK_Articulo_Periodista
        FOREIGN KEY (periodista_dni)
            REFERENCES Periodista (dni)
            ON DELETE CASCADE; -- Esta es la parte clave del ejercicio:
-- Si se borra un Periodista, sus Articulos se borran automáticamente.