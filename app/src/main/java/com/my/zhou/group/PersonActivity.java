package com.my.zhou.group;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.my.zhou.group.adapter.CameraAdapter;
import com.my.zhou.group.adapter.PersonSpinnerAdapter;
import com.my.zhou.group.bean.Region;
import com.my.zhou.group.constant.HttpUrl;
import com.my.zhou.group.data.GetRegionData;
import com.my.zhou.group.data.IRegionInterface;
import com.my.zhou.group.utils.BitmapUtils_My;
import com.my.zhou.group.utils.PhotoUploadTask;
import com.my.zhou.group.utils.ToPhotoOrGallery;
import com.my.zhou.group.view.DrawableCenterButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class PersonActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, IRegionInterface {
    public static final int PROVICE = 0;
    public static final int CITY = 1;
    public static final int REGRON = 2;
    @Bind(R.id.image_person_back)
    ImageView imageView_back;
    @Bind(R.id.text_title)
    TextView textView_title;
    @Bind(R.id.edit_person_username)
    EditText edit_person_username;
    @Bind(R.id.edit_person_number)
    EditText edit_person_number;
    @Bind(R.id.edit_person_district)
    EditText edit_person_district;
    @Bind(R.id.spinner_person_city)
    Spinner spinner_person_city;
    @Bind(R.id.spinner_person_region)
    Spinner spinner_person_region;
    @Bind(R.id.drawablebtn_person)
    DrawableCenterButton drawablebtn_person;
    @Bind(R.id.griview_person_camera)
    GridView griview_person_camera;
    @Bind(R.id.image_person_photo)
    ImageView image_person_photo;
    @Bind(R.id.spinner_person_province)
    Spinner spinner_person_province;
    @Bind(R.id.text_spinner_province)
    TextView text_spinner_province;
    @Bind(R.id.image_tu)
    ImageView image_tu;
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private static final int ALBUM_REQUEST_CODE = 3;
    private static final int CAMERA_REQUEST_CODE = 4;
    private static final int CROP_REQUEST_CODE = 5;

    private static final int TOUXIANG = 1;
    private static final int SHENFEN = 2;
    private int type;
    private LinearLayout shenqing, shenqingOk;

    public PersonActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        regionDatas("-1", PROVICE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter();


    }

    private List<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private CameraAdapter adapter;

    private void adapter() {
        Bitmap b = null;
        if (bitmaps.size() <= 0) {
            bitmaps.add(b);
        } else {
            Log.i("bitmaps", "" + bitmaps);
        }
        adapter = new CameraAdapter(this, bitmaps);
        griview_person_camera.setAdapter(adapter);

        setListener();

    }


    private void setListener() {
        imageView_back.setOnClickListener(this);
        griview_person_camera.setOnItemClickListener(this);


        image_tu.setOnClickListener(this);
        // spinner_person_province.setOnItemSelectedListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_person_back:
                finish();
                break;
            case R.id.image_tu:
                showPhotoPopupWindow(v);
                // photo(v);
                break;

        }
    }

    /**
     * 头像的获取
     */
    private void showPhotoPopupWindow(View view) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View contentView = inflater.inflate(R.layout.popwindow_headimage, null);
        // PopupWindow宽高设置
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // PopupWindow底部居中显示
        popupWindow.showAtLocation(contentView,
                Gravity.CENTER, 0, 0);
        //设置背景色
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x55000000));
        // 透明度，将主窗口透明
        // 记得关闭窗口的时候要设置回来哦!
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                             @Override
                                             public void onDismiss() {
                                                 WindowManager.LayoutParams lp = getWindow().getAttributes();
                                                 lp.alpha = 1.0f;
                                                 getWindow().setAttributes(lp);
                                             }
                                         }

        );
        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });
        popupWindow.showAsDropDown(view);
        // 拍照
        ImageView imageCamera = (ImageView) contentView.findViewById(R.id.image_selector_camera);
        imageCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                camera(null);
                popupWindow.dismiss();
            }
        });
        // 从相册选择
        ImageView frompictureBtn = (ImageView) contentView
                .findViewById(R.id.image_selector_album);
        frompictureBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gallery(null);
                popupWindow.dismiss();
            }
        });
        contentView.setFocusable(true);//comment by danielinbiti,设置view能够接听事件，标注1
        contentView.setFocusableInTouchMode(true); //comment by danielinbiti,设置view能够接听事件 标注2
        contentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                }
                return false;
            }
        });

    }

    /**
     * 从相册获取
     *
     * @param view
     */
    public void gallery(View view) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
        intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    /**
     * 从相机获取
     *
     * @param view
     */
    public void camera(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (file == null) {
                file = new File(Environment.
                        getExternalStorageDirectory(), "temp.jpg");
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(intent, ALBUM_REQUEST_CODE);
        } else {
            Toast.makeText(this, "请检查内存卡是否存在", Toast.LENGTH_SHORT).show();
        }
    }

    /*
        *判断SD卡是否可用
        */
    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 裁剪图片
     *
     * @param uri
     */
    private void startCrop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");//调用Android系统自带的一个图片剪裁页面,
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//进行修剪
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    /**
     * 修改个人信息
     */

    //private  String uid = login.get(0).getUid();
    private String uid;
    private static final int CHUSHI = 0;
    private static final int ZUIHOU = 1;
    int types;
    private String province_id;
    private String city_id;
    private String district;

    private void changeOk() {
        //判断uid，如果存了，根据存的uid直接调接口获取用户资料
        SharedPreferences sharedPre = getSharedPreferences("group", MODE_PRIVATE);
        uid = sharedPre.getString("uid", "nouid");
        //身份证图片路径
        String path = "";
        for (int i = 0; i < datas.size(); i++) {
            path += datas.get(i) + ",";
        }
        Log.i("TAG", "PATH=" + path);
        Log.i("TAG", "PATH1111=" + datas1);
        Log.i("TAG", "UID=" + uid);
        String user_name = edit_person_username.getText().toString();
        String mobile = edit_person_number.getText().toString();

        String detail_address = edit_person_district.getText().toString();

        RequestParams params = new RequestParams(HttpUrl.UPDATA);
        params.addBodyParameter("id", uid);
        params.addBodyParameter("user_name", user_name);
        params.addBodyParameter("mobile", mobile);
        // 省  市  区(ID)
        params.addBodyParameter("province_id", province_id);
        Log.i("TAG", "JJJJJJJJJJJJ===" + province_id + "城市" + city_id + "县城" + district);
        params.addBodyParameter("city_id", city_id);
        params.addBodyParameter("district", district);

        params.addBodyParameter("detail_address", detail_address);
        //身份证
        params.addBodyParameter("e_idcard", path);
        //人物头像
        params.addBodyParameter("e_head_photo", datas1);
        //Log.i("测试错误",params.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    Log.i("TAG", "RESULT==" + result);
                    if (object.getInt("retcode") == 2000) {
                        Toast.makeText(PersonActivity.this, "" + object.getString("msg"), Toast.LENGTH_SHORT).show();
                        JSONObject data = object.getJSONObject("data");
                        String islock = data.getString("is_lock");
                        Log.i("TAG", "Person_lock=" + islock);

                        //获取SharedPreferences对象
                        SharedPreferences sharedPre = getSharedPreferences("lock1", MODE_PRIVATE);
                        //获取Editor对象
                        SharedPreferences.Editor editor = sharedPre.edit();
                        //设置参数
                        editor.putString("islock1", data.getString("is_lock"));
                        //提交
                        editor.commit();


                        finish();
                    } else {
                        Toast.makeText(PersonActivity.this, "" + object.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == bitmaps.size() - 1) {
            if (bitmaps.size() < 3) {
                //调用系统相机拍照或读取相册
                photo(view);
                //showPhotoPopupWindow(view);
                view.showContextMenu();
            } else {
                Toast.makeText(PersonActivity.this, "最多上传二张图片", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void photo(View view) {
        //创建上下文菜单并实现监听
        view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            //menu 创建菜单项 v 表示长按的控件
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 1, 0, "拍照");
                menu.add(0, 2, 0, "相册");
            }
        });
        //显示上下文菜单
        view.showContextMenu();

    }

    /**
     * 重写方法，对contextMenu中的菜单项点击事件响应
     *
     * @param item 菜单选项
     * @return
     */
    private File file;//照相文件

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //如果菜单项布局的ID为0
        if (item.getGroupId() == 0) {
            //对item的ID判断
            switch (item.getItemId()) {
                case 1://拍照
                    if (file != null) {
                        //清空文件
                        file.delete();
                    }
                    //回调ToPhotoOrGallery的takephoto方法，调用摄像头拍照，将数据存入文件中
                    file = ToPhotoOrGallery.takePhotos(this, new ToPhotoOrGallery.Callback() {
                        //跳转到拍照界面
                        @Override
                        public void callBack(Intent in) {
                            //requestcode大于0  修改标准的行为允许交付结果片段
                            //打开新的Activity，起标识符作用，标识请求来源，可自由设定
                            startActivityForResult(in, 1);
                        }
                    });
                    break;

                case 2://图库
                    Intent intent = ToPhotoOrGallery.gallery(this);
                    startActivityForResult(intent, 2);
                    break;
            }
        }
        return super.onContextItemSelected(item);
    }


    /**
     * 接收新Activity结束时传回的数据
     *
     * @param requestCode 前边方法传入的标识符
     * @param resultCode
     * @param data        当新打开的Activity结束时，传出的数据，通过intent传递
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("TAG", "TAGTAG=" + data);
        //requestCode==1为拍照，并且文件不为空,则调用内部类方法并将文件传递过去
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && file.length() > 0) {
                new AsyTast().execute(Uri.fromFile(file));
            }
        } else
            ////requestCode==2为图库，并且数据不为空，则调用内部类方法将数据传递过去
            if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
                new AsyTast().execute(data.getData());
            } else if (requestCode == 4 && resultCode == RESULT_OK) {
                startCrop(data.getData());

            } else if (requestCode == 3 && resultCode == RESULT_OK) {
                File picture = new File(Environment.getExternalStorageDirectory()
                        + "/temp.jpg");
                startCrop(Uri.fromFile(picture));

            } else if (requestCode == 5 && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                if (extras != null) {

                    Bitmap photo = extras.getParcelable("data");
                    Log.i("TAG", "Photo=" + photo);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0-100)压缩文件
                    //此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
                    image_person_photo.setVisibility(View.VISIBLE);
                    image_tu.setVisibility(View.GONE);
                    image_person_photo.setImageBitmap(photo); //把图片显示在ImageView控件上
                    // 获得字节流
                    ByteArrayInputStream is = new ByteArrayInputStream(stream.toByteArray());
                    PhotoUploadTask put = new PhotoUploadTask(HttpUrl.FILEUPLOAD, is,
                            this, handler, TOUXIANG);
                    put.start();
                }
            }


    }

    /**
     * spinner(地区选择)
     * 监听
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_person_province:
                Log.i("chengshiming", listRegion.get(position).getName());

                province_id = listRegion.get(position).getId();
                regionDatas(listRegion.get(position).getId(), CITY);
                break;
            case R.id.spinner_person_city:
                city_id = listRegion1.get(position).getId();
                regionDatas(listRegion1.get(position).getId(), REGRON);
                break;
            case R.id.spinner_person_region:
                district = listRegion2.get(position).getId();
                break;
        }
        //添加数据
//        String str = parent.getItemAtPosition(position).toString();
//        Toast.makeText(PersonActivity.this, "你点击的是:" + str, Toast.LENGTH_LONG).show();
//          i = listRegion.get(position);
//          regionDatas(i);
        //ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
        //text_spinner_province.setText(adapter.getItem(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private GetRegionData region;

    private void regionDatas(String i, int number) {

        if (region == null) {
            region = new GetRegionData(this);
        }
        region.setRegionsNet(i, number);
    }


    /**
     * 实现接口方法，接收数据
     *
     * @param list
     */
    private List<Region> listRegion = new ArrayList<Region>();
    private List<Region> listRegion1 = new ArrayList<Region>();
    private List<Region> listRegion2 = new ArrayList<Region>();
    PersonSpinnerAdapter adapters;
    PersonSpinnerAdapter adapter1;
    PersonSpinnerAdapter adapter2;

    @Override
    public void setRegion(List<Region> list, int number) {

        switch (number) {
            case PROVICE://省数据
                listRegion.clear();
                listRegion.addAll(list);
                Log.i("TAG", "listRegion=" + listRegion);
//                for (int i = 0;i<list.size();i++){
//                    listRegion.add(list.get(i).getName());
//                    Log.i("TAG", "List==" + listRegion);
//                }
//                Log.i("TAG", "List==" + list);
                regionDatas(listRegion.get(0).getId(), CITY);
                if (adapters == null) {
                    adapters = new PersonSpinnerAdapter(PersonActivity.this, listRegion);
                    spinner_person_province.setAdapter(adapters);
                    spinner_person_province.setOnItemSelectedListener(this);
                } else {
                    adapters.notifyDataSetChanged();
                }
                province_id = listRegion.get(0).getId();
                break;
            case CITY://市数据
                listRegion1.clear();
                listRegion1.addAll(list);
                regionDatas(listRegion1.get(0).getId(), REGRON);
                Log.i("chengshiming", listRegion1.get(0).getName());
                if (adapter1 == null) {
                    adapter1 = new PersonSpinnerAdapter(PersonActivity.this, listRegion1);
                    spinner_person_city.setAdapter(adapter1);
                    spinner_person_city.setOnItemSelectedListener(this);
                } else {
                    adapter1.notifyDataSetChanged();
                }
                city_id = listRegion1.get(0).getId();
                break;
            case REGRON://县数据
                listRegion2.clear();
                listRegion2.addAll(list);
                if (adapter2 == null) {
                    adapter2 = new PersonSpinnerAdapter(PersonActivity.this, listRegion2);
                    spinner_person_region.setAdapter(adapter2);
                    spinner_person_region.setOnItemSelectedListener(this);
                } else {
                    adapter2.notifyDataSetChanged();
                }

                district = listRegion2.get(0).getId();
                break;
        }

    }

    /**
     * 创建内部类，并重写方法
     * 继承轻量级异步类，执行上传及加载任务
     * uri为传入的图片数据
     * Bitmap
     * 字节数组输入流，返回的结果
     */
    private class AsyTast extends AsyncTask<Uri, Bitmap, ByteArrayInputStream> {

        /**
         * 执行耗时操作，操作图片
         *
         * @param params
         * @return
         */
        @Override
        protected ByteArrayInputStream doInBackground(Uri... params) {
            try {
                Bitmap bitmapFormUri = BitmapUtils_My.getBitmapFormUri(PersonActivity.this, params[0]);
                int degree = BitmapUtils_My.getBitmapDegree(params[0].getPath());
                //newbitmap 为处理后的图片对象
                Bitmap newbitmap = BitmapUtils_My.rotateBitmapByDegree(bitmapFormUri, degree);
                publishProgress(newbitmap);//更新任务进度
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                newbitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);//转换为字节数组输出流
                ByteArrayInputStream is = new ByteArrayInputStream(baos.toByteArray());
                baos.close();
                return is;//返回字节流
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * 操作UI，在主线程执行
         * 上传图片到服务器
         *
         * @param byteArrayInputStream doInBackground方法返回的结果
         */
        @Override
        protected void onPostExecute(ByteArrayInputStream byteArrayInputStream) {
            super.onPostExecute(byteArrayInputStream);
            //如果返回结果不为null
            if (byteArrayInputStream != null) {
                //开启子线程上传图片，参数（上传图片的接口，上传的数据，对象，消息，ID）

                PhotoUploadTask put = new PhotoUploadTask(
                        HttpUrl.FILEUPLOAD, byteArrayInputStream,
                        PersonActivity.this, handler, SHENFEN);
                put.start();//开启线程
            } else {
                //  To.oo("图片上传网络失败，请稍后重新选择图片");
                //  layout.setRefreshing(false);
            }
        }

        /**
         * 此方法在主线程执行，可以显示操作的进度
         * 将图片显示到控件
         *
         * @param values 指示的进度,存储bitmap对象
         *               在UI线程上运行后调用后台任务的更新进度值
         *               publishProgress处理后的bitmap对象的值传递过来
         */
        @Override
        protected void onProgressUpdate(Bitmap... values) {
            super.onProgressUpdate(values);
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    Bitmap bitmap = values[i];
                    bitmaps.add(bitmaps.size() - 1, bitmap);

                }
                //执行适配器方法
                adapter();
            }

        }

        /**
         * 当任务执行之前开始调用此方法，可以在这里显示进度对话框。
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //内部类结束
    }

    /**
     * 获取返回的图片路径
     */
    private ArrayList<String> datas = new ArrayList<String>();
    private String datas1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 152) {
                //152为自由设定，表示上传成功
                String s = (String) msg.obj;//obj为装载路径的字符串数组

                switch (msg.arg1) {
                    case TOUXIANG:
                        try {
                            JSONObject object = new JSONObject(s);
                            datas1 = object.getString("data");//图片的路径
                            Log.i("TAG", "YYY===" + datas1);
                            Intent intent = new Intent();
                            intent.putExtra("url", datas1);
                            setResult(RESULT_OK, intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case SHENFEN:
                        try {

                            JSONObject object = new JSONObject(s);
                            String data = object.getString("data");//图片的路径
                            Log.i("TAG", "路径=" + data);
                            datas.add(data);
                            Log.i("TAG", "路径路径=" + datas);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                setclick();
            }
        }
    };

    /**
     * 对提交按钮设置监听
     */
    private void setclick() {

        drawablebtn_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeOk();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

