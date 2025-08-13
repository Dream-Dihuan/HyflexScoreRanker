package hyflex.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortUtils {
    /**
     * 根据 instanceScore 对 ExtractScoreItemByInstance 列表进行排序
     *
     * @param items 需要排序的列表
     * @param ascending true 为升序，false 为降序
     * @return 排序后的 ArrayList
     */
    public static ArrayList<ExtractScoreUtils.ExtractScoreItemByInstance> sortExtractScoreItemsByScore(
            List<ExtractScoreUtils.ExtractScoreItemByInstance> items, boolean ascending) {

        // 处理空列表情况
        if (items == null || items.isEmpty()) {
            return new ArrayList<>();
        }

        // 创建新的 ArrayList 并添加所有元素
        ArrayList<ExtractScoreUtils.ExtractScoreItemByInstance> sortedList = new ArrayList<>(items);

        // 根据参数确定排序方式
        if (ascending) {
            // 升序排序
            sortedList.sort(Comparator.comparingDouble(ExtractScoreUtils.ExtractScoreItemByInstance::getInstanceValue));
        } else {
            // 降序排序
            sortedList.sort(Comparator.comparingDouble(ExtractScoreUtils.ExtractScoreItemByInstance::getInstanceValue).reversed());
        }
        return sortedList;
    }

    public static List<ExtractScoreUtils.ExtractScoreItemByProblemDomain> sortExtractScoreItemsByProblemDomain(
            List<ExtractScoreUtils.ExtractScoreItemByProblemDomain> items) {

        // 按照各个问题域的实例名称进行排序
        for (ExtractScoreUtils.ExtractScoreItemByProblemDomain item : items) {
            String problemDomainName = item.getProblemDomainName();
            item.setInstanceScoreList(SortUtils.sortExtractScoreItemsByScore(item.getInstanceScoreList(), sortMethod(problemDomainName)));
        }

        return items;
    }

    public static List<ExtractScoreUtils.ExtractScoreItemByHyperHeuristic> sortExtractScoreItemsByHyperHeuristic(
            List<ExtractScoreUtils.ExtractScoreItemByHyperHeuristic> items) {

        // 对每个超启发式算法内部的问题域列表进行排序
        for (ExtractScoreUtils.ExtractScoreItemByHyperHeuristic item : items) {
            // 对每个问题域内的实例列表进行排序
            for (ExtractScoreUtils.ExtractScoreItemByProblemDomain problemDomainItem : item.getProblemDomainScoreList()) {
                problemDomainItem.setInstanceScoreList(
                        SortUtils.sortExtractScoreItemsByScore(problemDomainItem.getInstanceScoreList(), sortMethod(problemDomainItem.getProblemDomainName())));
            }
        }

        return items;
    }

    public static boolean sortMethod (String problemDomainName){
        switch (problemDomainName){
            case "TSP":
                return true;
            case "SAT":
                return false;
            case "FSP":
                return true;
            case "QAP":
                return true;
            case "Bin Packing":
                return true;
            case "VRP":
                return true;
            default:
                return true;
        }
    }
}
