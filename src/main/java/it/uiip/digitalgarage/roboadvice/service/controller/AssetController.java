package it.uiip.digitalgarage.roboadvice.service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.uiip.digitalgarage.roboadvice.logic.operator.AssetOperator;
import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetEntity;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;

@CrossOrigin("*")
@RestController
public class AssetController extends GenericController {

	@RequestMapping("/getAssetSet")
	@ResponseBody
	public GenericResponse<?> getAssetSet() {
		this.assetOp = new AssetOperator(this.assetRep);
		List<AssetEntity> result = this.assetOp.getAssetSet();
		return new GenericResponse<List<AssetEntity>>(1, result);
	}
	
}