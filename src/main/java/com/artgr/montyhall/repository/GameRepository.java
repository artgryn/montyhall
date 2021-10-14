package com.artgr.montyhall.repository;

import com.artgr.montyhall.data.entity.AggregatedData;
import com.artgr.montyhall.data.entity.Game;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, ObjectId> {

    @Aggregation(pipeline = { "{$match : {isWinner : true}}","{$group: {_id: '$winningBox', count: { $sum: 1 }}}","{$sort : { count: -1 }}"})
    AggregationResults<AggregatedData> aggregateBasicData();
}
