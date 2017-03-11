package it.uiip.digitalgarage.roboadvice.persistence.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.uiip.digitalgarage.roboadvice.persistence.entity.AssetClassEntity;

@Repository
@Transactional
public interface AssetClassRepository extends PagingAndSortingRepository<AssetClassEntity, Long> {

	public List<AssetClassEntity> findAll();

	public AssetClassEntity findById(Long id);
		
}