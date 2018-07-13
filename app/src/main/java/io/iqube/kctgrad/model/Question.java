
package io.iqube.kctgrad.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Question {

    private Integer choice_id;

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("question")
    @Expose
    private String questionText;
    @SerializedName("choice")
    @Expose
    private List<Option> options = new ArrayList<Option>();

    public Integer getChoice_id(){return choice_id;}

    public void setChoice_id(Integer choice_id){this.choice_id=choice_id;}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

}
