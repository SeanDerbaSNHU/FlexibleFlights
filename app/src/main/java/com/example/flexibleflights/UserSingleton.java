package com.example.flexibleflights;

public class UserSingleton {
    private static UserSingleton instance = null;

    private String email;
    protected UserSingleton(){email = "";}

    public static UserSingleton getInstance(){
        if(instance == null){
            instance = new UserSingleton();
        }
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
