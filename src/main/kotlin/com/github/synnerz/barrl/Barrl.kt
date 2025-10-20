package com.github.synnerz.barrl

import net.fabricmc.api.ClientModInitializer
import org.slf4j.LoggerFactory

object Barrl : ClientModInitializer {
    private val logger = LoggerFactory.getLogger("barrl")

	override fun onInitializeClient() {
		logger.info("Initialized Synnerz/Barrl library")
	}
}