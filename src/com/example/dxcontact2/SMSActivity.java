package com.example.dxcontact2;

import android.os.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.telephony.SmsManager;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import java.util.*;
import java.io.*;

import android.media.MediaPlayer;
import android.net.Uri;
import android.graphics.*;
import android.graphics.Bitmap.CompressFormat;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
public class SMSActivity extends Activity {
	 EditText etMessage;
	 ImageView ivSend, ivprofile;
	 TextView tvName, tvPhone, tvAddress;
	 String name; String phoneNumber; String address; //String frienlyscore;
	 
	 
	 int freindly=0; int saving=0; 
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_sms); // SMSActivity의 레이아웃은 activity_sms	     
		  
			
		     etMessage = (EditText)findViewById(R.id.editText1);
		     ivprofile = (ImageView)findViewById(R.id.imageView2);
		     ivSend = (ImageView)findViewById(R.id.imageView4);
		     ivSend.setOnClickListener(new onButtonSend());
		     tvName = (TextView)findViewById(R.id.textView1);
		     tvPhone = (TextView)findViewById(R.id.textView3);
		     tvAddress = (TextView)findViewById(R.id.textView2);
	 		 //----------------------------------//
	         // 이전 액티비티에서 값을 가져오는 부분
	         //----------------------------------//
		     Intent intent = getIntent();
		     name = intent.getExtras().getString("TAG_NAME"); // 꼬리표를 이용해 이전 액티비티가 전달한 값을 구분함
		     phoneNumber = intent.getExtras().getString("TAG_PHONE");
		     address = intent.getExtras().getString("TAG_ADDRESS");
		     
		     
		     tvName.setText(name);
		     tvPhone.setText(phoneNumber);
		     tvAddress.setText(address);
		     
		 }
		
	public class onButtonSend implements OnClickListener {		 
	     public void onClick(View v) {
	    	 String message = etMessage.getText().toString();
	    	 freindly++;
	    	 
	    	 //---------------------------------------------------------------//
	         // 문자전송 부분
	    	 // AndroidManifest.xml에 아래의 permission 을 적어줘야함
	    	 // <uses-permission android:name="android.permission.SEND_SMS"/>
	    	 //---------------------------------------------------------------//
	    	 SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, null, null);
	    	 Toast.makeText(getApplicationContext(), "전송완료", Toast.LENGTH_SHORT).show();
	    	 finish(); // 현재 액티비티 종료    	
	     }
	 }
}
