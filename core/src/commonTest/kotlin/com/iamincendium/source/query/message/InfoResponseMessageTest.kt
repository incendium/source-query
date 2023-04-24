package com.iamincendium.source.query.message

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.iamincendium.source.query.Environment
import com.iamincendium.source.query.ServerType
import com.iamincendium.source.query.VACStatus
import com.iamincendium.source.query.Visibility
import com.iamincendium.source.query.util.toByteArray
import io.kotest.core.spec.style.DescribeSpec

private fun infoResponseMessage(vararg values: Int) =
    InfoResponseMessage(SingleFragmentMessageHeader, values.toByteArray())

class InfoResponseMessageTest : DescribeSpec({
    xdescribe("InfoResponseMessage") {
        context("should create an info response from the examples in the valve api docs") {
            it("cs source") {
                val response = infoResponseMessage(
                    0x02, 0x67, 0x61, 0x6D, 0x65, 0x32, 0x78, 0x73, 0x2E, 0x63, 0x6F, 0x6D, 0x20, 0x43, 0x6F, 0x75,
                    0x6E, 0x74, 0x65, 0x72, 0x2D, 0x53, 0x74, 0x72, 0x69, 0x6B, 0x65, 0x20, 0x53, 0x6F, 0x75, 0x72,
                    0x63, 0x65, 0x20, 0x23, 0x31, 0x00, 0x64, 0x65, 0x5F, 0x64, 0x75, 0x73, 0x74, 0x00, 0x63, 0x73,
                    0x74, 0x72, 0x69, 0x6B, 0x65, 0x00, 0x43, 0x6F, 0x75, 0x6E, 0x74, 0x65, 0x72, 0x2D, 0x53, 0x74,
                    0x72, 0x69, 0x6B, 0x65, 0x3A, 0x20, 0x53, 0x6F, 0x75, 0x72, 0x63, 0x65, 0x00, 0xF0, 0x00, 0x05,
                    0x10, 0x04, 0x64, 0x6C, 0x00, 0x00, 0x31, 0x2E, 0x30, 0x2E, 0x30, 0x2E, 0x32, 0x32, 0x00,
                )

                response.run {
                    assertThat(networkVersion).isEqualTo(0x02.toByte())
                    assertThat(serverName).isEqualTo("game2xs.com Counter-Strike Source #1")
                    assertThat(mapName).isEqualTo("de_dust")
                    assertThat(gameFolder).isEqualTo("cstrike")
                    assertThat(gameName).isEqualTo("Counter-Strike: Source")
                    assertThat(appId).isEqualTo(240)
                    assertThat(numberOfPlayers).isEqualTo(5)
                    assertThat(maxPlayers).isEqualTo(16)
                    assertThat(numberOfBots).isEqualTo(4)
                    assertThat(serverType).isEqualTo(ServerType.Dedicated)
                    assertThat(environment).isEqualTo(Environment.Linux)
                    assertThat(visibility).isEqualTo(Visibility.Public)
                    assertThat(vacStatus).isEqualTo(VACStatus.Disabled)
                    assertThat(gameVersion).isEqualTo("1.0.0.22")
                }
            }

            it("sin multiplayer") {
                val response = infoResponseMessage(
                    0x2F, 0x53, 0x65, 0x6E, 0x73, 0x65, 0x6D, 0x61, 0x6E, 0x6E, 0x20, 0x53, 0x69, 0x4E, 0x20, 0x44,
                    0x4D, 0x00, 0x70, 0x61, 0x72, 0x61, 0x64, 0x6F, 0x78, 0x00, 0x53, 0x69, 0x4E, 0x20, 0x31, 0x00,
                    0x53, 0x69, 0x4E, 0x20, 0x31, 0x00, 0x1D, 0x05, 0x00, 0x10, 0x00, 0x6C, 0x77, 0x00, 0x00, 0x31,
                    0x2E, 0x30, 0x2E, 0x30, 0x2E, 0x30, 0x00,
                )

                response.run {
                    assertThat(networkVersion).isEqualTo(0x2F.toByte())
                    assertThat(serverName).isEqualTo("Sensemann SiN DM")
                    assertThat(mapName).isEqualTo("paradox")
                    assertThat(gameFolder).isEqualTo("SiN 1")
                    assertThat(gameName).isEqualTo("SiN 1")
                    assertThat(appId).isEqualTo(1309)
                    assertThat(numberOfPlayers).isEqualTo(0)
                    assertThat(maxPlayers).isEqualTo(16)
                    assertThat(numberOfBots).isEqualTo(0)
                    assertThat(serverType).isEqualTo(ServerType.Local)
                    assertThat(environment).isEqualTo(Environment.Windows)
                    assertThat(visibility).isEqualTo(Visibility.Public)
                    assertThat(vacStatus).isEqualTo(VACStatus.Disabled)
                    assertThat(gameVersion).isEqualTo("1.0.0.0")
                }
            }

            it("rag doll kung fu") {
                val response = infoResponseMessage(
                    0xFC, 0x54, 0x68, 0x65, 0x20, 0x44, 0x75, 0x64, 0x65, 0x27, 0x73, 0x20, 0x64, 0x6F, 0x6A, 0x6F,
                    0x00, 0x53, 0x6F, 0x63, 0x63, 0x65, 0x72, 0x00, 0x52, 0x44, 0x4B, 0x46, 0x53, 0x6F, 0x63, 0x63,
                    0x65, 0x72, 0x00, 0x52, 0x61, 0x67, 0x44, 0x6F, 0x6C, 0x6C, 0x4B, 0x75, 0x6E, 0x67, 0x46, 0x75,
                    0x3A, 0x20, 0x53, 0x6F, 0x63, 0x63, 0x65, 0x72, 0x00, 0xEA, 0x03, 0x01, 0x04, 0x00, 0x00, 0x77,
                    0x00, 0x00, 0x32, 0x2E, 0x33, 0x2E, 0x30, 0x2E, 0x30, 0x00,
                )

                response.run {
                    assertThat(networkVersion).isEqualTo(0xFC.toByte())
                    assertThat(serverName).isEqualTo("The Dude's dojo")
                    assertThat(mapName).isEqualTo("Soccer")
                    assertThat(gameFolder).isEqualTo("RDKFSoccer")
                    assertThat(gameName).isEqualTo("RagDollKungFu: Soccer")
                    assertThat(appId).isEqualTo(1002)
                    assertThat(numberOfPlayers).isEqualTo(1)
                    assertThat(maxPlayers).isEqualTo(4)
                    assertThat(numberOfBots).isEqualTo(0)
                    assertThat(serverType).isInstanceOf(Environment.Unknown::class)
                    assertThat(environment).isEqualTo(Environment.Windows)
                    assertThat(visibility).isEqualTo(Visibility.Public)
                    assertThat(vacStatus).isEqualTo(VACStatus.Disabled)
                    assertThat(gameVersion).isEqualTo("2.3.0.0")
                }
            }
        }
        context("should create an info response from the examples captured via wireshark") {
            it("7 days to die") {
                val response = infoResponseMessage(
                    0x11, 0x53, 0x6F, 0x75, 0x72, 0x63, 0x65, 0x20, 0x43, 0x6F, 0x6E, 0x6E, 0x65, 0x6B, 0x74, 0x20,
                    0x54, 0x65, 0x73, 0x74, 0x20, 0x53, 0x65, 0x72, 0x76, 0x65, 0x72, 0x00, 0x56, 0x75, 0x6D, 0x65,
                    0x70, 0x65, 0x20, 0x54, 0x65, 0x72, 0x72, 0x69, 0x74, 0x6F, 0x72, 0x79, 0x00, 0x37, 0x44, 0x54,
                    0x44, 0x00, 0x37, 0x20, 0x44, 0x61, 0x79, 0x73, 0x20, 0x54, 0x6F, 0x20, 0x44, 0x69, 0x65, 0x00,
                    0x00, 0x00, 0x00, 0x0A, 0x00, 0x64, 0x6C, 0x01, 0x00, 0x30, 0x30, 0x2E, 0x31, 0x37, 0x2E, 0x30,
                    0x30, 0x00, 0xB1, 0x16, 0x69, 0x02, 0x80, 0x47, 0xC1, 0x88, 0x2D, 0x40, 0x01, 0x41, 0x72, 0x67,
                    0x42, 0x41, 0x51, 0x49, 0x41, 0x42, 0x41, 0x41, 0x44, 0x6D, 0x47, 0x30, 0x56, 0x6C, 0x51, 0x45,
                    0x59, 0x75, 0x67, 0x4D, 0x44, 0x50, 0x43, 0x6B, 0x65, 0x41, 0x77, 0x41, 0x45, 0x42, 0x44, 0x4B,
                    0x6B, 0x41, 0x51, 0x41, 0x44, 0x41, 0x77, 0x4F, 0x72, 0x41, 0x51, 0x3D, 0x3D, 0x00, 0xB2, 0xD6,
                    0x03, 0x00, 0x00, 0x00, 0x00, 0x00,
                )

                response.run {
                    assertThat(networkVersion).isEqualTo(0x11.toByte())
                    assertThat(serverName).isEqualTo("Source Connekt Test Server")
                    assertThat(mapName).isEqualTo("Vumepe Territory")
                    assertThat(gameFolder).isEqualTo("7DTD")
                    assertThat(gameName).isEqualTo("7 Days To Die")
                    assertThat(appId).isEqualTo(0)
                    assertThat(numberOfPlayers).isEqualTo(0)
                    assertThat(maxPlayers).isEqualTo(10)
                    assertThat(numberOfBots).isEqualTo(0)
                    assertThat(serverType).isEqualTo(ServerType.Dedicated)
                    assertThat(environment).isEqualTo(Environment.Linux)
                    assertThat(visibility).isEqualTo(Visibility.Private)
                    assertThat(vacStatus).isEqualTo(VACStatus.Disabled)
                    assertThat(gameVersion).isEqualTo("00.17.00")
                }
            }
        }
    }
})
