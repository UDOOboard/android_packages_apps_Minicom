package org.udoo.minicom;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    protected FileDescriptor mFd;
    private ReadThread mReadThread;
    protected SerialPort mSerialPort;
    protected OutputStream mOutputStream;
    private InputStream mInputStream;
    private EditText tx;
    private TextView rx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tx = (EditText)findViewById(R.id.txText);
        rx = (TextView)findViewById(R.id.rxText);

        openSerialPort();

        mReadThread = new ReadThread(mInputStream) {
            @Override
            protected void onDataReceived(final byte[] buffer, final int size) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        rx.append(new String(buffer));
                    }
                });
            }
        };
        mReadThread.start();
    }

    @Override
    protected void onDestroy() {
        if (mReadThread != null) {
            mReadThread.interrupt();
        }
        if (mSerialPort != null) {
            closeSerialPort();
        }
        super.onDestroy();
    }

    public void openSerialPort() {
        try {
            mSerialPort = new SerialPort("/dev/ttyMCC", 115200, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mOutputStream = mSerialPort.getOutputStream();
        mInputStream = mSerialPort.getInputStream();
    }

    public void closeSerialPort() {
        mSerialPort.close();
        mSerialPort = null;
    }

    public void sendMessage(View view) {
        String msg = tx.getText().toString();
        Log.d("Minicom", msg);

        try {
            mOutputStream.write(msg.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
