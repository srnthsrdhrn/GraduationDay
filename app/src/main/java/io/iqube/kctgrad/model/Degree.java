package io.iqube.kctgrad.model;

/**
 * Created by raja sudhan on 5/2/2016.
 */
public class Degree {

    private Integer id;
    private String name;


    public void setId(Integer id)
    {
        this.id=id;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public String getName(){return this.name;}

    public Integer getId(){return this.id;}

}
