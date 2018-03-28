package com.lwan.rkswl.ezen_project.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.lwan.rkswl.ezen_project.DbManger;
import com.lwan.rkswl.ezen_project.R;
import com.lwan.rkswl.ezen_project.fragment.FragmentFindViewpager;
import com.lwan.rkswl.ezen_project.fragment.FragmentJoinViewPager;
import com.lwan.rkswl.ezen_project.model.user;
import com.lwan.rkswl.ezen_project.retrofit.RetrofitService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_id) EditText login_id;
    @BindView(R.id.login_pw) EditText login_pw;
    DbManger dbManger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        dbManger =  new DbManger(LoginActivity.this,"addwin_user.db",null,1);
        Log.d("ksj","아이디를 가져오는가 : " + dbManger.get_id());
        if(dbManger.get_id() == null){
            Log.d("ksj","없음");
        }else{
            Log.d("ksj","dddd "  + dbManger.get_id());
            Log.d("ksj","dddd "  + dbManger.get_user_id());
            Intent intent = new Intent(this, LoginPwActivity.class);
            startActivity(intent);

        }



    }
    @OnClick(R.id.login_rl_findpw)
    public void pw_serch(){
        Log.d("ksj","잉맞는데?");
        Intent intent = new Intent(this, FragmentFindViewpager.class);
        startActivity(intent);

    }

    @OnClick(R.id.login_rl_join)
    public void join_move(){
        Log.d("ksj","응?");
        Intent intent = new Intent(this, FragmentJoinViewPager.class);
        startActivity(intent);
    }


    @OnClick(R.id.login_rl_check)
    public void login(){
        String id = login_id.getText().toString();
        String pass = login_pw.getText().toString();
        if(!id.equals("")){
            if(!pass.equals("")){ //빈칸제어땜에쓴것
                Log.d("ksj","들어옴");
                Call<ArrayList<user>> item = RetrofitService.getInstance().getRetrofitRequest().getuserList(id,pass);
                item.enqueue(new Callback<ArrayList<user>>() {
                    @Override
                    public void onResponse(Call<ArrayList<user>> call, Response<ArrayList<user>> response) {
                        if(response.isSuccessful()){
                            ArrayList<user> user = response.body();
                            if(user.get(0).getID().equals("-1")){
                                Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                                login_id.setText("");
                                login_pw.setText("");
                            }else{
                                //성공시
                                Log.d("ksj","여긴들어오냐흠 왓는데에;;");
                                Log.d("ksj","흠냐 :" +  user.get(0).getID());
                                dbManger.delete();
                                Log.d("ksj","1");
                                dbManger.add(user.get(0).getUSER_ID() , user.get(0).getID(),user.get(0).getLOCK_PASSWORD());
                                Log.d("ksj","2");
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                Log.d("ksj","user :" + user.get(0).getID());
                                intent.putExtra("id" , user.get(0).getID());
                                startActivity(intent);
                                finish();
                                Toast.makeText(LoginActivity.this, "로그인성공!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<user>> call, Throwable t) {

                    }
                });

            }else{
                Toast.makeText(this, "비밀번호를 입력해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "아이디를 입력해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
        }

    }


}
