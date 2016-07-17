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
	EditText etSearch; ImageView sch; //검색용
	ImageView humanface; //친밀도 보여주는 곳
	ImageView findProfile; TextView findName, findAddress, findPhoneNum; //검색된 정보
	
	

	String name; String phoneNumber; String address; // 저장
	String Y = "";	String n = "";	String p="";	String a="";
	int k=0;//저장변수
	
	SMSActivity ff = new SMSActivity(); // 문자전송 기능에 있는 친밀도 정보를 가져와야함 그래서 참조
	TabTwo tt = new TabTwo(); // 추가된 연락처 정보를 가져온다
	
	//int k=0;int saving=0;
	Random random = new Random();
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    // TODO Auto-generated method stub
	    setContentView(R.layout.tab01);
	    
	    etSearch = (EditText)findViewById(R.id.editText1);//검색용
		sch = (ImageView)findViewById(R.id.imageView9); 
		sch.setOnClickListener(new onButtonSearch());
		
		humanface=(ImageView)findViewById(R.id.imageView4); 
		
		findProfile = (ImageView)findViewById(R.id.imageView2); //검색된 곳
		findProfile.setOnClickListener(new onButtonfindProfile());
		findName = (TextView)findViewById(R.id.textView1);
		findAddress = (TextView)findViewById(R.id.textView2);
		findPhoneNum = (TextView)findViewById(R.id.textView3);
		
		
		findName.setText("NAME");
		findAddress.setText("ADDRESS");
		findPhoneNum.setText("PHONE");
		
		//---------------------------//
        // 파일 읽기 관련 부분
        //---------------------------//
		String line = "";
		try {
			// 만약 list.txt 파일이 없으면 새로 list.txt 파일을 생성
			InputStream is = openFileInput("list.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
    		// 파일에 저장된 내용을 한 줄씩 읽음. 이를 반복하다가 더 이상 읽을 내용이 없으면 while()문 종료
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

	
	///////////////////검색
	public class onButtonSearch implements OnClickListener {
		public void onClick(View v) {
			String name = etSearch.getText().toString();
			//검색을 많이 하면 애정도가 높죠
			k++;
			for(int i = 0; i < tt.numOfPerson; i++) { // 처음부터 끝까지 모두 살펴본다
				if(name.equals(tt.personList[i].name) == true) {					
					Y = Y + "친밀도 프로필을 확인해보세요!";
					n = n+ tt.personList[i].name;
					p = p+ tt.personList[i].phone;
					a = a+ tt.personList[i].address;		
				}	
			}
			findName.setText(n);
			findAddress.setText(a);
			findPhoneNum.setText(p);
			//humanF();
			Toast.makeText(getApplicationContext(), Y, Toast.LENGTH_SHORT).show(); // 검색 결과 출력
			//초기화
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
			//검색 횟수 다시 초기화
			k=0;}
					
		}
	}
	//검색된 프로필  눌렀을 때
	//검색된 프로필의 정보를 받고 문자전송ㅇ하는 부분으로 가야함
		public class onButtonfindProfile implements OnClickListener {
			@Override
			public void onClick(View v)  {
				// TODO Auto-generated method stub
				setContentView(R.layout.activity_sms);
		
			} 
		}
	
}
