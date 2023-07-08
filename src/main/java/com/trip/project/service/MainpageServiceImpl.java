package com.trip.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.project.dto.CourseDTO;
import com.trip.project.dto.MainpageDTO;
import com.trip.project.dto.PlaceDTO;
import com.trip.project.mapper.CourseMapper;
import com.trip.project.mapper.MainpageMapper;

@Service
public class MainpageServiceImpl implements MainpageService{
	
	@Autowired
	private MainpageMapper mainpageMapper;

	@Autowired
	private CourseMapper cMapper;
	
	@Override
	public List<MainpageDTO> selectList() {
		
		return mainpageMapper.selectList();
	}
	
	@Override
	public List<MainpageDTO> animalList() {
		
		return mainpageMapper.animalList();
	}
	
	@Override
	public List<MainpageDTO> parentsList() {
		
		return mainpageMapper.parentsList();
	}
	
	@Override
    public PlaceDTO getPlaceInfo(String placeName) {
        
		return mainpageMapper.getPlaceInfo(placeName);
    }

	@Override
	public List<CourseDTO> courseCountMain() {

		return cMapper.courseCountMain();
	}
}
