package com.ten10.training.javaparsons.impl;


import com.ten10.training.javaparsons.ProgressReporter;

public interface CapturedOutputChecker{

    String getGoal();

    Boolean validate(String output, ProgressReporter progressReporter);


}
