(ns clj-app.main
  (:gen-class)
  (:require
   [config.core :refer [env]]
   [io.pedestal.http :as http]
   [io.pedestal.http.body-params :as body-params]
   [io.pedestal.http.route :as route]
   [namejen.names :as name]
   [next.jdbc :as jdbc]
   [next.jdbc.result-set :as rs]
   [next.jdbc.sql :as sql]))

;; ci testing

(def db-url (:jdbc-url env))

(def builder-options {:return-keys true :builder-fn rs/as-unqualified-maps})

(def db-interceptor
  {:name ::db-interceptor
   :enter (fn [context]
            (let [db-conn (jdbc/with-options db-url {})]
              (assoc context :db-conn db-conn)))})

(def healthcheck
  {:name ::healthcheck
   :enter
   (fn [context]
     (assoc context :response
            {:status 200 :body "Server is running!"}))})

(def add-random
  {:name ::add-random
   :enter
   (fn [context]
     (let [random-add (sql/insert! db-url :random
                                   {:name   (name/generic-name)
                                    :number (rand-int 1000)} builder-options)]
       (assoc context :response
              {:status 201
               :body   random-add})))})

(def list-random
  {:name ::list-random
   :enter
   (fn [context]
     (let [random-return (sql/find-by-keys db-url :random :all builder-options)]
       (assoc context :response
              {:status 200
               :body   random-return})))})

(def find-random
  {:name ::find-random
   :enter
   (fn [context]
     (let [id (Integer/parseInt (-> context :request :path-params :id))
           random-item (sql/find-by-keys db-url :random {:id id} builder-options)]
       (assoc context :response
              {:status 200
               :body   random-item})))})

(def routes
  (route/expand-routes
   #{["/"                :get  [healthcheck]]
     ["/add-random"      :get  [db-interceptor
                               http/json-body
                               add-random]]
     ["/list-random"     :get  [db-interceptor
                               http/json-body
                               list-random]]
     ["/find-random/:id" :get [db-interceptor
                               http/json-body
                               find-random]]}))

(def server {::http/join? false
             ::http/port 8090
             ::http/host "0.0.0.0"
             ::http/routes routes
             ::http/type :jetty})

(defonce runnable-service (http/create-server server))

(defn -main
  []
  (http/start runnable-service))
