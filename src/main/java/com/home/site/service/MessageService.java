package com.home.site.service;

import com.home.site.domain.*;
import com.home.site.domain.dto.*;
import com.home.site.repos.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;


@Service
public class MessageService {
    private final MessageRepo messageRepo;

    public MessageService(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public Page<MessageDto> messageList(Pageable pageable, String filter, User user) {
        if (filter != null && !filter.isEmpty()) {
            return messageRepo.findByTag(filter, pageable, user);
        } else {
            return messageRepo.findAll(pageable, user);
        }
    }

    public Page<MessageDto> messageListForUser(Pageable pageable, User currentUser, User author) {
        return messageRepo.findByUser(pageable, author, currentUser);
    }
}
