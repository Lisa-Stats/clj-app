(defproject clj-app "0.0.1-SNAPSHOT"
  :description "Simple clj server"
  :dependencies [[com.github.seancorfield/next.jdbc "1.3.847"]
                 [eigenhombre/namejen               "0.1.23"]
                 [io.pedestal/pedestal.service      "0.5.8"]
                 [io.pedestal/pedestal.route        "0.5.8"]
                 [io.pedestal/pedestal.jetty        "0.5.8"]
                 [migratus                          "1.4.5"]
                 [org.clojure/clojure               "1.10.3"]
                 [org.clojure/data.json             "1.0.0"]
                 [org.postgresql/postgresql         "42.5.0"]
                 [org.slf4j/slf4j-simple            "1.7.30"]
                 [yogthos/config                    "1.2.0"]]
  :main ^{:skip-aot true} clj-app.main
  :profiles {:dev {:resource-paths ["config/"]
                   :plugins        [[cider/cider-nrepl "0.28.7"]]}

             :uberjar {:aot [clj-app.main]}
             ;;  add migration profiles here?
             })
