package com.wix.gempuzzle.game;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class CollectionsShuffler implements Function<List<Integer>, List<Integer>>{
    @Override
    public List<Integer> apply(List<Integer> integers) {
        Collections.shuffle(integers);
        return integers;
    }
}
