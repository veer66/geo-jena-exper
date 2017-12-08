(ns geo-jena-exper.core
  (:require [clojure.string :as str]
            [clojure.tools.logging :as log])
  (:import [org.apache.jena.query.spatial EntityDefinition]
           [org.apache.jena.query
            ReadWrite
            QueryFactory
            DatasetFactory
            QueryExecutionFactory]
           [org.apache.lucene.store FSDirectory]
           [org.apache.jena.riot RDFDataMgr]
           [org.apache.jena.sparql.util QueryExecUtils]
           [org.apache.jena.query.spatial SpatialDatasetFactory])
  (:gen-class))

(defn init []
  (let [ent-def (EntityDefinition. "entityField" "geoField")
        idx-dir (-> "idx" java.io.File. .toPath FSDirectory/open)
        dataset (DatasetFactory/create)]
    (SpatialDatasetFactory/createLucene dataset idx-dir ent-def)))

(defn load-ttl [dataset]
  (.begin dataset ReadWrite/WRITE)
  (let [m (.getDefaultModel dataset)]
    (RDFDataMgr/read m "air.ttl")
    (.commit dataset))
  (.end dataset))

(defn query [dataset lat lon limit]
  (.begin dataset ReadWrite/READ)
  (let [q (QueryFactory/create
           (str "PREFIX spatial: <http://jena.apache.org/spatial#>"
                "SELECT ?p WHERE {"
                "  ?p spatial:nearby (" lat " " lon " " limit " 'km') ."
                "}"))
        q-exec (QueryExecutionFactory/create q dataset)]
    (QueryExecUtils/executeQuery q q-exec)
    (.end dataset)))

(defn -main
  [& args]
  (let [dataset (init)]
    (load-ttl dataset)
    (println "100 KM from Rayong")
    (query dataset 12.6813 101.2816 100.0)
    (println "1000 KM from Rayong")
    (query dataset 12.6813 101.2816 1000.0)))
