CREATE DATABASE BIBLIOTECA_AUTISTA;
USE BIBLIOTECA_AUTISTA;
-- DROP DATABASE BIBLIOTECA_AUTISTA;

-- 1. Tablas independientes (no dependen de otras)
CREATE TABLE categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE musica_relajante (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(200) NOT NULL,
    src VARCHAR (500)
);
		
CREATE TABLE cuentos (
		id BIGINT AUTO_INCREMENT PRIMARY KEY,
        titulo VARCHAR (500) NOT NULL, 
        contenido VARCHAR (500) NOT NULL
);

CREATE TABLE emociones (
	id BIGINT auto_increment PRIMARY KEY,
    descripcion VARCHAR(255),
    tipo_emoji VARCHAR (100),
    usuario_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- 2. Tabla usuarios (depende de roles)
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    fecha_registro DATETIME,
    metodo_registro VARCHAR(50),
    dispositivo VARCHAR(100),
    rol_id BIGINT,
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);

-- 3. Tabla libros (depende de categorias)
CREATE TABLE libros (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT,
    autor VARCHAR(100) NOT NULL,
    año VARCHAR(100) NOT NULL,
    imagen VARCHAR(255),
    categoria_id BIGINT,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);
-- 5. Juegos y actividades relacionadas
CREATE TABLE juegos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(200) NOT NULL,
    url VARCHAR(200) NOT NULL
);

CREATE TABLE videos (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    url VARCHAR(255) NOT NULL
    );

-- 6. Preguntas y opciones de respuesta

CREATE TABLE necesitas_ayuda (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT,
    comentario VARCHAR(200),
    numero_telefono VARCHAR(200),
    correo_electronico VARCHAR(200),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE preguntas_seguridad (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pregunta VARCHAR(200)
);

CREATE TABLE preguntas_de_seguridad (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT,
    pregunta_id BIGINT,
    respuesta VARCHAR(200),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (pregunta_id) REFERENCES preguntas_seguridad(id)
);

-- ----------------------------- INSERCIONES NECESARIAS DML ----------------------

-- INSERCION DE PREGUNTAS DE SEGURIDAD 

INSERT INTO preguntas_seguridad (id, pregunta) VALUES
(1, '¿Cómo se llamaba tu primera mascota?'),
(2, '¿Cuál fue el nombre de tu primer maestro o maestra?'),
(3, '¿En qué ciudad naciste?'),
(4, '¿Cuál es tu comida favorita de la infancia?');


-- INSERCION DE CATEGORIAS 

INSERT INTO categorias (id, nombre) VALUES
(2, 'terror'),
(3, 'accion'),
(4, 'fabulas'),
(5, 'fantasia');


-- INSERCION DE ROLES 

INSERT INTO roles (id, nombre) VALUES
(1, 'administrador'),
(2, 'usuario');

-- INSERCION DE CANCIONES RELAJANTES 

INSERT INTO musica_relajante (id, nombre, src) VALUES
(1, 'Sonidos de la naturaleza', '/audios/naturaleza.mp3'),
(2, 'Música instrumental', '/audios/instrumental.mp3'),
(3, 'Melodía relajante', '/audios/relajante.mp3');


-- INSERCION DE LIBROS INICIALES, NECESARIO MODIFICAR URL DE LOS LIBROS 

INSERT INTO libros (id, titulo, descripcion, imagen, categoria_id, creado_en, autor, año, url) VALUES
(1, 'Elmer nnn', 'Elmer era diferente. Elmer era de colores: amarillo y naranja y rojo y rosa y morado y azul y verde y negro y blanco. Elmer no era color elefante.', '/galeria/elmer_elelefante.jpg', 2, NULL, 'David McKee', 1989, 'https://www.amazon.com/-/es/Por-cuatro-esquinitas-nada-Spanish/dp/8426134475'),
(2, 'El monstruo de colores', 'El Monstruo de Colores no sabe qué le pasa. Se ha hecho un lío con las emociones y ahora le toca deshacer el embrollo.', '/galeria/elmostrodecolores.jpg', 3, NULL, 'Anna Llenas', 2012, 'https://www.amazon.com/-/es/Por-cuatro-esquinitas-nada-Spanish/dp/8426134475'),
(3, 'Por cuatro esquinitas de nada', 'Cuadradito quiere jugar en casa de sus amigos Redonditos, pero no pasa por la puerta porque ¡la puerta es redonda como sus amigos!', '/galeria/cuatroesquinitas.jpg', 3, NULL, 'Jerôme Ruillier', 2002, 'https://www.amazon.com/-/es/Por-cuatro-esquinitas-nada-Spanish/dp/8426134475'),
(4, 'Elmer pruebito cambio desde la web', 'Elmer es un elefante de colores que enseña a los niños sobre la importancia de la diversidad y la aceptación.', '/galeria/elmer.jpg', 3, '2025-05-23 19:47:47', 'David McKee', 1989, 'https://www.amazon.com/-/es/Elmer-David-McKee/dp/0399245792'),
(5, 'La oruga muy hambrienta', 'La historia de una pequeña oruga que se convierte en mariposa, mostrando el ciclo de la vida.', '/galeria/orugahambrienta.jpg', 3, '2025-05-23 19:47:47', 'Eric Carle', 1969, 'https://www.amazon.com/-/es/Oruga-Muy-Hambrienta-Spanish/dp/0399226907'),
(6, 'El monstruo de colores', 'Un libro que ayuda a los niños a entender y gestionar sus emociones a través de un monstruo colorido.', '/galeria/monstruocolores.jpg', 2, '2025-05-23 19:47:47', 'Anna Llenas', 2012, 'https://www.amazon.com/-/es/El-Monstruo-Colores-Anna-Llenas/dp/8415124636'),
(7, 'Donde viven los monstruos', 'Max se embarca en una aventura a la tierra de los monstruos donde aprende sobre la amistad y el hogar.', '/galeria/monstruos.jpg', 2, '2025-05-23 19:47:47', 'Maurice Sendak', 1963, 'https://www.amazon.com/-/es/Donde-viven-los-monstruos-Spanish/dp/0064431789'),
(8, 'A qué sabe la luna', 'Un grupo de animales trabajan juntos para descubrir a qué sabe la luna.', '/galeria/quesabelaluna.jpg', 2, '2025-05-23 19:47:47', 'Michael Grejniec', 2002, 'https://www.amazon.com/-/es/Que-sabe-la-luna-Spanish/dp/8426132281'),
(9, 'El pez arcoíris', 'Un pez que aprende la importancia de compartir para ser feliz.', '/galeria/pezarcoiris.jpg', 2, '2025-05-23 19:47:47', 'Marcus Pfister', 1992, 'https://www.amazon.com/-/es/El-pez-arco-iris-Spanish/dp/8426134491'),
(10, 'Cuentos para niños que se atreven a ser diferentes', 'Historias inspiradoras para que los niños aprendan a quererse y aceptarse.', '/galeria/cuentosdiferentes.jpg', 2, '2025-05-23 19:47:47', 'Ben Brooks', 2017, 'https://www.amazon.com/-/es/Cuentos-para-niños-atreven-diferentes/dp/8416695228'),
(44, 'Libro de prueba desde la web creado', 'Este es un libro de prueba que ha sido creado desde la web', 'url', 2, NULL, 'Alexander Martinez', 2001, 'url'),
(46, 'probando insercion con categoria', 'Cuadradito quiere jugar en casa de sus amigos Redonditos, pero no pasa por la puerta porque ¡la puerta es redonda como sus amigos!', '/galeria/cuatroesquinitas.jpg', 2, NULL, 'Jérôme Ruillier', 2002, 'https://www.amazon.com/-/es/Por-cuatro-esquinitas-nada-Spanish/dp/8426134475'),
(47, 'probando insercion con categoria2', 'Cuadradito quiere jugar en casa de sus amigos Redonditos...', '/galeria/cuatroesquinitas.jpg', 2, NULL, 'Jérôme Ruillier', 2002, 'https://www.amazon.com/-/es/Por-cuatro-esquinitas-nada-Spanish/dp/8426134475'),
(48, 'probando insercion con categoria23', 'Cuadradito quiere jugar en casa de sus amigos Redonditos...', '/galeria/cuatroesquinitas.jpg', 2, NULL, 'Jérôme Ruillier', 2002, 'https://www.amazon.com/-/es/Por-cuatro-esquinitas-nada-Spanish/dp/8426134475'),
(49, 'probando insercion con categoria2223', 'Cuadradito quiere jugar en casa de sus amigos Redonditos...', '/galeria/cuatroesquinitas.jpg', 2, NULL, 'Jérôme Ruillier', 2002, 'https://www.amazon.com/-/es/Por-cuatro-esquinitas-nada-Spanish/dp/8426134475'),
(50, 'probando insercion con categoria123', 'Cuadradito quiere jugar en casa de sus amigos Redonditos...', '/galeria/cuatroesquinitas.jpg', 2, NULL, 'Jérôme Ruillier', 2002, 'https://www.amazon.com/-/es/Por-cuatro-esquinitas-nada-Spanish/dp/8426134475'),
(51, 'probando insercion con categoria1234', 'Cuadradito quiere jugar en casa de sus amigos Redonditos...', '/galeria/cuatroesquinitas.jpg', 2, NULL, 'Jérôme Ruillier', 2002, 'https://www.amazon.com/-/es/Por-cuatro-esquinitas-nada-Spanish/dp/8426134475');

-- INSERCION DE LIBROS 

INSERT INTO cuentos (id, titulo, contenido) VALUES
(1, 'La tortuga tranquila', 'Había una vez una tortuga que siempre se sentía apurada y estresada. Un día, aprendió a meterse en su caparazón y respirar profundamente cuando se sentía así. Poco a poco, la tortuga aprendió a calmarse y disfrutar de la vida a su propio ritmo.'),
(2, 'El bosque sereno', 'En medio del bosque había un claro donde todos los animales iban cuando necesitaban paz. Allí, el sonido de las hojas y el arroyo creaban una melodía perfecta para relajarse y encontrar calma interior.');




