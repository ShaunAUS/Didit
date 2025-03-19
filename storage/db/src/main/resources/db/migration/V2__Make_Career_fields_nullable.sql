ALTER TABLE `careers`
    MODIFY `company` VARCHAR(20) NULL COMMENT '회사명',
    MODIFY `department` VARCHAR(20) NULL COMMENT '부서명',
    MODIFY `position` VARCHAR(20) NULL COMMENT '직무',
    MODIFY `work_start_date` TIMESTAMP NULL COMMENT '근무 시작 일자',
    MODIFY `work_finish_date` TIMESTAMP NULL COMMENT '근무 종료 일자',
    MODIFY `is_working` BIT(1) NULL COMMENT '현재 재직 여부',
    MODIFY `created_date` TIMESTAMP NULL COMMENT '생성 일시',
    MODIFY `updated_date` TIMESTAMP NULL COMMENT '마지막 업데이트 일시',
    MODIFY `delete_flag` BIT(1) NOT NULL COMMENT '삭제 여부' DEFAULT 0;

ALTER TABLE `careers`
DROP FOREIGN KEY `FK_resumes_TO_careers_1`,
    DROP FOREIGN KEY `FK_resumes_TO_careers_2`,
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (`id`);
