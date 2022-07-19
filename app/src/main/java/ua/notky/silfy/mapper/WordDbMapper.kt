package ua.notky.silfy.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.NullValueMappingStrategy
import org.mapstruct.factory.Mappers
import ua.notky.silfy.models.dto.WordDto
import ua.notky.silfy.models.local.WordDb
import ua.notky.silfy.models.states.WordState

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Mapper(nullValueMappingStrategy =  NullValueMappingStrategy.RETURN_DEFAULT)
interface WordDbMapper {

    @Mappings(
        Mapping(target = "id", ignore = true),
        Mapping(target = "isFavourite", ignore = true),
        Mapping(target = "isBlacklist", ignore = true),
        Mapping(target = "state", constant = WordState.DEFAULT),
        Mapping(target = "userId", source = "userId")
    )
    fun getDatabaseModel(dto: WordDto, userId: Int): WordDb

    companion object {
        val instance: WordDbMapper = Mappers.getMapper(WordDbMapper::class.java)
    }
}

fun List<WordDto>.toDatabaseModels(userId: Int): List<WordDb> {
    return this.map { WordDbMapper.instance.getDatabaseModel(it, userId) }
}