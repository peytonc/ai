package minijava;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class GP {
	public static final String PROGRAM_FILENAME = new String("GeneticProgram.java");
	public static final int MAX_SPECIES = 3;	// Number of species in environment
	public static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
	public static final int THREADS_PER_SPECIES = (int)Math.ceil((double)AVAILABLE_PROCESSORS/MAX_SPECIES)+1;
	public static final int DAYS_PER_YEAR = 1000;
	public static final int RANDOM_SEED = 0;
	public static final int MAX_EXECUTE_MILLISECONDS = 2000;
	public static final int MAX_EXECUTE_MILLISECONDS_95PERCENT = (int) Math.floor(0.95*MAX_EXECUTE_MILLISECONDS);
	public static final double RESTRICT_MIN_PERCENT = 0.975;	// use small seasonal differences (i.e. RESTRICT_MAX_PERCENT-RESTRICT_MIN_PERCENT < 0.05)
	public static final double RESTRICT_MAX_PERCENT = 1.025;
	private static final String PROPERTIES_FILENAME = new String("config.properties");
	private static final Logger LOGGER = Logger.getLogger(GP.class.getName());
	
	public static Fitness fitnessBestGlobal = null;
	private static String stringBestSource = null;
	public static int sizeSourceLength;
	List<Species> listSpecies = new ArrayList<Species>(MAX_SPECIES);
	public int sizeBeforeRestrictMin = 0;
	public int sizeBeforeRestrictMax = 0;
	public static int sizeBeforeRestrict = 0;
	public static BigInteger sizeBeforeRestrictBigInteger = Constants.I0;
	private int speedBeforeRestrictMin = (int)(RESTRICT_MIN_PERCENT * MAX_EXECUTE_MILLISECONDS/2.0);
	private int speedBeforeRestrictMax = (int)(RESTRICT_MAX_PERCENT * MAX_EXECUTE_MILLISECONDS/2.0);
	public static int speedBeforeRestrict = 0;
	public static BigInteger speedBeforeRestrictBigInteger = Constants.I0;
	
	
	public GP() {
		try(InputStream inputStream = new FileInputStream(PROPERTIES_FILENAME)) {
			LogManager.getLogManager().readConfiguration(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			stringBestSource = new String(Files.readAllBytes(Paths.get(PROGRAM_FILENAME)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		stringBestSource = Species.replacePackage(stringBestSource, 0, 0);
		stringBestSource = Species.removeSpace(stringBestSource);
		sizeSourceLength = stringBestSource.length();
		for(int index=0; index<MAX_SPECIES; index++) {
			Species species = new Species(index, stringBestSource);
			listSpecies.add(species);
		}
		
		int year = 0;
		do {
			initalizeYear(year);
			extinction();
			for(int day=0; day<DAYS_PER_YEAR; day++) {
				executeDay(day);
			}
			year++;
		} while(!isSolved());
	}
	
	public void initalizeYear(int year) {
		sizeBeforeRestrictMin = (int)(RESTRICT_MIN_PERCENT * sizeSourceLength);
		sizeBeforeRestrictMax = (int)(RESTRICT_MAX_PERCENT * sizeSourceLength);
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
				} else if(fitnessLeastFit.compareTo(species.fitnessBest) < 0) {
					leastFitIndex = index;
					fitnessLeastFit = species.fitnessBest;
				}
			}
			index++;
		}
		if(leastFitIndex >= 0) {
			index = listSpecies.get(leastFitIndex).species;
			listSpecies.get(leastFitIndex).extinction();
			listSpecies.remove(leastFitIndex);
			Species species = new Species(index, stringBestSource);	// create a new species as replacement to old
			listSpecies.add(species);
		}
	}
	
	public void executeDay(int day) {
		ExecutorService executorService = Executors.newFixedThreadPool(THREADS_PER_SPECIES);
		Tests.getTests().createTests();
		createEnviroment(day);
		try {
			for(Species species : listSpecies) {
				species.initalizeDay(day);
				executorService.execute(species);
			}
			executorService.shutdown();
			long timeStart = System.nanoTime();
			// Species ExecutorService is within this ExecutorService. Don't shutdownNow here as it hangs a Species forever
			while(!executorService.awaitTermination(MAX_EXECUTE_MILLISECONDS*Species.MAX_POPULATION, TimeUnit.MILLISECONDS)) {
				timeStart = System.nanoTime();
				LOGGER.warning("Runaway thread for " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - timeStart) + "ms");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			LOGGER.severe("Exception on terminate");
		}
	}
	
	public void createEnviroment(int day) {
		// model environment resource (specifically program size) as Summer-Winter-Summer or sizeBeforeRestrictMax-sizeBeforeRestrictMin-sizeBeforeRestrictMax
		double percent = (double)(day%DAYS_PER_YEAR)/DAYS_PER_YEAR;
		double cosineWithOffset = (Math.cos(percent*2.0*Math.PI)+1.0)/2.0;	// range in [0,1]
		sizeBeforeRestrict = (int)(sizeBeforeRestrictMin + cosineWithOffset*(sizeBeforeRestrictMax-sizeBeforeRestrictMin));
		sizeBeforeRestrictBigInteger = BigInteger.valueOf(sizeBeforeRestrict);
		speedBeforeRestrict = (int)(speedBeforeRestrictMin + cosineWithOffset*(speedBeforeRestrictMax-speedBeforeRestrictMin));
		speedBeforeRestrictBigInteger = BigInteger.valueOf(speedBeforeRestrict);
		if (sizeSourceLength < stringBestSource.length()) {	
			sizeSourceLength++;		// smooth convergence for fitness function
		} else if (sizeSourceLength > stringBestSource.length()) {
			sizeSourceLength--;
		}
	}
	
	public boolean isSolved() {
		for(Species species : listSpecies) {
			if(species.fitnessBest!=null && species.fitnessBest.difference.compareTo(Constants.I0) == 0) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		GP gp = new GP();
	}
}
