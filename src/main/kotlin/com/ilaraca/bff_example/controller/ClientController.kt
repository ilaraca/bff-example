package com.ilaraca.bff_example.controller

import com.ilaraca.bff_example.service.CMSService
import com.ilaraca.bff_example.service.LevelService
import com.ilaraca.bff_example.service.PointsService
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

data class BannerResponse(val banner: String)

@RestController
@RequestMapping("/api/client")
class ClientController(
    private val pointsService: PointsService,
    private val levelService: LevelService,
    private val cmsService: CMSService
) {

    @GetMapping
    fun getClientBanner(
        @RequestParam clientId: String,
        @RequestParam segment: String
    ): EntityModel<BannerResponse> {
        // Obter dados de saldo de pontos e nível de vantagens
        val points = pointsService.getPoints(clientId)
        val level = levelService.getLevel(clientId)

        // Obter o banner específico do CMS com base nos dados obtidos
        val cmsContent = cmsService.getCMSContent(clientId, points.points, level.level, segment)

        // Montar o payload final com o banner
        val bannerResponse = BannerResponse(banner = cmsContent.banner)
        val resource = EntityModel.of(bannerResponse)

        // Adicionar links HATEOAS para navegação
        resource.add(linkTo<ClientController> { getClientBanner(clientId, segment) }.withSelfRel())

        return resource
    }
}
