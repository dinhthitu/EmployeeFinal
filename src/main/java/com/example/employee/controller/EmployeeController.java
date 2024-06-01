package com.example.employee.controller;

import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
   


    //api show all employees
    @GetMapping("/")
    public String showEmployee(Model model) {
        model.addAttribute("listEmployees", employeeService.getAllEmployees());
        return findPaginated(1, model);
    }
    //showEmployeeForm

    @GetMapping("/createNewEmployee")
    public String createNewEmployee(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "new_employee";
    }
    //api save new employee

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/";
    }

    //    api update data
    @GetMapping("/updateEmployee/{id}")
    public String updateDataEmployee(Model model,
                                     @PathVariable long id) {
        //read employee data from service through get
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "update_employee";
    }
//api delete

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable long id) {
        this.employeeService.deleteById(id);
        return "redirect:/";
    }

    //api phan trang

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable int pageNo, Model model) {
        int pageSize = 5;
        Page<Employee> page = employeeService.findPaginated(pageNo, pageSize);
        List<Employee> listEmployees = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listEmployees", listEmployees);
        return "index";


    }

}
