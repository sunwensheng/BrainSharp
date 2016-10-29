/*
 * ��΢�Ź��ں���Ϣ����
 * ���ߣ���Ԫ��
 * ʱ�䣺2013��12��30��
 * ��������ԭ������д��������Ȼ����б����Ҿ���΢�ŵ�ʵ��ԭ��Ӧ��ʹ�õ�ScrollView��
 * �������Ŀǰ��ȱ���У�
 * 1��Card��BaseCard�����໹��Ҫ����
 * 2����������������Ļ��Ե�������ڿ�Ƭ��Ե��Ҳ����˵�����������������.[���������scrollbarStyle="outsideOverlay]
 * 3��������Ƭ��ļ�������޷����������������divider��dividerHeight���ԣ�������һ����ɫ��[���������android:divider="@null"]
 * 4�����Ҫʵ��΢�ŵ��Ǹ�֪ͨ����Ҫ����һ�����֡�һ�������ж�
 * 5����ͼƬ�϶�ʱ����ڴ����ĵ�����
 */
package com.sx.chartcart;

import java.util.ArrayList;
import java.util.List;

import net.youmi.android.offers.PointsManager;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;

import com.chinaMobile.MobileAgent;
import com.sx.brainsharp.HomeActivity;
import com.sx.brainsharp.HomeAddappActivity;
import com.sx.brainsharp.R;
import com.sx.model.Canginfo;
import com.sx.sql.DbMananger;
import com.sx.util.CommonUtil;
import com.sx.util.DateUtil;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

public class ChartActivity extends Activity {

	private ListView mListView;
	private CardAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_main);
		mListView=(ListView)findViewById(R.id.ListView);
		mAdapter=new CardAdapter(this,getItems());
		mListView.setAdapter(mAdapter); 
		
//		if(getjifencount()<100){
		if(DateUtil.isTime()){
			chabo();
		}
//		}
	}
	
	public int getjifencount(){
		return  PointsManager.getInstance(this).queryPoints();// ��ѯ�������
	}

	private List<Card> getItems() 
	{
		List<Card> mCards=new ArrayList<Card>();
		final DbMananger db = new DbMananger(this);
		List<Canginfo> canginfo = db.getScrollData();
//		Toast.makeText(HomeAddappActivity.this, "�����ղ�"+canginfo.size()+"��", Toast.LENGTH_SHORT).show();
		if(canginfo.size()==0){
			
			Card mCard=new Card(R.drawable.pic_0,"���㻹û���ղ�Ӵ");
			BaseCard mBaseCard1=new BaseCard(R.drawable.pic_0,"");
			mCard.AppendCard(mBaseCard1);
			mCards.add(mCard);
		}else{
			for(int i=0;i<canginfo.size();i++){
				String content=canginfo.get(i).getAppcontent();
				String type=canginfo.get(i).getApptype();
				String[] tcontent=content.split("#");
				Card mCard=new Card(R.drawable.pic_0,CommonUtil.getApp(type).getAppName());
				BaseCard mBaseCard1=new BaseCard(R.drawable.pic_0,tcontent[0]+"\n�� :"+tcontent[1]);
				mCard.AppendCard(mBaseCard1);
				mCards.add(mCard);
			}
			
		}
			
		return mCards;
	}
	
	public void chabo(){
		// չʾ�岥��棬���Բ�����loadSpot����ʹ��
				SpotManager.getInstance(ChartActivity.this).showSpotAds(
						ChartActivity.this, new SpotDialogListener() {
							@Override
							public void onShowSuccess() {
								Log.i("YoumiAdDemo", "չʾ�ɹ�");
							}

							@Override
							public void onShowFailed() {
								Log.i("YoumiAdDemo", "չʾʧ��");
							}

						}); // //
	}
	
	public String typeString(String type){
		String contnet="";
		if(type.equals("10001")){
			contnet="��Ц����";
		}else if(type.equals("10002")){
			contnet="��Ĭ����";
		}else if(type.equals("10003")){
			contnet="Ȥζ����";
		}else if(type.equals("10004")){
			contnet="��������";
		}else if(type.equals("10005")){
			contnet="��������";
		}else if(type.equals("10006")){
			contnet="��������";
		}else if(type.equals("10007")){
			contnet="�ۺ�����";
		}else if(type.equals("10008")){
			contnet="��ɫ����";
		}else if(type.equals("10009")){
			contnet="��Ȥ����";
		}
		else{
			contnet="�Խת��";
		}
		return contnet;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobileAgent.onResume(this);
	}
	@Override
	protected void onPause() {

		// TODO �Զ����ɷ������
		super.onPause();
		MobileAgent.onPause(this);
	}
}