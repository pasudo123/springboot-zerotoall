package toby.chapter02

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class Page01

fun main() {

    val pub = Publisher<Int> { sub ->

        val iter = (1..10).map { it }

        sub?.onSubscribe(object: Subscription {
            override fun request(n: Long) {
                println("request : $n")
                try {
                    iter.forEach { it -> sub.onNext(it) }
                    sub.onComplete()
                } catch (exception: Exception) {
                    sub.onError(exception)
                }
            }

            override fun cancel() {
                println("cancel()")
            }

        })
    }

    val sub = object: Subscriber<Int> {
        override fun onSubscribe(s: Subscription?) {
            println("onSubscribe()")
            s?.request(Long.MAX_VALUE)
        }

        override fun onError(t: Throwable?) {
            // TODO("Not yet implemented")
            println("onError()")
        }

        override fun onComplete() {
            // TODO("Not yet implemented")
            println("onComplete()")
        }

        override fun onNext(t: Int?) {
            // TODO("Not yet implemented")
            println("onNext() : $t")
        }
    }

    pub.subscribe(sub)
}