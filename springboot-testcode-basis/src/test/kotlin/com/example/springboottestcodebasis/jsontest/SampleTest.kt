package com.example.springboottestcodebasis.jsontest

import com.example.TestEnvironment
import net.javacrumbs.jsonunit.assertj.assertThatJson
import net.javacrumbs.jsonunit.core.Option
import org.junit.jupiter.api.Test

@TestEnvironment
class SampleTest {

    @Test
    fun jsonTest() {
        assertThatJson(
            """
                {
                    "root":{"a":1, "b": 2},
                    "rootArray": [{"sample":1}, {"sample": 2}],
                    "subRootArray": {
                        "firstPerson": {},
                        "secondPerson": {},
                        "thirdPerson": {},
                        "forthPerson": {
                            "one": 5
                        }
                    }
                }
            """.trimIndent()
        ) {
            // `root` 키가 존재함을 확인
            isObject.containsKey("root")

            // `root` 라는 이름의 노드를 조회하여 키 값 존재여부를 확인한다.
            inPath("$.root").isObject
                .containsKey("a")
                .containsKey("b")

            // `rootArray` 라는 키가 존재함을 확인
            isObject.containsKey("rootArray")

            // `rootArray` 의 요소값이 다 포함되고 있음을 확인할 수 있다.
            inPath("$.rootArray").isArray
                .containsAll(listOf("{\"sample\":1}", "{\"sample\":2}"))

            // `rootArray` 배열값 안에, sample 이라는 요소가 존재한다.
            inPath("$.rootArray[*].sample").isPresent

            // `rootArray` 에서 ignore values 를 수행한다.
            `when`(Option.IGNORING_VALUES)
                .inPath("$.rootArray")
                .isArray

            // `subRootArray` 내에 작성된 key 값들
            inPath("$.subRootArray").isObject
                .containsKey("firstPerson")
                .containsKey("secondPerson")
                .containsKey("thirdPerson")
                .containsKey("forthPerson")

            // `subRootArray` 내에 하위 key 값을 확인할 수 있다.
            inPath("$.subRootArray.forthPerson").isObject.containsKey("one")
        }
    }
}