//package com.skills.skills.models;
//
//
//import com.skills.skills.data.SkillsCategoryRepository;
//import com.skills.skills.data.TagRepository;
//import com.skills.skills.models.skill.SkillsCategory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class LoadDatabase {
//
//    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
//
//    @Bean
//    CommandLineRunner initDatabase(SkillsCategoryRepository repository) {
//
//        return args -> {
//
//            log.info("Preloading " + repository.save(new SkillsCategory("Charity/Non-Profit")));
//            log.info("Preloading " + repository.save(new SkillsCategory("Ceramics")));
//
//        };
//    }
//}