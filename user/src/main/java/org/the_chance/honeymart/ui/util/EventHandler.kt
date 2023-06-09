package org.the_chance.honeymart.ui.util

import androidx.lifecycle.Observer

open class EventHandler<out T>(private val content: T) {
    private var hasBeenHandled = false

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
    Observer<EventHandler<T>?> {
    override fun onChanged(value: EventHandler<T>?) {
        value?.getContentIfHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}