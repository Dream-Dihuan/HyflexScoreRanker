package hyflex.utils;

import hyflex.entity.HyperHeuristicInfo;
import hyflex.entity.InstanceInfo;
import hyflex.entity.ProblemDomainInfo;
import hyflex.constantData.CHeSC_2011_InstanceName_of_ProblemDomain;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class ExtractScoreUtils {

    /**
     * json元信息存储实体类
     */
    @Data
    @AllArgsConstructor
    public static class ExtractScoreItemByInstance {
        private String hyperHeuristicName;
        
        private String problemDomainName;
        
        private String instanceName;
        
        private double instanceValue;

        private double instanceScore=0;
    }

    /**
     * 根据实例来提取实例
     * @param hyperHeuristicInfos Json提取后原始数据列表
     * @param targetHyperHeuristicName 目标超启发式算法名称
     * @param targetProblemDomainName 目标问题域名称
     * @param targetInstanceName 目标实例名称
     * @return 提取的实例数据列表
     */
    public static List<ExtractScoreItemByInstance> extractScoreItemsByInstance(List<HyperHeuristicInfo> hyperHeuristicInfos,String targetHyperHeuristicName, String targetProblemDomainName,
                                                                               String targetInstanceName) {
        List<ExtractScoreItemByInstance> result = new ArrayList<>();

        for (HyperHeuristicInfo hyperHeuristicInfo : hyperHeuristicInfos) {
            if(hyperHeuristicInfo.getHyperHeuristicName().equals(targetHyperHeuristicName)||targetHyperHeuristicName==null){
                for (ProblemDomainInfo problemDomainInfo : hyperHeuristicInfo.getProblemDomainInfoList()) {
                    if(problemDomainInfo.getProblemDomainName().equals(targetProblemDomainName)|| targetProblemDomainName == null){
                        for (InstanceInfo instanceInfo : problemDomainInfo.getInstanceInfoList()) {
                            if(instanceInfo.getInstanceName().equals(targetInstanceName)|| targetInstanceName == null){
                                result.add(new ExtractScoreItemByInstance(hyperHeuristicInfo.getHyperHeuristicName(),
                                        problemDomainInfo.getProblemDomainName(),
                                        instanceInfo.getInstanceName(),
                                        instanceInfo.getInstanceValue(),
                                        0));
                            }
                        }
                    }
                }
            }
        }

        return result;
    }



    @Data
    @AllArgsConstructor
    public static class ExtractScoreItemByProblemDomain {
        private String hyperHeuristicName;

        private String problemDomainName;

        private double problemDomainScore=0;

        private List<ExtractScoreItemByInstance> instanceScoreList;
    }

    /**
     * 根据问题域来提取实例
     * @param hyperHeuristicInfos Json提取后原始数据列表
     * @param targetProblemDomainName 目标问题域名称
     * @return 提取的实例数据列表
     */
    public static List<ExtractScoreItemByProblemDomain> extractScoreItemsByProblemDomain(List<HyperHeuristicInfo> hyperHeuristicInfos, String targetProblemDomainName) {
        List<ExtractScoreItemByProblemDomain> result = new ArrayList<>();

        for (HyperHeuristicInfo hyperHeuristicInfo : hyperHeuristicInfos) {
            for (ProblemDomainInfo problemDomainInfo : hyperHeuristicInfo.getProblemDomainInfoList()) {
                if(problemDomainInfo.getProblemDomainName().equals(targetProblemDomainName)){
                    result.add(new ExtractScoreItemByProblemDomain(hyperHeuristicInfo.getHyperHeuristicName(),
                            problemDomainInfo.getProblemDomainName(),
                            0,
                            extractScoreItemsByInstance(hyperHeuristicInfos,hyperHeuristicInfo.getHyperHeuristicName(),targetProblemDomainName,null)));
                }
            }
        }

        return result;
    }

    @Data
    @AllArgsConstructor
    public static class ExtractScoreItemByHyperHeuristic {
        private String hyperHeuristicName;
        private List<ExtractScoreItemByProblemDomain> problemDomainScoreList;
        private double hyperHeuristicScore=0;
    }

    /**
     * 根据超启发式算法来提取实例
     * @param hyperHeuristicInfos Json提取后原始数据列表
     * @return 提取的实例数据列表
     */
    public static List<ExtractScoreItemByHyperHeuristic> extractHyperHeuristicItemsByScores(List<HyperHeuristicInfo> hyperHeuristicInfos)
    {
        List<ExtractScoreItemByHyperHeuristic> result = new ArrayList<>();

        for (HyperHeuristicInfo hyperHeuristicInfo : hyperHeuristicInfos) {
            List<ExtractScoreItemByProblemDomain> problemDomainScoreList = new ArrayList<>();
            for (String problemName : CHeSC_2011_InstanceName_of_ProblemDomain.problemNameList) {
                List<ExtractScoreItemByProblemDomain> extractScoreItemByProblemDomainList = extractScoreItemsByProblemDomain(hyperHeuristicInfos, problemName);

                List<ExtractScoreItemByProblemDomain> filteredList = extractScoreItemByProblemDomainList.stream()
                        .filter(item -> item.getHyperHeuristicName().equals(hyperHeuristicInfo.getHyperHeuristicName()))
                        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

                problemDomainScoreList.addAll(filteredList);
            }
            result.add(new ExtractScoreItemByHyperHeuristic(hyperHeuristicInfo.getHyperHeuristicName(), problemDomainScoreList,0));
        }
        return result;
    }
}
