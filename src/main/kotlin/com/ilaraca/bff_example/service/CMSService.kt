package com.ilaraca.bff_example.service;

import org.springframework.stereotype.Service;

data class CMSResponse(val banner: String)

@Service
class CMSService {
    fun getCMSContent(clientId: String, points: Int, level: String, segment: String): CMSResponse {
        // Simulação de lógica para obter o banner específico do CMS
        val banner = when {
            points > 1000 && level == "Gold" -> "Gold Member Exclusive Banner"
            segment == "Premium" -> "Premium Segment Banner"
            points < 500 -> "Low Points Generic Banner"
            else -> "Standard Banner"
        }
        return CMSResponse(banner = banner)
    }
}
