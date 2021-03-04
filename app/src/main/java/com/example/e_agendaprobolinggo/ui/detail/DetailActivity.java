package com.example.e_agendaprobolinggo.ui.detail;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.local.SharedPreferenceUtils;
import com.example.e_agendaprobolinggo.model.request.DetailAgenda;
import com.example.e_agendaprobolinggo.model.response.DataDetailAgenda;
import com.example.e_agendaprobolinggo.model.response.DetailAgendaResponse;
import com.example.e_agendaprobolinggo.model.response.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    public static final String KODE = "kode";
    private DetailContract.Presenter mPresenter;
    private TextView tvNameAgenda, tvCategoryAgenda, tvPlaceAgenda,
            tvDate, tvPersonAgenda, tvNote, tvSubAgenda,
            tvClothes, tvUndangan, tvPeran, tvRoundown,
            tvTataRuang, tvPerlengkapan, tvPenyelenggara,
            tvPetugasProtokol, tvDetailAgenda;
    private String urlLetter, urlSambutan;
    private String fileName;
    private Toolbar toolbarDetail;
    private Button btnLetter, btnSambutan;
    private ProgressDialog mProgressDialog;
    private DownloadTask downloadTask;
    private LinearLayout listContainer;
    private DetailAgenda detailAgenda;

    private SwipeRefreshLayout swipeRefreshLayout;

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
        } catch (MalformedURLException e) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();

        User user = SharedPreferenceUtils.getUser(this);
        String kode = getIntent().getStringExtra(KODE);
        int idUser = Integer.parseInt(user.getId());

        detailAgenda = new DetailAgenda(kode, idUser);

        mPresenter = new DetailPresenter(this);
        mPresenter.getDetailAgenda(detailAgenda);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        btnLetter.setOnClickListener(v -> {

            // "https://dev.karyastudio.com/e-agenda/webfile/no_image.png"
            fileName = getFileNameFromURL(urlLetter);

            if (checkDirectoryAndFileExists(fileName)) {
                downloadTask = new DownloadTask(DetailActivity.this);
                downloadTask.execute(urlLetter);
            }

        });
        btnLetter.setEnabled(false);

        btnSambutan.setOnClickListener(v -> {
            // "https://dev.karyastudio.com/e-agenda/webfile/6950c16c9bcc6995f376b297f163175926421.png"
            fileName = getFileNameFromURL(urlSambutan);

            if (checkDirectoryAndFileExists(fileName)) {
                downloadTask = new DownloadTask(DetailActivity.this);
                downloadTask.execute(urlSambutan);
            }
        });
        btnSambutan.setEnabled(false);

        mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", (dialog, which) -> {
            downloadTask.cancel(true);
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.getDetailAgenda(detailAgenda);
            listContainer.setVisibility(View.GONE);
        });

    }

    private void initView() {
        toolbarDetail = findViewById(R.id.toolbar_detail);
        setupToolbar();

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        listContainer = findViewById(R.id.listContainer);

        tvDetailAgenda = findViewById(R.id.tv_detail_agenda);
//        tvNameAgenda = findViewById(R.id.tv_name_agenda);
//        tvCategoryAgenda = findViewById(R.id.tv_category_agenda);
//        tvPlaceAgenda = findViewById(R.id.tv_place_agenda);
//        tvPersonAgenda = findViewById(R.id.tv_person_agenda);
//        tvNote = findViewById(R.id.tv_note_agenda);
//        tvClothes = findViewById(R.id.tv_clothes_agenda);
//        tvRoundown = findViewById(R.id.tv_roundown_agenda);
//        tvSubAgenda = findViewById(R.id.tv_sub_agenda);
//        tvUndangan = findViewById(R.id.tv_undangan_agenda);
//        tvPeran = findViewById(R.id.tv_peran_agenda);
//        tvTataRuang = findViewById(R.id.tv_tata_ruangan_agenda);
//        tvPerlengkapan = findViewById(R.id.tv_perlengkapan_agenda);
//        tvPenyelenggara = findViewById(R.id.tv_penyelenggara_agenda);
//        tvPetugasProtokol = findViewById(R.id.tv_petugas_agenda);
//        tvDate = findViewById(R.id.tv_date_agenda);
        btnLetter = findViewById(R.id.btn_download_letter);
        btnSambutan = findViewById(R.id.btn_download_sambutan);

        mProgressDialog = new ProgressDialog(DetailActivity.this);
        mProgressDialog.setMessage("Downloading...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
    }

    private void setupToolbar() {
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
    public void populateDetailAgenda(DetailAgendaResponse detailAgendaResponse) {
        swipeRefreshLayout.setRefreshing(false);
        listContainer.setVisibility(View.VISIBLE);

        DataDetailAgenda dataDetailAgenda = detailAgendaResponse.getData().get(0);
        tvDetailAgenda.setText(Html.fromHtml(dataDetailAgenda.getDetail()));
//        tvNameAgenda.setText(dataDetailAgenda.getNamaKegiatan());
//        tvCategoryAgenda.setText(dataDetailAgenda.getKategori());
//        tvPlaceAgenda.setText(dataDetailAgenda.getTempat());
//        tvPersonAgenda.setText(dataDetailAgenda.getAgenda());
//        tvDate.setText(dataDetailAgenda.getTanggal());
//        tvClothes.setText(dataDetailAgenda.getPakaian());
//        tvSubAgenda.setText(dataDetailAgenda.getSubAgenda());
//        tvTataRuang.setText(dataDetailAgenda.getTataRuangan());
//        tvPerlengkapan.setText(dataDetailAgenda.getPerlengkapan());
//        tvPenyelenggara.setText(dataDetailAgenda.getPenyelenggara());
//        tvPetugasProtokol.setText(dataDetailAgenda.getPetugasProtokol());

//        tvRoundown.setText(noTrailingwhiteLines(Html.fromHtml(dataDetailAgenda.getUrutanAcara())));
//        tvNote.setText(noTrailingwhiteLines(Html.fromHtml(dataDetailAgenda.getCatatan())));
//        tvUndangan.setText(noTrailingwhiteLines(Html.fromHtml(dataDetailAgenda.getUndangan())));
//        tvPeran.setText(noTrailingwhiteLines(Html.fromHtml(dataDetailAgenda.getPeranPimpinan())));

        urlLetter = dataDetailAgenda.getSurat();
        urlSambutan = dataDetailAgenda.getSambutan();

        if ((urlLetter.charAt(urlLetter.length() - 1) == '-') || urlLetter == null || urlLetter.isEmpty()) {
            btnLetter.setEnabled(false);
        } else {
            btnLetter.setEnabled(true);
        }

        if ((urlSambutan.charAt(urlSambutan.length() - 1) == '-') || urlSambutan == null || urlSambutan.isEmpty()) {
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

    private boolean checkDirectoryAndFileExists(String fileName) {

        File outputDirectory;
        String path = Environment.getExternalStorageDirectory() + "/Download/E-Agenda";

        if (new CheckForSDCard().isSDCardPresent()) {
            outputDirectory = new File(path);
        } else {
            Toast.makeText(this, "Memori tidak ditemukan", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!outputDirectory.exists()) {
            outputDirectory.mkdir();
            return true;
        } else {
            File f = new File(path + "/" + fileName);

            if (f.exists()) {
                Toast.makeText(this, "File sudah ada\nLokasi file di Penyimpanan/Download/E-Agenda", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }
    }

    private CharSequence noTrailingwhiteLines(CharSequence text) {

        while (text.charAt(text.length() - 1) == '\n') {
            text = text.subSequence(0, text.length() - 1);
        }
        return text;
    }

    public class CheckForSDCard {
        public boolean isSDCardPresent() {
            return Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED);
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

                byte[] data = new byte[4096];
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
                Toast.makeText(context, "Download berhasil\nLokasi file di Penyimpanan/Download/E-Agenda", Toast.LENGTH_LONG).show();
            }
        }
    }
}
