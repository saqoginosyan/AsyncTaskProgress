package com.example.sargis.progressasynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private EditText sleepTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sleepTime = findViewById(R.id.input_text);
        final Button startButton = findViewById(R.id.start_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressAsyncTask progressAsyncTask = new ProgressAsyncTask(MainActivity.this);
                final String time = sleepTime.getText().toString();
                if (!sleepTime.getText().toString().isEmpty()) {
                    progressAsyncTask.execute(time);
                } else {
                    final Snackbar snackbar = Snackbar
                            .make(v, "Please enter sleep time", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }

    private static class ProgressAsyncTask extends AsyncTask<String, Void, Void> {
        final WeakReference<MainActivity> weakReference;
        private ProgressDialog progressDialog;

        ProgressAsyncTask(MainActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        private MainActivity getReference() {
            return weakReference.get();
        }

        @Override
        protected Void doInBackground(String... strings) {
            final int time = Integer.parseInt(strings[0]) * 1000;

            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getReference(),
                    "ProgressDialog",
                    "Wait for " + getReference().sleepTime.getText().toString() + " seconds");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
        }
    }
}
