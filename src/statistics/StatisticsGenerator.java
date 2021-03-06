package statistics;

import agent.Action;
import agent.Heuristic;
import mummymaze.MummyMazeAgent;
import mummymaze.MummyMazeProblem;
import mummymaze.MummyMazeState;
import searchmethods.*;

import java.io.File;
import java.util.LinkedList;

public class StatisticsGenerator {
    MummyMazeAgent agent;
    int limit = 100;
    int beamSize = 100;
    String folder = "statistics/";
    double costToSolution = 0;
    LinkedList<Statistic> statisticsList = new LinkedList<>();

    public StatisticsGenerator(MummyMazeAgent agent, int limitDepthSearch, int beamSize) {
        this.agent = agent;
        this.limit = limitDepthSearch;
        this.beamSize = beamSize;

        File folder = new File(this.folder);
        if (!folder.exists()) {
            folder.mkdir();
        }

    }

    public void addStatistics(Statistic statistic) {
        statisticsList.add(statistic);
    }

    /*
    * Vamos executar todas os algoritmos em todos os niveis.
    * Depois cada estatistica é que decide o que é quer guardar
    *
    * */
    public void generateStatistics(){
        File folder = new File("Niveis");
        File[] listOfFiles = folder.listFiles();

        SearchMethod[] searchMethodsArray = agent.getSearchMethodsArray();
        createFile(searchMethodsArray);


        for (File levelFile : listOfFiles) {
            if (levelFile.isFile()) {
                System.out.println("Level: " + levelFile.getName());
                addToFile(""+levelFile.getName());
                try {
                    agent.readInitialStateFromFile(levelFile);

                    for (SearchMethod searchMethod : searchMethodsArray) {

                        if (searchMethod instanceof InformedSearch) {
                            for (Heuristic heuristic : agent.getHeuristicsArray()) {
                                System.out.println("Heuristic: " + heuristic.toString());
                                heuristic.setAdmissivel(true);
                                agent.setHeuristic(heuristic);
                                run(searchMethod);
                                if (costToSolution != agent.getSolution().getCost()) {
                                    heuristic.setAdmissivel(false);

                                }
                                addStatisticValueToFile(searchMethod);
                                heuristic.setAdmissivel(true);

                            }
                        }else{
                            run(searchMethod);
                        }
                        // guardar custo de solucao numa pasta para depois
                        // conseguirmos saber se uma heuristica é admissivel
                        if (searchMethod instanceof BreadthFirstSearch) {
                            costToSolution = agent.hasSolution() ? agent.getSolution().getCost() : 0;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    finishFileLine();
                    continue;
                }
                finishFileLine();
            }
        }
    }

    private void run(SearchMethod searchMethod) {
        System.out.println("Generating statistics for " + searchMethod.toString() +":");
        prepareAlghoritms(searchMethod);
        agent.setSearchMethod(searchMethod);
        MummyMazeProblem problem = new MummyMazeProblem((MummyMazeState) agent.getEnvironment().clone());
        agent.solveProblem(problem);
        System.out.println(agent.getSearchReport());

    }

    private void prepareAlghoritms(SearchMethod searchMethod) {
        if (searchMethod instanceof DepthLimitedSearch) {
            ((DepthLimitedSearch) searchMethod).setLimit(limit);
        } else if (searchMethod instanceof BeamSearch) {
            ((BeamSearch) searchMethod).setBeamSize(beamSize);
        }
    }

    private void createFile(SearchMethod[] searchMethods) {
        for (Statistic statistic : statisticsList) {
                File file = new File(statistic.fileName);
                if(!file.exists()){
                    utils.FileOperations.appendToTextFile(
                            folder + statistic.fileName,
                            "\t"+statistic.getStatisticHeader(searchMethods, agent.getHeuristicsArray()));
                }
            finishFileLine();

        }

    }

    private void addStatisticValueToFile(SearchMethod searchMethod){
            for (Statistic statistic : statisticsList) {
                    utils.FileOperations.appendToTextFile(folder + statistic.fileName, statistic.getStatisticValue(searchMethod, agent));
            }

    }

    private void addToFile(String string) {
        for (Statistic statistic : statisticsList) {
            utils.FileOperations.appendToTextFile(folder + statistic.fileName, string+ "\t");
        }
    }

    private void finishFileLine(){
        for (Statistic statistic : statisticsList) {
            utils.FileOperations.appendToTextFile(folder + statistic.fileName, "\r\n");
        }
    }
}
