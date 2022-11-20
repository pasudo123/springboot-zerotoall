package toby.chapter02

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * Pub -> [Data1] -> Op1 -> [Data2] -> Op2 -> [Data3] -> Sub
 * Pub 은 Op1 과 연결
 * Op1 은 Op2 와 연결
 * Op2 는 Sub 과 연결
 * https://youtu.be/DChIxy9g19o?t=2541 참고
 */
class Operator01

fun main() {
    val startPub = startPub()
    val middlePub = middlePub(startPub)
    middlePub.subscribe(endSub())
}

fun middlePub(publisher: Publisher<Int>): Publisher<Int> {

    return Publisher<Int> { subscriber ->
        publisher.subscribe(DelegateSub(subscriber))
    }
}

fun startPub(): Publisher<Int> {
    return object: Publisher<Int> {

        val sequence = (1..10).map { it }

        override fun subscribe(s: Subscriber<in Int>?) {
            println("subscribe")

            s?.onSubscribe(object: Subscription {
                override fun request(n: Long) {
                    println("request : $n")
                    try {
                        sequence.forEach { currentSeq -> s.onNext(currentSeq) }
                        s.onComplete()
                    } catch (exception: Exception) {
                        s.onError(exception)
                    }
                }

                override fun cancel() {
                    println("cancel")
                }
            })
        }
    }
}

fun endSub(): Subscriber<Int> {
    return object: Subscriber<Int> {
        override fun onSubscribe(s: Subscription?) {
            println("onSubscribe")
            s?.request(Long.MAX_VALUE)
        }

        override fun onError(t: Throwable?) {
            println("onError")
        }

        override fun onComplete() {
            println("onComplete")
        }

        override fun onNext(t: Int?) {
            println("onNext : $t")
        }
    }
}