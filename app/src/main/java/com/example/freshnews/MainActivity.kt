package com.example.freshnews

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.w3c.dom.Text


class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var recyclerView: RecyclerView
    private  lateinit var madapter:NewsListAdapter
     var str =""
    private lateinit var Bussiness:TextView
    private lateinit var Entertainment:TextView
    private lateinit var Sport:TextView
    private lateinit var Health:TextView
    private lateinit var Secience:TextView
    private lateinit var tecnology:TextView
    private lateinit var none:TextView
    private lateinit var Category:TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        Bussiness = findViewById<TextView>(R.id.bussiness)
        Entertainment = findViewById(R.id.entertainment)
        Health = findViewById(R.id.health)
        Sport = findViewById(R.id.Game)
        Secience =findViewById(R.id.science)
        tecnology = findViewById(R.id.tecnology)
        none = findViewById(R.id.none)
       Category = findViewById(R.id.category)
        recyclerView.layoutManager = LinearLayoutManager(this)
        if (Category.text.isEmpty()  || Category.text =="none"){
            fetchData()
    }
       none.setOnClickListener {
           fetchData()
           Category.text ="none"
       }
        Bussiness.setOnClickListener {
            val cat ="business"
            fetchData(cat)
            Category.text ="Bussiness"
        }
        Entertainment.setOnClickListener {
            val cat ="entertainment"
            fetchData(cat)
            Category.text ="Entertainment"
        }
       Secience.setOnClickListener {
            val cat ="science"
            fetchData(cat)
            Category.text ="science"
        }
        Health.setOnClickListener {
            val cat ="health"
            fetchData(cat)
            Category.text ="Health"
        }
        Sport.setOnClickListener {
            val cat ="sport"
            fetchData(cat)
            Category.text ="Sport"
        }
        tecnology.setOnClickListener {
            val cat ="technology"
            fetchData(cat)
            Category.text ="technology"
        }
        madapter =NewsListAdapter(this)
        recyclerView.adapter =madapter


    }
    private fun fetchData() {

        val url =
            "https://newsapi.org/v2/top-headlines?country=in&apiKey=208fb60b989b4cef8fa7ff86b5c0e38e" +
                    ""
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                Log.e("TAG", "fetchData: $it")
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }

                madapter.updateNews(newsArray)
            },

            {
                Log.d("Error occur", "Try again..." + it.networkResponse.statusCode)
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    private fun fetchData(category:String) {

        val url =
            "https://newsapi.org/v2/top-headlines?country=in&category=${category}&apiKey=208fb60b989b4cef8fa7ff86b5c0e38e" +
                    ""
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                Log.e("TAG", "fetchData: $it")
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }

                madapter.updateNews(newsArray)
            },

            {
                Log.d("Error occur", "Try again..." + it.networkResponse.statusCode)
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}