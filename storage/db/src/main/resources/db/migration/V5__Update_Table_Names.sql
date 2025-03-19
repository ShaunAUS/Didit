-- Rename tables to singular form
RENAME TABLE `resumes` TO `resume`;
RENAME TABLE `careers` TO `career`;
RENAME TABLE `main_tasks` TO `main_task`;
RENAME TABLE `spaces` TO `space`;
RENAME TABLE `memoirs` TO `memoir`;
RENAME TABLE `tags` TO `tag`;
RENAME TABLE `alarms` TO `alarm`;
RENAME TABLE `career_skills` TO `career_skill`;
RENAME TABLE `member_skills` TO `member_skill`;

-- Update foreign key relationships for resume
ALTER TABLE `resume`
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (`id`);

-- Update foreign key relationships for career
ALTER TABLE `career`
    ADD CONSTRAINT `FK_member_TO_career`
        FOREIGN KEY (`member_id`)
            REFERENCES `member` (`id`),
    ADD CONSTRAINT `FK_resume_TO_career`
        FOREIGN KEY (`resume_id`)
            REFERENCES `resume` (`id`);

-- Update foreign key relationships for main_task
ALTER TABLE `main_task`
    ADD CONSTRAINT `FK_career_TO_main_task`
        FOREIGN KEY (`career_id`)
            REFERENCES `career` (`id`);

-- Update foreign key relationships for space
ALTER TABLE `space`
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (`id`),
    ADD CONSTRAINT `FK_member_TO_space`
        FOREIGN KEY (`member_id`)
            REFERENCES `member` (`id`);

-- Update foreign key relationships for memoir
ALTER TABLE `memoir`
    ADD CONSTRAINT `FK_member_TO_memoir`
        FOREIGN KEY (`member_id`)
            REFERENCES `member` (`id`),
    ADD CONSTRAINT `FK_space_TO_memoir`
        FOREIGN KEY (`space_id`)
            REFERENCES `space` (`id`);

-- Fix tag table structure and relationships
ALTER TABLE `tag`
    ADD CONSTRAINT `FK_memoir_TO_tag`
        FOREIGN KEY (`memoir_id`)
            REFERENCES `memoir` (`id`);

-- Update foreign key relationships for career_skill
ALTER TABLE `career_skill`
    ADD CONSTRAINT `FK_career_TO_career_skill`
        FOREIGN KEY (`career_id`)
            REFERENCES `career` (`id`);

-- Update foreign key relationships for member_skill
ALTER TABLE `member_skill`
    ADD CONSTRAINT `FK_member_TO_member_skill`
        FOREIGN KEY (`member_id`)
            REFERENCES `member` (`id`);

-- Update foreign key relationships for alarm
ALTER TABLE `alarm`
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (`id`),
    ADD CONSTRAINT `FK_member_TO_alarm`
        FOREIGN KEY (`member_id`)
            REFERENCES `member` (`id`);
