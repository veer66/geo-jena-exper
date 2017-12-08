(defproject geo-jena-exper "0.1.0-SNAPSHOT"
  :description "A simplified example of Geospartial query on Apache Jena written in Clojure"
  :url "https://github.com/veer66/geo-jena-exper"
  :license {:name "2-Clause BSD License"
            :url "https://opensource.org/licenses/BSD-2-Clause"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.apache.jena/jena-core "3.6.0-SNAPSHOT"]
                 [org.apache.jena/jena-spatial "3.6.0-SNAPSHOT"]
                 [org.clojure/tools.logging "0.4.0"]]
  :main ^:skip-aot geo-jena-exper.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :repositories {"jena-snapshot" "https://repository.apache.org/content/repositories/snapshots/"})
