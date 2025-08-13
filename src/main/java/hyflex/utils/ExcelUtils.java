package hyflex.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import hyflex.entity.ExcelOutputEntity;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {
    /**
     * 生成Excel文件
     * @param excelOutputEntities excel 存储元信息列表
     * @param outputPath excel 输出路径（文件夹路径）
     */
    public static void generateExcelFile(List<ExcelOutputEntity> excelOutputEntities, String outputPath) {
        outputPath=outputPath+ File.separator + "rankerResult.xlsx";

        ExportParams exportParams = new ExportParams("运行结果信息", "数据表");

        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, ExcelOutputEntity.class, excelOutputEntities);

        // 写入到本地文件
        File saveFile = new File(outputPath);
        try (FileOutputStream fos = new FileOutputStream(saveFile)) {
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    /**
     * 将ExtractScoreItemByHyperHeuristic列表转换为ExcelOutputEntity列表
     * @param hyperHeuristicItems 评分算法运行结果
     * @return Excel 元信息列表
     */
    public static List<ExcelOutputEntity> convertHyperHeuristicToExcelEntities(List<ExtractScoreUtils.ExtractScoreItemByHyperHeuristic> hyperHeuristicItems) {
        List<ExcelOutputEntity> excelEntities = new ArrayList<>();

        for (ExtractScoreUtils.ExtractScoreItemByHyperHeuristic hyperHeuristicItem : hyperHeuristicItems) {
            String hyperHeuristicName = hyperHeuristicItem.getHyperHeuristicName();
            double totalScore = hyperHeuristicItem.getHyperHeuristicScore();

            for (ExtractScoreUtils.ExtractScoreItemByProblemDomain problemDomainItem : hyperHeuristicItem.getProblemDomainScoreList()) {
                String problemDomain = problemDomainItem.getProblemDomainName();
                double problemScore = problemDomainItem.getProblemDomainScore();

                for (ExtractScoreUtils.ExtractScoreItemByInstance instanceItem : problemDomainItem.getInstanceScoreList()) {
                    ExcelOutputEntity excelEntity = new ExcelOutputEntity();
                    excelEntity.setHyperHeuristicName(hyperHeuristicName);
                    excelEntity.setProblemDomain(problemDomain);
                    excelEntity.setInstance(instanceItem.getInstanceName());
                    excelEntity.setBestValue(instanceItem.getInstanceValue());
                    excelEntity.setInstanceScore(instanceItem.getInstanceScore());
                    excelEntity.setProblemScore(problemScore);
                    excelEntity.setTotalScore(totalScore);
                    excelEntities.add(excelEntity);
                }
            }
        }

        return excelEntities;
    }

    public static List<ExcelOutputEntity> convertProblemDomainToExcelEntities(List<ExtractScoreUtils.ExtractScoreItemByProblemDomain> problemDomainItems){

        List<ExcelOutputEntity> excelEntities = new ArrayList<>();
        for (ExtractScoreUtils.ExtractScoreItemByProblemDomain problemDomainItem : problemDomainItems) {
            String problemDomain = problemDomainItem.getProblemDomainName();
            String hyperHeuristicName = problemDomainItem.getHyperHeuristicName();
            double problemScore = problemDomainItem.getProblemDomainScore();
            for (ExtractScoreUtils.ExtractScoreItemByInstance instanceItem : problemDomainItem.getInstanceScoreList()) {
                ExcelOutputEntity excelEntity = new ExcelOutputEntity();
                excelEntity.setHyperHeuristicName(hyperHeuristicName);
                excelEntity.setProblemDomain(problemDomain);
                excelEntity.setInstance(instanceItem.getInstanceName());
                excelEntity.setBestValue(instanceItem.getInstanceValue());
                excelEntity.setInstanceScore(instanceItem.getInstanceScore());
                excelEntity.setProblemScore(problemScore);
                excelEntities.add(excelEntity);
            }
        }
        return excelEntities;
    }

    public static List<ExcelOutputEntity> convertInstanceToExcelEntities(List<ExtractScoreUtils.ExtractScoreItemByInstance> instanceItems){
        ArrayList<ExcelOutputEntity> excelEntities = new ArrayList<>();
        for (ExtractScoreUtils.ExtractScoreItemByInstance instanceItem : instanceItems) {
            ExcelOutputEntity excelOutputEntity = new ExcelOutputEntity();
            excelOutputEntity.setHyperHeuristicName(instanceItem.getHyperHeuristicName());
            excelOutputEntity.setProblemDomain(instanceItem.getProblemDomainName());
            excelOutputEntity.setInstance(instanceItem.getInstanceName());
            excelOutputEntity.setBestValue(instanceItem.getInstanceValue());
            excelOutputEntity.setInstanceScore(instanceItem.getInstanceScore());
            excelEntities.add(excelOutputEntity);
        }
        return excelEntities;
    }
}
