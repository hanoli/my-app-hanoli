package com.myhanoli.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.myhanoli.springboot.app.models.entity.Gasto;

public interface IGastoDao extends PagingAndSortingRepository<Gasto, Long> {

	@Query("select c from Gasto c")
	public Gasto fetchByIdWithFacturas(Long id);
}
