-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: danny_zapateria
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `compra`
--

DROP TABLE IF EXISTS `compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compra` (
  `folio_c` int NOT NULL,
  `fecha` date DEFAULT NULL,
  `prov` int DEFAULT NULL,
  `id_e` int DEFAULT NULL,
  PRIMARY KEY (`folio_c`),
  KEY `prov` (`prov`),
  KEY `id_e` (`id_e`),
  CONSTRAINT `compra_ibfk_1` FOREIGN KEY (`prov`) REFERENCES `proveedor` (`id_p`),
  CONSTRAINT `compra_ibfk_2` FOREIGN KEY (`id_e`) REFERENCES `empleado` (`id_e`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compra`
--

LOCK TABLES `compra` WRITE;
/*!40000 ALTER TABLE `compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `det_compra`
--

DROP TABLE IF EXISTS `det_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `det_compra` (
  `folio_c` int DEFAULT NULL,
  `codigo` int DEFAULT NULL,
  `cantidad` int DEFAULT NULL,
  `total` decimal(20,2) DEFAULT NULL,
  KEY `folio_c` (`folio_c`),
  KEY `codigo` (`codigo`),
  CONSTRAINT `det_compra_ibfk_1` FOREIGN KEY (`folio_c`) REFERENCES `compra` (`folio_c`),
  CONSTRAINT `det_compra_ibfk_2` FOREIGN KEY (`codigo`) REFERENCES `zapato` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `det_compra`
--

LOCK TABLES `det_compra` WRITE;
/*!40000 ALTER TABLE `det_compra` DISABLE KEYS */;
/*!40000 ALTER TABLE `det_compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_venta`
--

DROP TABLE IF EXISTS `detalle_venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_venta` (
  `folio_v` int DEFAULT NULL,
  `codigo` int DEFAULT NULL,
  `cantidad` int DEFAULT NULL,
  `total` int DEFAULT NULL,
  KEY `folio_v` (`folio_v`),
  KEY `codigo` (`codigo`),
  CONSTRAINT `detalle_venta_ibfk_1` FOREIGN KEY (`folio_v`) REFERENCES `venta` (`folio_v`),
  CONSTRAINT `detalle_venta_ibfk_2` FOREIGN KEY (`codigo`) REFERENCES `zapato` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_venta`
--

LOCK TABLES `detalle_venta` WRITE;
/*!40000 ALTER TABLE `detalle_venta` DISABLE KEYS */;
INSERT INTO `detalle_venta` VALUES (38051,8002,2,640),(38058,8002,2,640),(38059,8002,10,3200),(38059,8003,10,2000),(38060,8002,2,640),(38061,8002,2,640),(38062,8002,2,640),(38063,8002,2,640),(38063,8002,2,640),(38063,8003,4,800),(38064,8002,2,640),(38065,8002,2,640),(38066,8002,2,640),(38067,8002,2,640),(38068,8003,2,400),(38069,8003,2,400),(38070,9002,2,1000),(38070,8003,2,400),(38072,8002,2,640),(38073,8002,4,1280),(38074,8002,8,2560),(38076,8002,2,640),(38077,8002,2,640),(38077,8002,2,640),(38078,8002,1,320),(38079,8002,2,640);
/*!40000 ALTER TABLE `detalle_venta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleado`
--

DROP TABLE IF EXISTS `empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleado` (
  `id_e` int NOT NULL,
  `nombre` varchar(60) DEFAULT NULL,
  `apellidos` varchar(60) DEFAULT NULL,
  `fecha_ingreso` date DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `turno` varchar(15) DEFAULT NULL,
  `puesto` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_e`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleado`
--

LOCK TABLES `empleado` WRITE;
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
INSERT INTO `empleado` VALUES (1001,'Miguel Angel','Martinez Ramos','2022-08-07','2000-03-06','Matutino','Cajero'),(1002,'Yadira','Soto Jimenez','2021-09-07','2002-12-11','Matutino','Vendedor');
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventario`
--

DROP TABLE IF EXISTS `inventario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `codigo` int DEFAULT NULL,
  `id_talla` int DEFAULT NULL,
  `stock` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fkcod_idx` (`codigo`),
  KEY `fktalla_idx` (`id_talla`)
) ENGINE=InnoDB AUTO_INCREMENT=8005 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventario`
--

LOCK TABLES `inventario` WRITE;
/*!40000 ALTER TABLE `inventario` DISABLE KEYS */;
INSERT INTO `inventario` VALUES (1001,8002,130,10),(1003,8062,120,15),(1006,8045,120,10),(1007,8075,130,10),(1008,8075,140,7),(1009,8076,155,5),(1010,8002,140,8),(1011,8002,150,10),(1012,8002,160,8);
/*!40000 ALTER TABLE `inventario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedor` (
  `id_p` int NOT NULL,
  `nombre` varchar(40) DEFAULT NULL,
  `razon_social` varchar(40) DEFAULT NULL,
  `ciudad` varchar(20) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `e_mail` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id_p`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
INSERT INTO `proveedor` VALUES (1001,'Adidas México','adidas de México SA de CV.','México','919030052','Servicio@onlineshop.adidas.mx'),(1002,'Onena','Onena SA de CV.','México','5559753504','contacto@danzar.com.mx'),(1003,'Zuri Calzado','Zuri Calzado SA de C.V.','Puebla','2221242406','Ventas@zuricalzado.com'),(1004,'Lhesh','Lhesh','México','4791166178','lian@gmail.com'),(1005,'Flexi','Grupo Flexi de León, S.A.P.I. de C.V.','México','5568269455','somos@flexi.com.mx'),(1006,'My Shoes A',' My Shoes A México','México','5550780100',' myShoesmexico@gmail.com.mx'),(1007,'Multisafe ','Multisafe SA de C.V.','Monterrey','8141768700','adrianp@gmail.com'),(1008,'Nike','Nike Retail','México','913753366','servicenike@onlineshop.nike.mx'),(1009,'Borcegui','borcegui','puebla','2265432190','ventas@borcegui.com.mx'),(1010,'La norma','la horma italiana','oaxaca','9515153426','hormaitaliana@gmail.com'),(1011,'Cherokee','cherokee','puebla','2233087115','ventas@cherokee.com.mx'),(1012,'León','Zapatos de León','Guanajuato','891972342','zaplventaseon@gmail.com');
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `talla`
--

DROP TABLE IF EXISTS `talla`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `talla` (
  `id_talla` int NOT NULL,
  `talla` decimal(4,1) DEFAULT NULL,
  PRIMARY KEY (`id_talla`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `talla`
--

LOCK TABLES `talla` WRITE;
/*!40000 ALTER TABLE `talla` DISABLE KEYS */;
INSERT INTO `talla` VALUES (120,12.0),(125,12.5),(130,13.0),(135,13.5),(140,14.0),(145,14.5),(150,15.0),(155,15.5),(160,16.0),(165,16.5),(170,17.0),(175,17.5),(180,18.0),(185,18.5),(190,19.0),(195,19.5),(200,20.0),(205,20.5),(210,21.0),(215,21.5),(220,22.0),(225,22.5),(230,23.0),(235,23.5),(240,24.0),(245,24.5),(250,25.0),(255,25.5),(260,26.0),(265,26.5),(270,27.0),(275,27.5),(280,28.0),(285,28.5),(290,29.0),(295,29.5),(300,30.0);
/*!40000 ALTER TABLE `talla` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipos`
--

DROP TABLE IF EXISTS `tipos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipos` (
  `id_tipos` int NOT NULL,
  `tipo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_tipos`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipos`
--

LOCK TABLES `tipos` WRITE;
/*!40000 ALTER TABLE `tipos` DISABLE KEYS */;
INSERT INTO `tipos` VALUES (1,'Zapato'),(2,'Zapatilla'),(3,'Sandalia'),(4,'Bota'),(5,'Tenis'),(6,'Botin');
/*!40000 ALTER TABLE `tipos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id_usuarios` int NOT NULL,
  `id_e` int DEFAULT NULL,
  `correo` varchar(45) DEFAULT NULL,
  `nombreUsuario` varchar(45) DEFAULT NULL,
  `contraseña` varchar(45) DEFAULT NULL,
  `privilegio` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_usuarios`),
  KEY `fk_emp_idx` (`id_e`),
  CONSTRAINT `fk_emp` FOREIGN KEY (`id_e`) REFERENCES `empleado` (`id_e`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,1002,'yaya@gmail.com','Yadira','12345','Encargado de caja'),(2,1001,'migu782@gmail.com','migue','12345','Contador');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venta`
--

DROP TABLE IF EXISTS `venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venta` (
  `folio_v` int NOT NULL,
  `fecha` date DEFAULT NULL,
  `id_e` int DEFAULT NULL,
  PRIMARY KEY (`folio_v`),
  KEY `id_e` (`id_e`),
  CONSTRAINT `venta_ibfk_1` FOREIGN KEY (`id_e`) REFERENCES `empleado` (`id_e`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venta`
--

LOCK TABLES `venta` WRITE;
/*!40000 ALTER TABLE `venta` DISABLE KEYS */;
INSERT INTO `venta` VALUES (38001,'2020-11-01',1001),(38002,'2020-11-03',1001),(38003,'2020-11-04',1001),(38004,'2020-12-07',1001),(38005,'2020-12-12',1001),(38006,'2020-12-20',1001),(38007,'2020-02-21',1001),(38008,'2020-02-21',1001),(38009,'2020-02-12',1001),(38010,'2020-02-11',1001),(38011,'2020-03-14',1001),(38012,'2020-03-24',1001),(38013,'2020-03-25',1001),(38014,'2020-03-26',1001),(38015,'2021-03-28',1001),(38016,'2021-03-28',1001),(38017,'2021-03-23',1001),(38018,'2021-03-22',1001),(38019,'2021-03-20',1001),(38020,'2021-03-21',1001),(38021,'2021-04-21',1001),(38022,'2021-04-21',1001),(38023,'2021-04-21',1001),(38024,'2021-04-21',1001),(38025,'2021-04-02',1001),(38026,'2021-04-02',1001),(38027,'2021-04-21',1001),(38028,'2021-04-21',1001),(38029,'2021-04-21',1001),(38030,'2021-04-04',1001),(38031,'2021-05-21',1001),(38032,'2021-05-22',1001),(38033,'2021-05-09',1001),(38034,'2021-05-09',1001),(38035,'2022-05-22',1001),(38036,'2022-05-10',1001),(38037,'2022-05-11',1001),(38038,'2022-05-22',1001),(38039,'2022-05-11',1001),(38040,'2022-05-22',1001),(38041,'2022-06-23',1001),(38042,'2022-06-22',1001),(38043,'2022-06-24',1001),(38044,'2022-03-23',1001),(38045,'2022-04-25',1001),(38046,'2023-04-26',1001),(38047,'2023-04-26',1001),(38048,'2023-04-26',1001),(38049,'2023-05-23',1001),(38050,'2023-05-23',1001),(38051,'2023-06-05',1001),(38052,'2023-06-05',1001),(38053,'2023-06-05',1001),(38054,'2023-06-05',1001),(38055,'2023-06-05',1001),(38056,'2023-06-05',1001),(38057,'2023-06-05',1001),(38058,'2023-06-05',1001),(38059,'2023-06-05',1001),(38060,'2023-06-05',1001),(38061,'2023-06-05',1001),(38062,'2023-06-05',1001),(38063,'2023-06-05',1001),(38064,'2023-06-05',1001),(38065,'2023-06-05',1001),(38066,'2023-06-05',1001),(38067,'2023-06-05',1001),(38068,'2023-06-05',1001),(38069,'2023-06-05',1001),(38070,'2023-06-06',1001),(38071,'2023-06-06',1001),(38072,'2023-06-06',1001),(38073,'2023-06-06',1001),(38074,'2023-06-06',1001),(38075,'2023-06-06',1001),(38076,'2023-06-07',1001),(38077,'2023-06-07',1001),(38078,'2023-06-07',1001),(38079,'2023-06-08',1001);
/*!40000 ALTER TABLE `venta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zapato`
--

DROP TABLE IF EXISTS `zapato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `zapato` (
  `codigo` int NOT NULL,
  `descripcion` varchar(45) DEFAULT NULL,
  `prov` int DEFAULT NULL,
  `material` varchar(45) DEFAULT NULL,
  `precio_C` decimal(10,2) DEFAULT NULL,
  `precio_V` decimal(10,2) DEFAULT NULL,
  `tipo` int DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fkprov_idx` (`prov`),
  KEY `fktipo_idx` (`tipo`),
  CONSTRAINT `fkprov` FOREIGN KEY (`prov`) REFERENCES `proveedor` (`id_p`),
  CONSTRAINT `fktipo` FOREIGN KEY (`tipo`) REFERENCES `tipos` (`id_tipos`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zapato`
--

LOCK TABLES `zapato` WRITE;
/*!40000 ALTER TABLE `zapato` DISABLE KEYS */;
INSERT INTO `zapato` VALUES (8002,'sandalia primavera',1003,'sintetico',180.00,320.00,4),(8003,'zapato con agujeta para niña',1006,'piel',120.00,300.00,4),(8004,'sandalia para niña con estampado floral',1006,'piel',200.00,300.00,3),(8005,'bota corta',1003,'gamuza',350.00,600.00,4),(8007,'zapato con agujeta',1001,'piel',300.00,500.00,2),(8010,'zapato mocasin niño',1006,'gamuza',150.00,250.00,4),(8015,'sandalia para niña',1006,'sintetico',110.00,190.00,3),(8017,'tenis nike basquetbol',1009,'piel',700.00,1200.00,5),(8020,'tenis reebok soccer',1009,'piel y sintético',800.00,1400.00,5),(8022,'bota larga',1004,'piel',400.00,700.00,4),(8040,'tenis jourdan basquetbol',1009,'piel',800.00,1300.00,5),(8041,'zapatilla dorada',1003,'piel y sintético',500.00,800.00,2),(8045,'zapatilla con tacon no. 7',1001,'piel',200.00,430.00,2),(8050,'zapato',1005,'piel',250.00,800.00,2),(8060,'sandalia playa',1003,'sintetico',150.00,230.00,3),(8061,'zapato escolar con hebilla niño',1003,'piel',150.00,1000.00,4),(8062,'escolar niña',1009,'piel',150.00,200.00,1),(8063,'botin niño',1008,'gamuza',180.00,270.00,6),(8064,'botín niña',1009,'piel y sintetico',250.00,450.00,6),(8065,'zapatilla de plataforma',1009,'piel',350.00,500.00,2),(8066,'zapatilla de tacon no 5',1009,'sintetico',330.00,480.00,2),(8067,'sandalia niña',1010,'sintetico',80.00,120.00,3),(8068,'sandalia niño',1010,'sintetico',80.00,120.00,3),(8069,'sandalia baño dama',1010,'sintetido',100.00,180.00,3),(8070,'sandalia baño caballero',1001,'sintetico',100.00,180.00,3),(8071,'sandalia baño dama',1001,'sintetico',100.00,180.00,3),(8072,'botin dama hebilla',1011,'piel',300.00,600.00,6),(8073,'bota dama borrega',1011,'piel y sintetico',350.00,600.00,4),(8074,'bota caballero',1011,'gamuza',300.00,6000.00,4),(8075,'mocasin dama',1011,'piel',290.00,560.00,2),(8076,'mocasin dama',1011,'piel',300.00,670.00,2),(8077,'escolar niña',1011,'piel',200.00,450.00,1),(8078,'escolar niña flor',1007,'piel',210.00,460.00,5),(8079,'escolar niño',1011,'piel',210.00,460.00,1),(8088,'sandalia verano',1004,'sintetico',150.00,280.00,3),(9002,'bonita prueba 2',1005,'gamuza',400.00,500.00,3);
/*!40000 ALTER TABLE `zapato` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-08  9:41:22
