package com.skills.skills.data;

import com.skills.skills.models.user.Message;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessagesRepository extends PagingAndSortingRepository<Message, Integer> {
}
