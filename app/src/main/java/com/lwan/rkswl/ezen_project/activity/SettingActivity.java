package com.lwan.rkswl.ezen_project.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lwan.rkswl.ezen_project.DbManger;
import com.lwan.rkswl.ezen_project.R;

import com.lwan.rkswl.ezen_project.information_passwordActivity;
import com.lwan.rkswl.ezen_project.model.information;
import com.lwan.rkswl.ezen_project.retrofit.RetrofitService;
import com.lwan.rkswl.ezen_project.system_address;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {



    @BindView(R.id.setting_circle_man) CircleImageView user1;
    @BindView(R.id.setting_txt_name) TextView user1_name;

    @BindView(R.id.setting_circle_woman) CircleImageView user2;
    @BindView(R.id.setting_txt_name2) TextView user2_name;

    @BindView(R.id.setting_txt_id) TextView user_id;

    DbManger dbManger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        dbManger = new DbManger(this,"addwin_user.db" ,null,1);
        Log.d("ksj",dbManger.get_user_id());
        user_id.setText(dbManger.get_user_id());

        final Call<ArrayList<information>> item = RetrofitService.getInstance().getRetrofitRequest().getuser_information(dbManger.get_id());
        item.enqueue(new Callback<ArrayList<information>>() {
            @Override
            public void onResponse(Call<ArrayList<information>> call, Response<ArrayList<information>> response) {
                if(response.isSuccessful()){
                    ArrayList<information> items = response.body();
                    Log.d("ksj1","사이즈 : " + items.size());
                    for(int a=  0 ; a < items.size() ; a++){
                        Log.d("ksj" , "뭐지 : " + items.get(a).getId());
                        Log.d("ksj","아이?" + items.get(a).getPath());
                        if(a == 0 ){
                            ///resources/4
                            if(!items.get(a).getPath().equals(null)){
                            }else{
                                user1.setImageBitmap(new ImageRoader().getBitmapImg( "resources/"+items.get(a).getId() + "/" +   items.get(a).getPath()));
                            }

                            user1_name.setText(items.get(a).getName());
                        }else{
                            if(!items.get(a).getPath().equals(null)){
                            }else{
                                user2.setImageBitmap(new ImageRoader().getBitmapImg( "resources/"+items.get(a).getId() + "/" +   items.get(a).getPath()));
                            }
                            user2_name.setText(items.get(a).getName());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<information>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @OnClick(R.id.setting_back)
    public void back(){
        finish();
    }

    @OnClick(R.id.setting_btn_information)
    public void information(){
        Intent intent = new Intent(SettingActivity.this,InformationActivity.class);
        this.startActivity(intent);
    }
    @OnClick(R.id.setting_btn_information1)
    public void information1(){
        Intent intent = new Intent(SettingActivity.this,InformationActivity.class);
        this.startActivity(intent);
    }

    @OnClick({R.id.setting_btn_password,R.id.setting_btn_password1})
    public void password(){
        Intent intent = new Intent(SettingActivity.this, information_passwordActivity.class);
        this.startActivity(intent);
    }


    public class ImageRoader {

        private final String serverUrl = system_address.address;

        public ImageRoader() {

            new ThreadPolicy();
        }

        public Bitmap getBitmapImg(String imgStr) {
            Log.d("ksj1","들어와져있는 imgStr" + imgStr);
            Bitmap bitmapImg = null;

            try {
                //URL url = new URL(serverUrl + URLEncoder.encode(imgStr, "utf-8"));
                URL url = new URL(serverUrl + imgStr);
                // Character is converted to 'UTF-8' to prevent broken
                Log.d("ksj1",""+url);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();
                bitmapImg = BitmapFactory.decodeStream(is);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return bitmapImg;
        }
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public class ThreadPolicy {

        // For smooth networking
        public ThreadPolicy() {

            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }
    }
}
