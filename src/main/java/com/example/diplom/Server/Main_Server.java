package com.example.diplom.Server;

public class Main_Server {
    public String LOGIN;
    public String PASSWORD_HASH;
    public String COMPUTER_HASH;

    public Main_Server(String LOGIN, String PASSWORD_HASH, String COMPUTER_HASH) {
        this.LOGIN = LOGIN;
        this.PASSWORD_HASH = PASSWORD_HASH;
        this.COMPUTER_HASH = COMPUTER_HASH;
    }

    public boolean check (String login, String pass_hash, String computerID) {
        
        if(LOGIN.equals(login)){
            if(PASSWORD_HASH.equals(pass_hash)){
                if(COMPUTER_HASH.equals(computerID)){
                    return true;
                }
                else {
                    System.out.println("error comp");
                }
            }
            else {
                System.out.println("error pass");
            }
        }
        else {
            System.out.println("error login");
        }
        return false;
    }
}
