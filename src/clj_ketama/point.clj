(ns clj-ketama.point
  (:require [clj-ketama.server])
  (:import [clj_ketama.server Server]))

(defrecord Point [^Server server
                  ^long hash])

(defn make-point [server hash]
  (Point. server hash))
