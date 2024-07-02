package br.com.syma.mysql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import br.com.syma.postgres.compare.ResultComparaRepository;


@Service
public class ProductService {

	@Autowired
	private ResultRepositoryMysql resultRepositoryMysql;
	
	@Autowired
	private TimeRepositoryMysql timeRepositoryMysql;
	
	@Autowired
	private RootRepositoryMysql rootRepositoryMysql;
	
	public List<Root> consumirApiProduto(@PathVariable("page") int page) {
		List<Root> allResults = new ArrayList<>();
		 int limit = 100;
	     int pageSize = 50;
	     long startTimeTotal = System.currentTimeMillis();

	     while (page <= limit) {
	    
	    	 long startTime = System.currentTimeMillis();
	    	 RestTemplate restTemplate = new RestTemplate();
	    	 RootDTO resultAPI = restTemplate.getForObject(String.format("https://syma.bitrix24.com.br/rest/71/1uqgro6xgaz5v5rg/crm.product.list.json?&start=%s", page), RootDTO.class);
		
		if(resultAPI != null) {
			Time time = new Time();
			time.setDateFinish(resultAPI.getTime().getDateFinish());
			time.setDateStart(resultAPI.getTime().getDateStart());
			time.setStart(resultAPI.getTime().getStart());
			time.setFinish(resultAPI.getTime().getFinish());
			time.setDuration(resultAPI.getTime().getDuration());
			time.setProcessing(resultAPI.getTime().getProcessing());
			time.setOperatingResetAt(resultAPI.getTime().getOperatingResetAt());
			time.setOperating(resultAPI.getTime().getOperating());
			
			timeRepositoryMysql.save(time);
		}
		
		if (resultAPI != null && resultAPI.result != null) {
		    for (Result apiResult : resultAPI.result) {
		        Result result = new Result();
		        result.setID(apiResult.getID());
		        result.setNAME(apiResult.getNAME());
		        result.setCODE(apiResult.getCODE());
		        result.setACTIVE(apiResult.getACTIVE());
		        result.setSORT(apiResult.getSORT());
		        result.setXML_ID(apiResult.getXML_ID());
		        result.setTIMESTAMP_X(apiResult.getTIMESTAMP_X());
		        result.setDATE_CREATE(apiResult.getDATE_CREATE());
		        result.setMODIFIED_BY(apiResult.getMODIFIED_BY());
		        result.setCREATED_BY(apiResult.getCREATED_BY());
		        result.setCATALOG_ID(apiResult.getCATALOG_ID());
		        result.setDESCRIPTION_TYPE(apiResult.getDESCRIPTION_TYPE());
		        result.setVAT_INCLUDED(apiResult.getVAT_INCLUDED());

		        resultRepositoryMysql.save(result);
		    }
		}
		
		if(resultAPI != null) {
			Root root = new Root();
			root.setResult(resultAPI.getResult());
			root.setNext(resultAPI.getNext());
			root.setTime(resultAPI.getTime());
			
			rootRepositoryMysql.save(root);
			allResults.add(root);
		}
		
		long endTime = System.currentTimeMillis();
		double seconds = (endTime - startTime) / 1000.0;
		System.out.println("Tempo de cada iteração: " + seconds + " segundos");
		
		 page += pageSize;
    }
	     
	    long endTimeTotal = System.currentTimeMillis();
		double secondsTotal = (endTimeTotal - startTimeTotal) / 1000.0;
		System.out.println("Tempo total de iteração: " + secondsTotal + " segundos");
		
		return allResults;
	}
}
