(ns clj-ketama.point
  (:require [clj-ketama.server])
  (:import [clj_ketama.server Server]))

(defrecord Point [^Server server
                  ^long hash])
