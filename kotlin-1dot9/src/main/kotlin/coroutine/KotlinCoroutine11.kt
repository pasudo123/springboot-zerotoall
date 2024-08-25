package coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class KotlinCoroutine11

data class Details(
    val name: String,
    val followers: Int,
)

data class Tweet(
    val text: String
)

fun getFollowersNumber(): Int = throw RuntimeException("팔로워 수 획득 실패")

suspend fun getUserName(): String {
    delay(500)
    return "PARK"
}

suspend fun getTweets(): List<Tweet> {
    delay(300)
    return listOf(Tweet("Hello"), Tweet("World"))
}

/**
 * 아래 내용을 coroutineScope, 코루틴 스코프 함수로 변경하면 예외에도 유연하게 대처할 수 있다.
 * AS-IS : suspend fun CoroutineScope.getUserDetails(): Details {}
 * TO-BE : suspend fun getUserDetails() = coroutineScope {}
 */
suspend fun CoroutineScope.getUserDetails(): Details {
    val userName = async(Dispatchers.IO) { getUserName() }
    val followersNumber = async(Dispatchers.IO) { getFollowersNumber() }
    return Details(userName.await(), followersNumber.await())
}

fun main11() = runBlocking {
    val details = try {
        getUserDetails()
    } catch (exception: Exception) {
        null
    }

    val tweets = async { getTweets() }
    println("user=$details")
    println("tweets=${tweets.await()}")
}