package com.kute.util.thread.eventbus.event;

import lombok.*;

import java.time.Instant;

/**
 * created by bailong001 on 2019/03/08 15:34
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent extends BaseEvent {

    private Integer orderId;

    private Instant date;

}
