package io.iqube.kctgrad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import io.iqube.kctgrad.model.Option;
import io.iqube.kctgrad.model.Question;

/**
 * Created by raja sudhan on 5/1/2016.
 */
public class QuestionArrayAdapter extends BaseAdapter {

    ArrayList<Question> questions;

    Context mContext;
    LayoutInflater inflater;


    public QuestionArrayAdapter(Context context, ArrayList<Question> questionList)
    {

//        super(context,R.layout.lv_question_single,questionList);

        this.questions=questionList;
        mContext=context;
        inflater=LayoutInflater.from(mContext);
    }

    public void add(Question question)
    {
        questions.add(question);
        this.notifyDataSetChanged();
    }

    public Context getContext() {
        return mContext;
    }


    public JsonArray getPostData()
    {
        JsonArray jsonArray=new JsonArray();

        for(Question question :questions) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("question",question.getId());
            jsonObject.addProperty("option",question.getChoice_id());
            jsonArray.add(jsonObject);
        }

        return jsonArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Question question=getItem(position);
        RadioGroup rg;
                if(convertView==null)
                {
                    convertView= inflater.inflate(R.layout.lv_question_single,parent,false);


                    rg=(RadioGroup) convertView.findViewById(R.id.options_radio);



                    for (Option option : question.getOptions()
                            ) {

                        RadioButton rb=new RadioButton(getContext());
                        rb.setText(option.getChoiceText());
                        rb.setId(option.getId());
                        rg.addView(rb);

                    }
                }

        TextView QuestionText=(TextView)convertView.findViewById(R.id.question_text);

        rg=(RadioGroup) convertView.findViewById(R.id.options_radio);


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                question.setChoice_id(checkedId);
            }
        });

        QuestionText.setText(question.getQuestionText());

        return convertView;
    }


    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Question getItem(int position) {
        return questions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return questions.get(position).getId();
    }

}
