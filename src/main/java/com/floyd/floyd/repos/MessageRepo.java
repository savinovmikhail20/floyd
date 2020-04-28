package com.floyd.floyd.repos;

import com.floyd.floyd.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Long> {

    List<Message> findByTag(String tag);
    List<Message>  findByLocalDateTimeAfter(LocalDateTime localDateTime);
    List<Message> findByAuthor_Id(Long id);
    List<Message> findByAuthor_IdAndAdded(Long id, Boolean bool);
    List<Message> findByAdded(Boolean bool);
    @Override
    void deleteById(Long aLong);
}