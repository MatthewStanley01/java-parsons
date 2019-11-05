package com.ten10.training.javaparsons.webapp.controllers;


import com.ten10.training.javaparsons.Exercise;
import com.ten10.training.javaparsons.ExerciseRepository;
import com.ten10.training.javaparsons.ProgressReporter;
import com.ten10.training.javaparsons.Solution;
import com.ten10.training.javaparsons.webapp.views.Results;
import com.ten10.training.javaparsons.webapp.views.SubmittedSolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("/exercise/")
public class ExerciseController {

    @Autowired
    ExerciseRepository exerciseRepository;

    public static class ExerciseInformation {
        private final String url;
        private final String title;
        private final String description;
        private final String precedingCode;
        private final String followingCode;
        private final int dropdownCount;


        // Constructor
        ExerciseInformation(String URL, String title, String description, String precedingCode, String followingCode, int dropdownCount) {
            url = URL;
            this.title = title;
            this.description = description;
            this.precedingCode = precedingCode;
            this.followingCode = followingCode;
            this.dropdownCount = dropdownCount;
        }

        /**
         * @return The title of this {@link Exercise}.
         */
        public String getTitle() {
            return title;
        }

        /**
         * @return the URL of this {@link Exercise}.
         */
        public String getUrl() {
            return url;
        }
        public String getDescription() { return description;}
        public String getPrecedingCode() { return precedingCode;}
        public String getFollowingCode() { return followingCode;}
        public int getDropdownCount() { return dropdownCount; }

    }


    /**
     * <p>
     *     When {@link org.springframework.boot.autoconfigure.SpringBootApplication} receives a GET request with the
     *     value {@code getExercises} this method is called and run.</p>
     * <p>
     *     This method gets all stored {@link Exercise} in the {@link ExerciseRepository} as {@link ExerciseInformation}.
     * </p>
     * @return a list of all exercises with the title and description
     */
    @RequestMapping(value = "getExercises", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ExerciseInformation> getExercises() {
        List<ExerciseInformation> combinedExerciseParameters = new ArrayList<>();
        for (int i = 1; i <= exerciseRepository.getExerciseArraySize(); i++) {
            Exercise exercise = exerciseRepository.getExerciseByIdentifier(i);
            combinedExerciseParameters.add(new ExerciseInformation("exercise/" + i
                ,exercise.getTitle()
                ,exercise.getDescription()
                ,exercise.getPrecedingCode()
                ,exercise.getFollowingCode()
                ,exercise.getDropdownNumber()
            ));

        }
        return combinedExerciseParameters;
    }

    @RequestMapping(value = "getDropdownListMembers", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<String> getDropdownListMembers() {
        List<String> op = new ArrayList<String>() {};
        for(DropdownListMembers dlm: DropdownListMembers.values()){
            op.add(dlm.inputText);
        }
        return op;
    }


    /**
     * <p>
     *     When {@link org.springframework.boot.autoconfigure.SpringBootApplication} receives a POST request with the
     *     identifier this method is called and run. The identifier corresponds to the ID of an {@link Exercise}.
     * </p>
     * <p>
     *     This method stores the users input, creates a {@link Solution} using the user input, calls
     *     {@link Solution#evaluate()} which compiles, runs, and checks the user input. Stores the results in a
     *     {@link Results} object.
     * </p>
     * @param exercise the exercise type, get by the identifier given from
     *                       {@link org.springframework.boot.autoconfigure.SpringBootApplication} from the POST request.
     * @param submittedSolution is the solution submitted by the user
     * @return a {@link Results} object containing the result of compiling, running, and checking the user solution.
     */
    @RequestMapping(value ="{identifier}", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Results markUserInput(
        @PathVariable("identifier") Exercise exercise,
        @RequestBody SubmittedSolution submittedSolution) throws Exception {
        Results results = new Results();
        Solution solution = exercise.getSolutionFromUserInput(submittedSolution.getInput(), results);
        results.setSuccessfulSolution(solution.evaluate());
        return results;
    }
}
