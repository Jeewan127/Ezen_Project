package com.example.rkswl.ezen_project.fragment;

import android.Manifest;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rkswl.ezen_project.R;
import com.example.rkswl.ezen_project.retrofit.RetrofitService;
import com.example.rkswl.ezen_project.util.RealPathUtil;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class FragmentJoin extends Fragment {
    Boolean boo = false; //아이디 중복확인 Boolean
    Boolean image = false; //이미지 들어가져있는지 확인하는 Boolean

    @BindView(R.id.join_id)
    EditText join_id;
    @BindView(R.id.join_passwd) EditText join_password;
    @BindView(R.id.join_passwd2) EditText join_password2;
    @BindView(R.id.join_txt_password)
    TextView join_txt_password;
    @BindView(R.id.join_txt_id) TextView join_txt_id;
    @BindView(R.id.join_name) EditText join_name;
    @BindView(R.id.join_phone) EditText join_phone;

    @BindView(R.id.join_gender_m)
    ImageView join_gender_m;
    @BindView(R.id.join_gender_w) ImageView join_gender_w;


    @BindView(R.id.join_date_year) Spinner join_year;
    @BindView(R.id.join_date_month) Spinner join_month;
    @BindView(R.id.join_date_day) Spinner join_day;

    @BindView(R.id.join_profile1)
    CircleImageView join_img; //이미지


    String gen = "0"; // 성별 선택값 0 : 없음 1 : 남자  2 : 여자

    //다음부턴을 눌럿을시
    @OnClick(R.id.join_rl_next)
    public void move(){
        String id  = join_id.getText().toString();
        String pass = join_password.getText().toString();
        String pass2 = join_password2.getText().toString();
        String name = join_name.getText().toString();
        String phone = join_phone.getText().toString();
        String date = join_year.getSelectedItem().toString() + "-"+ join_month.getSelectedItem().toString() + "-" + join_day.getSelectedItem().toString();
        //제어문
        if(boo == false){
            //중복확인 실패시
            Toast.makeText(getContext(), "중복확인을 확인해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
        }else{
            if(pass.equals(pass2)){
                if(pass.length() >= 8) {
                    if (gen.equals("0")) {
                        //성별 버튼을 누르지 않앗을시
                        Toast.makeText(getContext(), "성별을 입력해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (name.equals("")) {
                            Toast.makeText(getContext(), "이름을 입력해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            if (phone.equals("")) {
                                Toast.makeText(getContext(), "핸드폰번호를 입력해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                //모든제어가 끝낫을경우 이쪽으로 들어오게 됩니다.
                                //일단 fragment에 있는 변수에 일단 입력해놓습니다.
                                ((FragmentJoinViewPager)getActivity()).server_id  = id;
                                ((FragmentJoinViewPager)getActivity()).server_pass = pass;
                                ((FragmentJoinViewPager)getActivity()).server_date = date;
                                ((FragmentJoinViewPager)getActivity()).server_gender = gen;
                                ((FragmentJoinViewPager)getActivity()).server_name = name;
                                ((FragmentJoinViewPager)getActivity()).server_number = phone;
                                ((FragmentJoinViewPager)getActivity()).two_move();      //이동 함수 함수의위치는 join_fragment
                            }
                        }
                    }
                }else{
                    //비밀번호가 8자리 미만일경우
                    Toast.makeText(getContext(), "비밀번호를 8자리 이상 입력해주시기 바랍니다.", Toast.LENGTH_SHORT).show();
                }
            }else{
                //비밀번호 두가지가 틀릴경우
                Toast.makeText(getContext(), "비밀번호를 다시 확인해 주시기 바랍니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_join,container,false);
        ButterKnife.bind(this,view);

        String[] year = new String[40];
        String[] month = new String[12];

        for(int a = 0 ; a < 40;a++) {
            int b = 1980 + a;
            year[a] = ""+b;
        }
        for(int a = 1 ; a < 13 ; a++){
            month[a-1] = ""+a;
        }

        ArrayAdapter spinnerAdapter = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,year);
        join_year.setAdapter(spinnerAdapter);
        ArrayAdapter spinnerAdapter2 = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,month);
        join_month.setAdapter(spinnerAdapter2);
        final Context context = getContext();
        join_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int day = date_count(position+1980,Integer.parseInt(join_month.getSelectedItem().toString())-1);

                String[] days = new String[day];
                for(int a = 0 ; a < days.length ; a++){
                    days[a] = ""+(a+1);
                }
                ArrayAdapter spinnerAdapter3 = new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,days);
                join_day.setAdapter(spinnerAdapter3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        join_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ksj",join_year.getSelectedItem().toString());
                int day = date_count(Integer.parseInt(join_year.getSelectedItem().toString()),position);

                String[] days = new String[day];
                for(int a = 0 ; a < days.length ; a++){
                    days[a] = ""+(a+1);
                }
                ArrayAdapter spinnerAdapter3 = new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,days);
                join_day.setAdapter(spinnerAdapter3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //스피너 활용법 adapter  활용 하거나 아니면 STring 값으로 그냥 가져오는 방법을 채용함.
        return view;
    }


    public int date_count(int year , int month){
        Log.d("ksj","들어온 날짜값 :" + year + " " + month);
        Calendar cal = Calendar.getInstance();
        cal.set(year,month,1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //중복확인
    @OnClick(R.id.join_overlap)
    public void overlap(){
        String id =  join_id.getText().toString();
        if(id.length() < 8){
            Toast.makeText(getContext(), "아이디는 8자리이상 부탁합니다.", Toast.LENGTH_SHORT).show();
        }else{
            Call<String> item = RetrofitService.getInstance().getRetrofitRequest().over(id);

            item.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        String a =  response.body();
                        if (a.equals("1")){
                            Toast.makeText(getContext(), "중복된 아이디가 있는값입니다.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                            boo = true;
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
    //이미지버튼을 클릭시 갤러리가 연동되는것을 사용함 (아직 다이얼로그가 없기에 일단 이것만 활용하였음)
    @OnClick(R.id.join_profile2)
    public void img_add(){
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(
                        Intent.createChooser(intent,"Select picture"),0);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };
        TedPermission.with(getContext())
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    //위에 이전화살표를 입력시 join_page 를 종료후 login 페이지로 이동
    @OnClick(R.id.join_back)
    public void back(){
        ((FragmentJoinViewPager)getActivity()).finish();
    }


    //아이디값이 바뀔경우
    @OnTextChanged(R.id.join_id)
    public void change(CharSequence t ){
        boo = false;
    }

    //성별(남) 클릭 시 background 값 변경
    @OnClick(R.id.join_gender_m)
    public void gender_m() {
        if(gen.equals("1")){
            join_gender_m.setBackground(getResources().getDrawable(R.drawable.signup_man1));
            join_gender_w.setBackground(getResources().getDrawable(R.drawable.signup_woman1));
            gen = "0";
        }else{
            join_gender_m.setBackground(getResources().getDrawable(R.drawable.signup_man2));
            join_gender_w.setBackground(getResources().getDrawable(R.drawable.signup_woman1));
            gen = "1";
        }



    }
    //성별(여) 클릭 시 background 값 변경
    @OnClick(R.id.join_gender_w)
    public void gender_w(){
        if(gen.equals("2")){
            join_gender_m.setBackground(getResources().getDrawable(R.drawable.signup_man1));
            join_gender_w.setBackground(getResources().getDrawable(R.drawable.signup_woman1));
            gen = "0";
        }else {
            join_gender_m.setBackground(getResources().getDrawable(R.drawable.signup_man1));
            join_gender_w.setBackground(getResources().getDrawable(R.drawable.signup_woman2));
            gen = "2";
        }

    }
    //아이디값이 변경될시 파란색 힌트 값 숨김
    @OnTextChanged(R.id.join_id)
    public void id_ch(){
        Log.d("ksj","실행되었습니다.");
        if(join_id.getText().toString().equals("")){
            join_txt_id.setVisibility(View.VISIBLE);
        }else{
            join_txt_id.setVisibility(View.GONE);
        }
    }
    //패스워드값이 변경될시 파란색 힌트 값을 숨김
    @OnTextChanged(R.id.join_passwd)
    public void pass_ch(){
        if(join_password.getText().toString().equals("")){
            join_txt_password.setVisibility(View.VISIBLE);
        }else{
            join_txt_password.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == 0){
//                File file = new File(
//                        RealPathUtil.getRealPath(getContext(),
//                                data.getData()));



                File file = new File(getRealPathFromURI(data.getData()));

                if (file.exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());

                    join_img.setImageBitmap(bitmap);
                    ((FragmentJoinViewPager)getActivity()).image_file = MultipartBody.Part.createFormData("file",file.getName(), RequestBody.create(MediaType.parse("image/*"),file));
//                    MultipartBody.Part.createFormData("file",
//                            file.getName(),
//                            RequestBody.create(MediaType.parse("image/*"), file));        //위에 한줄로 씀
                    image = true;
                }

            }else if(requestCode == 1){
                //임시로 저장 뒤 불러오고 삭제
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                join_img.setImageBitmap(photo);
                String path = saveBitmaptoJpeg(photo,"addwin","사진으로보냄");
                File file = new File(path);
                ((FragmentJoinViewPager)getActivity()).image_file =  MultipartBody.Part.createFormData("file",file.getName(),RequestBody.create(MediaType.parse("image/*"),file));
                image = true;
            }
        }
    }

    public String saveBitmaptoJpeg(Bitmap bitmap,String folder, String name){

        String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath();
        // Get Absolute Path in External Sdcard
        String foler_name = "/"+folder+"/";
        String file_name = name+".jpg";
        String string_path = ex_storage+foler_name;
        Log.d("ksj",string_path);
        File file_path;
        try{
            file_path = new File(string_path);
            if(!file_path.isDirectory()){
                file_path.mkdirs();
            }

            FileOutputStream out = new FileOutputStream(string_path+file_name);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

        }catch(FileNotFoundException exception){
            Log.e("FileNotFoundException", exception.getMessage());
        }catch(IOException exception){
            Log.e("IOException", exception.getMessage());
        }
        return  string_path+file_name;
    }


    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor =  getContext().getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }



}

