package hyflex.utils;

import hyflex.constantData.CHeSC_2011_InstanceName_of_ProblemDomain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static hyflex.constantData.EightDriversScore.EIGHT_DRIVERS_SCORE_LIST;

public class ScoringUtils {

    /**
     * 评分实例得分
     * @param sortedInstanceScoreList 排序后的实例列表
     * @return 评分后的实例列表
     */
    public static List<ExtractScoreUtils.ExtractScoreItemByInstance> ScoringInstanceScoreList(List<ExtractScoreUtils.ExtractScoreItemByInstance> sortedInstanceScoreList){
        for (int i = 0; i < sortedInstanceScoreList.size(); i++) {
            if(i<EIGHT_DRIVERS_SCORE_LIST.length){
                ExtractScoreUtils.ExtractScoreItemByInstance newExtractScoreItemByInstance = sortedInstanceScoreList.get(i);
                newExtractScoreItemByInstance.setInstanceScore(EIGHT_DRIVERS_SCORE_LIST[i]);
                sortedInstanceScoreList.set(i,newExtractScoreItemByInstance);

            }
        }
        return sortedInstanceScoreList;
    }

    /**
     * 评分问题域得分
     * @param sortedProblemDomainScoreList 排序后的问题域列表
     * @param targetProblemDomain 目标问题域
     * @return 评分后的问题域列表
     */
    public static List<ExtractScoreUtils.ExtractScoreItemByProblemDomain> ScoringProblemDomainScoreList(List<ExtractScoreUtils.ExtractScoreItemByProblemDomain> sortedProblemDomainScoreList,String targetProblemDomain){
    // 获取目标问题域的示例名称列表
    List<String> instanceNameList = CHeSC_2011_InstanceName_of_ProblemDomain.problemInstances.get(targetProblemDomain);

    // 对每个实例进行处理
    for (String instanceName : instanceNameList) {
        // 收集所有算法在该实例上的结果
        List<ExtractScoreUtils.ExtractScoreItemByInstance> instancesForScoring = new ArrayList<>();

        for (ExtractScoreUtils.ExtractScoreItemByProblemDomain problemDomainItem : sortedProblemDomainScoreList) {
            for (ExtractScoreUtils.ExtractScoreItemByInstance instanceItem : problemDomainItem.getInstanceScoreList()) {
                if (instanceItem.getInstanceName().equals(instanceName)) {
                    instancesForScoring.add(instanceItem);
                    break;
                }
            }
        }

        // 根据问题域类型决定排序方式
        boolean ascending = SortUtils.sortMethod(targetProblemDomain);
        if (ascending) {
            // 升序排序
            instancesForScoring.sort(Comparator.comparingDouble(ExtractScoreUtils.ExtractScoreItemByInstance::getInstanceValue));
        } else {
            // 降序排序
            instancesForScoring.sort(Comparator.comparingDouble(ExtractScoreUtils.ExtractScoreItemByInstance::getInstanceValue).reversed());
        }

        // 按照八车手规则打分
        for (int i = 0; i < instancesForScoring.size() && i < EIGHT_DRIVERS_SCORE_LIST.length; i++) {
            instancesForScoring.get(i).setInstanceScore(EIGHT_DRIVERS_SCORE_LIST[i]);
        }
    }

    // 计算每个算法在该问题域上的总分
    for (ExtractScoreUtils.ExtractScoreItemByProblemDomain problemDomainItem : sortedProblemDomainScoreList) {
        double totalScore = 0;
        for (ExtractScoreUtils.ExtractScoreItemByInstance instanceItem : problemDomainItem.getInstanceScoreList()) {
            totalScore += instanceItem.getInstanceScore();
        }
        problemDomainItem.setProblemDomainScore(totalScore);
    }

    return sortedProblemDomainScoreList;
}

    /**
     * 评分超启发式算法得分
     * @param hyperHeuristicItems 超启发式算法列表
     * @return 评分后的超启发式算法列表
     */
    public static List<ExtractScoreUtils.ExtractScoreItemByHyperHeuristic> ScoringHyperHeuristicScoreList(
            List<ExtractScoreUtils.ExtractScoreItemByHyperHeuristic> hyperHeuristicItems) {

        // 首先对每个问题域分别进行评分，更新问题域分数和实例分数
        for (String problemDomain : CHeSC_2011_InstanceName_of_ProblemDomain.problemInstances.keySet()) {
            // 收集该问题域的数据用于评分
            List<ExtractScoreUtils.ExtractScoreItemByProblemDomain> problemDomainItems = new ArrayList<>();

            for (ExtractScoreUtils.ExtractScoreItemByHyperHeuristic hyperHeuristicItem : hyperHeuristicItems) {
                for (ExtractScoreUtils.ExtractScoreItemByProblemDomain problemDomainItem : hyperHeuristicItem.getProblemDomainScoreList()) {
                    if (problemDomainItem.getProblemDomainName().equals(problemDomain)) {
                        problemDomainItems.add(problemDomainItem);
                        break;
                    }
                }
            }

            // 复用已有的问题域评分逻辑
            ScoringProblemDomainScoreList(problemDomainItems, problemDomain);
        }

        // 计算每个超启发式算法的总分（所有问题域分数之和）
        for (ExtractScoreUtils.ExtractScoreItemByHyperHeuristic hyperHeuristicItem : hyperHeuristicItems) {
            double totalScore = 0;
            for (ExtractScoreUtils.ExtractScoreItemByProblemDomain problemDomainItem : hyperHeuristicItem.getProblemDomainScoreList()) {
                totalScore += problemDomainItem.getProblemDomainScore();
            }
            hyperHeuristicItem.setHyperHeuristicScore(totalScore);
        }

        // 按总分降序排序
        hyperHeuristicItems.sort(Comparator.comparingDouble(ExtractScoreUtils.ExtractScoreItemByHyperHeuristic::getHyperHeuristicScore).reversed());

        return hyperHeuristicItems;
    }

}
