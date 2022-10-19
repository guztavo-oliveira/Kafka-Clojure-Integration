(ns kafka-clojure.core2
  (:gen-class)
  (:import
   [org.apache.kafka.clients.producer KafkaProducer ProducerRecord]
   [org.apache.kafka.common.serialization StringDeserializer StringSerializer]
   org.apache.kafka.clients.consumer.KafkaConsumer
   (java.time Duration)))


(defn buildProducer [bootstrap-server]
  (KafkaProducer. {"value.serializer"  StringSerializer
                   "key.serializer"    StringSerializer
                   "bootstrap.servers" bootstrap-server}))


(defn sendMessage [producer topic message]
  (with-open []
    (.send producer (ProducerRecord. topic message))))



(defn buildConsumer [bootstrap-server group]
  (KafkaConsumer. {"bootstrap.servers" bootstrap-server
                   "key.deserializer"   StringDeserializer
                   "value.deserializer" StringDeserializer
                   "auto.offset.reset"  "earliest"
                   "allow.auto.create.topics" "false"
                   "group.id" group}))

(defn do-consumer [consumer topic-list]

  (.subscribe consumer topic-list)
  (while true
    (let [message-list (.poll consumer (Duration/ofMillis 100))]
      (doseq [message message-list]
        (println  (str (.value message)))))))

(def args
  '({:bootstrap-server "host.docker.internal:29092" :topic-list ["topic1"] :group "teste" :message "mais um hello world"}))



(into {} (vec args))

(defn -main [& args]
  ;; host.docker.internal:29092
  (println "=========================================")
  (println (vec args) (type args))
  (println "=========================================")

  ;; {:bootstrap-server "host.docker.internal:29092" :topic-list "topic1" :group "teste" :message "mais um hello world"}


  (let [config (into {} (vec args))
        bootstrap-server (str (-> config :bootstrap-server))
        topic-list [(-> config :topic-list)]
        ;; (-> config :topic-list)
        group (str (-> config :group))
        message (str (-> config :message))
        ;; producer (buildProducer bootstrap-server)
        ;; consumer (buildConsumer bootstrap-server group)
        ]

    (println "=========================================")
    (println bootstrap-server)
    (println "=========================================")

    ;; (sendMessage producer (first topic-list) message)
    ;; (do-consumer consumer topic-list)
    ))




