(ns util.migration
  (:require
   [config.core :refer [env]]
   [migratus.core :as migratus]
   [next.jdbc :as jdbc]))

(def migration-config
  {:store         :database
   :migration-dir "resources/migrations/"
   :init-script   "init.sql"
   :db            {:connection (jdbc/get-connection (:jdbc-url env))}})

(defn create-migration
  []
  (migratus/create migration-config))

(defn init
  []
  (migratus/init migration-config))

(defn migrate
  []
  (migratus/migrate migration-config))

(defn reset
  []
  (migratus/reset migration-config))

(defn rollback
  []
  (migratus/rollback migration-config))
