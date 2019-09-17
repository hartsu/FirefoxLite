package org.mozilla.rocket.deeplink

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mozilla.rocket.deeplink.task.StartGameActivityTask
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DeepLinkTypeTest {

    @Test
    fun `When game home uri is matched, launch game activity`() {
        val deepLinkType = DeepLinkType.parse("rocket://content/game")

        assertEquals(DeepLinkType.GAME_HOME, deepLinkType)
        assertTrue(deepLinkType.getTaskList()[0] is StartGameActivityTask)
    }
}