# Source Query

(This library is still under heavy development.)

A Kotlin library for querying Source or Steamworks-based game servers using Coroutines. This project was originally 
designed around kotlinx-io, then ktor-io/ktor-network, but the latter seems to not have public access to UDP packets, 
so I am currently exploring options on how to approach this. One option is a simple PR, the other is to explore other 
options like Vert.x, Netty, or sticking to Java.NIO.

##### TODO List
* Multi-response packet support
* Support the RCON protocol

##### Nice to Have
* WASM support
* NodeJS support
* Native (macOS/Linux/Windows) support (contributors welcome) 

##### Won't Be Implemented
* Master server querying
* GoldSrc support
