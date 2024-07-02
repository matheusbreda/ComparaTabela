package br.com.syma.postgres.compare;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.syma.mysql.Result;
import br.com.syma.mysql.ResultRepositoryMysql;
import br.com.syma.postgres.ResultRepository;


@Service
public class ResultComparaService {

    @Autowired
    private ResultRepository tabelaARepository;

    @Autowired
    private ResultRepositoryMysql tabelaBRepository;

    @Autowired
    private ResultComparaRepository terceiraTabelaRepository;

    @Transactional
    public List<ResultCompara> compararETSalvarNaoCompatíveis() {
    	terceiraTabelaRepository.deleteAll();
        List<ResultCompara> itensSalvos = new ArrayList<>();
        List<br.com.syma.postgres.Result> tabelaAItems = tabelaARepository.findAll();
        List<Result> tabelaBItems = tabelaBRepository.findAll();
        for (br.com.syma.postgres.Result itemA : tabelaAItems) {
            boolean compativel = false;
            for (Result itemB : tabelaBItems) {
                if (itemA.getID().equals(itemB.getID()) && 
                		itemA.getNAME().equals(itemB.getNAME())) {
		                    compativel = true;                 
                }
            }
            if (!compativel) {
            	
                ResultCompara novoItem = new ResultCompara();
                novoItem.setID(itemA.getID());
                novoItem.setNAME(itemA.getNAME());
                novoItem.setCODE(itemA.getCODE());
                
                terceiraTabelaRepository.save(novoItem);
                itensSalvos.add(novoItem);
            }
        }
		return itensSalvos;
    }
}

