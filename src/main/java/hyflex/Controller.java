package hyflex;

import hyflex.config.configuration;
import hyflex.entity.ExcelOutputEntity;
import hyflex.entity.HyperHeuristicInfo;
import hyflex.utils.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static hyflex.config.configuration.JSON_FILE_PATH;
import static hyflex.data.TestValueAddition.generateInfo;
import static hyflex.utils.ExcelUtils.generateExcelFile;
import static hyflex.utils.ExtractScoreUtils.extractHyperHeuristicItemsByScores;
import static hyflex.utils.SortUtils.sortExtractScoreItemsByHyperHeuristic;
import static hyflex.utils.SortUtils.sortMethod;

public class Controller {

    /**
     * 按实例进行评分
     * @param ProblemDomainName 问题域名称
     * @param InstanceName 实例名称
     * @return 实例评分结果
     * @throws Exception
     */
    public static List<ExtractScoreUtils.ExtractScoreItemByInstance> ScoringByInstance (String ProblemDomainName,String InstanceName) throws Exception {
        List<HyperHeuristicInfo> compareData = new ArrayList<>();
        String jsonString = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));

        compareData = HyperHeuristicDataConverter.convertJsonStringToHyperHeuristicInfo(jsonString);
        compareData.add(generateInfo().get(0));

        List<ExtractScoreUtils.ExtractScoreItemByInstance> extractScoreItemByInstances = ExtractScoreUtils.extractScoreItemsByInstance(compareData,null, ProblemDomainName, InstanceName);
        ArrayList<ExtractScoreUtils.ExtractScoreItemByInstance> sortInstance = SortUtils.sortExtractScoreItemsByScore(extractScoreItemByInstances, sortMethod(ProblemDomainName));
        List<ExtractScoreUtils.ExtractScoreItemByInstance> result = ScoringUtils.ScoringInstanceScoreList(sortInstance);
        System.out.println(result);
        return result;
    }

    /**
     * 按问题域进行评分
     * @param ProblemDomainName 问题域名称
     * @return 问题域评分结果
     * @throws Exception
     */
    public static List<ExtractScoreUtils.ExtractScoreItemByProblemDomain> ScoringByProblemDomain(String ProblemDomainName)throws Exception {
        String jsonString = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));

        List<HyperHeuristicInfo> compareData = HyperHeuristicDataConverter.convertJsonStringToHyperHeuristicInfo(jsonString);
        compareData.add(generateInfo().get(0));

        List<ExtractScoreUtils.ExtractScoreItemByProblemDomain> problemDomain1 = ExtractScoreUtils.extractScoreItemsByProblemDomain(compareData, ProblemDomainName);
        System.out.println(problemDomain1);

        List<ExtractScoreUtils.ExtractScoreItemByProblemDomain> sortedItems = SortUtils.sortExtractScoreItemsByProblemDomain(problemDomain1);
        System.out.println(sortedItems);

        List<ExtractScoreUtils.ExtractScoreItemByProblemDomain> results = ScoringUtils.ScoringProblemDomainScoreList(sortedItems, ProblemDomainName);
        System.out.println(results);
        return results;
    }

    /**
     * 按超启发式算法进行评分
     * @return 超启发式算法评分结果
     * @throws Exception
     */
    public static List<ExtractScoreUtils.ExtractScoreItemByHyperHeuristic> ScoringByHyperHeuristic() throws Exception {
        String jsonString = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));

        List<HyperHeuristicInfo> compareData = HyperHeuristicDataConverter.convertJsonStringToHyperHeuristicInfo(jsonString);
        compareData.add(generateInfo().get(0));

        // 提取超启发式算法数据
        List<ExtractScoreUtils.ExtractScoreItemByHyperHeuristic> extractScoreItemByHyperHeuristics =
                extractHyperHeuristicItemsByScores(compareData);
        System.out.println(extractScoreItemByHyperHeuristics);

        // 对数据进行排序
        List<ExtractScoreUtils.ExtractScoreItemByHyperHeuristic> sortedItems =
                sortExtractScoreItemsByHyperHeuristic(extractScoreItemByHyperHeuristics);
        System.out.println(sortedItems);

        // 对超启发式算法进行评分
        List<ExtractScoreUtils.ExtractScoreItemByHyperHeuristic> scoredItems =
                ScoringUtils.ScoringHyperHeuristicScoreList(sortedItems);
        System.out.println(scoredItems);
        return scoredItems;
    }



    public static void main(String[] args) throws Exception {

        // hyper heuristic层面
        List<ExtractScoreUtils.ExtractScoreItemByHyperHeuristic> extractScoreItemByHyperHeuristics = ScoringByHyperHeuristic();
        List<ExcelOutputEntity> excelOutputEntities = ExcelUtils.convertHyperHeuristicToExcelEntities(extractScoreItemByHyperHeuristics);
        generateExcelFile(excelOutputEntities, configuration.EXCEL_OUTPUT_PATH);

        // problem domain 层面
//        List<ExtractScoreUtils.ExtractScoreItemByProblemDomain> extractScoreItemByProblemDomainList = ScoringByProblemDomain("VRP");
//        List<ExcelOutputEntity> excelOutputEntities = ExcelUtils.convertProblemDomainToExcelEntities(extractScoreItemByProblemDomainList);
//        generateExcelFile(excelOutputEntities, configuration.EXCEL_OUTPUT_PATH);

        // instance 层面
//        List<ExtractScoreUtils.ExtractScoreItemByInstance> extractScoreItemByInstances = ScoringByInstance("TSP", "pr299-hyflex-0");
//        List<ExcelOutputEntity> excelOutputEntities = ExcelUtils.convertInstanceToExcelEntities(extractScoreItemByInstances);
//        generateExcelFile(excelOutputEntities, configuration.EXCEL_OUTPUT_PATH);
    }
}
