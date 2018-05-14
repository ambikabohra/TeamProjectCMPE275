package com.surveyapp.web.domain.frontend;


public class Question {


    private String quesType;

    private String quesText;

    //multiple choice- checkbox, radio, dropdown
    private String option1Text;
    private String option2Text;
    private String option3Text;
    private String option4Text;



    public String getQuesText() {
        return quesText;
    }

    public void setQuesText(String quesText) {
        this.quesText = quesText;
    }

    public String getOption1Text() {
        return option1Text;
    }

    public void setOption1Text(String option1Text) {
        this.option1Text = option1Text;
    }

    public String getOption2Text() {
        return option2Text;
    }

    public void setOption2Text(String option2Text) {
        this.option2Text = option2Text;
    }

    public String getOption3Text() {
        return option3Text;
    }

    public void setOption3Text(String option3Text) {
        this.option3Text = option3Text;
    }

    public String getOption4Text() {
        return option4Text;
    }

    public void setOption4Text(String option4Text) {
        this.option4Text = option4Text;
    }

    public String getQuesType() {
        return quesType;
    }

    public void setQuesType(String quesType) {
        this.quesType = quesType;
    }

}
