package com.myhanoli.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.myhanoli.springboot.app.models.entity.Cliente;
import com.myhanoli.springboot.app.models.entity.Factura;
import com.myhanoli.springboot.app.models.entity.Gasto;
import com.myhanoli.springboot.app.models.entity.Producto;

public interface IGastoService {

	public List<Gasto> findAll();
	
	public Page<Gasto> findAll(Pageable pageable);

	public void save(Gasto gasto);
	
	public Gasto findOne(Long id);
	
	public void delete(Long id);
	
		
	

}