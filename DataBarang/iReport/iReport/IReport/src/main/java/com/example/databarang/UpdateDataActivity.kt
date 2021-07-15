package com.example.databarang

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.databarang.adapter.ListContent
import com.example.databarang.model.ResponseBarang
import com.example.databarang.network.koneksi
import kotlinx.android.synthetic.main.activity_insert_data.*
import kotlinx.android.synthetic.main.activity_insert_data.et_nama_barang
import kotlinx.android.synthetic.main.activity_insert_data.toolbar
import kotlinx.android.synthetic.main.activity_update_data.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class UpdateDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)
        toolbar.title = "UPDATE DATA"
        toolbar.setTitleTextColor(Color.WHITE)

        val i = intent
        val idBarang = i.getStringExtra("IDBARANG")
        val namaBarang = i.getStringExtra("NAMABARANG")
        val jumlahBarang = i.getStringExtra("JMLBARANG")

        et_nama_barang.setText(namaBarang)
        et_jumlah_barang.setText(jumlahBarang)
        btn_submit.setOnCLickListener {
            val etNamaBarang = et_nama_barang.text
            val etJmlBarang = et_jumlah_barang
            if(etJmlBarang.isEmpty){
                Toast.makeText(this@UpdateDataActivity, "Jumlah Barang Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
         }else if(etNamaBarang).isEmpty()){
            Toast.makeText(this@UpdateDataActivity), "Nama Barang Tidak Boleh Kosong", Toast.LENGTH_LONG).show()
        }else {
            actionData(idBarang.toString(), etNamaBarang.toString(), etJmlBarang.toString())
        }

        }
        btn_back.setOnClickListener {
            finish()
        }
        getData()
    }
    fun getData() {
        koneksi.service.getBarang().enqueue(object :Callback<ResponseBarang> {
            override fun onFailure(call: Call<ResponseBarang>, t: Throwable) {
                Log.d("pesan1", t.localizedMessage)
            }
            override fun onResponse(
                call: Call<ResponseBarang>,
                response: Response<ResponseBarang>
            ) {
                if(response.isSuccessful){
                    val dataBody = response.body()
                    val datacontent = dataBody!!.data

                    val rvAdapter = ListContent(datacontent, this@UpdateDataActivity)

                    rv_data_barang.apply {
                        adapter = rvAdapter
                        LayoutManager = LinearLayoutManager(this@UpdateDataActivity)
                    }
                }
            }
        })
    }
}