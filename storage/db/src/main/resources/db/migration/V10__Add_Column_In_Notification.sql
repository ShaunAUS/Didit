ALTER TABLE notification
ADD COLUMN `delete_flag`  BIT(1) NOT NULL COMMENT '삭제 여부';
