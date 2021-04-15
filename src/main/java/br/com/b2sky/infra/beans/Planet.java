package br.com.b2sky.infra.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Planet {
	private String id;
	private String name;
	private String clima;
	private String terreno;
	private String films;
	private String aparicoes;
}
