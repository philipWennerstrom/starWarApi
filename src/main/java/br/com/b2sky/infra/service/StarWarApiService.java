package br.com.b2sky.infra.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.b2sky.infra.beans.Planet;
import br.com.b2sky.infra.starwarapi.beans.PlanetsPage;
import br.com.b2sky.infra.starwarapi.beans.StarWarPlanet;
import reactor.core.publisher.Flux;

@Service
public class StarWarApiService {
	private final ModelMappingService mappingService;
	private final WebClient webClient;

	public StarWarApiService(ModelMappingService mappingService) {
		this.mappingService = mappingService;
		this.webClient = WebClient.create("https://swapi.dev");
	}
	
	public Flux<Planet> getAllPlanets() {
		return planetsResult()
				.map(this::toPlanetModel);
	}

	private Planet toPlanetModel(Object mapper) {
		return mappingService.map(mapper, Planet.class);
	}

	private Flux<StarWarPlanet> planetsResult() {
		return getPlanetsFromRestApi().map(PlanetsPage::getResults).flatMap(Flux::fromIterable);
	}

	private Flux<PlanetsPage> getPlanetsFromRestApi() {
		return webClient.get()
				.uri("/api/planets/")
				.exchange()
				.flatMapMany(a -> {
					return a.bodyToFlux(PlanetsPage.class);
				});
	}
}