package com.ingroinfo.trainProject.Controller;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ingroinfo.trainProject.Repository.AddTrainsRepository;
import com.ingroinfo.trainProject.Repository.CancelTrainsRepository;
import com.ingroinfo.trainProject.Repository.MyBookingRepository;
import com.ingroinfo.trainProject.Repository.PassengerDetailsRepository;
import com.ingroinfo.trainProject.Repository.UserRepository;
import com.ingroinfo.trainProject.entities.AddTrains;
import com.ingroinfo.trainProject.entities.CancelTrains;
import com.ingroinfo.trainProject.entities.Message;
import com.ingroinfo.trainProject.entities.MyOrder;
import com.ingroinfo.trainProject.entities.PassengerDetails;
import com.ingroinfo.trainProject.entities.User;
import com.ingroinfo.trainProject.entities.UserBookingDTO;
import com.ingroinfo.trainProject.service.JReportService;
import com.ingroinfo.trainProject.service.MyDataService;
import com.razorpay.*;
//import com.sun.tools.javac.resources.compiler;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.repo.Resource;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private PassengerDetailsRepository passengerDetailsRepository;

	@Autowired
	private AddTrainsRepository addTrainsRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Autowired
	private MyBookingRepository myBookingRepository;
	
	@Autowired
	private CancelTrainsRepository cancelTrainsRepository;
	
	
	@Autowired
	private JReportService service;
	
	
	

	@RequestMapping("/login")
	public String SearchTrainPage() {
		return "user_dashboard";
	}

//	@RequestMapping("/login")
//	public String SearchTrainPage() {
//		return "search_train";
//	}

	@GetMapping("/searchTrain")
	public String SearchTrainsPage() {
		return "search_train";
	}

//	@PostMapping("/bookTrainTicket")
//	public String bookTicketDetails(@ModelAttribute PassengerDetails passengerDetails, @RequestParam("id") long id, Model model,Principal principal) {
//		
//		String email = principal.getName();
//		User user = this.userRepository.getUserByUserEmail(email);
//		passengerDetails.setUser(user);
//		user.getPassengerDetails().add(passengerDetails);
//		this.userRepository.save(user);
//		
//	//	passengerDetailsRepository.save(passengerDetails);
//		Optional<PassengerDetails> findById = passengerDetailsRepository.findById(id);
//		PassengerDetails passengerDetailss = findById.get();
//		model.addAttribute("passengerDetailss", passengerDetailss);
//		return "paymentsPages";
//	}
		
	@PostMapping("/bookTrainTicket")
	public String bookTicketDetails(@ModelAttribute PassengerDetails passengerDetails, @RequestParam("id") long id, Model model,Principal principal) {
		
		String email = principal.getName();
		User user = this.userRepository.getUserByUserEmail(email);
		passengerDetails.setUser(user);
		user.getPassengerDetails().add(passengerDetails);
		this.userRepository.save(user);
		
	//	passengerDetailsRepository.save(passengerDetails);
//		PassengerDetails passengerDetailss = passengerDetailsRepository.findById(id);
		model.addAttribute("passengerDetailss", passengerDetails);
		return "paymentPagess";
	}
	
	@RequestMapping("/trainBook")
	public String SearchTrains(@RequestParam("trainFrom") String trainFrom, @RequestParam("trainTo") String trainTo,
			@RequestParam("departureDate") @DateTimeFormat(pattern = "MM-dd-yyyy") String departureDate,
			ModelMap modelMap) {
		List<AddTrains> findTrains = addTrainsRepository.findTrains(trainFrom, trainTo, departureDate);
		modelMap.addAttribute("findTrains", findTrains);
		return "display_trains";
	}

	@GetMapping("/bookTicket/{id}")
	public String bookOneTrains(@PathVariable("id") Long id, Model model) {
		Optional<AddTrains> findById = addTrainsRepository.findById(id);
		AddTrains addTrains = findById.get();
		model.addAttribute("addTrains", addTrains);
		return "book_tickets";
	}

	@RequestMapping("/paymentFinal")
	public String paymentfinalPage() {
		return "paymentFinal";
	}

	/*
	 * @GetMapping("/confirmTicket") public String
	 * ConfirmTicketDetails(@RequestParam("id") long id,Model model) {
	 * Optional<PassengerDetails> findById =
	 * passengerDetailsRepository.findById(id); PassengerDetails passengerDetailss =
	 * findById.get(); model.addAttribute("passengerDetailss", passengerDetailss);
	 * return "paymentFinal"; }
	 */

	// open setting handler
	@GetMapping("/settings")
	public String openSettings() {
		return "settings";
	}

	// change password handler
	@PostMapping("/change-password")
	public String changePassword(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword, Principal principal, HttpSession session) {
		System.out.println("OLD PASSWORD " + oldPassword);
		System.out.println("NEW PASSWORD " + newPassword);

		String email = principal.getName();
		User currentUser = this.userRepository.getUserByUserEmail(email);
		System.out.println(currentUser.getPassword());

		if (this.bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword())) {
			// change the password
			currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
			this.userRepository.save(currentUser);

			session.setAttribute("message",new Message("Your password is successfully changed...!!!","success"));
		} else {
			session.setAttribute("message", new Message("Please Enter correct old password !!!","danger"));
			return "redirect:/user/settings";
		}

		return "redirect:/loginPage?change=password changed successfully...";
		// return "redirect:/user/search_train";
	}

	//method for adding common data response
	@ModelAttribute
	public void addCommomData(Model m, Principal principal) {
		String username = principal.getName();
		System.out.println("USERNAME " + username);

		// get the user using username(email)
		User user = userRepository.getUserByUserEmail(username);
		System.out.println("USER " + user);

		m.addAttribute("user", user);
}

	//dashboard home
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		
		return "user_dashboard";
}
	//user profile
	@RequestMapping("/profile-user")
	public String userProfile() {
		return "profile_users";
	}
	
//	//pagination done
//	@GetMapping("/booking-details")
//	public String userBookingDetails(/* @PathVariable("page") Integer page, */ Model m, Principal principal) {
//		
//		String username = principal.getName();
//		User user = this.userRepository.getUserByUserEmail(username);
//		
////		Pageable pageable =  PageRequest.of(page, 8);
//		
//		List<PassengerDetails> passengerDetails = passengerDetailsRepository.findPassengerDetailsByUser(user.getId());
////		List<MyOrder> myOrderUser = myBookingRepository.findMyOrderByUser(user.getId());
//		
//		
//		
////		List<String> statusList = myOrderUser.stream()
////                .map(MyOrder::getStatus)
////                .collect(Collectors.toList());
//		
////		m.addAttribute("statusList", statusList);
//		m.addAttribute("userDetails", user);
//		m.addAttribute("passengerDetails", passengerDetails);
////		m.addAttribute("currentPage", page);
////		m.addAttribute("totalPages", passengerDetails.getTotalPages());
//		return "show_bookingDetails";
//	}
	
	
	
	@GetMapping("/booking-details")
	public String userbook(Model model, Principal principal) {
	    String userName = principal.getName();
	    User user = this.userRepository.getUserByUserEmail(userName);

	    List<PassengerDetails> paymentBookList = this.passengerDetailsRepository.findPassengerDetailsByUser(user.getId());
	    List<MyOrder> bookTicketList = this.myBookingRepository.findMyOrderByUser(user.getId());

	    List<UserBookingDTO> userBookingList = new ArrayList<>();

	    // Combine the data from PaymentBook and BookTicket entities into UserBookingDTO objects
	    for (int i = 0; i < bookTicketList.size(); i++) {
	    	
	    	
	        UserBookingDTO userBookingDTO = new UserBookingDTO();
	        userBookingDTO.setPassengerDetails(paymentBookList.get(i));
	        userBookingDTO.setMyBookTicket(bookTicketList.get(i));

	        userBookingList.add(userBookingDTO);
	    }

	    model.addAttribute("userBookingList", userBookingList);
	    return "show_bookingDetails";
	}
	
	
	@GetMapping("/bookingBackButton")
	public String bookingBackButton(Model model, Principal principal) {
	    String userName = principal.getName();
	    User user = this.userRepository.getUserByUserEmail(userName);

	    List<PassengerDetails> paymentBookList = this.passengerDetailsRepository.findPassengerDetailsByUser(user.getId());
	    List<MyOrder> bookTicketList = this.myBookingRepository.findMyOrderByUser(user.getId());

	    List<UserBookingDTO> userBookingList = new ArrayList<>();

	    // Combine the data from PaymentBook and BookTicket entities into UserBookingDTO objects
	    for (int i = 0; i < bookTicketList.size(); i++) {
	    	
	    	
	        UserBookingDTO userBookingDTO = new UserBookingDTO();
	        userBookingDTO.setPassengerDetails(paymentBookList.get(i));
	        userBookingDTO.setMyBookTicket(bookTicketList.get(i));

	        userBookingList.add(userBookingDTO);
	    }

	    model.addAttribute("userBookingList", userBookingList);
	    return "show_bookingDetails";
	}
	
	
	
	@GetMapping("/bookingviewMore/{id}")
	public String bookingViewMore(@PathVariable("id") Long id, Model model) {

		Optional<PassengerDetails> findById = passengerDetailsRepository.findById(id);
		PassengerDetails passengerview = findById.get();
		model.addAttribute("passengerview", passengerview);
	return "booking_view_more";	
	}
	
	
	@PostMapping("/delete")
	public String deleteUserBooking(@RequestParam("id") Long id,@RequestParam("bookingId") Long bookingId) {
	   
	
	Optional<PassengerDetails> findById = passengerDetailsRepository.findById(id);
	PassengerDetails passengerDetails = findById.get();	
	
	
	
	CancelTrains cancelTrains = new CancelTrains();
	
	cancelTrains.setFirstName(passengerDetails.getFirstName());
	cancelTrains.setEmail(passengerDetails.getEmail());
	cancelTrains.setPhone(passengerDetails.getPhone());
	
	cancelTrains.setTrainName(passengerDetails.getTrainName());
	cancelTrains.setTrainFrom(passengerDetails.getTrainFrom());
	cancelTrains.setTrainTo(passengerDetails.getTrainTo());
	cancelTrains.setDepartureDate(passengerDetails.getDepartureDate());
	cancelTrains.setTicketPrice(passengerDetails.getTicketPrice());
	cancelTrains.setUser(passengerDetails.getUser());
	
	cancelTrainsRepository.save(cancelTrains);

		
		passengerDetailsRepository.deleteById(id);
		myBookingRepository.deleteById(bookingId);
	    // Redirect back to the booking list page
	    return "redirect:/user/booking-details";
//	    return "cancelled-booking";
	}
	
	@GetMapping("/cancelled-List")
	public String CancelledList(Model model, Principal principal) {
		String userName = principal.getName();
		User user = this.userRepository.getUserByUserEmail(userName);
		 List<CancelTrains> findCancelTrainsBooking = this.cancelTrainsRepository.findCancelTrainsBooking(user.getId());
		 model.addAttribute("cancelTrain", findCancelTrainsBooking);
		return "cancelled-booking";
	}
	
	
	@GetMapping("/payable-Amount")
	public String payableAmount() {
		return "payment_amount";
	}
	
//	creating order for payment
	@PostMapping("/create_order")
	@ResponseBody
	public String createOrder(@RequestBody Map<String, Object> data, Principal principal) throws Exception {
		
		
		//System.out.println("hey order function executed");
		System.out.println(data);
		int amt = Integer.parseInt(data.get("amount").toString());
		var client =new RazorpayClient("rzp_test_gYB3Ndb3zXZh97", "5GyLYJJZqTReh7oJjnKRatas");
		
		JSONObject ob = new JSONObject();
		ob.put("amount", amt*100);
		ob.put("currency", "INR");
		ob.put("receipt", "txn_235425");
		
		//creating new order
		
		Order order = client.orders.create(ob);
		System.out.println(order);
		
		//save the order in database
		
		MyOrder myOrder = new MyOrder();
		
		myOrder.setAmount(order.get("amount")+"");
		myOrder.setOrderId(order.get("id"));
		myOrder.setPaymentId(null);
		myOrder.setStatus("failed");
		myOrder.setUser(this.userRepository.getUserByUserEmail(principal.getName()));
		myOrder.setReceipt(order.get("receipt"));
		
		this.myBookingRepository.save(myOrder);
		//if you want you can save this to your database
		
		return order.toString();
	}
	
	//another handler
	@PostMapping("/update_order")
	public ResponseEntity<?> updateOrder(@RequestBody Map<String , Object> data){
		
		MyOrder myorder = this.myBookingRepository.findByOrderId(data.get("order_id").toString());
		myorder.setPaymentId(data.get("payment_id").toString());
		myorder.setStatus(data.get("status").toString());
		this.myBookingRepository.save(myorder);
		
		System.out.println(data);
		
		return ResponseEntity.ok(Map.of("msg","updated"));
	}
	
	
//	//show payment information page
//	@GetMapping("/payment-information/{page}")
//	public String showPaymentDetails(@PathVariable("page") Integer page, Model m, Principal principal) {
//		String username = principal.getName();
//		User user = this.userRepository.getUserByUserEmail(username);
//		
//		Pageable pageable =  PageRequest.of(page, 8);
//		Page<MyOrder> mybookpayment = myBookingRepository.findMyOrderDetailsByUser(user.getId(), pageable);
//		m.addAttribute("mybookpayment", mybookpayment);
//		m.addAttribute("currentPage", page);
//		m.addAttribute("totalPages", mybookpayment.getTotalPages());
//		
//		return "payment_information";
//	}
	
	
//	//pagination done
//	@GetMapping("/booking-details/{page}")
//	public String userBookingDetails(@PathVariable("page") Integer page, Model m, Principal principal) {
//		
//		String username = principal.getName();
//		User user = this.userRepository.getUserByUserEmail(username);
//		
//		Pageable pageable =  PageRequest.of(page, 8);
//		
//		Page<PassengerDetails> passengerDetails = passengerDetailsRepository.findPassengerDetailsByUser(user.getId(),pageable);
//		m.addAttribute("passengerDetails", passengerDetails);
//		m.addAttribute("currentPage", page);
//		m.addAttribute("totalPages", passengerDetails.getTotalPages());
//		return "show_bookingDetails";
//	}

	
//	
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
//	    User user = this.userRepository.getUserByUserEmail(userName);
//
//	    List<PassengerDetails> passengerDetailsList = this.passengerDetailsRepository.findPassengerDetailsByUser(user.getId());
//	    List<MyOrder> bookingDetailsList = this.myBookingRepository.findMyOrderByUser(user.getId());
//
//	    
//	    service.exportJasperReport(response, passengerDetailsList, bookingDetailsList);
//	}
	
	
//	@GetMapping("/jasperpdf/export")
//	public void createPDF(HttpServletResponse response, Principal principal) throws IOException, JRException {
//	    response.setContentType("application/pdf");
//	    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
//	    String currentDateTime = dateFormatter.format(new Date());
//
//	    String headerKey = "Content-Disposition";
//	    String headerValue = "attachment; filename=BookingTicket_" + currentDateTime + ".pdf";
//	    response.setHeader(headerKey, headerValue);
//
//	    String userName = principal.getName();
//	    User user = this.userRepository.getUserByUserEmail(userName);
//
//	    List<PassengerDetails> passengerDetailsList = this.passengerDetailsRepository.findPassengerDetailsByUser(user.getId());
//	    List<MyOrder> bookingDetailsList = this.myBookingRepository.findMyOrderByUser(user.getId());
//
//	    List<UserBookingDTO> userBookingList = new ArrayList<>();
//
//	    // Combine the data from PaymentBook and BookTicket entities into UserBookingDTO objects
//	    for (int i = 0; i < bookingDetailsList.size(); i++) {
//	    	
//	    	
//	        UserBookingDTO userBookingDTO = new UserBookingDTO();
//	        userBookingDTO.setPassengerDetails(passengerDetailsList.get(i));
//	        userBookingDTO.setMyBookTicket(bookingDetailsList.get(i));
//
//	        userBookingList.add(userBookingDTO);
//	    }
//	    
//	    service.exportJasperReport(response, userBookingList);
//	}
//	
//	@Autowired
//	public void setPassengerDetailsRepository(PassengerDetailsRepository passengerDetailsRepository) {
//	    this.passengerDetailsRepository = passengerDetailsRepository;
//	}
//
//	@Autowired
//	public void setMyBookingRepository(MyBookingRepository myBookingRepository) {
//	    this.myBookingRepository = myBookingRepository;
//	}

	
	
	
	
	
	
	//2
	
//	@GetMapping("/jasperpdf/export")
//	public void createPDF(HttpServletResponse response, Principal principal) throws IOException, JRException {
//	    response.setContentType("application/pdf");
//	    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
//	    String currentDateTime = dateFormatter.format(new Date());
//
//	    String headerKey = "Content-Disposition";
//	    String headerValue = "attachment; filename=BookingTicket_" + currentDateTime + ".pdf";
//	    response.setHeader(headerKey, headerValue);
//
//	    String userName = principal.getName();
//	    User user = this.userRepository.getUserByUserEmail(userName);
//
//	    List<PassengerDetails> passengerDetailsList = this.passengerDetailsRepository.findPassengerDetailsByUser(user.getId());
//	    List<MyOrder> bookingDetailsList = this.myBookingRepository.findMyOrderByUser(user.getId());
//
//	    service.exportJasperReport(response, passengerDetailsList, bookingDetailsList);
//	}

	
//	@Autowired
//    private MyDataService myDataService;
//
//    @GetMapping("/jasperpdf/export")
//    public void generateReport(HttpServletResponse response) throws Exception {
//        // Fetch data from the two tables using the service
//        List<PassengerDetails> myData1List = myDataService.getMyData1();
//        List<MyOrder> myData2List = myDataService.getMyData2();
//
//        // Create a Map object to hold your report parameters
//        Map<String, Object> parameters = new HashMap<>();
//
//        // Add the two lists to the parameters Map
//        JRDataSource dataSource1 = new JRBeanCollectionDataSource(myData1List);
//        JRDataSource dataSource2 = new JRBeanCollectionDataSource(myData2List);
//        parameters.put("dataSource1", dataSource1);
//        parameters.put("dataSource2", dataSource2);
//
//        // Compile the Jasper report from your .jrxml file
//        ClassPathResource resource = new ClassPathResource("address.jrxml");
//        JasperReport report = JasperCompileManager.compileReport(resource.getInputStream());
//
//        // Fill the report with data and generate a PDF
//        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
//        byte[] pdfBytes = JasperExportManager.exportReportToPdf(print);
//
//        // Set the response headers to allow downloading the PDF file
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment; filename=report.pdf");
//
//        // Write the PDF bytes to the response output stream
//        ServletOutputStream outputStream = response.getOutputStream();
//        outputStream.write(pdfBytes);
//        outputStream.flush();
//        outputStream.close();
//    }
	
	
    @GetMapping("/jasperpdf/export/{id}")
    public void createPDF(HttpServletResponse response,Principal principal,@PathVariable("id") Long id) throws IOException, JRException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
  
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=BookingTicket_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
  
        service.exportJasperReport(response,principal.getName(),id);
    }

	
	
}
