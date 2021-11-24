package com.oskr19.breakingbad.modules.characters.data

import com.oskr19.breakingbad.core.domain.ListMapper
import com.oskr19.breakingbad.modules.characters.domain.CharacterDTO

class CharacterMapper: ListMapper<CharacterEntity, CharacterDTO> {
    override fun mapFrom(type: CharacterEntity) = CharacterDTO(
        charId = type.charId,
        name = type.name,
        birthday = type.birthday,
        occupation = type.occupation,
        img = type.img,
        status = type.status,
        nickname = type.nickname,
        portrayed = type.portrayed
    )

    override fun mapTo(type: CharacterDTO) = CharacterEntity(
        charId = type.charId,
        name = type.name,
        birthday = type.birthday,
        occupation = type.occupation,
        img = type.img,
        status = type.status,
        nickname = type.nickname,
        portrayed = type.portrayed
    )
}