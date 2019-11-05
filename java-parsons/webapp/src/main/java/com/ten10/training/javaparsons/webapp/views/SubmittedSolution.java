package com.ten10.training.javaparsons.webapp.views;

/**
 * Stores the raw solution provided by the user.
 * TODO maybe not the best way of storing raw input.
 */
public class SubmittedSolution {

    private String input;
    
    public String getInput() {
        return input;
    }

    public void setInput(String input, String id) {
        this.input = input;
    }
}
