package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.ProgressReporter;

import java.lang.reflect.Field;

public interface ClassChecker {

    String getGoal();

    Boolean validate(Field[] klassFields, ProgressReporter progressReporter);
}
