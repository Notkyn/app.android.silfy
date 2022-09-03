package ua.notky.silfy.util

import ua.notky.silfy.models.enums.GoLangType
import kotlin.random.Random

private const val EN_SYMBOLS = "abcdefghijklmnopqrstuvwyz"
private const val UA_SYMBOLS = "абвгдеєжзиіїйклмнопрстуфхцчшщьюя"
private const val SIZE_ADDITIONAL_SYMBOLS = 5


fun fetchAdditionalSymbol(isEasy: Boolean, lang: GoLangType): List<String> {
    return if(isEasy) {
        emptyList()
    } else {
        val symbols = when (lang) {
            GoLangType.EN -> EN_SYMBOLS
            else -> UA_SYMBOLS
        }

        val additionalSymbols: MutableList<String> = mutableListOf()

        for(i in 0 until Random.nextInt(SIZE_ADDITIONAL_SYMBOLS)) {
            val index = Random.nextInt(symbols.length)
            additionalSymbols.add(symbols[index].toString())
        }
        additionalSymbols
    }
}