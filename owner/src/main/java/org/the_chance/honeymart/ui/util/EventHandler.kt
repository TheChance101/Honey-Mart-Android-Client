package org.the_chance.honeymart.ui.util

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
