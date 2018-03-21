package com.example.rkswl.ezen_project.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rkswl.ezen_project.R;
import com.example.rkswl.ezen_project.retrofit.RetrofitService;

import java.net.PasswordAuthentication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSettingPassword extends Fragment {
    @BindView(R.id.pwst_password1) EditText password1;
    @BindView(R.id.pwst_password2) EditText password2;
    @BindView(R.id.pwst_txt_password) TextView pass_hint;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_setting_password,container,false);
        ButterKnife.bind(this,view);

        return  view;
    }

    @OnClick(R.id.pwst_back)
    public void back(){
        //뒤로가기 버튼을 눌럿을시
        ((FragmentFindViewpager)getActivity()).one_move();
    }

    @OnTextChanged(R.id.pwst_password1)
    public void password_hint(){
        if(password1.getText().toString().equals("")){
            pass_hint.setVisibility(View.VISIBLE);
        }else{
            pass_hint.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.cp_rl_next)
    public void next_ok(){
        if(password1.getText().toString().equals("")){
            Toast.makeText(getContext(), "비밀번호를 입력해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
        }else{
            if(password2.getText().toString().equals("")){
                Toast.makeText(getContext(), "비밀번호를 입력해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }else{
                if(password1.getText().toString().equals(password2.getText().toString())){
                    //모든조건을 성립햇을시
                    Call<String> item  = RetrofitService.getInstance().getRetrofitRequest().up_pass(((FragmentFindViewpager)getActivity()).server_id,password2.getText().toString());

                    item.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.isSuccessful()){
                                String update_ok = response.body();
                                //1이리턴이될경우
                                if(!update_ok.equals("0")){
                                    Toast.makeText(getContext(), "정상적으로 비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                    ((FragmentFindViewpager)getActivity()).finish();
                                }else{
                                    Toast.makeText(getContext(), "서버문제로인해 비밀번호변경이 원활하지 않습니다. 잠시후 다시 실행해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });


                }
            }
        }
        //비밀번호 수정!!! 내일 아침에해야징

    }



}
