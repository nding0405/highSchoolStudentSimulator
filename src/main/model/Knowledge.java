package model;

public class Knowledge {
    private static double courseFitpressureIndex = 0.9;
    private static double courseUnFitpressureIndex = 1.3;
    private static double courseFitKnowledgeIndex = 1.08;
    private static double courseUnFitKnowledgeIndex = 0.8;
    private static double playFitPressureIndex = 1.0;
    private static double playUnFitPressureIndex = 0.4;
    private static double loseKnowledgeWhenPlayIndex = 0.15;

    private int selectionOneKnowledge;
    private int selectionTwoKnowledge;
    private int selectionThreeKnowledge;

    public Knowledge() {
        selectionOneKnowledge = 0;
        selectionTwoKnowledge = 0;
        selectionThreeKnowledge = 0;
    }


    //MODIFIED: this
    //EFFECT: Adding knowledge according to the activity. If the activity is of type of "playing", then reduce knowledge
    // for all three sub-knowledge. If the activity type is of type of "course", and the course is one of the three
    // selection, add knowledge to the corresponding selection.(the index is determined based on whether the course type
    // fits the student preference. If the course type is none of the three selection, do nothing.)
    public void addKnowledge(Activities a, String s1, String s2, String s3, Boolean prefer, Boolean chr) {
        int actTime = a.getTime();
        Boolean courseOrPlay = a.getcourseOrPlay();//true课程false娱乐
        Boolean courseType = a.getActivityType();//true理科false文科
        if (!courseOrPlay) {
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

    //REQUIRES: index has to be one of 1 2 3.
    //MODIFIED: this
    //EFFECT: Adding knowledge to the corresponding selection bases on fitOrNot. (ex. if fitOrNot is true then use
    // "courseFitKnowledgeIndex" as the coefficient, otehr wise use "courseUnFitKnowledgeIndex". If the index is 1,
    // modifies selectionOneKnowledge, selectionTwoKnowledge if 2, selectionThreeKnowledge if 3.
    public void addKnowledgeHelper(int index, int actTime, Boolean fitOrNot) {
        if (fitOrNot) {
            if (index == 1) {
                selectionOneKnowledge += (int) (courseFitKnowledgeIndex * actTime);
            } else if (index == 2) {
                selectionTwoKnowledge += (int) (courseFitKnowledgeIndex * actTime);
            } else {
                selectionThreeKnowledge += (int) (courseFitKnowledgeIndex * actTime);
            }
        } else {
            if (index == 1) {
                selectionOneKnowledge += (int) (courseUnFitKnowledgeIndex * actTime);
            } else if (index == 2) {
                selectionTwoKnowledge += (int) (courseUnFitKnowledgeIndex * actTime);
            } else {
                selectionThreeKnowledge += (int) (courseUnFitKnowledgeIndex * actTime);
            }
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
}


