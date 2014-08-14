package com.fsn.cauly.example;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyCloseAd;
import com.fsn.cauly.CaulyCloseAdListener;
import com.fsn.cauly.CaulyInterstitialAd;
import com.fsn.cauly.CaulyInterstitialAdListener;

public class JavaActivity extends Activity implements CaulyCloseAdListener {

	private static final String APP_CODE = "CAULY"; 

	CaulyCloseAd mCloseAd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_java);

		// CloseAd 초기화
		CaulyAdInfo closeAdInfo = new CaulyAdInfoBuilder(APP_CODE).build();
		mCloseAd = new CaulyCloseAd();

		/*
		 * Optional //원하는 버튼의 문구를 설젇 할 수 있다. mCloseAd.setButtonText("취소", "종료");
		 * //원하는 텍스트의 문구를 설젇 할 수 있다. mCloseAd.setDescriptionText("종료하시겠습니까?");
		 */
		mCloseAd.setAdInfo(closeAdInfo);
		mCloseAd.setCloseAdListener(this);

	}

	@Override
	protected void onResume() {
		if(mCloseAd!=null)
			mCloseAd.resume(this);
		super.onResume();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 앱을 처음 설치하여 실행할 때, 필요한 리소스를 다운받았는지 여부.
			if (mCloseAd!=null&&mCloseAd.isModuleLoaded())
			{
				mCloseAd.show(this);
			} 
			else
			{
				// 광고에 필요한 리소스를 한번만  다운받는데 실패했을 때 앱의 종료팝업 구현
				showDefaultClosePopup();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showDefaultClosePopup()
	{
		new AlertDialog.Builder(this).setTitle("").setMessage("종료 하시겠습니까?")
		   .setPositiveButton("예", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		     finish();
		    }
		   })
		   .setNegativeButton("아니요",null)
		   .show();
	}
	
	
	// CaulyCloseAdListener

	@Override
	public void onFailedToReceiveCloseAd(CaulyCloseAd ad, int errCode ,String errMsg) {

	}

	// CloseAd의 광고를 클릭하여 앱을 벗어났을 경우 호출되는 함수이다.
	@Override
	public void onLeaveCloseAd(CaulyCloseAd ad) {
	}

	// CloseAd의 request()를 호출했을 때, 광고의 여부를 알려주는 함수이다.
	@Override
	public void onReceiveCloseAd(CaulyCloseAd ad, boolean isChargable) {

	}

	// 왼쪽 버튼을 클릭 하였을 때, 원하는 작업을 수행하면 된다.
	@Override
	public void onLeftClicked(CaulyCloseAd ad) {

	}

	// 오른쪽 버튼을 클릭 하였을 때, 원하는 작업을 수행하면 된다.
	// Default로는 오른쪽 버튼이 종료로 설정되어있다.
	@Override
	public void onRightClicked(CaulyCloseAd ad) {
		finish();
	}

	@Override
	public void onShowedCloseAd(CaulyCloseAd ad, boolean isChargable) {

	}

}
