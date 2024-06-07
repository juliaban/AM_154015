package com.example.sladamiprzygod

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class NextActivity : BaseActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewSzlakiAdapter: RecyclerViewSzlakiAdapter
    private val szlakiList = mutableListOf<Szlak>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)

        setLayout(R.layout.activity_next)

        recyclerView = findViewById(R.id.rvSzlakiLista)
        recyclerViewSzlakiAdapter = RecyclerViewSzlakiAdapter(this, szlakiList)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = recyclerViewSzlakiAdapter

        loadJsonData()
    }

    private fun loadJsonData() {
        val jsonFileString = getJsonDataFromAsset("szlaki.json")
        jsonFileString?.let {
            val gson = Gson()
            val listType = object : TypeToken<List<Szlak>>() {}.type
            val szlaki: List<Szlak> = gson.fromJson(it, listType)
            szlakiList.addAll(szlaki)
            recyclerViewSzlakiAdapter.notifyDataSetChanged()
        }
    }

    private fun getJsonDataFromAsset(fileName: String): String? {
        return try {
            assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            null
        }
    }
}
