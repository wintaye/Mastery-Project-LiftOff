package com.skills.skills.data;

import com.skills.skills.models.event.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Integer> {


}
