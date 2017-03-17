package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.AssetClassDTO;

@Service
public class AssetClassOperator extends AbstractOperator {

	@Cacheable("assetClassSet")
	public List<AssetClassDTO> getAssetClassSet() {
		List<AssetClassEntity> list = this.assetClassRep.findAll();
		return this.assetClassConv.convertToDTO(list);
	}

}
