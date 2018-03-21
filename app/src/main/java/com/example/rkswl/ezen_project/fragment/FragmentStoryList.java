package com.example.rkswl.ezen_project.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rkswl.ezen_project.DbManger;
import com.example.rkswl.ezen_project.R;
import com.example.rkswl.ezen_project.StroyImageFIle;
import com.example.rkswl.ezen_project.ViewHolder.StoryListViewHolder;
import com.example.rkswl.ezen_project.activity.MainActivity;
import com.example.rkswl.ezen_project.activity.PlusActivity;
import com.example.rkswl.ezen_project.adapter.StoryImageAdapter;
import com.example.rkswl.ezen_project.adapter.StoryListAdapter;
import com.example.rkswl.ezen_project.plusImageFIle;
import com.example.rkswl.ezen_project.retrofit.RetrofitService;
import com.example.rkswl.ezen_project.system_address;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentStoryList extends AppCompatActivity {

    String title , date , id;
    int count = 0;

    @BindView(R.id.story_list_recyclerview) RecyclerView list;
    StoryListAdapter storyListAdapter;

    @BindView(R.id.story_list_btn_back) Button btn_back;            //뒤로가기
    @BindView(R.id.story_list_btn_plus) Button btn_plus;            //사진을 더하는곳

    @BindView(R.id.story_list_txt_dday) TextView count_date;        //처음만난날에서 며칠째 되는날
    @BindView(R.id.story_list_txt_day) TextView insert_date;        //올린날짜
    @BindView(R.id.story_list_txt_title) TextView txt_title;        //제목

    @BindView(R.id.story_list_img) ImageView img;                   //맨위에 따로있는것 리사이클뷰 이외의것
    DbManger dbManger;


    ArrayList<StroyImageFIle> items;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_story_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
        dbManger = new DbManger(this,"addwin_user.db" , null,1);
//        1995-8-1        8
//        1995-12-1      9
//        1995-12-11      10

        SimpleDateFormat original_format = new SimpleDateFormat("yyyy-m-d");
        SimpleDateFormat new_format = new SimpleDateFormat("yyyy년 mm월 dd일");

        try{
            Date original_date = original_format.parse(date);
            String new_date = new_format.format(original_date);
            insert_date.setText(new_date);

        }catch (ParseException e){
            e.getErrorOffset();
        }

        txt_title.setText(title);
        id  =  dbManger.get_id();

        Call<ArrayList<StroyImageFIle>> item = RetrofitService.getInstance().getRetrofitRequest().get_story_list(id,title,date);
        item.enqueue(new Callback<ArrayList<StroyImageFIle>>() {
            @Override
            public void onResponse(Call<ArrayList<StroyImageFIle>> call, Response<ArrayList<StroyImageFIle>> response) {
                if(response.isSuccessful()){
                    items = response.body();
                    Log.d("ksj","들어와져있을시 파일 name : " + items.get(0).getFile_path());
                    BitmapDrawable ob = new BitmapDrawable(getResources(), new ImageRoader().getBitmapImg(items.get(0).getFile_path()));
                    img.setBackground(ob);
                    Log.d("ksj","item의 사이즈 : " + items.size());
                    items.remove(0);

                    list.setLayoutManager(new GridLayoutManager(FragmentStoryList.this,1));
                    storyListAdapter = new StoryListAdapter(items,FragmentStoryList.this);
                    list.setAdapter(storyListAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StroyImageFIle>> call, Throwable t) {

            }
        });

    }

    @OnClick(R.id.story_list_btn_plus)
    public void list_plus(){
        //리스트를 플러스하는곳!
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
        TedPermission.with(FragmentStoryList.this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

    }

    //onActivityResult


    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if (requestCode == 0) {
                final RequestBody server_id =
                        RequestBody.create(MediaType.parse("text/plain"),
                                id);
                final RequestBody titles =
                        RequestBody.create(MediaType.parse("text/plain"),
                                title);
                final RequestBody dates =
                        RequestBody.create(MediaType.parse("text/plain"),
                                date);

                Uri uri = data.getData();
                final ClipData clipData = data.getClipData();
                Log.d("ksj","개수 : " + clipData.getItemCount());
                if(clipData!=null){
                    for(int a = 0 ; a < clipData.getItemCount() ; a++){
                        File file = new File(getRealPathFromURI(clipData.getItemAt(a).getUri()));
                        MultipartBody.Part image_file = MultipartBody.Part.createFormData("file",file.getName(), RequestBody.create(MediaType.parse("image/*"),file));
                        Call<String> item = RetrofitService.getInstance().getRetrofitRequest().insert_plus(server_id,titles,dates,image_file);
                        final int finalA = a;
                        item.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.isSuccessful()){
                                    String insert_ok = response.body();
                                    Log.d("ksj","값" + insert_ok);
                                    if(!insert_ok.equals("0")){
                                        count++;
                                        Log.d("ksj","개수" + clipData.getItemCount());
                                        Log.d("ksj","음" + count);
                                        if(clipData.getItemCount() == count){
                                            Call<ArrayList<StroyImageFIle>> item = RetrofitService.getInstance().getRetrofitRequest().get_story_list(id,title,date);
                                            item.enqueue(new Callback<ArrayList<StroyImageFIle>>() {
                                                @Override
                                                public void onResponse(Call<ArrayList<StroyImageFIle>> call, Response<ArrayList<StroyImageFIle>> response) {
                                                    if(response.isSuccessful()){
                                                        items = response.body();
                                                        Log.d("ksj","들어오는 size " + items.size());
                                                        BitmapDrawable ob = new BitmapDrawable(getResources(), new ImageRoader().getBitmapImg(items.get(0).getFile_path()));
                                                        img.setBackground(ob);
                                                        items.remove(0);
                                                        Log.d("ksj","들어오긴함? 뺴애애애애액: " + items.size());
                                                        list.setLayoutManager(new GridLayoutManager(FragmentStoryList.this,1));
                                                        storyListAdapter = new StoryListAdapter(items,FragmentStoryList.this);
                                                        storyListAdapter.notifyDataSetChanged();
                                                        list.setAdapter(storyListAdapter);
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ArrayList<StroyImageFIle>> call, Throwable t) {

                                                }
                                            });
                                        }

                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });


                    }


                }

                Log.d("ksj","id 값 : " + id + "title = " + title + "date  = " + date );



            }

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

    public class ImageRoader {

        private final String serverUrl = system_address.address;

        public ImageRoader() {

            new FragmentStoryList.ThreadPolicy();


        }

        public Bitmap getBitmapImg(String imgStr) {
            Log.d("ksj","들어와져있는 imgStr" + imgStr);
            Bitmap bitmapImg = null;

            try {
                //URL url = new URL(serverUrl + URLEncoder.encode(imgStr, "utf-8"));
                URL url = new URL(serverUrl + imgStr);
                // Character is converted to 'UTF-8' to prevent broken
                Log.d("ksj",""+url);
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




    @OnClick(R.id.story_list_btn_back)
    public void back(){
        Log.d("ksj","눌려지긴한가.. back 버튼이");
        finish();
    }
}
