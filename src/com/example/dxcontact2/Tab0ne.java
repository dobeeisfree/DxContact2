package com.example.dxcontact2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import com.example.dxcontact2.SMSActivity.onButtonSend;

import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class Tab0ne extends Activity {
	EditText etSearch; ImageView sch; //�˻���
	ImageView humanface; //ģ�е� �����ִ� ��
	ImageView findProfile; TextView findName, findAddress, findPhoneNum; //�˻��� ����
	
	

	String name; String phoneNumber; String address; // ����
	String Y = "";	String n = "";	String p="";	String a="";
	int k=0;//���庯��
	
	SMSActivity ff = new SMSActivity(); // �������� ��ɿ� �ִ� ģ�е� ������ �����;��� �׷��� ����
	TabTwo tt = new TabTwo(); // �߰��� ����ó ������ �����´�
	
	//int k=0;int saving=0;
	Random random = new Random();
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    // TODO Auto-generated method stub
	    setContentView(R.layout.tab01);
	    
	    etSearch = (EditText)findViewById(R.id.editText1);//�˻���
		sch = (ImageView)findViewById(R.id.imageView9); 
		sch.setOnClickListener(new onButtonSearch());
		
		humanface=(ImageView)findViewById(R.id.imageView4); 
		
		findProfile = (ImageView)findViewById(R.id.imageView2); //�˻��� ��
		findProfile.setOnClickListener(new onButtonfindProfile());
		findName = (TextView)findViewById(R.id.textView1);
		findAddress = (TextView)findViewById(R.id.textView2);
		findPhoneNum = (TextView)findViewById(R.id.textView3);
		
		
		findName.setText("NAME");
		findAddress.setText("ADDRESS");
		findPhoneNum.setText("PHONE");
		
		//---------------------------//
        // ���� �б� ���� �κ�
        //---------------------------//
		String line = "";
		try {
			// ���� list.txt ������ ������ ���� list.txt ������ ����
			InputStream is = openFileInput("list.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
    		// ���Ͽ� ����� ������ �� �پ� ����. �̸� �ݺ��ϴٰ� �� �̻� ���� ������ ������ while()�� ����
			while((line = br.readLine()) != null) {     			
    			String[] str = line.split(",");
        		tt.personList[tt.numOfPerson] = new Person();
        		tt.personList[tt.numOfPerson].name = str[0];
        		tt.personList[tt.numOfPerson].address = str[1];
        		tt.personList[tt.numOfPerson].phone = str[2];
    			tt.numOfPerson++;
    		}
    		br.close();
    		is.close();
		} catch (Exception e) {	}
	}

	
	///////////////////�˻�
	public class onButtonSearch implements OnClickListener {
		public void onClick(View v) {
			String name = etSearch.getText().toString();
			//�˻��� ���� �ϸ� �������� ����
			k++;
			for(int i = 0; i < tt.numOfPerson; i++) { // ó������ ������ ��� ���캻��
				if(name.equals(tt.personList[i].name) == true) {					
					Y = Y + "ģ�е� �������� Ȯ���غ�����!";
					n = n+ tt.personList[i].name;
					p = p+ tt.personList[i].phone;
					a = a+ tt.personList[i].address;		
				}	
			}
			findName.setText(n);
			findAddress.setText(a);
			findPhoneNum.setText(p);
			//humanF();
			Toast.makeText(getApplicationContext(), Y, Toast.LENGTH_SHORT).show(); // �˻� ��� ���
			//�ʱ�ȭ
			n=""; Y=""; a=""; p="";
			if(k==1){ humanface.setImageResource(R.drawable.h1); }
			if(k==2){ humanface.setImageResource(R.drawable.h1); }
			if(k==3){ humanface.setImageResource(R.drawable.h2); }
			if(k==4){ humanface.setImageResource(R.drawable.h3); }
			if(k==5){ humanface.setImageResource(R.drawable.h4); }
			if(k==6){ humanface.setImageResource(R.drawable.h5); }
			if(k==7){ humanface.setImageResource(R.drawable.h6); }
			if(k==8){ humanface.setImageResource(R.drawable.h7); }
			if(k==9){ humanface.setImageResource(R.drawable.h8);
			//�˻� Ƚ�� �ٽ� �ʱ�ȭ
			k=0;}
					
		}
	}
	//�˻��� ������  ������ ��
	//�˻��� �������� ������ �ް� �������ۤ��ϴ� �κ����� ������
		public class onButtonfindProfile implements OnClickListener {
			@Override
			public void onClick(View v)  {
				// TODO Auto-generated method stub
				setContentView(R.layout.activity_sms);
		
			} 
		}
	
}
