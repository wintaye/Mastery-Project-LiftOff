package com.skills.skills.data;

import com.skills.skills.models.SkillsCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsCategoryRepository extends CrudRepository<SkillsCategory,Integer> {
}
