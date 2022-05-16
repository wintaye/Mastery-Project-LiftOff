//package com.skills.skills.models;
//
//import com.skills.skills.data.SkillsCategoryRepository;
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
//            log.info("Preloading " + repository.save(new SkillsCategory("Dance")));
//            log.info("Preloading " + repository.save(new SkillsCategory("Visual Arts")));
//            log.info("Preloading " + repository.save(new SkillsCategory("Performing Arts")));
//            log.info("Preloading " + repository.save(new SkillsCategory("Woodworking")));
//            log.info("Preloading " + repository.save(new SkillsCategory("Language")));
//            log.info("Preloading " + repository.save(new SkillsCategory("Music")));
//            log.info("Preloading " + repository.save(new SkillsCategory("Coding")));
//            log.info("Preloading " + repository.save(new SkillsCategory("Leisure Sports")));
//            log.info("Preloading " + repository.save(new SkillsCategory("Home Repair")));
//        };
//    }
//}