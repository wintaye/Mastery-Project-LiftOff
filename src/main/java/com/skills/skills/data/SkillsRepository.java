package com.skills.skills.data;

import com.skills.skills.models.Skill;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsRepository extends PagingAndSortingRepository<Skill, Integer> {

}
