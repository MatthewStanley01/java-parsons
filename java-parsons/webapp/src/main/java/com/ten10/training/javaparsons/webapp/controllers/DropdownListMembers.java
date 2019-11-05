package com.ten10.training.javaparsons.webapp.controllers;

public enum DropdownListMembers {

    blank(""),
    classOpen("public class Main{"),
    mainMethodOpen("public static void main(String[] args){"),
    printExercisePaths("System.out.println(\"Exercise Paths!\");"),
    closeBracket("}");

    String inputText;

    DropdownListMembers(String inputText) {
        this.inputText = inputText;
    }
}
