package io.iqube.kctgrad.model;

import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.Expose;


public class Option {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("choice_text")
    @Expose
    private String choiceText;

    /**
     * 
     * @return
     *     The id
     */
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
     *     The choiceText
     */
    public String getChoiceText() {
        return choiceText;
    }

    /**
     * 
     * @param choiceText
     *     The choice_text
     */
    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

}
