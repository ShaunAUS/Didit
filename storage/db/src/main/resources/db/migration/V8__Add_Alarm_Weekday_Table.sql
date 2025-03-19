CREATE TABLE `alarm_week_day`
(
    `id`       BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '알람 요일 고유 식별자',
    `alarm_id` BIGINT(20) NOT NULL COMMENT '알람 고유 식별자',
    `weekday`  INT(1)     NOT NULL COMMENT '요일 번호',
    PRIMARY KEY (`id`),
    CONSTRAINT `FK_alarm_TO_alarm_week_day_1`
        FOREIGN KEY (`alarm_id`)
            REFERENCES `alarm` (`id`)
);
