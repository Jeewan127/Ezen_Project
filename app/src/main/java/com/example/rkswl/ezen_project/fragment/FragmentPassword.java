package com.example.rkswl.ezen_project.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rkswl.ezen_project.R;
import com.example.rkswl.ezen_project.retrofit.RetrofitService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @OnClick(R.id.pw_rl_next)
    public void next(){
        final Context context = getContext();
        if(pass == false){
            //첫번째 패스워드일 경우
            if (password.length() == 4) {
                Toast.makeText(getContext(), "전체 패스워드 : " + password, Toast.LENGTH_SHORT).show();
                password2 = password;
                clear();
                pass = true;
                lock_text.setText("비밀번호 확인");
                pw_next.setText("확인");
            } else {
                Toast.makeText(getContext(), "패스워드를 전부 적어주시기 바랍니다.  " + password, Toast.LENGTH_SHORT).show();
            }
        }else{
            //두번째일경우
            if(password.equals(password2)){
                //앞에 password와 값이 같을경우
                //db에 연결후 입력!
                ((FragmentJoinViewPager)getActivity()).server_Lock_password = password;
                Log.d("ksj","총결산!");
                Log.d("ksj","아이디 : " + ((FragmentJoinViewPager)getActivity()).server_id );
                Log.d("ksj","비밀번호 : " + ((FragmentJoinViewPager)getActivity()).server_pass );
                Log.d("ksj","이름 : " + ((FragmentJoinViewPager)getActivity()).server_name );
                Log.d("ksj","생일 : " + ((FragmentJoinViewPager)getActivity()).server_date );
                Log.d("ksj","성별 : " + ((FragmentJoinViewPager)getActivity()).server_gender );
                Log.d("ksj","내번호 : " + ((FragmentJoinViewPager)getActivity()).server_number );
                Log.d("ksj","프로필 : " + ((FragmentJoinViewPager)getActivity()).image_file );
                Log.d("ksj","상대방번호 : " + ((FragmentJoinViewPager)getActivity()).server_op_number );
                Log.d("ksj","처음만난날 : " + ((FragmentJoinViewPager)getActivity()).server_First_day );
                Log.d("ksj","지금꺼_pass : " + ((FragmentJoinViewPager)getActivity()).server_Lock_password );
                //클리어!!!!!!!!!!!!

                final RequestBody server_id =
                        RequestBody.create(MediaType.parse("text/plain"),
                                ((FragmentJoinViewPager)getActivity()).server_id);
                final RequestBody server_pass =
                        RequestBody.create(MediaType.parse("text/plain"),
                                ((FragmentJoinViewPager)getActivity()).server_pass);
                final RequestBody server_name =
                        RequestBody.create(MediaType.parse("text/plain"),
                                ((FragmentJoinViewPager)getActivity()).server_name);
                final RequestBody server_date =
                        RequestBody.create(MediaType.parse("text/plain"),
                                ((FragmentJoinViewPager)getActivity()).server_date);
                final RequestBody server_gender =
                        RequestBody.create(MediaType.parse("text/plain"),
                                ((FragmentJoinViewPager)getActivity()).server_gender);
                final RequestBody server_phone =
                        RequestBody.create(MediaType.parse("text/plain"),
                                ((FragmentJoinViewPager)getActivity()).server_number);
                final RequestBody server_op_phone =
                        RequestBody.create(MediaType.parse("text/plain"),
                                ((FragmentJoinViewPager)getActivity()).server_op_number);
                final RequestBody server_FIRST_DAY =
                        RequestBody.create(MediaType.parse("text/plain"),
                                ((FragmentJoinViewPager)getActivity()).server_First_day);
                final RequestBody server_lock_pass =
                        RequestBody.create(MediaType.parse("text/plain"),
                                password);

                Call<String> item = RetrofitService.getInstance().getRetrofitRequest().insertuser(server_id,server_pass,server_name,server_date,server_gender,server_phone,((FragmentJoinViewPager)getActivity()).image_file,server_op_phone,server_lock_pass,server_FIRST_DAY);
                item.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            String insert_ok = response.body();
                            if(!insert_ok.equals("0")){
                                Toast.makeText(getContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                                ((FragmentJoinViewPager)getActivity()).finish();
                            }else{
                                Toast.makeText(getContext(), "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });





            }else{
                //패스워드가 틀릴경우
                Toast.makeText(getContext(), "패스워드를 다시 확인해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }

        }
    }





    @OnClick(R.id.pw_back)
    public void back(){
        if(pass == false){
            //첫번쨰 패스워드일 경우
            ((FragmentJoinViewPager)getActivity()).two_move();
        }else{
            //두번째 패스워드일경우 초기화하기!
            clear();
            lock_text.setText("비밀번호 설정");
            pw_next.setText("다음");
            pass = false;
        }

    }
    @OnClick(R.id.pw_ib_clear)
    public void can() {
        clear();
    }

    //숫자들을 눌럿을경우
    @OnClick({R.id.pw_ib_one, R.id.pw_ib_two, R.id.pw_ib_three, R.id.pw_ib_four, R.id.pw_ib_five, R.id.pw_ib_six, R.id.pw_ib_seven, R.id.pw_ib_eight, R.id.pw_ib_nine, R.id.pw_ib_zero})
    public void num(View view) {
        Log.d("ksj","음");
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
