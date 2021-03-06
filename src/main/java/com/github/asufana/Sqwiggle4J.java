package com.github.asufana;

import java.util.*;

import rx.Observable;

import com.github.asufana.resources.*;
import com.google.gson.reflect.*;

public class Sqwiggle4J extends AbstractSqwiggle4J {
    
    public static final String BASE_URL = "https://api.sqwiggle.com";
    private final String token;
    
    public Sqwiggle4J(final String token) {
        this.token = token;
    }
    
    public Observable<User> user(final Integer userId) {
        return request(token,
                       BASE_URL + "/users/" + userId.toString(),
                       new TypeToken<User>() {});
    }
    
    public Observable<User> users() {
        final Observable<List<User>> userList = userList();
        return flat(userList);
    }
    
    Observable<List<User>> userList() {
        return request(token,
                       BASE_URL + "/users",
                       new TypeToken<List<User>>() {});
    }
    
    public Observable<Message> message(final Integer messageId) {
        return request(token,
                       BASE_URL + "/messages/" + messageId.toString(),
                       new TypeToken<Message>() {});
    }
    
    public Observable<Message> messages() {
        return messages(null, null);
    }
    
    public Observable<Message> messages(final Integer page, final Integer limit) {
        final Observable<List<Message>> messageList = messageList(page, limit);
        return flat(messageList);
    }
    
    Observable<List<Message>> messageList(final Integer page,
                                          final Integer limit) {
        return request(token,
                       String.format("%s/messages?%s&%s",
                                     BASE_URL,
                                     pageParamString(page),
                                     limitParamString(limit)),
                       new TypeToken<List<Message>>() {});
    }
    
    private String pageParamString(final Integer page) {
        return String.format("page=%s", page != null
                ? page.toString()
                : "1");
    }
    
    private String limitParamString(final Integer limit) {
        return String.format("limit=%s", limit != null
                ? limit.toString()
                : "100");
    }
    
}
