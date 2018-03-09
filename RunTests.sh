#!/bin/bash
make
for i in `seq 1 10`;
do
    echo ==== TEST $i ====
    make run < tests/test$i.toy
    echo
    echo -----------------
    echo
done
