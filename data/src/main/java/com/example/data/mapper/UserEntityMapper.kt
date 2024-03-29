package com.example.data.mapper

import com.example.data.roomdb.entities.User
import com.example.domain.mapper.UserEntityMapper

object UserEntityMapperImpl : UserEntityMapper<User> {
    override fun fromEntity(entity: User) =
        com.example.domain.models.User(entity.userId, entity.username, entity.password, entity.email)

    override fun toEntity(model: com.example.domain.models.User) =
        User(model.userId, model.username, model.password, model.email)
}
