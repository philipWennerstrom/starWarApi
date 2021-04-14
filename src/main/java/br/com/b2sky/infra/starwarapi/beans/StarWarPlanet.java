
package br.com.b2sky.infra.starwarapi.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "climate",
    "created",
    "diameter",
    "edited",
    "films",
    "gravity",
    "name",
    "orbital_period",
    "population",
    "residents",
    "rotation_period",
    "surface_water",
    "terrain",
    "url"
})
@Getter
@Setter
@NoArgsConstructor
public class StarWarPlanet {

    @JsonProperty("climate")
    private String climate;
    @JsonProperty("created")
    private String created;
    @JsonProperty("diameter")
    private String diameter;
    @JsonProperty("edited")
    private String edited;
    @JsonProperty("films")
    private List<String> films = null;
    @JsonProperty("gravity")
    private String gravity;
    @JsonProperty("name")
    private String name;
    @JsonProperty("orbital_period")
    private String orbitalPeriod;
    @JsonProperty("population")
    private String population;
    @JsonProperty("residents")
    private List<String> residents = null;
    @JsonProperty("rotation_period")
    private String rotationPeriod;
    @JsonProperty("surface_water")
    private String surfaceWater;
    @JsonProperty("terrain")
    private String terrain;
    @JsonProperty("url")
    private String url;
}