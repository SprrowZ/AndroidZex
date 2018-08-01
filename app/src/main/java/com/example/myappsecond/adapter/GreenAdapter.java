package com.example.myappsecond.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myappsecond.GreenDaos.Base.Cartoons;
import com.example.myappsecond.R;
import com.example.myappsecond.utils.DateUtils;
import com.example.myappsecond.utils.ExtraUtil.Bean;
import com.example.myappsecond.utils.TypeFaceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zzg on 2018/7/7.
 */
public class GreenAdapter extends BaseArrayAdapter {
    private LayoutInflater layoutInflater;
    private List<Cartoons> list=new ArrayList<Cartoons>();
    private Bean bean=new Bean();
    public GreenAdapter(Activity activity, List<Cartoons> list){
        super(activity,0,list);
        this.list=list;
        layoutInflater=LayoutInflater.from(activity);
        initBean();
    }

    private void initBean() {
        bean.set("火影忍者","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532964733901&di=ff55cb39d7e96ed196afb6d005697490&imgtype=0&src=http%3A%2F%2Fimg4q.duitang.com%2Fuploads%2Fitem%2F201411%2F29%2F20141129184236_HzYti.thumb.224_0.jpeg");
        bean.set("一人之下","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532965072137&di=4549f7055c1136872ea9226e2e8973b5&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171110%2Fdb7ac7e41e5d45b8af54aaf8bef65c21.jpeg");
        bean.set("埃罗芒阿老师","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532965184403&di=09be9f8b509cc501c366dc5d8b4d4978&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F8973fdcde16601b01ea11fdf6a95d465de5bf720.png");
        bean.set("夏目友人帐","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532965240228&di=1e215170177c4b738443be71f6d7be87&imgtype=0&src=http%3A%2F%2Fimage.cnpp.cn%2Fupload%2Fimages%2F20170926%2F1506411060_24392_1.jpg");
        bean.set("东京喰种","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532965312502&di=1768d06c14ac3f1e922151414755a5c4&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2Fcomic%2Fpics%2Fhv1%2F58%2F9%2F2085%2F135579478.jpg");
        bean.set("天行九歌","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532965352732&di=aefdbf91483a514c6912be0fbae8252a&imgtype=0&src=http%3A%2F%2Fi1.hdslb.com%2Fbfs%2Farchive%2F4bf97213e40592cdce08904dd0f8003de3f7c736.jpg");
        bean.set("秦时明月","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532965445020&di=2df7bfd1762f014eb7ee43c97e2cab4b&imgtype=0&src=http%3A%2F%2Fimage.uczzd.cn%2F18210352033593496211.jpg%3Fid%3D0%26from%3Dexport");
        bean.set("某科学的超电磁炮","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532965511067&di=6b03a2a5caa6927c8cf68321361a4ba1&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201511%2F05%2F20151105202509_4UfxE.gif");
        bean.set("虫师","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532965598982&di=929fdafdad971463d83c48ced8482cc3&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201410%2F20%2F20141020154610_YCJHT.jpeg");
        bean.set("超神学院","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1532965757379&di=29cd7c4b00321a9c62729bfc5cef1499&imgtype=0&src=http%3A%2F%2Fimg.anzhuotan.com%2Fupic%2Fnews%2F2017-07-27%2F59798584f36e8.jpg");
    }

    Cartoons cartoons=new Cartoons();

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        cartoons=list.get(position);
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.greendao_item,null);
            viewHolder.iv=convertView.findViewById(R.id.iv);
            viewHolder.name=convertView.findViewById(R.id.name);
            viewHolder.hero=convertView.findViewById(R.id.hero);
            viewHolder.details=convertView.findViewById(R.id.details);
            viewHolder.status=convertView.findViewById(R.id.status);
            viewHolder.time=convertView.findViewById(R.id.time);
            viewHolder.item_id=convertView.findViewById(R.id.item_id);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
      //先设置默认图
        viewHolder.iv.setImageResource(R.drawable.leimu);
      //进行赋值操作
        for (int i=0;i<bean.size();i++){//这样循环，数据一多肯定就会卡死的
            if (!bean.getStr(cartoons.getNAME()).equals("")){
                Glide.with(activity).load(bean.getStr(cartoons.getNAME())).into(viewHolder.iv);
             continue;
            }
        }
        viewHolder.name.setText("《"+cartoons.getNAME()+"》");
        viewHolder.hero.setText(cartoons.getHERO()+"、"+cartoons.getHEROINE());

        viewHolder.time.setText(DateUtils.getConversationTime(cartoons.getINSERT_TIME()));
        viewHolder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击详情，进入到详情页面

            }
        });
        viewHolder.name.setTypeface(TypeFaceUtil.getInstance().tf(activity));
        viewHolder.time.setTypeface(TypeFaceUtil.getInstance().tf(activity));
        viewHolder.item_id.setText(cartoons.getID().toString());
        if (cartoons.getIS_END()){
            viewHolder.status.setText(R.string.end);
            viewHolder.status.setTextColor(activity.getResources().getColor(R.color.green3));
        }else{
            viewHolder.status.setText(R.string.keeping);
            viewHolder.status.setTextColor(activity.getResources().getColor(R.color.red_theme));
        }

        return convertView;
    }

}
class ViewHolder{
    public ImageView iv;
    public TextView  name;
    public TextView  hero;
    public RelativeLayout details;
    public TextView  time;
    public TextView  status;//状态
    public TextView  item_id;
}