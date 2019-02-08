package company.service;

import java.util.List;

import company.domain.AvioCompany;
import company.domain.Destination;

public interface AvioCompanyService {

	AvioCompany save (AvioCompany avCompany);
	
	AvioCompany modify(AvioCompany avCompany,Long id);
	
	List<AvioCompany> searchAV(String name, String address);
	
	List<Destination> getDestinations(Long cvId);
	
	AvioCompany findOne(Long id);
	
	List<AvioCompany> getAll();
}
