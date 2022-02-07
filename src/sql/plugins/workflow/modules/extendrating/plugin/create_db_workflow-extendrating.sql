--
-- Table structure for table task_alert_cf
--
DROP TABLE IF EXISTS task_extend_rating_update_resource_state_cf;
CREATE TABLE task_extend_rating_update_resource_state_cf(
  id_task INT DEFAULT 0 NOT NULL,
  id_state_start INT DEFAULT 0 NOT NULL,
  id_state_end INT DEFAULT 0 NOT NULL,
  nb_vote INT DEFAULT 0 NOT NULL,
  operation INT DEFAULT 0 NOT NULL,
  PRIMARY KEY (id_task)
);

--
-- Table structure for table extend_rating_update_resource_state_queue
--
DROP TABLE IF EXISTS extend_rating_update_resource_state_queue;
CREATE TABLE extend_rating_update_resource_state_queue (
  id_resource INT DEFAULT 0 NOT NULL,
  id_task INT DEFAULT 0 NOT NULL,
  resource_type VARCHAR(255) DEFAULT '' NOT NULL,
  initial_state_change INT DEFAULT 0 NOT NULL,
  PRIMARY KEY ( id_resource )
);