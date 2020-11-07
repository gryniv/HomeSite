package com.home.site.domain;

import com.home.site.domain.def.DefaultUser;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "usr")
public class User extends DefaultUser {
    private String name;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> messages;

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = {@JoinColumn(name = "channel_id")},
            inverseJoinColumns = {@JoinColumn(name = "subscriber_id")}
    )
    private Set<DefaultUser> subscribers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = {@JoinColumn(name = "subscriber_id")},
            inverseJoinColumns = {@JoinColumn(name = "channel_id")}
    )
    private Set<DefaultUser> subscriptions = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<DefaultUser> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<DefaultUser> subscribers) {
        this.subscribers = subscribers;
    }

    public Set<DefaultUser> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<DefaultUser> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
