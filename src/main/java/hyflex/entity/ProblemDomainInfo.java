package hyflex.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 问题域运行存储数据
 */
@Data
@AllArgsConstructor
public class ProblemDomainInfo {
    private String problemDomainName;

    private List<InstanceInfo> instanceInfoList;
}
