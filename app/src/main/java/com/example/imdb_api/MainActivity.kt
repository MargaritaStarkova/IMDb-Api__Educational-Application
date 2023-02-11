package com.example.imdb_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val API_KEY = "k_3ph8h7kw"

class MainActivity : AppCompatActivity() {
    private lateinit var placeholderMessage: TextView
    private lateinit var editText: EditText
    private lateinit var recyclerView: RecyclerView


    private val films = ArrayList<FilmData>()

    val baseUrl = "https://imdb-api.com/"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val filmService = retrofit.create(FilmApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        placeholderMessage = findViewById(R.id.placeholderMessage)

        val searchButton = findViewById<Button>(R.id.searchButton)
        recyclerView = findViewById<RecyclerView>(R.id.films)
        editText = findViewById<EditText>(R.id.queryInput)



        recyclerView.adapter = FilmAdapter(films)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        searchButton.setOnClickListener {
                    search()
        }
    }

    /*private fun showMessage(text: String, additionalMessage: String) {
        if (text.isNotEmpty()) {
            placeholderMessage.visibility = View.VISIBLE
            films.clear()
            adapter.notifyDataSetChanged()
            placeholderMessage.text = text
            if (additionalMessage.isNotEmpty()) {
                Toast.makeText(applicationContext, additionalMessage, Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            placeholderMessage.visibility = View.GONE
        }
    }*/

    private fun search(){
        filmService.getFilms(editText.text.toString())
            .enqueue(object : Callback<FilmResponse> {
                override fun onResponse(
                    call: Call<FilmResponse>,
                    response: Response<FilmResponse>
                ) {
                    when (response.code()) {
                        200 -> { Toast.makeText(this@MainActivity, "${response.body()?.results}", Toast.LENGTH_LONG).show()
                            if (response.body()?.results?.image) {
                                films.clear()
                                films.addAll(response.body()?.results!!)
                                recyclerView.adapter?.notifyDataSetChanged()
                                //showMessage("", "")
                                Toast.makeText(this@MainActivity, "ФИЛЬМЫ", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this@MainActivity, "${response.code()}", Toast.LENGTH_LONG).show()
                               // showMessage(getString(R.string.nothing_found), "")
                            }

                        }
                        else -> {Toast.makeText(this@MainActivity, "УПС, ЧТО ТО ПОШЛО НЕ ТАК22222", Toast.LENGTH_LONG).show()}//showMessage(getString(R.string.something_went_wrong), response.code().toString())
                    }

                }

                override fun onFailure(call: Call<FilmResponse>, t: Throwable) {
                   // TODO("Not yet implemented")
                }

            })
    }

}