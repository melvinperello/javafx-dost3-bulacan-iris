-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.2.10-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win32
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for iris_bulacan_dost3
CREATE DATABASE IF NOT EXISTS `iris_bulacan_dost3` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `iris_bulacan_dost3`;

-- Dumping structure for table iris_bulacan_dost3.filedata
CREATE TABLE IF NOT EXISTS `filedata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '0',
  `location` varchar(50) NOT NULL DEFAULT '0',
  `hash` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.filedata: ~0 rows (approximately)
/*!40000 ALTER TABLE `filedata` DISABLE KEYS */;
/*!40000 ALTER TABLE `filedata` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.setup_projects
CREATE TABLE IF NOT EXISTS `setup_projects` (
  `project_code` varchar(50) NOT NULL,
  `spin_no` varchar(300) DEFAULT NULL,
  `company_name` varchar(300) DEFAULT NULL,
  `company_owner` varchar(300) DEFAULT NULL,
  `owner_position` varchar(300) DEFAULT NULL,
  `owner_address` varchar(300) DEFAULT NULL,
  `project_name` varchar(300) DEFAULT NULL,
  `project_status` tinyint(4) DEFAULT NULL,
  `project_type` varchar(50) DEFAULT NULL,
  `endorsed_date` datetime DEFAULT NULL,
  `endorsed_attachment` int(11) DEFAULT NULL,
  `approved_date` datetime DEFAULT NULL,
  `approved_funding` double DEFAULT NULL,
  `approved_attachment` int(11) DEFAULT NULL,
  `moa_date` datetime DEFAULT NULL,
  `moa_attachment` int(11) DEFAULT NULL,
  `actual_cost` double DEFAULT NULL,
  `duration_from` datetime DEFAULT NULL,
  `duration_to` datetime DEFAULT NULL,
  `factory_street` varchar(300) DEFAULT NULL,
  `factory_brgy` varchar(300) DEFAULT NULL,
  `factory_city` varchar(50) DEFAULT NULL COMMENT 'ZIP Code',
  `factory_landmark` varchar(300) DEFAULT NULL,
  `year_established` varchar(100) DEFAULT NULL,
  `business_activity` int(11) DEFAULT NULL,
  `capital_classification` varchar(100) DEFAULT NULL,
  `employment_classification` varchar(100) DEFAULT NULL,
  `company_ownership` varchar(100) DEFAULT NULL,
  `profitability` varchar(100) DEFAULT NULL,
  `registration_info` varchar(300) DEFAULT NULL,
  `major_products` varchar(300) DEFAULT NULL,
  `existing_market` varchar(300) DEFAULT NULL,
  `website` varchar(300) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`project_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.setup_projects: ~4 rows (approximately)
/*!40000 ALTER TABLE `setup_projects` DISABLE KEYS */;
INSERT INTO `setup_projects` (`project_code`, `spin_no`, `company_name`, `company_owner`, `owner_position`, `owner_address`, `project_name`, `project_status`, `project_type`, `endorsed_date`, `endorsed_attachment`, `approved_date`, `approved_funding`, `approved_attachment`, `moa_date`, `moa_attachment`, `actual_cost`, `duration_from`, `duration_to`, `factory_street`, `factory_brgy`, `factory_city`, `factory_landmark`, `year_established`, `business_activity`, `capital_classification`, `employment_classification`, `company_ownership`, `profitability`, `registration_info`, `major_products`, `existing_market`, `website`, `deleted_at`) VALUES
	('STC30002018-0304081418', '', 'SAMPLE', 'asda', 'sda', 'sdasdasd', 'Improve the equiments', 1, 'GIA', '2018-03-14 00:00:00', NULL, '2018-02-28 00:00:00', 2000, NULL, '2018-04-04 00:00:00', NULL, 1000, '2018-03-14 00:00:00', '2018-03-18 00:00:00', '503 Villa Nieto L Gonzales', 'Bunducan', '3018', '', '', 1, 'MICRO', 'MICRO', 'SINGLE PROPRIETORSHIP', 'PROFIT', '', '', '', 'www.website.com', NULL),
	('STC30002018-0313104030', '', 'Marissa Sweets & Delicacies', 'Maricar DC. Ausan', 'Owner', '', 'Product and Process Improvement through the Adoption of Appropriate Technology for Marissa Sweets and Delicacies', 1, 'SETUP', NULL, NULL, '2017-05-26 00:00:00', 157789.02, NULL, '2017-06-09 00:00:00', NULL, 157789.02, '2017-06-01 00:00:00', '2018-05-01 00:00:00', '579 SITIO BATUHAN', 'BULIRAN', '3011', '', '2013', 1, 'MICRO', 'MICRO', 'SINGLE PROPRIETORSHIP', 'PROFIT', 'DTI REGISTRATION NO. 02047377 valid until May 2018', 'Pastillas De Leche, Yema, Ube', 'Retails Outlet, Pasalubong Center, Bus Stations in Tarlac and Pampanga', '', NULL),
	('STC30002018-0313105718', '', 'Jhul\'z Creamy Delights Cakes and Pastries', '', '', '', 'Ensuring Food Safety and Product Quality through the Application of Improved and Advanced Technologies for Jhul\'z Creamy Delights Cakes and Pastries', 1, 'SETUP', NULL, NULL, '2017-05-26 00:00:00', 350000, NULL, '2017-06-09 00:00:00', NULL, 373860, '2017-06-01 00:00:00', '2018-05-01 00:00:00', 'B-52 L-29 Area 1 Assumption', 'Sapang Palay', '3023', '', '', 1, 'MICRO', 'MICRO', 'SINGLE PROPRIETORSHIP', 'PROFIT', 'DTI REGISTRATION NO. 03950325 Valid until February 12 2021', 'Cakes and Pastries', 'Retailers such as Suisie\'s Cuisine', '', NULL),
	('STC30002018-0313111439', '', 'FGCA Machinery', '', '', '', '', 1, 'SETUP', NULL, NULL, NULL, 965000, NULL, '2017-11-21 00:00:00', NULL, 965000, '2017-11-01 00:00:00', '2018-10-01 00:00:00', '2292 La Trinidad Subdivision', 'Lolomboy', '3018', 'tapat ng iglesia ni cristo', '1971', 1, 'MEDIUM', 'MICRO', 'SINGLE PROPRIETORSHIP', 'PROFIT', 'dti 03597180 valid until May 2020', '', '', '', NULL);
/*!40000 ALTER TABLE `setup_projects` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.setup_projects_contact
CREATE TABLE IF NOT EXISTS `setup_projects_contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `setup_project_code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `position` varchar(50) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `landline` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `spc_fk_sp_code` (`setup_project_code`),
  CONSTRAINT `spc_fk_sp_code` FOREIGN KEY (`setup_project_code`) REFERENCES `setup_projects` (`project_code`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.setup_projects_contact: ~4 rows (approximately)
/*!40000 ALTER TABLE `setup_projects_contact` DISABLE KEYS */;
INSERT INTO `setup_projects_contact` (`id`, `setup_project_code`, `name`, `position`, `mobile`, `landline`, `email`, `deleted_at`) VALUES
	(7, 'STC30002018-0304081418', 'Jhon Melvin Nieto Perello', 'Owner', '0936 8955 866', '044 054 1241', 'jhmvinperello@gmail.com', NULL),
	(8, 'STC30002018-0304081418', 'Juan Dela Cruz', 'Owner', '0936 8955 866', '', '', '2018-03-13 09:03:03'),
	(9, 'STC30002018-0313104030', 'owner', '', '09264363929', '', 'ilovemarissasweets@yahoo.com', NULL),
	(10, 'STC30002018-0313105718', 'Julie S. Dela Cruz', 'Owner', '0922 786 0182', '', '', NULL);
/*!40000 ALTER TABLE `setup_projects_contact` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
