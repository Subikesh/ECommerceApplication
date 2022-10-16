package com.example.data.api.models

data class BooksInfo(
    val authors: List<Any>,
    val binding: Any,
    val language: Any,
    val pages: Any,
    val publisher: Any,
    val year: Int
)

data class LifeStyleInfo(
    val idealFor: Any,
    val neck: Any,
    val sleeve: Any
)

data class Specification(
    val key: String,
    val values: List<Value>
)

data class Value(
    val key: String,
    val value: List<String>
)

data class Attributes(
    val color: String,
    val displaySize: String,
    val size: String,
    val sizeUnit: String,
    val storage: String
)