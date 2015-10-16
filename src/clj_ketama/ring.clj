(ns clj-ketama.ring
  (:import [java.lang UnsupportedOperationException]))

(defprotocol IRing
  (find-point-for [this ^long hash]))

(defn- find-ceiling [points hash]
  "find points with hash greater or equal to given hash, if no such points return first point"
  (loop [left 0
         right (dec (count points))]
    (if (= left right)
      (let [point (nth points left)]
        (if (>= (:hash point) hash)
          point
          (nth points (rem (inc left) (count points)))))
      (let [center (quot (+ left right) 2)
            point (nth points center)]
        (if (>= (:hash point) hash)
          (recur left center)
          (recur (inc center) right))))))

(defn- sorted-by-hash? [points]
  "check if given vector of points sorted by point hash"
  (every? (fn [[a b]]
            (< (:hash a)
               (:hash b)))
          (partition 2 1 points)))

(defrecord Ring [points]
  IRing
  (find-point-for [this hash]
    (cond
      (neg? hash)
      (throw (UnsupportedOperationException. "hash value is less than zero"))

      true
      (let [pts (vec (:points this))]
        (find-ceiling pts hash)))))

(defn make-ring [points]
  (let [pts (vec points)]
    (cond
      (empty? pts)
      (throw (UnsupportedOperationException. "no points"))

      (some #(neg? (:hash %)) pts)
      (throw (UnsupportedOperationException. "some points has negative hash values"))

      (not (sorted-by-hash? pts))
      (throw (UnsupportedOperationException. "points are not sorted by hash"))

      true
      (Ring. pts))))
