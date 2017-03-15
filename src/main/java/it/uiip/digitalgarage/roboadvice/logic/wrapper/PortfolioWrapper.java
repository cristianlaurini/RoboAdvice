package it.uiip.digitalgarage.roboadvice.logic.wrapper;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.PortfolioEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioElementDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortfolioWrapper implements GenericWrapper<PortfolioEntity, PortfolioDTO> {

    @Override
    public List<PortfolioEntity> unwrapToEntity(PortfolioDTO dto) {
    	List<PortfolioEntity> entityList = new ArrayList<>();
    	UserEntity user = new UserEntity();
    	for (PortfolioElementDTO element : dto.getList()) {
			PortfolioEntity entity = new PortfolioEntity();
			AssetClassEntity assetClass = new AssetClassEntity();
			assetClass.setId(element.getId());
			assetClass.setName(element.getName());
			entity.setAssetClass(assetClass);
			entity.setDate(LocalDate.parse(dto.getDate()));
			entity.setUser(user);
			entity.setValue(element.getValue());
		}
    	return entityList;
    }

    @Override
    public PortfolioDTO wrapToDTO(List<PortfolioEntity> entityList) {
    	PortfolioDTO dto = new PortfolioDTO();
    	List<PortfolioElementDTO> elements = new ArrayList<>();
    	dto.setDate(entityList.get(0).getDate().toString());
    	createPortfolioElement(entityList, elements);
    	dto.setList(elements);
    	return dto;
    }

	private void createPortfolioElement(List<PortfolioEntity> entityList, List<PortfolioElementDTO> elements) {
		BigDecimal totalCapital = getTotalCapital(entityList);
		Map<Long, List<PortfolioEntity>> map = createMap(entityList);
    	for (Long idAssetClass : map.keySet()) {
			BigDecimal sum = new BigDecimal(0);
			PortfolioElementDTO element = new PortfolioElementDTO();
			for (PortfolioEntity entity : map.get(idAssetClass)) {
				sum = sum.add(entity.getValue());
			}
			element.setValue(sum);
			BigDecimal percentage = sum.divide(totalCapital, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100.00));
			element.setPercentage(percentage);			
			element.setId(idAssetClass);
			element.setName(map.get(idAssetClass).get(0).getAssetClass().getName());
			element.setId(idAssetClass);
			element.setName(map.get(idAssetClass).get(0).getAssetClass().getName());
			elements.add(element);
		}
	}

	private BigDecimal getTotalCapital(List<PortfolioEntity> entityList) {
		BigDecimal totalCapital = new BigDecimal(0);
		for (PortfolioEntity entity : entityList) {
			totalCapital = totalCapital.add(entity.getValue());
		}
		return totalCapital;
	}

	private Map<Long, List<PortfolioEntity>> createMap(List<PortfolioEntity> entityList) {
		Map<Long, List<PortfolioEntity>> map = new HashMap<>();
    	for (PortfolioEntity entity : entityList) {
    		if(map.get(entity.getAssetClass().getId()) == null) {
    			map.put(entity.getAssetClass().getId(), new ArrayList<>());
    		}
			map.get(entity.getAssetClass().getId()).add(entity);
		}
		return map;
	}

}
