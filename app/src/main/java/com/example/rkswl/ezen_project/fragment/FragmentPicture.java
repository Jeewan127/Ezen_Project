package com.example.rkswl.ezen_project.fragment;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.rkswl.ezen_project.R;
import com.example.rkswl.ezen_project.activity.PlusActivity;
import com.example.rkswl.ezen_project.activity.PlusToActivity;
import com.example.rkswl.ezen_project.adapter.PlusImageAdapter;

public class FragmentPicture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_picture);

        RecyclerView list = findViewById(R.id.picture_recyclerview);
        list.setLayoutManager(new GridLayoutManager(this, 4));
        PlusImageAdapter imageAdapter = new PlusImageAdapter(new PlusImageAdapter.Listener() {
            @Override
            public void onImageClicked(View view) {
                transition(view);  // 클릭시 코드
            }
        });
        list.setAdapter(imageAdapter);
    }

    private void transition(View view) {
        if (Build.VERSION.SDK_INT < 21) {
            Toast.makeText(FragmentPicture.this, "21+ only, keep out", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(FragmentPicture.this, FlagmentPictureTo.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(FragmentPicture.this, view, "test");
            startActivity(intent, options.toBundle());
        }
    }
}
