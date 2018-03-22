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

-- Dumping structure for table iris_bulacan_dost3.equipment_qoutation
CREATE TABLE IF NOT EXISTS `equipment_qoutation` (
  `qoute_code` varchar(50) NOT NULL,
  `fk_supplier_code` varchar(50) DEFAULT NULL,
  `equipment_name` varchar(50) DEFAULT NULL,
  `qoutation_date` datetime DEFAULT NULL,
  `specification` varchar(50) DEFAULT NULL,
  `remarks` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `file_qoute_attachment` int(11) DEFAULT NULL,
  `search_keys` varchar(50) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`qoute_code`),
  KEY `equipment_qoutation_supplier_code` (`fk_supplier_code`),
  CONSTRAINT `equipment_qoutation_supplier_code` FOREIGN KEY (`fk_supplier_code`) REFERENCES `equipment_supplier` (`supplier_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.equipment_qoutation: ~5 rows (approximately)
/*!40000 ALTER TABLE `equipment_qoutation` DISABLE KEYS */;
INSERT INTO `equipment_qoutation` (`qoute_code`, `fk_supplier_code`, `equipment_name`, `qoutation_date`, `specification`, `remarks`, `status`, `file_qoute_attachment`, `search_keys`, `deleted_at`) VALUES
	('30002018-0319104945', NULL, 'Table', NULL, '', '', 'CANVASSED', NULL, '', '2018-03-21 11:58:32'),
	('30002018-0319105653', NULL, 'Cutting Table', '2018-03-05 00:00:00', '', '', 'CANVASSED', NULL, 'table, kitchen, food', NULL),
	('30002018-0319111019', NULL, 'Chiller', '2018-03-14 00:00:00', '', '', 'ACQUIRED', NULL, '', '2018-03-21 11:55:50'),
	('30002018-0321120239', NULL, 'Embroider', '2018-02-28 00:00:00', '', '', 'ACQUIRED', NULL, '', NULL),
	('30002018-0321153446', NULL, 'High Tech Super Computer', '2018-03-15 00:00:00', '', '', 'CANVASSED', NULL, 'computer, ict, electronics', NULL);
/*!40000 ALTER TABLE `equipment_qoutation` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.equipment_supplier
CREATE TABLE IF NOT EXISTS `equipment_supplier` (
  `supplier_code` varchar(50) NOT NULL,
  `supplier_name` varchar(50) DEFAULT NULL,
  `mobile_no` varchar(50) DEFAULT NULL,
  `telephone_no` varchar(50) DEFAULT NULL,
  `fax_no` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `website_address` varchar(50) DEFAULT NULL,
  `sector` int(11) DEFAULT NULL,
  `dost_accredited` varchar(50) DEFAULT NULL,
  `supplier_address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`supplier_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.equipment_supplier: ~0 rows (approximately)
/*!40000 ALTER TABLE `equipment_supplier` DISABLE KEYS */;
INSERT INTO `equipment_supplier` (`supplier_code`, `supplier_name`, `mobile_no`, `telephone_no`, `fax_no`, `email`, `website_address`, `sector`, `dost_accredited`, `supplier_address`) VALUES
	('30002018-0322111406', 'Microsoft', '', '', NULL, '', '', 6, 'NO', ''),
	('30002018-0322111550', 'Microsoft Technologies', '09368955866', '044 215 2145', NULL, 'info@microsoft.com', 'microsoft.com', 6, 'YES', '');
/*!40000 ALTER TABLE `equipment_supplier` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.file_table
CREATE TABLE IF NOT EXISTS `file_table` (
  `id` varchar(50) NOT NULL,
  `file_display_name` varchar(50) DEFAULT NULL,
  `file_description` varchar(50) DEFAULT NULL,
  `file_path` varchar(50) DEFAULT NULL,
  `file_name` varchar(50) DEFAULT NULL,
  `file_ext` varchar(50) DEFAULT NULL,
  `file_size` varchar(50) DEFAULT NULL,
  `file_hash` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.file_table: ~0 rows (approximately)
/*!40000 ALTER TABLE `file_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `file_table` ENABLE KEYS */;

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
  `file_endorsed_attachment` int(11) DEFAULT NULL,
  `approved_date` datetime DEFAULT NULL,
  `approved_funding` double DEFAULT NULL,
  `file_approved_attachment` int(11) DEFAULT NULL,
  `moa_date` datetime DEFAULT NULL,
  `file_moa_attachment` int(11) DEFAULT NULL,
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

-- Dumping data for table iris_bulacan_dost3.setup_projects: ~6 rows (approximately)
/*!40000 ALTER TABLE `setup_projects` DISABLE KEYS */;
INSERT INTO `setup_projects` (`project_code`, `spin_no`, `company_name`, `company_owner`, `owner_position`, `owner_address`, `project_name`, `project_status`, `project_type`, `endorsed_date`, `file_endorsed_attachment`, `approved_date`, `approved_funding`, `file_approved_attachment`, `moa_date`, `file_moa_attachment`, `actual_cost`, `duration_from`, `duration_to`, `factory_street`, `factory_brgy`, `factory_city`, `factory_landmark`, `year_established`, `business_activity`, `capital_classification`, `employment_classification`, `company_ownership`, `profitability`, `registration_info`, `major_products`, `existing_market`, `website`, `deleted_at`) VALUES
	('STC30002018-0313104030', '', 'Marissa Sweets & Delicacies', 'Maricar DC. Ausan', 'Owner', '', 'Product and Process Improvement through the Adoption of Appropriate Technology for Marissa Sweets and Delicacies', 1, 'SETUP', NULL, NULL, '2017-05-26 00:00:00', 157789.02, NULL, '2017-06-09 00:00:00', NULL, 157789.02, '2017-06-01 00:00:00', '2018-05-01 00:00:00', '579 SITIO BATUHAN', 'BULIRAN', '3011', '', '2013', 1, 'MICRO', 'MICRO', 'SINGLE PROPRIETORSHIP', 'PROFIT', 'DTI REGISTRATION NO. 02047377 valid until May 2018', 'Pastillas De Leche, Yema, Ube', 'Retails Outlet, Pasalubong Center, Bus Stations in Tarlac and Pampanga', '', NULL),
	('STC30002018-0313105718', '', 'Jhul\'z Creamy Delights Cakes and Pastries', '', '', '', 'Ensuring Food Safety and Product Quality through the Application of Improved and Advanced Technologies for Jhul\'z Creamy Delights Cakes and Pastries', 1, 'SETUP', NULL, NULL, '2017-05-26 00:00:00', 350000, NULL, '2017-06-09 00:00:00', NULL, 373860, '2017-06-01 00:00:00', '2018-05-01 00:00:00', 'B-52 L-29 Area 1 Assumption', 'Sapang Palay', '3023', '', '', 1, 'MICRO', 'MICRO', 'SINGLE PROPRIETORSHIP', 'PROFIT', 'DTI REGISTRATION NO. 03950325 Valid until February 12 2021', 'Cakes and Pastries', 'Retailers such as Suisie\'s Cuisine', '', NULL),
	('STC30002018-0313111439', '', 'FGCA Machinery', '', '', '', '', 1, 'SETUP', NULL, NULL, NULL, 965000, NULL, '2017-11-21 00:00:00', NULL, 965000, '2017-11-01 00:00:00', '2018-10-01 00:00:00', '2292 La Trinidad Subdivision', 'Lolomboy', '3018', 'tapat ng iglesia ni cristo', '1971', 1, 'MEDIUM', 'MICRO', 'SINGLE PROPRIETORSHIP', 'PROFIT', 'dti 03597180 valid until May 2020', '', '', '', NULL),
	('STC30002018-0313133218', '', 'P. Zuñiga Jewelry Store', '', '', '', 'Introduction of Ring Maker CNC Machine in the Production of Jewelries for P. Zuñiga Jewelry Store', 1, 'SETUP', NULL, NULL, '2017-05-26 00:00:00', 1, NULL, '2017-06-16 00:00:00', NULL, 1646400, '2017-06-01 00:00:00', '2018-05-01 00:00:00', '30 Bagong Buhay, Malhacan Road', 'Malhacan', '3020', '', '1982', 4, 'MICRO', 'MICRO', 'SINGLE PROPRIETORSHIP', 'PROFIT', 'DTI Registration No. 04126142 Valid Until July 19 2021', 'Jewelries and Repair Services', 'Walk-in clients, distributors, store outlet', '', NULL),
	('STC30002018-0313134507', '', 'Allied Metals, Inc.', '', '', '', 'Introduction of Robotic Arm Welding Machine in the Production of Stainless Steel Sink, Bowls, Pans, Trench Drains for Allied Metals, Inc. Phase II', 1, 'SETUP', '2016-05-26 00:00:00', NULL, '2016-06-24 00:00:00', 0, NULL, '2017-07-13 00:00:00', NULL, 1898800, '2017-03-01 00:00:00', '2018-02-01 00:00:00', '2000 Governor Halili Highway', 'Muzon', '3023', '', '1965', 4, 'MEDIUM', 'MEDIUM', 'CORPORATION', 'PROFIT', 'sec reg# 26448', 'kitchen equiment', 'Hotels, Resorts, Restaurants & Bar, Hospitals, Culinary Schools, Pharma Companies', 'alliedmetals.com.ph', NULL),
	('STC30002018-0313144906', '', 'Eleven Fourteen Sweet Candies', '', '', '', 'Ensuring Food Safety and Product Quality Through Application of Improved and Advanced Technologies for Eleven Fourteen Sweet Candies', 1, 'SETUP', '2015-12-11 00:00:00', NULL, '2015-12-29 00:00:00', 340000, NULL, '2016-02-10 00:00:00', NULL, 0, '2016-01-01 00:00:00', '2016-12-01 00:00:00', '#1065', 'San Juan', '3011', '', '', 1, 'MICRO', 'MICRO', 'SINGLE PROPRIETORSHIP', 'PROFIT', '', '', '', '', NULL);
/*!40000 ALTER TABLE `setup_projects` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.setup_projects_contact
CREATE TABLE IF NOT EXISTS `setup_projects_contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_setup_project_code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `position` varchar(50) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `landline` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `spc_fk_sp_code` (`fk_setup_project_code`),
  CONSTRAINT `spc_fk_sp_code` FOREIGN KEY (`fk_setup_project_code`) REFERENCES `setup_projects` (`project_code`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.setup_projects_contact: ~4 rows (approximately)
/*!40000 ALTER TABLE `setup_projects_contact` DISABLE KEYS */;
INSERT INTO `setup_projects_contact` (`id`, `fk_setup_project_code`, `name`, `position`, `mobile`, `landline`, `email`, `deleted_at`) VALUES
	(9, 'STC30002018-0313104030', 'owner', '', '09264363929', '', 'ilovemarissasweets@yahoo.com', NULL),
	(10, 'STC30002018-0313105718', 'Julie S. Dela Cruz', 'Owner', '0922 786 0182', '', '', NULL),
	(11, 'STC30002018-0313133218', 'Pedro A. Zuñiga', 'Owner/Manager', '0917 817 7775 / 094 999 89049', '044 815 5623', 'pzunigajewellery@gmail.com', NULL),
	(12, 'STC30002018-0313134507', 'Domingo U. Castro', 'Operations Manager', '09177949077', '02 721 1349', 'cris.castro@alliedmetals.com.ph', NULL);
/*!40000 ALTER TABLE `setup_projects_contact` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
