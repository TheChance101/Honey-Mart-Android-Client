package org.the_chance.honeymart.data.source.remote.mapper
import org.the_chance.honeymart.data.source.remote.models.ProfileUserDto
import org.the_chance.honeymart.domain.model.UserProfile

fun ProfileUserDto.toUserProfile() = UserProfile(
    userId = userId ?: 0L,
    fullName =  fullName ?: "",
    profileImage = profileImage ?: "",
    email = email ?: "",
)