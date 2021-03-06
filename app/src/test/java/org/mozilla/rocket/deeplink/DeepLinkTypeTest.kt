package org.mozilla.rocket.deeplink

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mozilla.rocket.deeplink.task.StartGameActivityTask
import org.mozilla.rocket.deeplink.task.StartGameItemActivityTask
import org.mozilla.rocket.deeplink.task.StartNewsActivityTask
import org.mozilla.rocket.deeplink.task.StartNewsItemActivityTask
import org.mozilla.rocket.deeplink.task.StartRewardActivityTask
import org.mozilla.rocket.deeplink.task.StartShoppingActivityTask
import org.mozilla.rocket.deeplink.task.StartShoppingItemActivityTask
import org.mozilla.rocket.deeplink.task.StartTravelActivityTask
import org.mozilla.rocket.deeplink.task.StartTravelItemActivityTask
import java.net.URLEncoder

class DeepLinkTypeTest {

    @Test
    fun `When game home uri is matched, launch game activity`() {
        val deepLinkType = DeepLinkType.parse("rocket://content/game")

        assertEquals(DeepLinkType.GAME_HOME, deepLinkType)
        assertTrue(deepLinkType.getTaskList()[0] is StartGameActivityTask)
    }

    @Test
    fun `When game item uri is matched, launch game mode activity`() {
        val url = "https://www.mozilla.org"
        val feed = "test_feed"
        val source = "test_source"
        val deepLinkType = DeepLinkType.parse("rocket://content/game/item?url=${URLEncoder.encode(url, "utf-8")}&feed=$feed&source=$source")

        assertEquals(DeepLinkType.GAME_ITEM, deepLinkType)
        val task = deepLinkType.getTaskList()[0]
        assertTrue(task is StartGameItemActivityTask)
        task as StartGameItemActivityTask
        assertEquals(task.url, url)
        assertEquals(task.feed, feed)
        assertEquals(task.source, source)
    }

    @Test
    fun `When news home uri is matched, launch news activity`() {
        val deepLinkType = DeepLinkType.parse("rocket://content/news")

        assertEquals(DeepLinkType.NEWS_HOME, deepLinkType)
        assertTrue(deepLinkType.getTaskList()[0] is StartNewsActivityTask)
    }

    @Test
    fun `When news item uri is matched, launch content tab activity`() {
        val url = "https://www.mozilla.org"
        val feed = "test_feed"
        val source = "test_source"
        val deepLinkType = DeepLinkType.parse("rocket://content/news/item?url=${URLEncoder.encode(url, "utf-8")}&feed=$feed&source=$source")

        assertEquals(DeepLinkType.NEWS_ITEM, deepLinkType)
        val task = deepLinkType.getTaskList()[0]
        assertTrue(task is StartNewsItemActivityTask)
        task as StartNewsItemActivityTask
        assertEquals(task.url, url)
        assertEquals(task.feed, feed)
        assertEquals(task.source, source)
    }

    @Test
    fun `When shopping home uri is matched, launch shopping activity`() {
        val deepLinkType = DeepLinkType.parse("rocket://content/shopping")

        assertEquals(DeepLinkType.SHOPPING_HOME, deepLinkType)
        assertTrue(deepLinkType.getTaskList()[0] is StartShoppingActivityTask)
    }

    @Test
    fun `When shopping item uri is matched, launch content tab activity`() {
        val url = "https://www.mozilla.org"
        val feed = "test_feed"
        val source = "test_source"
        val deepLinkType = DeepLinkType.parse("rocket://content/shopping/item?url=${URLEncoder.encode(url, "utf-8")}&feed=$feed&source=$source")

        assertEquals(DeepLinkType.SHOPPING_ITEM, deepLinkType)
        val task = deepLinkType.getTaskList()[0]
        assertTrue(task is StartShoppingItemActivityTask)
        task as StartShoppingItemActivityTask
        assertEquals(task.url, url)
        assertEquals(task.feed, feed)
        assertEquals(task.source, source)
    }

    @Test
    fun `When travel home uri is matched, launch travel activity`() {
        val deepLinkType = DeepLinkType.parse("rocket://content/travel")

        assertEquals(DeepLinkType.TRAVEL_HOME, deepLinkType)
        assertTrue(deepLinkType.getTaskList()[0] is StartTravelActivityTask)
    }

    @Test
    fun `When travel item uri is matched, launch content tab activity`() {
        val url = "https://www.mozilla.org"
        val feed = "test_feed"
        val source = "test_source"
        val deepLinkType = DeepLinkType.parse("rocket://content/travel/item?url=${URLEncoder.encode(url, "utf-8")}&feed=$feed&source=$source")

        assertEquals(DeepLinkType.TRAVEL_ITEM, deepLinkType)
        val task = deepLinkType.getTaskList()[0]
        assertTrue(task is StartTravelItemActivityTask)
        task as StartTravelItemActivityTask
        assertEquals(task.url, url)
        assertEquals(task.feed, feed)
        assertEquals(task.source, source)
    }

    @Test
    fun `When reward home uri is matched, launch reward activity`() {
        val deepLinkType = DeepLinkType.parse("rocket://content/reward")

        assertEquals(DeepLinkType.REWARD_HOME, deepLinkType)
        assertTrue(deepLinkType.getTaskList()[0] is StartRewardActivityTask)
    }
}