package org.vrteam.ag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @title：
 * @author: Jeffery
 * @date: 2025/7/17 20:20
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NPCMovementCommand {
    private String npcId;
    private float targetX;
    private float targetY;
    private float targetZ;
    private float speed;
}
