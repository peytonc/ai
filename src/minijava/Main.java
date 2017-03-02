package minijava;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
	private static final String PROPERTIES_FILENAME = new String("config.properties");
	public final static String PROGRAM_FILENAME = new String("GeneticProgram.java");
	public final static int maxSpecies = 5;	// Number of species in environment
	public final static int threads = (int)Math.ceil((double)Runtime.getRuntime().availableProcessors()/maxSpecies)+1;
	public final Path pathBase = Paths.get("");
	public int daysPerYear = 1000;
	public static int randomSeed = 0;
	
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	private String sourceOrigin = null;
	private Tests test = new Tests();
	List<Species> listSpecies = new ArrayList<Species>(maxSpecies);
	
	public Main() {
		try(InputStream inputStream = new FileInputStream(PROPERTIES_FILENAME)) {
			LogManager.getLogManager().readConfiguration(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			sourceOrigin = new String(Files.readAllBytes(Paths.get(PROGRAM_FILENAME)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		sourceOrigin = Species.replacePackage(sourceOrigin, 0, 0);
		for(int index=0; index<maxSpecies; index++) {
			listSpecies.add(new Species(index, sourceOrigin, test, daysPerYear));
		}
	}
	
	public void initalizeYear(int year) {
		for(Species species : listSpecies) {
			species.initalizeYear(year);
		}
	}
	
	// Species extinction when stagnant exceeds max threshold and least fit
	public void extinction() {
		Fitness fitnessLeastFit = null;
		int leastFitIndex = -1;
		int index = 0;
		for(Species species : listSpecies) {
			if(species.stagnant < 0) {
				if(fitnessLeastFit == null) {
					leastFitIndex = index;
					fitnessLeastFit = species.fitnessBest;
				} else if(fitnessLeastFit.fit < species.fitnessBest.fit) {	// first store in terms of least fit, next use compareTo
					if(fitnessLeastFit.compareTo(species.fitnessBest) < 0) {
						leastFitIndex = index;
						fitnessLeastFit = species.fitnessBest;
					}
				}
			}
			index++;
		}
		if(leastFitIndex >= 0) {
			index = listSpecies.get(leastFitIndex).species;
			listSpecies.get(leastFitIndex).extinction();
			listSpecies.remove(leastFitIndex);
			listSpecies.add(new Species(index, sourceOrigin, test, daysPerYear));
		}
	}
	
	public void executeDay(int day) {
		ExecutorService executorService = Executors.newFixedThreadPool(threads);
		test.createTests();
		try {
			for(Species species : listSpecies) {
				species.initalizeDay(day);
				executorService.execute(species);
			}
			executorService.shutdown();
			int minute = 0;
			// The Species ExecutorService is within this ExecutorService. Don't shutdownNow here as it hangs a Species forever
			while(!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
				LOGGER.warning("Runaway thread for " + minute + " minute(s)");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			LOGGER.severe("Exception on terminate");
		}
	}
	
	public boolean isSolved() {
		for(Species species : listSpecies) {
			if(species.fitnessBest.fit == 0) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		int year = 0;
		do {
			main.initalizeYear(year);
			main.extinction();
			for(int day=0; day<main.daysPerYear; day++) {
				main.executeDay(day);
			}
			year++;
		} while(!main.isSolved());
	}
}
