-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.31-MariaDB - mariadb.org binary distribution
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

-- Dumping data for table iris_bulacan_dost3.contact_information: ~1 rows (approximately)
/*!40000 ALTER TABLE `contact_information` DISABLE KEYS */;
INSERT INTO `contact_information` (`contact_id`, `organization`, `org_type`, `office_name`, `contact_person`, `tel_no`, `fax_no`, `mobile_no`, `email`, `created_by`, `created_at`, `updated_by`, `updated_at`, `deleted_by`, `deleted_at`) VALUES
	('BUL30002018-0424085633', 'Sample', 'OTHERS', '', '', '', '', '', '', 'IRIS3000/SYS', '2018-04-24 08:56:38', '', NULL, '', NULL);
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

-- Dumping data for table iris_bulacan_dost3.raid_table: ~0 rows (approximately)
/*!40000 ALTER TABLE `raid_table` DISABLE KEYS */;
INSERT INTO `raid_table` (`id`, `file_name`, `file_display_name`, `file_path`, `file_ext`, `file_size`, `file_hash`, `reference_state`, `reference_description`, `created_by`, `created_at`, `deleted_by`, `deleted_at`) VALUES
	('RAID30002018_0423122205', 'RAID30002018_0423122205_D87CFFBBB0D031A24656D049D1CB6C5B2276D5A7', 'PulilanWorkplan', 'bin', 'xlsx', 10319, 'D87CFFBBB0D031A24656D049D1CB6C5B2276D5A7', 1, 'PROJECT ATTACHMENT', 'IRIS3000/SYS', '2018-04-23 12:22:05', '', NULL);
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
  `student_address` varchar(500) DEFAULT NULL,
  `student_city_municipality` varchar(500) DEFAULT NULL,
  `student_province` varchar(500) DEFAULT NULL,
  `created_by` varchar(500) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_by` varchar(500) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_by` varchar(500) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`scholar_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table iris_bulacan_dost3.scholar_information: ~2 rows (approximately)
/*!40000 ALTER TABLE `scholar_information` DISABLE KEYS */;
INSERT INTO `scholar_information` (`scholar_id`, `student_number`, `last_name`, `first_name`, `middle_name`, `ext_name`, `gender`, `scholar_type`, `merit_type`, `course`, `year_level`, `section`, `university`, `mobile_no`, `tel_no`, `e_mail`, `student_address`, `student_city_municipality`, `student_province`, `created_by`, `created_at`, `updated_by`, `updated_at`, `deleted_by`, `deleted_at`) VALUES
	('BUL30002018-0423093813', '2014113844', 'perello', 'jhon melvin', 'nieto', '', 'MALE', 1, 1, 'bsit', 1, '4A-G1', 'Bulacan State University', '09368955866', '', 'jhmvinperello@gmail.com', NULL, NULL, NULL, 'IRIS3000/SYS', '2018-04-23 09:38:48', '', NULL, '', NULL),
	('BUL30002018-0423130715', '2014112470', 'Liwanag', 'Sharlotte', 'Baguio', '', 'FEMALE', 1, 1, 'BSIT', 4, '4E-G2', 'Bulacan State University', '09368955866', '', 'sharlotte_liwanag@gmail.com', NULL, NULL, NULL, 'IRIS3000/SYS', '2018-04-23 13:08:00', '', NULL, '', NULL);
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

-- Dumping data for table iris_bulacan_dost3.scholar_submission: ~3 rows (approximately)
/*!40000 ALTER TABLE `scholar_submission` DISABLE KEYS */;
INSERT INTO `scholar_submission` (`submission_id`, `fk_scholar_id`, `fk_transmittal_id`, `documents_submitted`, `remarks`, `created_by`, `created_at`, `updated_by`, `updated_at`, `deleted_by`, `deleted_at`) VALUES
	('BUL30002018-0423130646', 'BUL30002018-0423093813', NULL, 'COR,OR,SOA, ROG', 'FOR ENDORSEMENT AND APPEAL', 'IRIS3000/SYS', '2018-04-23 13:06:46', 'IRIS3000/SYS', '2018-04-23 13:17:11', '', NULL),
	('BUL30002018-0423130817', 'BUL30002018-0423130715', NULL, 'COR, SOA, OR', 'For Endorsement', 'IRIS3000/SYS', '2018-04-23 13:08:17', '', NULL, 'IRIS3000/SYS', '2018-04-23 13:27:44'),
	('BUL30002018-0423135325', 'BUL30002018-0423130715', NULL, 'COR, OR, SOA, ROG', 'FOR ENDORSEMENT', 'IRIS3000/SYS', '2018-04-23 13:53:25', '', NULL, '', NULL);
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
INSERT INTO `setup_projects` (`project_code`, `spin_no`, `company_name`, `company_owner`, `history`, `owner_position`, `owner_address`, `project_name`, `project_status`, `project_type`, `endorsed_date`, `approved_date`, `approved_funding`, `moa_date`, `actual_cost`, `duration_from`, `duration_to`, `factory_street`, `factory_brgy`, `factory_city`, `factory_landmark`, `year_established`, `business_activity`, `capital_classification`, `employment_classification`, `company_ownership`, `profitability`, `registration_info`, `major_products`, `existing_market`, `website`, `created_by`, `created_at`, `updated_by`, `updated_at`, `deleted_by`, `deleted_at`) VALUES
	('BUL30002018-0423122144', '', '123', '', '', '', '', '', 0, 'GIA', NULL, NULL, 0, NULL, 0, NULL, NULL, '', '', '3012', '', '', 1, 'MICRO', 'MICRO', 'SINGLE PROPRIETORSHIP', 'PROFIT', '', '', '', '', 'IRIS3000/SYS', '2018-04-23 12:21:49', '', NULL, '', NULL);
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

-- Dumping data for table iris_bulacan_dost3.system_file: ~0 rows (approximately)
/*!40000 ALTER TABLE `system_file` DISABLE KEYS */;
INSERT INTO `system_file` (`doc_id`, `fk_raid_id`, `doc_name`, `file_cluster`, `file_reference`, `created_by`, `created_at`, `updated_by`, `updated_at`, `deleted_by`, `deleted_at`) VALUES
	('BUL30002018-0423122205', 'RAID30002018_0423122205', 'PulilanWorkplan', 2, 'BUL30002018-0423122144', 'IRIS3000/SYS', '2018-04-23 12:22:05', '', NULL, '', NULL);
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

-- Dumping data for table iris_bulacan_dost3.training: ~0 rows (approximately)
/*!40000 ALTER TABLE `training` DISABLE KEYS */;
INSERT INTO `training` (`training_code`, `title_of_training`, `resource_speakers`, `venue`, `date_start`, `date_end`, `created_by`, `created_at`, `updated_by`, `updated_at`, `deleted_by`, `deleted_at`) VALUES
	('BUL30002018-0423081343', 'Sample', '', '', NULL, NULL, 'IRIS3000/SYS', '2018-04-23 08:13:48', '', NULL, '', NULL);
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

-- Dumping data for table iris_bulacan_dost3.training_data: ~0 rows (approximately)
/*!40000 ALTER TABLE `training_data` DISABLE KEYS */;
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
