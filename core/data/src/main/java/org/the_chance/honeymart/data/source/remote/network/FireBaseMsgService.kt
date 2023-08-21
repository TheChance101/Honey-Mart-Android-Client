package org.the_chance.honeymart.data.source.remote.network

interface FireBaseMsgService {
   suspend fun getDeviceToken(): String

}