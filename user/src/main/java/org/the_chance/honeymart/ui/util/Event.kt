package org.the_chance.honeymart.ui.util

import androidx.lifecycle.Observer

open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    fun getContentIfHandled(): T? {
        return if (hasBeenHandled)
            null
        else {
            hasBeenHandled = true
            content
        }
    }
}

class EventObserve<T>(private val onEventUnhandledContent: (T) -> Unit) :
    Observer<Event<T>?> {
    override fun onChanged(value: Event<T>?) {
        value?.getContentIfHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}