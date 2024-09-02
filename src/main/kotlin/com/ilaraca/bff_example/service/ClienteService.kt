package com.ilaraca.bff_example.service

import org.springframework.stereotype.Service

data class Client(val clientId: String, val segment: String)

@Service
class ClientService {

    fun getAllClients(): List<Client> {
        // Simulação de uma lista de clientes
        return listOf(
            Client(clientId = "12345", segment = "Premium"),
            Client(clientId = "67890", segment = "Standard"),
            Client(clientId = "54321", segment = "Gold"),
            Client(clientId = "09876", segment = "Premium")
        )
    }
}
