package com.iamincendium.source.query

/**
 * A representation of a player connected to a source game server.
 *
 * @property id The player index (starting from 0)
 * @property name The player name
 * @property score The player score (usually "frags" or "kills")
 * @property connectedTime The time (in seconds) the player has been connected
 */
public data class Player(
    val id: Int,
    val name: String,
    val score: Int,
    val connectedTime: Float,
)
