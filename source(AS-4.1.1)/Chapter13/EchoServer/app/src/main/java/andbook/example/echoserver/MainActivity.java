package andbook.example.echoserver;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private ServerThread mServerThread; // 서버 통신 담당
    private TextView mTextOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextOutput = (TextView) findViewById(R.id.textOutput);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
        case R.id.btnStart:
            if (mServerThread == null) {
                mServerThread = new ServerThread(this, mMainHandler);
                mServerThread.start();
            }
            break;
        case R.id.btnQuit:
            finish();
            break;
        }
    }

    private Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case 1: // 화면에 데이터 출력
                mTextOutput.append((String) msg.obj);
                break;
            }
        }
    };
}

class ServerThread extends Thread {

    private Context mContext;
    private Handler mMainHandler;

    public ServerThread(Context context, Handler mainHandler) {
        mContext = context;
        mMainHandler = mainHandler;
    }

    @Override
    public void run() {
        ServerSocket servSock = null;
        try {
            // 서버 소켓을 초기화한다.
            servSock = new ServerSocket(9000);
            // 서버의 IP 주소와 포트 번호를 출력한다.
            WifiManager wifiMgr = (WifiManager) mContext.getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
            int serverip = wifiMgr.isWifiEnabled() ? wifiInfo.getIpAddress() : 0x0100007F;
            doPrintln(">> 서버 시작! " + ipv4ToString(serverip) + "/" + servSock.getLocalPort());

            while (true) {
                // 클라이언트 접속을 기다린다.
                Socket sock = servSock.accept();
                // 접속한 클라이언트 정보를 출력한다.
                String ip = sock.getInetAddress().getHostAddress();
                int port = sock.getPort();
                doPrintln(">> 클라이언트 접속: " + ip + "/" + port);
                // 접속한 클라이언트와 통신한다.
                InputStream in = sock.getInputStream();
                OutputStream out = sock.getOutputStream();
                // 클라이언트와 데이터를 주고받는다.
                byte[] buf = new byte[1024];
                while (true) {
                    try {
                        // 데이터 수신
                        int nbytes = in.read(buf);
                        if (nbytes > 0) {
                            // 데이터 출력
                            String s = new String(buf, 0, nbytes);
                            doPrintln("[" + ip + "/" + port + "] " + s);
                            // 데이터 송신
                            out.write(buf, 0, nbytes);
                        } else {
                            // 정상 종료
                            doPrintln(">> 클라이언트 종료: " + ip + "/" + port);
                            break;
                        }
                    } catch (IOException e) {
                        // 강제 종료
                        doPrintln(">> 클라이언트 종료: " + ip + "/" + port);
                        break;
                    }
                } // end of inner while-loop
                sock.close();
            } // end of outer while-loop
        } catch (IOException e) {
            doPrintln(e.getMessage());
        } finally {
            try {
                if (servSock != null) {
                    servSock.close();
                }
                doPrintln(">> 서버 종료!");
            } catch (IOException e) {
                doPrintln(e.getMessage());
            }
        }
    } // end of run()

    private void doPrintln(String str) {
        Message msg = Message.obtain();
        msg.what = 1;
        msg.obj = str + "\n";
        mMainHandler.sendMessage(msg);
    }

    private String ipv4ToString(int ip) {
        int a = (ip) & 0xFF, b = (ip >> 8) & 0xFF;
        int c = (ip >> 16) & 0xFF, d = (ip >> 24) & 0xFF;
        return Integer.toString(a) + "." + Integer.toString(b) + "."
                + Integer.toString(c) + "." + Integer.toString(d);
    }
}
