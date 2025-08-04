package hyflex.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * excel输出实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelOutputEntity {

    @Excel(name = "heuristic算法",orderNum = "1")
    private String hyperHeuristicName;

    @Excel(name = "问题领域", orderNum = "2")
    private String problemDomain;

    @Excel(name = "实例", orderNum = "3")
    private String instance;

    @Excel(name = "最佳值", orderNum = "4")
    private Double bestValue;

    @Excel(name = "实例得分", orderNum = "5")
    private Double instanceScore;

    @Excel(name = "问题得分", orderNum = "6")
    private Double problemScore;

    @Excel(name = "算法总得分", orderNum = "7")
    private Double totalScore;

}
