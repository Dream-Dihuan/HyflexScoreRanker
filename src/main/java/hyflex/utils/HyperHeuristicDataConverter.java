package hyflex.utils;

import hyflex.entity.HyperHeuristicInfo;
import hyflex.entity.InstanceInfo;
import hyflex.entity.ProblemDomainInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 工具类用于将JSON字符串转换为HyperHeuristicInfo列表
 */
public class HyperHeuristicDataConverter {

    /**
     * 表示原始JSON数据结构的类
     */
    public static class JsonData {
        private String heuristic;
        private String problemDomain;
        private String instance;
        private double bestValue;  // 改为bestValue而不是instanceScore

        // Getters and setters
        public String getHeuristic() { return heuristic; }
        public void setHeuristic(String heuristic) { this.heuristic = heuristic; }

        public String getProblemDomain() { return problemDomain; }
        public void setProblemDomain(String problemDomain) { this.problemDomain = problemDomain; }

        public String getInstance() { return instance; }
        public void setInstance(String instance) { this.instance = instance; }

        public double getBestValue() { return bestValue; }  // 改为getBestValue
        public void setBestValue(double bestValue) { this.bestValue = bestValue; }  // 改为setBestValue
    }

    /**
     * 将JSON字符串转换为HyperHeuristicInfo列表
     *
     * @param jsonString JSON字符串
     * @return HyperHeuristicInfo列表
     * @throws Exception JSON解析异常
     */
    public static List<HyperHeuristicInfo> convertJsonStringToHyperHeuristicInfo(String jsonString) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // 配置ObjectMapper忽略未知属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 解析JSON字符串为对象列表
        List<JsonData> rawDataList = objectMapper.readValue(jsonString, new TypeReference<List<JsonData>>() {});

        // 按heuristic分组
        Map<String, List<JsonData>> groupedByHeuristic = rawDataList.stream()
                .collect(Collectors.groupingBy(JsonData::getHeuristic));

        List<HyperHeuristicInfo> result = new ArrayList<>();

        // 处理每个heuristic组
        for (Map.Entry<String, List<JsonData>> heuristicEntry : groupedByHeuristic.entrySet()) {
            String hyperHeuristicName = heuristicEntry.getKey();
            List<JsonData> heuristicDataList = heuristicEntry.getValue();

            // 按problemDomain进一步分组
            Map<String, List<JsonData>> groupedByProblemDomain = heuristicDataList.stream()
                    .collect(Collectors.groupingBy(JsonData::getProblemDomain));

            List<ProblemDomainInfo> problemDomainInfoList = new ArrayList<>();

            // 处理每个problemDomain组
            for (Map.Entry<String, List<JsonData>> problemDomainEntry : groupedByProblemDomain.entrySet()) {
                String problemDomainName = problemDomainEntry.getKey();
                List<JsonData> problemDomainDataList = problemDomainEntry.getValue();

                // 按instance分组并计算平均值
                Map<String, List<JsonData>> groupedByInstance = problemDomainDataList.stream()
                        .collect(Collectors.groupingBy(JsonData::getInstance));

                List<InstanceInfo> instanceInfoList = new ArrayList<>();

                // 计算每个instance的平均bestValue
                for (Map.Entry<String, List<JsonData>> instanceEntry : groupedByInstance.entrySet()) {
                    String instanceName = instanceEntry.getKey();
                    List<JsonData> instanceDataList = instanceEntry.getValue();

                    // 计算平均bestValue（原instanceScore）
                    double averageBestValue = instanceDataList.stream()
                            .mapToDouble(JsonData::getBestValue)  // 使用getBestValue
                            .average()
                            .orElse(0.0);

                    instanceInfoList.add(new InstanceInfo(instanceName, averageBestValue));  // 使用averageBestValue
                }

                problemDomainInfoList.add(new ProblemDomainInfo(problemDomainName, instanceInfoList));
            }

            result.add(new HyperHeuristicInfo(hyperHeuristicName, problemDomainInfoList));
        }

        return result;
    }
}
