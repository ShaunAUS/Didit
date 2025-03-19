-- 기존 컬럼 수정
ALTER TABLE `memoirs`
    MODIFY `title` VARCHAR(50) NOT NULL COMMENT '제목',
    MODIFY `content` TEXT NOT NULL COMMENT '내용',
    MODIFY `write_date` TIMESTAMP NOT NULL COMMENT '작성 일시',
    MODIFY `created_date` TIMESTAMP NULL COMMENT '생성 일시',
    MODIFY `updated_date` TIMESTAMP NULL COMMENT '마지막 업데이트 일시',
    MODIFY `delete_flag` BIT(1) NOT NULL COMMENT '삭제 여부' DEFAULT 0;

-- 새 컬럼 추가
ALTER TABLE `memoirs`
    ADD COLUMN `template` INT NOT NULL COMMENT '템플릿 ID';

-- 기존 PRIMARY KEY 수정
ALTER TABLE `memoirs`
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (`id`);

-- 외래 키 수정
ALTER TABLE `memoirs`
    DROP FOREIGN KEY `FK_spaces_TO_memoirs_2`;
