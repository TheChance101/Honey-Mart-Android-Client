package org.the_chance.honeymart.data.source.remote.mapper

import org.the_chance.honeymart.data.source.remote.models.OwnerProfileDto
import org.the_chance.honeymart.domain.model.OwnerProfile

fun OwnerProfileDto.toOwnerProfile() = OwnerProfile(
    ownerId = ownerId ?: 0L,
    fullName = fullName ?: "",
    email = email ?: "",
)