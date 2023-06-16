package org.the_chance.honeymart.domain.repository

/**
 * Created by Aziza Helmy on 6/16/2023.
 */
interface AuthenticationRepository{
    suspend fun loginUser(email :String , password :String):String

}