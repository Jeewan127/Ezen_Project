package com.lwan.rkswl.ezen_project.fragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.lwan.rkswl.ezen_project.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentCoupleInfo extends Fragment {

    @BindView(R.id.cp_date_year) Spinner cp_year;
    @BindView(R.id.cp_date_month) Spinner cp_month;
    @BindView(R.id.cp_date_day) Spinner cp_day;

    @BindView(R.id.cp_phone)    EditText cp_phone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_couple_info,container,false);
        ButterKnife.bind(this,view);

        String[] year = new String[30];
        String[] month = new String[12];

        for(int a = 0 ; a < 30 ;a++) {
            int b = 1990 + a;
            year[a] = ""+b;
        }
        for(int a = 1 ; a < 13 ; a++){
            month[a-1] = ""+a;
        }

        ArrayAdapter spinnerAdapter = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,year);
        cp_year.setAdapter(spinnerAdapter);
        ArrayAdapter spinnerAdapter2 = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,month);
        cp_month.setAdapter(spinnerAdapter2);
        final Context context = getContext();
        int day = date_count(Integer.parseInt(cp_year.getSelectedItem().toString()),1);
        String[] days = new String[day];
        for(int a = 0 ; a < days.length ; a++){
            days[a] = ""+(a+1);
        }
        ArrayAdapter spinnerAdapter3 = new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,days);
        cp_day.setAdapter(spinnerAdapter3);


        cp_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ksj",cp_year.getSelectedItem().toString());
                int day = date_count(Integer.parseInt(cp_year.getSelectedItem().toString()),position);
                Log.d("ksj","음냠");
                String[] days = new String[day];
                for(int a = 0 ; a < days.length ; a++){
                    days[a] = ""+(a+1);
                }
                ArrayAdapter spinnerAdapter3 = new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,days);
                cp_day.setAdapter(spinnerAdapter3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
    public int date_count(int year , int month){

        Calendar cal = Calendar.getInstance();
        cal.set(year,month,1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }



    @OnClick(R.id.cp_rl_next)
    public void move(){
        if(cp_phone.getText().toString().equals("")){
            Toast.makeText(getContext(), "상대방 핸드폰 번호를 입력해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
        }else{
            //성공시 다음화면으로 이동
            ((FragmentJoinViewPager)getActivity()).server_op_number = cp_phone.getText().toString();
            ((FragmentJoinViewPager)getActivity()).server_First_day = cp_year.getSelectedItem().toString() + "-" + cp_month.getSelectedItem().toString() + "-" + cp_day.getSelectedItem().toString();
            ((FragmentJoinViewPager)getActivity()).three_move();
        }


    }

    @OnClick(R.id.cp_back)
    public void back(){
        ((FragmentJoinViewPager)getActivity()).one_move();
    }
}
