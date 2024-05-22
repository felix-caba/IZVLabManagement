-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 22, 2024 at 11:59 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quimica`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `MostrarCantidad` (IN `Nombres` VARCHAR(255))   BEGIN
    SELECT Cantidad
    FROM prod_aux
    WHERE Nombres = Nombre;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ObtenerNocivos` ()   BEGIN
    SELECT *
    FROM reactivos
    WHERE Riesgos = 'Nocivo';
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `reorganizar_columnas` (IN `tabla` VARCHAR(255))   BEGIN
    SET @stmt1 = CONCAT('ALTER TABLE `', tabla, '` MODIFY COLUMN id INT FIRST');
    SET @stmt2 = CONCAT('ALTER TABLE `', tabla, '` MODIFY COLUMN nombre VARCHAR(255) AFTER id');
    SET @stmt3 = CONCAT('ALTER TABLE `', tabla, '` MODIFY COLUMN localizacion VARCHAR(255) AFTER nombre');
    SET @stmt4 = CONCAT('ALTER TABLE `', tabla, '` MODIFY COLUMN ubicacion VARCHAR(255) AFTER localizacion');
    SET @stmt5 = CONCAT('ALTER TABLE `', tabla, '` MODIFY COLUMN cantidad INT AFTER ubicacion');
    
   

    PREPARE execute_stmt1 FROM @stmt1;
    EXECUTE execute_stmt1;
    DEALLOCATE PREPARE execute_stmt1;

    PREPARE execute_stmt2 FROM @stmt2;
    EXECUTE execute_stmt2;
    DEALLOCATE PREPARE execute_stmt2;

    PREPARE execute_stmt3 FROM @stmt3;
    EXECUTE execute_stmt3;
    DEALLOCATE PREPARE execute_stmt3;

    PREPARE execute_stmt4 FROM @stmt4;
    EXECUTE execute_stmt4;
    DEALLOCATE PREPARE execute_stmt4;

    PREPARE execute_stmt5 FROM @stmt5;
    EXECUTE execute_stmt5;
    DEALLOCATE PREPARE execute_stmt5;
END$$

--
-- Functions
--
CREATE DEFINER=`root`@`localhost` FUNCTION `FuncionMostrarCantidades` (`cantParam` INT) RETURNS INT(11)  BEGIN
DECLARE total int UNSIGNED;
SET total = (SELECT COUNT(*) FROM reactivos WHERE Cantidad >= cantParam);
RETURN total;
END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `FuncionMostrarCantidadPorBalda` (`numeroUbicacion` VARCHAR(5)) RETURNS INT(11)  BEGIN
DECLARE total int UNSIGNED;
SET total = (SELECT COUNT(*) FROM reactivos WHERE Ubicacion = numeroUbicacion);
RETURN total;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `auxiliares`
--

CREATE TABLE `auxiliares` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `localizacion` varchar(255) DEFAULT NULL,
  `ubicacion` varchar(255) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `Formato` varchar(31) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `auxiliares`
--

INSERT INTO `auxiliares` (`id`, `nombre`, `localizacion`, `ubicacion`, `cantidad`, `Formato`) VALUES
(1, 'Bandejas ', 'Almacén general ', 'Estantería 0 balda 4', 4, 'Plástico verdes medianas'),
(2, 'Vaso medidor ', 'Almacén general ', 'Estantería 0  balda 4', 2, 'Plástico'),
(3, 'Botellas con cierre manual', 'Almacén general ', 'Estantería 0  balda 4', 2, 'Cristal'),
(4, 'Exprimidor ', 'Almacén general ', 'Estantería 0 balda 4', 1, 'Plástico '),
(5, 'Vasos variados ', 'Almacén general ', 'Estantería 0 balda 4', 10, 'Plástico '),
(6, 'Coladores ', 'Almacén general ', 'Estantería 0 balda 4', 3, 'Plástico metal tela'),
(7, 'Ladrillos', 'Almacén general ', 'Estantería 0 balda 4', 4, 'De cerámica 6 cuadrados enteros');

--
-- Triggers `auxiliares`
--
DELIMITER $$
CREATE TRIGGER `PonerFormatoMayusculas` BEFORE UPDATE ON `auxiliares` FOR EACH ROW SET NEW.Formato = UCASE(NEW.Formato)
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `localizacion`
--

CREATE TABLE `localizacion` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `localizacion`
--

INSERT INTO `localizacion` (`id`, `nombre`) VALUES
(1, 'Almacén Principal'),
(2, 'Laboratorio 2'),
(3, 'Almacen Auxiliar');

-- --------------------------------------------------------

--
-- Table structure for table `materiales`
--

CREATE TABLE `materiales` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `localizacion` varchar(255) DEFAULT NULL,
  `ubicacion` varchar(255) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `Subcategoria` varchar(24) DEFAULT NULL,
  `Descripcion` varchar(8) DEFAULT NULL,
  `StockMinimo` varchar(10) DEFAULT NULL,
  `Nserie` varchar(50) DEFAULT NULL,
  `FechaDeCompra` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `materiales`
--

INSERT INTO `materiales` (`id`, `nombre`, `localizacion`, `ubicacion`, `cantidad`, `Subcategoria`, `Descripcion`, `StockMinimo`, `Nserie`, `FechaDeCompra`) VALUES
(1, 'Vasos de precipitados', 'Almacén General', 'estantería 1, balda 3', 2, 'plástico ', '1000 ml', '', '', ''),
(2, 'Vasos de precipitados ', 'Almacén General', 'estantería 1, balda 3', 2, 'plástico ', '500ml', '', '', ''),
(3, 'Vaso de precipitados ', 'Almacén General', 'estantería 1, balda 3', 1, 'plástico ', '1000 ml ', '', '', ''),
(4, 'Matraz erlenmeyer grande', 'Almacén General', 'estantería 1, balda 3', 1, 'cristal', '3 L', '', '', ''),
(5, 'Matraz erlenmeyer con tapón de cristal', 'Almacén General', 'estantería 1, balda 3', 0, 'cristal ', '1000 ml', '', '', ''),
(6, 'Matraz erlenmeyer ', 'Almacén General', 'estantería 1, balda 3', 0, 'cristal', '2L ', '', '', ''),
(7, 'Matraz erlenmeyer ', 'Almacén General', 'estantería 1,balda 3', 0, 'cristal ', '1000 ml', '', '', ''),
(8, 'Recipiente aséptico ', 'Almacén General', 'estantería 1,balda 4', 0, 'plástico ', '2L ', '', '', ''),
(9, 'Tubos de ensayo graduados ', 'Almacén General', 'estantería 1,balda 4', 0, 'cristal', '50 ml', '', '', ''),
(10, 'Conductímetro', 'Laboratorio Instrumental', 'C1', 1, 'Instrumental Electrónico', '', '', '534031', ''),
(11, 'Conductímetro', 'Laboratorio Instrumental', 'C2', 1, 'Instrumental Electrónico', '', '', '531030', ''),
(12, 'Potenciómetro', 'Laboratorio Instrumental', 'P1', 1, 'Instrumental Electrónico', '', '', '539010', ''),
(13, 'Potenciómetro', 'Laboratorio Instrumental', 'P2', 1, 'Instrumental Electrónico', '', '', '539007', '');

--
-- Triggers `materiales`
--
DELIMITER $$
CREATE TRIGGER `Sustituir caracteres` BEFORE UPDATE ON `materiales` FOR EACH ROW SET NEW.Localizacion := REPLACE(NEW.Localizacion, '?', 'é')
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `reactivos`
--

CREATE TABLE `reactivos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `localizacion` varchar(255) DEFAULT NULL,
  `ubicacion` varchar(255) DEFAULT NULL,
  `cantidad` int(11) DEFAULT NULL,
  `Formato` varchar(20) DEFAULT NULL,
  `Riesgos` varchar(100) DEFAULT NULL,
  `GradoPureza` varchar(40) DEFAULT NULL,
  `FechaCaducidad` varchar(230) DEFAULT NULL,
  `StockMinimo` int(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Dumping data for table `reactivos`
--

INSERT INTO `reactivos` (`id`, `nombre`, `localizacion`, `ubicacion`, `cantidad`, `Formato`, `Riesgos`, `GradoPureza`, `FechaCaducidad`, `StockMinimo`) VALUES
(1, 'AMONIO NITRATO', 'Almacén principal', '5N', 1, '1 Kg', 'Comburente y irritante ', 'No viene reflejado', 'No viene reflejada', 0),
(2, 'BROMURO DE AMONIO (PARA ANÁLISIS)', 'Almacén principal', '5N', 2, '1 Kg', 'Toxicidad aguda (6)', '99 %', 'No viene reflejada', 0),
(3, 'AZUFRE SUBLIMADO (AZUFRE FLOR)', 'Almacén principal', '5N', 2, '1 Kg', 'Toxicidad aguda (6)', 'Puro', 'No viene reflejada', 0),
(4, 'ALUMINIO NITRATO 9-HIDRATO', 'Almacén principal', '5N', 2, '1 Kg', 'Comburente y nocivo', '98 %', 'No viene reflejada', 0),
(5, 'ACETANILIDA CRISTALIZADA', 'Almacén principal', '5N', 1, '1 Kg', 'Atención', 'Purísima', 'No viene reflejada', 0),
(6, 'AMINOMETANO', 'Almacén principal', '5N', 1, '100 g', 'Atención', '99 %', 'No viene reflejada', 0),
(7, 'ESTAÑO II CLORURO', 'Almacén principal', '5N', 2, '250 g', 'Nocivo', '99 %', 'No viene reflejada', 0),
(8, 'ÁCIDO TARTÁRICO', 'Almacén principal', '5N', 2, '500 g', 'Irritante', '99 %', 'No viene reflejada', 0),
(9, 'ÁCIDO TARTÁRICO', 'Almacén principal', '5N', 1, '1 Kg', 'Irritante', '99,7 %', 'No viene reflejada', 0),
(10, 'BIPIRIDINA', 'Almacén principal', '5N', 1, '5 g', 'Toxicidad aguda', 'No viene reflejado', 'No viene reflejada', 0),
(11, 'HIDROXIQUINOLEINA', 'Almacén principal', '5N', 1, '100 g', 'Atención', '98 %', 'No viene reflejada', 0),
(12, 'HIDROXIQUINOLEINA', 'Almacén principal', '5N', 1, '500 g', 'Atención', '99 %', 'No viene reflejada', 0),
(13, 'CELITE 545', 'Almacén principal', '5N', 1, 'No viene reflejado', 'Nocivo', 'No viene reflejado', 'No viene reflejada', 0),
(14, 'REACTIVO DE BENEDICT', 'Almacén principal', '5N', 1, '1 L', 'Nocivo', 'No viene reflejado', 'No viene reflejada', 0),
(15, 'REACTIVO FEHLING-A', 'Almacén principal', '5N', 1, '500 mL', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(16, 'REACTIVO FEHLING-B', 'Almacén principal', '5N', 1, '500 mL', 'Corrosivo', 'No viene reflejado', 'No viene reflejada', 0),
(17, 'UREA', 'Almacén principal', '5N', 1, 'No viene reflejado', 'Atención', '99 %', 'No viene reflejada', 0),
(18, 'AMONIO MOLIBDATO 4-HIDRATO', 'Almacén principal', '4N', 1, '250 g', 'Atención', '99 %', 'No viene reflejada', 0),
(19, 'AMONIO OXALATO 1-HIDRATO', 'Almacén principal', '4N', 1, '500 g', 'Nocivo', '99,5 %', '11-2012', 0),
(20, 'AMONIO OXALATO 1-HIDRATO', 'Almacén principal', '4N', 1, '1 Kg', 'Nocivo', '99 %', '7-2016', 0),
(21, 'PERSULFATO AMÓNICO', 'Almacén principal', '4N', 1, 'No viene reflejado', 'Atención', '98 %', 'No viene reflejada', 0),
(22, 'AMÓNIO TIOCIANATO', 'Almacén principal', '4N', 1, '1 Kg', 'Nocivo', '99 %', '1-3-2016', 0),
(23, 'AMÓNIO TIOCIANATO', 'Almacén principal', '4N', 1, '1 Kg', 'Nocivo', '99 %', '9-2010', 0),
(24, 'AMONIO META-VANADATO', 'Almacén principal', '4N', 1, '250 g', 'Nocivo', '99 %', 'No viene reflejada', 0),
(25, 'CADMIO NITRATO 4-HIDRATO', 'Almacén principal', '4N', 1, '250 g', 'Nocivo y peligroso para el medio ambiente', '98 %', 'No viene reflejada', 0),
(26, 'COBALTO II NITRATO 6-HIDRATO', 'Almacén principal', '4N', 1, '250 g', 'Comburente y nocivo', '98 %', 'No viene reflejada', 0),
(27, 'COBRE (II) NITRATO 3-HIDRATO', 'Almacén principal', '4N', 1, '500 g', 'Comburente, corrosivo y peligroso para el medio ambiente', '99 %', 'No viene reflejada', 0),
(28, 'ÓXIDO DE COBRE (II)', 'Almacén principal', '4N', 1, '500 g', 'Toxicidad aguda', '95 %', 'No viene reflejada', 0),
(29, 'COBRE (II) SULFATO 5-HIDRATO', 'Almacén principal', '4N', 1, '1 Kg', 'Nocivo, peligroso para el medio ambiente y corrosivo', '99 %', 'No viene reflejada', 0),
(30, 'COBRE (II) SULFATO 5-HIDRATO', 'Almacén principal', '4N', 1, 'No viene reflejado', 'Nocivo, peligroso para el medio ambiente y corrosivo', 'No viene reflejado', 'No viene reflejada', 0),
(31, 'ESTRONCIO NITRATO ANHIDRO', 'Almacén principal', '4N', 1, '1 Kg', 'Comburente', '98 %', 'No viene reflejada', 0),
(32, 'HIERRO (III) CLORURO 6-HIDRATO', 'Almacén principal', '4N', 2, '1 Kg', 'Nocivo', '98 %', 'No viene reflejada', 0),
(33, 'HIERRO (III) NITRATO 9-HIDRATO', 'Almacén principal', '4N', 1, '1 Kg', 'Comburente y irritante', '98 %', '10-2015', 0),
(34, 'HIERRO (II) SULFATO ~ 2-HIDRATO', 'Almacén principal', '4N', 2, '1 Kg', 'Atención', '80 %', 'No viene reflejada', 0),
(35, 'HIERRO (II) SULFATO 7-HIDRATO', 'Almacén principal', '4N', 1, '1 Kg', 'Nocivo', '99,5 %', '2-2015', 0),
(36, 'HIERRO (II) SULFATO 7-HIDRATO', 'Almacén principal', '4N', 1, '1 Kg', 'Nocivo', '98 %', 'No viene reflejada', 0),
(37, 'LANTANO (III) CLORURO 7-HIDRATO', 'Almacén principal', '4N', 1, '250 g', 'Atención', '98 %', 'No viene reflejada.', 0),
(38, 'MANGANESO (IV) ÓXIDO', 'Almacén principal', '4N', 1, '500 g', 'Nocivo', '85 %', 'No viene reflejada.', 0),
(39, 'SULFATO DE MANGANESO 1-HIDRATO', 'Almacén principal', '4N', 1, '1 Kg', 'Atención', 'Purísima', 'No viene reflejada.', 0),
(40, 'MANGANESO (II) SULFATO 1-HIDRATO', 'Almacén principal', '4N', 1, '1 Kg', 'Atención', '99 %', 'No viene reflejada', 0),
(41, 'CARBONATO DE NÍQUEL', 'Almacén principal', '4N', 1, 'No viene reflejado', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(42, 'NÍQUEL (II) NITRATO 6-HIDRATO PRS', 'Almacén principal', '4N', 1, '1 Kg', 'Nocivo, comburente, corrosivo, peligroso para el medio ambiente y carcinogeno', '98 %', '8-2019', 0),
(43, 'PLOMO (II) ACETATO 3-HIDRATO', 'Almacén principal', '4N', 1, '1 Kg', 'Nocivo', '99 %', 'No viene reflejada', 0),
(44, 'ZINC NITRATO 6-HIDRATO', 'Almacén principal', '4N', 1, '500 g', 'Nocivo y comburente', '98 %', 'No viene reflejada', 0),
(45, 'ÓXIDO DE ZINC', 'Almacén principal', '4N', 1, 'No viene reflejado', 'Atención', '98 %', 'No viene reflejada', 0),
(46, 'SULFATO DE ZINC 7-HIDRATO', 'Almacén principal', '4N', 1, 'No viene reflejado', 'Atención', 'Puro', 'No viene reflejada', 0),
(47, 'ZINC SULFATO 1-HIDRATO', 'Almacén principal', '4N', 1, '1 Kg', 'Comburente y peligroso para el medio ambiente', '98 %', 'No viene reflejada', 0),
(48, 'SODIO CARBONATO 10-HIDRATO', 'Almacén principal', '3N', 4, '1 Kg', 'Irritante', '99 %', 'No viene reflejada', 0),
(49, 'SODIO CARBONATO ANHIDRO', 'Almacén principal', '3N', 1, '100 g', 'Irritante', 'Puro', 'No viene reflejada', 0),
(50, 'SODIO CARBONATO ANHIDRO', 'Almacén principal', '3N', 1, '1 Kg', 'Irritante', 'Puro', 'No viene reflejada', 0),
(51, 'SODIO OXALATO', 'Almacén principal', '3N', 1, '1 Kg', 'Nocivo', '98 %', 'No viene reflejada', 0),
(52, 'SODIO SALICILATO', 'Almacén principal', '3N', 1, '250 g', 'Nocivo', '99,5 %', 'No viene reflejada', 0),
(53, 'SODIO SILICATO', 'Almacén principal', '3N', 1, '1 L', 'Irritante', 'No viene reflejado', '7-2011', 0),
(54, 'SODIO TUNGSTATO 2-HIDRATO', 'Almacén principal', '3N', 2, '250 g', 'Nocivo', '99 %', 'No viene reflejada', 0),
(55, 'POTASIO DE BROMURO', 'Almacén principal', '3N', 1, '500 g', 'Atención', '98 %', 'No viene reflejada', 0),
(56, 'POTASIO DE BROMURO', 'Almacén principal', '3N', 1, '500 g', 'Atención', '99 %', 'No viene reflejada', 0),
(57, 'POTASIO DE BROMURO', 'Almacén principal', '3N', 1, '250 g', 'Atención', 'Puro', 'No viene reflejada', 0),
(58, 'POTASIO DE BROMURO', 'Almacén principal', '3N', 1, '250 g', 'Atención', '98 %', 'No viene reflejada', 0),
(59, 'POTASIO CARBONATO PRS', 'Almacén principal', '3N', 2, '1 Kg', 'Nocivo', '99 %', '5-2009 y 11-2012', 0),
(60, 'POTASIO CLORATO', 'Almacén principal', '3N', 1, 'No viene reflejado', 'Comburente y nocivo', '98,5 %', 'No viene reflejada', 0),
(61, 'OXALATO DE POTASIO 1-HIDRATO', 'Almacén principal', '3N', 2, '500 g', 'Toxicidad aguda', '99 %', 'No viene reflejada', 0),
(62, 'POTASIO OXALATO', 'Almacén principal', '3N', 1, '1 Kg', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(63, 'POTASIO BIOXALATO', 'Almacén principal', '3N', 1, '500 g', 'Nocivo', '99 %', 'No viene reflejada', 0),
(64, 'POTASIO BIOXALATO', 'Almacén principal', '3N', 1, '1 Kg', 'Nocivo', '99 %', 'No viene reflejada', 0),
(65, 'PERMANGANATO DE POTASIO', 'Almacén principal', '3N', 2, '250 g', 'Comburente, nocivo y peligroso para el medio ambiente', 'No viene reflejado', '1-2-2022 y no viene reflejada', 0),
(66, 'PERMANGANATO DE POTASIO', 'Almacén principal', '3N', 1, '100 g', 'Comburente, nocivo y peligroso para el medio ambiente', 'No viene reflejado', '31-5-2015', 0),
(67, 'PERMANGANATO DE POTASIO', 'Almacén principal', '3N', 1, '1 Kg', 'Comburente, nocivo y peligroso para el medio ambiente', '99 %', '6-2028', 0),
(68, 'PERMANGANATO DE POTASIO', 'Almacén principal', '3N', 1, 'No viene reflejado', 'Comburente, nocivo y peligroso para el medio ambiente', 'Puro', 'No viene reflejada', 0),
(69, 'POTASIO TIOCIANATO', 'Almacén principal', '3N', 2, '500 g', 'Nocivo', '99 %', 'No viene reflejada', 0),
(70, 'POTASIO TIOCIANATO', 'Almacén principal', '3N', 1, '500 g', 'Nocivo', '98 %', 'No viene reflejada', 0),
(71, 'BARIO ACETATO', 'Almacén principal', '3N', 1, '500 g', 'Nocivo', '98 %', 'No viene reflejada', 0),
(72, 'BARIO HIDRÓXIDO 8-HIDRATO', 'Almacén principal', '3N', 1, '500 g', 'Nocivo', 'Puro', 'No viene reflejada', 0),
(73, 'BARIO HIDRÓXIDO 8-HIDRATO', 'Almacén principal', '3N', 1, '1 Kg', 'Nocivo', '98 %', 'No viene reflejada', 0),
(74, 'CALCIO CLORURO ANHIDRO QP', 'Almacén principal', '3N', 1, '1 Kg', 'Irritante', '95 %', '3-2015', 0),
(75, 'CALCIO CLORURO 2-HIDRATO PRS', 'Almacén principal', '3N', 1, '5 Kg', 'Irritante', '99 %', '10-2012', 0),
(76, 'CALCIO OXOLATO-1-HIDRATO', 'Almacén principal', '3N', 1, '500 g', 'Nocivo', '98 %', '10/2009', 0),
(77, 'CALCIO HIDROXISO', 'Almacén principal', '3N', 1, '1 Kg', 'Irritante', '95 %', '5/2009', 0),
(78, 'HIDROXIHAMONIO CLORURO', 'Almacén principal', '3N', 1, '250 g', 'Corrosivo, peligroso para el medio ambiente y cancerigeno', '99,55 %', 'No viene reflejada', 0),
(79, 'AMONIO CLORURO', 'Almacén principal', '3N', 1, '1 Kg', 'Atención', '100 %', 'No viene reflejada', 0),
(80, 'ÁCIDO BENZOICO', 'Almacén principal', '2N', 1, '500 g', 'Atención', '99,5 %', 'No viene reflejada', 0),
(81, 'ÁCIDO BENZOICO', 'Almacén principal', '2N', 1, '1 Kg', 'Nocivo', '99,5 %', '9-2016', 0),
(82, '4-DICLOROBENCENO', 'Almacén principal', '2N', 1, '500 g', 'Toxicidad aguda', 'Puro', 'No viene reflejada', 0),
(83, 'ÁCIDO 2-CLOROBENZOICO', 'Almacén principal', '2N', 1, '500 g', 'Atención', '99 %', 'No viene reflejada', 0),
(84, 'ÁCIDO 4-CLOROBENZOICO', 'Almacén principal', '2N', 1, '250 g', 'Nocivo', '97 %', 'No viene reflejada', 0),
(85, 'ÁCIDO 4-AMINOBENZOICO', 'Almacén principal', '2N', 1, '250 g', 'Atención', '99 %', '31-7-2007', 0),
(86, 'ÁCIDO 4-NITROBENZOICO', 'Almacén principal', '2N', 1, 'No viene reflejado', 'Nocivo', '99 %', 'No viene reflejada', 0),
(87, 'AMONÍACO', 'Almacén principal', '2N', 1, '1 L', 'Corrosivo, peligroso para el medio ambiente, nocivo y irritante', '30 %', '2-2029', 0),
(88, 'ISOBUTANOL', 'Almacén principal', '2N', 1, '1 L', 'Irritante', '99 %', '6-2010', 0),
(89, '2-BUTANOL', 'Almacén principal', '2N', 1, '1 L', 'Irritante', '99 %', '10-11-2004', 0),
(90, '3-METIL-1-BUTANOL SEGÚN GERBER', 'Almacén principal', '2N', 3, '1 L', 'Nocivo', '98,5 %', 'No viene reflejada', 0),
(91, 'CLORAMINA T', 'Almacén principal', '2N', 1, '250 g', 'Irritante', '98 %', 'No viene reflejada', 0),
(92, 'CLORAMINA T TRIHIDRATADA', 'Almacén principal', '2N', 1, '250 g', 'Corrosivo', '99 %', 'No viene reflejada', 0),
(93, 'DIMETILGLIOXIMA', 'Almacén principal', '2N', 1, '500 g', 'Nocivo', '99 %', '10-2013', 0),
(94, 'ÁCIDO 3,5-DINITROSALICÍLICO PS', 'Almacén principal', '2N', 1, '100 g', 'Nocivo y irritante', '99 %', '10-2017', 0),
(95, 'ETILENGLICOL', 'Almacén principal', '2N', 1, '1 L', 'Nocivo', '99 %', 'No viene reflejada', 0),
(96, 'AMONIO FORMATO', 'Almacén principal', '2N', 1, '1 Kg', 'Atención', '99 %', 'No viene reflejada', 0),
(97, 'CICLOHEXANOL', 'Almacén principal', '2N', 1, 'No viene reflejado', 'Atención', 'Purísimo', 'No viene reflejada', 0),
(98, 'DICLOROMETANO', 'Almacén principal', '2N', 1, '1 L', 'Nocivo, irritante y carcinogeno', 'No viene reflejado', '1-2025', 0),
(99, 'NAFTALENO PRS', 'Almacén principal', '2N', 1, '500 g', 'Nocivo y peligroso para el medio ambiente', '98 %', '12-2011', 0),
(100, 'NAFTALENO PRS', 'Almacén principal', '2N', 1, '500 g', 'Nocivo y peligroso para el medio ambiente', '98 %', '9-2010', 0),
(101, 'NAFTALENO', 'Almacén principal', '2N', 1, 'No viene reflejado', 'Inflamable', 'Purísimo', 'No viene reflejada', 0),
(102, '1-NAFTILAMINA PS', 'Almacén principal', '2N', 1, '100 g', 'Nocivo y peligroso para el medio ambiente', '99 %', 'No viene reflejada', 0),
(103, '1-NAFTOL', 'Almacén principal', '2N', 1, '250 g', 'Nocivo', '99 %', 'No viene reflejada', 0),
(104, 'p-NITROFENOL', 'Almacén principal', '2N', 1, '100 g', 'Nocivo', '96 %', 'No viene reflejada', 0),
(105, 'p-NITROFENOL', 'Almacén principal', '2N', 1, '250 g', 'Nocivo', '96 %', 'No viene reflejada', 0),
(106, 'ÁCIDO OXÁLICO 2-HIDRATO', 'Almacén principal', '2N', 2, '500 g', 'Nocivo', '99 %', 'No viene reflejada', 0),
(107, 'ÁCIDO OXÁLICO', 'Almacén principal', '2N', 1, '1 L', 'Atención', 'No viene reflejado', '10-2011', 0),
(108, 'ÁCIDO OXÁLICO CRISTALIZADO', 'Almacén principal', '2N', 1, 'No viene reflejado', 'Toxicidad aguda', 'Purísimo', 'No viene reflejada', 0),
(109, 'ÁCIDO SULFANÍLICO', 'Almacén principal', '2N', 2, '250 g', 'Nocivo', '98 %', 'No viene reflejada', 0),
(110, 'TRITÓN X', 'Almacén principal', '2N', 1, 'No viene reflejado', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(111, 'ÁCIDO ACÉTICO GLACIAL', 'Almacén principal', '1CA', 1, '1 L', 'Inflamable y corrosivo', '99,7 %', '7-2028', 0),
(112, 'ÁCIDO ACÉTICO GLACIAL', 'Almacén principal', '1CA', 1, '1 L', 'Inflamable y corrosivo', '99,9 %', 'No viene reflejada', 0),
(113, 'ÁCIDO ACÉTICO GLACIAL', 'Almacén principal', '1CA', 1, '2,5 L', 'Inflamable y corrosivo', '99,9 %', 'No viene reflejada', 0),
(114, 'ÁCIDO ACÉTICO GLACIAL', 'Almacén principal', '1CA', 1, '250 mL', 'Corrosivo', '99,5 %', '7-2014', 0),
(115, 'ÁCIDO ACÉTICO', 'Almacén principal', '1CA', 1, '1 L', 'Toxicidad aguda y corrosivo', '99,7 %', 'No viene reflejada', 0),
(116, 'ANHÍDRICO ACÉTICO', 'Almacén principal', '1CA', 1, '1 L', 'Corrosivo', '98 %', 'No viene reflejada', 0),
(117, 'ÁCIDO CLORHÍDRICO', 'Almacén principal', '1CA', 4, '2,5 L', 'Corrosivo, nocivo y irritante', '37 %', '5-6-2029', 0),
(118, 'ÁCIDO CLORHÍDRICO', 'Almacén principal', '1CA', 3, '1 L', 'Corrosivo, nocivo y irritante', '37 %', '2-2028', 0),
(119, 'ÁCIDO CLORHÍDRICO', 'Almacén principal', '1CA', 1, '1 L', 'Corrosivo, nocivo y irritante', '37 %', '2-11-2013', 0),
(120, 'ÁCIDO PERCLÓRICO EN ÁCIDO ACÉTICO', 'Almacén principal', '1CA', 2, '1 L', 'Inflamable y corrosivo', 'No viene reflejado', '1-2030', 0),
(121, 'ÁCIDO PERCLÓRICO', 'Almacén principal', '1CA', 1, '1 L', 'Comburente y corrosivo', '60 %', '6-2010', 0),
(122, 'ÁCIDO ORTOFOSFÓRICO', 'Almacén principal', '1CA', 1, '1 L', 'Corrosivo', '85 %', '9-2014', 0),
(123, 'ÁCIDO ORTOFOSFÓRICO', 'Almacén principal', '1CA', 1, '1 L', 'Corrosivo', '85 %', '12-2008', 0),
(124, 'ÁCIDO NÍTRICO', 'Almacén principal', '1CA', 2, '1 L', 'Comburente, toxicidad aguda y corrosivo', '65 %', '11-2024', 0),
(125, 'ÁCIDO NÍTRICO', 'Almacén principal', '1CA', 1, '1 L', 'Comburente, toxicidad aguda y corrosivo', '65 %', '8-2024', 0),
(126, 'ÁCIDO NÍTRICO', 'Almacén principal', '1CA', 3, '1 L', 'Corrosivo', '70 %', 'No viene reflejada', 0),
(127, 'ÁCIDO SULFÚRICO', 'Almacén principal', '1CA', 3, 'No viene reflejado', 'Atención', '96 %', 'No viene reflejada', 0),
(128, 'ÁCIDO SULFÚRICO', 'Almacén principal', '1CA', 1, '1 L', 'Corrosivo', '98 %', '3-2025', 0),
(129, 'ÁCIDO FLUORHÍDRICO', 'Almacén principal', '1CA', 1, '250 mL', 'Toxicidad aguda y corrosivo.', 'No viene reflejado', 'No viene reflejada', 0),
(130, 'ÁCIDO FÓRMICO', 'Almacén principal', '1CA', 1, '500 mL', 'Corrosivo', '95 %', 'No viene reflejada', 0),
(131, 'ÁCIDO ORTO-FOSFÓRICO', 'Almacén principal', '1CA', 1, '1 L', 'Corrosivo', '85 %', '12-2008', 0),
(132, 'REACTIVO DE HANUS', 'Almacén principal', '1CA', 3, '1 L', 'Corrosivo', 'No viene reflejado', 'No viene reflejada', 0),
(133, 'REACTIVO DE HANUS', 'Almacén principal', '1CA', 1, '1 L', 'Corrosivo', 'No viene reflejado', '11-2012', 0),
(134, 'DIETANOLAMINA', 'Almacén principal', '1CA', 1, '100 mL', 'Atención', '99 %', 'No viene reflejada', 0),
(135, 'CLOROACETILO CLORURO RPE', 'Almacén principal', '1CA', 1, '100 mL', 'Corrosivo', 'Puro', 'No viene reflejada', 0),
(136, 'HEXAMETILENODIAMINA', 'Almacén principal', '1CA', 1, '500 g', 'Nocivo', 'Puro', 'No viene reflejada', 0),
(137, 'HEXAMETILENODIAMINA', 'Almacén principal', '1CA', 1, '250 mL', 'Corrosivo y toxicidad aguda', '98 %', 'No viene reflejada', 0),
(138, 'HEXAMETILENODIAMINA', 'Almacén principal', '1CA', 2, '250 g', 'Atención', '98 %', 'No viene reflejada', 0),
(139, 'HEXAMETILENODIAMINA', 'Almacén principal', '1CA', 1, '250 g', 'Atención', 'Puro', 'No viene reflejada', 0),
(140, 'AMONIO ACETATO', 'Almacén principal', '6l', 1, '500 g', 'Atención', '98 %', 'No viene reflejada', 0),
(141, 'AMONIO ACETATO', 'Almacén principal', '6l', 1, '250 g', 'Atención', '98 %', '30-8-2008', 0),
(142, 'AMONIO ACETATO', 'Almacén principal', '6l', 1, '500 g', 'Atención', '96 %', '11-2011', 0),
(143, 'AMONIO CARBONATO', 'Almacén principal', '6l', 2, '1 Kg', 'Atención', '30-34 %', 'No viene reflejada', 0),
(144, 'CITRATO DE AMONIO di-BÁSICO', 'Almacén principal', '6l', 1, 'No viene reflejado', 'Atención', '98 %', 'No viene reflejada', 0),
(145, 'AMONIO HIERRO (III) CITRATO', 'Almacén principal', '6l', 1, '1 Kg', 'Atención', '16,5-18,5 %', '3-2012', 0),
(146, 'AMONIO HIERRO (III) CITRATO', 'Almacén principal', '6l', 1, '1 Kg', 'Atención', '16,5-18,5 %', 'No viene reflejada', 0),
(147, 'AMONIO DIHIDRÓGENO FOSFATO', 'Almacén principal', '6l', 1, '500 g', 'Nocivo y irritante', '98 %', '13-1-2021', 0),
(148, 'AMONIO DIHIDRÓGENO FOSFATO', 'Almacén principal', '6l', 1, '500 g', 'Atención', '100,1 %', '30-9-2027', 0),
(149, 'AMONIO SULFATO', 'Almacén principal', '6l', 1, '50 g', 'Atención', '99,9999 %', 'No viene reflejada', 0),
(150, 'SULFATO DE HIERRO (III) Y AMONIO 12-HIDRATO', 'Almacén principal', '6l', 1, 'No viene reflejado', 'Atención', 'Puro', 'No viene reflejada', 0),
(151, 'HIERRO (III) Y AMONIO SULFATO 12-HIDRATO', 'Almacén principal', '6l', 2, '100 g', 'Atención', 'Puro', 'No viene reflejada', 0),
(152, 'AMONIO DE HIERRO (II) SULFATO 6-HIDRATO', 'Almacén principal', '6l', 1, '500 g', 'Atención', '98-101 %', '9-2012', 0),
(153, 'AMONIO DE HIERRO (II) SULFATO 6-HIDRATO', 'Almacén principal', '6l', 1, '1 Kg', 'Atención', '98-101 %', '10-2007', 0),
(154, 'AMONIO HIERRO (III) SULFATO 12-HIDRATO', 'Almacén principal', '6l', 1, '1 Kg', 'Atención', '97-102 %', '7-2012', 0),
(155, 'HIERRO (II) AMONIO SULFATO 6-HIDRATO', 'Almacén principal', '6l', 1, '500 g', 'Atención', '99-101 %', 'No viene reflejada', 0),
(156, 'TARTRATO DE AMONIO', 'Almacén principal', '6l', 2, '500 g', 'Atención', '99 %', 'No viene reflejada', 0),
(157, 'AMONIO TARTRATO', 'Almacén principal', '6l', 1, '1 Kg', 'Atención', '98 %', '7-2016', 0),
(158, 'ALUMINIO METAL TROZOS DE LÁMINA', 'Almacén principal', '6l', 1, 'No viene reflejado', 'Atención', 'Puro', 'No viene reflejada', 0),
(159, 'ALUMINIO METAL LIMADURAS', 'Almacén principal', '6l', 1, '250 g', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(160, 'ALUMINIO METAL LIMADURAS', 'Almacén principal', '6l', 2, '500 g', 'Atención', 'Puro', 'No viene reflejada', 0),
(161, 'ALUMINIO METAL GRANALLA', 'Almacén principal', '6l', 1, '2 Kg', 'Atención', 'Puro', 'No viene reflejada', 0),
(162, 'CLORURO ALUMÍNICO', 'Almacén principal', '6l', 2, '1 Kg', 'Atención', 'Purísimo', 'No viene reflejada', 0),
(163, 'ALUMINIO CLORURO 6-HIDRATO', 'Almacén principal', '6l', 1, '500 g', 'Atención', 'Puro', 'No viene reflejada', 0),
(164, 'ALUMINIO HIDRÓXIDO', 'Almacén principal', '6l', 1, '250 g', 'Atención', '90 %', 'No viene reflejada', 0),
(165, 'ESMERIL', 'Almacén principal', '6l', 1, 'No viene reflejado', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(166, 'ALUMINIO ÓXIDO BÁSICO', 'Almacén principal', '6l', 8, '1 Kg', 'Atención', '9,5-10,5 %', 'No viene reflejada', 0),
(167, 'ALUMINIO ÓXIDO BÁSICO', 'Almacén principal', '6l', 1, '500 g', 'Atención', '9-10,5 %', 'No viene reflejada', 0),
(168, 'ALUMINIO ÓXIDO BÁSICO', 'Almacén principal', '6l', 1, '500 g', 'Atención', '9-10 %', '7-2017', 0),
(169, 'ALUMINIO SULFATO 18-HIDRATO', 'Almacén principal', '6l', 2, '500 g', 'Corrosivo', 'No viene reflejado', '5-2026', 0),
(170, 'ALUMINIO SULFATO 12-HIDRATO', 'Almacén principal', '6l', 1, '500 g', 'Atención', '99 %', 'No viene reflejada', 0),
(171, 'ALUMINIO SULFATO 18-HIDRATO', 'Almacén principal', '6l', 1, '500 g', 'Atención', '98-105 %', 'No viene reflejada', 0),
(172, 'ALUMINIO SULFATO 18-HIDRATO', 'Almacén principal', '6l', 1, '1 Kg', 'Atención', '51-59 %', 'No viene reflejada', 0),
(173, 'LANA DE VIDRIO', 'Almacén principal', '6l', 1, '1 Kg', 'Atención', '0,01 %', '1-2026', 0),
(174, 'POTASIO ACETATO', 'Almacén principal', '7l', 1, '1 Kg', 'Atención', '99 %', 'No viene reflejada', 0),
(175, 'POTASIO CLORURO', 'Almacén principal', '7l', 1, '1 Kg', 'Atención', '99-100,5 %', 'No viene reflejada', 0),
(176, 'YODURO POTÁSICO', 'Almacén principal', '7l', 1, '250 g', 'Atención', 'No viene reflejado', '11-2019', 0),
(177, 'POTASIO YODURO', 'Almacén principal', '7l', 1, '1 Kg', 'Atención', '99 %', 'No viene reflejada', 0),
(178, 'POTASIO YODATO', 'Almacén principal', '7l', 1, '500 g', 'Comburente, corrosivo y nocivo', 'No viene reflejado', 'No viene reflejada', 0),
(179, 'POTASIO YODATO', 'Almacén principal', '7l', 1, '100 g', 'Comburente', 'No viene reflejado.', '2-2025', 0),
(180, 'POTASIO YODADO', 'Almacén principal', '7l', 1, '250 g', 'Cancerigeno', '99,5 %', 'No viene reflejada', 0),
(181, 'POTASIO YODADO', 'Almacén principal', '7l', 1, '1 Kg', 'Nocivo', '99,5 %', 'No viene reflejada', 0),
(182, 'FERRICIANURO POTÁSICO CRIST', 'Almacén principal', '7l', 1, 'No viene reflejado', 'Atención', 'Puro', 'No viene reflejada', 0),
(183, 'POTASIO FERRICIANURO', 'Almacén principal', '7l', 1, '500 g', 'Atención', '99 %', 'No viene reflejada', 0),
(184, 'FERROCIANURO DE POTASIO 3-HIDRATO', 'Almacén principal', '7l', 1, 'No viene reflejado', 'Atención', 'Puro', 'No viene reflejada', 0),
(185, 'POTASIO HEXACIANOFERRATO (II) 3-HIDRATO', 'Almacén principal', '7l', 2, '1 Kg', 'Atención', '99,0-102,0 %', 'No viene reflejada', 0),
(186, 'POTASIO HEXACIANOFERRATO (II) 3-HIDRATO', 'Almacén principal', '7l', 1, '500 g', 'Atención', '98 %', 'No viene reflejada', 0),
(187, 'POTASIO HEXACIANOFERRATO (III)', 'Almacén principal', '7l', 1, 'No viene reflejado', 'Atención', '98 %', 'No viene reflejada', 0),
(188, 'HEXACIANOFERRATO (II) DE POTASIO', 'Almacén principal', '7l', 1, '500 g', 'Atención', '99 %', 'No viene reflejada', 0),
(189, 'POTASIO di-HIDRÓGENO FOSFATO', 'Almacén principal', '7l', 5, '1 Kg', 'Atención', '99,5 %', 'No viene reflejada', 0),
(190, 'di-POTASIO HIDROGENOFOSFATO', 'Almacén principal', '7l', 1, '1 Kg', 'Atención', '99,8 %', '4-2022', 0),
(191, 'POTASIO HIDRÓGENO FTALATO', 'Almacén principal', '7l', 1, '500 g', 'Atención', '99-101 %', '1-2028', 0),
(192, 'POTASIO NITRATO', 'Almacén principal', '7l', 1, '1 Kg', 'Comburente', '100,1 %', '11-2022', 0),
(193, 'POTASIO NITRATO', 'Almacén principal', '7l', 1, '50 g', 'Comburente', '99 %', '29-2-2008', 0),
(194, 'POTASIO NITRATO sin antiapelmazante', 'Almacén principal', '7l', 1, '1 Kg', 'Comburente', '99,0 %', '7-2027', 0),
(195, 'POTASIO SULFATO PRS', 'Almacén principal', '7l', 1, '1 Kg', 'Atención', '98 %', '12-2014', 0),
(196, 'POTASIO SULFATO', 'Almacén principal', '7l', 1, '1 Kg', 'Atención', '99,6 %', '1-2022', 0),
(197, 'POTASIO SULFATO', 'Almacén principal', '7l', 1, '1 Kg', 'Atención', '99 %', 'No viene reflejada', 0),
(198, 'POTASIO SULFATO', 'Almacén principal', '7l', 1, '1 Kg', 'Atención', '98 %', '9-2008', 0),
(199, 'POTASIO BISULFATO', 'Almacén principal', '7l', 1, '500 g', 'Atención', '95 %', 'No viene reflejada', 0),
(200, 'CARBONATO CÁLCICO', 'Almacén principal', '7l', 1, 'No viene reflejado', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(201, 'ÁCIDO TARTÁRICO', 'Almacén principal', '7l', 1, '500 g', 'Nocivo', '99,0 %', '3-2023', 0),
(202, 'TARTRATO DE POTASIO', 'Almacén principal', '7l', 1, 'No viene reflejado', 'Atención', 'Neutro', 'No viene reflejada', 0),
(203, 'BITARTRATO DE POTASIO', 'Almacén principal', '7l', 1, '500 g', 'Atención', '98 %', 'No viene reflejada', 0),
(204, 'BITARTRATO DE POTASIO', 'Almacén principal', '7l', 1, '500 g', 'Atención', '99 %', 'No viene reflejada', 0),
(205, 'SOBATO DE POTASIO', 'Almacén principal', '7l', 2, '500 g', 'Atención', '99 %', 'No viene reflejada', 0),
(206, 'SODIO ACETATO ANHIDRO', 'Almacén principal', '8l', 1, 'No viene reflejado', 'Atención', '99 %', 'No viene reflejada', 0),
(207, 'SODIO TETRATO 2 HIDRATO', 'Almacén principal', '8l', 1, '500 g', 'Atención', '99 %', 'No viene reflejada', 0),
(208, 'SODIO TETRA-BORATO 10 HIDRATO', 'Almacén principal', '8l', 2, '250 g,1 kg', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(209, 'TETRABORATO SODICO', 'Almacén principal', '8l', 1, 'No viene reflejado', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(210, 'SODIO TETRA-FENILBORATO', 'Almacén principal', '8l', 4, '10 g', 'Atención', '99 %', 'No viene reflejada', 0),
(211, 'SODIO HIDROGENOCARBONATO', 'Almacén principal', '8l', 1, '1 Kg', 'Atención', '99,9 %', 'No viene reflejada', 0),
(212, 'SODIO HIDROGENOCARBONATO', 'Almacén principal', '8l', 2, '1 Kg', 'Atención', '99 %', 'No viene reflejada', 0),
(213, 'CARBOXIMETILCELULOSA SAL SODICA', 'Almacén principal', '8l', 1, '250 g', 'Atención', '99,5 %', 'No viene reflejada', 0),
(214, 'SODIO FOSFATO MONO-BASICO 2-HIDRATO', 'Almacén principal', '8l', 1, '250 g', 'Atención', '99,10 %', '2-2012', 0),
(215, 'SODIO FOSFATO MONO-BASICO 2-HIDRATO', 'Almacén principal', '8l', 1, '250 g', 'Atención', 'Puro', 'No viene reflejada', 0),
(216, 'SODIO FOSFATO MONO-BASICO 1-HIDRATO', 'Almacén principal', '8l', 2, '500 g', 'Atención', '99 %', 'No viene reflejada', 0),
(217, 'SODIO FOSFATO MONO-BASICO 2-HIDRATO', 'Almacén principal', '8l', 1, '500 g', 'Atención', '99 %', 'No viene reflejada', 0),
(218, 'SODIO FOSFATO DI-BASICO ANHIDRATO', 'Almacén principal', '8l', 1, '500 g', 'Atención', '98 %', 'No viene reflejada', 0),
(219, 'SODIO HIDRÓGENO FOSFATO ANHIDRO', 'Almacén principal', '8l', 1, '1 Kg', 'Atención', '99 %', '28-2-2007', 0),
(220, 'FOSFATO TRISÓDICO ANHIDRO', 'Almacén principal', '8l', 1, '500 g', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(221, 'DI-SODIO HIDRÓGENO FOSFATO ANHIDRO', 'Almacén principal', '8l', 1, '500 g', 'Atención', '98 %', 'No viene reflejada', 0),
(222, 'DI-SODIO HIDRÓGENO FOSFATO ANHIDRO', 'Almacén principal', '8l', 1, '500 g', 'Atención', '99 %', 'No viene reflejada', 0),
(223, 'ÁCIDO ETILENDIAMINOTETRAACÉTICO SAL DISÓDICA 2-HIDRATO', 'Almacén principal', '8l', 1, '250 g', 'Nocivo', '99 %', '11-2011', 0),
(224, 'SODIO MOLIBDATO 2-HIDRATO', 'Almacén principal', '8l', 1, '1 Kg', 'Atención', '99 %', 'No viene reflejada', 0),
(225, 'SODIO MOLIBDATO CRIST', 'Almacén principal', '8l', 1, '1 Kg', 'Atención', '99 %', 'No viene reflejada', 0),
(226, 'MOLIBDENO (VI) ÓXIDO', 'Almacén principal', '8l', 2, '250 g', 'Toxicidad aguda', '99 %', 'No viene reflejada', 0),
(227, 'SODIO NITRATO', 'Almacén principal', '9l', 3, '1 Kg', 'Comburente', '99 %', 'No viene reflejada', 0),
(228, 'SODIO NITRATO', 'Almacén principal', '9l', 1, '1 Kg', 'Comburente', '99 %', '7-2016', 0),
(229, 'SODIO HEXANITROCOBALTATO (III)', 'Almacén principal', '9l', 1, '100 g', 'Comburente', 'No viene reflejado', 'No viene reflejada', 0),
(230, 'SODIO SULFATO ANHIDRO', 'Almacén principal', '9l', 1, '1 Kg', 'Atención', 'No viene reflejado', '3-2015', 0),
(231, 'SODIO DODECILO SULFATO', 'Almacén principal', '9l', 1, '250 g', 'Nocivo', '85 %', '2-2007', 0),
(232, 'SODIO SULFATO', 'Almacén principal', '9l', 1, '1 Kg', 'Atención', '99 %', 'No viene reflejada', 0),
(233, 'SODIO SULFATO 10-HIDRATO', 'Almacén principal', '9l', 1, '1 Kg', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(234, 'SODIO SULFATO ANHIDRO', 'Almacén principal', '9l', 1, '500 g', 'Atención', 'Puro', 'No viene reflejada', 0),
(235, 'HIERRO METAL, GRANULADO FINO', 'Almacén principal', '9l', 1, '250 g', 'Atención', '99 %', 'No viene reflejada', 0),
(236, 'SULFITO DE SODIO ANHIDRO', 'Almacén principal', '9l', 1, 'No viene reflejado', 'Toxicidad aguda', '85-90 %', 'No viene reflejada', 0),
(237, 'SODIO SULFITO ANHIDRO', 'Almacén principal', '9l', 1, '1 Kg', 'Atención', '98 %', 'No viene reflejada', 0),
(238, 'BISULFITO SÓDICO ANHIDRO', 'Almacén principal', '9l', 1, '250 g', 'Atención', '95 %', 'No viene reflejada', 0),
(239, 'BISULFITO SÓDICO', 'Almacén principal', '9l', 1, 'No viene reflejado', 'Atención', '95 %', 'No viene reflejada', 0),
(240, 'meta-BISULFITO DE SODIO', 'Almacén principal', '9l', 2, '500 g', 'Atención', '95 %', 'No viene reflejada', 0),
(241, 'POTASIO SODIO TARTRATO 4-HIDRATO', 'Almacén principal', '9l', 2, '1 Kg', 'Atención', '99-102 %', 'No viene reflejada', 0),
(242, 'POTASIO SODIO TARTRATO 4-HIDRATO', 'Almacén principal', '9l', 1, '500 g', 'Atención', '99-102 %', '7-2026', 0),
(243, 'SODIO TIOSULFATO PENTAHIDRATADO', 'Almacén principal', '9l', 1, '1 Kg', 'Atención', '99,5-100,5 %', '30-6-2021', 0),
(244, 'SODIO TIOSULFATO PENTAHIDRATADO', 'Almacén principal', '9l', 1, '500 g', 'Atención', '100,2 %', '12-2017', 0),
(245, 'WOLFRAMATO SÓDICO', 'Almacén principal', '9l', 1, '500 g', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(246, 'ACETATO CÁLCICO', 'Almacén principal', '9l', 1, 'No viene reflejado', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(247, 'CARBONATO DE CALCIO MARMOL TROCEADO', 'Almacén principal', '9l', 1, '2 Kg', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(248, 'MARMOL', 'Almacén principal', '9l', 1, 'No viene reflejado', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(249, 'FOSFATO TRICÁLCICO', 'Almacén principal', '9l', 1, '500 g', 'Atención', 'Puro', 'No viene reflejada', 0),
(250, 'CALCIO NITRATO 4-HIDRATO', 'Almacén principal', '9l', 1, 'No viene reflejado', 'Comburente', '98 %', 'No viene reflejada', 0),
(251, 'CALCIO NITRATO 2-HIDRATO', 'Almacén principal', '9l', 1, '500 g', 'Atención', '98 %', 'No viene reflejada', 0),
(252, 'CALCIO SULFATO 2-HIDRATO', 'Almacén principal', '9l', 1, '1 Kg', 'Atención', 'Puro', 'No viene reflejada', 0),
(253, 'SULFATO DE CALCIO 2-HIDRATO', 'Almacén principal', '9l', 1, 'No viene reflejado', 'Atención', 'Puro', 'No viene reflejada', 0),
(254, 'SULFATO CÁLCICO CRISTALIZADO', 'Almacén principal', '9l', 1, '1 Kg', 'Atención', 'Purísimo', 'No viene reflejada', 0),
(255, 'COBRE (II) SULFATO 5-HIDRATO', 'Almacén principal', '9l', 1, '500 g', 'Nocivo y peligroso para el medio ambiente', '99-100,5 %', '10-2011', 0),
(256, 'SULFATO DE BARIO', 'Almacén principal', '10l', 1, 'No viene reflejado', 'Atención', 'Purísimo', 'No viene reflejada', 0),
(257, 'SULFATO DE CADMIO', 'Almacén principal', '10l', 1, '250 g', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(258, 'COBRE METAL VIRUTAS', 'Almacén principal', '10l', 1, '25 g', 'Atención', 'Puro', 'No viene reflejada', 0),
(259, 'ESTRONCIO CLORURO', 'Almacén principal', '10l', 1, '1 Kg', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(260, 'HIERRO METAL GRANULADO', 'Almacén principal', '10l', 1, 'No viene reflejado', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(261, 'HIERRO METAL GRANULADO', 'Almacén principal', '10l', 1, '50 g', 'Atención', 'Puro', 'No viene reflejada', 0),
(262, 'LIMADURA DE HIERRO', 'Almacén principal', '10l', 2, 'No viene reflejado', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(263, 'GRANALLA DE HIERRO', 'Almacén principal', '10l', 1, 'No viene reflejado', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(264, 'HIERRO METAL TORNEADURAS', 'Almacén principal', '10l', 1, '2 Kg', 'Atención', 'Purísimo', 'No viene reflejada', 0),
(265, 'COBRE METAL POLVO', 'Almacén principal', '10l', 1, 'No viene reflejado', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(266, 'HIERRO (III) SULFATO', 'Almacén principal', '10l', 1, '1 Kg', 'Atención', '75 %', 'No viene reflejada', 0),
(267, 'HIERRO (II) SULFURO, CILINDROS', 'Almacén principal', '10l', 1, 'No viene reflejado', 'Atención', 'Purísimo', 'No viene reflejada', 0),
(268, 'HIERRO (II) SULFURO, TROZOS', 'Almacén principal', '10l', 1, '250 g', 'Atención', 'Puro', 'No viene reflejada', 0),
(269, 'CLORURO DE CESIO', 'Almacén principal', '10l', 2, '50 g', 'Atención', '99 %', 'No viene reflejada', 0),
(270, 'CARBONATO DE MAGNESIO', 'Almacén principal', '10l', 1, 'No viene reflejado', 'Atención', '26 %', 'No viene reflejada', 0),
(271, 'MAGNESIO CLORURO 6-HIDRATO', 'Almacén principal', '10l', 1, '500 g', 'Atención', '99 %', 'No viene reflejada', 0),
(272, 'ZINC METAL GRANALLA', 'Almacén principal', '10l', 1, '500 g', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(273, 'CINC GRANALLA', 'Almacén principal', '10l', 1, '1 Kg', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(274, 'ZINC METAL POLVO', 'Almacén principal', '10l', 1, '500 g', 'Inflamable', '95 %', 'No viene reflejada', 0),
(275, 'ZINC CLORURO', 'Almacén principal', '10l', 1, '500 g', 'Corrosivo, peligroso para el medio ambiente, nocivo y irritante', '97 %', '12-2020', 0),
(276, 'ZINC ACETATO 2-HIDRATO', 'Almacén principal', '10l', 1, '500 g', 'Atención', '99-101 %', '12-2012', 0),
(277, 'FENOL', 'Almacén principal', '11T', 2, '1 Kg', 'Toxicidad aguda', '90 %', 'No viene reflejada', 0),
(278, 'FORMALDEHÍDO', 'Almacén principal', '11T', 3, '1 L', 'Atención', '30 %', 'No viene reflejada', 0),
(279, 'FORMALDEHÍDO', 'Almacén principal', '11T', 3, '1 Kg', 'Toxicidad aguda', '35-40 %', 'No viene reflejada', 0),
(280, 'NITROBENCENO', 'Almacén principal', '11T', 2, '1 L', 'Toxicidad aguda', '99 %', 'No viene reflejada', 0),
(281, 'NITROBENCENO', 'Almacén principal', '11T', 1, '1 Kg', 'Toxicidad aguda y inflamable', 'Purísimo', 'No viene reflejada', 0),
(282, 'NITROBENCENO', 'Almacén principal', '11T', 1, '250 mL', 'Toxicidad aguda', 'Puro', 'No viene reflejada', 0),
(283, 'TETRACLORURO DE CARBONO', 'Almacén principal', '11T', 1, 'No viene reflejado', 'Nocivo', '99,8 %', 'No viene reflejada', 0),
(284, 'DIFENILAMINA', 'Almacén principal', '11T', 1, '100 g', 'Atención', '99 %', 'No viene reflejada', 0),
(285, 'BENZOATO DE ETILO', 'Almacén principal', '11T', 1, '1 L', 'Toxicidad aguda', '98 %', 'No viene reflejada', 0),
(286, 'FORMAMIDA', 'Almacén principal', '11T', 1, '1 L', 'Atención', 'Puro', 'No viene reflejada', 0),
(287, 'DIMETIL FORMAMIDA', 'Almacén principal', '11T', 1, '1 L', 'Atención', 'Puro', 'No viene reflejada', 0),
(288, '1, 2-PROPILENGLICOL', 'Almacén principal', '11T', 1, '1 L', 'Toxicidad aguda', '99 %', 'No viene reflejada', 0),
(289, 'N, N-DIMETILAMINA', 'Almacén principal', '11T', 1, '1 L', 'Toxicidad aguda', '99 %', 'No viene reflejada', 0),
(290, 'GLICINA', 'Almacén principal', '11T', 1, '100 g', 'Atención', '99,7 %', '29-2-2008', 0),
(291, 'POTASIO CROMATO', 'Almacén principal', '12T', 2, '500 g', 'Irritante', '99-101 %', 'No viene reflejada', 0),
(292, 'POTASIO DICROMATO', 'Almacén principal', '12T', 1, '500 g', 'Comburente y toxicidad aguda', 'No viene reflejado', 'No viene reflejada', 0),
(293, 'POTASIO DICROMATO', 'Almacén principal', '12T', 1, '2 Kg', 'Comburente, toxicidad aguda, corrosivo, peligroso para el medio ambiente y cancerigeno', '99,5 %', 'No viene reflejada', 0),
(294, 'TIOCARBANILIDA', 'Almacén principal', '12T', 1, '50 g', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(295, 'TIOCARBACILA', 'Almacén principal', '12T', 1, 'No viene reflejado', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(296, 'ANILINA', 'Almacén principal', '12T', 1, '250 mL', 'Toxicidad aguda y peligroso para el medio ambiente', '99,5 %', '12-2002', 0),
(297, 'FENOLFTALEINA', 'Almacén principal', '12T', 2, '100 g', 'Atención', '99 %', 'No viene reflejada', 0),
(298, '2, 4-DINITROFENILHIDRACINA', 'Almacén principal', '12T', 1, '100 g', 'Nocivo', '99 %', '10-2020', 0),
(299, 'SULFATO DE HIDRACINA', 'Almacén principal', '12T', 1, 'No viene reflejado', 'Irritante y toxicidad aguda', '99 %', 'No viene reflejada', 0),
(300, 'HIDROXILAMINA SULFATO', 'Almacén principal', '12T', 2, '250 g', 'Toxicidad aguda', '99 %', 'No viene reflejada', 0),
(301, 'HIDROXILAMINA CLORURO', 'Almacén principal', '12T', 1, '250 g', 'Corrosivo, peligroso para el medio ambiente, nocivo y cancerigeno', '99,5 %', '7-2029', 0),
(302, 'HIDROXILAMINA CLORURO', 'Almacén principal', '12T', 1, '250 g', 'Nocivo y peligroso para el medio ambiente', '99 %', 'No viene reflejada', 0),
(303, 'ÁCIDO PICRICO', 'Almacén principal', '12T', 1, '25 g', 'Toxicidad aguda y corrosivo', 'Puro', 'No viene reflejada', 0),
(304, 'ÁCIDO BORICO SOLUCIÓN', 'Almacén principal', '13T', 2, '1 L', 'Atención', '4 %', '5-2014', 0),
(305, 'ÁCIDO BORICO CRISTALIZADO', 'Almacén principal', '13T', 1, '2 Kg', 'Atención', 'Purísimo', 'No viene reflejada', 0),
(306, 'SODIO NITRITO', 'Almacén principal', '13T', 1, '500 g', 'Comburente y toxicidad aguda', '97 %', 'No viene reflejada', 0),
(307, 'SELENIO METAL POLVO', 'Almacén principal', '13T', 1, '50 g', 'Toxicidad aguda', '99 %', 'No viene reflejada', 0),
(308, 'LANA DE VIDRO LAVADA', 'Almacén principal', '13T', 1, '250 g', 'Atención', '1 %', '1-2023', 0),
(309, 'CLORURO DE BARIO di-HIDRATO', 'Almacén principal', '14T', 1, '500 g', 'Toxicidad aguda', '100,2 %', '2-2021', 0),
(310, 'BARIO CLORURO 2-HIDRATO', 'Almacén principal', '14T', 1, 'No viene reflejado', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(311, 'BARIO CLORURO 2-HIDRATO', 'Almacén principal', '14T', 1, '1 Kg', 'Toxicidad aguda', '99 %', '11-2025', 0),
(312, 'CLORURO DE COBRE (II) 2-HIDRATO', 'Almacén principal', '14T', 1, '250 g', 'Toxicidad aguda', 'Puro', 'No viene reflejada', 0),
(313, 'COBALTO (II) CLORURO 6-HIDRATO', 'Almacén principal', '14T', 1, '250 g', 'Toxicidad aguda y peligroso para el medio ambiente', '98 %', '7-2010', 0),
(314, 'CROMO (VI) ÓXIDO', 'Almacén principal', '14T', 1, '500 g', 'Comburente y toxicidad aguda', '99 %', 'No viene reflejada', 0),
(315, 'PLOMO (II) NITRATO', 'Almacén principal', '14T', 1, 'No viene reflejado', 'Comburente y nocivo', '99 %', 'No viene reflejada', 0),
(316, 'PLOMO (II) NITRATO', 'Almacén principal', '14T', 1, '500 g', 'Comburente y nocivo', 'Puro', 'No viene reflejada', 0),
(317, 'PLOMO (IV) ÓXIDO', 'Almacén principal', '14T', 1, '1 Kg', 'Nocivo', '95 %', 'No viene reflejada', 0),
(318, 'MOLIBDENO (VI) ÓXIDO', 'Almacén principal', '14T', 1, '250 g', 'Toxicidad aguda', '99 %', 'No viene reflejada', 0),
(319, 'SILICAGEL 2, 5-6mm', 'Almacén principal', '14T', 1, '1 Kg', 'Atención', '27 %', '2-2025', 0),
(320, 'HIDROQUINONA', 'Almacén principal', '15O', 1, '250 g', 'Toxicidad aguda', 'No viene reflejado', 'No viene reflejada', 0),
(321, 'HIDROQUINONA', 'Almacén principal', '15O', 1, '250 g', 'Atención', 'Purísimo', 'No viene reflejada', 0),
(322, 'ÁCIDO SÓRBICO', 'Almacén principal', '15O', 1, '250 g', 'Atención', '98,5 %', 'No viene reflejada', 0),
(323, 'ÁCIDO SÓRBICO', 'Almacén principal', '15O', 1, '100 g', 'Atención', 'Puro', 'No viene reflejada', 0),
(324, 'ÁCIDO PALMÍTICO', 'Almacén principal', '15O', 1, 'No viene reflejado', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(325, 'ÁCIDO CÍTRICO MONOHIDRATADO', 'Almacén principal', '15O', 2, '1 Kg', 'Nocivo', '99,7 %', '28-3-2018', 0),
(326, 'ÁCIDO ETILENDIAMMINOTETRAACETICO SAL DISÓDICA 2-HIDRATO', 'Almacén principal', '15O', 1, '500 g', 'Nocivo y cancerigeno', '99-101 %', 'No viene reflejada', 0),
(327, 'ÁCIDO ETILENDIAMMINOTETRAACETICO SAL DISÓDICA 2-HIDRATO', 'Almacén principal', '15O', 1, '250 g', 'Atención', '98 %', 'No viene reflejada', 0),
(328, 'ÁCIDO ETILENDIAMINOTETRAACÉTICO SAL DISÓDICA DIHIDRATO', 'Almacén principal', '15O', 1, '1 Kg', 'Nocivo', '99-101 %', '12-2021', 0),
(329, 'ÁCIDO CÍTRICO ANHIDRO', 'Almacén principal', '15O', 1, '1 Kg', 'Nocivo', '99,5-100,5 %', '2-2027', 0),
(330, 'ÁCIDO ASPÁRTICO', 'Almacén principal', '15O', 1, '500 g', 'Atención', '98,5-101,5 %', 'No viene reflejada', 0),
(331, 'ÁCIDO ASÓRDICO', 'Almacén principal', '15O', 1, '100 g', 'Atención', '99 %', '11-2012', 0),
(332, 'ÁCIDO ASÓRDICO', 'Almacén principal', '15O', 1, '100 g', 'Atención', 'No viene reflejado', '9-2022', 0),
(333, 'ÁCIDO ASÓRDICO', 'Almacén principal', '15O', 1, '100 g', 'Atención', '99,7 %', 'No viene reflejada', 0),
(334, 'ÁCIDO ASÓRDICO', 'Almacén principal', '15O', 1, '250 g', 'Atención', '99,7 %', 'No viene reflejada', 0),
(335, 'ÁCIDO ASÓRDICO', 'Almacén principal', '15O', 1, '500 g', 'Atención', '99-100,5 %', '11-2023', 0),
(336, 'ÁCIDO ASÓRDICO', 'Almacén principal', '15O', 1, '500 g', 'Atención', '99 %', '28-2-2022', 0),
(337, 'ETIL ACETATO', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable y irritante', '99,5 %', '10-2010', 0),
(338, '1-PROPANOL', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable y irritante', '99,5 %', '2-2010', 0),
(339, 'ISO-BUTIRALDEHIDO', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable', '99 %', 'No viene reflejada', 0),
(340, 'SODIO BOROHIDRURO', 'Almacén principal', 'Armario de inflamables', 1, '100 g', 'Toxicidad aguda y inflamable', '97 %', 'No viene reflejada', 0),
(341, 'DL-ALCANFOR', 'Almacén principal', 'Armario de inflamables', 1, '1 Kg', 'Inflamable', 'Purísimo', 'No viene reflejada', 0),
(342, 'TETRACLORURO DE CARBONO', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Nocivo', '99,8 %', 'No viene reflejada', 0),
(343, '1, 2-PROPANEDIOL', 'Almacén principal', 'Armario de inflamables', 1, '500 mL', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(344, 'BENZALDEHIDO', 'Almacén principal', 'Armario de inflamables', 1, 'No viene reflejado', 'Atención', 'Puro', 'No viene reflejada', 0),
(345, 'HEXANO Y ALCOHOL RECUPERADO', 'Almacén principal', 'Armario de inflamables', 1, 'No viene reflejado', 'Atención', 'No viene reflejado', '10-2-2017', 0),
(346, 'N-HEXANO PARA RECUPERAR', 'Almacén principal', 'Armario de inflamables', 1, 'No viene reflejado', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(347, 'HEPTANO PARA RECICLAR', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(348, 'TERT-BUTANOL', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable y nocivo', '99 %', 'No viene reflejada', 0),
(349, 'ETILO ACETATO', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable y irritante', '99,5 %', 'No viene reflejada', 0),
(350, 'DICLORO BENCENO', 'Almacén principal', 'Armario de inflamables', 1, 'No viene reflejado', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(351, '1-BUTANOL', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Nocivo', '99,5 %', 'No viene reflejada', 0),
(352, '2-METIL-2-PROPANOL', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable y nocivo', '99 %', 'No viene reflejada', 0),
(353, 'BENZALDEHIDO', 'Almacén principal', 'Armario de inflamables', 1, 'No viene reflejado', 'Atención', 'Puro', 'No viene reflejada', 0),
(354, 'ALCOHOL AMÍLICO (ISO)', 'Almacén principal', 'Armario de inflamables', 3, '1 L', 'Atención', 'Puro', 'No viene reflejada', 0),
(355, 'CETONA', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable y irritante', '99,5 %', '12-2012', 0),
(356, 'ETER DE PETROLEO', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable, nocivo y peligroso para el medio ambiente', 'No viene reflejado', '5-2012', 0),
(357, '4-METIL-2-PENTANONA', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable', '99 %', 'No viene reflejada', 0),
(358, 'ISOOCTANO', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable, nocivo y peligroso para el medio ambiente', '99 %', '10-2010', 0),
(359, 'DIFENILAMINA', 'Almacén principal', 'Armario de inflamables', 1, '100 g', 'Toxicidad aguda', '99 %', 'No viene reflejada', 0),
(360, 'RESIDUOS TOLUENO', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(361, 'METANOL', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable y toxicidad aguda', '99,8 %', 'No viene reflejada', 0),
(362, 'METANOL', 'Almacén principal', 'Armario de inflamables', 2, '1 L', 'Inflamable y toxicidad aguda', '99,5 %', '7-2011', 0),
(363, 'TETRABUTILAMONIO HIDRÓXIDO', 'Almacén principal', 'Armario de inflamables', 1, '500 mL', 'Inflamable y toxicidad aguda', 'No viene reflejado', 'No viene reflejada', 0),
(364, 'BENCENO SECO', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable y toxicidad aguda', '99,8 %', '9-2010', 0),
(365, '2-MERCAPTOETANOL', 'Almacén principal', 'Armario de inflamables', 1, '250 mL', 'Corrosivo, toxicidad aguda y peligroso para el medio ambiente', 'No viene reflejado', '3-2016', 0),
(366, 'N-PETANO', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable', '95 %', 'No viene reflejada', 0),
(367, 'ÉTER ETILICO', 'Almacén principal', 'Armario de inflamables', 1, '2,5 L', 'Inflamable y nocivo', '100 %', '30-4-2026', 0),
(368, 'TOLUENO', 'Almacén principal', 'Armario de inflamables', 1, '2,5 L', 'Inflamable, cancerigeno y nocivo', '100 %', '13-10-2028', 0),
(369, 'HEXANO', 'Almacén principal', 'Armario de inflamables', 1, '5 L', 'Inflamable, cancerigeno, nocivo y peligroso para el medio ambiente', '95 %', '31-12-2021', 0),
(370, 'SATURADO DE N-HEXANO', 'Almacén principal', 'Armario de inflamables', 1, 'No viene reflejado', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(371, 'PIPERIDINA', 'Almacén principal', 'Armario de inflamables', 1, '500 mL', 'Inflamable y toxicidad aguda', '99 %', 'No viene reflejada', 0),
(372, 'N-HEXANO', 'Almacén principal', 'Armario de inflamables', 1, '5 L', 'Inflamable, cancerigeno, nocivo y peligroso para el medio ambiente', '97 %', '1-2022', 0),
(373, 'TOLUENO', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable y nocivo', '99,5 %', '1-2012', 0),
(374, 'PIPERININA', 'Almacén principal', 'Armario de inflamables', 2, '2,5 L', 'Inflamable y nocivo', '99 %', 'No viene reflejada', 0),
(375, 'GASOLINA', 'Almacén principal', 'Armario de inflamables', 1, 'No viene reflejado', 'Atención', 'No viene reflejado', 'No viene reflejada', 0),
(376, 'SODIO METILATO', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable y toxicidad aguda', '30 %', 'No viene reflejada', 0),
(377, '2-PROPANOL', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable y nocivo', 'No viene reflejado', '10-2026', 0),
(378, 'PIPERININA', 'Almacén principal', 'Armario de inflamables', 1, 'No viene reflejado', 'Atención', 'Puro', 'No viene reflejada', 0),
(379, 'METANOL', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable, toxicidad aguda y cancerigeno', 'No viene reflejado', '2-2027', 0),
(380, 'BENZOLLO CLORURO', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Corrosivo', '99 %', 'No viene reflejada', 0),
(381, 'DIOXANO', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Nocivo y inflamable', '98 %', 'No viene reflejada', 0),
(382, '1-BUTANOL', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable, corrosivo y nocivo', '99 %', '1-2017', 0),
(383, 'XILENO', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable y nocivo', 'No viene reflejado', '23-3-2019', 0),
(384, '1-PROPANOL', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable y irritante', '99,5 %', '12-2026', 0),
(385, '2-PROPANOL', 'Almacén principal', 'Armario de inflamables', 1, '1 L', 'Inflamable y irritante', '99,5 %', '11-2026', 0);

-- --------------------------------------------------------

--
-- Table structure for table `ubicacion`
--

CREATE TABLE `ubicacion` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `localizacion_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ubicacion`
--

INSERT INTO `ubicacion` (`id`, `nombre`, `localizacion_id`) VALUES
(1, '5N', 1),
(2, '4N', 1),
(3, '3N', 1),
(4, '2N', 1),
(5, '1CA', 1),
(6, '6l', 1),
(7, '7l', 1),
(8, '8l', 1),
(9, '9l', 1),
(10, '10l', 1),
(11, '11T', 1),
(12, '12T', 1),
(13, '13T', 1),
(14, '14T', 1),
(15, '15O', 1),
(16, 'Armario de inflamables', 1),
(17, 'Armario 4', 2),
(18, 'Armario 5', 2),
(19, 'Armario 8', 2),
(20, 'Armario 6', 2),
(21, 'Sobre encimera', 2),
(22, 'Armario bajo 1', 2),
(23, 'Armario bajo 3', 2),
(24, 'Armario bajo 4', 2),
(25, 'Estantería 1', 2),
(26, 'Armario 1', 2),
(27, 'Nevera 1', 2),
(28, 'Armario 2', 2),
(29, 'Estantería', 3);

-- --------------------------------------------------------

--
-- Table structure for table `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  `contrasena` varchar(100) NOT NULL,
  `es_admin` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre_usuario`, `contrasena`, `es_admin`) VALUES
(1, 'profesor', 'profesor', 1),
(2, 'alumno', 'alumno', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `auxiliares`
--
ALTER TABLE `auxiliares`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `localizacion`
--
ALTER TABLE `localizacion`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `materiales`
--
ALTER TABLE `materiales`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `reactivos`
--
ALTER TABLE `reactivos`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ubicacion`
--
ALTER TABLE `ubicacion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `localizacion_id` (`localizacion_id`);

--
-- Indexes for table `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `localizacion`
--
ALTER TABLE `localizacion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `reactivos`
--
ALTER TABLE `reactivos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=390;

--
-- AUTO_INCREMENT for table `ubicacion`
--
ALTER TABLE `ubicacion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `ubicacion`
--
ALTER TABLE `ubicacion`
  ADD CONSTRAINT `ubicacion_ibfk_1` FOREIGN KEY (`localizacion_id`) REFERENCES `localizacion` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
