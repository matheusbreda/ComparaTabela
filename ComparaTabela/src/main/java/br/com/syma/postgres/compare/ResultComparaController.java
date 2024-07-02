package br.com.syma.postgres.compare;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*@Component
public class ResultComparaController {
	
	    @Autowired
	    private ResultComparaService comparacaoService;

	    @Scheduled(fixedRate = 60000) // Executa a cada 60 segundos
	    public void executarComparacao() {
	        comparacaoService.compararETSalvarNaoCompatíveis();
	    }
}*/

@RestController
@RequestMapping("/compara")
public class ResultComparaController {

    @Autowired
    private ResultComparaService comparacaoService;

    @PostMapping("/exec")
    public ResponseEntity<List<ResultCompara>> executarComparacao() {
        List<ResultCompara> itensSalvos = comparacaoService.compararETSalvarNaoCompatíveis();
        return ResponseEntity.ok(itensSalvos);
    }

}

