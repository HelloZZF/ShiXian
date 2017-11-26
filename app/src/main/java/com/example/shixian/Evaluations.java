package com.example.shixian;


/**
 * Created by admin on 2017/11/15.
 */

public class Evaluations {

    private int evaluationIconId;
    private String evaluationName;
    private String evaluationContent;
    private String evaluationTime;

    public Evaluations(int evaluationIconId, String evaluationName, String evaluationContent, String evaluationTime) {
        this.evaluationIconId = evaluationIconId;
        this.evaluationName = evaluationName;
        this.evaluationContent = evaluationContent;
        this.evaluationTime = evaluationTime;
    }

    public int getEvaluationIconId() {
        return evaluationIconId;
    }

    public String getEvaluationName() {
        return evaluationName;
    }

    public String getEvaluationContent() {
        return evaluationContent;
    }

    public String getEvaluationTime() {
        return evaluationTime;
    }
}
