package gp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import gp.comparator.FitnessComparators;
import gp.comparator.ProgramComparators;
import gp.logging.FileHandlerBest;
import gp.test.Tests;

public class GP {
	public static final String PROGRAM_FILENAME = new String("GeneticProgram.java");
	public static final int MAX_SPECIES = 1;	// Number of species in environment
	public static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
	public static final int THREADS_PER_SPECIES = (int)Math.ceil((double)AVAILABLE_PROCESSORS/MAX_SPECIES)+1;
	public static final int RANDOM_SEED = 0;
	private static final String PROPERTIES_FILENAME = new String("config.properties");
	private static final Logger LOGGER = Logger.getLogger(GP.class.getName());
	public static final Logger LOGGER_BEST = Logger.getLogger("gp.logging.FileHandlerBest");
	public static final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
	public static Fitness fitnessBestGlobal = null;
	private static String stringBestSource = null;
	public static int sizeSourceLength;
	List<Species> listSpecies = new ArrayList<Species>(MAX_SPECIES);
	
	
	public GP() {
		try(InputStream inputStream = new FileInputStream(PROPERTIES_FILENAME)) {
			LogManager.getLogManager().readConfiguration(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			LOGGER_BEST.addHandler(new FileHandlerBest());
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
			return;
		}
		// GP has the highest priority and is almost always sleeping (highest priority to interrupt Species and CallableMiniJava)
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		try {
			threadMXBean.setThreadCpuTimeEnabled(true);
		} catch (UnsupportedOperationException | SecurityException e) {
			LOGGER.severe("OS and JVM must support CPU time as defined by ThreadMXBean");
			return;
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
		ProgramComparators.getProgramComparators().createProgramComparators();
	}
	
	public void simulate() {
		int year = 0;
		do {
			initalizeYear(year);
			extinction(year);
			for(int day=0; day<Environment.DAYS_PER_YEAR; day++) {
				executeDay(day);
			}
			year++;
			System.gc();
		} while(!isSolved());
	}
	
	public void initalizeYear(int year) {
		Environment.getEnvironment().createYear(sizeSourceLength);
		for(Species species : listSpecies) {
			species.initalizeYear(year);
		}
	}
	
	// Species extinction when stagnant exceeds max threshold and least fit
	public void extinction(int year) {
		Fitness fitnessLeastFit = null;
		int leastFitIndex = -1;
		int index = 0;
		for(Species species : listSpecies) {
			if(species.stagnant < 0) {
				if(fitnessLeastFit == null) {
					leastFitIndex = index;
					fitnessLeastFit = species.fitnessBest;
				} else if(FitnessComparators.BY_MEAN_CORRECT.compare(fitnessLeastFit, species.fitnessBest) < 0) {
					leastFitIndex = index;
					fitnessLeastFit = species.fitnessBest;
				}
			}
			index++;
		}
		if(leastFitIndex >= 0) {
			index = listSpecies.get(leastFitIndex).species;
			GP.LOGGER_BEST.info("\t" + year + "\t" + -1 + "\t" + index + "\t" + -1 + "\t" + fitnessLeastFit);
			listSpecies.get(leastFitIndex).extinction();
			listSpecies.remove(leastFitIndex);
			Species species = new Species(index, stringBestSource);	// create a new species as replacement to old
			listSpecies.add(species);
		}
	}
	
	public void executeDay(int day) {
		ExecutorService executorService = Executors.newFixedThreadPool(THREADS_PER_SPECIES);
		Tests.getTests().createTests();
		Environment.getEnvironment().createDay(day);
		if (sizeSourceLength < stringBestSource.length()) {	
			sizeSourceLength++;		// smooth convergence for fitness function
		} else if (sizeSourceLength > stringBestSource.length()) {
			sizeSourceLength--;
		}
		try {
			for(Species species : listSpecies) {
				species.initalizeDay(day);
				executorService.execute(species);
			}
			executorService.shutdown();
			long timeStart = System.nanoTime();
			// Species ExecutorService is within this ExecutorService. Don't shutdownNow here as it hangs a Species forever
			while(!executorService.awaitTermination(Environment.MAX_EXECUTE_MILLISECONDS*Species.MAX_POPULATION, TimeUnit.MILLISECONDS)) {
				LOGGER.warning("Runaway day for " + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - timeStart) + "ms");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			LOGGER.severe("Exception on terminate");
		}
	}
	
	public boolean isSolved() {
		for(Species species : listSpecies) {
			if(species.fitnessBest!=null && species.fitnessBest.meanError.compareTo(Constants.I0) == 0) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		GP gp = new GP();
		gp.simulate();
	}
}
