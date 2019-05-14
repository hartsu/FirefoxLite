package org.mozilla.rocket.content

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.mozilla.focus.utils.Settings
import org.mozilla.rocket.download.SingleLiveEvent
import org.mozilla.rocket.extension.invalidate

class ChromeViewModel(settings: Settings) : ViewModel() {
    val isNightMode = MutableLiveData<Boolean>()
    val tabCount = MutableLiveData<TabCountChangedEvent>()
    val isRefreshing = MutableLiveData<Boolean>()
    val canGoBack = MutableLiveData<Boolean>()
    val canGoForward = MutableLiveData<Boolean>()

    val showTabTray = SingleLiveEvent<Unit>()
    val showMenu = SingleLiveEvent<Unit>()
    val showNewTab = SingleLiveEvent<Unit>()
    val showUrlInput = SingleLiveEvent<String?>()
    val doScreenshot = SingleLiveEvent<Unit>()
    val pinShortcut = SingleLiveEvent<Unit>()
    val toggleBookmark = SingleLiveEvent<Unit>()
    // TODO: separate to startRefresh / stopLoading
    val refreshOrStop = SingleLiveEvent<Unit>()
    val share = SingleLiveEvent<Unit>()
    val goNext = SingleLiveEvent<Unit>()
    val showDownloadPanel = SingleLiveEvent<Unit>()

    init {
        isNightMode.value = settings.isNightModeEnable
        tabCount.value = TabCountChangedEvent(0, false)
        isRefreshing.value = false
        canGoBack.value = false
        canGoForward.value = false
    }

    fun invalidate() {
        isNightMode.invalidate()
        tabCount.invalidate()
        isRefreshing.invalidate()
        canGoBack.invalidate()
        canGoForward.invalidate()
    }

    fun onNightModeChanged(isEnabled: Boolean) {
        if (isNightMode.value != isEnabled) {
            isNightMode.value = isEnabled
        }
    }

    @JvmOverloads
    fun onTabCountChanged(count: Int, needAnimation: Boolean = false) {
        val currentCount = tabCount.value?.count
        if (currentCount != count) {
            tabCount.value = TabCountChangedEvent(count, needAnimation)
        }
    }

    fun onPageLoadingStarted() {
        if (isRefreshing.value != true) {
            isRefreshing.value = true
        }
    }

    fun onPageLoadingStopped() {
        if (isRefreshing.value == true) {
            isRefreshing.value = false
        }
    }

    fun onNavigationStateChanged(canGoBack: Boolean, canGoForward: Boolean) {
        if (this.canGoBack.value != canGoBack) {
            this.canGoBack.value = canGoBack
        }
        if (this.canGoForward.value != canGoForward) {
            this.canGoForward.value = canGoForward
        }
    }

    data class TabCountChangedEvent(val count: Int, val withAnimation: Boolean)
}