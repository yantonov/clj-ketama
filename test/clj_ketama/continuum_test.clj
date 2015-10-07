(ns clj-ketama.continuum-test
  (:require [clojure.test :refer :all])
  (:require [clj-ketama.continuum :refer :all])
  (:import [clj_ketama.continuum Continuum])
  (:require [clj-ketama.server])
  (:import [clj_ketama.server Server])
  (:require [clj-ketama.point])
  (:import [clj_ketama.point Point]))

(deftest no-points
  (is (thrown? UnsupportedOperationException
               (create-continuum []))))

(deftest negative-hash
  (let [c (create-continuum [(Point. (Server. "srv" 1) 123)])]
    (is (thrown? UnsupportedOperationException
                 (. c find-point-for -1)))))

(deftest negative-hash-values
  (is (thrown? UnsupportedOperationException
               (create-continuum [(Point. (Server. "s1" 1) 123)
                                  (Point. (Server. "s2" 1) 100000)
                                  (Point. (Server. "s3" 1) 456)]))))

(deftest single-point
  (let [c (create-continuum [(Point. (Server. "srv" 1) 123)])]
    (are [hash expected-name]
        (is (= (. c find-point-for hash) expected-name))
      0 "srv"
      124 "srv")))

(deftest couple-of-points
  (let [c (create-continuum [(Point. (Server. "s1" 1) 10)
                             (Point. (Server. "s2" 1) 20)])]
    (are [hash expected-name]
        (is (= (. c find-point-for hash) expected-name))
      10 "s1"
      11 "s2"
      19 "s2"
      20 "s2"
      21 "s1"
      100000 "s1"
      0 "s1")))

(deftest three-points
  (let [c (create-continuum [(Point. (Server. "s1" 1) 10)
                             (Point. (Server. "s2" 1) 20)
                             (Point. (Server. "s3" 1) 30)])]
    (are [hash expected-name]
        (is (= (-> (. c find-point-for hash)
                   :server
                   :name) expected-name))
      10 "s1"
      11 "s2"
      19 "s2"
      20 "s2"
      21 "s3"
      29 "s3"
      30 "s3"
      31 "s1"
      100000 "s1"
      0 "s1")))
