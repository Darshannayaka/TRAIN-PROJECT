package com.ingroinfo.trainProject.Controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.event.PublicInvocationEvent;
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
import com.ingroinfo.trainProject.Repository.PassengerDetailsRepository;
import com.ingroinfo.trainProject.Repository.TrainMasterRepository;
import com.ingroinfo.trainProject.Repository.UserRepository;
import com.ingroinfo.trainProject.entities.AddTrains;
import com.ingroinfo.trainProject.entities.Message;
import com.ingroinfo.trainProject.entities.PassengerDetails;
import com.ingroinfo.trainProject.entities.TrainMaster;
import com.ingroinfo.trainProject.entities.User;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AddTrainsRepository addTrainsRepository;
	
	@Autowired
	private TrainMasterRepository trainMasterRepository;
	
	@Autowired
	private PassengerDetailsRepository passengerDetailsRepository;

	
	@GetMapping("/adminLogin")
	public String viewAdminLoginForm() {
		return "admin";
	}
	
	//pagination done
	@GetMapping("/list_users/{page}")
	public String viewUserList(@PathVariable("page") Integer page ,Model model) {
		Pageable pageable = PageRequest.of(page, 8);
		Page<User> listUsers = userRepo.findAll(pageable);
		
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", listUsers.getTotalPages());
		return "userLists";
	}
	
	@GetMapping("/addTrain")
	public String viewAdd( Model model) {
	List<TrainMaster> trainMaster = trainMasterRepository.findAll();
	model.addAttribute("trainMaster", trainMaster);
		return "addTrains";
	}
	
	@PostMapping("/addTrainDetails")
	public String saveTrainDetails(@ModelAttribute AddTrains addTrains,HttpSession session) {
		boolean f = this.addTrainsRepository.existsByMessage(addTrains.getMessage());
		if(f) {
			session.setAttribute("message", new Message("Train details saved Successfully !!","danger"));
			addTrainsRepository.save(addTrains);
			
		}else {
			session.setAttribute("message", new Message("Train details exist !! ","success"));
			System.out.println("Train already exist !!");
		}
		
		return	"redirect:/admin/addTrain";	
	}
	
	
//	@PostMapping("/process_register")
//	public String processRegistration(@ModelAttribute User user,HttpSession session) {
//		boolean f = this.userRepo.existsByEmail(user.getEmail());
//		if (f) {
//			session.setAttribute("message", new Message("Email Alredy Exist !!","danger"));
//			System.out.println("Email Already Exist !!");
//		}else {
//			session.setAttribute("message", new Message("User Data Saved Successfully !!","success"));
//			//password encrypted
//			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//			String encodedPassword = encoder.encode(user.getPassword());
//			user.setPassword(encodedPassword);
//			user.setRole("ROLE_USER");
//			this.userRepo.save(user);
//		}		
//		return "redirect:/register";
//	}
	
//	@PostMapping("/addTrainDetails")
//	public String saveTrainDetails(@ModelAttribute AddTrains addTrains) {
//		addTrainsRepository.save(addTrains);
//		return	"redirect:/admin/addTrain";	
//	}
		
		
	//pagination done
	@GetMapping("/allTrains/{page}")
	public String getAllTrains(@PathVariable("page") Integer page, Model model) {
	     	Pageable pageable = PageRequest.of(page, 8);
			
	     	Page<AddTrains> listTrains = addTrainsRepository.findAll(pageable);
				model.addAttribute("listTrains", listTrains);
				model.addAttribute("currentPage", page);
				model.addAttribute("totalPages", listTrains.getTotalPages());
		return "showTrain";
	}
	
	@GetMapping("/update/{id}")
	public String UpdateOneTrain(@PathVariable("id") Long id, Model model) {
		Optional<AddTrains> findById = addTrainsRepository.findById(id);
		AddTrains addTrains = findById.get();
		model.addAttribute("addTrains", addTrains);
		return "update_trains";
	}
	
	@PostMapping("/updateTrainDetails")
	public String saveUpdateTrainDetails(@ModelAttribute AddTrains addTrains) {
		addTrainsRepository.save(addTrains);
		return	"redirect:/admin/allTrains/0";	
	}
	
	@GetMapping("/delete/{id}")
	public String deleteTrain (@PathVariable("id") Long id) {
		addTrainsRepository.deleteById(id);
		return "redirect:/admin/allTrains/0";
	}
	
	
	@GetMapping("/trainMaster")
	public String trainMasterViewPage() {
		return "TrainMaster";
	}
	
	@PostMapping("/saveTrainMaster")
	public String saveOneStops(TrainMaster trainMaster) {
		trainMasterRepository.save(trainMaster);
		return "redirect:/admin/trainMaster";
	}
	
	//pagination done
	//per page = 5[n]
	//current page = 0 [page]
	@GetMapping("/displayTrainAndPassenger/{page}")
	public String displayTrainPassenger(@PathVariable("page") Integer page, Model model) {
		
		Pageable pageable = PageRequest.of(page, 8);
		
		Page<PassengerDetails> displayTrain = passengerDetailsRepository.findAll(pageable);
		
		model.addAttribute("displayTrain", displayTrain);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages",displayTrain.getTotalPages());
		
		return "displayTrainAndPassenger";
	}
	
	@GetMapping("/viewMore/{id}")
	public String displayTrainPassngrViewMore(@PathVariable("id") Long id, Model model) {
		Optional<PassengerDetails> findById = passengerDetailsRepository.findById(id);
		PassengerDetails passengerview = findById.get();
		model.addAttribute("passengerview", passengerview);
		return "passengerViewMore";
	}
	

}

