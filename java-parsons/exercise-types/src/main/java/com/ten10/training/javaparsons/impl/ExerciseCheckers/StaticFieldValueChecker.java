package com.ten10.training.javaparsons.impl.ExerciseCheckers;
;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.impl.ClassChecker;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class StaticFieldValueChecker implements ClassChecker {

    private final String goal;
    private final Object answer;

    public StaticFieldValueChecker(String goal, Object answer) {

        this.goal = goal;
        this.answer = answer;
    }

    @Override
    public String getGoal() {
        return goal;
    }

    @Override
    public Boolean validate(Field[] klassFields, ProgressReporter progressReporter) {

            if (!(klassFields.length == 1)) { //In this iteration of the Static Field exercise we only expect 1 field.
                progressReporter.reportRunnerError("Incorrect number of fields");
                return false;
            } else if (!(Modifier.isStatic(klassFields[0].getModifiers()))) {
                progressReporter.reportRunnerError("Field no static");
                return false;
            } else {
                Field field = klassFields[0];
                field.setAccessible(true);
                try {
                    if (field.get(field).equals(answer)) {
                        progressReporter.storeCapturedOutput(field.get(field).toString());
                        return true;
                    }
                } catch (IllegalAccessException e) {
                    progressReporter.reportRunnerError("No access to field: " + field.getName());
                }
            }
            return false;
        }
    }

