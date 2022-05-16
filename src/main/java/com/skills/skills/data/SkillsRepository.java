package com.skills.skills.data;

import com.skills.skills.models.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsRepository extends CrudRepository<Skill, Integer> {

}
