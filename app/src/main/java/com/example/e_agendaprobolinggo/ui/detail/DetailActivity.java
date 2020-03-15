package com.example.e_agendaprobolinggo.ui.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_agendaprobolinggo.R;
import com.example.e_agendaprobolinggo.model.response.AgendaResponse;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    private DetailContract.Presenter mPresenter;
    private TextView tvNamaKegiatan, tvTanggalKegiatan, tvWaktuKegiatan;
    public static final String KEY = "key";
    private String key;
//    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();

        key = getIntent().getStringExtra(KEY);

        mPresenter = new DetailPresenter(this);
        mPresenter.getDetailAgenda(key);
        //SharedPreferenceUtils.removeUser(this);
    }

    private void initView() {
        tvNamaKegiatan = findViewById(R.id.tv_nama_kegiatan);
        tvTanggalKegiatan = findViewById(R.id.tv_tgl_kegiatan);
        tvWaktuKegiatan = findViewById(R.id.tv_waktu_kegitan);
    }

    @Override
    public void populateDetailAenda(AgendaResponse agendaResponse) {
        tvNamaKegiatan.setText(agendaResponse.getData().get(0).getNamaKegiatan());
        tvTanggalKegiatan.setText(agendaResponse.getData().get(0).getTanggal());
        tvWaktuKegiatan.setText(agendaResponse.getData().get(0).getJam());
    }

    @Override
    public void showFailureDetailAgenda(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
