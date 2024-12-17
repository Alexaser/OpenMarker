package com.javarush.finalproject.Servlets;


public class User {
    private String name;
    private static User instance;

    private int countGamesMysteriousForest = 0;
    private User() {}
    public static User getUser(){
        if(instance==null){
            instance = new User();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountGamesMysteriousForest() {
        return countGamesMysteriousForest;
    }

    public void setCountGamesMysteriousForest(int count) {
        this.countGamesMysteriousForest=count;
    }

}
