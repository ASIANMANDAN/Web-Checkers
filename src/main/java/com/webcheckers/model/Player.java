package com.webcheckers.model;

/**
 * PLayer instance. Contains username for the isntance of that Player.
 */
public class Player {

    private final String username;

    /**
     * Intstatiates Player with a username.
     * @param newUserName new user name
     *
     * @author Dan Wang, Emily Lederman, Kevin Paradis, Nathan Farrell
     */
    public Player(String newUserName){
        this.username = newUserName;
    }

    /**
     * Gets username.
     * @return username
     *
     * @author Dan Wang, Emily Lederman, Kevin Paradis, Nathan Farrell
     */
    public String getUsername(){
        return this.username;
    }

}
