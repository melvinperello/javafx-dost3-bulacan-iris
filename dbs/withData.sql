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
  `organization` varchar(500) DEFAULT NULL,
  `org_type` varchar(50) DEFAULT NULL,
  `office_name` varchar(500) DEFAULT NULL,
  `contact_person` varchar(500) DEFAULT NULL,
  `tel_no` varchar(500) DEFAULT NULL,
  `fax_no` varchar(500) DEFAULT NULL,
  `mobile_no` varchar(500) DEFAULT NULL,
  `email` varchar(500) DEFAULT NULL,
  `created_by` varchar(500) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(500) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(500) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.contact_information: ~0 rows (approximately)
/*!40000 ALTER TABLE `contact_information` DISABLE KEYS */;
INSERT INTO `contact_information` (`contact_id`, `organization`, `org_type`, `office_name`, `contact_person`, `tel_no`, `fax_no`, `mobile_no`, `email`, `created_by`, `created_at`, `updated_by`, `updated_at`, `deleted_by`, `deleted_at`) VALUES
	('BUL30002018-0424081653', 'CHED REGION 3', 'OTHERS', '', '', '(045) 436 1846', '', '', '', 'Helen/PC', '2018-04-24 08:17:08', '', NULL, '', NULL);
/*!40000 ALTER TABLE `contact_information` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.equipment_qoutation
CREATE TABLE IF NOT EXISTS `equipment_qoutation` (
  `qoute_code` varchar(50) NOT NULL,
  `fk_supplier_code` varchar(50) DEFAULT NULL,
  `equipment_name` varchar(500) DEFAULT NULL,
  `qoutation_date` datetime DEFAULT NULL,
  `specification` varchar(1500) DEFAULT NULL,
  `remarks` varchar(500) DEFAULT NULL,
  `status` varchar(500) DEFAULT NULL,
  `search_keys` varchar(500) DEFAULT NULL,
  `created_by` varchar(500) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(500) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(500) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`qoute_code`),
  KEY `equipment_qoutation_supplier_code` (`fk_supplier_code`),
  CONSTRAINT `equipment_qoutation_supplier_code` FOREIGN KEY (`fk_supplier_code`) REFERENCES `equipment_supplier` (`supplier_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.equipment_qoutation: ~0 rows (approximately)
/*!40000 ALTER TABLE `equipment_qoutation` DISABLE KEYS */;
/*!40000 ALTER TABLE `equipment_qoutation` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.equipment_supplier
CREATE TABLE IF NOT EXISTS `equipment_supplier` (
  `supplier_code` varchar(50) NOT NULL,
  `supplier_name` varchar(500) DEFAULT NULL,
  `mobile_no` varchar(500) DEFAULT NULL,
  `telephone_no` varchar(500) DEFAULT NULL,
  `fax_no` varchar(500) DEFAULT NULL,
  `email` varchar(500) DEFAULT NULL,
  `website_address` varchar(500) DEFAULT NULL,
  `sector` int(11) DEFAULT NULL,
  `dost_accredited` varchar(50) DEFAULT NULL,
  `supplier_address` varchar(500) DEFAULT NULL,
  `created_by` varchar(500) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(500) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(500) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`supplier_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.equipment_supplier: ~0 rows (approximately)
/*!40000 ALTER TABLE `equipment_supplier` DISABLE KEYS */;
/*!40000 ALTER TABLE `equipment_supplier` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.raid_table
CREATE TABLE IF NOT EXISTS `raid_table` (
  `id` varchar(50) NOT NULL,
  `file_name` varchar(500) DEFAULT NULL,
  `file_display_name` varchar(500) DEFAULT NULL,
  `file_path` varchar(500) DEFAULT NULL,
  `file_ext` varchar(20) DEFAULT NULL,
  `file_size` bigint(20) DEFAULT NULL,
  `file_hash` varchar(128) DEFAULT NULL,
  `reference_state` tinyint(4) DEFAULT NULL,
  `reference_description` varchar(100) DEFAULT NULL,
  `created_by` varchar(500) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `deleted_by` varchar(500) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.raid_table: ~5 rows (approximately)
/*!40000 ALTER TABLE `raid_table` DISABLE KEYS */;
INSERT INTO `raid_table` (`id`, `file_name`, `file_display_name`, `file_path`, `file_ext`, `file_size`, `file_hash`, `reference_state`, `reference_description`, `created_by`, `created_at`, `deleted_by`, `deleted_at`) VALUES
	('RAID30002018_0423140850', 'RAID30002018_0423140850_1694CB4E5B682F60DDED31CF05B646D2DE7434B2', 'letter_format_a4', 'bin', 'docx', 203308, '1694CB4E5B682F60DDED31CF05B646D2DE7434B2', 1, 'SHARED DOCUMENTS', 'Helen/PC', '2018-04-23 14:08:50', '', NULL),
	('RAID30002018_0423140917', 'RAID30002018_0423140917_278E54C83368EE6692F459C738E5CE9389911D82', 'letter_format_long', 'bin', 'docx', 203556, '278E54C83368EE6692F459C738E5CE9389911D82', 1, 'SHARED DOCUMENTS', 'Helen/PC', '2018-04-23 14:09:17', '', NULL),
	('RAID30002018_0423143302', 'RAID30002018_0423143302_577E132616DD69A19AAB8709786B9F24BCC2661F', 'letter to ITDI april 23 2018', 'bin', 'docx', 204402, '577E132616DD69A19AAB8709786B9F24BCC2661F', 1, 'SHARED DOCUMENTS', 'Angie/PC', '2018-04-23 14:33:02', '', NULL),
	('RAID30002018_0423143705', 'RAID30002018_0423143705_FA2AF9784DB6F33C67B02D11F577C589A1EB4572', 'ITDI_LETTER', 'bin', 'pdf', 575007, 'FA2AF9784DB6F33C67B02D11F577C589A1EB4572', 1, 'SHARED DOCUMENTS', 'Ador/PC', '2018-04-23 14:37:05', '', NULL),
	('RAID30002018_0423151817', 'RAID30002018_0423151817_0754AB760FBD011D35DDAA948BFF1A1AF206EC03', 'photos of feeding program', 'bin', 'rar', 5341765, '0754AB760FBD011D35DDAA948BFF1A1AF206EC03', 1, 'SHARED DOCUMENTS', 'Jane/PC', '2018-04-23 15:18:18', '', NULL),
	('RAID30002018_0424082428', 'RAID30002018_0424082428_B406FA489C492C3BCB5AACE309216536E580F308', 'food_pic', 'bin', '7z', 10581490, 'B406FA489C492C3BCB5AACE309216536E580F308', 1, 'SHARED DOCUMENTS', 'Helen/PC', '2018-04-24 08:24:28', '', NULL);
/*!40000 ALTER TABLE `raid_table` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.scholar_information
CREATE TABLE IF NOT EXISTS `scholar_information` (
  `scholar_id` varchar(50) NOT NULL,
  `student_number` varchar(100) DEFAULT NULL,
  `last_name` varchar(500) DEFAULT NULL,
  `first_name` varchar(500) DEFAULT NULL,
  `middle_name` varchar(500) DEFAULT NULL,
  `ext_name` varchar(500) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `scholar_type` tinyint(4) DEFAULT NULL,
  `merit_type` tinyint(4) DEFAULT NULL,
  `course` varchar(500) DEFAULT NULL,
  `year_level` tinyint(4) DEFAULT NULL,
  `section` varchar(500) DEFAULT NULL,
  `university` varchar(500) DEFAULT NULL,
  `mobile_no` varchar(500) DEFAULT NULL,
  `tel_no` varchar(500) DEFAULT NULL,
  `e_mail` varchar(500) DEFAULT NULL,
  `created_by` varchar(500) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(500) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(500) DEFAULT NULL,
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
  `documents_submitted` varchar(500) DEFAULT NULL,
  `remarks` varchar(500) DEFAULT NULL,
  `created_by` varchar(500) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(500) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(500) DEFAULT NULL,
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
  `transmitted_by` varchar(500) DEFAULT NULL,
  `created_by` varchar(500) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(500) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(500) DEFAULT NULL,
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
  `company_name` varchar(500) DEFAULT NULL,
  `company_owner` varchar(500) DEFAULT NULL,
  `history` varchar(1000) DEFAULT NULL,
  `owner_position` varchar(500) DEFAULT NULL,
  `owner_address` varchar(500) DEFAULT NULL,
  `project_name` varchar(500) DEFAULT NULL,
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
  `factory_city` varchar(100) DEFAULT NULL COMMENT 'ZIP Code',
  `factory_landmark` varchar(300) DEFAULT NULL,
  `year_established` varchar(100) DEFAULT NULL,
  `business_activity` int(11) DEFAULT NULL,
  `capital_classification` varchar(100) DEFAULT NULL,
  `employment_classification` varchar(100) DEFAULT NULL,
  `company_ownership` varchar(100) DEFAULT NULL,
  `profitability` varchar(100) DEFAULT NULL,
  `registration_info` varchar(500) DEFAULT NULL,
  `major_products` varchar(500) DEFAULT NULL,
  `existing_market` varchar(500) DEFAULT NULL,
  `website` varchar(500) DEFAULT NULL,
  `created_by` varchar(500) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(500) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(500) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`project_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.setup_projects: ~0 rows (approximately)
/*!40000 ALTER TABLE `setup_projects` DISABLE KEYS */;
/*!40000 ALTER TABLE `setup_projects` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.setup_projects_contact
CREATE TABLE IF NOT EXISTS `setup_projects_contact` (
  `contact_code` varchar(50) NOT NULL,
  `fk_setup_project_code` varchar(50) DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL,
  `position` varchar(500) DEFAULT NULL,
  `mobile` varchar(500) DEFAULT NULL,
  `landline` varchar(500) DEFAULT NULL,
  `email` varchar(500) DEFAULT NULL,
  `created_by` varchar(500) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(500) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(500) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`contact_code`),
  KEY `spc_fk_sp_code` (`fk_setup_project_code`),
  CONSTRAINT `spc_fk_sp_code` FOREIGN KEY (`fk_setup_project_code`) REFERENCES `setup_projects` (`project_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.setup_projects_contact: ~0 rows (approximately)
/*!40000 ALTER TABLE `setup_projects_contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `setup_projects_contact` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.system_file
CREATE TABLE IF NOT EXISTS `system_file` (
  `doc_id` varchar(50) NOT NULL,
  `fk_raid_id` varchar(50) DEFAULT NULL,
  `doc_name` varchar(500) DEFAULT NULL,
  `file_cluster` tinyint(4) DEFAULT NULL,
  `file_reference` varchar(500) DEFAULT NULL,
  `created_by` varchar(500) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(500) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(500) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`doc_id`),
  KEY `shared_docs_raid_id` (`fk_raid_id`),
  CONSTRAINT `shared_docs_raid_id` FOREIGN KEY (`fk_raid_id`) REFERENCES `raid_table` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.system_file: ~4 rows (approximately)
/*!40000 ALTER TABLE `system_file` DISABLE KEYS */;
INSERT INTO `system_file` (`doc_id`, `fk_raid_id`, `doc_name`, `file_cluster`, `file_reference`, `created_by`, `created_at`, `updated_by`, `updated_at`, `deleted_by`, `deleted_at`) VALUES
	('BUL30002018-0423140850', 'RAID30002018_0423140850', 'letter_format_a4', 1, '', 'Helen/PC', '2018-04-23 14:08:50', '', NULL, '', NULL),
	('BUL30002018-0423140917', 'RAID30002018_0423140917', 'letter_format_long', 1, '', 'Helen/PC', '2018-04-23 14:09:17', '', NULL, '', NULL),
	('BUL30002018-0423143302', 'RAID30002018_0423143302', 'letter to ITDI april 23 2018', 1, '', 'Angie/PC', '2018-04-23 14:33:02', '', NULL, '', NULL),
	('BUL30002018-0423143705', 'RAID30002018_0423143705', 'ITDI_LETTER', 1, '', 'Ador/PC', '2018-04-23 14:37:05', '', NULL, '', NULL),
	('BUL30002018-0423151818', 'RAID30002018_0423151817', 'photos of feeding program', 1, '', 'Jane/PC', '2018-04-23 15:18:18', '', NULL, '', NULL),
	('BUL30002018-0424082428', 'RAID30002018_0424082428', 'FEEDING_ALBUM', 1, '', 'Helen/PC', '2018-04-24 08:24:28', 'Helen/PC', '2018-04-24 08:28:07', '', NULL);
/*!40000 ALTER TABLE `system_file` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.training
CREATE TABLE IF NOT EXISTS `training` (
  `training_code` varchar(50) NOT NULL,
  `title_of_training` varchar(500) DEFAULT NULL,
  `resource_speakers` varchar(500) DEFAULT NULL,
  `venue` varchar(500) DEFAULT NULL,
  `date_start` datetime DEFAULT NULL,
  `date_end` datetime DEFAULT NULL,
  `created_by` varchar(500) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(500) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(500) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`training_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.training: ~6 rows (approximately)
/*!40000 ALTER TABLE `training` DISABLE KEYS */;
INSERT INTO `training` (`training_code`, `title_of_training`, `resource_speakers`, `venue`, `date_start`, `date_end`, `created_by`, `created_at`, `updated_by`, `updated_at`, `deleted_by`, `deleted_at`) VALUES
	('BUL30002018-0427083703', 'Awareness Seminar on Good Manufacturing Practices', 'Angelita Q. Parungao', 'Umpucan, San Ildefonso, Bulacan', '2018-03-09 00:00:00', NULL, 'Jane/PC', '2018-04-27 08:38:47', '', NULL, '', NULL),
	('BUL30002018-0427092043', 'Awareness Seminar on Good Manufacturing Practices and Food Safety', 'Angelita Q. Parungao', 'Galilee Resort, San Pedro, Bustos, Bulacan', '2018-03-23 00:00:00', NULL, 'Jane/PC', '2018-04-27 09:27:59', '', NULL, '', NULL),
	('BUL30002018-0427101033', 'Seminar on Packaging and Labeling', 'Angelita Q. Parungao', 'Enchanted Farm, Encanto, Angat, Bulacan', '2018-03-01 00:00:00', NULL, 'Jane/PC', '2018-04-27 10:12:34', '', NULL, '', NULL),
	('BUL30002018-0427110104', 'Seminar on Packaging and Labeling', 'Angelita Q. Parungao', 'Galilee Resort, San Pedro, Bustos, Bulacan', '2018-03-23 00:00:00', NULL, 'Jane/PC', '2018-04-27 11:01:51', 'Jane/PC', '2018-04-27 11:05:24', '', NULL),
	('BUL30002018-0427113049', 'Orientation on Plant Growth Promoter Carageenan Technology', 'Ador V. Valdez', 'Agricultural Office, San Jose,Bulakan, Bulacan', '2018-02-01 00:00:00', NULL, 'Jane/PC', '2018-04-27 11:32:29', 'Jane/PC', '2018-04-27 11:32:43', '', NULL),
	('BUL30002018-0427120411', 'Starbooks Orientation and Hands-On Training', 'Homer Baustista', 'San Jose Brgy Hall, San Jose, Bulakan, Bulacan', '2018-01-15 00:00:00', NULL, 'Jane/PC', '2018-04-27 12:05:52', '', NULL, '', NULL);
/*!40000 ALTER TABLE `training` ENABLE KEYS */;

-- Dumping structure for table iris_bulacan_dost3.training_data
CREATE TABLE IF NOT EXISTS `training_data` (
  `data_code` varchar(50) NOT NULL,
  `fk_training_code` varchar(50) DEFAULT NULL,
  `entry_no` varchar(50) DEFAULT NULL,
  `rating` varchar(300) DEFAULT NULL,
  `comment` varchar(500) DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL,
  `created_by` varchar(500) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(500) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(500) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`data_code`),
  KEY `training_data_training_code` (`fk_training_code`),
  CONSTRAINT `training_data_training_code` FOREIGN KEY (`fk_training_code`) REFERENCES `training` (`training_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.training_data: ~126 rows (approximately)
/*!40000 ALTER TABLE `training_data` DISABLE KEYS */;
INSERT INTO `training_data` (`data_code`, `fk_training_code`, `entry_no`, `rating`, `comment`, `name`, `created_by`, `created_at`, `updated_by`, `updated_at`, `deleted_by`, `deleted_at`) VALUES
	('BUL30002018-0427083901', 'BUL30002018-0427083703', '1', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 08:40:01', '', NULL, '', NULL),
	('BUL30002018-0427084008', 'BUL30002018-0427083703', '2', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 08:40:46', '', NULL, '', NULL),
	('BUL30002018-0427084048', 'BUL30002018-0427083703', '3', '{"12":"3","13":"3","14":"3","15":"4","1":"4","2":"4","3":"5","4":"3","5":"3","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 08:41:14', '', NULL, '', NULL),
	('BUL30002018-0427084117', 'BUL30002018-0427083703', '4', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 08:41:36', '', NULL, '', NULL),
	('BUL30002018-0427084140', 'BUL30002018-0427083703', '5', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 08:42:06', 'Jane/PC', '2018-04-27 08:59:32', '', NULL),
	('BUL30002018-0427084209', 'BUL30002018-0427083703', '6', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 08:42:31', '', NULL, '', NULL),
	('BUL30002018-0427084233', 'BUL30002018-0427083703', '7', '{"12":"3","13":"3","14":"3","15":"4","1":"5","2":"5","3":"4","4":"5","5":"3","7":"3","8":"3","9":"3","10":"2"}', '', '', 'Jane/PC', '2018-04-27 08:42:52', '', NULL, '', NULL),
	('BUL30002018-0427084256', 'BUL30002018-0427083703', '8', '{"12":"4","13":"4","14":"5","15":"5","1":"4","2":"4","3":"4","4":"4","5":"3","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 08:43:30', '', NULL, '', NULL),
	('BUL30002018-0427084342', 'BUL30002018-0427083703', '9', '{"12":"4","13":"4","14":"5","15":"5","1":"4","2":"4","3":"5","4":"4","5":"5","7":"4","8":"4","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 08:44:19', '', NULL, '', NULL),
	('BUL30002018-0427084421', 'BUL30002018-0427083703', '10', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 08:44:41', '', NULL, '', NULL),
	('BUL30002018-0427084444', 'BUL30002018-0427083703', '11', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 08:45:01', '', NULL, '', NULL),
	('BUL30002018-0427084503', 'BUL30002018-0427083703', '12', '{"12":"4","13":"4","14":"5","15":"5","1":"4","2":"5","3":"5","4":"4","5":"4","7":"4","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 08:45:48', '', NULL, '', NULL),
	('BUL30002018-0427084626', 'BUL30002018-0427083703', '13', '{"12":"4","13":"4","14":"4","15":"5","1":"5","2":"4","3":"5","4":"4","5":"4","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 08:47:00', '', NULL, '', NULL),
	('BUL30002018-0427084702', 'BUL30002018-0427083703', '14', '{"12":"5","13":"5","14":"5","15":"5","1":"4","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 08:47:35', '', NULL, '', NULL),
	('BUL30002018-0427092847', 'BUL30002018-0427092043', '1', '{"12":"5","13":"4","14":"4","15":"5","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"5"}', '', '', 'Jane/PC', '2018-04-27 09:31:06', '', NULL, '', NULL),
	('BUL30002018-0427093110', 'BUL30002018-0427092043', '2', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 09:31:53', '', NULL, '', NULL),
	('BUL30002018-0427093156', 'BUL30002018-0427092043', '3', '{"12":"4","13":"4","14":"4","15":"4","1":"5","2":"5","3":"4","4":"4","5":"4","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 09:32:38', '', NULL, '', NULL),
	('BUL30002018-0427093241', 'BUL30002018-0427092043', '4', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 09:32:57', '', NULL, '', NULL),
	('BUL30002018-0427093300', 'BUL30002018-0427092043', '5', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 09:33:33', '', NULL, '', NULL),
	('BUL30002018-0427093337', 'BUL30002018-0427092043', '6', '{"12":"4","13":"4","14":"4","15":"5","1":"5","2":"4","3":"5","4":"4","5":"4","7":"5","8":"5","9":"5","10":"4"}', '', '', 'Jane/PC', '2018-04-27 09:33:54', '', NULL, '', NULL),
	('BUL30002018-0427093356', 'BUL30002018-0427092043', '7', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 09:34:23', '', NULL, '', NULL),
	('BUL30002018-0427093434', 'BUL30002018-0427092043', '8', '{"12":"3","13":"3","14":"3","15":"3","1":"4","2":"4","3":"3","4":"3","5":"3","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 09:35:05', '', NULL, '', NULL),
	('BUL30002018-0427093508', 'BUL30002018-0427092043', '9', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 09:35:41', '', NULL, '', NULL),
	('BUL30002018-0427093544', 'BUL30002018-0427092043', '10', '{"12":"4","13":"4","14":"4","15":"5","1":"5","2":"5","3":"5","4":"4","5":"4","7":"4","8":"4","9":"5","10":"4"}', '', '', 'Jane/PC', '2018-04-27 09:36:31', '', NULL, '', NULL),
	('BUL30002018-0427093635', 'BUL30002018-0427092043', '11', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 09:36:59', '', NULL, '', NULL),
	('BUL30002018-0427093703', 'BUL30002018-0427092043', '12', '{"12":"4","13":"4","14":"4","15":"4","1":"5","2":"5","3":"5","4":"4","5":"4","7":"5","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 09:37:31', '', NULL, '', NULL),
	('BUL30002018-0427093736', 'BUL30002018-0427092043', '13', '{"12":"3","13":"3","14":"3","15":"3","1":"4","2":"4","3":"3","4":"3","5":"3","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 09:38:11', '', NULL, '', NULL),
	('BUL30002018-0427093817', 'BUL30002018-0427092043', '14', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 09:38:39', '', NULL, '', NULL),
	('BUL30002018-0427093847', 'BUL30002018-0427092043', '15', '{"12":"3","13":"3","14":"3","15":"3","1":"3","2":"3","3":"3","4":"3","5":"3","7":"3","8":"3","9":"3","10":"3"}', '', '', 'Jane/PC', '2018-04-27 09:39:15', '', NULL, '', NULL),
	('BUL30002018-0427093944', 'BUL30002018-0427092043', '16', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 09:40:07', '', NULL, '', NULL),
	('BUL30002018-0427094011', 'BUL30002018-0427092043', '17', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 09:40:51', '', NULL, '', NULL),
	('BUL30002018-0427094054', 'BUL30002018-0427092043', '18', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 09:42:20', '', NULL, '', NULL),
	('BUL30002018-0427094344', 'BUL30002018-0427092043', '19', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 09:44:01', '', NULL, '', NULL),
	('BUL30002018-0427094415', 'BUL30002018-0427092043', '20', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"5","8":"4","9":"5","10":"4"}', '', '', 'Jane/PC', '2018-04-27 09:45:04', '', NULL, '', NULL),
	('BUL30002018-0427094507', 'BUL30002018-0427092043', '21', '{"12":"3","13":"4","14":"3","15":"3","1":"4","2":"4","3":"4","4":"3","5":"3","7":"4","8":"4","9":"5","10":"4"}', '', '', 'Jane/PC', '2018-04-27 09:45:37', '', NULL, '', NULL),
	('BUL30002018-0427094542', 'BUL30002018-0427092043', '22', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 09:46:03', '', NULL, '', NULL),
	('BUL30002018-0427094606', 'BUL30002018-0427092043', '23', '{"12":"5","13":"4","14":"4","15":"5","1":"4","2":"4","3":"5","4":"5","5":"4","7":"5","8":"5","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 09:48:24', '', NULL, '', NULL),
	('BUL30002018-0427094833', 'BUL30002018-0427092043', '24', '{"12":"5","13":"4","14":"4","15":"5","1":"4","2":"4","3":"5","4":"5","5":"4","7":"5","8":"5","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 09:49:54', '', NULL, '', NULL),
	('BUL30002018-0427095155', 'BUL30002018-0427092043', '25', '{"12":"4","13":"4","14":"4","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 09:52:15', '', NULL, '', NULL),
	('BUL30002018-0427101249', 'BUL30002018-0427101033', '1', '{"12":"4","13":"5","14":"5","15":"4","1":"5","2":"5","3":"5","4":"4","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 10:18:42', '', NULL, '', NULL),
	('BUL30002018-0427101854', 'BUL30002018-0427101033', '2', '{"12":"4","13":"4","14":"5","15":"4","1":"5","2":"5","3":"5","4":"5","5":"4","7":"5","8":"4","9":"4","10":"5"}', '', '', 'Jane/PC', '2018-04-27 10:22:44', '', NULL, '', NULL),
	('BUL30002018-0427102247', 'BUL30002018-0427101033', '3', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 10:23:05', '', NULL, '', NULL),
	('BUL30002018-0427102311', 'BUL30002018-0427101033', '4', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"4","10":"5"}', '', '', 'Jane/PC', '2018-04-27 10:23:47', '', NULL, '', NULL),
	('BUL30002018-0427102350', 'BUL30002018-0427101033', '5', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"4","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 10:24:22', '', NULL, '', NULL),
	('BUL30002018-0427102426', 'BUL30002018-0427101033', '6', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 10:24:43', '', NULL, '', NULL),
	('BUL30002018-0427102447', 'BUL30002018-0427101033', '7', '{"12":"5","13":"5","14":"5","15":"5","1":"4","2":"4","3":"4","4":"4","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 10:25:09', '', NULL, '', NULL),
	('BUL30002018-0427102524', 'BUL30002018-0427101033', '8', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 10:25:41', '', NULL, '', NULL),
	('BUL30002018-0427102550', 'BUL30002018-0427101033', '9', '{"12":"4","13":"4","14":"4","15":"3","1":"5","2":"4","3":"4","4":"4","5":"3","7":"4","8":"2","9":"3","10":"4"}', '', '', 'Jane/PC', '2018-04-27 10:29:52', '', NULL, '', NULL),
	('BUL30002018-0427102957', 'BUL30002018-0427101033', '10', '{"12":"4","13":"3","14":"3","15":"4","1":"4","2":"4","3":"3","4":"3","5":"4","7":"4","8":"3","9":"3","10":"4"}', '', '', 'Jane/PC', '2018-04-27 10:30:52', '', NULL, '', NULL),
	('BUL30002018-0427103248', 'BUL30002018-0427101033', '11', '{"12":"5","13":"4","14":"4","15":"5","1":"5","2":"5","3":"5","4":"4","5":"5","7":"5","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 10:33:26', '', NULL, '', NULL),
	('BUL30002018-0427103329', 'BUL30002018-0427101033', '12', '{"12":"5","13":"5","14":"5","15":"5","1":"4","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 10:33:53', '', NULL, '', NULL),
	('BUL30002018-0427103356', 'BUL30002018-0427101033', '13', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"5","3":"5","4":"5","5":"4","7":"4","8":"4","9":"4","10":"4"}', 'Naunawaan ko po ang tamang packaging at detalye para sa product.', 'Jeven Estorba', 'Jane/PC', '2018-04-27 10:35:36', '', NULL, '', NULL),
	('BUL30002018-0427103552', 'BUL30002018-0427101033', '14', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', 'thank you po.', 'Syra Mae Cruz', 'Jane/PC', '2018-04-27 10:36:35', '', NULL, '', NULL),
	('BUL30002018-0427103642', 'BUL30002018-0427101033', '15', '{"12":"4","13":"3","14":"3","15":"4","1":"4","2":"4","3":"4","4":"4","5":"3","7":"4","8":"3","9":"4","10":"4"}', '', 'Richard M. Rebolles', 'Jane/PC', '2018-04-27 10:38:41', '', NULL, '', NULL),
	('BUL30002018-0427103845', 'BUL30002018-0427101033', '16', '{"12":"4","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"4","5":"4","7":"4","8":"5","9":"5","10":"5"}', '', 'Ma. Crisanta Sison', 'Jane/PC', '2018-04-27 10:39:25', '', NULL, '', NULL),
	('BUL30002018-0427103947', 'BUL30002018-0427101033', '17', '{"12":"5","13":"5","14":"5","15":"5","1":"4","2":"5","3":"4","4":"4","5":"5","7":"5","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 10:40:11', '', NULL, '', NULL),
	('BUL30002018-0427104657', 'BUL30002018-0427101033', '18', '{"12":"4","13":"4","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"4","8":"5","9":"5","10":"5"}', '', 'April Karen Bunao', 'Jane/PC', '2018-04-27 10:47:28', '', NULL, '', NULL),
	('BUL30002018-0427104813', 'BUL30002018-0427101033', '19', '{"12":"5","13":"4","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"4","9":"5","10":"4"}', '', '', 'Jane/PC', '2018-04-27 10:48:35', '', NULL, '', NULL),
	('BUL30002018-0427104842', 'BUL30002018-0427101033', '20', '{"12":"5","13":"4","14":"4","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"4"}', '', '', 'Jane/PC', '2018-04-27 10:49:23', '', NULL, '', NULL),
	('BUL30002018-0427104928', 'BUL30002018-0427101033', '21', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"5","3":"5","4":"5","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', 'Danilo Ablen', 'Jane/PC', '2018-04-27 10:50:13', '', NULL, '', NULL),
	('BUL30002018-0427105027', 'BUL30002018-0427101033', '22', '{"12":"3","13":"3","14":"3","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"3","9":"3","10":"4"}', '', '', 'Jane/PC', '2018-04-27 10:51:11', '', NULL, '', NULL),
	('BUL30002018-0427105114', 'BUL30002018-0427101033', '23', '{"12":"3","13":"4","14":"4","15":"4","1":"5","2":"5","3":"5","4":"4","5":"4","7":"5","8":"4","9":"4","10":"5"}', '', '', 'Jane/PC', '2018-04-27 10:51:52', '', NULL, '', NULL),
	('BUL30002018-0427105203', 'BUL30002018-0427101033', '24', '{"12":"5","13":"5","14":"4","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"4","8":"5","9":"5","10":"4"}', '', 'Madelyn Talavera', 'Jane/PC', '2018-04-27 10:52:43', '', NULL, '', NULL),
	('BUL30002018-0427105246', 'BUL30002018-0427101033', '25', '{"12":"5","13":"5","14":"4","15":"5","1":"4","2":"4","3":"4","4":"4","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 10:53:09', '', NULL, '', NULL),
	('BUL30002018-0427110826', 'BUL30002018-0427110104', '1', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 11:09:06', '', NULL, '', NULL),
	('BUL30002018-0427110909', 'BUL30002018-0427110104', '2', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 11:09:20', '', NULL, '', NULL),
	('BUL30002018-0427110923', 'BUL30002018-0427110104', '3', '{"12":"4","13":"5","14":"5","15":"5","1":"4","2":"4","3":"4","4":"4","5":"4","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 11:09:54', '', NULL, '', NULL),
	('BUL30002018-0427110956', 'BUL30002018-0427110104', '4', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 11:10:16', '', NULL, '', NULL),
	('BUL30002018-0427111019', 'BUL30002018-0427110104', '5', '{"12":"4","13":"4","14":"4","15":"5","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 11:10:59', '', NULL, '', NULL),
	('BUL30002018-0427111102', 'BUL30002018-0427110104', '6', '{"12":"5","13":"5","14":"5","15":"5","1":"4","2":"4","3":"4","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 11:11:32', '', NULL, '', NULL),
	('BUL30002018-0427111134', 'BUL30002018-0427110104', '7', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 11:11:45', '', NULL, '', NULL),
	('BUL30002018-0427111148', 'BUL30002018-0427110104', '8', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 11:12:07', '', NULL, '', NULL),
	('BUL30002018-0427111228', 'BUL30002018-0427110104', '9', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"5","3":"4","4":"4","5":"4","7":"5","8":"5","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 11:12:46', '', NULL, '', NULL),
	('BUL30002018-0427111249', 'BUL30002018-0427110104', '10', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 11:13:20', '', NULL, '', NULL),
	('BUL30002018-0427111325', 'BUL30002018-0427110104', '11', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 11:14:31', '', NULL, '', NULL),
	('BUL30002018-0427111434', 'BUL30002018-0427110104', '12', '{"12":"3","13":"4","14":"4","15":"4","1":"5","2":"5","3":"5","4":"4","5":"4","7":"4","8":"4","9":"4","10":"3"}', '', '', 'Jane/PC', '2018-04-27 11:15:35', '', NULL, '', NULL),
	('BUL30002018-0427111537', 'BUL30002018-0427110104', '13', '{"12":"4","13":"4","14":"4","15":"4","1":"5","2":"4","3":"5","4":"3","5":"3","7":"4","8":"4","9":"4","10":"3"}', '', '', 'Jane/PC', '2018-04-27 11:16:55', '', NULL, '', NULL),
	('BUL30002018-0427111659', 'BUL30002018-0427110104', '14', '{"12":"3","13":"3","14":"3","15":"3","1":"3","2":"3","3":"3","4":"3","5":"3","7":"3","8":"3","9":"3","10":"3"}', '', '', 'Jane/PC', '2018-04-27 11:17:23', '', NULL, '', NULL),
	('BUL30002018-0427111735', 'BUL30002018-0427110104', '15', '{"12":"3","13":"3","14":"3","15":"3","1":"3","2":"3","3":"3","4":"3","5":"3","7":"3","8":"3","9":"3","10":"3"}', '', '', 'Jane/PC', '2018-04-27 11:17:56', '', NULL, '', NULL),
	('BUL30002018-0427111812', 'BUL30002018-0427110104', '16', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 11:18:30', '', NULL, '', NULL),
	('BUL30002018-0427111910', 'BUL30002018-0427110104', '17', '{"12":"3","13":"3","14":"3","15":"5","1":"4","2":"4","3":"5","4":"4","5":"4","7":"4","8":"5","9":"5","10":"4"}', '', '', 'Jane/PC', '2018-04-27 11:20:38', '', NULL, '', NULL),
	('BUL30002018-0427113254', 'BUL30002018-0427113049', '1', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 11:33:19', '', NULL, '', NULL),
	('BUL30002018-0427113321', 'BUL30002018-0427113049', '2', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 11:33:38', '', NULL, '', NULL),
	('BUL30002018-0427113340', 'BUL30002018-0427113049', '3', '{"12":"4","13":"4","14":"4","15":"5","1":"4","2":"4","3":"4","4":"4","5":"4","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 11:34:00', '', NULL, '', NULL),
	('BUL30002018-0427113412', 'BUL30002018-0427113049', '4', '{"12":"4","13":"4","14":"3","15":"4","1":"4","2":"4","3":"5","4":"5","5":"4","7":"4","8":"4","9":"5","10":"4"}', '', '', 'Jane/PC', '2018-04-27 11:34:47', '', NULL, '', NULL),
	('BUL30002018-0427114903', 'BUL30002018-0427113049', '5', '{"12":"4","13":"4","14":"4","15":"3","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 11:50:05', '', NULL, '', NULL),
	('BUL30002018-0427115011', 'BUL30002018-0427113049', '6', '{"12":"4","13":"4","14":"4","15":"5","1":"4","2":"5","3":"4","4":"5","5":"4","7":"5","8":"5","9":"5","10":"4"}', '', '', 'Jane/PC', '2018-04-27 11:50:51', '', NULL, '', NULL),
	('BUL30002018-0427115054', 'BUL30002018-0427113049', '7', '{"12":"3","13":"4","14":"3","15":"4","1":"4","2":"4","3":"4","4":"4","5":"3","7":"4","8":"4","9":"4","10":"3"}', '', '', 'Jane/PC', '2018-04-27 11:51:50', '', NULL, '', NULL),
	('BUL30002018-0427115153', 'BUL30002018-0427113049', '8', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 11:52:32', '', NULL, '', NULL),
	('BUL30002018-0427115235', 'BUL30002018-0427113049', '9', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 11:53:18', '', NULL, '', NULL),
	('BUL30002018-0427115322', 'BUL30002018-0427113049', '10', '{"12":"4","13":"4","14":"4","15":"3","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 11:53:54', '', NULL, '', NULL),
	('BUL30002018-0427115356', 'BUL30002018-0427113049', '11', '{"12":"5","13":"5","14":"4","15":"5","1":"5","2":"5","3":"5","4":"4","5":"4","7":"4","8":"5","9":"5","10":"4"}', '', '', 'Jane/PC', '2018-04-27 11:54:23', '', NULL, '', NULL),
	('BUL30002018-0427115425', 'BUL30002018-0427113049', '12', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 11:54:44', '', NULL, '', NULL),
	('BUL30002018-0427115447', 'BUL30002018-0427113049', '13', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"5","3":"5","4":"5","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 11:55:08', '', NULL, '', NULL),
	('BUL30002018-0427115511', 'BUL30002018-0427113049', '14', '{"12":"4","13":"4","14":"4","15":"5","1":"4","2":"5","3":"4","4":"4","5":"5","7":"4","8":"5","9":"5","10":"4"}', '', '', 'Jane/PC', '2018-04-27 11:56:20', '', NULL, '', NULL),
	('BUL30002018-0427115622', 'BUL30002018-0427113049', '15', '{"12":"4","13":"4","14":"4","15":"4","1":"5","2":"5","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 11:56:43', '', NULL, '', NULL),
	('BUL30002018-0427115645', 'BUL30002018-0427113049', '16', '{"12":"3","13":"3","14":"3","15":"3","1":"3","2":"3","3":"3","4":"3","5":"3","7":"3","8":"3","9":"3","10":"3"}', '', '', 'Jane/PC', '2018-04-27 11:56:56', '', NULL, '', NULL),
	('BUL30002018-0427115718', 'BUL30002018-0427113049', '17', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 11:57:32', '', NULL, '', NULL),
	('BUL30002018-0427115735', 'BUL30002018-0427113049', '18', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 11:57:55', '', NULL, '', NULL),
	('BUL30002018-0427115757', 'BUL30002018-0427113049', '19', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 11:58:13', '', NULL, '', NULL),
	('BUL30002018-0427115815', 'BUL30002018-0427113049', '20', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 11:58:25', '', NULL, '', NULL),
	('BUL30002018-0427115833', 'BUL30002018-0427113049', '21', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 11:58:55', '', NULL, '', NULL),
	('BUL30002018-0427115902', 'BUL30002018-0427113049', '22', '{"12":"3","13":"3","14":"3","15":"3","1":"3","2":"3","3":"3","4":"3","5":"3","7":"3","8":"3","9":"3","10":"3"}', '', '', 'Jane/PC', '2018-04-27 11:59:13', '', NULL, '', NULL),
	('BUL30002018-0427120628', 'BUL30002018-0427120411', '1', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"4","7":"5","8":"4","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 12:06:53', '', NULL, '', NULL),
	('BUL30002018-0427120656', 'BUL30002018-0427120411', '2', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"4","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 12:09:18', '', NULL, '', NULL),
	('BUL30002018-0427120920', 'BUL30002018-0427120411', '3', '{"12":"4","13":"4","14":"4","15":"5","1":"5","2":"5","3":"5","4":"4","5":"4","7":"4","8":"4","9":"4","10":"5"}', '', '', 'Jane/PC', '2018-04-27 12:09:41', '', NULL, '', NULL),
	('BUL30002018-0427120945', 'BUL30002018-0427120411', '4', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"4","3":"5","4":"5","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 12:10:14', '', NULL, '', NULL),
	('BUL30002018-0427121017', 'BUL30002018-0427120411', '5', '{"12":"5","13":"5","14":"5","15":"5","1":"4","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 12:10:30', '', NULL, '', NULL),
	('BUL30002018-0427121034', 'BUL30002018-0427120411', '6', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"4","3":"5","4":"5","5":"5","7":"5","8":"4","9":"4","10":"5"}', '', '', 'Jane/PC', '2018-04-27 12:11:14', '', NULL, '', NULL),
	('BUL30002018-0427122352', 'BUL30002018-0427120411', '7', '{"12":"5","13":"3","14":"5","15":"5","1":"3","2":"5","3":"3","4":"5","5":"4","7":"5","8":"4","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 12:25:38', '', NULL, '', NULL),
	('BUL30002018-0427122542', 'BUL30002018-0427120411', '8', '{"12":"4","13":"5","14":"5","15":"4","1":"4","2":"5","3":"5","4":"4","5":"4","7":"3","8":"5","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 12:26:50', '', NULL, '', NULL),
	('BUL30002018-0427122655', 'BUL30002018-0427120411', '9', '{"12":"5","13":"5","14":"4","15":"5","1":"4","2":"5","3":"5","4":"5","5":"4","7":"5","8":"4","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 12:27:26', '', NULL, '', NULL),
	('BUL30002018-0427122728', 'BUL30002018-0427120411', '10', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 12:27:46', '', NULL, '', NULL),
	('BUL30002018-0427122757', 'BUL30002018-0427120411', '11', '{"12":"4","13":"4","14":"4","15":"4","1":"5","2":"4","3":"4","4":"3","5":"4","7":"4","8":"4","9":"5","10":"4"}', '', '', 'Jane/PC', '2018-04-27 12:28:19', '', NULL, '', NULL),
	('BUL30002018-0427122826', 'BUL30002018-0427120411', '12', '{"12":"5","13":"4","14":"4","15":"5","1":"4","2":"5","3":"5","4":"4","5":"4","7":"5","8":"5","9":"5","10":"4"}', '', '', 'Jane/PC', '2018-04-27 12:28:48', '', NULL, '', NULL),
	('BUL30002018-0427122850', 'BUL30002018-0427120411', '13', '{"12":"5","13":"4","14":"4","15":"4","1":"5","2":"5","3":"4","4":"4","5":"4","7":"5","8":"5","9":"5","10":"4"}', '', '', 'Jane/PC', '2018-04-27 12:30:15', '', NULL, '', NULL),
	('BUL30002018-0427123019', 'BUL30002018-0427120411', '14', '{"12":"4","13":"4","14":"4","15":"5","1":"4","2":"4","3":"4","4":"4","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 12:31:04', '', NULL, '', NULL),
	('BUL30002018-0427123107', 'BUL30002018-0427120411', '15', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"5","3":"5","4":"5","5":"5","7":"5","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 12:31:29', '', NULL, '', NULL),
	('BUL30002018-0427123159', 'BUL30002018-0427120411', '16', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 12:32:18', '', NULL, '', NULL),
	('BUL30002018-0427123220', 'BUL30002018-0427120411', '17', '{"12":"5","13":"5","14":"5","15":"4","1":"5","2":"4","3":"5","4":"3","5":"3","7":"4","8":"4","9":"5","10":"3"}', '', '', 'Jane/PC', '2018-04-27 12:32:46', '', NULL, '', NULL),
	('BUL30002018-0427123248', 'BUL30002018-0427120411', '18', '{"12":"4","13":"4","14":"4","15":"4","1":"5","2":"4","3":"5","4":"3","5":"4","7":"5","8":"5","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 12:33:05', '', NULL, '', NULL),
	('BUL30002018-0427123317', 'BUL30002018-0427120411', '19', '{"12":"5","13":"5","14":"5","15":"5","1":"5","2":"4","3":"4","4":"5","5":"4","7":"5","8":"4","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 12:33:37', '', NULL, '', NULL),
	('BUL30002018-0427123347', 'BUL30002018-0427120411', '20', '{"12":"5","13":"5","14":"5","15":"5","1":"4","2":"4","3":"5","4":"3","5":"4","7":"5","8":"5","9":"5","10":"4"}', '', '', 'Jane/PC', '2018-04-27 12:34:24', '', NULL, '', NULL),
	('BUL30002018-0427123426', 'BUL30002018-0427120411', '21', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 12:34:56', '', NULL, '', NULL),
	('BUL30002018-0427123459', 'BUL30002018-0427120411', '22', '{"12":"4","13":"4","14":"4","15":"4","1":"4","2":"4","3":"4","4":"4","5":"4","7":"4","8":"4","9":"4","10":"4"}', '', '', 'Jane/PC', '2018-04-27 12:35:15', '', NULL, '', NULL),
	('BUL30002018-0427123522', 'BUL30002018-0427120411', '23', '{"12":"4","13":"4","14":"4","15":"5","1":"4","2":"4","3":"4","4":"4","5":"5","7":"4","8":"5","9":"5","10":"5"}', '', '', 'Jane/PC', '2018-04-27 12:35:40', '', NULL, '', NULL);
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
