package statistics;

import agent.Heuristic;
import mummymaze.MummyMazeAgent;
import searchmethods.InformedSearch;
import searchmethods.SearchMethod;

public class StatisticNumGeneratedNodesInformed extends Statistic{
    public StatisticNumGeneratedNodesInformed(String fileName) {
        super(fileName);
    }

    @Override
    public String getStatisticValue(SearchMethod searchMethod, MummyMazeAgent agent) {
        if ((searchMethod instanceof InformedSearch)) {
            if (agent.hasSolution()){
                return searchMethod.getStatistics().numGeneratedNodes + "\t";

            }else{
                return "\t";
            }
        }

        return "";
    }

    @Override
    public String getStatisticHeader(SearchMethod[] searchMethods, Heuristic[] heuristics) {
        String header = "";

        for (SearchMethod searchMethod : searchMethods) {
            if ((searchMethod instanceof InformedSearch)) {
                for (Heuristic heuristic : heuristics) {
                    header  += searchMethod.toString() +", " + heuristic.toString() + "\t";
                }
            }
        }

        return header;
    }
}
