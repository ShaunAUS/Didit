CREATE TABLE notification
(
    id                BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '알림 고유 식별자',
    content           VARCHAR(100) NOT NULL COMMENT '알림 내용',
    url               VARCHAR(200) NOT NULL COMMENT '알림 링크',
    is_read           BOOLEAN      NOT NULL DEFAULT FALSE COMMENT '읽음 여부',
    notification_type INT          NOT NULL COMMENT '알림 유형',
    receiver_id       BIGINT       NOT NULL COMMENT '수신자 고유 식별자',
    `created_date`    TIMESTAMP    NOT NULL COMMENT '계정 생성 일시',
    `updated_date`    TIMESTAMP    NOT NULL COMMENT '마지막 업데이트 일시'
);
