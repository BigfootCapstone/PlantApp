package com.codeup.plantapp.controllers;

import com.codeup.plantapp.models.*;
import com.codeup.plantapp.repositories.FriendRepository;
import com.codeup.plantapp.repositories.GardenPlantRepository;
import com.codeup.plantapp.repositories.PlantRepository;
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
import static com.codeup.plantapp.util.FriendsManager.friendsRequests;
import static com.codeup.plantapp.util.FriendsManager.knownFriends;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository usersDao;
    private final PasswordEncoder passwordEncoder;
    private final GardenPlantRepository gardenPlantDao;
    private final PlantRepository plantDao;
    private final FriendRepository friendDao;


//    private final UserRepository userDao;

    public UserController(UserRepository usersDao, GardenPlantRepository gardenPlantDao,
                          FriendRepository friendDao,
                          PlantRepository plantDao, PasswordEncoder passwordEncoder){
        this.usersDao = usersDao;
        this.gardenPlantDao = gardenPlantDao;
        this.passwordEncoder = passwordEncoder;
        this.plantDao =  plantDao;
        this.friendDao = friendDao;
    }

    @Autowired
    private Keys keys;

    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "createUserForm";
    }

    //messing with it for the create user
    @PostMapping("/create")
    public String createUserProfile(@ModelAttribute("user") User user) {
        user.setCreated_at(LocalDate.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersDao.save(user);
        return "redirect:/users/login";
//        usersDao.save(user);
//        return "redirect:/userProfile";
    }
    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }
//    @PostMapping("/login")
//    public String loginSessionSetter(Model model, HttpSession session){
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        session.setAttribute("user", user);
//        return "redirect: /users/profile";
//    }
//    @GetMapping("/logout")
//    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null){
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/";
//    }

    @GetMapping("/profile")
    public String showProfile(Model model) throws Exception{
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = user.getId();

        User userFromDb = usersDao.findUserById(userId);
        model.addAttribute("user", userFromDb);

        String successMessage = (String) model.asMap().get("successMessage");

//      Pass the success message to the view if it exists
        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
        }

//      Get Weather for User's City
        String city = userFromDb.getCity();
        String key = keys.getOpenWeather();
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key +
                "&units=imperial");
        Weather userWeather = getWeather(url);

//      Aggregate all plants in user's garden
        List<GardenPlant> allPlants = gardenPlantDao.findGardenPlantByUser(userFromDb);

//      Run conditional logic for all plants based on Days passed
        for (GardenPlant plant: allPlants) {
            GardenPlant gardenPlant = gardenPlantDao.findGardenPlantsById(plant.getId());
            GardenPlant checked = plantTipCheck(gardenPlant, userWeather);
            gardenPlantDao.save(checked);
        }

        List <User> friendsRequest = friendsRequests(userFromDb);
        List <User> friends = knownFriends(userFromDb);

        for (User userTest : friends) {
            System.out.println(userTest.getCity());
        }

        model.addAttribute("friendsRequest", friendsRequest);
        model.addAttribute("friends", friends);

//      Set Weather for View
        model.addAttribute("weather", userWeather);

//      Set Outdoor plants for quick weather reference and warnings
        model.addAttribute("outdoorPlants", checkForOutdoorPlants(userFromDb));

//      Set All Plants for Garden Preview
        model.addAttribute("userPlants", allPlants);

        System.out.println(userFromDb.getUsername());
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
    public String editUserProfileForm(@PathVariable Long id, Model model) {
        User user = usersDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        model.addAttribute("user", user);
        return "editUserForm";
    }


    @PostMapping("/{id}/edit")
    public String updateUserProfile(@PathVariable Long id, @ModelAttribute("user") User updatedUser) {
//        User user = userDao.findUserById(1L);
        User users = usersDao.findUserById(1L);
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        users.setUsername(updatedUser.getUsername());
        users.setFirst_name(updatedUser.getFirst_name());
        users.setLast_name(updatedUser.getLast_name());
        users.setCity(updatedUser.getCity());
        users.setEmail(updatedUser.getEmail());
//        userDao.save(user);
        usersDao.save(users);
        return "redirect:/users/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteUserProfile(@PathVariable Long id) {
        usersDao.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", usersDao.findAll());
        return "users";
    }


}