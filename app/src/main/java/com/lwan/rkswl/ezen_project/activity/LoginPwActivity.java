package com.lwan.rkswl.ezen_project.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.lwan.rkswl.ezen_project.DbManger;
import com.lwan.rkswl.ezen_project.R;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginPwActivity extends AppCompatActivity {
    @BindView(R.id.main_pw_img1) ImageView pw_input1;
    @BindView(R.id.main_pw_img2) ImageView pw_input2;
    @BindView(R.id.main_pw_img3) ImageView pw_input3;
    @BindView(R.id.main_pw_img4) ImageView pw_input4;


    String password = "";

    DbManger dbManger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pw);
        ButterKnife.bind(this);
        dbManger = new DbManger(this, "addwin_user.db", null, 1);

    }


    @OnClick({R.id.main_id_one, R.id.main_id_two, R.id.main_id_three, R.id.main_id_four, R.id.main_id_five, R.id.main_id_six, R.id.main_id_seven, R.id.main_id_eight, R.id.main_id_nine, R.id.main_id_zero})
    public void num(View view) {
        Log.d("ksj","음");
        Button btn = (Button) view;
        String a = btn.getText().toString();
        number_click(a);
    }


    @OnClick(R.id.main_id_clear)
    public void can() {
        clear();
    }

    //숫자들을 클릭햇을시의 함수
    public void number_click(String number) {
        Log.d("ksj","현재 패스워드 값 : " +password);
        Log.d("ksj","들어온 숫자 : " + number);
        Log.d("ksj","패스워드 글자숫 : " + password.length());
        if (password.length() != 4) {
            password = password + number;
            int len = password.length();
            Log.d("ksj","여긴들어옴");
            switch (len) {
                case 1:
                    pw_input1.setBackground(getResources().getDrawable(R.drawable.password_check2));
                    break;
                case 2:
                    pw_input2.setBackground(getResources().getDrawable(R.drawable.password_check2));
                    break;
                case 3:
                    pw_input3.setBackground(getResources().getDrawable(R.drawable.password_check2));
                    break;
                case 4:
                    pw_input4.setBackground(getResources().getDrawable(R.drawable.password_check2));
                    if(password.equals(dbManger.get_lock_password())){
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("id" , dbManger.get_id());
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(this, "비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                        clear();
                    }
                    break;
            }
        } else {
            //4개이상일시에는 동작을 하지않습니다.
        }
    }



    public void clear() {
        password = "";
        pw_input1.setBackground(getResources().getDrawable(R.drawable.password_check1));
        pw_input2.setBackground(getResources().getDrawable(R.drawable.password_check1));
        pw_input3.setBackground(getResources().getDrawable(R.drawable.password_check1));
        pw_input4.setBackground(getResources().getDrawable(R.drawable.password_check1));
    }

    @OnClick(R.id.main_id_back)
    public void back_space() {
        int b = password.length();
        switch (b) {
            case 1:
                pw_input1.setBackground(getResources().getDrawable(R.drawable.password_check1));
                password = "";
                break;
            case 2:
                pw_input2.setBackground(getResources().getDrawable(R.drawable.password_check1));
                password = password.substring(0, 1);
                break;
            case 3:
                pw_input3.setBackground(getResources().getDrawable(R.drawable.password_check1));
                password = password.substring(0, 2);
                break;
            case 4:
                pw_input4.setBackground(getResources().getDrawable(R.drawable.password_check1));
                password = password.substring(0, 3);
                break;
        }

    }





}
