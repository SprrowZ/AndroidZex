package com.rye.catcher.activity.adapter.recyadapter.diffdata;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.recyadapter.samedata.TypeAbstractViewHolder;
import com.rye.catcher.activity.adapter.recyadapter.samedata.TypeOneViewHolder;
import com.rye.catcher.activity.adapter.recyadapter.samedata.TypeThreeViewHolder;
import com.rye.catcher.activity.adapter.recyadapter.samedata.TypeTwoViewHolder;
import com.rye.catcher.activity.testdata.DataModel;
import com.rye.catcher.activity.testdata.DataModelOne;
import com.rye.catcher.activity.testdata.DataModelThree;
import com.rye.catcher.activity.testdata.DataModelTwo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created at 2018/9/18.
 *
 * @author Zzg
 */
public class DemoAdapterEx extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_ONE=1;
    public static final int TYPE_TWO=2;
    public static final int TYPE_THREE=3;
    private LayoutInflater mInflater;
    private List<DataModelOne> mList1;
    private List<DataModelTwo> mList2;
    private List<DataModelThree> mList3;
    public DemoAdapterEx(Context context){
        mInflater=LayoutInflater.from(context);
    }

    private List<Integer> types=new ArrayList<>();
    private Map<Integer,Integer> mPositions=new HashMap<>();//用来记录每种list的起始位置
    public void  addList(List<DataModelOne> list1, List<DataModelTwo> list2, List<DataModelThree> list3){
        addListByType(TYPE_ONE,list1);
        addListByType(TYPE_TWO,list2);
        addListByType(TYPE_THREE,list3);
        this.mList1=list1;
        this.mList2=list2;
        this.mList3=list3;
    }
    private void addListByType(int type,List list){
        mPositions.put(type,types.size());
        for (int i=0;i<list.size();i++){
            types.add(type);
        }
    }
//    public  void addList(List<DataModel> list){
//        mList.addAll(list);
//    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         switch (viewType){
             case TYPE_ONE:
                 return  new TypeOneViewHolderEx(mInflater.inflate(
                         R.layout.recycleview_type_one,parent,false));
             case TYPE_TWO:
                 return  new TypeTwoViewHolderEx(mInflater.inflate(
                         R.layout.recycleview_type_two,parent,false));
             case TYPE_THREE:
                 return  new TypeThreeViewHolderEx(mInflater.inflate(
                         R.layout.recycleview_type_three,parent,false));
         }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        int realPosition=position-mPositions.get(viewType);
         switch (viewType){
             case TYPE_ONE:
                 ((TypeOneViewHolderEx)holder).bindHolder(mList1.get(realPosition));
                 break;
             case TYPE_TWO:
                 ((TypeTwoViewHolderEx)holder).bindHolder(mList2.get(realPosition));
                 break;
             case TYPE_THREE:
                 ((TypeThreeViewHolderEx)holder).bindHolder(mList3.get(realPosition));
                 break;

        }

    }
    @Override
    public int getItemCount() {
        return types.size();
    }

    @Override
    public int getItemViewType(int position) {
        return types.get(position);
    }
}
