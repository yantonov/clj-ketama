(ns clj-ketama.server)

(defrecord Server
    [^String name
     ^int weight])

(defn make-server
  "create server, optionally with weight"
  ([name] (make-server name 1))
  ([name weight] (Server. name weight)))
