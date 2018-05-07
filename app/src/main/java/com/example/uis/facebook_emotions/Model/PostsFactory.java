package com.example.uis.facebook_emotions.Model;

import java.util.ArrayList;

public enum PostsFactory {
    INSTANCE;


    private ArrayList<Post> posts;

    PostsFactory() {

        posts = new ArrayList<Post>(10);

        posts.add(new Post("The weather is very nice",
                "http://pluspng.com/img-png/happy-guy-png-png-600x570-happy-person-transparent-background-600.png"));

    }

    public ArrayList<Post> getPosts(){
        return this.posts;
    }
}
