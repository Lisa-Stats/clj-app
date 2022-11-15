(defproject clj-app "0.0.1-SNAPSHOT"
  :description "Simple clj server"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [io.pedestal/pedestal.service "0.5.8"]
                 [io.pedestal/pedestal.route   "0.5.8"]
                 [io.pedestal/pedestal.jetty   "0.5.8"]
                 [org.clojure/data.json        "1.0.0"]
                 [org.slf4j/slf4j-simple       "1.7.30"]]
  :main ^{:skip-aot true} clj-app.main
  :profiles {:uberjar {:aot [clj-app.main]}})
