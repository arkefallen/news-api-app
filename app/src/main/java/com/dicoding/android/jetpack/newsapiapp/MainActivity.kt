package com.dicoding.android.jetpack.newsapiapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dicoding.android.jetpack.newsapiapp.data.response.ArticlesItem
import com.dicoding.android.jetpack.newsapiapp.data.response.Source
import com.dicoding.android.jetpack.newsapiapp.ui.theme.NewsAPIAppTheme
import com.dicoding.android.jetpack.newsapiapp.ui.viewmodel.NewsViewModel
import com.dicoding.android.jetpack.newsapiapp.ui.viewmodel.NewsViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vmf = NewsViewModelFactory.getInstance()
        val newsViewModel : NewsViewModel by viewModels { vmf }

        setContent {
            NewsAPIAppTheme {
                // A surface container using the 'background' color from the theme
                NewsApp(newsViewModel)
            }
        }
    }
}

@Composable
fun SectionTitle() {
    Text(
        text = "Top Global News",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        )
}

@Preview(showBackground = true)
@Composable
fun SectionTitlePreview() {
    NewsAPIAppTheme {
        SectionTitle()
    }
}

@Composable
fun NewsItem(
    title : String,
    urlImage : String,
    source : String,
    article : ArticlesItem
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                context.startActivity(
                    Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.TAG, article)
                    }
                )
            },
        elevation = 5.dp,
    ) {
        Column {
            AsyncImage(
                model = urlImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
            Column(
                modifier = Modifier.padding(12.dp),
            ) {
                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = source,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsItemPreview() {
    NewsAPIAppTheme {
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
        NewsItem(
            title = article.title.toString(),
            urlImage = article.urlToImage.toString(),
            source = article.source?.name.toString(),
            article = article
        )
    }
}

@Composable
fun NewsApp(
    newsViewModel: NewsViewModel
) {
    val newsList by newsViewModel.newsList.collectAsState()
    val context = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        SectionTitle()
                    },
                    actions = {
                        IconButton(onClick = {
                            context.startActivity(
                                Intent(context, AboutActivity::class.java)
                            )
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "about_page",
                                tint = Color.White
                            )
                        }
                    }
                )
            },
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                val listState = rememberLazyListState()

                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .padding(12.dp),
                ) {
                    items(newsList) {
                       if (it.urlToImage != null) {
                           NewsItem(
                               title = it.title.toString(),
                               urlImage = it.urlToImage.toString(),
                               source = it.source?.name.toString(),
                               article = it
                           )
                           Spacer(modifier = Modifier.height(15.dp))
                       }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun AppPreview() {
    val newsViewModel : NewsViewModel = viewModel(factory = NewsViewModelFactory.getInstance())
    NewsAPIAppTheme {
        NewsApp(newsViewModel)
    }
}