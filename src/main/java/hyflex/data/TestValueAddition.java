package hyflex.data;

import hyflex.entity.HyperHeuristicInfo;
import hyflex.entity.InstanceInfo;
import hyflex.entity.ProblemDomainInfo;

import java.util.Arrays;
import java.util.List;

public class TestValueAddition {

    // Last-RL超启发式算法的运行结果数据
    public static List<HyperHeuristicInfo> generateInfo(){
        //LAST-RL
        List<InstanceInfo> LAST_RL_TSP_InstanceInfoList = Arrays.asList(
                new InstanceInfo("pr299-hyflex-0", 48194.92010274795),
                new InstanceInfo("usa13509-hyflex-8", 2.0908808236178078E7),
                new InstanceInfo("rat575-hyflex-2", 6821.995430235488),
                new InstanceInfo("u2152-hyflex-7", 66869.02321927367),
                new InstanceInfo("d1291-hyflex-6", 53030.55695151882)
                );

        List<InstanceInfo> LAST_RL_SAT_InstanceInfoList = Arrays.asList(
                new InstanceInfo("pg-525-2336-hyflex-4", 5.0),
                new InstanceInfo("pg-696-3122-hyflex-5", 10.0),
                new InstanceInfo("hg4-300-1200-hyflex-11", 3.0),
                new InstanceInfo("pg-525-2276-hyflex-3", 8.0),
                new InstanceInfo("jarv-684-2300-hyflex-10", 10.0)
        );

        List<InstanceInfo> LAST_RL_FSP_InstanceInfoList = Arrays.asList(
                new InstanceInfo("tai100_20_02", 6254.0),
                new InstanceInfo("tai500_20_02", 26819.0),
                new InstanceInfo("tai100_20_04", 6331.0),
                new InstanceInfo("tai200_20_01", 11411.0),
                new InstanceInfo("tai500_20_03", 26633.0)
        );

        List<InstanceInfo> LAST_RL_QAP_InstanceInfoList = Arrays.asList(
                new InstanceInfo("sko100a", 152334.0),
                new InstanceInfo("tai100a", 2.138009E7),
                new InstanceInfo("tai256c", 4.4848456E7),
                new InstanceInfo("tho150", 8171212.0),
                new InstanceInfo("wil100", 273336.0)
        );

        List<InstanceInfo> LAST_RL_BinPacking_InstanceInfoList = Arrays.asList(
                new InstanceInfo("triples2004/instance1", 0.027980227138644453),
                new InstanceInfo("falkenauer/u1000-01", 0.016164737842976895),
                new InstanceInfo("test/testdual7/binpack0", 0.03521330734968553),
                new InstanceInfo("50-90/instance1", 0.11133284198771631),
                new InstanceInfo("test/testdual10/binpack0", 0.05893379917185948)
        );

        List<InstanceInfo> LAST_RL_VRP_InstanceInfoList = Arrays.asList(
                new InstanceInfo("Homberger/RC/RC2-10-1", 61675.303793195824),
                new InstanceInfo("Solomon/R/R101", 13356.051256670911),
                new InstanceInfo("Homberger/C/C1-10-1", 145784.69975564512),
                new InstanceInfo("Solomon/R/R101", 20655.97640175985),
                new InstanceInfo("Homberger/RC/RC1-10-5", 147438.31458381517)
        );

        ProblemDomainInfo LAST_RL_TSP_ProblemDomainInfo = new ProblemDomainInfo("TSP", LAST_RL_TSP_InstanceInfoList);

        ProblemDomainInfo LAST_RL_SAT_ProblemDomainInfo = new ProblemDomainInfo("SAT", LAST_RL_SAT_InstanceInfoList);

        ProblemDomainInfo LAST_RL_FSP_ProblemDomainInfo = new ProblemDomainInfo("FSP", LAST_RL_FSP_InstanceInfoList);

        ProblemDomainInfo LAST_RL_QAP_ProblemDomainInfo = new ProblemDomainInfo("QAP", LAST_RL_QAP_InstanceInfoList);

        ProblemDomainInfo LAST_RL_BinPacking_ProblemDomainInfo = new ProblemDomainInfo("BinPacking", LAST_RL_BinPacking_InstanceInfoList);

        ProblemDomainInfo LAST_RL_VRP_ProblemDomainInfo = new ProblemDomainInfo("VRP", LAST_RL_VRP_InstanceInfoList);

        HyperHeuristicInfo LAST_RL_HyperHeuristicInfo = new HyperHeuristicInfo("LAST-RL", Arrays.asList(LAST_RL_TSP_ProblemDomainInfo,LAST_RL_SAT_ProblemDomainInfo,LAST_RL_FSP_ProblemDomainInfo,LAST_RL_QAP_ProblemDomainInfo,LAST_RL_BinPacking_ProblemDomainInfo,LAST_RL_VRP_ProblemDomainInfo));

        return Arrays.asList(LAST_RL_HyperHeuristicInfo);
    }
}
