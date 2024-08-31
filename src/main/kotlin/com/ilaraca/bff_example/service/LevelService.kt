package com.ilaraca.bff_example.service

import org.springframework.stereotype.Service

data class LevelResponse(val level: String)

@Service
class LevelService {
    fun getLevel(clientId: String): LevelResponse {
        // Simulação de lógica para obter o nível de vantagens
        return LevelResponse(level = "Gold")
    }
}
