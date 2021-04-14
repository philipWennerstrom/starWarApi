/**
 * 
 */
package br.com.b2sky.infra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.b2sky.infra.beans.Planet;
import br.com.b2sky.infra.errors.PlanetNotFoundException;
import br.com.b2sky.infra.log.IntegrationMonitorLog;
import br.com.b2sky.infra.service.PlanetService;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * 
 * @author wennerstrom
 * @version 1.0
 */
@RestController
public class PlanetController {
	private final PlanetService planetService;
	
	public PlanetController(PlanetService planetService) {
		super();
		this.planetService = planetService;
	}
	
	@GetMapping("/planets")
	public Mono<ResponseEntity<Planet>> searchTerms(@RequestParam("name") String[] name) {
		return findByName(name)
				.map(ResponseEntity::ok)
				.onErrorResume(PlanetNotFoundException.class, this::notFound);
	}

	private Mono<Planet> findByName(String[] name) {
		return nameToMono(name)
				.flatMap(planetService::find);
	}

	private Mono<ResponseEntity<Planet>> notFound(PlanetNotFoundException e) {
		planetNotFoundLog(e).subscribe();
		
		return Mono.just(ResponseEntity.notFound())
				.map(HeadersBuilder::build);
	}

	private Mono<Void> planetNotFoundLog(PlanetNotFoundException e) {
		return IntegrationMonitorLog.builder(getClass())
				.error("Planeta nao encontrado")
				.exception(e)
				.build();
	}

	private Mono<String> nameToMono(String[] name) {
		return Mono.just(nameToSearch(name))
				.publishOn(Schedulers.elastic());
	}

	private String nameToSearch(String[] name) {
		return name[0];
	}
}