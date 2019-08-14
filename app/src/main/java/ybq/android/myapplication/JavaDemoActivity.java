package ybq.android.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JavaDemoActivity extends AppCompatActivity {

    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Button btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(BuildConfig.VERSION_CODE < 2){
                    update(view);
                }
                startActivity(new Intent(JavaDemoActivity.this,SecondActivity.class));
            }
        });

        mContext = this;
        ApkExtract.extract(this);

    }

    private void update(View v) {
    }
}
