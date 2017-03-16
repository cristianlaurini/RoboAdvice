package it.uiip.digitalgarage.roboadvice.logic.operator;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import it.uiip.digitalgarage.roboadvice.persistence.entity.UserEntity;
import it.uiip.digitalgarage.roboadvice.service.dto.CustomStrategyResponseDTO;
import it.uiip.digitalgarage.roboadvice.service.dto.PortfolioDTO;

@Service
public class SchedulingOperator extends AbstractOperator {
	
	@Autowired 
	private UserOperator userOp;
	
	@Autowired
	private QuandlOperator quandlOp;
	
	@Autowired
	private PortfolioOperator portfolioOp;
	
	@Autowired
	private CapitalOperator capitalOp;
	
	@Autowired
	private CustomStrategyOperator customStrategyOp;
	
	@Scheduled(cron = "0 0 10 * * *")
	public void scheduleTask() {
		quandlOp.updateFinancialDataSet();
		List<UserEntity> users = userOp.getAllUsers();
		for (UserEntity user : users) {
			PortfolioDTO currentPortfolio = portfolioOp.getCurrentPortfolio(user);
			if(currentPortfolio == null) {
				boolean created = portfolioOp.createUserPortfolio(user);
				if(created) {
					System.out.println("Created portfolio for user: " + user.getId());
				}
				continue;
			}
			boolean computed = capitalOp.computeCapital(user);
			if(computed) {
				System.out.println("Computed capital for user: " + user.getId());
			}
			CustomStrategyResponseDTO strategy = customStrategyOp.getActiveStrategy(user);
			if(strategy != null && customStrategyOp.getCustomStrategySet(user, 0).size() > 1 && 
					(strategy.getDate().equals(LocalDate.now().toString()) || 
					 strategy.getDate().equals(LocalDate.now().minus(Period.ofDays(1)).toString()))) {
				boolean recreated = portfolioOp.createUserPortfolio(user);
				if(recreated) {
					System.out.println("Re-created portfolio for user: " + user.getId());
				}
				continue;
			}
			computed = portfolioOp.computeUserPortfolio(user);
			if(computed) {
				System.out.println("Computed portfolio for user: " + user.getId());
			}
		}
	}
}
