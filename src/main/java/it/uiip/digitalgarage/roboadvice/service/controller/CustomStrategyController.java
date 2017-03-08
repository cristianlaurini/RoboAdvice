package it.uiip.digitalgarage.roboadvice.service.controller;

import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;
import it.uiip.digitalgarage.roboadvice.service.util.GenericResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
public class CustomStrategyController extends AbstractController {

	@RequestMapping("/setCustomStrategy")
	@ResponseBody
    public GenericResponse<?> setCustomStrategy(@Valid @RequestBody CustomStrategyDTO request){
		this.customStrategyOp.setCustomStrategy(request);
		return new GenericResponse<String>(1, "Done");
    }
    
    @RequestMapping("/getUserCustomStrategySet")
    @ResponseBody
    public GenericResponse<?> getUserCustomStrategySet(@Valid @RequestBody UserLoggedDTO user){
    	List<CustomStrategyResponseDTO> result = this.customStrategyOp.getUserCustomStrategySet(user);
    	if(result == null) {
    		return new GenericResponse<String>(0, "This user doesn't have any strategy");
    	}
        return new GenericResponse<List<CustomStrategyResponseDTO>>(1, result);
    }

    @RequestMapping("/getActiveUserCustomStrategy")
    @ResponseBody
    public GenericResponse<?> getUserCustomStrategyActive(@Valid @RequestBody UserLoggedDTO user){
    	CustomStrategyResponseDTO result = this.customStrategyOp.getActiveUserCustomStrategy(user);
    	if(result == null) {
    		return new GenericResponse<String>(0, "This user doesn't have any active strategy");
    	}
    	return new GenericResponse<CustomStrategyResponseDTO>(1, result);
    }

}
