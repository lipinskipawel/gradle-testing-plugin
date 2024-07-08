package com.github.lipinskipawel.testing

import javax.inject.Inject

abstract class TestingExtension @Inject constructor(
    val name: String
)
