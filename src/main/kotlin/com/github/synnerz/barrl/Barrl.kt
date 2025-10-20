package com.github.synnerz.barrl

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object Barrl : ModInitializer {
    private val logger = LoggerFactory.getLogger("barrl")

	override fun onInitialize() {
		logger.info("Initialized Synnerz/Barrl library")
	}
}