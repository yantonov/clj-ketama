(ns clj-ketama.consistent-hash-test
  (:require [clojure.test :refer :all])
  (:require [clj-ketama.consistent-hash])
  (:import [clj_ketama.consistent_hash ConsistentHash])
  (:require [clj-ketama.server])
  (:import [clj_ketama.server Server]))

(deftest no-servers
  (let [h (ConsistentHash. [])]
    (is (thrown? IllegalArgumentException
                 (. h build [])))))

(deftest single-server
  (let [h (ConsistentHash. [(Server. "svr" 123)])
        c (. h build)]
    (is (= "svr"
           (-> (. c find-point-for 0)
               :server
               :name)))))

(deftest write-non-trivial-test-for-ring-distribution
  (is false))

