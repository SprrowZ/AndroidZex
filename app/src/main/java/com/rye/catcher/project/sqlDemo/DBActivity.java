package com.rye.catcher.project.sqlDemo;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZZG on 2018/1/16.
 */

public class DBActivity extends BaseActivity {
    private static final   String TAG="DBActivity";
    private DBManager dbManager;
    private ListView listView;

    private CartoonDao dao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_1);
        listView=findViewById(R.id.listView);
        //初始化DBManger
        dbManager=new DBManager(this);
        //第二个测试
        dao=new CartoonDao(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDB();
    }
   public void add(View view){
    ArrayList<Person> persons=new ArrayList<Person>();
    Person person1=new Person("Ella",22,"lively girl");
    Person person2 = new Person("Jenny", 22, "beautiful girl");
    Person person3 = new Person("Jessica", 23, "sexy girl");
    Person person4 = new Person("Kelly", 23, "hot baby");
    Person person5 = new Person("Jane", 25, "a pretty woman");

    persons.add(person1);
    persons.add(person2);
    persons.add(person3);
    persons.add(person4);
    persons.add(person5);

    dbManager.add(persons);
}

  public void update(View view){
    //将Jane的年龄改为30（更改的事数据库中的值，要查询才能刷新ListView中显示的结果）
      Person person=new Person();
      person.name="Jane";
      person.age=30;
      dbManager.updataAge(person);
  }

  public void delete(View view){
      //删除所有三十岁以上的人（此操作在update之后进行，Jane会被删除（因为她的年龄被改为30））
      //同样是查询才能查看更改结果
      Person person=new Person();
      person.age=30;
      dbManager.deleteOldPerson(person);
  }
  public void query(View view){
      List<Person> persons=dbManager.query();
      ArrayList<Map<String,String>> list=new ArrayList<Map<String,String>>();
      for (Person person:persons){
          HashMap<String,String> map=new HashMap<String, String>();
          map.put("name",person.name);
          map.put("info",person.age+"years old"+person.info);
          list.add(map);
      }
      SimpleAdapter adapter=new SimpleAdapter(this,list,android.R.layout.simple_expandable_list_item_2,
              new String[]{"name","info"},new int[]{android.R.id.text1,android.R.id.text2});
      listView.setAdapter(adapter);

  }

@SuppressWarnings("deprecation")
    public void queryTheCursor(View view){
    Cursor c=dbManager.queryTheCursor();
    startManagingCursor(c);//托付给activity根据自己的生命周期去管理cursor的生命周期
    CursorWrapper cursorWrapper=new CursorWrapper(c){
        @Override
        public String getString(int columnIndex) {
            //将间接前加上年龄
            if (getColumnName(columnIndex).equals("info")){
                int age=getInt(getColumnIndex("age"));
                return age+"years old,"+ super.getString(columnIndex);
            }
            return  super.getString(columnIndex);

        }
    };


    //确保查询结果中有 "_id"列
    SimpleCursorAdapter adapter=new SimpleCursorAdapter(this,android.R.layout.simple_expandable_list_item_2,
            cursorWrapper,new String[]{"name","info"},new int[]{
            android.R.id.text1,android.R.id.text2
    });
    ListView listView=findViewById(R.id.listView);
    listView.setAdapter(adapter);
}

/*******************************第二次Demo*****************************************/
public void addCartoons(View view){
    ArrayList<Cartoon> dataList=new ArrayList<>();
    Cartoon cartoon=new Cartoon();
    cartoon.setValue(Cartoon.NAME,"夏目友人帐");
    cartoon.setValue(Cartoon.LEAD,"夏目贵志");
    cartoon.setValue(Cartoon.NATIONALITY,"日本");
    //
    Cartoon cartoon2=new Cartoon();
    cartoon2.setValue(Cartoon.NAME,"天行九歌");
    cartoon2.setValue(Cartoon.LEAD,"韩非子");
    cartoon2.setValue(Cartoon.NATIONALITY,"中国");
    //
    dataList.add(cartoon);
    dataList.add(cartoon2);
    dao.insertData(dataList);
}

public void updateCartoons(View view){
    Cartoon cartoon=new Cartoon();
   // cartoon.setValue(Cartoon.ID,"15");
    cartoon.setValue(Cartoon.NAME,"我的一生");
    cartoon.setValue(Cartoon.LEAD,"悲歌一首..");
    dao.update(cartoon);
}

public void deleteCartoons(View view){
    dao.deleteData();
}

public void queryCartoons(View view){
    ArrayList<Cartoon> cartoons=dao.query();
    Log.i(TAG, "queryCartoons: "+cartoons.toString());
   ArrayList<Map<String,String>> dataList=new ArrayList<>();
   for (Cartoon cartoon:cartoons){
       HashMap map=new HashMap();
       map.put(Cartoon.ID,cartoon.getValue(Cartoon.ID));
       map.put("INFO",cartoon.getValue(Cartoon.NAME)+","+cartoon.getValue(Cartoon.LEAD));
       dataList.add(map);
   }
   SimpleAdapter adapter=new SimpleAdapter(this,dataList,android.R.layout.simple_expandable_list_item_2,
           new String[]{Cartoon.ID,"INFO"},new int[]{android.R.id.text1,android.R.id.text2});
   listView.setAdapter(adapter);
}







}
