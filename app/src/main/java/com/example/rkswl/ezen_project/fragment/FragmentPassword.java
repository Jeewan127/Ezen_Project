package com.example.rkswl.ezen_project.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rkswl.ezen_project.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentPassword extends Fragment {

    //버튼 이미지 들
    @BindView(R.id.pw_input_img1)
    ImageView pw_input1;
    @BindView(R.id.pw_input_img2) ImageView pw_input2;
    @BindView(R.id.pw_input_img3) ImageView pw_input3;
    @BindView(R.id.pw_input_img4) ImageView pw_input4;

    @BindView(R.id.pw_txt_setting)
    TextView lock_text; //위에 있는 이름

    @BindView(R.id.pw_next) TextView pw_next; //하단 다음 버튼

    Boolean pass = false;
    String password = "";
    String password2 = "";
    String id;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_password,container,false);
        ButterKnife.bind(this,view);

        return view;
    }



    @OnClick(R.id.pw_ib_clear)
    public void can() {
        clear();
    }


    @OnClick(R.id.pw_back)
    public void back(){
        ((FragmentJoinViewPager)getActivity()).two_move();
    }

    //숫자들을 눌럿을경우
    @OnClick({R.id.pw_ib_one, R.id.pw_ib_two, R.id.pw_ib_three, R.id.pw_ib_four, R.id.pw_ib_five, R.id.pw_ib_six, R.id.pw_ib_seven, R.id.pw_ib_eight, R.id.pw_ib_nine, R.id.pw_ib_zero})
    public void num(View view) {
        Button btn = (Button) view;
        String a = btn.getText().toString();
        number_click(a);
    }

    //초기화 함수
    public void clear() {
        password = "";
        pw_input1.setBackground(getResources().getDrawable(R.drawable.password_check1));
        pw_input2.setBackground(getResources().getDrawable(R.drawable.password_check1));
        pw_input3.setBackground(getResources().getDrawable(R.drawable.password_check1));
        pw_input4.setBackground(getResources().getDrawable(R.drawable.password_check1));
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
                    //join_gender_m.setBackground(getResources().getDrawable(R.drawable.signup_man1));
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
                    break;
            }
        } else {
            //4개이상일시에는 동작을 하지않습니다.
        }
    }
    @OnClick(R.id.pw_ib_back)
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
