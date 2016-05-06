package io.iqube.kctgrad.model;

/**
 * Created by raja sudhan on 5/6/2016.
 */
public class program {

    public program(String n,String a1,String a2,String a3)
    {
        name=n;
        s1=a1;
        s2=a2;
        s3=a3;

    }

    String name;
    String s1,s2,s3;


    public String getName() {
        return name;

    }


    public String getS1() {
        return s1;
    }


    public String getS2() {
        return s2;
    }

    public String getS3() {
        return s3;
    }

}
