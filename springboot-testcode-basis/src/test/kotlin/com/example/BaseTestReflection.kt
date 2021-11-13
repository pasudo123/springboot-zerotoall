package com.example

class BaseTestReflection {
}

fun <T: Any> T.setPrivateProperty(variableName: String, data: Any) {
    if (variableName === "createdAt" || variableName === "modifiedAt") {
        javaClass.superclass.getDeclaredField(variableName).let { field ->
            field.isAccessible = true
            field.set(this, data)
        }
        return
    }

    javaClass.getDeclaredField(variableName).let { field ->
        field.isAccessible = true
        field.set(this, data)
    }
}