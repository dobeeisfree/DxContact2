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
	    setContentView(R.layout.activity_sms); // SMSActivity�� ���̾ƿ��� activity_sms	     
		  
			
		     etMessage = (EditText)findViewById(R.id.editText1);
		     ivprofile = (ImageView)findViewById(R.id.imageView2);
		     ivSend = (ImageView)findViewById(R.id.imageView4);
		     ivSend.setOnClickListener(new onButtonSend());
		     tvName = (TextView)findViewById(R.id.textView1);
		     tvPhone = (TextView)findViewById(R.id.textView3);
		     tvAddress = (TextView)findViewById(R.id.textView2);
	 		 //----------------------------------//
	         // ���� ��Ƽ��Ƽ���� ���� �������� �κ�
	         //----------------------------------//
		     Intent intent = getIntent();
		     name = intent.getExtras().getString("TAG_NAME"); // ����ǥ�� �̿��� ���� ��Ƽ��Ƽ�� ������ ���� ������
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
	         // �������� �κ�
	    	 // AndroidManifest.xml�� �Ʒ��� permission �� ���������
	    	 // <uses-permission android:name="android.permission.SEND_SMS"/>
	    	 //---------------------------------------------------------------//
	    	 SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, null, null);
	    	 Toast.makeText(getApplicationContext(), "���ۿϷ�", Toast.LENGTH_SHORT).show();
	    	 finish(); // ���� ��Ƽ��Ƽ ����    	
	     }
	 }
}
