package com.ilaraca.bff_example.controller

import com.ilaraca.bff_example.service.*
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

data class BannerResponse(val banner: String)

@RestController
@RequestMapping("/api/clients")
class ClientController(
    private val clientService: ClientService,
    private val pointsService: PointsService,
    private val levelService: LevelService,
    private val cmsService: CMSService
) {

    @GetMapping
    fun getAllClients(): CollectionModel<EntityModel<Client>> {
        val clients = clientService.getAllClients()

        val clientModels = clients.map { client ->
            val clientModel = EntityModel.of(client)
            clientModel.add(linkTo<ClientController> {
                getClientBanner(client.clientId, client.segment)
            }.withRel("client-banner"))
            clientModel
        }

        return CollectionModel.of(clientModels).add(linkTo<ClientController> { getAllClients() }.withSelfRel())
    }

    @GetMapping("/banner")
    fun getClientBanner(
        @RequestParam clientId: String,
        @RequestParam segment: String
    ): EntityModel<BannerResponse> {
        val points = pointsService.getPoints(clientId)
        val level = levelService.getLevel(clientId)
        val cmsContent = cmsService.getCMSContent(clientId, points.points, level.level, segment)

        val bannerResponse = BannerResponse(banner = cmsContent.banner)
        val resource = EntityModel.of(bannerResponse)
        resource.add(linkTo<ClientController> { getClientBanner(clientId, segment) }.withSelfRel())

        return resource
    }
}
