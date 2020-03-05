package com.kousenit.kotlindemo

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class KotlinDemoApplicationTests {
	@Autowired
	lateinit var context: ApplicationContext

	@Test
	fun contextLoads() {
		println("There are ${context.beanDefinitionCount} beans in the application context")
		val names = context.beanDefinitionNames.toList()
		names.filter { it.contains("Service") }
				.also(::println)
	}

}
