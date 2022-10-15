package com.example.springbootreactivebasis.toby.chapter01

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

                // pub -> sub 에게 어느정도 요청을 받을 것인지 요청한다.
                override fun request(n: Long) {

                    TODO("Not yet implemented")
                }

                override fun cancel() {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    val sub: Subscriber<Int> = object : Subscriber<Int> {
        override fun onSubscribe(s: Subscription?) {
            TODO("Not yet implemented")
        }

        override fun onError(t: Throwable?) {
            TODO("Not yet implemented")
        }

        override fun onComplete() {
            TODO("Not yet implemented")
        }

        override fun onNext(t: Int?) {
            TODO("Not yet implemented")
        }
    }

    pub.subscribe(sub)
}
