package it.uiip.digitalgarage.roboadvice.logic.operator;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CustomStrategyEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.persistence.repository.CustomStrategyRepository;
import it.uiip.digitalgarage.roboadvice.persistence.repository.UserRepository;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyRequestDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomStrategyOperator extends AbstractOperator{

	public CustomStrategyOperator(CustomStrategyRepository customStrategyRep){
        this.customStrategyRep = customStrategyRep;
    }
	
	public CustomStrategyOperator(CustomStrategyRepository customStrategyRep, UserRepository userRep){
        this.customStrategyRep = customStrategyRep;
        this.userRep = userRep;
    }
    
    public void setCustomStrategy(CustomStrategyRequestDTO request){
    	this.customStrategyRep.setStrategyInactive(request.getIdUser());
    	UserEntity userEntity = this.userRep.findById(request.getIdUser());
    	List<CustomStrategyEntity> entityList = this.customStrategyConv.convertToEntity(request);
    	for (CustomStrategyEntity entity : entityList) {
			entity.setUser(userEntity);
			entity.setActive(true);
			entity.setDate(LocalDate.now());
		}
    	this.customStrategyRep.save(entityList);	    
    }

    public List<CustomStrategyDTO> getUserCustomStrategySet(UserLoggedDTO user){
    	List<CustomStrategyEntity> entityList = this.customStrategyRep.findByUserId(user.getId());
        Map<String, List<CustomStrategyEntity>> map = new HashMap<>();
        for (CustomStrategyEntity entity : entityList) {
			if(map.get(entity.getDate().toString()) == null) {
				map.put(entity.getDate().toString(), new ArrayList<>());
			}
			map.get(entity.getDate().toString()).add(entity);
		}
        List<CustomStrategyDTO> list = new ArrayList<>();
        for (String date : map.keySet()) {
			CustomStrategyDTO dto = this.customStrategyConv.convertToDTO(map.get(date));
			list.add(dto);
		}
    	return list;
    }

    public CustomStrategyDTO getActiveUserCustomStrategy(UserLoggedDTO user){
    	List<CustomStrategyEntity> entityList = this.customStrategyRep.findByUserIdAndActive(user.getId(), true);
    	if(entityList.size() == 0) {
    		return null;
    	}
    	CustomStrategyDTO result = this.customStrategyConv.convertToDTO(entityList);
    	return result;
    	
    }

}
