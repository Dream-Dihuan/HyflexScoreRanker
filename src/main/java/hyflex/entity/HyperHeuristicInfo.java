package hyflex.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * HyperHeuristic的运行存储信息
 */
@Data
@AllArgsConstructor
public class HyperHeuristicInfo {
    private String HyperHeuristicName;

    private List<ProblemDomainInfo> problemDomainInfoList;

    @Override
    public String toString() {
        return "HyperHeuristicInfo{" +
                "HyperHeuristicName='" + HyperHeuristicName + '\'' +
                ", problemDomainInfoList=" + problemDomainInfoList +
                '}';
    }
}
