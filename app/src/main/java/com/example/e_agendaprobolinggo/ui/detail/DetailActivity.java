package com.example.e_agendaprobolinggo.ui.detail;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;
import com.example.e_agendaprobolinggo.model.response.DataAgenda;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    public static final String KEY = "key";
    private DetailContract.Presenter mPresenter;
    private TextView tvNameAgenda, tvCategoryAgenda, tvPlaceAgenda, tvPersonAgenda, tvStatusAgenda,
            tvStartDate, tvStartTime, tvEndDate, tvEndTime, tvRoundown, tvNote, tvClothes;
    private MaterialCardView materialCardView;
    private String key;
    private String urlLetter, urlSambutan;
    private String fileName;
    private Toolbar toolbarDetail;
    private Button btnLetter, btnSambutan;
    private ProgressDialog mProgressDialog;
    private DownloadTask downloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();

        key = getIntent().getStringExtra(KEY);

        mPresenter = new DetailPresenter(this);
        mPresenter.getDetailAgenda(key);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        btnLetter.setOnClickListener(v -> {

            fileName = getFileNameFromURL("https://dev.karyastudio.com/e-agenda/webfile/no_image.png");

            if (checkDirectoryAndFileExists(fileName)){
                downloadTask = new DownloadTask(DetailActivity.this);
                downloadTask.execute("https://dev.karyastudio.com/e-agenda/webfile/no_image.png");
            }

        });

        btnSambutan.setOnClickListener(v -> {
            fileName = getFileNameFromURL("https://dev.karyastudio.com/e-agenda/webfile/6950c16c9bcc6995f376b297f163175926421.png");

            if (checkDirectoryAndFileExists(fileName)){
                downloadTask = new DownloadTask(DetailActivity.this);
                downloadTask.execute("https://dev.karyastudio.com/e-agenda/webfile/6950c16c9bcc6995f376b297f163175926421.png");
            }
        });

        mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", (dialog, which) -> {
            downloadTask.cancel(true);
        });

    }

    private void initView() {
        toolbarDetail = findViewById(R.id.toolbar_detail);
        setupTollbar();

        tvNameAgenda = findViewById(R.id.tv_name_agenda);
        tvCategoryAgenda = findViewById(R.id.tv_category_agenda);
        tvPlaceAgenda = findViewById(R.id.tv_place_agenda);
        tvPersonAgenda = findViewById(R.id.tv_person_agenda);
        tvStatusAgenda = findViewById(R.id.tv_status);
        tvStartDate = findViewById(R.id.tv_start_date);
        tvStartTime = findViewById(R.id.tv_start_time);
        tvEndDate = findViewById(R.id.tv_end_date);
        tvEndTime = findViewById(R.id.tv_end_time);
        tvRoundown = findViewById(R.id.tv_roundown);
        tvNote = findViewById(R.id.tv_note);
        tvClothes = findViewById(R.id.tv_clothes);
        btnLetter = findViewById(R.id.btn_download_letter);
        btnSambutan = findViewById(R.id.btn_download_sambutan);
        materialCardView = findViewById(R.id.cardLabeled);

        mProgressDialog = new ProgressDialog(DetailActivity.this);
        mProgressDialog.setMessage("Downloading...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
    }

    private void setupTollbar() {
        setSupportActionBar(toolbarDetail);
        TextView toolbarTitle = toolbarDetail.findViewById(R.id.toolbar_title_detail);
        toolbarTitle.setText(R.string.detail_toolbar_title);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void populateDetailAenda(AgendaResponse agendaResponse) {
        DataAgenda dataAgenda = agendaResponse.getData().get(0);
        tvNameAgenda.setText(dataAgenda.getNamaKegiatan());
        tvCategoryAgenda.setText(dataAgenda.getKategori());
        tvPlaceAgenda.setText(dataAgenda.getTempat());
        tvPersonAgenda.setText(dataAgenda.getSubAgenda());
        tvStartDate.setText(dataAgenda.getTanggal());
        tvEndDate.setText(dataAgenda.getTanggalend());
        tvStartTime.setText(dataAgenda.getJam());
        tvEndTime.setText(dataAgenda.getJamend());
        tvStatusAgenda.setText(dataAgenda.getStatusAgenda());
        tvRoundown.setText(Html.fromHtml(dataAgenda.getUrutanAcara()));
        tvNote.setText(Html.fromHtml(dataAgenda.getCatatan()));
        tvClothes.setText(dataAgenda.getPakaian());
        materialCardView.setCardBackgroundColor(Color.parseColor(agendaResponse.getData().get(0).getStatusColor()));

        urlLetter = dataAgenda.getSurat();
        urlSambutan = dataAgenda.getSambutan();

        if ((urlLetter.charAt(urlLetter.length() - 1) == '-') || urlLetter == null || urlLetter.isEmpty()){
            btnLetter.setEnabled(false);
        } else {
            btnLetter.setEnabled(true);
        }

        if ((urlSambutan.charAt(urlSambutan.length() - 1) == '-') || urlLetter == null || urlLetter.isEmpty()){
            btnSambutan.setEnabled(false);
        } else {
            btnSambutan.setEnabled(true);
        }
    }

    @Override
    public void showFailureDetailAgenda(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    public static String getFileNameFromURL(String url) {
        if (url == null) {
            return "";
        }
        try {
            URL resource = new URL(url);
            String host = resource.getHost();
            if (host.length() > 0 && url.endsWith(host)) {
                // handle ...example.com
                return "";
            }
        }
        catch(MalformedURLException e) {
            return "";
        }

        int startIndex = url.lastIndexOf('/') + 1;
        int length = url.length();

        // find end index for ?
        int lastQMPos = url.lastIndexOf('?');
        if (lastQMPos == -1) {
            lastQMPos = length;
        }

        // find end index for #
        int lastHashPos = url.lastIndexOf('#');
        if (lastHashPos == -1) {
            lastHashPos = length;
        }

        // calculate the end index
        int endIndex = Math.min(lastQMPos, lastHashPos);
        return url.substring(startIndex, endIndex);
    }

    private boolean checkDirectoryAndFileExists(String fileName) {

        File outputDirectory;
        String path = Environment.getExternalStorageDirectory() + "/Download/E-Agenda";

        if (new CheckForSDCard().isSDCardPresent()) {
            outputDirectory = new File(path);
        } else {
            Toast.makeText(this, "Memori tidak ditemukan", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!outputDirectory.exists()) {
            outputDirectory.mkdir();
            return true;
        } else {
            File f = new File(path + "/" + fileName);

            if (f.exists()) {
                Toast.makeText(this, "File sudah ada", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }
    }

    public class CheckForSDCard {
        public boolean isSDCardPresent() {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                return true;
            }
            return false;
        }
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;

            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                int fileLength = connection.getContentLength();

                    input = connection.getInputStream();
                    output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/Download/E-Agenda/" + fileName);

                    byte data[] = new byte[4096];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        if (isCancelled()) {
                            input.close();
                            return null;
                        }
                        total += count;
                        if (fileLength > 0)
                            publishProgress((int) (total * 100 / fileLength));
                        output.write(data, 0, count);
                    }


            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
