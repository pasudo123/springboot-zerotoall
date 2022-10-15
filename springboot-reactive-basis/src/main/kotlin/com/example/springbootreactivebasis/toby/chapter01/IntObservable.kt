package com.example.springbootreactivebasis.toby.chapter01

import java.util.Observable

/**
 * 아래의 옵저버 클래스 부족한 부분이 있음
 * - 완료라는 개념이 없음
 * - 익셉션 핸들리에 대한 개념이 없음
 *  - 버그, 네트워크 상태(복구가능함),
 */
@Suppress("DEPRECATION")
object IntObservable : Observable(), Runnable {

    override fun run() {
        for (index in 1..10) {
            setChanged()
            // push
            notifyObservers(index)
        }
    }
}
