
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
    @SerializedName("question_text")
    @Expose
    private String questionText;
    @SerializedName("options")
    @Expose
    private List<Option> options = new ArrayList<Option>();

    /**
     * 
     * @return
     *     The id
     */

    public Integer getChoice_id(){return choice_id;}

    public void setChoice_id(Integer choice_id){this.choice_id=choice_id;}

    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The questionText
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * 
     * @param questionText
     *     The question_text
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     * 
     * @return
     *     The options
     */
    public List<Option> getOptions() {
        return options;
    }

    /**
     * 
     * @param options
     *     The options
     */
    public void setOptions(List<Option> options) {
        this.options = options;
    }

}
