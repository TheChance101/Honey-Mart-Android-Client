package org.the_chance.honeymart.data.source.remote.network

interface FireBaseMessageService {
   suspend fun getDeviceToken(): String

}