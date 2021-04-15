/**
 * 
 */
package br.com.b2sky.infra.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.b2sky.infra.beans.Planet;
import br.com.b2sky.infra.errors.PlanetNotFoundException;
import br.com.b2sky.infra.log.IntegrationMonitorLog;
import br.com.b2sky.infra.service.PlanetService;
import reactor.core.publisher.Flux;
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
	public Mono<ResponseEntity<List<Planet>>> find(@RequestParam(required=false, name="name") String[] name) {
		return searchPlanets(name)
				.collectList()
				.map(ResponseEntity::ok)
				.onErrorResume(PlanetNotFoundException.class, this::notFound);
	}
	
	@GetMapping("/planets/{id}")
	public Mono<ResponseEntity<Planet>> findById(@PathVariable String id) {
		return planetService.findById(id)
				.map(ResponseEntity::ok)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}

	private Flux<Planet> searchPlanets(String[] name) {
		return Optional
				.ofNullable(name)
				.map(this::findByName)
				.orElseGet(planetService::findAll);
	}

	private Flux<Planet> findByName(String[] n) {
		return nameToMono(n).flatMapMany(planetService::find);
	}

	private Mono<ResponseEntity<List<Planet>>> notFound(PlanetNotFoundException e) {
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