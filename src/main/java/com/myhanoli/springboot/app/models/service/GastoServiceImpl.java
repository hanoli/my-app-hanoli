package com.myhanoli.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.myhanoli.springboot.app.models.dao.IGastoDao;
import com.myhanoli.springboot.app.models.entity.Gasto;


@Service
public class GastoServiceImpl implements IGastoService{

	@Autowired
	private IGastoDao gastoDao;

	
	@Override
	@Transactional(readOnly = true)
	public List<Gasto> findAll() {
		// TODO Auto-generated method stub
		return (List<Gasto>) gastoDao.findAll();
	}

	@Override
	@Transactional
	public void save(Gasto gasto) {
		gastoDao.save(gasto);
	}

	@Override
	@Transactional(readOnly = true)
	public Gasto findOne(Long id) {
		return gastoDao.findById(id).orElse(null);
	}
	


	@Override
	@Transactional
	public void delete(Long id) {
		gastoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Gasto> findAll(Pageable pageable) {
		return gastoDao.findAll(pageable);
	}

	
	
	
}
