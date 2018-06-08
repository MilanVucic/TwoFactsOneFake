package com.example.nebojsa.twofactsonefake;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Nebojsa on 12/6/2015.
 */
class GetTask extends AsyncTask<Object, Void, String> {
    Context context;
    ProgressDialog mDialog;

    GetTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mDialog = new ProgressDialog(context);
        mDialog.setMessage("Please wait...");
        mDialog.show();
    }

    @Override
    protected String doInBackground(Object... params) {
        return "Doing stuff";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        mDialog.dismiss();
    }
}
