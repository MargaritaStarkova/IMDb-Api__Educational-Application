package com.example.imdb_api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
    
    private val baseUrl = "https://imdb-api.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val filmService = retrofit.create(FilmApi::class.java)

    private val film = ArrayList<FilmData>()

  //  private lateinit var placeholderMessage: TextView
    private lateinit var editText: EditText
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // placeholderMessage = findViewById(R.id.placeholderMessage)

        val searchButton = findViewById<Button>(R.id.searchButton)
        recyclerView = findViewById<RecyclerView>(R.id.films)
        editText = findViewById<EditText>(R.id.queryInput)



        recyclerView.adapter = FilmAdapter(film)
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
                        200 ->
                            if (response.body()?.results?.isNotEmpty() == true) {
                                film.clear()
                                film.addAll(response.body()?.results!!)
                                recyclerView.adapter?.notifyDataSetChanged()
                                //showMessage("", "")
                               // Toast.makeText(this@MainActivity, "ФИЛЬМЫ", Toast.LENGTH_LONG)
                                  //  .show()
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "ПОИСК НЕ ДАЛ РЕЗУЛЬТАТОВ",
                                    Toast.LENGTH_LONG
                                ).show()
                                // showMessage(getString(R.string.nothing_found), "")
                            }


                        else -> {Toast.makeText(
                            this@MainActivity,
                            "ПОИСК НЕ ДАЛ РЕЗУЛЬТАТОВ",
                            Toast.LENGTH_LONG
                        ).show()}
                    }
                }

                override fun onFailure(call: Call<FilmResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }

            })



    }

}