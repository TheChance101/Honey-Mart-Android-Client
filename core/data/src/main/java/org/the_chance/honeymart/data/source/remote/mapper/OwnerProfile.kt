package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.OwnerProfileDto
import org.the_chance.honeymart.domain.model.OwnerProfileEntity

fun OwnerProfileDto.toOwnerProfileEntity() = OwnerProfileEntity(
    userId = userId,
    fullName = fullName,
    email = email,
    profileImage = profileImage,
)