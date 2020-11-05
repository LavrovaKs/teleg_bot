package ru.urfu;

public class Pair {

    private static String answer;
    private static String exercise;

    Pair(String a, String ex){
        answer = a;
        exercise = ex;
    }

    public String getAnswer(){ return answer;}
    public String getExercise(){ return exercise; }
}
