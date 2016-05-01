package io.iqube.kctgrad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import io.iqube.kctgrad.model.Option;
import io.iqube.kctgrad.model.Question;

/**
 * Created by raja sudhan on 5/1/2016.
 */
public class QuestionArrayAdapter extends ArrayAdapter<Question> {

    public QuestionArrayAdapter(Context context, List<Question> questionList)
    {

        super(context,R.layout.lv_question_single,questionList);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Question question=getItem(position);

                if(convertView==null)
                {
                    convertView= LayoutInflater.from(getContext()).inflate(R.layout.lv_question_single,parent,false);
                }

        TextView QuestionText=(TextView)convertView.findViewById(R.id.question_text);

        ViewGroup rg=(ViewGroup) convertView.findViewById(R.id.options_radio);


        for (Option option : question.getOptions()
                ) {

            RadioButton rb=new RadioButton(getContext());
            rb.setText(option.getChoiceText());
            rb.setId(option.getId());
            rg.addView(rb);

        }

        QuestionText.setText(question.getQuestionText());

        return convertView;
    }
}
