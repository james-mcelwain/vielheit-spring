package org.vielheit.core

import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringRunner::class)
abstract class BaseTest