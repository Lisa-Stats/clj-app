(ns clj-app.main
  (:gen-class)
  (:require
   [io.pedestal.http :as http]
   [io.pedestal.http.route :as route]))

(defn respond-hello [_request]
  {:status 200 :body "Hello, everybody!"})

(def routes
  (route/expand-routes
   #{["/" :get respond-hello :route-name :hello]}))

(def server {::http/join? false
             ::http/port 8090
             ::http/host "0.0.0.0"
             ::http/routes routes
             ::http/type :jetty})

(defonce runnable-service (http/create-server server))

(defn -main
  []
  (http/start runnable-service))
