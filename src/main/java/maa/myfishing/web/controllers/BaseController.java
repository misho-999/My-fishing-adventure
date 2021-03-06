package maa.myfishing.web.controllers;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

public abstract class BaseController {

    protected ModelAndView view(String viewName, ModelAndView modelAndView) {
        modelAndView.setViewName(viewName);

        return modelAndView;
    }

    protected ModelAndView view(String viewName) {
        return this.view(viewName, new ModelAndView());
    }

    protected ModelAndView redirect(String url) {
        return this.view("redirect:" + url);
    }

    protected boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

}
