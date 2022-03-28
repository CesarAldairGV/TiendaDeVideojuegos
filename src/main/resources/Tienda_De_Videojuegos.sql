DROP DATABASE IF EXISTS tienda_de_videojuegos;
DROP DATABASE IF EXISTS tienda_de_videojuegos_test;
DROP USER IF EXISTS'sistema'@'localhost';
CREATE DATABASE tienda_de_videojuegos;
CREATE DATABASE tienda_de_videojuegos_test;
CREATE USER 'sistema'@'localhost' IDENTIFIED BY 'sistema';
GRANT ALL ON tienda_de_videojuegos.* TO 'sistema'@'localhost';
GRANT ALL ON tienda_de_videojuegos_test.* TO 'sistema'@'localhost';
USE tienda_de_videojuegos;

CREATE TABLE usuario(
	id_usu SMALLINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    niv_usu VARCHAR(20) NOT NULL,
    nom_usu VARCHAR(25) NOT NULL,
    con_usu VARCHAR(25) NOT NULL
);

CREATE TABLE distribuidora(
	id_dis SMALLINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nom_dis VARCHAR(25) NOT NULL,
    tel_dis VARCHAR(20) NOT NULL,
    dir_dis VARCHAR(30) NOT NULL, 
    cue_dis VARCHAR(30) NOT NULL
);

CREATE TABLE videojuego(
	id_vid SMALLINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    iddis_vid SMALLINT NOT NULL,
	nom_vid VARCHAR(50) NOT NULL,
    des_vid VARCHAR(25) NOT NULL,
    con_vid VARCHAR(25) NOT NULL,
    cla_vid VARCHAR(50) NOT NULL,
    gen_vid VARCHAR(200) NOT NULL,
    pre_vid DECIMAL(10,2) NOT NULL,
    stock_vid INT NOT NULL,
    FOREIGN KEY (iddis_vid)
    REFERENCES distribuidora(id_dis)
);

CREATE TABLE venta(
	id_ven VARCHAR(40) NOT NULL PRIMARY KEY,
    idusu_ven SMALLINT NOT NULL,
    fec_ven DATE NOT NULL,
    tot_ven DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (idusu_ven)
    REFERENCES usuario(id_usu)
);

CREATE TABLE detallesventa(
	idvid_detven SMALLINT NOT NULL,
    idven_detven VARCHAR(40) NOT NULL,
    can_detven INT NOT NULL,
    tot_detven DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (idvid_detven)
    REFERENCES videojuego(id_vid),
    FOREIGN KEY (idven_detven)
    REFERENCES venta(id_ven)
);

CREATE TABLE compra(
	id_com VARCHAR(40) NOT NULL PRIMARY KEY,
    idusu_com  SMALLINT NOT NULL,
    iddis_com SMALLINT NOT NULL,
    fec_com DATE NOT NULL,
    tot_com DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (idusu_com)
    REFERENCES usuario(id_usu),
    FOREIGN KEY (iddis_com)
    REFERENCES distribuidora(id_dis)
);

CREATE TABLE detallescompra(
	idvid_detcom SMALLINT NOT NULL,
    idcom_detcom VARCHAR(40) NOT NULL,
    can_detcom INT NOT NULL,
    tot_detcom DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (idvid_detcom)
    REFERENCES videojuego(id_vid),
    FOREIGN KEY (idcom_detcom)
    REFERENCES compra(id_com)
);

CREATE TABLE corte(
	id_cor SMALLINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    idusu_cor SMALLINT NOT NULL,
    fec_cor DATE NOT NULL,
    mon_cor DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (idusu_cor)
    REFERENCES usuario(id_usu)
);

USE tienda_de_videojuegos;
INSERT INTO usuario (niv_usu, nom_usu, con_usu) VALUES
("Administrador","Cesar","1234"),
("Administrador","Victor","1234"),
("Administrador","Emilio ","1234"),
("Cajero","David","1234");

INSERT INTO distribuidora (id_dis,nom_dis, tel_dis,dir_dis,cue_dis) VALUES
(1,"Nintendo","001 (425) 558-7078","Japon","123456789"),
(2,"2K Games","9873-34321-2123","Australia","13214324235"),
(3,"Sierra Studios","892473245","Sierra","3424263131"),
(4,"Steam","33341413143","Estados Unidos","9284732745"),
(5,"Rockstar Games","424253522","Estados Unidos","3142534533"),
(6,"Dimas","81112044988","Perdon 119, Balcon del Norte","829830362492666"),
(7,"Ubisoft","8211209911","Rosa Garcia Noriega 515","12934590871090124189"),
(8,"Bandai Namco","9214388127","Helm Street 658","748233601248248446011"),
(9, "Bethesda", "81120114885","Calle Sovengard 624","Cuenta 857834362562861"),
(10,"Focus Interactive","8114568955","Calle Naranjo 324","8426990571654007368"),
(11,"Valve","8113332543","Calle Lambda 333","8281129381827113"),
(12,"Microsoft","8218643901","Calle Ebano 954","98827349472467281941"),
(13,"Sega","8215690317","Calle O'Campo 638","8271472174274792413"),
(14,"Activision","8110851743","Las Vegas","8279428426618643918"),
(15,"EA","8174017729","Calle Roma 521","4448297426273223"),
(16,"Capcom","817401772954","Calle Valdez 2423","444832974234237984233244");

/*Las consolas pueden ser PS3, PS4, XBOX 360, XBOX ONE, Nintendo Switch, Nintendo 3DS, Nintendo Wii U, y mas. (Se deben respetar los nombres y solo se debe tener una consola por registro) */
/*Los generos deben estar separados por una coma si es que tienen mas de uno.*/
/*Los generos pueden ser Lucha, Beat 'em up, Arcade, Plataformas, FPS, TPS, Shoot 'em up, Estrategia, Simulacion, Deporte, Carreras, Aventura, Aventura Grafica, Surival Horror, RPG, JRPG, Mundo Abierto y mas. (Se deben respetar los nombres)*/
/*Las clasificaciones pueden ser Early Childhood, Everyone, Everyone 10 and up/Everyone 10+, Teen, Mature 17+, Adults Only 18+. (Se deben respetar los nombres y solo se debe tener una clasificacion por registro)*/
INSERT INTO videojuego (id_vid, iddis_vid, nom_vid, des_vid, con_vid, cla_vid, gen_vid, pre_vid, stock_vid) VALUES
(1,1,"The Legend of Zelda: Breath of the Wild", "Nintendo", "Nintendo Switch", "Everyone 10 and up/Everyone 10+","Aventura,Mundo Abierto", 1235.00, 5),
(2,2,"Bioshock", "2K Games", "XBOX 360", "Mature 17+","FPS", 850.00, 5),
(3,6,"Piensa Frankie, Piensa Rivers The Game","Desarrolladoras Dimas","XBOX","Teen","FPS",300,5),
(4,9,"Fallout 3","Bethesda Works","PS3","Mature 17+","TSP,FPS,Mundo Abierto,RPG",400,5),
(5,9,"Fallout 3","Bethesda Works","XBOX","Mature 17+","TSP,FPS,Mundo Abierto,RPG",400,5),
(6,9,"DOOM","Id Software","XBOX ONE","Adults Only 18+","FPS",600,5),
(7,7,"Watch Dogs 2","Ubisoft","XBOX 360","Mature 17+","FPS",600,5),
(8,7,"Watch Dogs 2","Ubisoft","XBOX ONE","Mature 17+","FPS",600,5),
(9,7,"Watch Dogs 2","Ubisoft","PS4","Mature 17+","FPS",600,5),
(10,7,"Watch Dogs 2","Ubisoft","PC","Mature 17+","FPS",600,5),
(11,7,"Assasins Creed Valhalla","Ubisoft","XBOX ONE","Mature 17+","Accion,Aventura",1200,5),
(12,7,"Assasins Creed Valhalla","Ubisoft","PS4","Mature 17+","Accion,Aventura",1200,5),
(13,7,"Assasins Creed Valhalla","Ubisoft","PC","Mature 17+","Accion,Aventura",1200,5),
(14,9,"Skyrim","Bethesda Works","XBOX 360","Mature 17+","RPG,Aventura",499,5),
(15,9,"Skyrim","Bethesda Works","XBOX ONE","Mature 17+","RPG,Aventura",499,5),
(16,9,"Skyrim","Bethesda Works","PS3","Mature 17+","RPG,Aventura",499,5),
(17,9,"Skyrim","Bethesda Works","PS4","Mature 17+","RPG,Aventura",499,5),
(18,7,"The Division 2","Ubisoft","XBOX 360","Mature 17+","Accion,FPS",600,5),
(19,7,"The Division 2","Ubisoft","XBOX ONE","Mature 17+","Accion,FPS",600,5),
(20,7,"The Division 2","Ubisoft","PS3","Mature 17+","Accion,FPS",600,5),
(21,7,"The Division 2","Ubisoft","PS4","Mature 17+","Accion,FPS",600,5),
(22,4,"Half Life","Valve","PC","Mature 17+","Accion,FPS",999,5),
(23,11,"Half Life 3","Valve","PC","Mature 17+","Accion,FPS",999,5),
(24,11,"Half Life 3","Valve","XBOX ONE","Mature 17+","Accion,FPS",999,5),
(25,7,"Raibown Six Siege","Ubisoft","XBOX ONE","Mature 17+","Accion,FPS",400,5),
(26,7,"Raibown Six Siege","Ubisoft","PC","Mature 17+","Accion,FPS",400,5),
(27,7,"Raibown Six Siege","Ubisoft","PS4","Mature 17+","Accion,FPS",400,5),
(28,11,"Portal 3","Valve","PC","Teen","Plataformas",1099,5),
(29,11,"Portal 3","Valve","Nintendo Switch","Teen","Plataformas",1099,5),
(30,8,"Divinity Original Sin 2","Larian","XBOX ONE","Mature +17","RPG",1200,5),
(31,8,"Divinity Original Sin 2","Larian","PS4","Mature +17","RPG",1200,5),
(32,8,"Dark Souls 3","From Software","XBOX ONE","Mature +17","Accion,RPG",800,5),
(33,11,"Left 4 Dead 3","Valve","PC","Mature +17","FPS",899,5);


