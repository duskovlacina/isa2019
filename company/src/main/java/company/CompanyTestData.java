package company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import company.domain.AvioCompany;
import company.domain.Destination;
import company.domain.DestinationDate;
import company.domain.DestinationTime;
import company.domain.Flight;
import company.domain.Seat;
import company.domain.User;
import company.domain.UserType;
import company.service.AvioCompanyService;
import company.service.DestinationDateService;
import company.service.DestinationService;
import company.service.DestinationTimeService;
import company.service.FlightService;
import company.service.SeatService;
import company.service.UserService;

@Component
public class CompanyTestData {

	@Autowired
	private UserService userservice;
	
	@Autowired
	private AvioCompanyService acservice;
	
	@Autowired
	private DestinationService destservice;
	
	@Autowired
	private DestinationDateService destDateservice;
	
	@Autowired
	private DestinationTimeService dtservice;
	
	@Autowired
	private FlightService flservice;
	
	@Autowired
	private SeatService seatservice;
	
	@PostConstruct
	private void init() throws ParseException{ 
		
		if(userservice.findAll().size() == 0) {
			
			User user1 = new User("vlacina2@gmail.com","dusko","Dusko","Vlacina","Novi Sad","6611632",UserType.REGULAR);
			user1.setVerified(true);
			userservice.save(user1);
			
			User user2 = new User("jandro_tolis@gmail.com","dusko","Andrej","Radic","Novi Sad","6611632",UserType.REGULAR);
			user2.setVerified(true);
			userservice.save(user2);
			
			User user3 = new User("user3@gmail.com","dusko","Suba","Subasic","Novi Sad","6611632",UserType.AVIOADMIN);
			user3.setVerified(true);
			userservice.save(user3);
			
			AvioCompany av1 = new AvioCompany("Modena","Novi Sad","dobar","2.4");
			acservice.save(av1);
			
			AvioCompany av2 = new AvioCompany("Lufthansa","Berlin","gut","4.0");
			acservice.save(av2);
			
			Destination d1 = new Destination("Maldivi",1,av1);
			Hibernate.initialize(d1.getAvioCompany());
			d1.setAvioCompany(av1);
			Hibernate.initialize(d1.getDestDate());
			destservice.save(d1);

			
			Destination d2 = new Destination("Colombo",5,av1);
			Hibernate.initialize(d2.getAvioCompany());
			d2.setAvioCompany(av1);
			Hibernate.initialize(d2.getDestDate());
			destservice.save(d2);
			
			Hibernate.initialize(av1.getDestinations());
			av1.getDestinations().add(d1);
			av1.getDestinations().add(d2);
			//acservice.save(av1);     --nakon sto sve dodas sacuvati, zakometarisano jer je save kod letova
			
			Destination d3 = new Destination("Pariz",3,av2);
			Hibernate.initialize(d3.getAvioCompany());
			d3.setAvioCompany(av2);
			destservice.save(d3);
			
			Hibernate.initialize(av2.getDestinations());
			av2.getDestinations().add(d3);
			acservice.save(av2);
			
			//add to avioCompany
			//Hibernate.initialize(av1.getDestinations());
			//Hibernate.initialize(av2.getDestinations());
			
			//av1.getDestinations().add(d1);
			//av1.getDestinations().add(d2);
			//av2.getDestinations().add(d3);
			
			
			// flights
			Flight f1 = new Flight(1,8,8,av1);
			flservice.save(f1);
			Hibernate.initialize(f1.getSeats());
			for(int i=1;i<=f1.getRows();i++) {
				for(int j=1;j<=f1.getSeatsPerRow();j++) {
					Seat seat = new Seat(i,j,f1);
					seatservice.save(seat);
					f1.getSeats().add(seat);	
				}
			}
			flservice.save(f1);
			
			
			Flight f2 = new Flight(2,8,10,av1);
			flservice.save(f2);
			Hibernate.initialize(f2.getSeats());
			for(int i=1;i<=f2.getRows();i++) {
				for(int j=1;j<=f2.getSeatsPerRow();j++) {
					Seat seat = new Seat(i,j,f2);
					seatservice.save(seat);
					f2.getSeats().add(seat);	
				}
			}
			flservice.save(f2);
			
			Hibernate.initialize(av1.getFlights());
			av1.getFlights().add(f1);
			av1.getFlights().add(f2);
			acservice.save(av1);
			
			
			//destination dates
			DestinationDate dd1 = new DestinationDate(d1,new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-27"));
			DestinationDate dd2 = new DestinationDate(d2,new SimpleDateFormat("yyyy-MM-dd").parse("2019-04-17"));
			/*
			DestinationDate dd1 = new DestinationDate(d1, LocalDate.of(2018,12,27));
			DestinationDate dd2 = new DestinationDate(d2, LocalDate.of(2021,12,27));
			DestinationDate dd3 = new DestinationDate(d2, LocalDate.of(2022,12,27));
			DestinationDate dd4 = new DestinationDate(d2, LocalDate.of(2024,12,27)); */
			DestinationDate dd3 = new DestinationDate(d1,new SimpleDateFormat("yyyy-MM-dd").parse("2021-02-05"));
			DestinationDate dd4 = new DestinationDate(d2,new SimpleDateFormat("yyyy-MM-dd").parse("2020-04-17"));
			
			destDateservice.save(dd1);
			destDateservice.save(dd2);
			destDateservice.save(dd3);
			destDateservice.save(dd4);

			Hibernate.initialize(d1.getDestDate());
			d1.getDestDate().add(dd1);
			d1.getDestDate().add(dd3);
			
			Hibernate.initialize(d2.getDestDate());
			d2.getDestDate().add(dd2);
			d2.getDestDate().add(dd4);

			destservice.save(d1);
			destservice.save(d2);
			
			//destinationTimes
			DestinationTime dt1 = new DestinationTime(dd1,f1,new SimpleDateFormat("HH:mm").parse("10:00"),new SimpleDateFormat("HH:mm").parse("21:00"),
					new SimpleDateFormat("yyyy-MM-dd").parse("2019-02-27"),3.0,1002,1,"Dublin",200);
			DestinationTime dt2 = new DestinationTime(dd2,f1,new SimpleDateFormat("HH:mm").parse("12:00"),new SimpleDateFormat("HH:mm").parse("17:00"),
					new SimpleDateFormat("yyyy-MM-dd").parse("2020-12-27"),5.5,100,1,"Birmingen",250);
			DestinationTime dt3 = new DestinationTime(dd3,f2,new SimpleDateFormat("HH:mm").parse("14:00"),new SimpleDateFormat("HH:mm").parse("22:00"),
					new SimpleDateFormat("yyyy-MM-dd").parse("2020-02-07"),3.0,1002,1,"Pristina",100);
			DestinationTime dt4 = new DestinationTime(dd4,f2,new SimpleDateFormat("HH:mm").parse("18:00"),new SimpleDateFormat("HH:mm").parse("21:00"),
					new SimpleDateFormat("yyyy-MM-dd").parse("2019-02-17"),3.0,1002,1,"Beograd",200);
			
			dtservice.save(dt1);
			dtservice.save(dt2);
			dtservice.save(dt3);
			dtservice.save(dt4);
			
			Hibernate.initialize(dd1.getDestinationTime());
			dd1.getDestinationTime().add(dt1);
			Hibernate.initialize(dd2.getDestinationTime());
			dd2.getDestinationTime().add(dt2);
			Hibernate.initialize(dd3.getDestinationTime());
			dd3.getDestinationTime().add(dt3);
			Hibernate.initialize(dd4.getDestinationTime());
			dd4.getDestinationTime().add(dt4);
			
			destDateservice.save(dd1);
			destDateservice.save(dd2);
			destDateservice.save(dd3);
			destDateservice.save(dd4);
			
			
		}
	}
	
}

