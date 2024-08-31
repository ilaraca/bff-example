package com.ilaraca.bff_example.service

import org.springframework.stereotype.Service

data class PointsResponse(val points: Int)

@Service
class PointsService {
    fun getPoints(clientId: String): PointsResponse {
        // Simulação de lógica para obter o saldo de pontos
        return PointsResponse(points = 1500)
    }
}
