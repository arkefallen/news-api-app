package com.dicoding.android.jetpack.newsapiapp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dicoding.android.jetpack.newsapiapp.data.response.ArticlesItem
import com.dicoding.android.jetpack.newsapiapp.data.response.Source
import com.dicoding.android.jetpack.newsapiapp.ui.theme.NewsAPIAppTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val article = intent.getParcelableExtra<ArticlesItem>(TAG) as ArticlesItem

        setContent {
            NewsAPIAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DetailActivityPage(article)
                }
            }
        }
    }

    companion object {
        val TAG = this::class.java.simpleName
    }
}

@Composable
fun DetailActivityPage(article: ArticlesItem) {
    val context = LocalContext.current as Activity
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Read News")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        context.finish()
                    }) { Icon(Icons.Filled.ArrowBack,null)}
                }
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                Column {
                    AsyncImage(
                        model = article.urlToImage,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                    )
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = article.title.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Source from: ${article.source?.name.toString()}",
                            fontSize = 18.sp,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = article.description.toString(), lineHeight = 30.sp)
                        Spacer(modifier = Modifier.height(20.dp))
                        if (article.author != null) {
                            Text(text = "Written by ${article.author}", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DetailActivityPreview() {
    val article = ArticlesItem(
        publishedAt = "2023-05-30T03:39:23Z",
        source = Source(
            id = "bbc-news",
            name = "BBC News"
        ),
        author = "BBC News",
        description ="Foxconn's move comes ahead of the expected launch of Apple's iPhone 15 later this year.",
        title = "Foxconn's move comes ahead of the expected launch of Apple's iPhone 15 later this year.",
        urlToImage = "https://ichef.bbci.co.uk/news/1024/branded_news/FAF0/production/_129904246_gettyimages-1244051546.jpg",
        url = "https://www.bbc.co.uk/news/business-65750918",
        content = "Apple supplier Foxconn is ramping up efforts to recruit more workers for the world's largest iPhone factory, ahead of the launch of a new model.\r\nFoxconn says new workers at its plant in Zhengzhou, C… [+1737 chars]"
    )
    NewsAPIAppTheme {
        DetailActivityPage(article)
    }
}