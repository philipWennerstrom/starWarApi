package br.com.b2sky.infra.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import br.com.b2sky.infra.beans.Planet;
import reactor.core.publisher.Flux;


@Repository
public interface PlanetRepository extends ReactiveMongoRepository<Planet, String> {
	public Flux<Planet> findByName(String name);
}
