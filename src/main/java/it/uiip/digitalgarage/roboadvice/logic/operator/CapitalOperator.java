package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.CapitalEntity;
import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.CapitalResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.UserLoggedDTO;

@Service
public class CapitalOperator extends AbstractOperator {
	
	@Autowired
	private PortfolioOperator portfolioOp;
	
	public CapitalResponseDTO getCurrentCapital(UserLoggedDTO user) {
		CapitalEntity entity = this.capitalRep.findLast(user.getId());
		if(entity == null) {
			return null;
		}
		return (CapitalResponseDTO) this.capitalConv.convertToDTO(entity);
	}
	
	public void addCapital(CapitalDTO capital) {
		CapitalEntity entity = this.capitalConv.convertToEntity(capital);
		entity.setDate(LocalDate.now());
		CapitalEntity saved = this.capitalRep.findByUserIdAndDate(entity.getUser().getId(), entity.getDate());
		if(saved == null) {
			saved = this.capitalRep.findLast(entity.getUser().getId());
			if(saved != null) {
				entity.setAmount(entity.getAmount().add(saved.getAmount()));
			}
			this.capitalRep.save(entity);
		} else {
			BigDecimal newAmount = entity.getAmount().add(saved.getAmount());
			this.capitalRep.updateCapital(entity.getUser().getId(), entity.getDate().toString(), newAmount);
		}
	}

	public boolean computeCapital(UserLoggedDTO user) {
		CapitalEntity capitalEntity = new CapitalEntity();
		UserEntity userEntity = new UserEntity();
		BigDecimal amount = portfolioOp.evaluatePortfolio(user);
		if(amount == null) {
			return false;
		}
		LocalDate currentDate = LocalDate.now();
		userEntity.setId(user.getId());
		capitalEntity.setUser(userEntity);
		capitalEntity.setAmount(amount);
		capitalEntity.setDate(currentDate);
		CapitalEntity saved = this.capitalRep.findByUserIdAndDate(user.getId(), currentDate);
		if(saved == null) {
			this.capitalRep.save(capitalEntity);
		} else {
			this.capitalRep.updateCapital(user.getId(), currentDate.toString(), amount);
		}
		return true;
	}
	
}
