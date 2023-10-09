package model;

public class Knowledge {
    private static double courseFitKnowledgeIndex = 1.05;
    private static double courseUnFitKnowledgeIndex = 0.95;
    private static double loseKnowledgeWhenPlayIndex = 0.15;
    private static double fullKnowledge = 196;

    private int mandarinKnowledge;
    private int mathKnowledge;
    private int englishKnowledge;
    private int selectionOneKnowledge;
    private int selectionTwoKnowledge;
    private int selectionThreeKnowledge;

    public Knowledge() {
        mandarinKnowledge = 0;
        mathKnowledge = 0;
        englishKnowledge = 0;
        selectionOneKnowledge = 0;
        selectionTwoKnowledge = 0;
        selectionThreeKnowledge = 0;
    }


    //MODIFIED: this
    //EFFECT: Adding knowledge according to the activity. If the activity is of type of "playing", then reduce knowledge
    // for all three sub-knowledge. If the activity type is of type of "course", and the course is one of the three
    // selection, add knowledge to the corresponding selection.(the index is determined based on whether the course type
    // fits the student preference. If the course type is none of the three selection, do nothing.)
    public void addKnowledge(Activities a, String s1, String s2, String s3, Boolean prefer) {
        int actTime = a.getTime();
        Boolean courseOrPlay = a.getcourseOrPlay();//true课程false娱乐
        Boolean courseType = a.getActivityType();//true理科false文科
        if (!courseOrPlay) {
            mandarinKnowledge -= (int)(loseKnowledgeWhenPlayIndex * actTime);
            mathKnowledge -= (int)(loseKnowledgeWhenPlayIndex * actTime);
            englishKnowledge -= (int)(loseKnowledgeWhenPlayIndex * actTime);
            selectionOneKnowledge -= (int)(loseKnowledgeWhenPlayIndex * actTime);
            selectionTwoKnowledge -= (int)(loseKnowledgeWhenPlayIndex * actTime);
            selectionThreeKnowledge -= (int)(loseKnowledgeWhenPlayIndex * actTime);
        } else if (a.actInSelection(s1, s2, s3)) {
            int index = a.getCorrespondingIndex(s1, s2, s3);
            Boolean fitOrNot =  (prefer == courseType);
            addKnowledgeHelper(index, actTime, fitOrNot);
        } else {
            //doNothing
        }
    }

    //REQUIRES: index has to be one of 1 2 3 4 5 6.
    //MODIFIED: this
    //EFFECT: Adding knowledge to the corresponding selection bases on fitOrNot. (ex. if fitOrNot is true then use
    // "courseFitKnowledgeIndex" as the coefficient, otherwise use "courseUnFitKnowledgeIndex". If the index is 1,
    // modifies selectionOneKnowledge, selectionTwoKnowledge if 2, selectionThreeKnowledge if 3, mandarinKnowledge if 4
    // mathKnowledge if 5, englishknowledge if 6.
    public void addKnowledgeHelper(int index, int actTime, Boolean fitOrNot) {
        if (fitOrNot) {
            fitAddknowledge(index, actTime);
        } else {
            unFitAddknowledge(index, actTime);
        }
    }

    public void fitAddknowledge(int index, int actTime) {
        if (index == 1) {
            selectionOneKnowledge += (int) (courseFitKnowledgeIndex * actTime);
        } else if (index == 2) {
            selectionTwoKnowledge += (int) (courseFitKnowledgeIndex * actTime);
        } else if (index == 3) {
            selectionThreeKnowledge += (int) (courseFitKnowledgeIndex * actTime);
        } else if (index == 4) {
            mandarinKnowledge += (int) (courseFitKnowledgeIndex * actTime);
        } else if (index == 5) {
            mathKnowledge += (int) (courseFitKnowledgeIndex * actTime);
        } else {
            englishKnowledge += (int) (courseFitKnowledgeIndex * actTime);
        }
    }

    public void unFitAddknowledge(int index, int actTime) {
        if (index == 1) {
            selectionOneKnowledge += (int) (courseUnFitKnowledgeIndex * actTime);
        } else if (index == 2) {
            selectionTwoKnowledge += (int) (courseUnFitKnowledgeIndex * actTime);
        } else if (index == 3) {
            selectionThreeKnowledge += (int) (courseUnFitKnowledgeIndex * actTime);
        } else if (index == 4) {
            mandarinKnowledge += (int) (courseUnFitKnowledgeIndex * actTime);
        } else if (index == 5) {
            mathKnowledge += (int) (courseUnFitKnowledgeIndex * actTime);
        } else {
            englishKnowledge += (int) (courseUnFitKnowledgeIndex * actTime);
        }
    }


    //EFFECT: generate and print out the score for each subject according to the corresponding knowledge
    // of the student.
    // The basic score for Mandarin Math and English is (corresponding knowledge / fullKnowledge) * 150
    // when the knowledge is less than fullKnowledge. It will be 150 if the knowledge is greater than or equals to
    // fullKnowledge.
    // The basic score for combined art or combined science (depend on the selection) is (sum of corresponding 3
    // selection knowledge / (fullKnowledge * 3)) * 300 when the sum of the three knowledge is less than
    // fullKnowledge * 3. It will be 300 if the knowledge is greater than or equals to fullKnowledge.
    // Return the total score.
    public int takeExam(Boolean scienceOrArt) {
        int mandarinScore = takeExamForMandarin();
        int mathScore = takeExamForMath();
        int englishScore = takeExamForEnglish();
        int combinedScore = takeExamForCombinedScienceOrArt(scienceOrArt);
        return (mandarinScore + mathScore + englishScore + combinedScore);
    }

    //过了
    //EFFECTS: print out the basic score for Mandarin, which is (corresponding knowledge / fullKnowledge) * 150
    // when the Mandarinknowledge is less than fullKnowledge. It will be 150 if the knowledge is greater than or equals
    // to fullKnowledge. Return the calculated score.
    public int takeExamForMandarin() {
        if (mandarinKnowledge >= fullKnowledge) {
            System.out.println("Mandarin: 150");
            return 150;
        } else {
            int score = (int) ((mandarinKnowledge / fullKnowledge) * 150);
            System.out.println("Mandarin: " + score);
            return score;
        }
    }

    //过了
    //EFFECTS: print out the basic score for math, which is (corresponding knowledge / fullKnowledge) * 150
    // when the Mathknowledge is less than fullKnowledge. It will be 150 if the knowledge is greater than or equals
    // to fullKnowledge. Return the calculated score.
    public int takeExamForMath() {
        if (mathKnowledge >= fullKnowledge) {
            System.out.println("Math: 150");
            return 150;
        } else {
            int score = (int) ((mathKnowledge / fullKnowledge) * 150);
            System.out.println("Math: " + score);
            return score;
        }
    }

    //过了
    //EFFECTS: print out the basic score for english, which is (corresponding knowledge / fullKnowledge) * 150
    // when the englishknowledge is less than fullKnowledge. It will be 150 if the knowledge is greater than or equals
    // to fullKnowledge. Return the calculated score.
    public int takeExamForEnglish() {
        if (englishKnowledge >= fullKnowledge) {
            System.out.println("English: 150");
            return 150;
        } else {
            int score = (int) ((englishKnowledge / fullKnowledge) * 150);
            System.out.println("English: " + score);
            return score;
        }
    }

    //EFFECTS: Calculate and print out the basic score for selection 1 selection 2 selection 3,
    // which is (corresponding knowledge / (fullKnowledge * 3)) * 300 when the selectionOneKnowledge is less than
    // fullKnowledge * 3. It will be 300 if the knowledge is greater than or equals to fullKnowledge.
    // Return the calculated score.
    public int takeExamForCombinedScienceOrArt(Boolean artOrScience) {
        int totKnowledge = (selectionOneKnowledge + selectionThreeKnowledge + selectionTwoKnowledge);
        if (totKnowledge >= fullKnowledge * 3) {
            if (artOrScience) {
                System.out.println("Combined Science: 150");
            } else {
                System.out.println("Combined Arts: 150");
            }
            return 300;
        } else {
            int score = (int) ((totKnowledge / (3 * fullKnowledge)) * 300);
            if (artOrScience) {
                System.out.println("Combined Science: " + score);
            } else {
                System.out.println("Combined Arts: " + score);
            }
            return score;
        }
    }

    //getters

    //EFFECT: get the value of selectionOneKnowledge and return.
    public int getS1Knowledge() {
        return selectionOneKnowledge;
    }

    //EFFECT: get the value of selectionOneKnowledge and return.
    public int getS2Knowledge() {
        return selectionTwoKnowledge;
    }

    //EFFECT: get the value of selectionOneKnowledge and return.
    public int getS3Knowledge() {
        return selectionThreeKnowledge;
    }

    //EFFECT: get the value of Mandarin and return.
    public int getMandarinKnowledge() {
        return mandarinKnowledge;
    }

    //EFFECT: get the value of Math and return.
    public int getMathKnowledge() {
        return mathKnowledge;
    }

    //EFFECT: get the value of English and return.
    public int getEnglishKnowledge() {
        return englishKnowledge;
    }

    //setters

    //EFFECT: set selectionOneKnowledge
    public void setS1Knowledge(int i) {
        selectionOneKnowledge = i;
    }

    //EFFECT: set the value of selectionOneKnowledge and return.
    public void setS2Knowledge(int i) {
        selectionTwoKnowledge = i;
    }

    //EFFECT: set the value of selectionOneKnowledge and return.
    public void setS3Knowledge(int i) {
        selectionThreeKnowledge = i;
    }

    //EFFECT: set the value of Mandarin and return.
    public void setMandarinKnowledge(int i) {
        mandarinKnowledge = i;
    }

    //EFFECT: set the value of Math and return.
    public void setMathKnowledge(int i) {
        mathKnowledge = i;
    }

    //EFFECT: set the value of English and return.
    public void setEnglishKnowledge(int i) {
        englishKnowledge = i;
    }
}


