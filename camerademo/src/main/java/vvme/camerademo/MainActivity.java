package vvme.camerademo;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements RectView.IAutoFocus {

    private MyCameraView mMyCameraView;
    private Button mBtnTakePhoto;
    private RectView mRectView;
    private int cameraPosition = 0;//1代表前置摄像头，0代表后置摄像头
    private Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mMyCameraView = (MyCameraView) findViewById(R.id.myCamera);
        mBtnTakePhoto = (Button) findViewById(R.id.btn_takePhoto);
        mRectView = (RectView) findViewById(R.id.rectView);
        mRectView.setAutoFocusListener(this);
        initCamera();
    }

    public void takePhoto(View view) {
        mBtnTakePhoto.setEnabled(false);
        mMyCameraView.takePicture();
        mBtnTakePhoto.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBtnTakePhoto.setEnabled(true);
            }
        }, 500);

    }

   /* public void exchangeCamera(View view) {
        if (cameraPosition == 0) {
            mCamera = Camera.open(1);
            mMyCameraView.exchangeCamera(mCamera);
            cameraPosition = 1;
        } else {
            mCamera = Camera.open(0);
            mMyCameraView.exchangeCamera(mCamera);
            cameraPosition = 0;
        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initCamera() {
        mCamera = mMyCameraView.getCamera();
    }

    @Override
    public void autoFocus() {
        mMyCameraView.setAutoFocus();
    }

    @Override
    protected void onDestroy() {
        mMyCameraView = null;
        System.gc();
        super.onDestroy();
    }
}
