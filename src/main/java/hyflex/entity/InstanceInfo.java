package hyflex.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * instance运行存储信息
 */
@Data
@AllArgsConstructor
public class InstanceInfo {
    private String instanceName;
    private double instanceValue;
}
