package com.artgr.montyhall.data.entity;

import lombok.Builder;
import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class AggregatedData {
    private String id;
    private Integer count;
}
