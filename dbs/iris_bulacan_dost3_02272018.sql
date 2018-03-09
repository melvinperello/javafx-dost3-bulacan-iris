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

-- Dumping structure for table iris_bulacan_dost3.setup_projects
CREATE TABLE IF NOT EXISTS `setup_projects` (
  `project_code` varchar(50) NOT NULL,
  `spin_no` varchar(50) DEFAULT NULL,
  `company_name` varchar(50) DEFAULT NULL,
  `company_owner` varchar(50) DEFAULT NULL,
  `owner_position` varchar(50) DEFAULT NULL,
  `owner_address` varchar(50) DEFAULT NULL,
  `project_name` varchar(50) DEFAULT NULL,
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
  `factory_street` varchar(50) DEFAULT NULL,
  `factory_brgy` varchar(50) DEFAULT NULL,
  `factory_city` varchar(50) DEFAULT NULL,
  `factory_long` varchar(50) DEFAULT NULL,
  `factory_lat` varchar(50) DEFAULT NULL,
  `factory_landmark` varchar(50) DEFAULT NULL,
  `year_established` varchar(50) DEFAULT NULL,
  `business_activity` int(11) DEFAULT NULL,
  `capital_classification` varchar(50) DEFAULT NULL,
  `employment_classification` varchar(50) DEFAULT NULL,
  `company_ownership` varchar(50) DEFAULT NULL,
  `profitability` varchar(50) DEFAULT NULL,
  `registration_info` varchar(50) DEFAULT NULL,
  `major_products` varchar(50) DEFAULT NULL,
  `existing_market` varchar(50) DEFAULT NULL,
  `website` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`project_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.setup_projects: ~0 rows (approximately)
/*!40000 ALTER TABLE `setup_projects` DISABLE KEYS */;
INSERT INTO `setup_projects` (`project_code`, `spin_no`, `company_name`, `company_owner`, `owner_position`, `owner_address`, `project_name`, `project_status`, `project_type`, `endorsed_date`, `endorsed_attachment`, `approved_date`, `approved_funding`, `approved_attachment`, `moa_date`, `moa_attachment`, `actual_cost`, `duration_from`, `duration_to`, `factory_street`, `factory_brgy`, `factory_city`, `factory_long`, `factory_lat`, `factory_landmark`, `year_established`, `business_activity`, `capital_classification`, `employment_classification`, `company_ownership`, `profitability`, `registration_info`, `major_products`, `existing_market`, `website`) VALUES
	('STC30002018-0304081418', '', 'TEXICON', 'asda', 'sda', 'sdasdasd', '', 1, 'GIA', '2018-03-14 00:00:00', NULL, '2018-02-28 00:00:00', 1000, NULL, NULL, NULL, 1000, '2018-03-14 00:00:00', '2018-03-18 00:00:00', '', '', '3012', '', '', '', '', 1, 'MICRO', 'MICRO', 'SINGLE PROPRIETORSHIP', 'PROFIT', '', '', '', '');
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.setup_projects_contact: ~4 rows (approximately)
/*!40000 ALTER TABLE `setup_projects_contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `setup_projects_contact` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
