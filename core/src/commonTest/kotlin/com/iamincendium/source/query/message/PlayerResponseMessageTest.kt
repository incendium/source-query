package com.iamincendium.source.query.message

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import com.iamincendium.source.query.test.util.byteArrayFromInts
import io.kotest.core.spec.style.DescribeSpec

private fun playerResponseMessage(vararg values: Int) =
    PlayerResponseMessage(SingleFragmentMessageHeader, byteArrayFromInts(*values))

class PlayerResponseMessageTest : DescribeSpec({
    describe("PlayerResponseMessage") {
        it("should handle a response with no players") {
            val response = playerResponseMessage(0x00)

            assertThat(response.players).isEmpty()
        }

        it("should handle a response with multiple players") {
            val response = playerResponseMessage(
                0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            )

            response.players.run {
                assertThat(this).hasSize(3)

                forEach { player ->
                    assertThat(player.id).isEqualTo(0)
                    assertThat(player.name).isEmpty()
                    assertThat(player.score).isEqualTo(0)
                    assertThat(player.connectedTime).isEqualTo(0.0F)
                }
            }
        }

        it("should handle the response from the api docs") {
            val response = playerResponseMessage(
                0x02, 0x01, 0x5B, 0x44, 0x5D, 0x2D, 0x2D, 0x2D, 0x2D, 0x3E, 0x54, 0x2E, 0x4E, 0x2E, 0x57, 0x3C, 0x2D,
                0x2D, 0x2D, 0x2D, 0x00, 0x0E, 0x00, 0x00, 0x00, 0xB4, 0x97, 0x00, 0x44, 0x02, 0x4B, 0x69, 0x6C, 0x6C,
                0x65, 0x72, 0x20, 0x21, 0x21, 0x21, 0x00, 0x05, 0x00, 0x00, 0x00, 0x69, 0x24, 0xD9, 0x43,
            )

            response.players.run {
                assertThat(this).hasSize(2)

                get(0).run {
                    assertThat(id).isEqualTo(1)
                    assertThat(name).isEqualTo("[D]---->T.N.W<----")
                    assertThat(score).isEqualTo(14)
                    assertThat(connectedTime).isEqualTo(514.37036F)
                }

                get(1).run {
                    assertThat(id).isEqualTo(2)
                    assertThat(name).isEqualTo("Killer !!!")
                    assertThat(score).isEqualTo(5)
                    assertThat(connectedTime).isEqualTo(434.28445F)
                }
            }
        }
    }
})
