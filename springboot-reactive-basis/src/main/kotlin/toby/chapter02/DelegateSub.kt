package toby.chapter02

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class DelegateSub(
    private val subscriber: Subscriber<in Int>
) : Subscriber<Int> {

    override fun onSubscribe(s: Subscription?) {
        subscriber.onSubscribe(s)
    }

    override fun onError(t: Throwable?) {
        subscriber.onError(t)
    }

    override fun onComplete() {
        subscriber.onComplete()
    }

    override fun onNext(t: Int) {
        subscriber.onNext(t * 10)
    }
}