package it.uiip.digitalgarage.roboadvice.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.operator.DefaultStrategyOperator;
import it.uiip.digitalgarage.roboadvice.service.dto.DefaultStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class DefaultStrategyController extends GenericController {
	
	@RequestMapping("/getDefaultStrategySet")
	@ResponseBody
	public GenericResponse<?> getDefaultStrategySet() {
		this.defaultStrategyOp = new DefaultStrategyOperator(this.defaultStrategyRep);
		List<DefaultStrategyDTO> assets = this.defaultStrategyOp.getDefaultStrategySet();
		return new GenericResponse<List<DefaultStrategyDTO>>(1, assets);
	}

}