package ua.notky.base.model

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
sealed class ResultState<out K> {
    sealed class Success<T> : ResultState<T>() {
        data class Result<L>(val data: L) : Success<L>()
        object Empty : Success<Nothing>()
    }

    data class Failure(val error: Throwable?) : ResultState<Nothing>()

    companion object {
        fun <L> successResult(data: L) = ResultState.Success.Result(data)
        fun successEmpty() = Success.Empty

        fun failureMissing(parameterName: String?) = Failure(getMissingException(parameterName))

        private fun getMissingException(parameterName: String?): IllegalStateException {
            return IllegalStateException("Missing data: [name=$parameterName]")
        }
    }
}