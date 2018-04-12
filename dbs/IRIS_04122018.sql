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

-- Dumping structure for table iris_bulacan_dost3.contact_information
CREATE TABLE IF NOT EXISTS `contact_information` (
  `contact_id` varchar(50) NOT NULL,
  `organization` varchar(50) DEFAULT NULL,
  `org_type` varchar(50) DEFAULT NULL,
  `office_name` varchar(50) DEFAULT NULL,
  `contact_person` varchar(50) DEFAULT NULL,
  `tel_no` varchar(50) DEFAULT NULL,
  `fax_no` varchar(50) DEFAULT NULL,
  `mobile_no` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(50) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.contact_information: ~7 rows (approximately)
/*!40000 ALTER TABLE `contact_information` DISABLE KEYS */;
INSERT INTO `contact_information` (`contact_id`, `organization`, `org_type`, `office_name`, `contact_person`, `tel_no`, `fax_no`, `mobile_no`, `email`, `created_by`, `created_at`, `updated_by`, `updated_at`, `deleted_by`, `deleted_at`) VALUES
	('BUL30002018-0330195516', 'bulsu', 'OTHERS', 'registrar', 'leila', '11', '2', '3', '4', NULL, NULL, NULL, NULL, NULL, '2018-03-30 20:11:26'),
	('BUL30002018-0330223916', 'bulsu', 'ACADEME', 'registrar', 'asd', '', '', '', '', NULL, NULL, NULL, NULL, NULL, '2018-03-30 22:39:36'),
	('BUL30002018-0330232136', 'bsu', 'ACADEME', '', '', '', '', '09368955866', '', NULL, NULL, NULL, NULL, NULL, '2018-03-31 20:02:43'),
	('BUL30002018-0331231108', 'ewan', 'OTHERS', '23', '', '', '', '', '', 'JHON MELVIN NIETO PERELLO/IRIS-SYS-BUL3000', '2018-03-31 23:11:11', 'JHON MELVIN NIETO PERELLO/IRIS-SYS-BUL3000', '2018-03-31 23:11:16', 'JHON MELVIN NIETO PERELLO/IRIS-SYS-BUL3000', '2018-03-31 23:11:30'),
	('BUL30002018-0401111233', 'dr yanga', 'OTHERS', '', '', '', '', '', '', 'JHON MELVIN NIETO PERELLO/IRIS-SYS-BUL3000', '2018-04-01 11:12:38', '', NULL, 'JHON MELVIN NIETO PERELLO/IRIS-SYS-BUL3000', '2018-04-01 11:14:27'),
	('BUL30002018-0401111241', 'dr yanga', 'OTHERS', '', '', '', '', '', '', 'JHON MELVIN NIETO PERELLO/IRIS-SYS-BUL3000', '2018-04-01 11:12:44', '', NULL, 'JHON MELVIN NIETO PERELLO/IRIS-SYS-BUL3000', '2018-04-01 11:14:31'),
	('BUL30002018-0411142829', 'asd', 'OTHERS', '', '', '', '', '', '', 'IRIS3000/SYS', '2018-04-11 14:28:30', 'IRIS3000/SYS', '2018-04-11 14:28:37', '', NULL);
/*!40000 ALTER TABLE `contact_information` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.equipment_qoutation
CREATE TABLE IF NOT EXISTS `equipment_qoutation` (
  `qoute_code` varchar(50) NOT NULL,
  `fk_supplier_code` varchar(50) DEFAULT NULL,
  `equipment_name` varchar(50) DEFAULT NULL,
  `qoutation_date` datetime DEFAULT NULL,
  `specification` varchar(50) DEFAULT NULL,
  `remarks` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `file_qoute_attachment` varchar(50) DEFAULT NULL,
  `search_keys` varchar(50) DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(50) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`qoute_code`),
  KEY `equipment_qoutation_supplier_code` (`fk_supplier_code`),
  CONSTRAINT `equipment_qoutation_supplier_code` FOREIGN KEY (`fk_supplier_code`) REFERENCES `equipment_supplier` (`supplier_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.equipment_qoutation: ~4 rows (approximately)
/*!40000 ALTER TABLE `equipment_qoutation` DISABLE KEYS */;
INSERT INTO `equipment_qoutation` (`qoute_code`, `fk_supplier_code`, `equipment_name`, `qoutation_date`, `specification`, `remarks`, `status`, `file_qoute_attachment`, `search_keys`, `created_by`, `created_at`, `updated_by`, `updated_at`, `deleted_by`, `deleted_at`) VALUES
	('BUL30002018-0328103231', NULL, 'Ultrasonic Scissors', NULL, '', '', 'CANVASSED', NULL, '', '', NULL, '', NULL, '', '2018-03-31 21:31:17'),
	('BUL30002018-0328103240', NULL, 'Electro Magnetic Eraser', NULL, '', '', 'CANVASSED', NULL, '', '', NULL, '', NULL, 'JHON MELVIN NIETO PERELLO/IRIS-SYS-BUL3000', '2018-03-31 23:04:46'),
	('BUL30002018-0331222107', NULL, 'Kamot Chopper', NULL, '', '', 'ACQUIRED', NULL, '', 'Jhon Melvin Nieto Perello/BUL30002018-0330232136', '2018-03-31 22:21:14', 'Jhon Melvin Nieto Perello/BUL30002018-0330232136', '2018-03-31 22:21:35', 'Jhon Melvin Nieto Perello/BUL30002018-0330232136', '2018-03-31 22:21:54'),
	('BUL30002018-0401150845', NULL, 'asdddd', NULL, '', '', 'CANVASSED', NULL, '', 'JHON MELVIN NIETO PERELLO/IRIS-SYS-BUL3000', '2018-04-01 15:08:47', 'IRIS3000/SYS', '2018-04-12 13:40:26', '', NULL);
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
  `created_by` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(50) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`supplier_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.equipment_supplier: ~4 rows (approximately)
/*!40000 ALTER TABLE `equipment_supplier` DISABLE KEYS */;
INSERT INTO `equipment_supplier` (`supplier_code`, `supplier_name`, `mobile_no`, `telephone_no`, `fax_no`, `email`, `website_address`, `sector`, `dost_accredited`, `supplier_address`, `created_by`, `created_at`, `updated_by`, `updated_at`, `deleted_by`, `deleted_at`) VALUES
	('BUL30002018-0328103308', 'Mongol', '', '', NULL, '', '', 3, 'NO', '', NULL, NULL, NULL, NULL, NULL, '2018-03-28 10:33:25'),
	('BUL30002018-0328103505', 'Mongol', '', '', NULL, '', '', 1, 'NO', '', NULL, NULL, NULL, NULL, NULL, '2018-03-28 10:35:10'),
	('BUL30002018-0328104546', 'Mongol', '', '', NULL, '', '', 1, 'NO', '', NULL, NULL, NULL, NULL, NULL, '2018-03-28 10:46:34'),
	('BUL30002018-0411142123', '21222222222222222', '', '', NULL, '', '', 1, 'NO', '', 'IRIS3000/SYS', '2018-04-11 14:21:23', '', NULL, '', NULL);
/*!40000 ALTER TABLE `equipment_supplier` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.raid_table
CREATE TABLE IF NOT EXISTS `raid_table` (
  `id` varchar(50) NOT NULL,
  `file_name` varchar(500) DEFAULT NULL,
  `file_display_name` varchar(500) DEFAULT NULL,
  `file_path` varchar(50) DEFAULT NULL,
  `file_ext` varchar(50) DEFAULT NULL,
  `file_size` bigint(20) DEFAULT NULL,
  `file_hash` varchar(128) DEFAULT NULL,
  `reference_state` tinyint(4) DEFAULT NULL,
  `reference_description` varchar(100) DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `deleted_by` varchar(50) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.raid_table: ~4 rows (approximately)
/*!40000 ALTER TABLE `raid_table` DISABLE KEYS */;
INSERT INTO `raid_table` (`id`, `file_name`, `file_display_name`, `file_path`, `file_ext`, `file_size`, `file_hash`, `reference_state`, `reference_description`, `created_by`, `created_at`, `deleted_by`, `deleted_at`) VALUES
	('RAID30002018_0412151547', 'RAID30002018_0412151547_9395AF20CF29D9D1DE9457C04C764E1A5D6A2491', 'KAUNLARAN_PLANT_LAYOUT', 'bin', 'vsdx', 247478, '9395AF20CF29D9D1DE9457C04C764E1A5D6A2491', 1, 'SHARED DOCUMENTS', 'IRIS3000/SYS', '2018-04-12 15:15:47', '', NULL),
	('RAID30002018_0412151819', 'RAID30002018_0412151819_131CFA021620F0051A2370F2F92E9C94FEA247EF', 'Git-2.16.2-64-bit', 'bin', 'exe', 39139744, '131CFA021620F0051A2370F2F92E9C94FEA247EF', 0, 'BROKEN', '', NULL, '', NULL),
	('RAID30002018_0412151857', 'RAID30002018_0412151857_B79F6402CC50BEA2EA8E826C82AB2687241480F7', 'SETUP CORE-FUNDING', 'bin', 'rar', 35919302, 'B79F6402CC50BEA2EA8E826C82AB2687241480F7', 1, 'SHARED DOCUMENTS', 'IRIS3000/SYS', '2018-04-12 15:18:58', '', NULL),
	('RAID30002018_0412153113', 'RAID30002018_0412153113_0DA1F2B3C5507848EA695FB822E32D7312A4B707', 'DOST_SETUP_FINAL', 'bin', 'mp4', 36172732, '0DA1F2B3C5507848EA695FB822E32D7312A4B707', 1, 'SHARED DOCUMENTS', 'IRIS3000/SYS', '2018-04-12 15:31:14', '', NULL);
/*!40000 ALTER TABLE `raid_table` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.scholar_information
CREATE TABLE IF NOT EXISTS `scholar_information` (
  `scholar_id` varchar(50) NOT NULL,
  `student_number` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `middle_name` varchar(50) DEFAULT NULL,
  `ext_name` varchar(50) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `scholar_type` tinyint(4) DEFAULT NULL,
  `merit_type` tinyint(4) DEFAULT NULL,
  `course` varchar(50) DEFAULT NULL,
  `year_level` tinyint(4) DEFAULT NULL,
  `section` varchar(50) DEFAULT NULL,
  `university` varchar(50) DEFAULT NULL,
  `mobile_no` varchar(50) DEFAULT NULL,
  `tel_no` varchar(50) DEFAULT NULL,
  `e_mail` varchar(50) DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(50) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`scholar_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.scholar_information: ~0 rows (approximately)
/*!40000 ALTER TABLE `scholar_information` DISABLE KEYS */;
/*!40000 ALTER TABLE `scholar_information` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.scholar_submission
CREATE TABLE IF NOT EXISTS `scholar_submission` (
  `submission_id` varchar(50) NOT NULL,
  `fk_scholar_id` varchar(50) DEFAULT NULL,
  `fk_transmittal_id` varchar(50) DEFAULT NULL,
  `documents_submitted` varchar(50) DEFAULT NULL,
  `remarks` varchar(50) DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(50) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`submission_id`),
  KEY `submission_scholar_id` (`fk_scholar_id`),
  KEY `submission_transmittal_id` (`fk_transmittal_id`),
  CONSTRAINT `submission_scholar_id` FOREIGN KEY (`fk_scholar_id`) REFERENCES `scholar_information` (`scholar_id`),
  CONSTRAINT `submission_transmittal_id` FOREIGN KEY (`fk_transmittal_id`) REFERENCES `scholar_transmittal` (`transmittal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.scholar_submission: ~0 rows (approximately)
/*!40000 ALTER TABLE `scholar_submission` DISABLE KEYS */;
/*!40000 ALTER TABLE `scholar_submission` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.scholar_transmittal
CREATE TABLE IF NOT EXISTS `scholar_transmittal` (
  `transmittal_id` varchar(50) NOT NULL,
  `transmittal_date` datetime DEFAULT NULL,
  `transmitted_by` varchar(50) DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(50) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`transmittal_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.scholar_transmittal: ~0 rows (approximately)
/*!40000 ALTER TABLE `scholar_transmittal` DISABLE KEYS */;
/*!40000 ALTER TABLE `scholar_transmittal` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.setup_projects
CREATE TABLE IF NOT EXISTS `setup_projects` (
  `project_code` varchar(50) NOT NULL,
  `spin_no` varchar(300) DEFAULT NULL,
  `company_name` varchar(300) DEFAULT NULL,
  `company_owner` varchar(300) DEFAULT NULL,
  `history` varchar(500) DEFAULT NULL,
  `owner_position` varchar(300) DEFAULT NULL,
  `owner_address` varchar(300) DEFAULT NULL,
  `project_name` varchar(300) DEFAULT NULL,
  `project_status` tinyint(4) DEFAULT NULL,
  `project_type` varchar(50) DEFAULT NULL,
  `endorsed_date` datetime DEFAULT NULL,
  `approved_date` datetime DEFAULT NULL,
  `approved_funding` double DEFAULT NULL,
  `moa_date` datetime DEFAULT NULL,
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
  `created_by` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(50) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`project_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.setup_projects: ~6 rows (approximately)
/*!40000 ALTER TABLE `setup_projects` DISABLE KEYS */;
INSERT INTO `setup_projects` (`project_code`, `spin_no`, `company_name`, `company_owner`, `history`, `owner_position`, `owner_address`, `project_name`, `project_status`, `project_type`, `endorsed_date`, `approved_date`, `approved_funding`, `moa_date`, `actual_cost`, `duration_from`, `duration_to`, `factory_street`, `factory_brgy`, `factory_city`, `factory_landmark`, `year_established`, `business_activity`, `capital_classification`, `employment_classification`, `company_ownership`, `profitability`, `registration_info`, `major_products`, `existing_market`, `website`, `created_by`, `created_at`, `updated_by`, `updated_at`, `deleted_by`, `deleted_at`) VALUES
	('BUL30002018-0322115144', '', 'Sample', '', NULL, '', '', '', 0, 'GIA', NULL, NULL, 0, NULL, 0, NULL, NULL, '', '', '3012', '', '', 1, 'MICRO', 'MICRO', 'SINGLE PROPRIETORSHIP', 'PROFIT', '', '', '', '', '', NULL, '', NULL, 'JHON MELVIN NIETO PERELLO/IRIS-SYS-BUL3000', '2018-03-31 23:22:36'),
	('BUL30002018-0401111132', '', 'hello', '', 'sample', '', '', '', 1, 'SETUP', NULL, NULL, 0, NULL, 1000000, NULL, NULL, '', '', '3012', '', '', 1, 'MICRO', 'MICRO', 'SINGLE PROPRIETORSHIP', 'PROFIT', '', '', '', '', 'JHON MELVIN NIETO PERELLO/IRIS-SYS-BUL3000', '2018-04-01 11:11:37', 'IRIS3000/SYS', '2018-04-10 15:23:46', '', NULL),
	('BUL30002018-0408232655', '', 'Hi', '', NULL, '', '', '', 0, 'GIA', NULL, NULL, 0, NULL, 0, NULL, NULL, '', '', '3012', '', '', 1, 'MICRO', 'MICRO', 'SINGLE PROPRIETORSHIP', 'PROFIT', '', '', '', '', 'JHON MELVIN NIETO PERELLO/IRIS-SYS-BUL3000', '2018-04-08 23:27:05', '', NULL, '', NULL),
	('BUL30002018-0408232721', '', 'Konnichiwa', '', NULL, '', '', '', 0, 'GIA', NULL, NULL, 0, NULL, 0, NULL, NULL, '', '', '3012', '', '', 1, 'MICRO', 'MICRO', 'SINGLE PROPRIETORSHIP', 'PROFIT', '', '', '', '', 'JHON MELVIN NIETO PERELLO/IRIS-SYS-BUL3000', '2018-04-08 23:27:32', '', NULL, '', NULL),
	('BUL30002018-0408232948', '', 'Kamusta', '', 'ewan ko ba', '', '', '', 0, 'GIA', NULL, '2018-04-01 00:00:00', 0, NULL, 1200000, NULL, NULL, '', '', '3012', '', '', 1, 'MICRO', 'MICRO', 'SINGLE PROPRIETORSHIP', 'PROFIT', '', '', '', '', 'JHON MELVIN NIETO PERELLO/IRIS-SYS-BUL3000', '2018-04-08 23:29:56', 'IRIS3000/SYS', '2018-04-10 15:26:34', '', NULL),
	('BUL30002018-0411142030', '', 'sample', '', '', '', '', '', 0, 'GIA', NULL, NULL, 0, NULL, 0, NULL, NULL, '', '', '3012', '', '', 1, 'MICRO', 'MICRO', 'SINGLE PROPRIETORSHIP', 'PROFIT', '', '', '', '', 'IRIS3000/SYS', '2018-04-11 14:20:34', '', NULL, '', NULL);
/*!40000 ALTER TABLE `setup_projects` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.setup_projects_contact
CREATE TABLE IF NOT EXISTS `setup_projects_contact` (
  `contact_code` varchar(50) NOT NULL,
  `fk_setup_project_code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `position` varchar(50) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `landline` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(50) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`contact_code`),
  KEY `spc_fk_sp_code` (`fk_setup_project_code`),
  CONSTRAINT `spc_fk_sp_code` FOREIGN KEY (`fk_setup_project_code`) REFERENCES `setup_projects` (`project_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.setup_projects_contact: ~5 rows (approximately)
/*!40000 ALTER TABLE `setup_projects_contact` DISABLE KEYS */;
INSERT INTO `setup_projects_contact` (`contact_code`, `fk_setup_project_code`, `name`, `position`, `mobile`, `landline`, `email`, `created_by`, `created_at`, `updated_by`, `updated_at`, `deleted_by`, `deleted_at`) VALUES
	('BUL30002018-0322115157', 'BUL30002018-0322115144', 'Maria Sinukuan', 'Diwata', '09368955866', '', '', '', NULL, '', NULL, 'JHON MELVIN NIETO PERELLO/IRIS-SYS-BUL3000', '2018-03-31 23:22:37'),
	('BUL30002018-0322115210', 'BUL30002018-0322115144', 'Sampple2', '', '', '', '', NULL, NULL, NULL, NULL, 'JHON MELVIN NIETO PERELLO/IRIS-SYS-BUL3000', '2018-03-31 23:22:37'),
	('BUL30002018-0322115329', 'BUL30002018-0322115144', 'Sample3', '', '', '', '', NULL, NULL, NULL, NULL, 'JHON MELVIN NIETO PERELLO/IRIS-SYS-BUL3000', '2018-03-31 23:22:37'),
	('BUL30002018-0411140835', 'BUL30002018-0401111132', 'sample', '', '', '', '', 'IRIS3000/SYS', '2018-04-11 14:08:35', '', NULL, '', NULL),
	('BUL30002018-0411141005', 'BUL30002018-0408232655', 'asd', '', '', '', '', 'IRIS3000/SYS', '2018-04-11 14:10:05', 'IRIS3000/SYS', '2018-04-11 14:10:08', '', NULL);
/*!40000 ALTER TABLE `setup_projects_contact` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.system_file
CREATE TABLE IF NOT EXISTS `system_file` (
  `doc_id` varchar(50) NOT NULL,
  `fk_raid_id` varchar(50) DEFAULT NULL,
  `doc_name` varchar(500) DEFAULT NULL,
  `file_cluster` tinyint(4) DEFAULT NULL,
  `file_reference` varchar(50) DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(50) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`doc_id`),
  KEY `shared_docs_raid_id` (`fk_raid_id`),
  CONSTRAINT `shared_docs_raid_id` FOREIGN KEY (`fk_raid_id`) REFERENCES `raid_table` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.system_file: ~3 rows (approximately)
/*!40000 ALTER TABLE `system_file` DISABLE KEYS */;
INSERT INTO `system_file` (`doc_id`, `fk_raid_id`, `doc_name`, `file_cluster`, `file_reference`, `created_by`, `created_at`, `updated_by`, `updated_at`, `deleted_by`, `deleted_at`) VALUES
	('BUL30002018-0412151547', 'RAID30002018_0412151547', 'KAUNLARAN_PLANT_LAYOUT', 1, '', 'IRIS3000/SYS', '2018-04-12 15:15:47', '', NULL, '', NULL),
	('BUL30002018-0412151858', 'RAID30002018_0412151857', 'SETUP CORE-FUNDING', 1, '', 'IRIS3000/SYS', '2018-04-12 15:18:58', '', NULL, '', NULL),
	('BUL30002018-0412153114', 'RAID30002018_0412153113', 'DOST_SETUP_FINAL', 1, '', 'IRIS3000/SYS', '2018-04-12 15:31:14', '', NULL, '', NULL);
/*!40000 ALTER TABLE `system_file` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.training
CREATE TABLE IF NOT EXISTS `training` (
  `training_code` varchar(50) NOT NULL,
  `title_of_training` varchar(50) DEFAULT NULL,
  `resource_speakers` varchar(50) DEFAULT NULL,
  `venue` varchar(50) DEFAULT NULL,
  `date_start` datetime DEFAULT NULL,
  `date_end` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(50) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`training_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.training: ~0 rows (approximately)
/*!40000 ALTER TABLE `training` DISABLE KEYS */;
INSERT INTO `training` (`training_code`, `title_of_training`, `resource_speakers`, `venue`, `date_start`, `date_end`, `created_by`, `created_at`, `updated_by`, `updated_at`, `deleted_by`, `deleted_at`) VALUES
	('BUL30002018-0411084954', 'GMP', 'SDAS', '', '2018-04-03 00:00:00', NULL, 'Jane/Terminal', '2018-04-11 08:50:02', '', NULL, '', NULL);
/*!40000 ALTER TABLE `training` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.training_data
CREATE TABLE IF NOT EXISTS `training_data` (
  `data_code` varchar(50) NOT NULL,
  `fk_training_code` varchar(50) DEFAULT NULL,
  `entry_no` varchar(50) DEFAULT NULL,
  `rating` varchar(200) DEFAULT NULL,
  `comment` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(50) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`data_code`),
  KEY `training_data_training_code` (`fk_training_code`),
  CONSTRAINT `training_data_training_code` FOREIGN KEY (`fk_training_code`) REFERENCES `training` (`training_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.training_data: ~0 rows (approximately)
/*!40000 ALTER TABLE `training_data` DISABLE KEYS */;
INSERT INTO `training_data` (`data_code`, `fk_training_code`, `entry_no`, `rating`, `comment`, `name`, `created_by`, `created_at`, `updated_by`, `updated_at`, `deleted_by`, `deleted_at`) VALUES
	('BUL30002018-0411085015', 'BUL30002018-0411084954', '1', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"4","5":"4","7":"5","8":"4","9":"5","10":"4"}', 'asddddd', '', 'Jane/Terminal', '2018-04-11 08:50:37', 'IRIS3000/SYS', '2018-04-12 13:58:17', '', NULL);
/*!40000 ALTER TABLE `training_data` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.user_account
CREATE TABLE IF NOT EXISTS `user_account` (
  `user_id` varchar(50) NOT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `middle_name` varchar(50) DEFAULT NULL,
  `ext_name` varchar(50) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `access_level` varchar(50) DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(50) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.user_account: ~0 rows (approximately)
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
