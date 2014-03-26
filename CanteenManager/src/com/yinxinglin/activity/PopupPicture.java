package com.yinxinglin.activity;

import java.io.File;

import com.yingxinlin.canteenmanager.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

public class PopupPicture extends PopupWindow{

    private static final int OPEN_CAMERA_CODE = 99;
	private static final int OPEN_GALLERY_CODE = 100; 
    public static final int CROP_PHOTO_CODE = 101;
    public static final String TEMP_FILENAME = "temp.jpg";
    
    public static String TEMP_PATH = ""; 
    private File tempFile = null;
    private Context context = null;
    private View layout = null;
    
	public PopupPicture(Context context) {
		super(context);
		this.context = context;
		layout = ((Activity)context).getLayoutInflater().inflate(R.layout.popup_picture, null);
		layout.findViewById(R.id.add_by_camera).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					TEMP_PATH = Environment.getExternalStorageDirectory() + File.separator + "CanteenManager" + File.separator;
	                File myDir = new File(TEMP_PATH);
	                if(!myDir.exists()) {
	                	myDir.mkdirs();
	                };
	                tempFile = new File(TEMP_PATH + TEMP_FILENAME);
	                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 打开相机
	                intent.putExtra("output", Uri.fromFile(tempFile));
			        ((Activity)PopupPicture.this.context).startActivityForResult(intent, OPEN_CAMERA_CODE);
	            } else {
	                Toast.makeText(PopupPicture.this.context, "请插入sdcard", Toast.LENGTH_SHORT).show();
	            }
				dismiss();
			}
		});
		layout.findViewById(R.id.add_by_gallery).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					TEMP_PATH = Environment.getExternalStorageDirectory() + File.separator + "CanteenManager" + File.separator;
	                File myDir = new File(TEMP_PATH);
	                if(!myDir.exists()) {
	                	myDir.mkdirs();
	                };
	                tempFile = new File(TEMP_PATH + TEMP_FILENAME);
					Intent intent = new Intent(Intent.ACTION_PICK);// 打开相册
					intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
			        intent.putExtra("output", Uri.fromFile(tempFile));
			        ((Activity)PopupPicture.this.context).startActivityForResult(intent, OPEN_GALLERY_CODE);
	            } else {
	                Toast.makeText(PopupPicture.this.context, "请插入sdcard", Toast.LENGTH_SHORT).show();
	            }
				dismiss();
			}
		});
		layout.findViewById(R.id.add_cancel).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();	
			}
		});
		setContentView(layout);
		setWindowLayoutMode(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		setOutsideTouchable(true);
		setFocusable(true);
		setBackgroundDrawable(new BitmapDrawable()); 
	}
	
	public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("output", Uri.fromFile(tempFile));
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 128);
        intent.putExtra("outputY", 128);
        ((Activity)context).startActivityForResult(intent, CROP_PHOTO_CODE);
    }
	
	public static void deleteTemp() {
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			String TEMP_PATH = Environment.getExternalStorageDirectory() + File.separator + "CanteenManager" + File.separator;
            File myDir = new File(TEMP_PATH);
            if(!myDir.exists()) {
            	return;
            };
            File tempFile = new File(TEMP_PATH + TEMP_FILENAME);
            if(tempFile.exists())
            	tempFile.delete();
        }
	}
	
	public interface PicSolver {
		public void afterCrop(String file);
	}
	
	public void OnActionResult(int requestCode, int resultCode, Intent data, PicSolver pic) {
        switch (requestCode) {
        case OPEN_CAMERA_CODE:
        	if(tempFile.length()>0)
        		cropPhoto(Uri.fromFile(tempFile));
            break;
        case OPEN_GALLERY_CODE:
            cropPhoto(data.getData());
            break;
        case CROP_PHOTO_CODE:
        	pic.afterCrop(TEMP_PATH+TEMP_FILENAME);
        	break;
        default:
            break;
        }
	}
}
