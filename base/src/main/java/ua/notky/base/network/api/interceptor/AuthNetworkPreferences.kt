package ua.notky.base.network.api.interceptor

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 07.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

interface AuthNetworkPreferences {
    fun getAuthToken(): String
}