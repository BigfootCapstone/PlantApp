package com.codeup.plantapp.controllers;

import com.codeup.plantapp.models.GardenPlant;
import com.codeup.plantapp.models.User;
import com.codeup.plantapp.models.Weather;
import com.codeup.plantapp.repositories.GardenPlantRepository;
import com.codeup.plantapp.repositories.UserRepository;
import com.codeup.plantapp.services.Keys;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import static com.codeup.plantapp.services.WeatherCall.getWeather;
import static com.codeup.plantapp.util.CareTips.checkForOutdoorPlants;
import static com.codeup.plantapp.util.CareTips.plantTipCheck;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository usersDao;

    private final PasswordEncoder passwordEncoder;
    private final GardenPlantRepository gardenPlantDao;


//    private final UserRepository userDao;

    public UserController(UserRepository usersDao, GardenPlantRepository gardenPlantDao, PasswordEncoder passwordEncoder){
        this.usersDao = usersDao;
        this.gardenPlantDao = gardenPlantDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private Keys keys;

    @GetMapping("/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User());
        return "createUserForm";
    }

    //messing with it for the create user
    @PostMapping("/create")
    public String createUserProfile(@ModelAttribute("user") User user) {
        user.setCreated_at(LocalDate.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersDao.save(user);
        return "redirect:/users/" + user.getId();
    }
    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }
    @PostMapping("/login")
    public String loginSessionSetter(Model model, HttpSession session){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        session.setAttribute("user", user);
        return "redirect: /users/profile";
    }


    @GetMapping("/profile")
    public String showProfile(Model model) throws Exception{
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = user.getId();

        user = usersDao.findUserById(userId);
        model.addAttribute("user", user);

//      Get Weather for User's City
        String city = user.getCity();
        String key = keys.getOpenWeather();
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key +
                "&units=imperial");
        Weather userWeather = getWeather(url);

//      Aggregate all plants in user's garden
        List<GardenPlant> allPlants = gardenPlantDao.findGardenPlantByUser(user);

//      Run conditional logic for all plants based on Days passed
        for (GardenPlant plant: allPlants) {
            GardenPlant gardenPlant = gardenPlantDao.findGardenPlantsById(plant.getId());
            GardenPlant checked = plantTipCheck(gardenPlant, userWeather);
            gardenPlantDao.save(checked);
        }

//      Set Weather for View
        model.addAttribute("weather", userWeather);

//      Set Outdoor plants for quick weather reference and warnings
        model.addAttribute("outdoorPlants", checkForOutdoorPlants(user));

//      Set All Plants for Garden Preview
        model.addAttribute("userPlants", allPlants);

        System.out.println(user.getUsername());
        return "userProfile";
    }

    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable Long id, Model model) {
        User user = usersDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        model.addAttribute("user", user);
        return "userProfile";
    }

    @GetMapping("/{id}/edit")
    public String editUserProfileForm(@PathVariable("id") String id, Model model) {
        User user = usersDao.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        model.addAttribute("user", user);
        return "editUserForm";
    }

    @PostMapping("/{id}/edit")
    public String updateUserProfile(@PathVariable("id") String id, @ModelAttribute("user") User user) {
        User updatedUser = usersDao.findUserById(1L);
        updatedUser.setUsername(user.getUsername());
        updatedUser.setFirst_name(user.getFirst_name());
        updatedUser.setLast_name(user.getLast_name());
        updatedUser.setCity(user.getCity());
        updatedUser.setEmail(user.getEmail());
        usersDao.save(updatedUser);
        return "redirect:/users/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteUserProfile(@PathVariable Long id) {
        User user = usersDao.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        usersDao.delete(user);
        return "redirect:/users";
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", usersDao.findAll());
        return "users";
    }
}