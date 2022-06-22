package com.skills.skills.data;

import com.skills.skills.models.user.Message;
import org.hibernate.boot.model.source.spi.Sortable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface MessagesRepository extends PagingAndSortingRepository<Message, Integer> {

}
