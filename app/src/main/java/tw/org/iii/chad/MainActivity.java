package tw.org.iii.chad;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private EditText userinput;
    private TextView textArea;
    private Button click,setting,restart,exit;
    private String anwser;
    private int number;
    private int allTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userinput=findViewById(R.id.userinput);
        textArea=findViewById(R.id.textArea);
        click =findViewById(R.id.guess);
        exit=findViewById(R.id.exit);
        setting=findViewById(R.id.setting);
        restart=findViewById(R.id.restart);
        number=3;
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doguess();
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initGame();
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSetting();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("test","開始");
        textArea.setText("");
        userinput.setText("");
        anwser =createAnwser(number);
        allTimes=0;
    }
    private void initGame(){
        onStart();
    }
    public void doguess() {
        Log.v("test","OK");
        String foo =userinput.getText().toString();
        if(allTimes>=10){
            showEnd();
        }else if(foo.length()==number&&foo.matches("[0-9]+")&&checkNumber(foo)) {
                userinput.setText("");
                String ab = checkAB(anwser, foo);
                if (ab.equals(number + "A0B")) {
                    showDialog();
                } else {
                    allTimes++;
                    textArea.append(" 輸入: " + foo + " AB:" + ab + '\n');
                }
            }else {
                showError();
            }
    }

    private void showEnd() {
        AlertDialog.Builder bulider = new AlertDialog.Builder(this);
        bulider.setTitle("失敗");
        bulider.setCancelable(false);
        bulider.setMessage("正確答案"+anwser);
        bulider.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    initGame();
            }
        });
        bulider.create().show();
    }

    public void end(View view) {
        finish();
    }

    private boolean checkNumber(String foo){
        for(int i=0;i<foo.length();i++){
            for(int z=i+1;z<foo.length();z++) {
                if (foo.charAt(i % foo.length()) == foo.charAt((i + z) % foo.length()))
                    return false;
            }
        }
        return true;
    };
    private void showSetting(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage("請輸入3~9");
        builder.setTitle("設定答案長度");
        final EditText foo =new EditText(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String bar =foo.getText().toString();
                if(bar.matches("[2-9]")&&bar.length()==1) {
                    number = Integer.parseInt(bar);
                    initGame();
                }else{
                    showSetting();
                }
            }
        });
        AlertDialog alertdialog =builder.create();
        alertdialog.setView(foo);
        alertdialog.show();
    }
    private  void showError(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage("請輸入不重複的數字"+number+"碼");
        builder.setTitle("輸入錯誤");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v("test","OK");
            }
        });
        AlertDialog alertdialog =builder.create();
        alertdialog.show();
        alertdialog.setCancelable(false);
    }
    private void showDialog(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage("正確");
        builder.setTitle("WINNER");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                initGame();
            }
        });
        AlertDialog alertdialog =builder.create();
        alertdialog.show();
    }
    private String createAnwser(int i){
        HashSet<String> anwser =new HashSet<String>();
        while(anwser.size()<i){
            String bar =((int)(Math.random()*10))+"";
            anwser.add(bar);
        }
        StringBuffer sb= new StringBuffer();
        Iterator<String> temp = anwser.iterator();
        while(temp.hasNext()){
            sb.append(temp.next());
        }
        return sb.toString();
    }
    private static String checkAB(String anwser,String usrInput){
        int A, B; A = B = 0;

        for (int i=0 ;i<anwser.length(); i++) {
            if (usrInput.charAt(i) == anwser.charAt(i)) {
                A++;
            }else if (anwser.indexOf(usrInput.charAt(i)) != -1) {
                B++;
            }
        }
        return A + "A" + B +"B";
    }
}
