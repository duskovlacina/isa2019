package company.service;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import company.domain.AvioCompany;
import company.domain.Destination;
import company.domain.DestinationDate;
import company.domain.DestinationTime;
import company.domain.Seat;
import company.repository.AvioCompanyRepository;
import company.repository.DestinationDateRepository;
import company.repository.DestinationRepository;
import company.repository.DestinationTimeRepository;


@Service
public class DestinationServiceImpl implements DestinationService{

	@Autowired
	private DestinationRepository destrepository;
	
	@Autowired
	private DestinationDateRepository destDaterepository;
	
	@Autowired
	private AvioCompanyRepository avRepository;
	
	@Autowired
	private DestinationTimeRepository timeRepository;
	
	@Override
	public Destination save(Destination destination) {
		return destrepository.save(destination);
	}

	@Override
	public List<Destination> findAll() {
		return destrepository.findAll();
	}

	@Override
	public Destination findOne(Long id) {
		return destrepository.findOne(id);
	}

	@Override
	public Destination deleteDestination(Long id) {
		Destination destination = destrepository.findById(id);
		AvioCompany av = avRepository.findOne(destination.getAvioCompany().getId());
		Hibernate.initialize(av.getDestinations());
		Hibernate.initialize(destination.getDestDate());
		List<DestinationDate> destDate = destination.getDestDate();
		for(int i=0;i<destDate.size();i++) {
			Hibernate.initialize(destDate.get(i).getDestinationTime());
			List<DestinationTime> time = destDate.get(i).getDestinationTime();
			for(int j=0;j<time.size();j++)  {
				Hibernate.initialize(time.get(j).getTakenSeats());
				List<Seat> seats = time.get(j).getTakenSeats();
				for(int k=0;k<seats.size();k++) {
					seats.remove(seats.get(k));
				}
				time.remove(time.get(j));
				timeRepository.delete(time.get(j));
			}
			destDate.remove(destDate.get(i));
			destDaterepository.delete(destDate.get(i));
		}
		av.getDestinations().remove(destination);
		destrepository.delete(destination);
		return destination;
	}

	@Override
	public Destination modify(Destination destination, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Destination rateDestination(Long id, int grade) {
		Destination destination = destrepository.findOne(id);
	    float zbir = 0;
	    destination.getGrades().add(grade);
		for(int i=0;i<destination.getGrades().size();i++) {
			zbir += destination.getGrades().get(i);
		}
		System.out.println(destination.getGrades().size());
		float rezultat = (float) zbir/destination.getGrades().size();
		System.out.println(zbir);
		System.out.println(rezultat);
		destination.setAverageRating(rezultat);
		destrepository.save(destination);
		return destination;
	}

	@Override
	public List<Destination> searchDestination(String name) {
		if(!name.equals("nema")){
			return destrepository.findByNameContaining(name);
		}else{
			return destrepository.findAll();
		}
	}

}
