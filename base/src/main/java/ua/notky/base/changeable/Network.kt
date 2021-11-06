package ua.notky.base.changeable

import ua.notky.base.failure.FAILURE_APP
import ua.notky.base.failure.Failure

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

//fun NetworkResponse.Failure.toFailureInfo(): FailureInfo {
//    return when (this) {
//        // api errors
//        is NetworkResponse.Failure.ApiError.UnAuthorized ->
//            FailureInfo(FailureType.UNAUTHORIZED, this.msg)
//        is NetworkResponse.Failure.ApiError.BadPinCode ->
//            FailureInfo(FailureType.BAD_PIN_CODE, this.msg)
//        is NetworkResponse.Failure.ApiError.FailAddResident ->
//            FailureInfo(FailureType.FAIL_ADD_RESIDENT, this.msg)
//        is NetworkResponse.Failure.ApiError.Other ->
//            FailureInfo(FailureType.OTHER, this.msg)
//        // other errors
//        is NetworkResponse.Failure.HttpError -> FailureInfo(FailureType.HTTP, this.msg)
//        is NetworkResponse.Failure.NetworkError -> FailureInfo(FailureType.NETWORK, this.error.localizedMessage)
//        is NetworkResponse.Failure.Error -> FailureInfo(FailureType.APP, this.error?.localizedMessage)
//    }
//}

fun <E : Throwable> E.toFailure(): Failure {
    return Failure(FAILURE_APP, this.localizedMessage)
}

//fun <K : BaseResponse> handleNetworkResponse(
//    response: NetworkResponse<K>,
//    failBlock: (failure: FailureInfo) -> Unit
//): K? {
//    return when (response) {
//        is NetworkResponse.Success.Result -> response.body
//        is NetworkResponse.Success.Empty -> null
//        is NetworkResponse.Failure -> {
//            failBlock.invoke(response.toFailureInfo())
//            null
//        }
//    }
//}
//
//fun <K : BaseResponse> checkNetworkResponse(
//    response: NetworkResponse<K>,
//    failBlock: (failure: FailureInfo) -> Unit
//): Boolean {
//    return when (response) {
//        is NetworkResponse.Success -> true
//        is NetworkResponse.Failure -> {
//            failBlock.invoke(response.toFailureInfo())
//            false
//        }
//    }
//}