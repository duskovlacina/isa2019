package company.service;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import company.domain.AvioCompany;
import company.domain.Destination;
import company.repository.AvioCompanyRepository;



@Service
public class AvioCompanyServiceImpl implements AvioCompanyService {

	@Autowired
	private AvioCompanyRepository avcrepository;
	
	@Override
	public AvioCompany save(AvioCompany avCompany) {
		return avcrepository.save(avCompany);
	}

	@Override
	public List<Destination> getDestinations(Long avId) {
		AvioCompany av = avcrepository.findOne(avId);
		Hibernate.initialize(av.getDestinations());
		return av.getDestinations();
	}

	@Override
	public AvioCompany findOne(Long id) {
		return avcrepository.findOne(id);
	}

	@Override
	public List<AvioCompany> getAll() {
		return avcrepository.findAll();
	}
	
	@Override
	public AvioCompany modify(AvioCompany avCompany, Long id) {
		AvioCompany av = avcrepository.findOne(id);
		if(avCompany.getName() != null) {
			av.setName(avCompany.getName());
		}
		if(avCompany.getAddress() != null) {
			av.setAddress(avCompany.getAddress());
		}
		if(avCompany.getDescription() != null) {
			av.setDescription(avCompany.getDescription());
		}
		
		return avcrepository.save(av);
	}
	

	@Override
	public List<AvioCompany> searchAV(String name, String address) {
		if(!name.equals("nema") && !address.equals("nema")){
			return avcrepository.findByNameContainingAndAddressContaining(name, address);
		}else if(!name.equals("nema") && address.equals("nema")){
			return avcrepository.findByNameContaining(name);
		}else if(name.equals("nema") && !address.equals("nema")){
			return avcrepository.findByAddressContaining(address);
		}else{
			return avcrepository.findAll();
		}
	}

}
