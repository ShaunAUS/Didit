-- Member Table (회원 테이블)
CREATE TABLE `member`
(
    `id`           BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '고유 식별자',
    `email`        VARCHAR(50) NOT NULL COMMENT '이메일 주소',
    `password`     VARCHAR(30) NOT NULL COMMENT '비밀번호',
    `name`         VARCHAR(50) NOT NULL COMMENT '사용자 이름',
    `nick_name`    VARCHAR(50) NOT NULL COMMENT '닉네임',
    `job_family`   INT(1)      NOT NULL COMMENT '직군',
    `job_role`     INT(1)      NOT NULL COMMENT '직무',
    `created_date` TIMESTAMP   NOT NULL COMMENT '계정 생성 일시',
    `updated_date` TIMESTAMP   NOT NULL COMMENT '마지막 업데이트 일시',
    `delete_flag`  BIT(1)      NOT NULL COMMENT '계정 삭제 여부',
    PRIMARY KEY (`id`)
);

-- Resumes Table (이력서 테이블)
CREATE TABLE `resumes`
(
    `id`                    BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '이력서 고유 식별자',
    `member_id`             BIGINT(20)  NOT NULL COMMENT '회원 고유 식별자',
    `phone_number`          VARCHAR(11) NOT NULL COMMENT '전화번호',
    `email`                 VARCHAR(50) NOT NULL COMMENT '이메일 주소',
    `one_line_introduction` VARCHAR(50) NOT NULL COMMENT '한 줄 소개',
    `created_date`          TIMESTAMP   NOT NULL COMMENT '생성 일시',
    `updated_date`          TIMESTAMP   NOT NULL COMMENT '마지막 업데이트 일시',
    `delete_flag`           BIT(1)      NOT NULL COMMENT '삭제 여부',
    PRIMARY KEY (`id`, `member_id`),
    CONSTRAINT `FK_Member_TO_resumes_1`
        FOREIGN KEY (`member_id`)
            REFERENCES `member` (`id`)
);

-- Careers Table (경력 테이블)
CREATE TABLE `careers`
(
    `id`               BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '경력 고유 식별자',
    `resume_id`        BIGINT(20)  NOT NULL COMMENT '이력서 고유 식별자',
    `member_id`        BIGINT(20)  NOT NULL COMMENT '회원 고유 식별자',
    `company`          VARCHAR(20) NOT NULL COMMENT '회사명',
    `department`       VARCHAR(20) NOT NULL COMMENT '부서명',
    `position`         VARCHAR(20) NOT NULL COMMENT '직무',
    `work_start_date`  TIMESTAMP   NOT NULL COMMENT '근무 시작 일자',
    `work_finish_date` TIMESTAMP   NOT NULL COMMENT '근무 종료 일자',
    `is_working`       BIT(1)      NOT NULL COMMENT '현재 재직 여부',
    `created_date`     TIMESTAMP   NOT NULL COMMENT '생성 일시',
    `updated_date`     TIMESTAMP   NOT NULL COMMENT '마지막 업데이트 일시',
    `delete_flag`      BIT(1)      NOT NULL COMMENT '삭제 여부',
    PRIMARY KEY (`id`, `resume_id`, `member_id`),
    CONSTRAINT `FK_resumes_TO_careers_1`
        FOREIGN KEY (`resume_id`)
            REFERENCES `resumes` (`id`),
    CONSTRAINT `FK_resumes_TO_careers_2`
        FOREIGN KEY (`member_id`)
            REFERENCES `resumes` (`member_id`)
);

-- Main Tasks Table (주요 업무 테이블)
CREATE TABLE `main_tasks`
(
    `id`               BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '주요 업무 고유 식별자',
    `career_id`        BIGINT(20)   NOT NULL COMMENT '경력 고유 식별자',
    `work_start_date`  TIMESTAMP    COMMENT '업무 시작 일자',
    `work_finish_date` TIMESTAMP    COMMENT '업무 종료 일자',
    `achievement`      VARCHAR(100) NOT NULL COMMENT '업무 성과',
    `explanation`          TEXT     NOT NULL COMMENT '업무 설명',
    `created_date`     TIMESTAMP    COMMENT '생성 일시',
    `updated_date`     TIMESTAMP    COMMENT '마지막 업데이트 일시',
    `delete_flag`      BIT(1)       NOT NULL COMMENT '삭제 여부' DEFAULT 0,
    PRIMARY KEY (`id`)
);

-- Spaces Table (공간 테이블)
CREATE TABLE `spaces`
(
    `id`           BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '공간 고유 식별자',
    `member_id`    BIGINT(20)  NOT NULL COMMENT '회원 고유 식별자',
    `name`         VARCHAR(50) NOT NULL COMMENT '공간 이름',
    `created_date` TIMESTAMP   NOT NULL COMMENT '생성 일시',
    `updated_date` TIMESTAMP   NOT NULL COMMENT '마지막 업데이트 일시',
    `delete_flag`  BIT(1)      NOT NULL COMMENT '삭제 여부',
    PRIMARY KEY (`id`, `member_id`),
    CONSTRAINT `FK_Member_TO_spaces_1`
        FOREIGN KEY (`member_id`)
            REFERENCES `member` (`id`)
);

-- Memoirs Table (회고록 테이블)
CREATE TABLE `memoirs`
(
    `id`           BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '회고록 고유 식별자',
    `space_id`     BIGINT(20)  NOT NULL COMMENT '공간 고유 식별자',
    `member_id`    BIGINT(20)  NOT NULL COMMENT '회원 고유 식별자',
    `title`        VARCHAR(50) NOT NULL COMMENT '제목',
    `content`      TEXT        NOT NULL COMMENT '내용',
    `write_date`   TIMESTAMP   NOT NULL COMMENT '작성 일시',
    `created_date` TIMESTAMP   NOT NULL COMMENT '생성 일시',
    `updated_date` TIMESTAMP   NOT NULL COMMENT '마지막 업데이트 일시',
    `delete_flag`  BIT(1)      NOT NULL COMMENT '삭제 여부',
    PRIMARY KEY (`id`, `space_id`, `member_id`),
    CONSTRAINT `FK_spaces_TO_memoirs_1`
        FOREIGN KEY (`space_id`)
            REFERENCES `spaces` (`id`),
    CONSTRAINT `FK_spaces_TO_memoirs_2`
        FOREIGN KEY (`member_id`)
            REFERENCES `spaces` (`member_id`)
);

-- Tags Table (태그 테이블)
CREATE TABLE `tags`
(
    `id`       BIGINT(20)  NOT NULL AUTO_INCREMENT COMMENT '태그 고유 식별자',
    `space_id` BIGINT(20)  NOT NULL COMMENT '공간 고유 식별자',
    `memoir_id` BIGINT(20)  NOT NULL COMMENT '식별자',
    `name`     VARCHAR(20) NOT NULL COMMENT '태그 이름',
    PRIMARY KEY (`id`, `space_id`),
    CONSTRAINT `FK_memoirs_TO_Tags_1`
        FOREIGN KEY (`space_id`)
            REFERENCES `memoirs` (`id`)
);

-- Template Table (템플릿 테이블)
CREATE TABLE `template`
(
    `id`       BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '템플릿 고유 식별자',
    `space_id` BIGINT(20) NOT NULL COMMENT '공간 고유 식별자',
    `memoir_id` BIGINT(20)  NOT NULL COMMENT '식별자',
    `type`     INT(1)     NOT NULL COMMENT '템플릿 유형',
    PRIMARY KEY (`id`, `space_id`),
    CONSTRAINT `FK_memoirs_TO_template_1`
        FOREIGN KEY (`space_id`)
            REFERENCES `memoirs` (`id`)
);

-- Career Skills Table (경력 기술 테이블)
CREATE TABLE `career_skills`
(
    `id`        BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '경력 기술 고유 식별자',
    `career_id` BIGINT(20) NOT NULL COMMENT '경력 고유 식별자',
    `type`      INT(1)     NOT NULL COMMENT '기술 유형',
    PRIMARY KEY (`id`, `career_id`),
    CONSTRAINT `FK_careers_TO_career_skills_1`
        FOREIGN KEY (`career_id`)
            REFERENCES `careers` (`id`)
);

-- Member Skills Table (회원 기술 테이블)
CREATE TABLE `member_skills`
(
    `id`        BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '회원 기술 고유 식별자',
    `member_id` BIGINT(20) NOT NULL COMMENT '회원 고유 식별자',
    `type`      INT(1)     NOT NULL COMMENT '기술 유형',
    PRIMARY KEY (`id`, `member_id`),
    CONSTRAINT `FK_Member_TO_member_skills_1`
        FOREIGN KEY (`member_id`)
            REFERENCES `member` (`id`)
);

-- Alarms Table (알람 테이블)
CREATE TABLE `alarms`
(
    `id`             BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '알람 고유 식별자',
    `member_id`      BIGINT(20) NOT NULL COMMENT '회원 고유 식별자',
    `time`           TIMESTAMP  NOT NULL COMMENT '알람 시간',
    `day`            TIMESTAMP  NOT NULL COMMENT '알람 요일',
    `is_alarm_enabled` BIT(1)     NOT NULL COMMENT '알람 활성화 여부',
    `created_date`   TIMESTAMP  NOT NULL COMMENT '생성 일시',
    `updated_date`   TIMESTAMP  NOT NULL COMMENT '마지막 업데이트 일시',
    `delete_flag`    BIT(1)     NOT NULL COMMENT '계정 삭제 여부',
    PRIMARY KEY (`id`, `member_id`),
    CONSTRAINT `FK_Member_TO_alarms_1`
        FOREIGN KEY (`member_id`)
            REFERENCES `member` (`id`)
);
