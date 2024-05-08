-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-05-2024 a las 14:15:52
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `quimica`
--

DELIMITER $$
--
-- Procedimientos
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

--
-- Funciones
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
-- Estructura de tabla para la tabla `materiales`
--

CREATE TABLE `materiales` (
  `Nombre` varchar(38) DEFAULT NULL,
  `Localizacion` varchar(24) DEFAULT NULL,
  `Ubicacion` varchar(21) DEFAULT NULL,
  `Subcategoria` varchar(24) DEFAULT NULL,
  `Descripcion` varchar(8) DEFAULT NULL,
  `Cantidad` varchar(1) DEFAULT NULL,
  `StockMinimo` varchar(10) DEFAULT NULL,
  `Nserie` varchar(50) DEFAULT NULL,
  `FechaDeCompra` varchar(10) DEFAULT NULL,
  `Código` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `materiales`
--

INSERT INTO `materiales` (`Nombre`, `Localizacion`, `Ubicacion`, `Subcategoria`, `Descripcion`, `Cantidad`, `StockMinimo`, `Nserie`, `FechaDeCompra`, `Código`) VALUES
('Vasos de precipitados', 'Almacén General', 'estantería 1, balda 3', 'plástico ', '1000 ml', '2', '', '', '', 1),
('Vasos de precipitados ', 'Almacén General', 'estantería 1, balda 3', 'plástico ', '500ml', '2', '', '', '', 2),
('Vaso de precipitados ', 'Almacén General', 'estantería 1, balda 3', 'plástico ', '1000 ml ', '1', '', '', '', 3),
('Matraz erlenmeyer grande', 'Almacén General', 'estantería 1, balda 3', 'cristal', '3 L', '1', '', '', '', 4),
('Matraz erlenmeyer con tapón de cristal', 'Almacén General', 'estantería 1, balda 3', 'cristal ', '1000 ml', '', '', '', '', 5),
('Matraz erlenmeyer ', 'Almacén General', 'estantería 1, balda 3', 'cristal', '2L ', '', '', '', '', 6),
('Matraz erlenmeyer ', 'Almacén General', 'estantería 1,balda 3', 'cristal ', '1000 ml', '', '', '', '', 7),
('Recipiente aséptico ', 'Almacén General', 'estantería 1,balda 4', 'plástico ', '2L ', '', '', '', '', 8),
('Tubos de ensayo graduados ', 'Almacén General', 'estantería 1,balda 4', 'cristal', '50 ml', '', '', '', '', 9),
('Conductímetro', 'Laboratorio Instrumental', 'C1', 'Instrumental Electrónico', '', '1', '', '534031', '', 10),
('Conductímetro', 'Laboratorio Instrumental', 'C2', 'Instrumental Electrónico', '', '1', '', '531030', '', 11),
('Potenciómetro', 'Laboratorio Instrumental', 'P1', 'Instrumental Electrónico', '', '1', '', '539010', '', 12),
('Potenciómetro', 'Laboratorio Instrumental', 'P2', 'Instrumental Electrónico', '', '1', '', '539007', '', 13);

--
-- Disparadores `materiales`
--
DELIMITER $$
CREATE TRIGGER `Sustituir caracteres` BEFORE UPDATE ON `materiales` FOR EACH ROW SET NEW.Localizacion := REPLACE(NEW.Localizacion, '?', 'é')
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prod_aux`
--

CREATE TABLE `prod_aux` (
  `Codigo` int(11) NOT NULL,
  `Nombre` varchar(26) DEFAULT NULL,
  `Formato` varchar(31) DEFAULT NULL,
  `Cantidad` int(2) DEFAULT NULL,
  `Localizacion` varchar(16) DEFAULT NULL,
  `Ubicacion` varchar(21) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `prod_aux`
--

INSERT INTO `prod_aux` (`Codigo`, `Nombre`, `Formato`, `Cantidad`, `Localizacion`, `Ubicacion`) VALUES
(1, 'Bandejas ', 'Plástico verdes medianas', 4, 'Almacén general ', 'Estantería 0 balda 4'),
(2, 'Vaso medidor ', 'Plástico', 2, 'Almacén general ', 'Estantería 0  balda 4'),
(3, 'Botellas con cierre manual', 'Cristal', 2, 'Almacén general ', 'Estantería 0  balda 4'),
(4, 'Exprimidor ', 'Plástico ', 1, 'Almacén general ', 'Estantería 0 balda 4'),
(5, 'Vasos variados ', 'Plástico ', 10, 'Almacén general ', 'Estantería 0 balda 4'),
(6, 'Coladores ', 'Plástico metal tela', 3, 'Almacén general ', 'Estantería 0 balda 4'),
(7, 'Ladrillos', 'De cerámica 6 cuadrados enteros', 4, 'Almacén general ', 'Estantería 0 balda 4'),
(8, 'Jeringa', 'PLÁSTICO', 1, 'Almacén general ', 'Estantería 0 balda 4');

--
-- Disparadores `prod_aux`
--
DELIMITER $$
CREATE TRIGGER `PonerFormatoMayusculas` BEFORE UPDATE ON `prod_aux` FOR EACH ROW SET NEW.Formato = UCASE(NEW.Formato)
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reactivos`
--

CREATE TABLE `reactivos` (
  `Nombre` varchar(54) DEFAULT NULL,
  `Formato` varchar(20) DEFAULT NULL,
  `Cantidad` int(1) DEFAULT NULL,
  `Localizacion` varchar(29) DEFAULT NULL,
  `Ubicacion` varchar(4) DEFAULT NULL,
  `Riesgos` varchar(100) DEFAULT NULL,
  `GradoPureza` varchar(40) DEFAULT NULL,
  `FechaCaducidad` varchar(230) DEFAULT NULL,
  `StockMinimo` int(2) DEFAULT NULL,
  `Codigo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `reactivos`
--

INSERT INTO `reactivos` (`Nombre`, `Formato`, `Cantidad`, `Localizacion`, `Ubicacion`, `Riesgos`, `GradoPureza`, `FechaCaducidad`, `StockMinimo`, `Codigo`) VALUES
('AMONIO NITRATO', '1 Kg', 1, 'Almacén 1/principal', '5N', 'Comburente y irritante ', 'No viene reflejado', 'No viene reflejada', 0, 1),
('BROMURO DE AMONIO (PARA ANÁLISIS)', '1 Kg', 2, 'Almacén 1/principal', '5N', 'Toxicidad aguda (6)', '99 %', 'No viene reflejada', 0, 2),
('AZUFRE SUBLIMADO (AZUFRE FLOR)', '1 Kg', 2, 'Almacén 1/principal', '5N', 'Toxicidad aguda (6)', 'Puro', 'No viene reflejada', 0, 3),
('ALUMINIO NITRATO 9-HIDRATO', '1 Kg', 2, 'Almacén 1/principal', '5N', 'Comburente y nocivo', '98 %', 'No viene reflejada', 0, 4),
('ACETANILIDA CRISTALIZADA', '1 Kg', 1, 'Almacén 1/principal', '5N', 'Atención', 'Purísima', 'No viene reflejada', 0, 5),
('AMINOMETANO', '100 g', 1, 'Almacén 1/principal', '5N', 'Atención', '99 %', 'No viene reflejada', 0, 6),
('ESTAÑO II CLORURO', '250 g', 2, 'Almacén 1/principal', '5N', 'Nocivo', '99 %', 'No viene reflejada', 0, 7),
('ÁCIDO TARTÁRICO', '500 g', 2, 'Almacén 1/principal', '5N', 'Irritante', '99 %', 'No viene reflejada', 0, 8),
('ÁCIDO TARTÁRICO', '1 Kg', 1, 'Almacén 1/principal', '5N', 'Irritante', '99,7 %', 'No viene reflejada', 0, 9),
('BIPIRIDINA', '5 g', 1, 'Almacén 1/principal', '5N', 'Toxicidad aguda', 'No viene reflejado', 'No viene reflejada', 0, 10),
('HIDROXIQUINOLEINA', '100 g', 1, 'Almacén 1/principal', '5N', 'Atención', '98 %', 'No viene reflejada', 0, 11),
('HIDROXIQUINOLEINA', '500 g', 1, 'Almacén 1/principal', '5N', 'Atención', '99 %', 'No viene reflejada', 0, 12),
('CELITE 545', 'No viene reflejado', 1, 'Almacén 1/principal', '5N', 'Nocivo', 'No viene reflejado', 'No viene reflejada', 0, 13),
('REACTIVO DE BENEDICT', '1 L', 1, 'Almacén 1/principal', '5N', 'Nocivo', 'No viene reflejado', 'No viene reflejada', 0, 14),
('REACTIVO FEHLING-A', '500 mL', 1, 'Almacén 1/principal', '5N', 'Atención', 'No viene reflejado', 'No viene reflejada', 0, 15),
('REACTIVO FEHLING-B', '500 mL', 1, 'Almacén 1/principal', '5N', 'Corrosivo', 'No viene reflejado', 'No viene reflejada', 0, 16),
('UREA', 'No viene reflejado', 1, 'Almacén 1/principal', '5N', 'Atención', '99 %', 'No viene reflejada', 0, 17),
('AMONIO MOLIBDATO 4-HIDRATO', '250 g', 1, 'Almacén 1/principal', '4N', 'Atención', '99 %', 'No viene reflejada', 0, 18),
('AMONIO OXALATO 1-HIDRATO', '500 g', 1, 'Almacén 1/principal', '4N', 'Nocivo', '99,5 %', '11-2012', 0, 19),
('AMONIO OXALATO 1-HIDRATO', '1 Kg', 1, 'Almacén 1/principal', '4N', 'Nocivo', '99 %', '7-2016', 0, 20),
('PERSULFATO AMÓNICO', 'No viene reflejado', 1, 'Almacén 1/principal', '4N', 'Atención', '98 %', 'No viene reflejada', 0, 21),
('AMÓNIO TIOCIANATO', '1 Kg', 1, 'Almacén 1/principal', '4N', 'Nocivo', '99 %', '3-2016', 0, 22),
('AMÓNIO TIOCIANATO', '1 Kg', 1, 'Almacén 1/principal', '4N', 'Nocivo', '99 %', '9-2010', 0, 23),
('AMONIO META-VANADATO', '250 g', 1, 'Almacén 1/principal', '4N', 'Nocivo', '99 %', 'No viene reflejada', 0, 24),
('CADMIO NITRATO 4-HIDRATO', '250 g', 1, 'Almacén 1/principal', '4N', 'Nocivo y peligroso para el medio ambiente', '98 %', 'No viene reflejada', 0, 25),
('COBALTO II NITRATO 6-HIDRATO', '250 g', 1, 'Almacén 1/principal', '4N', 'Comburente y nocivo', '98 %', 'No viene reflejada', 0, 26),
('COBRE (II) NITRATO 3-HIDRATO', '500 g', 1, 'Almacén 1/principal', '4N', 'Comburente, corrosivo y peligroso para el medio ambiente', '99 %', 'No viene reflejada', 0, 27),
('ÓXIDO DE COBRE (II)', '500 g', 1, 'Almacén 1/principal', '4N', 'Toxicidad aguda', '95 %', 'No viene reflejada', 0, 28),
('COBRE (II) SULFATO 5-HIDRATO', '1 Kg', 1, 'Almacén 1/principal', '4N', 'Nocivo, peligroso para el medio ambiente y corrosivo', '99 %', 'No viene reflejada', 0, 29),
('COBRE (II) SULFATO 5-HIDRATO', 'No viene reflejado', 1, 'Almacén 1/principal', '4N', 'Nocivo, peligroso para el medio ambiente y corrosivo', 'No viene reflejado', 'No viene reflejada', 0, 30),
('ESTRONCIO NITRATO ANHIDRO', '1 Kg', 1, 'Almacén 1/principal', '4N', 'Comburente', '98 %', 'No viene reflejada', 0, 31),
('HIERRO (III) CLORURO 6-HIDRATO', '1 Kg', 2, 'Almacén 1/principal', '4N', 'Nocivo', '98 %', 'No viene reflejada', 0, 32),
('HIERRO (III) NITRATO 9-HIDRATO', '1 Kg', 1, 'Almacén 1/principal', '4N', 'Comburente y irritante', '98 %', '10-2015', 0, 33),
('HIERRO (II) SULFATO ~ 2-HIDRATO', '1 Kg', 2, 'Almacén 1/principal', '4N', 'Atención', '80 %', 'No viene reflejada', 0, 34),
('HIERRO (II) SULFATO 7-HIDRATO', '1 Kg', 1, 'Almacén 1/principal', '4N', 'Nocivo', '99,5 %', '2-2015', 0, 35),
('HIERRO (II) SULFATO 7-HIDRATO', '1 Kg', 1, 'Almacén 1/principal', '4N', 'Nocivo', '98 %', 'No viene reflejada', 0, 36),
('LANTANO (III) CLORURO 7-HIDRATO', '250 g', 1, 'Almacén 1/principal', '4N', 'Atención', '98 %', 'No viene reflejada.', 0, 37),
('MANGANESO (IV) ÓXIDO', '500 g', 1, 'Almacén 1/principal', '4N', 'Nocivo', '85 %', 'No viene reflejada.', 0, 38),
('SULFATO DE MANGANESO 1-HIDRATO', '1 Kg', 1, 'Almacén 1/principal', '4N', 'Atención', 'Purísima', 'No viene reflejada.', 0, 39),
('MANGANESO (II) SULFATO 1-HIDRATO', '1 Kg', 1, 'Almacén 1/principal', '4N', 'Atención', '99 %', 'No viene reflejada', 0, 40),
('CARBONATO DE NÍQUEL', 'No viene reflejado', 1, 'Almacén 1/principal', '4N', 'Atención', 'No viene reflejado', 'No viene reflejada', 0, 41),
('NÍQUEL (II) NITRATO 6-HIDRATO PRS', '1 Kg', 1, 'Almacén 1/principal', '4N', 'Nocivo, comburente, corrosivo, peligroso para el medio ambiente y carcinogeno', '98 %', '8-2019', 0, 42),
('PLOMO (II) ACETATO 3-HIDRATO', '1 Kg', 1, 'Almacén 1/principal', '4N', 'Nocivo', '99 %', 'No viene reflejada', 0, 43),
('ZINC NITRATO 6-HIDRATO', '500 g', 1, 'Almacén 1/principal', '4N', 'Nocivo y comburente', '98 %', 'No viene reflejada', 0, 44),
('ÓXIDO DE ZINC', 'No viene reflejado', 1, 'Almacén 1/principal', '4N', 'Atención', '98 %', 'No viene reflejada', 0, 45),
('SULFATO DE ZINC 7-HIDRATO', 'No viene reflejado', 1, 'Almacén 1/principal', '4N', 'Atención', 'Puro', 'No viene reflejada', 0, 46),
('ZINC SULFATO 1-HIDRATO', '1 Kg', 1, 'Almacén 1/principal', '4N', 'Comburente y peligroso para el medio ambiente', '98 %', 'No viene reflejada', 0, 47),
('SODIO CARBONATO 10-HIDRATO', '1 Kg', 4, 'Almacén 1/principal', '3N', 'Irritante', '99 %', 'No viene reflejada', 0, 48),
('SODIO CARBONATO ANHIDRO', '100 g', 1, 'Almacén 1/principal', '3N', 'Irritante', 'Puro', 'No viene reflejada', 0, 49),
('SODIO CARBONATO ANHIDRO', '1 Kg', 1, 'Almacén 1/principal', '3N', 'Irritante', 'Puro', 'No viene reflejada', 0, 50),
('SODIO OXALATO', '1 Kg', 1, 'Almacén 1/principal', '3N', 'Nocivo', '98 %', 'No viene reflejada', 0, 51),
('SODIO SALICILATO', '250 g', 1, 'Almacén 1/principal', '3N', 'Nocivo', '99,5 %', 'No viene reflejada', 0, 52),
('SODIO SILICATO', '1 L', 1, 'Almacén 1/principal', '3N', 'Irritante', 'No viene reflejado', '7-2011', 0, 53),
('SODIO TUNGSTATO 2-HIDRATO', '250 g', 2, 'Almacén 1/principal', '3N', 'Nocivo', '99 %', 'No viene reflejada', 0, 54),
('POTASIO DE BROMURO', '500 g', 1, 'Almacén 1/principal', '3N', 'Atención', '98 %', 'No viene reflejada', 0, 55),
('POTASIO DE BROMURO', '500 g', 1, 'Almacén 1/principal', '3N', 'Atención', '99 %', 'No viene reflejada', 0, 56),
('POTASIO DE BROMURO', '250 g', 1, 'Almacén 1/principal', '3N', 'Atención', 'Puro', 'No viene reflejada', 0, 57),
('POTASIO DE BROMURO', '250 g', 1, 'Almacén 1/principal', '3N', 'Atención', '98 %', 'No viene reflejada', 0, 58),
('POTASIO CARBONATO PRS', '1 Kg', 2, 'Almacén 1/principal', '3N', 'Nocivo', '99 %', '5-2009 y 11-2012', 0, 59),
('POTASIO CLORATO', 'No viene reflejado', 1, 'Almacén 1/principal', '3N', 'Comburente y nocivo', '98,5 %', 'No viene reflejada', 0, 60),
('OXALATO DE POTASIO 1-HIDRATO', '500 g', 2, 'Almacén 1/principal', '3N', 'Toxicidad aguda', '99 %', 'No viene reflejada', 0, 61),
('POTASIO OXALATO', '1 Kg', 1, 'Almacén 1/principal', '3N', 'Atención', 'No viene reflejado', 'No viene reflejada', 0, 62),
('POTASIO BIOXALATO', '500 g', 1, 'Almacén 1/principal', '3N', 'Nocivo', '99 %', 'No viene reflejada', 0, 63),
('POTASIO BIOXALATO', '1 Kg', 1, 'Almacén 1/principal', '3N', 'Nocivo', '99 %', 'No viene reflejada', 0, 64),
('PERMANGANATO DE POTASIO', '250 g', 2, 'Almacén 1/principal', '3N', 'Comburente, nocivo y peligroso para el medio ambiente', 'No viene reflejado', '1-2-2022 y no viene reflejada', 0, 65),
('PERMANGANATO DE POTASIO', '100 g', 1, 'Almacén 1/principal', '3N', 'Comburente, nocivo y peligroso para el medio ambiente', 'No viene reflejado', '31-5-2015', 0, 66),
('PERMANGANATO DE POTASIO', '1 Kg', 1, 'Almacén 1/principal', '3N', 'Comburente, nocivo y peligroso para el medio ambiente', '99 %', '6-2028', 0, 67),
('PERMANGANATO DE POTASIO', 'No viene reflejado', 1, 'Almacén 1/principal', '3N', 'Comburente, nocivo y peligroso para el medio ambiente', 'Puro', 'No viene reflejada', 0, 68),
('POTASIO TIOCIANATO', '500 g', 2, 'Almacén 1/principal', '3N', 'Nocivo', '99 %', 'No viene reflejada', 0, 69),
('POTASIO TIOCIANATO', '500 g', 1, 'Almacén 1/principal', '3N', 'Nocivo', '98 %', 'No viene reflejada', 0, 70),
('BARIO ACETATO', '500 g', 1, 'Almacén 1/principal', '3N', 'Nocivo', '98 %', 'No viene reflejada', 0, 71),
('BARIO HIDRÓXIDO 8-HIDRATO', '500 g', 1, 'Almacén 1/principal', '3N', 'Nocivo', 'Puro', 'No viene reflejada', 0, 72),
('BARIO HIDRÓXIDO 8-HIDRATO', '1 Kg', 1, 'Almacén 1/principal', '3N', 'Nocivo', '98 %', 'No viene reflejada', 0, 73),
('CALCIO CLORURO ANHIDRO QP', '1 Kg', 1, 'Almacén 1/principal', '3N', 'Irritante', '95 %', '3-2015', 0, 74),
('CALCIO CLORURO 2-HIDRATO PRS', '5 Kg', 1, 'Almacén 1/principal', '3N', 'Irritante', '99 %', '10-2012', 0, 75),
('CALCIO OXOLATO-1-HIDRATO', '500 g', 1, 'Almacén 1/principal', '3N', 'Nocivo', '98 %', '10/2009', 0, 76),
('CALCIO HIDROXISO', '1 Kg', 1, 'Almacén 1/principal', '3N', 'Irritante', '95 %', '5/2009', 0, 77),
('HIDROXIHAMONIO CLORURO', '250 g', 1, 'Almacén 1/principal', '3N', 'Corrosivo, peligroso para el medio ambiente y carcinógeno', '99,55 %', 'No viene reflejada', 0, 78),
('AMONIO CLORURO', '1 Kg', 1, 'Almacén 1/principal', '3N', 'Atención', '100 %', 'No viene reflejada', 0, 79),
('ÁCIDO BENZOICO', '500 g', 1, 'Almacén 1/principal', '2N', 'Atención', '99,5 %', 'No viene reflejada', 0, 80),
('ÁCIDO BENZOICO', '1 Kg', 1, 'Almacén 1/principal', '2N', 'Nocivo', '99,5 %', '9-2016', 0, 81),
('4-DICLOROBENCENO', '500 g', 1, 'Almacén 1/principal', '2N', 'Toxicidad aguda', 'Puro', 'No viene reflejada', 0, 82),
('ÁCIDO 2-CLOROBENZOICO', '500 g', 1, 'Almacén 1/principal', '2N', 'Atención', '99 %', 'No viene reflejada', 0, 83),
('ÁCIDO 4-CLOROBENZOICO', '250 g', 1, 'Almacén 1/principal', '2N', 'Nocivo', '97 %', 'No viene reflejada', 0, 84),
('ÁCIDO 4-AMINOBENZOICO', '250 g', 1, 'Almacén 1/principal', '2N', 'Atención', '99 %', '31-7-2007', 0, 85),
('ÁCIDO 4-NITROBENZOICO', 'No viene reflejado', 1, 'Almacén 1/principal', '2N', 'Nocivo', '99 %', 'No viene reflejada', 0, 86),
('AMONÍACO', '1 L', 1, 'Almacén 1/principal', '2N', 'Corrosivo, peligroso para el medio ambiente, nocivo y irritante', '30 %', '2-2029', 0, 87),
('ISOBUTANOL', '1 L', 1, 'Almacén 1/principal', '2N', 'Irritante', '99 %', '6-2010', 0, 88),
('2-BUTANOL', '1 L', 1, 'Almacén 1/principal', '2N', 'Irritante', '99 %', '10-11-2004', 0, 89),
('3-METIL-1-BUTANOL SEGÚN GERBER', '1 L', 3, 'Almacén 1/principal', '2N', 'Nocivo', '98,5 %', 'No viene reflejada', 0, 90),
('CLORAMINA T', '250 g', 1, 'Almacén 1/principal', '2N', 'Irritante', '98 %', 'No viene reflejada', 0, 91),
('CLORAMINA T TRIHIDRATADA', '250 g', 1, 'Almacén 1/principal', '2N', 'Corrosivo', '99 %', 'No viene reflejada', 0, 92),
('DIMETILGLIOXIMA', '500 g', 1, 'Almacén 1/principal', '2N', 'Nocivo', '99 %', '10-2013', 0, 93),
('ÁCIDO 3,5-DINITROSALICÍLICO PS', '100 g', 1, 'Almacén 1/principal', '2N', 'Nocivo y irritante', '99 %', '10-2017', 0, 94),
('ETILENGLICOL', '1 L', 1, 'Almacén 1/principal', '2N', 'Nocivo', '99 %', 'No viene reflejada', 0, 95),
('AMONIO FORMATO', '1 Kg', 1, 'Almacén 1/principal', '2N', 'Atención', '99 %', 'No viene reflejada', 0, 96),
('CICLOHEXANOL', 'No viene reflejado', 1, 'Almacén 1/principal', '2N', 'Atención', 'Purísimo', 'No viene reflejada', 0, 97),
('DICLOROMETANO', '1 L', 1, 'Almacén 1/principal', '2N', 'Nocivo, irritante y carcinógeno', 'No viene reflejado', '1-2025', 0, 98),
('NAFTALENO PRS', '500 g', 1, 'Almacén 1/principal', '2N', 'Nocivo y peligroso para el medio ambiente', '98 %', '12-2011', 0, 99),
('NAFTALENO PRS', '500 g', 1, 'Almacén 1/principal', '2N', 'Nocivo y peligroso para el medio ambiente', '98 %', '9-2010', 0, 100),
('NAFTALENO', 'No viene reflejado', 1, 'Almacén 1/principal', '2N', 'Inflamable', 'Purísimo', 'No viene reflejada', 0, 101),
('1-NAFTILAMINA PS', '100 g', 1, 'Almacén 1/principal', '2N', 'Nocivo y peligroso para el medio ambiente', '99 %', 'No viene reflejada', 0, 102),
('1-NAFTOL', '250 g', 1, 'Almacén 1/principal', '2N', 'Nocivo', '99 %', 'No viene reflejada', 0, 103),
('p-NITROFENOL', '100 g', 1, 'Almacén 1/principal', '2N', 'Nocivo', '96 %', 'No viene reflejada', 0, 104),
('p-NITROFENOL', '250 g', 1, 'Almacén 1/principal', '2N', 'Nocivo', '96 %', 'No viene reflejada', 0, 105),
('ÁCIDO OXÁLICO 2-HIDRATO', '500 g', 2, 'Almacén 1/principal', '2N', 'Nocivo', '99 %', 'No viene reflejada', 0, 106),
('ÁCIDO OXÁLICO', '1 L', 1, 'Almacén 1/principal', '2N', 'Atención', 'No viene reflejado', '10-2011', 0, 107),
('ÁCIDO OXÁLICO CRISTALIZADO', 'No viene reflejado', 1, 'Almacén 1/principal', '2N', 'Toxicidad aguda', 'Purísimo', 'No viene reflejada', 0, 108),
('ÁCIDO SULFANÍLICO', '250 g', 2, 'Almacén 1/principal', '2N', 'Nocivo', '98 %', 'No viene reflejada', 0, 109),
('TRITÓN X', 'No viene reflejado', 1, 'Almacén 1/principal', '2N', 'Atención', 'No viene reflejado', 'No viene reflejada', 0, 110),
('ÁCIDO ACÉTICO GLACIAL', '1 L', 1, 'Almacén 1/principal', '1CA', 'Inflamable y corrosivo', '99,7 %', '7-2028', 0, 111),
('ÁCIDO ACÉTICO GLACIAL', '1 L', 1, 'Almacén 1/principal', '1CA', 'Inflamable y corrosivo', '99,9 %', 'No viene reflejada', 0, 112),
('ÁCIDO ACÉTICO GLACIAL', '2,5 L', 1, 'Almacén 1/principal', '1CA', 'Inflamable y corrosivo', '99,9 %', 'No viene reflejada', 0, 113),
('ÁCIDO ACÉTICO GLACIAL', '250 mL', 1, 'Almacén 1/principal', '1CA', 'Corrosivo', '99,5 %', '7-2014', 0, 114),
('ÁCIDO ACÉTICO', '1 L', 1, 'Almacén 1/principal', '1CA', 'Toxicidad aguda y corrosivo', '99,7 %', 'No viene reflejada', 0, 115),
('ANHÍDRICO ACÉTICO', '1 L', 1, 'Almacén 1/principal', '1CA', 'Corrosivo', '98 %', 'No viene reflejada', 0, 116),
('ÁCIDO CLORHÍDRICO', '2,5 L', 4, 'Almacén 1/principal', '1CA', 'Corrosivo, nocivo y irritante', '37 %', '5-6-2029', 0, 117),
('ÁCIDO CLORHÍDRICO', '1 L', 3, 'Almacén 1/principal', '1CA', 'Corrosivo, nocivo y irritante', '37 %', '2-2028', 0, 118),
('ÁCIDO CLORHÍDRICO', '1 L', 1, 'Almacén 1/principal', '1CA', 'Corrosivo, nocivo y irritante', '37 %', '2-11-2013', 0, 119),
('ÁCIDO PERCLÓRICO EN ÁCIDO ACÉTICO', '1 L', 2, 'Almacén 1/principal', '1CA', 'Inflamable y corrosivo', 'No viene reflejado', '1-2030', 0, 120),
('ÁCIDO PERCLÓRICO', '1 L', 1, 'Almacén 1/principal', '1CA', 'Comburente y corrosivo', '60 %', '6-2010', 0, 121),
('ÁCIDO ORTOFOSFÓRICO', '1 L', 1, 'Almacén 1/principal', '1CA', 'Corrosivo', '85 %', '9-2014', 0, 122),
('ÁCIDO ORTOFOSFÓRICO', '1 L', 1, 'Almacén 1/principal', '1CA', 'Corrosivo', '85 %', '12-2008', 0, 123),
('ÁCIDO NÍTRICO', '1 L', 2, 'Almacén 1/principal', '1CA', 'Comburente, toxicidad aguda y corrosivo', '65 %', '11-2024', 0, 124),
('ÁCIDO NÍTRICO', '1 L', 1, 'Almacén 1/principal', '1CA', 'Comburente, toxicidad aguda y corrosivo', '65 %', '8-2024', 0, 125),
('ÁCIDO NÍTRICO', '1 L', 3, 'Almacén 1/principal', '1CA', 'Corrosivo', '70 %', 'No viene reflejada', 0, 126),
('ÁCIDO SULFÚRICO', 'No viene reflejado', 3, 'Almacén 1/principal', '1CA', 'Atención', '96 %', 'No viene reflejada', 0, 127),
('ÁCIDO SULFÚRICO', '1 L', 1, 'Almacén 1/principal', '1CA', 'Corrosivo', '98 %', '3-2025', 0, 128),
('ÁCIDO FLUORHÍDRICO', '250 mL', 1, 'Almacén 1/principal', '1CA', 'Toxicidad aguda y corrosivo.', 'No viene reflejado', 'No viene reflejada', 0, 129),
('ÁCIDO FÓRMICO', '500 mL', 1, 'Almacén 1/principal', '1CA', 'Corrosivo', '95 %', 'No viene reflejada', 0, 130),
('ÁCIDO ORTO-FOSFÓRICO', '1 L', 1, 'Almacén 1/principal', '1CA', 'Corrosivo', '85 %', '12-2008', 0, 131),
('REACTIVO DE HANUS', '1 L', 3, 'Almacén 1/principal', '1CA', 'Corrosivo', 'No viene reflejado', 'No viene reflejada', 0, 132),
('REACTIVO DE HANUS', '1 L', 1, 'Almacén 1/principal', '1CA', 'Corrosivo', 'No viene reflejado', '11-2012', 0, 133),
('DIETANOLAMINA', '100 mL', 1, 'Almacén 1/principal', '1CA', 'Atención', '99 %', 'No viene reflejada', 0, 134),
('CLOROACETILO CLORURO RPE', '100 mL', 1, 'Almacén 1/principal', '1CA', 'Corrosivo', 'Puro', 'No viene reflejada', 0, 135),
('SODIO ACETATO ANHIDRO', 'No viene reflejado', 1, 'Almacén 1/principal', '8l', 'Atención', '99 %', 'No viene reflejada', 0, 136),
('SODIO TETRATO 2 HIDRATO', '500 g', 1, 'Almacén 1/principal', '8l', 'Atención', '99 %', 'No viene reflejada', 0, 137),
('SODIO TETRA-BORATO 10 HIDRATO', '250 g,1 kg', 2, 'Almacén 1/principal', '8l', 'Atención', 'No viene reflejado', 'No viene reflejada', 0, 138),
('TETRABORATO SODICO', 'No viene reflejado', 1, 'Almacén 1/principal', '8l', 'Atención', 'No viene reflejado', 'No viene reflejada', 0, 139),
('SODIO TETRA-FENILBORATO', '10 g', 4, 'Almacén 1/principal', '8l', 'Atención', '99 %', 'No viene reflejada', 0, 140),
('SODIO HIDROGENOCARBONATO', '1 Kg', 1, 'Almacén 1/principal', '8l', 'Atención', '99,9 %', 'No viene reflejada', 0, 141),
('SODIO HIDROGENOCARBONATO', '1 Kg', 2, 'Almacén 1/principal', '8l', 'Atención', '99 %', 'No viene reflejada', 0, 142),
('CARBOXIMETILCELULOSA SAL SODICA', '250 g', 1, 'Almacén 1/principal', '8l', 'Atención', '99,5 %', 'No viene reflejada', 0, 143),
('SODIO FOSFATO MONO-BASICO 2-HIDRATO', '250 g', 1, 'Almacén 1/principal', '8l', 'Atención', '99,10 %', '2-2012', 0, 144),
('SODIO FOSFATO MONO-BASICO 2-HIDRATO', '250 g', 1, 'Almacén 1/principal', '8l', 'Atención', 'Puro', 'No viene reflejada', 0, 145),
('SODIO FOSFATO MONO-BASICO 1-HIDRATO', '500 g', 2, 'Almacén 1/principal', '8l', 'Atención', '99 %', 'No viene reflejada', 0, 146),
('SODIO FOSFATO MONO-BASICO 2-HIDRATO', '500 g', 1, 'Almacén 1/principal', '8l', 'Atención', '99 %', 'No viene reflejada', 0, 147),
('SODIO FOSFATO DI-BASICO ANHIDRATO', '500 g', 1, 'Almacén 1/principal', '8l', 'Atención', '98 %', 'No viene reflejada', 0, 148),
('SODIO HIDRÓGENO FOSFATO ANHIDRO', '1 Kg', 1, 'Almacén 1/principal', '8l', 'Atención', '99 %', '28-2-2007', 0, 149),
('FOSFATO TRISÓDICO ANHIDRO', '500 g', 1, 'Almacén 1/principal', '8l', 'Atención', 'No viene reflejado', 'No viene reflejada', 0, 150),
('DI-SODIO HIDRÓGENO FOSFATO ANHIDRO', '500 g', 1, 'Almacén 1/principal', '8l', 'Atención', '98 %', 'No viene reflejada', 0, 151),
('DI-SODIO HIDRÓGENO FOSFATO ANHIDRO', '500 g', 1, 'Almacén 1/principal', '8l', 'Atención', '99 %', 'No viene reflejada', 0, 152),
('ÁCIDO ETILENDIAMINOTETRAACÉTICO SAL DISÓDICA 2-HIDRATO', '250 g', 1, 'Almacén 1/principal', '8l', 'Nocivo', '99 %', '11-2011', 0, 153),
('SODIO MOLIBDATO 2-HIDRATO', '1 Kg', 1, 'Almacén 1/principal', '8l', 'Atención', '99 %', 'No viene reflejada', 0, 154),
('SODIO MOLIBDATO CRIST', '1 Kg', 1, 'Almacén 1/principal', '8l', 'Atención', '99 %', 'No viene reflejada', 0, 155),
('MOLIBDENO (VI) ÓXIDO', '250 g', 2, 'Almacén 1/principal', '8l', 'Toxicidad aguda', '99 %', 'No viene reflejada', 0, 156);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  `contrasena` varchar(100) NOT NULL,
  `es_admin` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre_usuario`, `contrasena`, `es_admin`) VALUES
(1, 'profesor', 'profesor', 1),
(2, 'alumno', 'alumno', 0);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `materiales`
--
ALTER TABLE `materiales`
  ADD PRIMARY KEY (`Código`);

--
-- Indices de la tabla `prod_aux`
--
ALTER TABLE `prod_aux`
  ADD PRIMARY KEY (`Codigo`);

--
-- Indices de la tabla `reactivos`
--
ALTER TABLE `reactivos`
  ADD PRIMARY KEY (`Codigo`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre_usuario` (`nombre_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `materiales`
--
ALTER TABLE `materiales`
  MODIFY `Código` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `prod_aux`
--
ALTER TABLE `prod_aux`
  MODIFY `Codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `reactivos`
--
ALTER TABLE `reactivos`
  MODIFY `Codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=157;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
