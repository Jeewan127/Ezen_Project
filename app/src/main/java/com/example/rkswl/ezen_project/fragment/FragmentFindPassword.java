package com.example.rkswl.ezen_project.fragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

import com.example.rkswl.ezen_project.R;
import com.example.rkswl.ezen_project.retrofit.RetrofitService;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentFindPassword extends Fragment {

    @BindView(R.id.find_date_year) Spinner find_year;
    @BindView(R.id.find_date_month) Spinner find_month;
    @BindView(R.id.find_date_day) Spinner find_day;

    @BindView(R.id.find_id) EditText id;
    @BindView(R.id.find_phone) EditText phone;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_find_password,container,false);
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
        find_year.setAdapter(spinnerAdapter);
        ArrayAdapter spinnerAdapter2 = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,month);
        find_month.setAdapter(spinnerAdapter2);
        final Context context = getContext();
        int day = date_count(Integer.parseInt(find_year.getSelectedItem().toString()),1);
        String[] days = new String[day];
        for(int a = 0 ; a < days.length ; a++){
            days[a] = ""+(a+1);
        }
        ArrayAdapter spinnerAdapter3 = new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,days);
        find_day.setAdapter(spinnerAdapter3);


        find_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ksj",find_year.getSelectedItem().toString());
                int day = date_count(Integer.parseInt(find_year.getSelectedItem().toString()),position);
                Log.d("ksj","음냠");
                String[] days = new String[day];
                for(int a = 0 ; a < days.length ; a++){
                    days[a] = ""+(a+1);
                }
                ArrayAdapter spinnerAdapter3 = new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,days);
                find_day.setAdapter(spinnerAdapter3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return  view;
    }

    public int date_count(int year , int month){

        Calendar cal = Calendar.getInstance();
        cal.set(year,month,1);

        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }



    @OnClick(R.id.find_back)
    public void back(){
        ((FragmentFindViewpager)getActivity()).finish();
    }

    @OnClick(R.id.cp_rl_next)
    public void move(){
        String date = find_year.getSelectedItem().toString() + "-" + find_month.getSelectedItem().toString() + "-" + find_day.getSelectedItem().toString();
        //비밀번호찾기에서 다음 버튼을 눌럿을시
        if(id.getText().toString().equals("")){
            Toast.makeText(getContext(), "아이디를 입력해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
        }else{
            if(phone.getText().toString().equals("")){
                Toast.makeText(getContext(), "비밀번호를 입력해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }else{

                Call<String> item = RetrofitService.getInstance().getRetrofitRequest().password(id.getText().toString(),phone.getText().toString(),date);
                item.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            String serch = response.body();
                            if(serch.equals("0")){
                                Toast.makeText(getContext(), "해당아이디에대한 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(), "성공", Toast.LENGTH_SHORT).show();
                                ((FragmentFindViewpager)getActivity()).server_id = id.getText().toString();
                                ((FragmentFindViewpager)getActivity()).two_move();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        }

    }

}