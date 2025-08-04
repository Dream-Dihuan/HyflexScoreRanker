package hyflex.constantData;

import java.util.*;

public class CHeSC_2011_InstanceName_of_ProblemDomain {

    // 问题域与实例对应关系
    public static final Map<String, List<String>> problemInstances = new HashMap<>() {
        {
            put("SAT", new ArrayList<>(Arrays.asList(
                    "pg-525-2276-hyflex-3",
                    "pg-696-3122-hyflex-5",
                    "pg-525-2336-hyflex-4",
                    "jarv-684-2300-hyflex-10",
                    "hg4-300-1200-hyflex-11"
            )));
            put("TSP", new ArrayList<>(Arrays.asList(
                    "pr299-hyflex-0",
                    "usa13509-hyflex-8",
                    "rat575-hyflex-2",
                    "u2152-hyflex-7",
                    "d1291-hyflex-6"
            )));
            put("FSP", new ArrayList<>(Arrays.asList(
                    "tai100_20_02",
                    "tai500_20_02",
                    "tai100_20_04",
                    "tai200_20_01",
                    "tai500_20_03"
            )));
            put("QAP", new ArrayList<>(Arrays.asList(
                    "sko100a",
                    "tai100a",
                    "tai256c",
                    "tho150",
                    "wil100"
            )));
            put("BinPacking", new ArrayList<>(Arrays.asList(
                    "triples2004/instance1",
                    "falkenauer/u1000-01",
                    "test/testdual7/binpack0",
                    "50-90/instance1",
                    "test/testdual10/binpack0"
            )));
            put("VRP", new ArrayList<>(Arrays.asList(
                    "Homberger/RC/RC2-10-1",
                    "Solomon/R/R101",
                    "Homberger/C/C1-10-1",
                    "Solomon/R/R101",
                    "Homberger/RC/RC1-10-5"
            )));
        }
    };

    // 问题域名称列表
    public static final String[] problemNameList = {"SAT", "TSP", "FSP", "QAP","BinPacking","VRP"};
}
