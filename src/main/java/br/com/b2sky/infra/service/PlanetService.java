package br.com.b2sky.infra.service;

import org.springframework.stereotype.Service;

import br.com.b2sky.infra.beans.Planet;
import br.com.b2sky.infra.errors.PlanetNotFoundException;
import br.com.b2sky.infra.log.IntegrationMonitorLog;
import br.com.b2sky.infra.repo.PlanetRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
public class PlanetService {
	private final PlanetRepository repository;
	private final StarWarApiService swapiService;

	public PlanetService(PlanetRepository repository, StarWarApiService swapiService) {
		super();
		this.repository = repository;
		this.swapiService = swapiService;
	}

	/**
	 * Retorna a ultima ocorrencia de um {@link Planet} no banco de dados. Caso o
	 * planeta não exista será realizada a consulta para SWAPI
	 * 
	 * @param name
	 *            - nome do planeta
	 * @return {@link Mono}<{@link Planet}>
	 */
	public Mono<Planet> find(String name) {
		return retrieveByName(name).next().switchIfEmpty(getPlanetFromSWAPI(name));
	}

	private Mono<Planet> getPlanetFromSWAPI(String name) {
		Mono<String> just = Mono.just(name);
		
		return swapiService.getAllPlanets()
				.zipWith(just)
				.filter(this::byName)
				.switchIfEmpty(planetNotFound(name))
				.next()
				.map(Tuple2::getT1);
	}

	private Mono<Tuple2<Planet, String>> planetNotFound(String name) {
		return Mono.error(new PlanetNotFoundException(name));
	}

	private boolean byName(Tuple2<Planet, String> p) {
		Planet t1 = p.getT1();
		String name = p.getT2();
		
		return name.equals(t1.getName());
	}

	/**
	 * Realiza o acesso ao banco de dados da app
	 * @param name - nome do planeta
	 * @return
	 */
	private Flux<Planet> retrieveByName(String name) {
		return repository.findByName(name)
				.onErrorResume(this::emptyReturn);
	}
	
	/**
	 * Retorna vazio a partir de um {@link Throwable}
	 * @param e
	 * @return
	 */
	private Flux<Planet> emptyReturn(Throwable e) {
		return errorLog(e).thenMany(Mono.empty());
	}
	
	/**
	 * Log de erro ao consultar o banco de dados
	 * @param e
	 * @return
	 */
	private Mono<Void> errorLog(Throwable e) {
		return IntegrationMonitorLog.builder(getClass())
				.error("Erro ao acessar banco de dados")
				.exception((Exception) e)
				.build();
	}
}
