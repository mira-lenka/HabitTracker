package lesniak.marta.habitTracker.controller;


import jakarta.servlet.http.HttpServletResponse;
import lesniak.marta.habitTracker.dto.LoginDto;
import lesniak.marta.habitTracker.entity.User;
import lesniak.marta.habitTracker.repository.UserRepository;
import lesniak.marta.habitTracker.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class AppController {

    @Autowired
    private CustomUserDetailsService service;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AppController(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    public String viewHomePage() {
        return "redirect:/records/dailyrecords";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {

        User user = new User();

        model.addAttribute("user",user);

        return "registration";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("user") User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        service.saveUser(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(Model model, HttpServletResponse response) {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        LoginDto loginDto = new LoginDto();

        model.addAttribute("logindto",loginDto);

        return "login";

    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute("login_dto") LoginDto loginDto) {

        UserDetails user = service.loadUserByUsername(loginDto.getUsernameOrEmail());

        model.addAttribute("user",service.getCurrentUser());

        return "index";

    }

    @GetMapping("/edit")
    public String edit(Model model){

        User user = service.getCurrentUser();

        model.addAttribute("user",user);

        return "uuser";

    }

    @PostMapping("/update")
    public String update(Model model, @ModelAttribute("user") User user){

        User currentUser = service.getCurrentUser();

        currentUser.setName(user.getName());
        currentUser.setUsername(user.getUsername());
        currentUser.setEmail(user.getEmail());
        currentUser.setPassword(passwordEncoder.encode(user.getPassword()));

        service.saveUser(currentUser);

        return "redirect:/records/dailyrecords";

    }

}
