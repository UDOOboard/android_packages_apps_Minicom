package org.udoo.minicom;

import java.io.IOException;
import java.io.InputStream;

public class ReadThread extends Thread {

    private InputStream mInputStream;

    public ReadThread(InputStream is) {
        mInputStream = is;
    }

    @Override
    public void run() {
        super.run();
        while(!isInterrupted()) {
            int size;

            try {
                byte[] buffer = new byte[256];
                if (mInputStream == null) return;
                size = mInputStream.available();
                if (size > 0)  {
                    size = mInputStream.read(buffer);
                }
                if (size > 0) {
                    onDataReceived(buffer, size);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    protected void onDataReceived(final byte[] buffer, final int size) {

    }
}