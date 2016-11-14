package vvme.camerademo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Build;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by VV on 2016/11/11.
 */

public class MyCameraView extends SurfaceView implements SurfaceHolder.Callback, Camera.AutoFocusCallback {

    private Context mContext;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private int mScreenWidth;
    private int mScreenHeight;

    public MyCameraView(Context context) {
        this(context, null);
    }

    public MyCameraView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        getScreenMetric(mContext);
        initView();
    }

    private void initView() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.setKeepScreenOn(true);
//        setFocusable(true);
//        setBackgroundColor(TRIM_MEMORY_BACKGROUND);
        mSurfaceHolder.addCallback(this);
//        mSurfaceHolder.setType();
    }

    private void getScreenMetric(Context context) {
        WindowManager windowManger = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManger.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
        Log.i("hate", "屏幕宽和高:" + mScreenWidth + "/" + mScreenHeight);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initView();
        if (mCamera == null) {
            mCamera = Camera.open(1);
            setCameraParams(mCamera, mScreenWidth, mScreenHeight);
            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置摄像头各个参数
     *
     * @param camera
     * @param screenWidth
     * @param screenHeight
     */
    private void setCameraParams(Camera camera, int screenWidth, int screenHeight) {
        Camera.Parameters parameters = camera.getParameters();
        //获取相机支持的PictureSize列表
        List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes();
        //从列表中选取合适的分辨率
        Camera.Size size = getProperSize(pictureSizes, (float) screenWidth / screenHeight);
        if (size == null) {
            size = parameters.getPictureSize();
        }
        float w = size.width;
        float h = size.height;
        parameters.setPictureSize(size.width, size.height);
        this.setLayoutParams(new FrameLayout.LayoutParams((int) (screenHeight * (h / w)), screenHeight));

        //获取摄像头支持的PreviewSize列表
        List<Camera.Size> previewSize = parameters.getSupportedPictureSizes();
        Camera.Size preSize = getProperSize(previewSize, (float) screenWidth / screenHeight);
        if (preSize != null) {
            parameters.setPreviewSize(preSize.width, preSize.height);
        }

        //设置照片质量
        parameters.setJpegQuality(100);
        if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }
        setDisplay(parameters, mCamera);
        mCamera.cancelAutoFocus();//自动对焦
//        mCamera.setDisplayOrientation(90);//将捕捉到的画面旋转多少度 这里设置90°不严谨
        mCamera.setParameters(parameters);
    }

    /**
     * 获取支持的像素列表
     *
     * @param sizeList
     * @param screenRatio
     * @return
     */
    private Camera.Size getProperSize(List<Camera.Size> sizeList, float screenRatio) {
        Camera.Size resultSize = null;
        for (Camera.Size size : sizeList) {
            float currentRatio = (float) size.height / size.width;
            if (currentRatio - screenRatio == 0) {
                resultSize = size;
                break;
            }
        }

        if (resultSize == null) {
            for (Camera.Size size : sizeList) {
                float currentRatio = (float) size.height / size.width;
                if (currentRatio == 4f / 3) {
                    resultSize = size;
                    break;
                }
            }
        }
        return resultSize;

    }

    //控制图像的正确显示方向
    private void setDisplay(Camera.Parameters parameters, Camera camera) {
        if (Build.VERSION.SDK_INT >= 8) {
            setDisplayOrientation(camera, 90);
        } else {
            parameters.setRotation(90);
        }

    }

    //实现的图像的正确显示
    private void setDisplayOrientation(Camera camera, int i) {
        Method downPolymorphic;
        try {
            downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[]{int.class});
            if (downPolymorphic != null) {
                downPolymorphic.invoke(camera, new Object[]{i});
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();
//        mSurfaceHolder.removeCallback(this);
        mCamera = null;
//        mSurfaceHolder = null;
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if (success) {
            mCamera.cancelAutoFocus();//自动对焦
            setCameraParams(mCamera, mScreenWidth, mScreenHeight);
        }
    }

    public void takePicture() {
        setCameraParams(mCamera, mScreenWidth, mScreenHeight);
        mCamera.takePicture(null, null, jpeg);
    }

    private Camera.PictureCallback raw = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.i("hate", "图片没有压缩的方法回调");
        }
    };

    /**
     * 拍照瞬间方法回调
     */
    private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            Log.i("hate", "拍照瞬间调用");
        }
    };

    /**
     * 保存拍摄下来的照片
     */
    private Camera.PictureCallback jpeg = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            BufferedOutputStream bos = null;
            Bitmap bitmap = null;
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            //SDCard是否挂载
            try {
                if (Environment.isExternalStorageEmulated()) {
                    Log.i("hate", "存储目录==>" + Environment.getExternalStorageDirectory().getAbsolutePath());
                    String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/vvme";
                    Log.i("hate", "图片存储路径==>" + filePath);
                    File rootFile = new File(filePath);
                    if (!rootFile.exists()) {
                        rootFile.mkdir();
                    }
                    File file = new File(filePath + "/" + System.currentTimeMillis() + "vvme.jpg");
                    if (!file.exists()) {
                        file.createNewFile();
                        Log.i("hate", "文件不存在");
                    }
                    Log.i("hate", "文件位置:" + file.getAbsolutePath());
                    bos = new BufferedOutputStream(new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);//将图片压缩到流中
                } else {
                    Toast.makeText(mContext, "没有检测到SD卡", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bos != null) {
                        bos.flush();
                        bos.close();
                    }
                    bitmap.recycle();
                    mCamera.stopPreview();//关闭预览
                    mCamera.startPreview();//开启预览
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public Camera getCamera(){
        return mCamera;
    }


    public void setAutoFocus() {
        if (mCamera != null) {
            mCamera.autoFocus(this);
        }
    }
}
