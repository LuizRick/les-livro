package web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping("/olaSpring")
	public String execute() {
		System.out.println("Executando methdo spring");
		return "ok";
	}
}
