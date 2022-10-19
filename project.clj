(defproject kafka-clojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.apache.kafka/kafka-clients "3.0.0"]
                 [org.apache.kafka/kafka_2.12 "3.0.0"]                 
                 [org.slf4j/slf4j-log4j12 "1.7.1"]]
  :main ^:skip-aot kafka-clojure.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
