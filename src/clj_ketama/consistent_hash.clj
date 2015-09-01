(ns clj-ketama.consistent-hash
  (:require [clj-ketama.continuum])
  (:import [clj_ketama.continuum Continuum])
  (:require [clj-ketama.server])
  (:import [clj_ketama.server Server])
  (:require [clj-ketama.point])
  (:import [clj_ketama.point Point])
  (:import [java.security MessageDigest]))

(defprotocol IConsistentHash
  (build [this]))

(defn- add-server [hash
                   server
                   total-weight
                   server-count]
  "adds server points accoring to weight"
  (let [md5 (MessageDigest/getInstance "MD5")
        w (:weight server)
        factor (int (Math/floor (/ (double (* 40 w server-count))
                                   (double total-weight))))]
    (reduce (fn [h iteration]
              (let [d (.digest
                       md5
                       (.getBytes (str (:name server)
                                       "-"
                                       iteration)))]
                (reduce (fn [h index]
                          (let [tmp (* index 4)
                                d1 (bit-and (long (aget d tmp)) 0xFF)
                                d2 (bit-and (long (aget d (inc tmp))) 0xFF)
                                d3 (bit-and (long (aget d (+ 2 tmp))) 0xFF)
                                d4 (bit-and (long (aget d (+ 3 tmp))) 0xFF)
                                k (long (bit-and (bit-or (bit-shift-left d4 24)
                                                         (bit-shift-left d3 16)
                                                         (bit-shift-left d2 8)
                                                         d1)
                                                 0xFFFFFFFF))]
                            (assoc h k server)))
                        h
                        (range 4))))
            hash 
            (range factor))))

(defn- get-points [servers]
  (let [total-weight (reduce + 0 (map :weight servers))
        server-count (count servers)
        long-to-server (reduce (fn [hash server]
                                 (add-server hash
                                             server
                                             total-weight
                                             server-count))
                               (hash-map)
                               servers)]
    (sort-by :hash
             (map (fn [[key value]]
                    (Point. value key))
                  long-to-server))))

(defrecord ConsistentHash [servers]
  IConsistentHash
  (build [this]
    (let [svrs (:servers this)]
      (cond
        (empty? svrs)
        (throw (IllegalArgumentException. "no servers"))

        true
        (Continuum. (get-points servers))))))
