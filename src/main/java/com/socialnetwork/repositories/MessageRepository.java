package com.socialnetwork.repositories;

import com.socialnetwork.entities.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByUserId(Long id);

    @Query("select msg from Message msg where msg.user.id in :ids order by msg.createdDate desc")
    List<Message> findCommunityMessages(@Param("ids") List<Long> ids, Pageable pageable);
}
