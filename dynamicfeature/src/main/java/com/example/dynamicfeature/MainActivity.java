package com.example.dynamicfeature;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
  StringBuilder mLogs = new StringBuilder();

  public void log(String s) {
    Log.i("IsolatedSplits", s);
    mLogs.append("\n").append(s);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Copy logs via intent rather than a static since statics are ClassLoader-specific.
    if (getIntent().hasExtra("logs")) {
      mLogs.append(getIntent().getStringExtra("logs"));
    }

    log(String.format("MainActivity.class = %x", System.identityHashCode(MainActivity.class)));
    log(String.format("MainActivity.class.getClassLoader() = %x", System.identityHashCode(MainActivity.class.getClassLoader())));
    log(String.format("Thread.currentThread().getContextClassLoader() = %x", System.identityHashCode(Thread.currentThread().getContextClassLoader())));

    setContentView(com.example.myapplication.R.layout.activity_main);
    View btn = findViewById(com.example.myapplication.R.id.button);
    btn.setOnClickListener(X -> {
      log("Restarting activity");
      Intent intent = new Intent(this, MainActivity.class);
      intent.putExtra("logs", mLogs.toString());
      startActivity(intent);
    });
    TextView t = findViewById(com.example.myapplication.R.id.logs);
    t.setText(mLogs.toString());
  }
}