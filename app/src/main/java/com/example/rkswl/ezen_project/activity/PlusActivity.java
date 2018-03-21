package com.example.rkswl.ezen_project.activity;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rkswl.ezen_project.DbManger;
import com.example.rkswl.ezen_project.R;
import com.example.rkswl.ezen_project.ViewHolder.PlusImageViewHolder;
import com.example.rkswl.ezen_project.adapter.PlusImageAdapter;
import com.example.rkswl.ezen_project.fragment.FragmentJoinViewPager;
import com.example.rkswl.ezen_project.plusImageFIle;
import com.example.rkswl.ezen_project.retrofit.RetrofitService;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlusActivity extends AppCompatActivity {

    @BindView(R.id.plus_date_year)    Spinner plus_year;
    @BindView(R.id.plus_date_month) Spinner plus_month;
    @BindView(R.id.plus_date_day) Spinner plus_day;
    @BindView(R.id.plus_etx_title)  EditText plus_ti;

    public String insert_ok;
    private Button plus_btn_picture;
    RecyclerView list;
    PlusImageAdapter plusImageAdapter;
    ArrayList<plusImageFIle> item = new ArrayList<>();

    String id;


    DbManger dbManger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);
        ButterKnife.bind(this);
        dbManger = new DbManger(this,"addwin_user.db",null,1);
        id = dbManger.get_id();
        Log.d("ksj","db에서 가져온 id 값" + dbManger.get_id());
        plus_btn_picture = (Button) findViewById(R.id.plus_btn_picture);
        list = findViewById(R.id.plus_recyclerview);

        plus_btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionListener permissionListener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                    }
                };
                TedPermission.with(PlusActivity.this)
                        .setPermissionListener(permissionListener)
                        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .check();
            }
        });





        plusImageAdapter = new PlusImageAdapter(new PlusImageAdapter.Listener() {
            @Override
            public void onImageClicked(View view) {
                transition(view);  // 클릭시 코드
            }
        });


        String[] year = new String[30];
        String[] month = new String[12];

        for(int a = 0 ; a < 30 ;a++) {
            int b = 1990 + a;
            year[a] = ""+b;
        }
        for(int a = 1 ; a < 13 ; a++){
            month[a-1] = ""+a;
        }

        Log.d("ksj","year 값 : " + year.length);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(PlusActivity.this,R.layout.support_simple_spinner_dropdown_item,year);
        plus_year.setAdapter(spinnerAdapter);


        final Context context = this;

        ArrayAdapter spinnerAdapter2 = new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,month);
        plus_month.setAdapter(spinnerAdapter2);

        plus_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int day = date_count(position+1990,Integer.parseInt(plus_month.getSelectedItem().toString())-1);

                String[] days = new String[day];
                for(int a = 0 ; a < days.length ; a++){
                    days[a] = ""+(a+1);
                }
                ArrayAdapter spinnerAdapter3 = new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,days);
                plus_day.setAdapter(spinnerAdapter3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        plus_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ksj",plus_year.getSelectedItem().toString());
                int day = date_count(Integer.parseInt(plus_year.getSelectedItem().toString()),position);

                String[] days = new String[day];
                for(int a = 0 ; a < days.length ; a++){
                    days[a] = ""+(a+1);
                }
                ArrayAdapter spinnerAdapter3 = new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,days);
                plus_day.setAdapter(spinnerAdapter3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //plus_date_year
    }

    public int date_count(int year , int month){
        Log.d("ksj","들어온 날짜값 :" + year + " " + month);
        Calendar cal = Calendar.getInstance();
        cal.set(year,month,1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //onActivityResult
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if (requestCode == 0) {
                Uri uri = data.getData();
                ClipData clipData = data.getClipData();
                Log.d("ksj","개수 : " + clipData.getItemCount());
                if(clipData!=null){
                    for(int a = 0 ; a < clipData.getItemCount() ; a++){
                        item.add(new plusImageFIle(getRealPathFromURI(clipData.getItemAt(a).getUri()),clipData.getItemAt(a).getUri()));
                    }
                }

                list.setLayoutManager(new GridLayoutManager(this,4));
                //list.setLayoutManager(new LinearLayoutManager(this));
                plusImageAdapter = new PlusImageAdapter(item);
                list.setAdapter(plusImageAdapter);
            }
        }
    }




    private void transition(View view) {
        if (Build.VERSION.SDK_INT < 21) {
            Toast.makeText(PlusActivity.this, "21+ only, keep out", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(PlusActivity.this, PlusToActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(PlusActivity.this, view, "test");
            startActivity(intent, options.toBundle());
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor =  getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }

    @OnClick(R.id.plus_back)
    public void btn_back(){
        finish();
    }

    @OnClick(R.id.plus_btn_complete)
    public void btn_complete(){
        String Title = plus_ti.getText().toString();
        String date = plus_year.getSelectedItem().toString()  + "-"+ plus_month.getSelectedItem().toString()  + "-"+ plus_day.getSelectedItem().toString();
        if(Title.equals("")){
            Toast.makeText(this, "제목을 입력해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
        }else{
            if(item.size() == 0){
                Toast.makeText(this, "사진을 넣어주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }else{
                //모든제어문을 통과햇을시
                final RequestBody server_id =
                        RequestBody.create(MediaType.parse("text/plain"),
                                id);
                final RequestBody title =
                        RequestBody.create(MediaType.parse("text/plain"),
                                Title);
                final RequestBody dates =
                        RequestBody.create(MediaType.parse("text/plain"),
                                date);

                Log.d("ksj","들어옴");
                for(int a = 0 ; a < item.size() ; a++){
                    Log.d("ksj","들어옴" + a);
                    File file = new File(item.get(a).getPath());
                    Log.d("ksj",file.getName());
                    MultipartBody.Part image_file = MultipartBody.Part.createFormData("file",file.getName(), RequestBody.create(MediaType.parse("image/*"),file));
                    Log.d("ksj","서버에 들어감!");
                    final int finalA = a;
                    Call<String> items = RetrofitService.getInstance().getRetrofitRequest().insert_plus(server_id,title,dates,image_file);
                    Log.d("ksj","지나침");
                    items.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Log.d("ksj","들어와져있냠?");
                            if(response.isSuccessful()){
                                insert_ok = response.body();
                                Log.d("ksj","값" + insert_ok);
                                if(!insert_ok.equals("0")){
                                    if(item.size() == finalA+1){
                                        finish();
                                    }
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

    }
}

