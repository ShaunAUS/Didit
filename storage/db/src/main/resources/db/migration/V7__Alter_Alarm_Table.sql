-- 기존 컬럼 수정
ALTER TABLE `alarm`
    MODIFY `time` TIME NOT NULL COMMENT '알람 시간',
    MODIFY `member_id` BIGINT(20) NOT NULL COMMENT '회원 고유 식별자',
    MODIFY `created_date` TIMESTAMP NOT NULL COMMENT '생성 일시',
    MODIFY `updated_date` TIMESTAMP NOT NULL COMMENT '마지막 업데이트 일시',
    MODIFY `delete_flag` BIT(1) NOT NULL COMMENT '삭제 여부' DEFAULT 0;

-- 컬럼명 변경 (is_alarm_enabled -> is_alarm_enable)
ALTER TABLE `alarm`
    CHANGE COLUMN `is_alarm_enabled` `is_alarm_enable` BIT(1) NOT NULL COMMENT '알람 활성화 여부';

-- 불필요한 컬럼 삭제
ALTER TABLE `alarm`
    DROP COLUMN `day`;
