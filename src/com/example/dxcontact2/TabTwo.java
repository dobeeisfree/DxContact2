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
//Person Ŭ���� 
//- �ּҷϿ� �߰��� ������� �����͸� �����ϱ� ���� Ŭ���� 
//--------------------------------------------------------------------------------------------//
class Person {
	String name;
	String address;
	String phone;
	//String friendlyScore;
	
	String stringAll() {
		return "�̸�:"+name+", "+"�ּ�:"+address+", ��:"+phone;
	}
}

//--------------------------------------------------------------------------------------------//
//MyAdapter Ŭ����
//- ����Ʈ�� �������� ���� ���ϴ� ������� �����ϱ� ���� Ŭ����  
//--------------------------------------------------------------------------------------------//
class MyAdapter extends ArrayAdapter<Person> {
	LayoutInflater inFlater;
	
	public MyAdapter(Context context, int resource) {
		super(context, resource);
		inFlater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
	}
	
	// ����Ʈ�信 ��� �������� �����ش�
	public View getView(int position, View v, ViewGroup parent) {
		// ����Ʈ�信 �������� ���̾ƿ��� myitem (TextView 3��)
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
	MyAdapter adapter; // ����Ʈ�信 ������(Person��ü) ���� �����
	
	int MAX = 1000;
	Person[] personList = new Person[MAX];	
	int numOfPerson = 0; // �߰��� ����� ��

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
		btOpenYours = (ImageView)findViewById(R.id.imageView2);//������ q �ҷ�����
		//btOpenYours.setOnClickListener(new onButtonOpen());
		btOpen = (ImageView)findViewById(R.id.imageView4); // ȭ��ǥǥ�� �����ϱ�
		//btOpen.setOnClickListener(new onButtonSave());
		

        //---------------------------//
        // ����Ʈ�� ���� ���� �κ�
        //---------------------------//
		lv = (ListView)findViewById(R.id.listView1);
		adapter = new MyAdapter(this, R.layout.myitem);
		lv.setAdapter(adapter);
		// ����Ʈ�� �������� ª�� ������ �� �Ͼ�� �̺�Ʈ�� onListClick
		lv.setOnItemClickListener(new onListClick());
		// ����Ʈ�� �������� ª�� ������ �� �Ͼ�� �̺�Ʈ�� onListViewLongClick
		lv.setOnItemLongClickListener(new onListViewLongClick());
				
		//deleteFile("list.txt"); // list.txt ������ ����
		
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
        		personList[numOfPerson] = new Person();
        		personList[numOfPerson].name = str[0];
        		personList[numOfPerson].address = str[1];
        		personList[numOfPerson].phone = str[2];
    			numOfPerson++;
    		}
    		br.close();
    		is.close();
		} catch (Exception e) {	}
		
		// ����Ʈ�信�� �߰�����
		for(int i =  0; i < numOfPerson; i++) {
			adapter.add(personList[i]); 
		}
		
		Toast.makeText(getApplicationContext(), "WELCOME", Toast.LENGTH_SHORT).show();
	}
	 //--------------------------------------------------------------------------------------------//
    // ����Ʈ�� ª�� ������ �� �Ͼ�� �̺�Ʈ
    // import android.widget.AdapterView.OnItemClickListener; �߰��� ��
    //--------------------------------------------------------------------------------------------//
    public class onListClick implements OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        	// position���� ���õ� �������� ��ġ�� ����Ǿ� ����
        	// ���õ� �������� ������(���õ� Person ��ü)�� �����ͼ� p�� ���� 
        	Person p = adapter.getItem(position);
        	
            //---------------------------//
            // ��Ƽ��Ƽ �̵� �κ�
            //---------------------------//
        	Intent intent = new Intent(TabTwo.this, SMSActivity.class); // MainActivity���� SMSActivity�� �̵��ҰŴ�
        	// ����ǥ�� �̵��� ��Ƽ��Ƽ���� ���޵Ǵ� ���� ������ �ֱ� ���� �ʿ�
        	intent.putExtra("TAG_NAME", p.name); 	// "TAG_NAME"�� name�� ����ǥ 
    		intent.putExtra("TAG_PHONE", p.phone); 	// "TAG_PHONE"�� phone�� ����ǥ
    		intent.putExtra("TAG_ADDRESS", p.address);
    		startActivity(intent); 					// �ش� ��Ƽ��Ƽ�� �̵�    		
        } 
	};

    //--------------------------------------------------------------------------------------------//
    // ����Ʈ�� ��� ������ �� �Ͼ�� �̺�Ʈ
    // import android.widget.AdapterView.OnItemClickListener; �߰��� ��
    //--------------------------------------------------------------------------------------------//
    public class onListViewLongClick implements OnItemLongClickListener  {
        public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
        	//---------------------------//
            // ���õ� ģ�� �����ϴ� �κ�
            //---------------------------//
        	int index = position;        	
			for(int i = (index+1); i < numOfPerson; i++) {
				personList[i-1] = personList[i];
			}
			numOfPerson--;
			personList[numOfPerson] = null;				
			
        	//---------------------------//
            // ����Ʈ�並 �����ϴ� �κ�
            //---------------------------//
			adapter.clear(); // ������ �� ����� 
			for(int i =  0; i < numOfPerson; i++) {
				adapter.add(personList[i]); // �ٽ� �߰���
			}
			
			Toast.makeText(getApplicationContext(), "�����Ϸ�", Toast.LENGTH_SHORT).show();
			
			//---------------------------//
            // ���� ���� ���� �κ�
            //---------------------------//
    	    try {
    	    	// MODE_PRIVATE(�����) - ���� ������ �����, ���� �ٽ� ���ڴ�
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
	// �߰� ��ư ������ �� �Ͼ�� �̺�Ʈ
	//--------------------------------------------------------------------------------------------//
    public class onButtonAdd implements OnClickListener {
    	public void onClick(View v) {
    		//---------------------------//
            // personList�� �߰��ϴ� �κ�
            //---------------------------//
    		personList[numOfPerson] = new Person(); 						// ��ü ����
    		personList[numOfPerson].name = etAddName.getText().toString(); 	// editText�� �Է��� ���ڿ��� name�� ����
    		personList[numOfPerson].address = etAddAddress.getText().toString();
    		personList[numOfPerson].phone = etAddPhone.getText().toString();
    		
    		adapter.add(personList[numOfPerson]); // ����Ʈ�信�� �߰�    		
    		Toast.makeText(getApplicationContext(), "����Ϸ�", Toast.LENGTH_SHORT).show();
    		
    		//---------------------------//
            // ���� ���� �κ�
            //---------------------------//
    	    try {
    	    	// MODE_APPEND(�̾��) - ���� ���뿡 �̾ ���ڴ�
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
