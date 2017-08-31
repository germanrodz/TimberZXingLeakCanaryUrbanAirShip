package com.blovvme.timberzxingleakcanaryurbanairship;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;
import com.squareup.leakcanary.RefWatcher;
import com.urbanairship.UAirship;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private ZXingScannerView scannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.v("Log");
        onTimber();

        UAirship.shared().getPushManager().setUserNotificationsEnabled(true);
        //new MyAsyncTask().execute(this);
    }

    public void scanCode(View view) {

        scannerView = new ZXingScannerView(this);
        scannerView.setResultHandler(new ZXingScannerResultHandler());

        setContentView(scannerView);
        scannerView.startCamera();
    }


    @Override
    public void onPause() {
        super.onPause();
        if (scannerView != null) {
            scannerView.stopCamera();
        }
    }

    public void onLeak(View view) {
        new MyAsyncTask().execute(this);
    }


    class ZXingScannerResultHandler implements ZXingScannerView.ResultHandler {


        @Override
        public void handleResult(Result result) {
            String resultCode = result.getText();
            Toast.makeText(MainActivity.this, resultCode, Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_main);
            scannerView.stopCamera();
            Timber.d("Hello");
        }
    }


    //////////////
    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }

    public class MyAsyncTask extends AsyncTask<Object, String, String> {
        private Context context;

        @Override
        protected String doInBackground(Object... params) {
            context = (Context) params[0];

            // Invoke the leak!
            SingletonSavesContext.getInstance().setContext(context);

            // Simulate long running task
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }

            return "result";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Intent newActivity = new Intent(context, AnotherActivity.class);
            startActivity(newActivity);
        }
    }

    private void onTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                //Add the line number to the tag
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + ":" + element.getLineNumber();
                }
            });

        }


    }
}