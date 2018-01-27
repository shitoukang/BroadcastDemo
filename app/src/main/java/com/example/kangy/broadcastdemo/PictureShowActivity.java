package com.example.kangy.broadcastdemo;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class PictureShowActivity extends AppCompatActivity {

    public URL mURL;
    //获取sd卡根目录
    String SDcardPath = Environment.getExternalStorageDirectory()+"/";
    //设置缓存目录
    String catchPath = SDcardPath+"pictureCatch/";
    private Button mButton;
    private ImageView mImageView;
    private EditText mEditText;
    HashMap<String,String> maps = new HashMap<>();
    //新建File对象
    File mFile;
    //创建一个urlStirng，这个是用户通过EditText输入框输入的
    String urlString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_show);
        mImageView = findViewById(R.id.image_view);
        mEditText = findViewById(R.id.edit_text);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlString = mEditText.getText().toString();
                if(urlString!=null&&urlString.length()>0)
                {
                    urlString =(urlString.startsWith("http://"))?urlString:"http://"+urlString;

                }
                if(maps!=null&&maps.containsKey(urlString))
                {
                    String catchFile = maps.get(urlString).toString();
                    if(checkFileExists(catchFile))
                    {
                        Drawable d = Drawable.createFromPath(catchFile);
                        mImageView.setImageDrawable(d);
                    }
                    else
                    {
                    }
                }

            }
        });


    }
    //判断文件是否存在
    public boolean checkFileExists(String filepath)
    {
        File file = new File(filepath);
        return file.exists();
    }
    //创建一个下载文件的方法
    public void downFile(String url)
    {
        new DownLoadFileTask().execute(url);
    }
    //创建一个文件下载的具体实施类，使用的是异步任务处理机制
    public class DownLoadFileTask extends AsyncTask<String,Integer,Integer>
    {
        @Override
        protected Integer doInBackground(String... sUrl) {
            try {
                String urlString = sUrl[0];
                mURL = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection)mURL.openConnection();
                InputStream stream = connection.getInputStream();
                String fileName = urlString.substring(urlString.lastIndexOf("/")+1);
                File dir = new File(catchPath);
                if(!dir.exists())
                {
                    dir.mkdir();
                }
                mFile = new File(catchPath+fileName);
                mFile.createNewFile();
                //读取信息到文件中
                OutputStream outputStream = new FileOutputStream(mFile);
                byte[] buffer = new byte[1024];
                while (stream.read(buffer)!=-1)
                {
                    outputStream.write(buffer);
                }
                outputStream.flush();
                outputStream.close();
                stream.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return -1;
            }
            return 1;


        }

        @Override
        protected void onPostExecute(Integer integer) {
            if(integer>0)
            {
                maps.put(urlString,mFile.getPath());
                Drawable d = Drawable.createFromPath(mFile.getPath());
                mImageView.setBackgroundDrawable(d);
            }
        }
    }
}
