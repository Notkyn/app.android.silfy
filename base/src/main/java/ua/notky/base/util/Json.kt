package ua.notky.base.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

inline fun <reified T> T?.toJsonString(customAdapter: Any? = null): String {
    val moshiBuilder = Moshi.Builder().add(KotlinJsonAdapterFactory())
    customAdapter?.let { moshiBuilder.add(customAdapter) }
    val adapter: JsonAdapter<T> = moshiBuilder.build().adapter(T::class.java)
    return adapter.toJson(this)
}

inline fun <reified T> List<T>?.toJsonString(): String {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val type = Types.newParameterizedType(List::class.java, T::class.java)
    val adapter: JsonAdapter<List<T>> = moshi.adapter(type)
    return adapter.toJson(this)
}

inline fun <reified K, reified V> Map<K, V>.toJsonString(): String {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val type = Types.newParameterizedType(Map::class.java, K::class.java, V::class.java)
    val adapter: JsonAdapter<Map<K, V>> = moshi.adapter(type)
    return adapter.toJson(this)
}

inline fun <reified T> String.fromJsonToObject(customAdapter: Any? = null): T? {
    val moshiBuilder = Moshi.Builder().add(KotlinJsonAdapterFactory())
    customAdapter?.let { moshiBuilder.add(customAdapter) }
    val adapter: JsonAdapter<T> = moshiBuilder.build().adapter(T::class.java)

    return adapter.fromJson(this)
}

inline fun <reified T> String.fromJsonToObjects(customAdapter: Any? = null): List<T>? {
    val moshiBuilder = Moshi.Builder().add(KotlinJsonAdapterFactory())
    customAdapter?.let { moshiBuilder.add(customAdapter) }
    val type = Types.newParameterizedType(List::class.java, T::class.java)
    val adapter: JsonAdapter<List<T>> = moshiBuilder.build().adapter(type)

    return adapter.fromJson(this)
}

inline fun <reified K, reified V> String.fromJsonToMap(customAdapter: Any? = null): Map<K, V>? {
    val moshiBuilder = Moshi.Builder().add(KotlinJsonAdapterFactory())
    customAdapter?.let { moshiBuilder.add(customAdapter) }
    val type = Types.newParameterizedType(Map::class.java, K::class.java, V::class.java)
    val adapter: JsonAdapter<Map<K, V>> = moshiBuilder.build().adapter(type)

    return adapter.fromJson(this)
}