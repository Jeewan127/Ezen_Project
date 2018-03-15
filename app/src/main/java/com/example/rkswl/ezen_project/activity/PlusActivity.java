package com.example.rkswl.ezen_project.activity;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rkswl.ezen_project.R;
import com.example.rkswl.ezen_project.ViewHolder.PlusImageViewHolder;
import com.example.rkswl.ezen_project.adapter.PlusImageAdapter;
import com.example.rkswl.ezen_project.plusImageFIle;

import java.util.ArrayList;

public class PlusActivity extends AppCompatActivity {

    private Button plus_btn_picture;
    RecyclerView list;
    PlusImageAdapter plusImageAdapter;
    ArrayList<plusImageFIle> item = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);

        plus_btn_picture = (Button) findViewById(R.id.plus_btn_picture);
        list = findViewById(R.id.plus_recyclerview);

        plus_btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
            }
        });

        plusImageAdapter = new PlusImageAdapter(new PlusImageAdapter.Listener() {
            @Override
            public void onImageClicked(View view) {
                transition(view);  // 클릭시 코드
            }
        });
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
                        Log.d("ksj","리얼패스들 : " + getRealPathFromURI(clipData.getItemAt(a).getUri()));
                        item.add(new plusImageFIle(getRealPathFromURI(clipData.getItemAt(a).getUri()),clipData.getItemAt(a).getUri()));
                    }
                }

                list.setLayoutManager(new GridLayoutManager(this, 4));
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
}
