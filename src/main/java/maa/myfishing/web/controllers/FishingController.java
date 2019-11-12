package maa.myfishing.web.controllers;

import maa.myfishing.service.models.OverNightBindingModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FishingController extends BaseController {

    @GetMapping("/add-fishing")
    public ModelAndView getAddFishing(ModelAndView modelAndView, @ModelAttribute(name = "overnight") OverNightBindingModel overnight) {
        modelAndView.addObject("model", overnight);
        modelAndView.setViewName("add-fishing.html");
        return modelAndView;

    }
}
