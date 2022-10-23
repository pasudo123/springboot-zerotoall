package toby.chapter01

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class Page02

fun main() {
    // Publisher  : Observable
    // Subscriber : Observer

    val elements = listOf(1, 2, 3, 4, 5)

    @Suppress("ObjectLiteralToLambda")
    val pub: Publisher<Int> = object : Publisher<Int> {
        override fun subscribe(sub: Subscriber<in Int>?) {

            // 백프레셔 : pub & sub 사이의 속도차를 조절하는 기술
            sub?.onSubscribe(object : Subscription {

                val it = elements.iterator()

                // pub -> sub 에게 어느정도 요청을 받을 것인지 요청한다. : 별도 응답을 받진 않는다. (VOID)
                // 이렇게 받겠다는 것보단, 의지 전달?
                override fun request(n: Long) {
                    try {
                        println("request : $n")
                        var currentSize = n

                        while (currentSize-- > 0) {
                            if (it.hasNext()) {
                                sub.onNext(it.next())
                            } else {
                                sub.onComplete()
                                break
                            }
                        }
                    } catch (exception: Exception) {
                        println("exception")
                        sub.onError(exception)
                    }
                }

                override fun cancel() {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    val sub: Subscriber<Int> = object : Subscriber<Int> {

        private var bufferSize = 2L
        private var subscription: Subscription? = null

        override fun onSubscribe(s: Subscription?) {
            println("onSubscribe()")
            // 최대한으로 받고싶다.
            subscription = s
            subscription?.request(2)
        }

        override fun onNext(t: Int?) {
            println("onNext() : $t")

            if (--bufferSize <= 0) {
                bufferSize = 2
                subscription?.request(bufferSize)
            }
        }

        override fun onError(t: Throwable?) {
            println("onError() : $t")
        }

        override fun onComplete() {
            println("onComplete()")
        }
    }

    // pub 을 sub 이 구독.
    pub.subscribe(sub)
}
