package org.tech.ebookcmp.core.data

import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsBytes
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import org.tech.ebookcmp.core.domain.DataError
import org.tech.ebookcmp.core.domain.Result
import kotlin.coroutines.coroutineContext

suspend inline  fun <reified T> safeApiCall(
    execute: () -> HttpResponse
) : Result<T, DataError.Remote>{
    return try {
        responseToResult(execute())
    }catch (e : SocketTimeoutException){
        Result.Error(DataError.Remote.UNKNOWN)
    }catch (e : UnresolvedAddressException){
        Result.Error(DataError.Remote.NO_INTERNET)
    }catch (e : Exception){
        coroutineContext.ensureActive()
        Result.Error(DataError.Remote.UNKNOWN)
    }
}
suspend inline fun <reified T> responseToResult(
    response : HttpResponse
):Result<T,DataError.Remote>{
    return when(response.status.value){
        in 200..299->{
            try {
                Result.Success(response.body<T>())
            }catch (e : Exception){
                Result.Error(DataError.Remote.SERIALIZATION)
            }
        }
        408->{
            Result.Error(DataError.Remote.REQUEST_TIMEOUT)
        }
        429-> {
            Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
        }
        in 500..599-> Result.Error(DataError.Remote.SERVER)
        else -> Result.Error(DataError.Remote.UNKNOWN)
    }
}