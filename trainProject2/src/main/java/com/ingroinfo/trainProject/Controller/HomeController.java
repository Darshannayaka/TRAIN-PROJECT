package com.ingroinfo.trainProject.Controller;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ingroinfo.trainProject.Repository.AddTrainsRepository;
import com.ingroinfo.trainProject.Repository.MyBookingRepository;
import com.ingroinfo.trainProject.Repository.PassengerDetailsRepository;
import com.ingroinfo.trainProject.Repository.UserRepository;
import com.ingroinfo.trainProject.entities.AddTrains;
import com.ingroinfo.trainProject.entities.Message;
import com.ingroinfo.trainProject.entities.MyOrder;
import com.ingroinfo.trainProject.entities.PassengerDetails;
import com.ingroinfo.trainProject.entities.User;
import com.ingroinfo.trainProject.entities.UserBookingDTO;
import com.ingroinfo.trainProject.service.JReportService;

import net.sf.jasperreports.engine.JRException;

@SpringBootApplication
@Controller
//@RequestMapping("/home")
public class HomeController {

	@Autowired
	private AddTrainsRepository addTrainsRepository;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private MyBookingRepository myBookingRepository;
	
	@Autowired
	private PassengerDetailsRepository passengerDetailsRepository;
	
	
	@Autowired
	private JReportService service;
	 
	 
	@GetMapping("/")
	public String viewIndexPage() {
		return "index";
	}

	@GetMapping("/home")
	public String viewHomePage() {
		return "index";
	}

//	@GetMapping("/aboutUs")
//	public String viewAboutUs() {
//		return "aboutUs";
//	}
	
	@GetMapping("/about")
	public String viewAboutUs() {
		return "about";
	}

//	@GetMapping("/contactUs")
//	public String viewContactUs() {
//		return "contactUs";
//	}
	
	@GetMapping("/contact")
	public String viewContactUs() {
		return "contact";
	}

	@GetMapping("/register")
	public String viewSignUpForm(Model model) {
		model.addAttribute("user", new User());
		return "signup_form";
	}
	

	@PostMapping("/process_register")
	public String processRegistration(@ModelAttribute User user,HttpSession session) {
		boolean f = this.userRepo.existsByEmail(user.getEmail());
		if (f) {
			session.setAttribute("message", new Message("Email Alredy Exist !!","danger"));
			System.out.println("Email Already Exist !!");
		}else {
			session.setAttribute("message", new Message("User Data Saved Successfully !!","success"));
			//password encrypted
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encodedPassword = encoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			user.setRole("ROLE_USER");
			this.userRepo.save(user);
		}		
		return "redirect:/register";
	}
	
	@GetMapping("/loginPage")
	public String viewLoginForm() {
		return "userLogin";
	}

	@GetMapping("/HomeSearchTrain")
	public String SearchTrainsPage() {
		return "Home-sarchTrains";
	}
	
	@GetMapping("/gallery")
	public String Gallery() {
		return "team.html";
	}
	
	@GetMapping("/directors")
	public String Directors() {
		return "testimonial.html";
	}
	
	@GetMapping("/service")
	public String serive() {
		return "service.html";
	}
	
	@RequestMapping("/homeTrains")
	public String SearchTrains(@RequestParam("trainFrom") String trainFrom, @RequestParam("trainTo") String trainTo,
			@RequestParam("departureDate") @DateTimeFormat(pattern = "MM-dd-yyyy") String departureDate,
			ModelMap modelMap) {
		List<AddTrains> findTrains = addTrainsRepository.findTrains(trainFrom, trainTo, departureDate);
		modelMap.addAttribute("homeTrains", findTrains);
		return "Home_Show_Trains";
	}

//	@GetMapping("/userLogin")
//	public String viewUserLoginForm() {
//		return "userLogin";
//	}
	

	 
//	    @GetMapping("/getAddress")
//	    public List<PassengerDetails> getAddress() {
//	        List<PassengerDetails> address = (List<PassengerDetails>) passengerDetailsRepository.findAll();
//	        return address;
//	    }
	 
	         
//	    @GetMapping("/jasperpdf/export")
//	    public void createPDF(HttpServletResponse response) throws IOException, JRException {
//	        response.setContentType("application/pdf");
//	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
//	        String currentDateTime = dateFormatter.format(new Date());
//	  
//	        String headerKey = "Content-Disposition";
//	        String headerValue = "attachment; filename=BookingTicket_" + currentDateTime + ".pdf";
//	        response.setHeader(headerKey, headerValue);
//	  
//	        service.exportJasperReport(response);
//	    }
	 
	  
	 
//	@GetMapping("/jasperpdf/export")
//	public void createPDF(HttpServletResponse response,Principal principal) throws IOException, JRException {
//	    response.setContentType("application/pdf");
//	    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
//	    String currentDateTime = dateFormatter.format(new Date());
//
//	    String headerKey = "Content-Disposition";
//	    String headerValue = "attachment; filename=BookingTicket_" + currentDateTime + ".pdf";
//	    response.setHeader(headerKey, headerValue);
//
//	   // List<PassengerDetails> passengerDetailsList = passengerDetailsRepository.findAll();
//	   // List<MyOrder> bookingDetailsList = myBookingRepository.findAll();
//	    
//	    String userName = principal.getName();
//	    User user = this.userRepo.getUserByUserEmail(userName);
//
//	    List<PassengerDetails> passengerDetailsList = this.passengerDetailsRepository.findPassengerDetailsByUser(user.getId());
//	    List<MyOrder> bookingDetailsList = this.myBookingRepository.findMyOrderByUser(user.getId());
//
//	    
//	    service.exportJasperReport(response, passengerDetailsList, bookingDetailsList);
//	}

	//1
	
//	@GetMapping("/booking-details")
//	public String userbook(Model model, Principal principal) {
//	    String userName = principal.getName();
//	    User user = this.userRepository.getUserByUserEmail(userName);
//
//	    List<PassengerDetails> paymentBookList = this.passengerDetailsRepository.findPassengerDetailsByUser(user.getId());
//	    List<MyOrder> bookTicketList = this.myBookingRepository.findMyOrderByUser(user.getId());
//
//	    List<UserBookingDTO> userBookingList = new ArrayList<>();
//
//	    // Combine the data from PaymentBook and BookTicket entities into UserBookingDTO objects
//	    for (int i = 0; i < bookTicketList.size(); i++) {
//	    	
//	    	
//	        UserBookingDTO userBookingDTO = new UserBookingDTO();
//	        userBookingDTO.setPassengerDetails(paymentBookList.get(i));
//	        userBookingDTO.setMyBookTicket(bookTicketList.get(i));
//
//	        userBookingList.add(userBookingDTO);
//	    }

	
	
	}



