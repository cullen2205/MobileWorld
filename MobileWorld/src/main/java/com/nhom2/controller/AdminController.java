package com.nhom2.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhom2.entities.Category;
import com.nhom2.entities.Order;
import com.nhom2.entities.Product;
import com.nhom2.services.CategoryService;
import com.nhom2.services.OrderDetailService;
import com.nhom2.services.OrderService;
import com.nhom2.services.ProductService;

@Controller
public class AdminController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderDetailService detailService;

	@GetMapping("/admin")
	public String index() {
		return "ad_index";
	}

	// Start
	// Category-------------------------------------------------------------
	@GetMapping("/categoryManager")
	public String category(ModelMap model) {
		model.addAttribute("categories", categoryService.findAll());
		return "categoryManager";
	}

	@GetMapping("/category/{id}/delete")
	public String deleteCategory(@PathVariable int id, ModelMap model) {
		categoryService.delete(id);
		return "redirect:/categoryManager";

	}

	@GetMapping("/addCategory")
	public String addCategory(ModelMap model) {
		model.addAttribute("categoryForm", new Category());
		return "formCategory";
	}

	@RequestMapping(value = "/categoryManager/save", method = RequestMethod.POST)
	public String saveCategory(ModelMap model, @ModelAttribute("categoryForm") Category category, BindingResult validateForm) {
		if(category.getName().trim().length()==0){
			validateForm.rejectValue("name", "category", "vui long nhap ten");
		}
		if(validateForm.hasErrors()){
			model.addAttribute("message", "vui long sua loi");
			
		}
		else{
			model.addAttribute("message", "them moi thanh cong");
			categoryService.save(category);
		}
		
		return "redirect:/categoryManager";
	}

//	@GetMapping("/category/edit")
//	public String editCategory(@PathVariable int id, ModelMap model) {
////		model.addAttribute("categories", categoryService.findOne(id));
//		return "formUpdateCategory";
//
//	}
	
	@GetMapping("/editCategory")
	public String updateCategory(ModelMap model, @RequestParam("cateID") int id) {		
		model.addAttribute("categoryForm", categoryService.findOne(id));
		return "formUpdateCategory";
	}
	
	@PostMapping("/saveChangeCategory")
	public String saveChangeCategory(ModelMap model, @Validated @ModelAttribute("categoryForm") Category category, BindingResult validateForm){
		if(category.getName().trim().length()==0){
			validateForm.rejectValue("name", "category", "vui long nhap ten");
		}
		if(validateForm.hasErrors()){
			model.addAttribute("message", "vui long sua loi");
			
		}
		else{
			model.addAttribute("message", "them moi thanh cong");
			categoryService.save(category);
		}
		
		return "redirect:/categoryManager";
	}
	// End Category

	// Start Product
	// ---------------------------------------------------------------------
	@GetMapping("/editProduct")
	public String updateProduct(ModelMap model, @RequestParam("proID") int id){
		model.addAttribute("Categories", categoryService.findAll());
		model.addAttribute("productForm", productService.findOne(id));
		return "formUpdateProduct";
		
	} 
	
	@PostMapping("/saveChangeProduct")
	public String saveChangeProduct(ModelMap model, @ModelAttribute("productForm") Product product, BindingResult validateForm){
		if(product.getName().trim().length()==0){
			validateForm.rejectValue("name", "product", "vui long nhap ten");
		}
		if(product.getPrice() < 0){
			validateForm.rejectValue("price", "product", "gia khong nho hon 0");
		}
		if(product.getQuantity() < 0){
			validateForm.rejectValue("quantity", "product", "so luong khong nho hon 0");
		}
		if(validateForm.hasErrors()){
			model.addAttribute("message", "vui long sua loi");
		}else{
			model.addAttribute("message", "them moi thanh cong");
			productService.save(product);
		}
		
		return "redirect:/productManager";
	}
	
	@GetMapping("/productManager")
	public String product(ModelMap model) {
		model.addAttribute("products", productService.findAll());
		return "productManager";
	}

	@GetMapping("/product/{id}/delete")
	public String deleteProduct(@PathVariable int id, ModelMap model) {
		productService.delete(id);
		return "redirect:/productManager";
	}

	@GetMapping("/addProduct")
	public String addProduct(ModelMap model) {
		model.addAttribute("Categories", categoryService.findAll());
		model.addAttribute("productForm", new Product());
		return "formProduct";
	}

	@RequestMapping(value = "/productManager/save", method = RequestMethod.POST)
	public String saveProduct(ModelMap model, @ModelAttribute("productForm") Product product, BindingResult validateForm) {
		if(product.getName().trim().length()==0){
			validateForm.rejectValue("name", "product", "vui long nhap ten");
		}
		if(product.getPrice() < 0){
			validateForm.rejectValue("price", "product", "gia khong nho hon 0");
		}
		if(product.getQuantity() < 0 ){
			validateForm.rejectValue("quantity", "product", "so luong khong nho hon 0");
		}
		if(validateForm.hasErrors()){
			model.addAttribute("message", "vui long sua loi");
			
		}else{
			model.addAttribute("message", "them moi thanh cong");
			product.setImage("static/images/phone/" + product.getImage());
			productService.save(product);
		}
		
		return "redirect:/productManager";
	}
	// closeProduct
	
	//Start Order
		@GetMapping("/orderManagerr")
		public String orderManager(ModelMap model) {
			model.addAttribute("newOrder", orderService.getNewOrder());
			model.addAttribute("checkedOrder", orderService.getCheckedOrder());
			return "oderManager";
		}
		
		@GetMapping("/viewOrderDetail")
		public String viewOrderDetail(@RequestParam("orderID") int orderID, ModelMap model) {
			model.addAttribute("mode", "viewDetail");
			model.addAttribute("orderID", orderID);
			model.addAttribute("orderDetails", detailService.findByOrderID(orderID));
			return "oderManager";
		}
		
		@PostMapping("/checkedOrder")
		public String checkedOrder(HttpServletRequest request) {
			int orderID = Integer.parseInt(request.getParameter("orderID"));
			int orderStatus = Integer.parseInt(request.getParameter("orderStatus"));
			boolean check = false;
			if(orderStatus == 1) {
				check = true;
			}
			Order order = orderService.findOne(orderID);
			order.setStatus(check);
			orderService.update(order);
			return "oderManager";
		}
		//End Order
	
}
