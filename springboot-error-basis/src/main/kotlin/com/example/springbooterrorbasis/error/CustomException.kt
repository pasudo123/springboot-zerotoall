package com.example.springbooterrorbasis.error

/**
 * sealed 로 선언된 클래스는 항상 동일 패키지에 상속구조를 가져야 한다.
 */
sealed class CustomException(
    errorDetail: ErrorDetail
): RuntimeException(errorDetail.message)

/**
 * A 에 관련한 익셉션
 */
class AException(
    val errorDetail: ErrorDetail
): CustomException(errorDetail)

class BException(
    private val errorDetail: ErrorDetail
): CustomException(errorDetail)

class CException(
    private val errorDetail: ErrorDetail
): CustomException(errorDetail)

