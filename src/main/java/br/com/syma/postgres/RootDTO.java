package br.com.syma.postgres;

import java.util.ArrayList;
import lombok.Data;

@Data
public class RootDTO {
	
		public ArrayList<Result> result;
	    public int next;
	    public int total;
	    public Time time;
}

