package it.uiip.digitalgarage.roboadvice.logic.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "financial_data")
public @Data class FinancialDataEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@ManyToOne()
	@JoinColumn(name = "id_asset", nullable = false)
    private AssetEntity asset;
	
	@Column(name = "value", nullable = false)
    private BigDecimal value;
	
	@Column(name = "date", nullable = false)
    private LocalDate date;
    
}