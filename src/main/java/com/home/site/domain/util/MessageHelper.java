package com.home.site.domain.util;


import com.home.site.domain.User;

public abstract class MessageHelper {
    public static String getAuthorName(User author) {
        return author != null ? author.getUsername() : "<none>";
    }
}
