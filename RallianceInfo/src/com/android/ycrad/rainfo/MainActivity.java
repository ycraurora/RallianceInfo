package com.android.ycrad.rainfo;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author YcraD
 * @function Tab页面手势滑动切换以及动画效果
 */
public class MainActivity extends Activity {
	// ViewPager是google SDk中自带的一个附加包的一个类，可以用来实现屏幕间的切换。
	// android-support-v4.jar
	private ViewPager mPager;//页卡内容
	private List<View> listViews; // Tab页面列表
	private ImageView cursor;// 动画图片
	private TextView textView_team, textView_rom, textView_news;// 页卡头标
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		InitImageView();
		InitTextView();
		InitViewPager();
	}

//	/**
//	 * 设置团队信息并存至TextView
//	 */
//	private void _setTeamTxt(View teamInfo){
//		textView_team = (TextView) teamInfo.findViewById(R.id.team_info);
//		InputStream is_team = getResources().openRawResource(R.raw.ra_teaminfo);
//		String team_info = TxtReader.getString(is_team);
//		textView_team.setText(team_info);
//	}
//	
//	/**
//	 * 设置ROM信息并存至TextView
//	 */
//	private void _setRomTxt(View romInfo){
//		textView_rom = (TextView) romInfo.findViewById(R.id.rom_info);
//		InputStream is_rom = getResources().openRawResource(R.raw.ra_rominfo);
//		String rom_info = TxtReader.getString(is_rom);
//		textView_rom.setText(rom_info);
//	}
//	
//	/**
//	 * 设置新鲜事并存至TextView
//	 */
//	private void _setNewsTxt(View newsInfo){
//		textView_news = (TextView) newsInfo.findViewById(R.id.news_info);
//		InputStream is_news = getResources().openRawResource(R.raw.news);
//		String news = TxtReader.getString(is_news);
//		textView_news.setText(news);
//	}

	/**
	 * 初始化头标
	 */
	private void InitTextView() {
		textView_team = (TextView) findViewById(R.id.text_team);
		textView_rom = (TextView) findViewById(R.id.text_rom);
		textView_news = (TextView) findViewById(R.id.text_news);

		textView_team.setOnClickListener(new MyOnClickListener(0));
		textView_rom.setOnClickListener(new MyOnClickListener(1));
		textView_news.setOnClickListener(new MyOnClickListener(2));
	}

	/**
	 * 初始化ViewPager
	 */
	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		listViews = new ArrayList<View>();
		LayoutInflater mInflater = getLayoutInflater();
		View teamInfo = mInflater.inflate(R.layout.lay_team, null);
		WebView web_team = (WebView) teamInfo.findViewById(R.id.web_team);
		setWebView(web_team, "file:///android_asset/ra_teaminfo.html");
		//_setTeamTxt(teamInfo);
		listViews.add(teamInfo);
		View romInfo = mInflater.inflate(R.layout.lay_rom, null);
		WebView web_rom = (WebView) romInfo.findViewById(R.id.web_rom);
		setWebView(web_rom, "file:///android_asset/ra_rominfo.html");
		//_setRomTxt(romInfo);
		listViews.add(romInfo);
		View newsInfo = mInflater.inflate(R.layout.lay_news, null);
		WebView web_news = (WebView) newsInfo.findViewById(R.id.web_news);
		setWebView(web_news, "file:///android_asset/news.html");
		//_setNewsTxt(newsInfo);
		listViews.add(newsInfo);
		mPager.setAdapter(new MyPagerAdapter(listViews));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * 初始化动画
	 */
	private void InitImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.ic_slider)
				.getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置
	}

	/**
	 * ViewPager适配器
	 */
	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
	
	public void setWebView(WebView webview, String loadUrl){
		WebSettings webSettings = webview.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setSupportZoom(false);
        webview.loadUrl(loadUrl);//本地
	}
}