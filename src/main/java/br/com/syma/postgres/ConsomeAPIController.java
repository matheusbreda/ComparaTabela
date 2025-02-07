package br.com.syma.postgres;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("postgres")
@CrossOrigin(origins="*", maxAge = 3600)
public class ConsomeAPIController {
	
	@Autowired
	private ConsomeAPIService consomeService;
	
	@GetMapping("{page}")
	public List<Root> listar(@PathVariable int page){
		return consomeService.consumirApi(page);
	}
	
}
