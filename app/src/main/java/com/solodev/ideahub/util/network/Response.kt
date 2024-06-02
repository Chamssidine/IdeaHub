package com.solodev.ideahub.util.network

data class  ServerResponse (
    var status: ResponseState? = null,
    var message: String? = null,
    var data: String? = null
)
data class ResponseState(
    val status: ServerStatus,
)

enum class  ServerStatus {
    LOADING,
    SUCCESS,
    ERROR
}