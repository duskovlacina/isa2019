package company.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import company.domain.Destination;
import company.domain.DestinationDate;
import company.domain.DestinationTime;
import company.domain.Reservation;
import company.domain.Seat;
import company.domain.User;
import company.repository.DestinationDateRepository;
import company.repository.DestinationRepository;
import company.repository.DestinationTimeRepository;
import company.repository.ReservationRepository;
import company.repository.SeatRepository;
import company.repository.UserRepository;

@Service
public class DestinationTimeServiceImpl implements DestinationTimeService {

	@Autowired
	private DestinationTimeRepository dtrepository;
	
	@Autowired
	private DestinationDateRepository ddrepository;
	
	@Autowired
	private DestinationRepository destrepository;
	
	@Autowired
	private SeatRepository seatrepository;
	
	@Autowired
	private ReservationRepository reservationrepository;

	@Autowired
	private UserRepository userrepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<DestinationTime> findByDestinationDate(Long destinationDateId) {
		DestinationDate dd = ddrepository.findOne(destinationDateId);
		return dtrepository.findByDestinationDate(dd);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	public DestinationTime save(DestinationTime destinationTime) {
		return dtrepository.save(destinationTime);
	}

	@Override
	@Transactional(readOnly = true)
	public DestinationTime findOne(Long dtId) {
		return dtrepository.findOne(dtId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation=Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
	public Reservation reserveSeats(Long destinationtimeId, List<String> seatinfo, Long userId) {
		//adding to taken seats
				DestinationTime dt = dtrepository.findOne(destinationtimeId);
				Hibernate.initialize(dt.getTakenSeats());
				List<Seat> resSeats = new ArrayList<Seat>();
				for(int i=0;i<seatinfo.size();i++) {
					String arr[] = seatinfo.get(i).split("_");
					Seat seat =seatrepository.findByFlightAndRowAndSeatInRow(dt.getFlights(), Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
					if(dt.getTakenSeats().contains(seat)) {
						return null;
					}
					resSeats.add(seat);
					dt.getTakenSeats().add(seat);
				}
				dtrepository.save(dt);
				//end
				//saving reservation
				User user = userrepository.findOne(userId);
				Reservation reservation = new Reservation(user,dt,resSeats);
				reservationrepository.save(reservation);
				Hibernate.initialize(user.getReservations());
				user.getReservations().add(reservation);
				userrepository.save(user);
				//end
				return reservation;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Seat> getTakenSeats(Long destinationtimeId) {
		DestinationTime dt = dtrepository.findOne(destinationtimeId);
		Hibernate.initialize(dt.getTakenSeats());
		return dt.getTakenSeats();
	}

	@Override
	public List<DestinationTime> getTimessForDestination(Long destinationId) {
		DestinationDate destination = ddrepository.findOne(destinationId);		
		return dtrepository.findByDestinationDate(destination);
	}

	@Override
	public List<DestinationTime> searchDestination(String destination, Date date) {
		System.out.println("implementacijjaaa");
		List<Destination> destinationName = destrepository.findByNameContaining(destination);
		List<DestinationTime> dt = new ArrayList<>();
		for(Destination d : destinationName) {
			for(DestinationDate dd : d.getDestDate())
			{
				if(dd.getDestinationDate().equals(date))
				{
					dt.addAll(dd.getDestinationTime());
				}
			}
		}
			
		return dt;
		}


}
