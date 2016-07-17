package com.example.dxcontact2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemClickListener; 
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Contacts;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.Activity;
import android.os.Bundle;
import android.database.Cursor;
//--------------------------------------------------------------------------------------------//
//Person 클래스 
//- 주소록에 추가될 사람들의 데이터를 관리하기 위한 클래스 
//--------------------------------------------------------------------------------------------//
class Person {
	String name;
	String address;
	String phone;
	//String friendlyScore;
	
	String stringAll() {
		return "이름:"+name+", "+"주소:"+address+", ☎:"+phone;
	}
}

//--------------------------------------------------------------------------------------------//
//MyAdapter 클래스
//- 리스트뷰 아이템을 내가 원하는 모양으로 관리하기 위한 클래스  
//--------------------------------------------------------------------------------------------//
class MyAdapter extends ArrayAdapter<Person> {
	LayoutInflater inFlater;
	
	public MyAdapter(Context context, int resource) {
		super(context, resource);
		inFlater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
	}
	
	// 리스트뷰에 어떻게 보여질지 정해준다
	public View getView(int position, View v, ViewGroup parent) {
		// 리스트뷰에 보여지는 레이아웃은 myitem (TextView 3개)
		View view = inFlater.inflate(R.layout.myitem, null);
		TextView tv1 = (TextView)view.findViewById(R.id.textView1);
		TextView tv2 = (TextView)view.findViewById(R.id.textView2);
		TextView tv3 = (TextView)view.findViewById(R.id.textView3);
		ImageView iv = (ImageView)view.findViewById(R.id.imageView1);
		
		Person p = this.getItem(position);		
		tv1.setText(p.name); tv2.setText(p.address); tv3.setText(p.phone);
		iv.setImageResource(R.drawable.yours);
		
		return view;
	}
}

public class TabTwo extends Activity {
	EditText etAddName, etAddAddress, etAddPhone;
	ImageView btAdd, btOpenYours, btOpen;
	ListView lv;
	Bitmap input, output;
	MyAdapter adapter; // 리스트뷰에 데이터(Person객체) 간의 연결고리
	
	int MAX = 1000;
	Person[] personList = new Person[MAX];	
	int numOfPerson = 0; // 추가된 사람의 수

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tab02);
	    // TODO Auto-generated method stub
	    etAddName = (EditText)findViewById(R.id.editText1);
		etAddAddress = (EditText)findViewById(R.id.editText2);
		etAddPhone = (EditText)findViewById(R.id.editText3);
		btAdd = (ImageView)findViewById(R.id.imageView1);
		btAdd.setOnClickListener(new onButtonAdd());
		btOpenYours = (ImageView)findViewById(R.id.imageView2);//프로필 q 불러오기
		//btOpenYours.setOnClickListener(new onButtonOpen());
		btOpen = (ImageView)findViewById(R.id.imageView4); // 화살표표시 저장하기
		//btOpen.setOnClickListener(new onButtonSave());
		

        //---------------------------//
        // 리스트뷰 관련 설정 부분
        //---------------------------//
		lv = (ListView)findViewById(R.id.listView1);
		adapter = new MyAdapter(this, R.layout.myitem);
		lv.setAdapter(adapter);
		// 리스트뷰 아이템을 짧게 눌렀을 때 일어나는 이벤트는 onListClick
		lv.setOnItemClickListener(new onListClick());
		// 리스트뷰 아이템을 짧게 눌렀을 때 일어나는 이벤트는 onListViewLongClick
		lv.setOnItemLongClickListener(new onListViewLongClick());
				
		//deleteFile("list.txt"); // list.txt 파일을 삭제
		
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
        		personList[numOfPerson] = new Person();
        		personList[numOfPerson].name = str[0];
        		personList[numOfPerson].address = str[1];
        		personList[numOfPerson].phone = str[2];
    			numOfPerson++;
    		}
    		br.close();
    		is.close();
		} catch (Exception e) {	}
		
		// 리스트뷰에도 추가해줌
		for(int i =  0; i < numOfPerson; i++) {
			adapter.add(personList[i]); 
		}
		
		Toast.makeText(getApplicationContext(), "WELCOME", Toast.LENGTH_SHORT).show();
	}
	 //--------------------------------------------------------------------------------------------//
    // 리스트뷰 짧게 눌렀을 때 일어나는 이벤트
    // import android.widget.AdapterView.OnItemClickListener; 추가할 것
    //--------------------------------------------------------------------------------------------//
    public class onListClick implements OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        	// position에는 선택된 아이템의 위치가 저장되어 있음
        	// 선택된 아이템의 데이터(선택된 Person 객체)를 가져와서 p에 복사 
        	Person p = adapter.getItem(position);
        	
            //---------------------------//
            // 액티비티 이동 부분
            //---------------------------//
        	Intent intent = new Intent(TabTwo.this, SMSActivity.class); // MainActivity에서 SMSActivity로 이동할거다
        	// 꼬리표는 이동할 액티비티에게 전달되는 값을 구별해 주기 위해 필요
        	intent.putExtra("TAG_NAME", p.name); 	// "TAG_NAME"은 name의 꼬리표 
    		intent.putExtra("TAG_PHONE", p.phone); 	// "TAG_PHONE"는 phone의 꼬리표
    		intent.putExtra("TAG_ADDRESS", p.address);
    		startActivity(intent); 					// 해당 액티비티로 이동    		
        } 
	};

    //--------------------------------------------------------------------------------------------//
    // 리스트뷰 길게 눌렀을 때 일어나는 이벤트
    // import android.widget.AdapterView.OnItemClickListener; 추가할 것
    //--------------------------------------------------------------------------------------------//
    public class onListViewLongClick implements OnItemLongClickListener  {
        public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
        	//---------------------------//
            // 선택된 친구 삭제하는 부분
            //---------------------------//
        	int index = position;        	
			for(int i = (index+1); i < numOfPerson; i++) {
				personList[i-1] = personList[i];
			}
			numOfPerson--;
			personList[numOfPerson] = null;				
			
        	//---------------------------//
            // 리스트뷰를 갱신하는 부분
            //---------------------------//
			adapter.clear(); // 모조리 다 지우고 
			for(int i =  0; i < numOfPerson; i++) {
				adapter.add(personList[i]); // 다시 추가함
			}
			
			Toast.makeText(getApplicationContext(), "삭제완료", Toast.LENGTH_SHORT).show();
			
			//---------------------------//
            // 파일 쓰기 관련 부분
            //---------------------------//
    	    try {
    	    	// MODE_PRIVATE(덮어쓰기) - 기존 내용은 지우고, 새로 다시 쓰겠다
    			FileOutputStream fos = openFileOutput("list.txt", MODE_PRIVATE);
    			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    			for(int i = 0; i < numOfPerson; i++) {
    				bw.write(personList[i].name+",");
    				bw.write(personList[i].address+",");
    				bw.write(personList[i].phone+"\n");
    			}
    			bw.close();
    			fos.close();
    		} catch (Exception e) { }
    	    
        	return true;
        } 
	};	
	
	//--------------------------------------------------------------------------------------------//
	// 추가 버튼 눌렀을 때 일어나는 이벤트
	//--------------------------------------------------------------------------------------------//
    public class onButtonAdd implements OnClickListener {
    	public void onClick(View v) {
    		//---------------------------//
            // personList에 추가하는 부분
            //---------------------------//
    		personList[numOfPerson] = new Person(); 						// 객체 생성
    		personList[numOfPerson].name = etAddName.getText().toString(); 	// editText에 입력한 문자열을 name에 저장
    		personList[numOfPerson].address = etAddAddress.getText().toString();
    		personList[numOfPerson].phone = etAddPhone.getText().toString();
    		
    		adapter.add(personList[numOfPerson]); // 리스트뷰에도 추가    		
    		Toast.makeText(getApplicationContext(), "저장완료", Toast.LENGTH_SHORT).show();
    		
    		//---------------------------//
            // 파일 쓰기 부분
            //---------------------------//
    	    try {
    	    	// MODE_APPEND(이어쓰기) - 기존 내용에 이어서 쓰겠다
    			FileOutputStream fos = openFileOutput("list.txt", MODE_APPEND);
    			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    			bw.write(personList[numOfPerson].name+",");
    			bw.write(personList[numOfPerson].address+",");
    			bw.write(personList[numOfPerson].phone+"\n");    			
    			bw.close();
    			fos.close();
    		} catch (Exception e) { }    	    
    	    
    	    numOfPerson++;
    	}
    }
    
}
