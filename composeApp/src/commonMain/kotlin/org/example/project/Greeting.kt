package org.example.project

class Greeting {
    private val platform = hello()

    fun greet(): String {
        return "Hello, ${platform}!"
    }
}