package com.codeup.plantapp.controllers;

import com.codeup.plantapp.models.*;
import com.codeup.plantapp.repositories.GardenPlantRepository;
import com.codeup.plantapp.repositories.PlantLogRepository;
import com.codeup.plantapp.repositories.PlantRepository;
import com.codeup.plantapp.repositories.UserRepository;
import com.codeup.plantapp.services.Keys;
import com.codeup.plantapp.util.PlantDTO;
import com.codeup.plantapp.util.PlantResultDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import static com.codeup.plantapp.services.PlantsCall.*;

@Controller
@RequestMapping("/plants")
public class PlantController {


    private final UserRepository usersDao;
    private final PlantRepository plantsDao;
    private final GardenPlantRepository gardenPlantsDao;
    private final PlantLogRepository plantLogsDao;


    @Autowired
    private Keys keys;

    private static final String TREFLE_API_URL = "https://trefle.io/api/v1/plants/search";

    private final RestTemplate restTemplate;

    public PlantController(UserRepository usersDao, PlantRepository plantsDao, GardenPlantRepository gardenPlantsDao,
                           PlantLogRepository plantLogsDao) {
        this.restTemplate = new RestTemplate();
        this.usersDao = usersDao;
        this.plantsDao = plantsDao;
        this.gardenPlantsDao = gardenPlantsDao;
        this.plantLogsDao = plantLogsDao;
    }

/*
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>><<>><ADD PLANT NOT IN API ><<>><<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
*/
//
//    @GetMapping("/add")
//    public String addPlantForm(HttpSession session, Model model){
//        session.getAttribute("user");
//        model.addAttribute("plant", new Plant());
//        return "addPlantForm";
//    }
//
//    @PostMapping("/add")
//    public String addPlant(@ModelAttribute Plant plant,
//                           @RequestParam(name="name") String plant_name,
//                           @RequestParam(name="image_url") String image_url,
//                           @RequestParam(name="sun_amount") sun_amount sun_amount,
//                           @RequestParam(name="water_interval") long water_interval,
//                           @RequestParam(name="is_outside") boolean is_outside){
//        Plant newPlant = new Plant();
//        plantsDao.save(newPlant);
//
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User userFound = usersDao.findUserById(user.getId());
//
//        LocalDate date = LocalDate.now();
//
//        GardenPlant newGardenPlant = new GardenPlant(userFound, newPlant, sun_amount, date, water_interval, is_outside);
//
//        gardenPlantsDao.save(newGardenPlant);
//        return "redirect:/users/profile";
//    }


/*
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>><<>><FIND PLANT IN API ><<>><<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
*/

    @GetMapping("/search/{query}")
    public String showSearchForm(@PathVariable("query") String query, Model model) {
        String apiUrl = TREFLE_API_URL + "?token=" + keys.getTrefle() + "&q=" + query;
        PlantResultDTO plantResult = restTemplate.getForObject(apiUrl, PlantResultDTO.class);

        model.addAttribute("query", query);
        assert plantResult != null;
        model.addAttribute("plants", plantResult.getData());

        return "searchResults";
    }

    @PostMapping("/search")
    public String searchPlants(@RequestParam("query") String query) {
        return "redirect:/plants/search/" + query;
    }



/*
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>><<>><VIEW PLANT DETAILS><<>><<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
*/

    @GetMapping("/{id}")
    public String showPlantDetails(@PathVariable("id") String id, Model model) throws Exception {
        URL trefleApiUrl = new URL("https://trefle.io/api/v1/plants/" + id + "?token=" + keys.getTrefle());
        PlantDTO plant = getTreflePlant(trefleApiUrl);

        assert plant != null;
        String selectedPlantCommonName = plant.getCommon_name();

        String commonNameSlug = selectedPlantCommonName.toLowerCase().replace(" ", "-");

        URL openfarmApiUrl = new URL("https://openfarm.cc/api/v1/crops/" + commonNameSlug);

        // Call ChatGPT to get the care guide
//        String chatKey = keys.getChatGPT();
//        String prompt = "Give me a care guide for " + selectedPlantCommonName + "!";
//        String careGuide = getChatGPTResponse(prompt, chatKey);
//        primedPlant.setCareGuide(careGuide);

        PlantDTO primedPlant = getOpenFarmPrimer(openfarmApiUrl, plant);

        model.addAttribute("plant", primedPlant);
        model.addAttribute("selectedPlantCommonName", selectedPlantCommonName);

        return "view-more";
    }

/*
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>><<>><USER SAVE A PLANT ><<>><<>><<>><<>><<>><<>><<>><|
*/

    @PostMapping("/{id}")
    public String savePlant(@PathVariable("id") String id,
                            @RequestParam(name="name") String plant_name,
                            @RequestParam(name="openFarmID") String openfarm_id,
                            @RequestParam(name="plantImage") String image_url,
                            @RequestParam(name="CommonName") String common_name,
                            @RequestParam(name="sun_amount") sun_amount sun_amount,
                            @RequestParam(name="careGuide") String careGuide,
                            @RequestParam(name="water_interval") long water_interval,
                            @RequestParam(name="is_outside") boolean is_outside
    ) {

    String name = plant_name.isEmpty() ? common_name : plant_name;

    Plant userPlant = new Plant(id, openfarm_id, name, image_url);
    plantsDao.save(userPlant);

    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User userFound = usersDao.findUserById(user.getId());

    LocalDate date = LocalDate.now();

    GardenPlant newGardenPlant = new GardenPlant(userFound, userPlant, sun_amount, date, water_interval, is_outside);
    newGardenPlant.setCareGuide(careGuide);

    gardenPlantsDao.save(newGardenPlant);
    return "redirect:/users/profile";
}

/*
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
*/
/*
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>><<>><USER DELETE PLANT ><<>><<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
*/
    @GetMapping("/{id}/delete")
    public String deletePlant(@PathVariable long id) {
        GardenPlant userGardenPlant = gardenPlantsDao.findGardenPlantsById(id);
        List<PlantLog> remove = plantLogsDao.findPlantLogByGardenPlant(userGardenPlant);
        plantLogsDao.deleteAll(remove);
        gardenPlantsDao.deleteById(id);
        long userPlant = userGardenPlant.getPlant().getId();
        plantsDao.deleteById(userPlant);
        return "redirect:/users/profile";
    }

/*
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>><<>><USER UPDATE PLANT ><<>><<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
*/
    @PostMapping("/plantEdit/{id}")
    public String updateUserPlant(
            @PathVariable("id") long id,
            @RequestParam(name="name") String plant_name,
            @RequestParam(name="sun_amount") sun_amount sun_amount,
            @RequestParam(name="water_interval") long water_interval,
            @RequestParam(name="is_outside") boolean is_outside ) {
        GardenPlant updateGardenPlant = gardenPlantsDao.findGardenPlantsById(id);
        Plant updatePlant = updateGardenPlant.getPlant();
        updatePlant.setName(plant_name);
        plantsDao.save(updatePlant);

        updateGardenPlant.setSun_amount(sun_amount);
        updateGardenPlant.setWater_interval(water_interval);
        updateGardenPlant.setIs_outside(is_outside);
        gardenPlantsDao.save(updateGardenPlant);

        return "redirect:/users/profile";
    }

/*
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>><<>><<USER QUICK WATER>><<>><<>><<>><<>><<>><<>><<>><|
*/
    @GetMapping("/quickWater/{id}")
    public String quickWater(@PathVariable("id") long id) {
        GardenPlant updateGardenPlant = gardenPlantsDao.findGardenPlantsById(id);
        updateGardenPlant.setLast_watered(LocalDate.now());
        gardenPlantsDao.save(updateGardenPlant);
        return "redirect:/users/profile";
    }

/*
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
*/
/*
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>><<>><<User Garden Plant><<>><<>><<>><<>><<>><<>><<>><|
|><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><|
*/

    @GetMapping("/garden/{id}")
    public String viewPlantManager(
            @PathVariable("id") long id,
            Model model) throws Exception {
        String treflePlantId = gardenPlantsDao.findGardenPlantsById(id).getPlant().getTrefle_id();

        URL trefleApiUrl = new URL("https://trefle.io/api/v1/plants/" + treflePlantId + "?token=" + keys.getTrefle());
        PlantDTO plant =  getTreflePlant(trefleApiUrl);

        String selectedPlantCommonName = plant.getCommon_name();
        String commonNameSlug = selectedPlantCommonName.toLowerCase().replace(" ", "-");

        URL openfarmApiUrl = new URL("https://openfarm.cc/api/v1/crops/" + commonNameSlug);

//        OLD CHAT GPT CODE
//        String chatKey = keys.getChatGPT();
//        String prompt = "Give me a care guide for " + selectedPlantCommonName + "!";
//        String careGuide = getChatGPTResponse(prompt, chatKey);
//        primedPlant.setCareGuide(careGuide);
///////////////////////////////////////////////////////////////////////////////////////

        PlantDTO primedPlant = getOpenFarmPrimer(openfarmApiUrl, plant);

        GardenPlant userGardenPlant = gardenPlantsDao.findGardenPlantsById(id);
        Plant userPlant = userGardenPlant.getPlant();
        List<PlantLog> usersPlantLogs = plantLogsDao.findPlantLogByGardenPlant(userGardenPlant);

        model.addAttribute("careGuide", gardenPlantsDao.findGardenPlantsById(id).getCareGuide());
        model.addAttribute("plant", primedPlant);
        model.addAttribute("selectedPlantCommonName", plant.getCommon_name());
        model.addAttribute("userGardenPlant", userGardenPlant);
        model.addAttribute("userPlant", userPlant);
        model.addAttribute("usersPlantLogs", usersPlantLogs);

        return "userPlantManager";
    }

    @GetMapping("/diagnose/{id}.{stems}.{leaves}.{fruits}")
    public String diagnosePlant(Model model,
                                @PathVariable("id") long id,
                                @PathVariable("stems") String stems,
                                @PathVariable("leaves") String leaves,
                                @PathVariable("fruits") String fruits) throws Exception {
        String treflePlantId = gardenPlantsDao.findGardenPlantsById(id).getPlant().getTrefle_id();

        URL trefleApiUrl = new URL("https://trefle.io/api/v1/plants/" + treflePlantId + "?token=" + keys.getTrefle());
        PlantDTO plant =  getTreflePlant(trefleApiUrl);

        assert plant != null;
        String selectedPlantCommonName = plant.getCommon_name();

        PlantMedForm plantMedForm = new PlantMedForm(selectedPlantCommonName, stems, leaves, fruits);

        String chatKey = keys.getChatGPT();

        String chatResponse = getChatGPTDiagnosis(plantMedForm, chatKey);

        model.addAttribute("plantID", id);
        model.addAttribute("form", plantMedForm);
        model.addAttribute("chatResponse", chatResponse);


        return "diagnosePlant";
    }

    @PostMapping("/diagnose/{id}")
    public String diagnosePlant(@PathVariable("id") long id,
                                @RequestParam(name="stems") String stems,
                                @RequestParam(name="leaves") String leaves,
                                @RequestParam(name="fruits") String fruits) {

        return "redirect:/plants/diagnose/"+ id +"."+ stems +"."+ leaves +"."+ fruits;
    }

    @PostMapping("/comment/{id}")
    public String savePlantLog(
            RedirectAttributes redirectAttributes,
            @PathVariable("id") long id,
            @RequestParam(name="title") String title,
            @RequestParam(name="content") String content) {

        LocalDate date = LocalDate.now();
        GardenPlant userGardenPlant = gardenPlantsDao.findGardenPlantsById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PlantLog plantLog = new PlantLog(title, content, date, userGardenPlant, user);
        plantLogsDao.save(plantLog);

        redirectAttributes.addFlashAttribute("successMessage", "Comment submitted successfully!");

        return "redirect:/plants/garden/{id}";
    }

    @GetMapping("/comment/delete/{plant}.{id}")
    public String deletePlantLog(
            @PathVariable long id,
            @PathVariable long plant ){

        plantLogsDao.deleteById(id);

        return "redirect:/plants/garden/{plant}";
    }

    @GetMapping("/careguide/{plant}")
    public ResponseEntity<String> careGuideAI(Model model,
                                              @PathVariable String plant) throws Exception {

        String chatKey = keys.getChatGPT();
        String prompt = "Give me a care guide for " + plant + "!";
        String careGuide = getChatGPTResponse(prompt, chatKey);

        model.addAttribute("chatGPTGuide", careGuide);

        return ResponseEntity.ok(careGuide);
    }
}