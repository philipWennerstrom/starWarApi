/**
 * 
 */
package br.com.b2sky.infra.service;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.builder.ConfigurableConditionExpression;
import org.springframework.stereotype.Service;

import br.com.b2sky.infra.beans.Planet;
import br.com.b2sky.infra.starwarapi.beans.StarWarPlanet;

/**
 * @author luiz.philip
 *
 */
@Service
public class ModelMappingService {
	private final ModelMapper modelMapper;

	public ModelMappingService() {
		super();
		this.modelMapper = new ModelMapper();
	}
	
	@PostConstruct
	public void init() {
		modelMapper.typeMap(StarWarPlanet.class, Planet.class).addMappings(this::mappingConfig);
	}
	
	private void mappingConfig(ConfigurableConditionExpression<StarWarPlanet, Planet> mapper) {
		mapper.map(StarWarPlanet::getName, Planet::setName);
		mapper.map(StarWarPlanet::getClimate, Planet::setClima);
		mapper.map(StarWarPlanet::getFilms, Planet::setFilms);
		mapper.map(StarWarPlanet::getTerrain, Planet::setTerreno);
	}
	
	public <T> T map(Object a, Class<T> classType) {   
	    return modelMapper.map(a, classType);
	}
}
