package com.ingroinfo.trainProject.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.ingroinfo.trainProject.Repository.MyBookingRepository;
import com.ingroinfo.trainProject.Repository.PassengerDetailsRepository;
import com.ingroinfo.trainProject.Repository.UserRepository;
import com.ingroinfo.trainProject.entities.MyOrder;
import com.ingroinfo.trainProject.entities.PassengerDetails;
import com.ingroinfo.trainProject.entities.User;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class JReportService {
  
    @Autowired
    private PassengerDetailsRepository repository;
  
    
    @Autowired
	private UserRepository userRepository;
    
	@Autowired
	private MyBookingRepository myBookingRepository;
  
    public void exportJasperReport(HttpServletResponse response,String email, Long id) throws JRException, IOException {
        User user = userRepository.getUserByUserEmail(email);
    	List<PassengerDetails> address = repository.findPassengerDetailsByUsers(user.getId(), id);
    	
//    	List<PassengerDetails> address = repository.findAll();
        //Get file and compile it
        File file = ResourceUtils.getFile("classpath:address.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(address);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Simplifying Tech");
        //Fill Jasper report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        //Export report
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
    }
    
	
	
	
//    public byte exportJasperReport(HttpServletResponse response,String email, Long id) throws JRException, IOException {
//        User user = userRepository.getUserByUserEmail(email);
//    	List<PassengerDetails> address = repository.findPassengerDetailsByUsers(user.getId(), id);
//    	
////    	List<PassengerDetails> address = repository.findAll();
//        //Get file and compile it
//        File file = ResourceUtils.getFile("classpath:address.jrxml");
//        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//       
//        
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(address);
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("createdBy", "Simplifying Tech");
//        parameters.put("logo", "classpath:img/railway-logo.png");
//        
//        //Fill Jasper report
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
//        //Export report
//    byte reportData =   JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
//    return reportData;
//    }
    
	

	
//	
//	public void generateJasperReport(HttpServletResponse response) throws JRException, IOException {
//	
//		   List<PassengerDetails> passengerDetailsList = this.passengerDetailsRepository.findAll();
//	    List<MyOrder> bookingDetailsList = this.myBookingRepository.findAll();
//	
//	    File file = ResourceUtils.getFile("classpath:address.jrxml");
//	    JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//    
//	    
//	    Map<String, Object> parameters = new HashMap<>();
//      parameters.put("entityAList", passengerDetailsList);
//      parameters.put("entityBList", bookingDetailsList);
//      
//      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
//	    
//      
//      OutputStream outputStream = response.getOutputStream();
//    JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
//    
//    response.setContentType("application/pdf");
//  response.setHeader("Content-Disposition", "attachment; filename=report.pdf");
//  
//  outputStream.flush();
//  outputStream.close();
//	}

	
	//2
//	public void exportJasperReport(HttpServletResponse response, List<PassengerDetails> passengerDetailsList,
//			List<MyOrder> bookingDetailsList) throws JRException, IOException  {
//		
//		    File file = ResourceUtils.getFile("classpath:address.jrxml");
//		    JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//	    
//		    
//		    Map<String, Object> parameters = new HashMap<>();
//	      parameters.put("entityAList", passengerDetailsList);
//	      parameters.put("entityBList", bookingDetailsList);
//	      
//	      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
//		    
//	      
//	      OutputStream outputStream = response.getOutputStream();
//	    JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
//	    
//	    response.setContentType("application/pdf");
//	  response.setHeader("Content-Disposition", "attachment; filename=report.pdf");
//	  
//	  outputStream.flush();
//	  outputStream.close();
//	}
	
	
	
//    public void exportJasperReport(HttpServletResponse response, List<PassengerDetails> passengerDetailsList, List<MyOrder> bookingDetailsList) throws JRException, IOException {
//        //Get file and compile it
//        File file = ResourceUtils.getFile("classpath:address.jrxml");
//        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(passengerDetailsList);
//        
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("createdBy", "Simplifying Tech");
//        parameters.put("passengerDetailsList", passengerDetailsList);
//        parameters.put("bookingDetailsList", bookingDetailsList);
//
//        //Fill Jasper report
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,dataSource);
//
//        //Export report
//        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
//    }

    
//    public void exportJasperReport(Principal principal, HttpServletResponse response) throws JRException, IOException {
//
// // Retrieve the login information from the Principal object
//
//        // Fetch data for the specific login
//        String userName = principal.getName();
//	    User user = this.userRepository.getUserByUserEmail(userName);
//
//	    List<PassengerDetails> passengerDetailsList = this.passengerDetailsRepository.findPassengerDetailsByUser(user.getId());
//	    List<MyOrder> bookingDetailsList = this.myBookingRepository.findMyOrderByUser(user.getId());
//
//        // Get file and compile it
//        File file = ResourceUtils.getFile("classpath:TrainTicket.jrxml");
//        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//
//        // Create a list to hold the data for both tables
//        List<Object> combinedData = new ArrayList<>();
//        combinedData.addAll(passengerDetailsList);
//        combinedData.addAll(bookingDetailsList);
//
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(combinedData);
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("createdBy", "Simplifying Tech");
//
//        // Fill Jasper report
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
//
//        // Export report
//        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
//    }
	
	
	
}
