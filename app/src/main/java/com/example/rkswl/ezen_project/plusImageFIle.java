package com.example.rkswl.ezen_project;

import android.net.Uri;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by rkswl on 2018-03-14.
 */
// 아이템 저장 클래스
@Data
@NoArgsConstructor
@ToString
public class plusImageFIle {
    private String path;
    private Uri image;
    public plusImageFIle(String path , Uri image){
        this.path = path;
        this.image = image;

    }
}
