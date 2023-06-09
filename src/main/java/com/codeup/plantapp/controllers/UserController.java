package com.codeup.plantapp.controllers;

import com.codeup.plantapp.models.*;
import com.codeup.plantapp.repositories.*;
import com.codeup.plantapp.services.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    private final PlantLogRepository plantlogsDao;

    public UserController(UserRepository usersDao, GardenPlantRepository gardenPlantDao,
                          FriendRepository friendDao, PlantLogRepository plantlogsDao,
                          PlantRepository plantDao, PasswordEncoder passwordEncoder) {
        this.usersDao = usersDao;
        this.gardenPlantDao = gardenPlantDao;
        this.passwordEncoder = passwordEncoder;
        this.plantDao = plantDao;
        this.friendDao = friendDao;
        this.plantlogsDao = plantlogsDao;
    }

    @Autowired
    private Keys keys;

    @GetMapping("/about")
    public String aboutUs() {
        return "aboutus";
    }

    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        return "createUserForm";
    }

    @PostMapping("/create")
    public String createUserProfile(@ModelAttribute("user") User user,
                                    @RequestParam(name = "subscribe", required = false) String subscribe,
                                    Model model) {
        // Check if username already exists
        User existingUser = usersDao.findByUsername(user.getUsername());
        if (existingUser != null) {
            model.addAttribute("usernameError", "Username already exists. Please choose a different username.");
            return "createUserForm";
        }

        // Check if email already exists
        User existingEmailUser = usersDao.findByEmail(user.getEmail());
        if (existingEmailUser != null) {
            model.addAttribute("emailError", "Email already exists. Please use a different email address.");
            return "createUserForm";
        }

        user.setCreated_at(LocalDate.now());

        String userPic = "https://cdn.filestackcontent.com/mzEXQKGFQvW4pbksWgeB";
        user.setProfile_pic(userPic);

        if (subscribe == null) {
            user.setIs_emailNotifiable(false);
        } else {
            user.setIs_emailNotifiable(true);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        usersDao.save(user);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = user.getId();

        User userFromDb = usersDao.findUserById(userId);
        model.addAttribute("user", userFromDb);

        // Get Weather for User's City
        String city = userFromDb.getCity();
        String weatherKey = keys.getOpenWeather();
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?zip=" + city + "&appid=" + weatherKey + "&units=imperial");
        String zipError = "Weather data not available, check zipcode";
        Weather userWeather = getWeather(url) == null ? new Weather(0, 0, "NA", "NA", "NA", 0, zipError, "NA") : getWeather(url);

        // Aggregate all plants in user's garden
        List<GardenPlant> allPlants = gardenPlantDao.findGardenPlantByUser(userFromDb);

        // Run conditional logic for all plants based on Days passed
        for (GardenPlant plant: allPlants) {
            GardenPlant gardenPlant = gardenPlantDao.findGardenPlantsById(plant.getId());
            GardenPlant checked = plantTipCheck(gardenPlant, userWeather);
            gardenPlantDao.save(checked);
        }

        List <User> friendsRequest = friendsRequests(userFromDb);
        List <User> friends = knownFriends(userFromDb);

        model.addAttribute("friendsRequest", friendsRequest);
        model.addAttribute("friends", friends);
        model.addAttribute("weather", userWeather);
        model.addAttribute("outdoorPlants", checkForOutdoorPlants(userFromDb));
        model.addAttribute("userPlants", allPlants);
        model.addAttribute("recentPlantLogs", plantlogsDao.findTop5ByUserOrderByCreatedAtDesc(user));

        return "userProfile";
    }


    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable("id") long id, Model model) {
        User user = usersDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        model.addAttribute("user", user);
        return "userProfile";
    }

    @GetMapping("/{id}/edit")
    public String editUserProfileForm(@PathVariable(name = "id") Long id, Model model) {
        User user = usersDao.findUserById(id);
        model.addAttribute("user", user);

        String fileStackKey = keys.getfileStack();
        model.addAttribute("filestackKey", fileStackKey);
        return "editUserForm";
    }

    @PostMapping("/{id}/edit")
    public String updateUserProfile(@PathVariable(name = "id") Long id,
                                    @RequestParam(name = "subscribe", required = false) String subscribe,
                                    @RequestParam(name = "filestackUrl", required = false) String fileStackLink,
                                    @ModelAttribute User updatedUser) throws IOException {
        User user = usersDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));
        user.setUsername(updatedUser.getUsername());
        user.setFirst_name(updatedUser.getFirst_name());
        user.setLast_name(updatedUser.getLast_name());
        user.setCity(updatedUser.getCity());
        user.setEmail(updatedUser.getEmail());

        String userPic = fileStackLink == null ? user.getProfile_pic() : fileStackLink;
        user.setProfile_pic(userPic);

        if (subscribe == null) {
            user.setIs_emailNotifiable(false);
        } else {
            user.setIs_emailNotifiable(true);
        }
        usersDao.save(user);

        return "redirect:/users/profile";
    }

    @PostMapping("/{id}/pic")
    public String changeProfPic (
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "filestackUrl", required = false) String fileStackLink) {

        User user = usersDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));

        String userPic = fileStackLink == null ? "https://cdn.filestackcontent.com/mzEXQKGFQvW4pbksWgeB" : fileStackLink;
        user.setProfile_pic(userPic);

        usersDao.save(user);

        return "redirect:/users/profile";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable(name = "id") Long id, Model model) {
        User user = usersDao.findUserById(id);
        model.addAttribute("user", user);
        usersDao.deleteById(id);
        return "deleteProfile";
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", usersDao.findAll());
        return "users";
    }
}