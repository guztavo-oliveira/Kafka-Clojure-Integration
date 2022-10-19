(ns kafka-clojure.core
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
  (KafkaConsumer. {"bootstrap.servers"  bootstrap-server
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

(defn -main [& args]
  ;; the arguments must be passed in this order bootstrap-server topic group message
  ;;"host.docker.internal:29092" "topic1" teste "outro teste"

  (let [bootstrap-server (str (nth args 0))
        topic-list [(nth args 1)]
        group (str (nth args 2))
        message (str (nth args 3))

        producer (buildProducer bootstrap-server)

        consumer (buildConsumer bootstrap-server group)]

    (sendMessage producer (first topic-list) message)

    (do-consumer consumer topic-list)))
