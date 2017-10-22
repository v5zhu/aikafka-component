CREATE TABLE `t_b_schedule_job` (
  `job_id` bigint(20) NOT NULL,
  `job_name` varchar(32) NOT NULL,
  `job_group` varchar(32) NOT NULL,
  `job_status` varchar(32) NOT NULL,
  `cron_expression` varchar(32) NOT NULL,
  `is_concurrent` varchar(10) NOT NULL,
  `description` varchar(128) DEFAULT NULL,
  `bean_class` varchar(255) NOT NULL,
  `spring_id` varchar(64) NOT NULL,
  `method_name` varchar(64) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

