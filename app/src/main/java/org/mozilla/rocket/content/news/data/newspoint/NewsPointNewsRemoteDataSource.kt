package org.mozilla.rocket.content.news.data.newspoint

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mozilla.components.concept.fetch.Request
import org.json.JSONObject
import org.mozilla.rocket.content.Result
import org.mozilla.rocket.content.news.data.NewsDataSource
import org.mozilla.rocket.content.news.data.NewsItem
import org.mozilla.rocket.content.news.data.NewsProvider
import org.mozilla.rocket.util.safeApiCall
import org.mozilla.rocket.util.sendHttpRequest
import org.mozilla.rocket.util.sha256
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class NewsPointNewsRemoteDataSource(private val newsProvider: NewsProvider?) : NewsDataSource {

    override suspend fun getNewsItems(category: String, language: String, pages: Int, pageSize: Int): Result<List<NewsItem>> = withContext(Dispatchers.IO) {
        return@withContext safeApiCall(
            call = {
                sendHttpRequest(request = Request(url = getApiEndpoint(category, language, pages, pageSize), method = Request.Method.GET),
                    onSuccess = {
                        Result.Success(fromJson(it.body.string()))
                    },
                    onError = {
                        Result.Error(it)
                    }
                )
            },
            errorMessage = "Unable to get news items ($category, $language, $pages, $pageSize)"
        )
    }

    private fun getApiEndpoint(category: String, language: String, pages: Int, pageSize: Int): String {
        val url = newsProvider?.newsUrl ?: DEFAULT_URL
        return String.format(Locale.US, url, category, language, pages, pageSize)
    }

    private fun fromJson(jsonString: String): List<NewsItem> {
        val newsList = ArrayList<NewsItem>()
        val items = JSONObject(jsonString).getJSONArray("items")
        for (i in 0 until items.length()) {
            val jsonObject = items.getJSONObject(i)
            val id = jsonObject.optString("id")
            val title = jsonObject.optString("hl")
            val link = jsonObject.optString("mwu")
            val imageUrl = jsonObject.optJSONArray("images")?.getString(0) ?: ""
            val source = jsonObject.optString("pn")
            val publishDate = jsonObject.optString("dl")
            if (id == null || title == null || link == null || publishDate == null) {
                continue
            }
            var publishTime: Long
            try {
                publishTime = SimpleDateFormat("EEE MMM dd HH:mm:ss 'IST' yyyy", Locale.US).parse(publishDate).time
            } catch (e: ParseException) {
                e.printStackTrace()
                // skip this item
                continue
            }

            newsList.add(NewsItem(title, link, imageUrl, source, publishTime, link.sha256(), feed = "newspoint"))
        }
        return newsList
    }

    companion object {
        private const val DEFAULT_URL = "http://partnersnp.indiatimes.com/feed/fx/atp?channel=*&section=%s&lang=%s&curpg=%s&pp=%s&v=v1&fromtime=1551267146210"
    }
}