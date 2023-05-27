package com.customer.api.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.customer.api.dto.ApiResponse;
import com.customer.api.entity.Region;
import com.customer.api.repository.RepoRegion;
import com.customer.exception.ApiException;

@Service
public class SvcRegionImp implements SvcRegion {

	@Autowired
	RepoRegion repo;
	
	@Override
	public List<Region> getRegions() {
		return repo.findByStatus(1);
	}
	
	@Override
	public Region getRegion(Integer region_id) {
		Region region = repo.findByID(region_id);
		if (region == null)
			throw new ApiException(HttpStatus.BAD_REQUEST, "region does not exist");
		return region;
	}

	@Override
	public ApiResponse createRegion(Region region) {
		Region regionSaved = (Region) repo.findByRegion(region.getRegion());

		if (regionSaved != null) {
			if (regionSaved.getStatus() == 0) {
				repo.activateRegion(regionSaved.getRegion_id());
				return new ApiResponse("region has been activated");
			} else
				throw new ApiException(HttpStatus.BAD_REQUEST, "region already exists!");
		} else {
			//Si es null no existe y creamos una nueva region.
			repo.createRegion(region.getRegion());	
			return new ApiResponse("region created");
		}
	}

	@Override
	public ApiResponse updateRegion(Integer region_id, Region region) {
		Region regionSaved = repo.findByID(region_id);
		if (regionSaved != null) {
			if (regionSaved.getStatus() == 0) 
				return new ApiResponse("region is not active");
			else {
				regionSaved = (Region) repo.findByRegion(region.getRegion());
				if (regionSaved == null) {
					repo.updateRegion(region_id, region.getRegion());
					return new ApiResponse("region updated");
				} else
					return new ApiResponse("region already exists");
			}
		} else 
			return new ApiResponse("región does not exist");
	}

	@Override
	public ApiResponse deleteRegion(Integer region_id) {
		Region regionSaved = (Region) repo.findByID(region_id);
		if (regionSaved == null)
			return new ApiResponse("region does not exists, cannot be removed if it has clients");
		repo.deleteByID(region_id);
		return new ApiResponse("region removed");
	}
	

}
